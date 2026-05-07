#!/bin/bash
# =========================================================
# Android Emulator Stopper (Safe & Clean)
# =========================================================

set -euo pipefail

log()  { echo "ℹ️ $*"; }
ok()   { echo "✅ $*"; }
warn() { echo "⚠️ $*"; }

log "Checking for running Android emulators..."

RUNNING=$(adb devices | awk 'NR>1 && $1 ~ /^emulator-/ {print $1}')

if [ -z "$RUNNING" ]; then
    log "No emulators are currently running."
    exit 0
fi

for EMU in $RUNNING; do
    log "Stopping emulator: $EMU"

    adb -s "$EMU" emu kill >/dev/null 2>&1 || true

    START=$(date +%s)

    while adb devices | awk 'NR>1 {print $1}' | grep -q "^$EMU$"; do
        NOW=$(date +%s)
        ELAPSED=$((NOW - START))

        if [ "$ELAPSED" -ge 20 ]; then
            warn "Timeout waiting for $EMU to exit adb list"
            break
        fi

        sleep 1
    done

    if ! adb devices | awk 'NR>1 {print $1}' | grep -q "^$EMU$"; then
        ok "Emulator $EMU removed from adb"
    fi
done

# ---------------------------------------------------------
# Optional fallback (SAFE MODE)
# ---------------------------------------------------------
LEFTOVER=$(pgrep -f "emulator.*-avd" || true)

if [ -n "$LEFTOVER" ]; then
    warn "Some emulator processes still alive (not killed to avoid conflicts)"
    echo "$LEFTOVER"
fi

adb kill-server >/dev/null 2>&1 || true

ok "Emulator cleanup complete."
exit 0