#!/bin/bash
# ==============================
# Appium Unified Starter (final clean + multi-device safe)
# ==============================
set -euo pipefail

IS_ANDROID=$1
REQUESTED_TAGS=$2

case "$IS_ANDROID" in
  true)  PLATFORM_PORT_OFFSET=1000 ;;
  false) PLATFORM_PORT_OFFSET=2000 ;;
  *) echo "❌ IS_ANDROID must be true or false"; exit 1 ;;
esac

BASE_DIR="$(cd "$(dirname "$0")" && pwd)"
PROP_FILE="$BASE_DIR/../env/default/appium.properties"
EMULATOR_SCRIPT="$BASE_DIR/start-emulator.sh"

GRID_MODE=$(grep "^GRID=" "$PROP_FILE" | cut -d'=' -f2 | tr '[:upper:]' '[:lower:]')
GRID_HUB_URL=$(grep "^GRID_HUB_URL" "$PROP_FILE" | cut -d'=' -f2)
LOCAL_HUB_URL=$(grep "^LOCAL_HUB_URL" "$PROP_FILE" | cut -d'=' -f2)

GRID_START_NODE_PORT=1488
MAX_SESSIONS=$(grep "^GRID_MAX_SESSIONS" "$PROP_FILE" | cut -d'=' -f2)
MAX_TOTAL_DEVICES=$(grep "^GRID_MAX_TOTAL_DEVICES" "$PROP_FILE" | cut -d'=' -f2)
APPIUM_BASE_PORT=4725

GRID_HUB_HOST=$(echo "$GRID_HUB_URL" | sed -E 's,https?://([^:/]+).*,\1,')
GRID_HUB_PORT=$(echo "$GRID_HUB_URL" | sed -E 's,.*:([0-9]+).*,\1,')

LOCAL_HOST=$(echo "$LOCAL_HUB_URL" | sed -E 's,^[^/]*//([^:/]*).*,\1,')
LOCAL_PATH="/wd/hub"

SELENIUM_JAR="$BASE_DIR/../src/test/resources/apps/appiumGrid/selenium-server-4.23.0.jar"
GRID_DIR="$BASE_DIR/../src/test/resources/deviceConfig"
DEVICE_FILE=$([ "$IS_ANDROID" = "true" ] && echo "$GRID_DIR/android.json" || echo "$GRID_DIR/ios.json")

RUNTIME_DIR="$BASE_DIR/../target/runtime"
mkdir -p "$RUNTIME_DIR"

PORT_MAP_FILE="$RUNTIME_DIR/appium_ports.properties"
HUB_PID_FILE="$RUNTIME_DIR/hub.pid"
NODE_PIDS_FILE="$RUNTIME_DIR/node.pids"
APPIUM_PIDS_FILE="$RUNTIME_DIR/appium.pids"
WDA_COUNTER_FILE="$RUNTIME_DIR/wda.counter"

touch "$PORT_MAP_FILE"
touch "$NODE_PIDS_FILE"
touch "$APPIUM_PIDS_FILE"

if [ ! -f "$WDA_COUNTER_FILE" ]; then echo 0 > "$WDA_COUNTER_FILE"; fi

# ------------------------------
# Dynamic wait (exponential backoff, clean log)
# ------------------------------
wait_for_port() {
  local HOST=$1
  local PORT=$2
  local NAME=$3
  local TIMEOUT=${4:-30}
  local attempt=0
  local elapsed=0

  while [ $elapsed -lt $TIMEOUT ]; do
    if nc -z "$HOST" "$PORT" 2>/dev/null; then
      echo "   ✅ $NAME ready on $PORT after ${elapsed}s"
      return 0
    fi
    local wait=$((2**attempt))
    [ $wait -gt 5 ] && wait=5
    sleep $wait
    elapsed=$((elapsed+wait))
    attempt=$((attempt+1))
  done
  echo "   ❌ $NAME not ready on $PORT within ${TIMEOUT}s"
  return 1
}

# ------------------------------
# Android helpers
# ------------------------------
adb_devices_safe() {
  adb devices 2>/dev/null || {
    sleep 1
    adb devices 2>/dev/null
  }
}

normalize() { echo "$1" | tr '[:upper:]' '[:lower:]' | tr -d '_ '; }

resolve_android_udid_by_avd() {
  local AVD_NAME="$1"
    for d in $(adb_devices_safe | awk 'NR>1 && $1 ~ /emulator-/ {print $1}'); do
    local avd=$(adb -s "$d" emu avd name 2>/dev/null | tr -d '\r\n' | sed 's/OK$//' | xargs)
    if [ "$(normalize "$avd")" = "$(normalize "$AVD_NAME")" ]; then echo "$d"; return; fi
  done
  echo ""
}

ensure_android_emulator() {
  local TAG="$1"
  bash "$EMULATOR_SCRIPT" "$TAG" || return 1
  return 0
}

# ==============================
# Local Mode
# ==============================
start_local() {
  echo "💻 LOCAL mode enabled → Appium direct start"
  DEVICE_COUNT=$(jq length "$DEVICE_FILE")
  echo "🔍 Found $DEVICE_COUNT base devices in $DEVICE_FILE"

  # 1) Emülatörleri sırayla aç
  if [ "$IS_ANDROID" = "true" ]; then
    for i in $(seq 0 $((DEVICE_COUNT-1))); do
      BASE_DEVICE=$(jq -c ".[$i]" "$DEVICE_FILE")
      TAG=$(echo "$BASE_DEVICE" | jq -r '.tag')
      DEVICE_NAME=$(echo "$BASE_DEVICE" | jq -r '."appium:deviceName"')

      if [ -n "$REQUESTED_TAGS" ] && ! grep -qw "$TAG" <<< "$REQUESTED_TAGS"; then continue; fi
      ensure_android_emulator "$TAG" || { echo "❌ Emulator failed for tag=$TAG"; exit 1; }
    done
  fi

  TOTAL_STARTED=0

  # 2) Appium başlat + Port map yaz
  for i in $(seq 0 $((DEVICE_COUNT-1))); do
    BASE_DEVICE=$(jq -c ".[$i]" "$DEVICE_FILE")
    TAG=$(echo "$BASE_DEVICE" | jq -r '.tag')
    DEVICE_NAME=$(echo "$BASE_DEVICE" | jq -r '."appium:deviceName"')
    JSON_UDID=$(echo "$BASE_DEVICE" | jq -r '."appium:udid"')

    if [ -n "$REQUESTED_TAGS" ] && ! grep -qw "$TAG" <<< "$REQUESTED_TAGS"; then
      echo "⏭️ Skipping $TAG (not requested)"; continue
    fi
    if [ $TOTAL_STARTED -ge $MAX_TOTAL_DEVICES ]; then echo "⚠️ Reached MAX_TOTAL_DEVICES=$MAX_TOTAL_DEVICES"; break; fi

    APP_PORT=$((APPIUM_BASE_PORT + i + PLATFORM_PORT_OFFSET))
    COUNTER=$(cat "$WDA_COUNTER_FILE"); COUNTER=$((COUNTER + 1)); [ "$COUNTER" -gt 2000 ] && COUNTER=1; echo $COUNTER > "$WDA_COUNTER_FILE"
    WDA_PORT=$((8100 + COUNTER)); BUNDLE="com.mycompany.WebDriverAgentRunner.${TAG}.${COUNTER}"

    if [ "$IS_ANDROID" = "true" ]; then
      UDID=$(resolve_android_udid_by_avd "$DEVICE_NAME")
      [ -z "$UDID" ] && { echo "❌ Cannot resolve UDID for $DEVICE_NAME"; continue; }
    else
      UDID="$JSON_UDID"; [ -z "$UDID" ] || [ "$UDID" = "null" ] && UDID="device_$i"
    fi

    echo "📱 Preparing Appium for tag=$TAG ($DEVICE_NAME, UDID=$UDID) → port=$APP_PORT | WDA=$WDA_PORT | BUNDLE=$BUNDLE"
    if ! nc -z "$LOCAL_HOST" "$APP_PORT"; then
      echo "🚀 Starting Appium on $APP_PORT ..."
      appium --address "$LOCAL_HOST" --port "$APP_PORT" --base-path "$LOCAL_PATH" \
        --relaxed-security --session-override \
        --default-capabilities "{\"appium:wdaLocalPort\":$WDA_PORT,\"appium:updatedWDABundleId\":\"$BUNDLE\"}" \
        > "$RUNTIME_DIR/appium_${UDID}.log" 2>&1 &
      echo "$!" >> "$APPIUM_PIDS_FILE"
      wait_for_port "$LOCAL_HOST" "$APP_PORT" "Appium" 20 || continue
    fi

    grep -v "^${TAG}:${UDID}=" "$PORT_MAP_FILE" > "$PORT_MAP_FILE.tmp" || true
    echo "${TAG}:${UDID}=${APP_PORT}" >> "$PORT_MAP_FILE.tmp"
    mv "$PORT_MAP_FILE.tmp" "$PORT_MAP_FILE"

    TOTAL_STARTED=$((TOTAL_STARTED+1))
  done

  echo "✅ Started $TOTAL_STARTED device(s) in LOCAL mode (WDA isolated)"
  echo "   - Port map: $PORT_MAP_FILE"
}

# ==============================
# Grid Mode
# ==============================
start_grid() {
  echo "🌐 GRID mode enabled → Selenium Grid + Appium"
  LOCK_FILE="$RUNTIME_DIR/grid.lock"
  LOCK_DIR="$LOCK_FILE.lockdir"
  DEVICE_COUNT=$(jq length "$DEVICE_FILE")
  # =======================================
  #  GRID HUB LOCK CONTROL (atomic + macOS safe)
  # =======================================
  if nc -z "$GRID_HUB_HOST" "$GRID_HUB_PORT" >/dev/null 2>&1; then
    echo "🌐 Hub port $GRID_HUB_PORT aktif, lock senkronize ediliyor."
    echo "started" > "$LOCK_FILE"
  else
    rm -f "$LOCK_FILE"
  fi
  if mkdir "$LOCK_DIR" 2>/dev/null; then
    if [ ! -f "$LOCK_FILE" ] || ! grep -q "started" "$LOCK_FILE"; then
      echo "🚀 Starting Selenium Grid Hub on $GRID_HUB_URL ..."
      java -jar "$SELENIUM_JAR" hub --port "$GRID_HUB_PORT" > "$RUNTIME_DIR/hub.log" 2>&1 &
      HUB_PID=$!
      echo "$HUB_PID" > "$HUB_PID_FILE"
      if wait_for_port "$GRID_HUB_HOST" "$GRID_HUB_PORT" "Hub" 30; then
        echo "started" > "$LOCK_FILE"
        echo "✅ Hub ready on $GRID_HUB_PORT"
      else
        echo "❌ Hub startup failed — cleaning lock & killing process"
        rm -f "$LOCK_FILE"
        kill -9 "$HUB_PID" 2>/dev/null || true
        rmdir "$LOCK_DIR" 2>/dev/null || true
        exit 1
      fi

    else
      echo "🌐 Grid Hub zaten aktif (detected via grid.lock)"
    fi
    rmdir "$LOCK_DIR"
  else
    echo "⏭️ Grid Hub zaten başka bir JVM tarafından çalıştırılıyor."
  fi

    if [ "$IS_ANDROID" = "true" ]; then
      for i in $(seq 0 $((DEVICE_COUNT-1))); do
        BASE=$(jq -c ".[$i]" "$DEVICE_FILE")
        TAG=$(echo "$BASE" | jq -r '.tag')
        if [ -n "$REQUESTED_TAGS" ] && ! echo "$REQUESTED_TAGS" | grep -qw "$TAG"; then continue; fi
        ensure_android_emulator "$TAG" || { echo "❌ Emulator failed for tag=$TAG"; exit 1; }
      done
    fi

  TOTAL_STARTED=0

  # 2) Appium + Node registration
  for i in $(seq 0 $((DEVICE_COUNT-1))); do
    BASE=$(jq -c ".[$i]" "$DEVICE_FILE")
    TAG=$(echo "$BASE" | jq -r '.tag')
    DEVICE_NAME=$(echo "$BASE" | jq -r '."appium:deviceName"')
    JSON_UDID=$(echo "$BASE" | jq -r '."appium:udid"')

    if [ -n "$REQUESTED_TAGS" ] && ! echo "$REQUESTED_TAGS" | grep -qw "$TAG"; then
      echo "⏭️ Skipping $TAG"
      continue
    fi

    if [ $TOTAL_STARTED -ge $MAX_TOTAL_DEVICES ]; then
      echo "⚠️ Reached MAX_TOTAL_DEVICES=$MAX_TOTAL_DEVICES"
      break
    fi

    APP_PORT=$((APPIUM_BASE_PORT + i + PLATFORM_PORT_OFFSET))
    NODE_PORT=$((GRID_START_NODE_PORT + i + PLATFORM_PORT_OFFSET))
    COUNTER=$(cat "$WDA_COUNTER_FILE")
    COUNTER=$((COUNTER + 1))
    [ "$COUNTER" -gt 2000 ] && COUNTER=1
    echo $COUNTER > "$WDA_COUNTER_FILE"
    WDA_PORT=$((8100 + COUNTER))
    BUNDLE="com.mycompany.WebDriverAgentRunner.${TAG}.${COUNTER}"

    if [ "$IS_ANDROID" = "true" ]; then
      UDID=$(resolve_android_udid_by_avd "$DEVICE_NAME")
      [ -z "$UDID" ] && { echo "❌ Cannot resolve UDID"; continue; }
      PLATFORM="Android"; AUTO="UiAutomator2"
    else
      UDID="$JSON_UDID"
      [ -z "$UDID" ] || [ "$UDID" = "null" ] && UDID="device_$i"
      PLATFORM="iOS"; AUTO="XCUITest"
    fi

    echo "📱 Preparing device tag=$TAG (UDID=$UDID) → port=$APP_PORT | WDA=$WDA_PORT | BUNDLE=$BUNDLE"
    if ! nc -z "$GRID_HUB_HOST" "$APP_PORT"; then
      echo "🚀 Starting Appium on $APP_PORT ..."
      appium --address "$GRID_HUB_HOST" --port "$APP_PORT" --base-path /wd/hub \
        --relaxed-security --session-override \
        > "$RUNTIME_DIR/appium_${UDID}.log" 2>&1 &
      echo "$!" >> "$APPIUM_PIDS_FILE"
      wait_for_port "$GRID_HUB_HOST" "$APP_PORT" "Appium" 30 || continue
    fi

grep -v "^${TAG}:${UDID}=" "$PORT_MAP_FILE" > "$PORT_MAP_FILE.tmp" || true
echo "${TAG}:${UDID}=${APP_PORT}" >> "$PORT_MAP_FILE.tmp"
mv "$PORT_MAP_FILE.tmp" "$PORT_MAP_FILE"

    STEREOTYPE=$(echo "$BASE" | jq 'del(.tag)' \
      | jq --arg wdaPort "$WDA_PORT" '. + {"appium:wdaLocalPort": ($wdaPort|tonumber)}' \
      | jq --arg bundle "$BUNDLE" '. + {"appium:updatedWDABundleId": $bundle}' \
      | jq --arg platformName "$PLATFORM" '. + {"platformName": $platformName}' \
      | jq --arg automationName "$AUTO" '. + {"appium:automationName": $automationName}' \
      | jq --arg ud "$UDID" '. + {"appium:udid": $ud}' )
    echo "🔗 Registering Node on $NODE_PORT with stereotype: $STEREOTYPE"
    java -jar "$SELENIUM_JAR" node --detect-drivers false --port "$NODE_PORT" \
      --service-url "http://$GRID_HUB_HOST:$APP_PORT/wd/hub" --service-host "$GRID_HUB_HOST" \
      --service-port "$APP_PORT" --service-status-endpoint /status \
      --service-configuration max-sessions="$MAX_SESSIONS" stereotype="$STEREOTYPE" \
      > "$RUNTIME_DIR/node_${UDID}.log" 2>&1 &
    echo "$!" >> "$NODE_PIDS_FILE"
    wait_for_port "$GRID_HUB_HOST" "$NODE_PORT" "Node" 30 || continue
    TOTAL_STARTED=$((TOTAL_STARTED+1))
  done

  echo "✅ Started $TOTAL_STARTED devices (limit: $MAX_TOTAL_DEVICES)"
  echo "   - Hub UI: $GRID_HUB_URL/ui"
  echo "   - Port map: $PORT_MAP_FILE"
}

# ==============================
# Dispatcher
# ==============================
if [ "$GRID_MODE" = "true" ]; then start_grid; else start_local; fi