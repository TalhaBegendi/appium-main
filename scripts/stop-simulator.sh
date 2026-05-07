#!/bin/bash
# =========================================================
# iOS Simulator Stopper (FINAL / SAFE / NO FALSE FAIL)
# =========================================================

set -euo pipefail

TARGET="${1:-all}"

log()  { echo "ℹ️ $*"; }
ok()   { echo "✅ $*"; }
warn() { echo "⚠️ $*"; }

log "Stopping iOS simulators... (target=$TARGET)"

DEVICES_JSON=$(xcrun simctl list devices --json)

shutdown_device() {
    local udid="$1"
    local name="$2"

    log "Shutting down: $name ($udid)"
    xcrun simctl shutdown "$udid" >/dev/null 2>&1 || warn "$udid shutdown may have failed"
}

# ---------------------------------------------------------
# ALL mode
# ---------------------------------------------------------
if [[ "$TARGET" == "all" ]]; then

    BOOTED=$(echo "$DEVICES_JSON" | jq -r '
        .devices[][] | select(.state=="Booted") | "\(.udid)|\(.name)"
    ')

    if [ -z "$BOOTED" ]; then
        log "No booted simulators found"
    else
        while IFS="|" read -r UDID NAME; do
            shutdown_device "$UDID" "$NAME"
        done <<< "$BOOTED"

        ok "Booted simulators shutdown completed"
    fi

# ---------------------------------------------------------
# SINGLE UDID mode
# ---------------------------------------------------------
else

    DEVICE=$(echo "$DEVICES_JSON" | jq -r --arg ID "$TARGET" '
        .devices[][] | select(.udid==$ID) | "\(.udid)|\(.name)|\(.state)"
    ')

    if [ -z "$DEVICE" ]; then
        warn "Device not found: $TARGET"
    else
        IFS="|" read -r UDID NAME STATE <<< "$DEVICE"

        if [[ "$STATE" == "Shutdown" ]]; then
            log "$NAME ($UDID) already shutdown"
        else
            shutdown_device "$UDID" "$NAME"
        fi

        ok "Simulator shutdown completed"
    fi
fi

# ---------------------------------------------------------
# Simulator App cleanup (SAFE)
# ---------------------------------------------------------
if pgrep -q Simulator >/dev/null 2>&1; then
    log "Closing Simulator app..."

    osascript -e 'quit app "Simulator"' >/dev/null 2>&1 || true
    sleep 1

    if pgrep -q Simulator >/dev/null 2>&1; then
        killall Simulator >/dev/null 2>&1 || true
    fi
fi

# ---------------------------------------------------------
# Optional CoreSimulator reset (SAFE)
# ---------------------------------------------------------
if [[ "${FORCE_RESET:-false}" == "true" ]]; then
    if pgrep -f CoreSimulatorService >/dev/null 2>&1; then
        warn "Force resetting CoreSimulatorService..."
        killall -9 com.apple.CoreSimulator.CoreSimulatorService >/dev/null 2>&1 || true
    fi
fi

ok "Simulator cleanup finished"
exit 0