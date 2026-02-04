package org.halkKatilim.utility.helpers;

import org.halkKatilim.enums.Platform;
import static org.halkKatilim.constant.Config.*;
import static org.halkKatilim.utility.Driver.*;
import static org.halkKatilim.utility.terminal.Terminal.runShellScriptWithParameter;

public final class DeviceManager {

    private DeviceManager() {}

    public static void prepare(String device, String platform) {

        if (device == null || platform == null) {
            throw new IllegalArgumentException(
                    "[DEVICE] device veya platform null olamaz"
            );
        }

        Platform resolvedPlatform =
                "android".equalsIgnoreCase(platform)
                        ? Platform.ANDROID
                        : Platform.IOS;

        setPlatformForThread(resolvedPlatform);

        FrameworkLogger.info(String.format(
                "[DEVICE] %s modda device='%s' platform='%s' için cihaz hazırlanıyor...",
                GRID ? "Grid" : "Local",
                device,
                platform
        ));

        boolean portExists = DeviceWaitUtils.hasPortFor(device);
        boolean shouldStart = PARALLEL || !portExists;
        FrameworkLogger.info(
                PARALLEL
                        ? "🚀 PARALLEL mod → startDevice çalışıyor."
                        : shouldStart
                        ? "🚀 SERIAL mod → port yok, startDevice çalışıyor."
                        : "⏭️ SERIAL mod → port hazır, startDevice atlanıyor."
        );
        if (shouldStart) {
            startDevice(device, platform);
        }
        DeviceWaitUtils.waitForPortMap(device);
    }


    private static void startDevice(String device, String platform) {

        String platformParam =
                "android".equalsIgnoreCase(platform) ? "true" : "false";

        if (!PARALLEL) {
            FileUtil.ensureMarker(SERIAL_MARKER_ALL);
        }

        String script = "scripts/start-all.sh";
        runShellScriptWithParameter(
                script,
                new String[]{platformParam, device}
        );
    }

    private static class FileUtil {
        static void ensureMarker(java.io.File markerFile) {
            if (!markerFile.exists()) {
                markerFile.getParentFile().mkdirs();
                RuntimeFileUtils.writeText(markerFile, "started");
            }
        }
    }
}