#!/bin/bash
# ==============================
# Appium Unified Stopper (FINAL / FIXED)
# Works for both GRID and LOCAL modes
# ==============================

set -uo pipefail

BASE_DIR="$(cd "$(dirname "$0")" && pwd)"
PROP_FILE="$BASE_DIR/../env/default/appium.properties"
RUNTIME_DIR="$BASE_DIR/../target/runtime"

GRID_MODE=$(grep "^GRID=" "$PROP_FILE" 2>/dev/null | cut -d'=' -f2 | tr '[:upper:]' '[:lower:]')

HUB_PID_FILE="$RUNTIME_DIR/hub.pid"
NODE_PIDS_FILE="$RUNTIME_DIR/node.pids"
APPIUM_PIDS_FILE="$RUNTIME_DIR/appium.pids"
PORT_MAP_FILE="$RUNTIME_DIR/appium_ports.properties"

# ------------------------------
# Kill from PID file (safe)
# ------------------------------
stop_pid_file() {
  local FILE="$1"
  local NAME="$2"
  local COUNT=0

  [ ! -f "$FILE" ] && { echo "ℹ️ $NAME PID file not found"; return 0; }

  sort -u "$FILE" -o "$FILE"

  while IFS= read -r PID; do
    [[ -z "$PID" ]] && continue
    if kill -0 "$PID" 2>/dev/null; then
      echo "🛑 Stopping $NAME (PID=$PID)"
      kill "$PID" 2>/dev/null || true
      sleep 1
      if kill -0 "$PID" 2>/dev/null; then
        echo "⚠️ $NAME (PID=$PID) still alive, force killing..."
        kill -9 "$PID" 2>/dev/null || true
      fi
      COUNT=$((COUNT+1))
    fi
  done < "$FILE"

  rm -f "$FILE"
  echo "✅ $NAME: $COUNT process(es) terminated"
}

# ------------------------------
# Kill by pattern (fallback safe)
# ------------------------------
stop_by_pattern() {
  local PATTERN="$1"
  local NAME="$2"
  local COUNT=0

  pgrep -f "$PATTERN" 2>/dev/null | while read -r PID; do
    if kill -0 "$PID" 2>/dev/null; then
      echo "🛑 Killing stray $NAME (PID=$PID)"
      kill -9 "$PID" 2>/dev/null || true
      COUNT=$((COUNT+1))
    fi
  done

  [ "$COUNT" -gt 0 ] && echo "✅ $NAME: $COUNT stray process(es) killed" \
                      || echo "ℹ️ No stray $NAME processes found"
}

# ==============================
# Local Cleanup
# ==============================
stop_local() {
  echo "🛑 Stopping Appium (LOCAL mode)..."

  stop_pid_file "$APPIUM_PIDS_FILE" "Appium Server"

  [ -f "$PORT_MAP_FILE" ] && rm -f "$PORT_MAP_FILE" && echo "🧹 Port map cleared"

  # iOS leftovers
  pgrep -f "WebDriverAgentRunner|xctest" 2>/dev/null | while read -r PID; do
    echo "🛑 Killing leftover WDA/XCTest PID=$PID"
    kill -9 "$PID" 2>/dev/null || true
  done

  # Android leftovers
  pgrep -f "emulator" 2>/dev/null | while read -r PID; do
    echo "🛑 Killing emulator PID=$PID"
    kill -9 "$PID" 2>/dev/null || true
  done

  adb kill-server >/dev/null 2>&1 || true

  echo "🎉 Local cleanup finished."
}

# ==============================
# Grid Cleanup
# ==============================
stop_grid() {
  echo "🛑 Stopping Selenium Grid + Appium..."

  stop_pid_file "$HUB_PID_FILE" "Selenium Grid Hub"
  stop_pid_file "$NODE_PIDS_FILE" "Selenium Grid Node"
  stop_pid_file "$APPIUM_PIDS_FILE" "Appium Server"

  stop_by_pattern "appium --" "Appium"
  stop_by_pattern "selenium-server" "Selenium"

  rm -f "$RUNTIME_DIR/grid.lock" "$RUNTIME_DIR/cleanup.counter" 2>/dev/null || true

  adb kill-server >/dev/null 2>&1 || true

  echo "🎉 Grid cleanup finished."
}

# ==============================
# Dispatcher
# ==============================
if [ "$GRID_MODE" = "true" ]; then
  stop_grid
else
  stop_local
fi

# ==============================
# Final cleanup
# ==============================
rm -f "$RUNTIME_DIR"/*.pids \
      "$RUNTIME_DIR"/hub.pid \
      "$RUNTIME_DIR"/appium_ports.properties 2>/dev/null || true

echo "✅ Stop-All completed successfully."
exit 0