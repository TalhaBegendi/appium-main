#!/bin/bash
# ==============================
# iOS Simulator Stopper (JSON-safe, optimized, idempotent)
# ==============================

TARGET="${1:-all}"   # Parametre: "all" ya da tek UDID
DEVICES_JSON=$(xcrun simctl list devices --json)

echo "🛑 iOS simülatör kapatma işlemi başlatıldı... (target=$TARGET)"

shutdown_device() {
    local UDID=$1
    local NAME=$2
    local STATE=$3

    if [ "$STATE" == "Shutdown" ]; then
        echo "ℹ️ $NAME ($UDID) zaten kapalı, skip ediliyor."
        return
    fi

    echo "🛑 Kapatılıyor: $NAME ($UDID)"
    xcrun simctl shutdown "$UDID" || echo "⚠️ $UDID shutdown başarısız olabilir."
}

if [ "$TARGET" == "all" ]; then
    # Sadece Booted cihazları bul
    BOOTED_UDIDS=$(echo "$DEVICES_JSON" | jq -r '.devices[][] | select(.state=="Booted") | .udid')

    if [ -z "$BOOTED_UDIDS" ]; then
        echo "ℹ️ Açık simülatör bulunamadı."
    else
        for UDID in $BOOTED_UDIDS; do
            NAME=$(echo "$DEVICES_JSON" | jq -r ".devices[][] | select(.udid==\"$UDID\") | .name")
            STATE=$(echo "$DEVICES_JSON" | jq -r ".devices[][] | select(.udid==\"$UDID\") | .state")
            shutdown_device "$UDID" "$NAME" "$STATE"
        done
        echo "✅ Booted simülatör(ler) için kapatma denemesi bitti."
    fi
else
    # Tek UDID kapatma
    NAME=$(echo "$DEVICES_JSON" | jq -r ".devices[][] | select(.udid==\"$TARGET\") | .name")
    STATE=$(echo "$DEVICES_JSON" | jq -r ".devices[][] | select(.udid==\"$TARGET\") | .state")
    if [ -z "$NAME" ] || [ "$NAME" == "null" ]; then
        echo "⚠️ UDID $TARGET için cihaz bulunamadı!"
    else
        shutdown_device "$TARGET" "$NAME" "$STATE"
        echo "✅ Simülatör kapatma denemesi bitti."
    fi
fi

# Simulator.app açık ise önce graceful quit, sonra killall
if pgrep -q Simulator; then
    echo "🛑 Simulator uygulaması sonlandırılıyor..."
    osascript -e 'quit app "Simulator"' 2>/dev/null || true
    sleep 1
    pgrep -q Simulator && killall Simulator
fi


# CoreSimulatorService hard reset (network / permission bug fix)
if pgrep -f CoreSimulatorService >/dev/null; then
    echo "🧹 Resetting CoreSimulatorService..."
    killall -9 com.apple.CoreSimulator.CoreSimulatorService 2>/dev/null || true
fi

echo "🎉 Simulator cleanup finished."
exit 0