#!/bin/bash
# start-emulator.sh (clean, stable, no redundant logs)

set -euo pipefail

adb start-server >/dev/null 2>&1
sleep 1

BOOT_TIMEOUT=180
BASE_DIR="$(cd "$(dirname "$0")" && pwd)"
CONFIG_FILE="$BASE_DIR/../src/test/resources/deviceConfig/android.json"
DEVICE_TAG=$(echo "${1:-}" | tr '[:upper:]' '[:lower:]')

if [ -z "$DEVICE_TAG" ]; then
    jq -r '.[].tag' "$CONFIG_FILE" | sed 's/^/  - /'
    exit 1
fi

DEVICE=$(jq -c --arg TAG "$DEVICE_TAG" '.[] | select(.tag==$TAG)' "$CONFIG_FILE")
if [ -z "$DEVICE" ]; then
    jq -r '.[].tag' "$CONFIG_FILE" | sed 's/^/  - /'
    exit 1
fi

AVD_NAME=$(echo "$DEVICE" | jq -r '."appium:deviceName"')
echo "📱 Preparing AVD: $AVD_NAME"

normalize() { echo "$1" | tr '[:upper:]' '[:lower:]' | tr -d '_ '; }

is_emulator_process_running() {
  pgrep -f "emulator.*-avd $AVD_NAME" >/dev/null
}

wait_for_adb_online() {
  local udid="$1"
  local retries=10
  local attempt=1

  while (( attempt <= retries )); do
    state=$(adb devices | awk -v u="$udid" '$1==u {print $2}')

    if [ "$state" = "device" ]; then
      echo "   ✅ adb online ($udid)"
      sleep 3
      return 0
    fi

    echo "   ⏳ adb state=$state (attempt $attempt/$retries)"
    sleep 3
    ((attempt++))
  done

  echo "❌ adb never became online for $udid"
  return 1
}

list_running_devices() {
  adb_devices_safe | awk 'NR>1 && $1 ~ /emulator-/ {print $1}'
}

adb_devices_safe() {
  adb devices 2>/dev/null || {
    sleep 1
    adb devices 2>/dev/null
  }
}

dynamic_wait_until() {
  local condition="$1"
  local label="$2"
  local timeout=$3
  local attempt=0
  local elapsed=0

  while [ $elapsed -lt $timeout ]; do
    if eval "$condition"; then
      return 0
    fi
    local wait=$((2**attempt))
    [ $wait -gt 5 ] && wait=5
    sleep $wait
    elapsed=$((elapsed+wait))
    attempt=$((attempt+1))
  done
  return 1
}

# --------------------------------------------------------
# Partner setting injection (clean)
# --------------------------------------------------------
enable_partner_setting() {
  local name="$1"
  local value="$2"

  adb -s "$FOUND_UDID" root >/dev/null 2>&1 || true
  adb -s "$FOUND_UDID" shell "content insert \
        --uri content://com.google.settings/partner \
        --bind name:s:${name} \
        --bind value:i:${value}" >/dev/null 2>&1 || true
}
# --------------------------------------------------------

FOUND_UDID=""
for d in $(list_running_devices); do
  avd=$(adb -s "$d" emu avd name 2>/dev/null | tr -d '\r\n' | sed 's/OK$//' | xargs)
  if [ "$(normalize "$avd")" = "$(normalize "$AVD_NAME")" ]; then
    FOUND_UDID=$d
    break
  fi
done

if [ -z "$FOUND_UDID" ]; then
  if ! is_emulator_process_running; then
    BEFORE_COUNT=$(list_running_devices | wc -l | tr -d ' ')
    nohup emulator -avd "$AVD_NAME" -no-snapshot-save -no-boot-anim -gpu swiftshader_indirect -netdelay none -netspeed full >/dev/null 2>&1 &
    dynamic_wait_until \
      "[ \$(list_running_devices | wc -l | tr -d ' ') -gt $BEFORE_COUNT ]" \
      "$AVD_NAME (adb detect)" $BOOT_TIMEOUT
  else
    echo "ℹ️ Emulator process already running, waiting for adb..."
  fi
fi

FOUND_UDID=""
for d in $(list_running_devices); do
  avd=$(adb -s "$d" emu avd name 2>/dev/null | tr -d '\r\n' | sed 's/OK$//' | xargs)
  if [ "$(normalize "$avd")" = "$(normalize "$AVD_NAME")" ]; then
    FOUND_UDID=$d
    break
  fi
done

[ -z "$FOUND_UDID" ] && exit 1

wait_for_adb_online "$FOUND_UDID" || exit 1
dynamic_wait_until "adb -s $FOUND_UDID shell getprop sys.boot_completed | grep -q '1'" \
  "$AVD_NAME boot" $BOOT_TIMEOUT

wait_for_emulator_ready() {
    local udid="$1"
    local timeout="${2:-30}"
    local interval=2
    local elapsed=0

    while (( elapsed < timeout )); do
      local focus
      focus=$(adb -s "$udid" shell dumpsys window | grep -E 'mCurrentFocus' 2>/dev/null || true)
      if [[ "$focus" =~ launcher|Launcher ]]; then
        return 0
      fi
      sleep "$interval"
      ((elapsed += interval))
    done
    return 1
}

wait_for_emulator_ready "$FOUND_UDID" 40

adb -s "$FOUND_UDID" shell settings put global captive_portal_detection_enabled 0
adb -s "$FOUND_UDID" shell settings put global private_dns_mode off
adb -s "$FOUND_UDID" shell svc wifi disable
sleep 1
adb -s "$FOUND_UDID" shell svc wifi enable

# --------------------------------------------------------
# Apply Google Partner flag (logsuz)
# --------------------------------------------------------
enable_partner_setting "network_location_opt_in" 1
# --------------------------------------------------------

MODEL=$(adb -s "$FOUND_UDID" shell getprop ro.product.model | tr -d '\r')
ANDROID_VER=$(adb -s "$FOUND_UDID" shell getprop ro.build.version.release | tr -d '\r')

echo "✅ Ready: $MODEL (AVD=$AVD_NAME, UDID=$FOUND_UDID, Android $ANDROID_VER)"
