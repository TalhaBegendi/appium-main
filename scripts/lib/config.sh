#!/bin/bash

BASE_DIR="${BASE_DIR:-$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)}"
PROP_FILE="$BASE_DIR/../env/default/appium.properties"
EMULATOR_SCRIPT="$BASE_DIR/start-emulator.sh"

RUNTIME_DIR="$BASE_DIR/../target/runtime"
mkdir -p "$RUNTIME_DIR"

PORT_MAP_FILE="$RUNTIME_DIR/appium_ports.properties"
HUB_PID_FILE="$RUNTIME_DIR/hub.pid"
NODE_PIDS_FILE="$RUNTIME_DIR/node.pids"
APPIUM_PIDS_FILE="$RUNTIME_DIR/appium.pids"
WDA_COUNTER_FILE="$RUNTIME_DIR/wda.counter"

[ -f "$PROP_FILE" ] || { err "Property file not found: $PROP_FILE"; exit 1; }

read_prop() {
  local key="$1"
  local default_value="${2:-}"
  local value

  value=$(awk -F= -v k="$key" '$1 == k {print substr($0, index($0, "=") + 1); exit}' "$PROP_FILE")

  if [ -n "$value" ]; then
    echo "$value"
    return 0
  fi

  if [ -n "$default_value" ]; then
    echo "$default_value"
    return 0
  fi

  err "Missing property: $key"
  exit 1
}

normalize_bool() {
  echo "$1" | tr '[:upper:]' '[:lower:]'
}

parse_host_from_url() {
  local url="$1"
  echo "$url" | sed -E 's#^[a-zA-Z]+://([^:/]+).*#\1#'
}

parse_port_from_url() {
  local url="$1"
  local port
  port=$(echo "$url" | sed -nE 's#.*:([0-9]+).*#\1#p')
  [ -n "$port" ] || { err "Cannot parse port from URL: $url"; exit 1; }
  echo "$port"
}

load_config() {
  GRID_MODE="$(normalize_bool "$(read_prop "GRID")")"
  GRID_HUB_URL="$(read_prop "GRID_HUB_URL")"
  LOCAL_HUB_URL="$(read_prop "LOCAL_HUB_URL")"

  GRID_START_NODE_PORT="$(read_prop "GRID_START_NODE_PORT" "$GRID_START_NODE_PORT")"
  MAX_SESSIONS="$(read_prop "GRID_MAX_SESSIONS")"
  MAX_TOTAL_DEVICES="$(read_prop "GRID_MAX_TOTAL_DEVICES")"
  APPIUM_BASE_PORT="$(read_prop "APPIUM_BASE_PORT" "$APPIUM_BASE_PORT")"
  LOCAL_BASE_PATH="$(read_prop "LOCAL_BASE_PATH" "/wd/hub")"
  GRID_BASE_PATH="$(read_prop "GRID_BASE_PATH" "/wd/hub")"
  WDA_BASE_PORT="$(read_prop "WDA_BASE_PORT" "$WDA_BASE_PORT")"
  WDA_COUNTER_MAX="$(read_prop "WDA_COUNTER_MAX" "$WDA_COUNTER_MAX")"
  ALLOW_DYNAMIC_PORT_FALLBACK="$(normalize_bool "$(read_prop "ALLOW_DYNAMIC_PORT_FALLBACK" "true")")"

  GRID_HUB_HOST="$(parse_host_from_url "$GRID_HUB_URL")"
  GRID_HUB_PORT="$(parse_port_from_url "$GRID_HUB_URL")"
  LOCAL_HOST="$(parse_host_from_url "$LOCAL_HUB_URL")"

  SELENIUM_JAR="$BASE_DIR/../src/test/resources/apps/appiumGrid/selenium-server-4.23.0.jar"
  GRID_DIR="$BASE_DIR/../src/test/resources/deviceConfig"
  DEVICE_FILE=$([ "$IS_ANDROID" = "true" ] && echo "$GRID_DIR/android.json" || echo "$GRID_DIR/ios.json")

  [ -f "$DEVICE_FILE" ] || { err "Device file not found: $DEVICE_FILE"; exit 1; }
  [ -f "$SELENIUM_JAR" ] || { err "Selenium jar not found: $SELENIUM_JAR"; exit 1; }
}

init_runtime_files() {
  touch "$PORT_MAP_FILE"
  : > "$NODE_PIDS_FILE"
  : > "$APPIUM_PIDS_FILE"

  if [ ! -f "$WDA_COUNTER_FILE" ]; then
    echo 0 > "$WDA_COUNTER_FILE"
  fi
}