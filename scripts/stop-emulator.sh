#!/bin/bash
# ==============================
# Android Emulator Stopper (FINAL, safe, idempotent)
# ==============================

set -u
echo "🔍 Checking for running Android emulators..."
RUNNING=$(adb devices | awk '/^emulator-/ {print $1}')
if [ -z "$RUNNING" ]; then
    echo "ℹ️ No emulators are currently running."
    exit 0
fi

for EMU in $RUNNING; do
    echo "🛑 Stopping emulator: $EMU"
    adb -s "$EMU" emu kill >/dev/null 2>&1 || true
    START=$(date +%s)
    while adb devices | awk '{print $1}' | grep -q "^$EMU$"; do
        NOW=$(date +%s)
        ELAPSED=$((NOW - START))
        if [ "$ELAPSED" -ge 20 ]; then
            echo "   ⚠️ Timeout waiting for $EMU to exit adb list"
            break
        fi
        sleep 1
    done

    if ! adb devices | awk '{print $1}' | grep -q "^$EMU$"; then
        echo "   ✅ Emulator $EMU removed from adb"
    fi
done

if pgrep -f "emulator" >/dev/null; then
    echo "🧹 Force killing remaining emulator processes..."
    pkill -9 -f "emulator" || true
    echo "   ✅ All emulator processes killed"
fi

adb kill-server >/dev/null 2>&1 || true

echo "✅ Emulator cleanup complete."
exit 0