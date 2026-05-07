#!/bin/bash
set -euo pipefail

# =========================================================
# ⚙️ DEFAULTS & PORT OFFSETS
# =========================================================
export ANDROID_PORT_OFFSET="${ANDROID_PORT_OFFSET:-1000}"
export IOS_PORT_OFFSET="${IOS_PORT_OFFSET:-2000}"
export APPIUM_BASE_PORT="${APPIUM_BASE_PORT:-4725}"
export GRID_START_NODE_PORT="${GRID_START_NODE_PORT:-1488}"
export WDA_BASE_PORT="${WDA_BASE_PORT:-8100}"
export WDA_COUNTER_MAX="${WDA_COUNTER_MAX:-2000}"
# =========================================================

BASE_DIR="$(cd "$(dirname "$0")" && pwd)"

source "$BASE_DIR/lib/common.sh"
source "$BASE_DIR/lib/config.sh"
source "$BASE_DIR/lib/device.sh"
source "$BASE_DIR/lib/appium.sh"
source "$BASE_DIR/lib/local.sh"
source "$BASE_DIR/lib/grid.sh"

main() {
  parse_args "$@"
  check_dependencies
  load_config
  init_runtime_files

  if [ "$GRID_MODE" = "true" ]; then
    start_grid
  else
    start_local
  fi
}

main "$@"