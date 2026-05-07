#!/bin/bash
# =========================================================
# Start Emulator (Stable / Deterministic / Appium-Ready)
# =========================================================

set -euo pipefail

BOOT_TIMEOUT=180
BASE_DIR="$(cd "$(dirname "$0")" && pwd)"
CONFIG_FILE="$BASE_DIR/../src/test/resources/deviceConfig/android.json"

DEVICE_TAG=$(echo "${1:-}" | tr '[:upper:]' '[:lower:]')

log()  { echo "ℹ️ $*" >&2; }
ok()   { echo "✅ $*" >&2; }
err()  { echo "❌ $*" >&2; }

# --------------------------------------------------------
# Validate input
# --------------------------------------------------------

[ -z "$DEVICE_TAG" ] && {
  err "Missing device tag"
  jq -r '.[].tag' "$CONFIG_FILE"
  exit 1
}

DEVICE=$(jq -c --arg TAG "$DEVICE_TAG" '
  .[] | select((.tag | ascii_downcase) == $TAG)
' "$CONFIG_FILE")

[ -z "$(echo "$DEVICE" | tr -d '[:space:]')" ] && {
  err "Device not found: $DEVICE_TAG"
  exit 1
}

AVD_NAME=$(echo "$DEVICE" | jq -r '."appium:deviceName"')

# --------------------------------------------------------
# Helpers
# --------------------------------------------------------

normalize() {
  echo "$1" | tr '[:upper:]' '[:lower:]' | tr -d '_ '
}

adb_devices_safe() {
  adb devices 2>/dev/null || {
    sleep 1
    adb devices 2>/dev/null
  }
}

list_running_devices() {
  adb_devices_safe | awk 'NR>1 && $1 ~ /emulator-/ {print $1}'
}

get_avd_name_by_udid() {
  local udid="$1"
  adb -s "$udid" emu avd name 2>/dev/null \
    | tr -d '\r\n' | sed 's/OK$//' | xargs
}

find_udid_by_avd() {
  local target="$1"

  for d in $(list_running_devices); do
    local avd
    avd=$(get_avd_name_by_udid "$d")

    if [ "$(normalize "$avd")" = "$(normalize "$target")" ]; then
      echo "$d"
      return 0
    fi
  done

  echo ""
}

wait_until_udid_found() {
  local avd="$1"
  local timeout="$2"
  local elapsed=0

  while [ "$elapsed" -lt "$timeout" ]; do
    local udid
    udid=$(find_udid_by_avd "$avd")

    if [ -n "$udid" ]; then
      echo "$udid"
      return 0
    fi

    sleep 2
    elapsed=$((elapsed + 2))
  done

  return 1
}

wait_for_adb_fully_ready() {
  local udid="$1"
  local timeout=60
  local elapsed=0

  while [ "$elapsed" -lt "$timeout" ]; do

    # 1. boot tamam mı
    if adb -s "$udid" shell getprop sys.boot_completed 2>/dev/null | grep -q "1"; then

      # 2. adb çalışıyor mu
      if adb -s "$udid" shell echo "ping" >/dev/null 2>&1; then

        # 3. UI hazır mı (EN GÜVENLİ)
        if adb -s "$udid" shell dumpsys window 2>/dev/null | grep -q "mCurrentFocus"; then
          ok "Device ready ($udid)"
          return 0
        fi

      fi
    fi

    sleep 2
    elapsed=$((elapsed + 2))
  done

  err "Device not ready: $udid"
  return 1
}

start_emulator_if_needed() {
  local avd="$1"

  if pgrep -f "emulator.*-avd[[:space:]]$avd" >/dev/null; then
    return 0
  fi

  log "Starting emulator: $avd"

  nohup emulator -avd "$avd" \
    -no-snapshot-save \
    -no-boot-anim \
    -gpu swiftshader_indirect \
    -netdelay none \
    -netspeed full \
    >/dev/null 2>&1 &
}

apply_network_fixes() {
  local udid="$1"

  adb -s "$udid" shell settings put global captive_portal_detection_enabled 0 || true
  adb -s "$udid" shell settings put global private_dns_mode off || true

  adb -s "$udid" shell svc wifi disable || true
  sleep 1
  adb -s "$udid" shell svc wifi enable || true
}

enable_partner_setting() {
  local udid="$1"

  adb -s "$udid" root >/dev/null 2>&1 || true

  adb -s "$udid" shell "content insert \
    --uri content://com.google.settings/partner \
    --bind name:s:network_location_opt_in \
    --bind value:i:1" >/dev/null 2>&1 || true
}

# --------------------------------------------------------
# FLOW
# --------------------------------------------------------

adb start-server >/dev/null 2>&1

UDID=$(find_udid_by_avd "$AVD_NAME")

if [ -z "$UDID" ]; then
  start_emulator_if_needed "$AVD_NAME"

  UDID=$(wait_until_udid_found "$AVD_NAME" "$BOOT_TIMEOUT") || {
    err "Emulator not detected"
    exit 1
  }
fi

wait_for_adb_fully_ready "$UDID"
apply_network_fixes "$UDID"
enable_partner_setting "$UDID"

MODEL=$(adb -s "$UDID" shell getprop ro.product.model | tr -d '\r')
ANDROID_VER=$(adb -s "$UDID" shell getprop ro.build.version.release | tr -d '\r')

ok "Ready: $MODEL (AVD=$AVD_NAME, UDID=$UDID, Android $ANDROID_VER)"