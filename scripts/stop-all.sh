#!/bin/bash
# =========================================================
# Appium Unified Stopper (FINAL / STABLE / NO-FAIL)
# =========================================================

set -euo pipefail

BASE_DIR="$(cd "$(dirname "$0")" && pwd)"
PROP_FILE="$BASE_DIR/../env/default/appium.properties"
RUNTIME_DIR="$BASE_DIR/../target/runtime"

# ---------------------------------------------------------
# Config (safe)
# ---------------------------------------------------------
GRID_MODE=$(awk -F= '$1=="GRID"{print tolower($2)}' "$PROP_FILE")
GRID_MODE=${GRID_MODE:-false}

HUB_PID_FILE="$RUNTIME_DIR/hub.pid"
NODE_PIDS_FILE="$RUNTIME_DIR/node.pids"
APPIUM_PIDS_FILE="$RUNTIME_DIR/appium.pids"
PORT_MAP_FILE="$RUNTIME_DIR/appium_ports.properties"

# ---------------------------------------------------------
# Logging (minimal)
# ---------------------------------------------------------
log()  { echo "ℹ️ $*"; }
ok()   { echo "✅ $*"; }
warn() { echo "⚠️ $*"; }

# ---------------------------------------------------------
# Kill from PID file (safe)
# ---------------------------------------------------------
stop_pid_file() {
  local FILE="$1"
  local NAME="$2"
  local COUNT=0

  [ ! -f "$FILE" ] && return 0

  sort -u "$FILE" -o "$FILE"

  while read -r PID; do
    [ -z "$PID" ] && continue

    if kill -0 "$PID" 2>/dev/null; then
      kill "$PID" 2>/dev/null || true
      sleep 1

      if kill -0 "$PID" 2>/dev/null; then
        kill -9 "$PID" 2>/dev/null || true
      fi

      COUNT=$((COUNT+1))
    fi
  done < "$FILE"

  rm -f "$FILE"
  [ "$COUNT" -gt 0 ] && ok "$NAME stopped ($COUNT)"
}

# ---------------------------------------------------------
# Kill by pattern (safe)
# ---------------------------------------------------------
stop_by_pattern() {
  local PATTERN="$1"

  local PIDS
  PIDS=$(pgrep -f "$PATTERN" 2>/dev/null || true)

  for PID in $PIDS; do
    kill -9 "$PID" 2>/dev/null || true
  done
}

# ---------------------------------------------------------
# Local Cleanup
# ---------------------------------------------------------
kill_by_patterns() {
  local label="$1"
  shift
  local patterns=("$@")

  for pattern in "${patterns[@]}"; do
    PIDS=$(pgrep -f "$pattern" 2>/dev/null || true)

    while read -r PID; do
      [ -z "$PID" ] && continue
      warn "Killing $label PID=$PID"
      kill -9 "$PID" 2>/dev/null || true
    done <<< "$PIDS"
  done
}

stop_local() {
  log "Stopping LOCAL..."

  stop_pid_file "$APPIUM_PIDS_FILE" "Appium"

  kill_by_patterns "Appium" "appium" "node.*appium"
  kill_by_patterns "iOS" "WebDriverAgentRunner" "xctest"
  kill_by_patterns "Emulator" "emulator.*-avd"

  rm -f "$PORT_MAP_FILE" 2>/dev/null || true

  adb kill-server >/dev/null 2>&1 || true

  ok "Local cleanup done"
}

stop_grid() {
  log "Stopping GRID..."

  stop_pid_file "$HUB_PID_FILE" "Hub"
  stop_pid_file "$NODE_PIDS_FILE" "Node"
  stop_pid_file "$APPIUM_PIDS_FILE" "Appium"

  kill_by_patterns "Appium" "appium" "node.*appium"
  kill_by_patterns "Selenium" "selenium-server"

  rm -f "$RUNTIME_DIR/grid.lock" 2>/dev/null || true

  adb kill-server >/dev/null 2>&1 || true

  ok "Grid cleanup done"
}

# ---------------------------------------------------------
# Dispatcher
# ---------------------------------------------------------
if [ "$GRID_MODE" = "true" ]; then
  stop_grid
else
  stop_local
fi

# ---------------------------------------------------------
# Final cleanup (NEVER FAIL)
# ---------------------------------------------------------
rm -f "$RUNTIME_DIR"/*.pids \
      "$RUNTIME_DIR"/hub.pid \
      "$RUNTIME_DIR"/appium_ports.properties 2>/dev/null || true

ok "Stop-All completed"
exit 0