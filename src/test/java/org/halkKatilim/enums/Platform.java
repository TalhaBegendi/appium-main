package org.halkKatilim.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.halkKatilim.constant.Config;
import org.halkKatilim.deviceConfig.DeviceManagement;
import org.halkKatilim.utility.helpers.FrameworkLogger;

import static org.halkKatilim.utility.terminal.Terminal.runShellScript;

@Getter
@RequiredArgsConstructor
public enum Platform {


    ANDROID("scripts/stop-emulator.sh"),
    IOS("scripts/stop-simulator.sh");

    private final String stopScript;

    public void markUsed() {
        DeviceManagement.markUsed(this);
    }

    public boolean isUsed() {
        return DeviceManagement.isUsed(this);
    }

    public void stopIfUsed() {
        if (isUsed()) {
            FrameworkLogger.info("🛑 Stopping " + name());
            runShellScript(stopScript);
        }
    }
}
