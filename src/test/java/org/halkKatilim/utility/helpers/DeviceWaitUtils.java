package org.halkKatilim.utility.helpers;

import org.halkKatilim.constant.Config;
import org.halkKatilim.enums.Platform;
import org.halkKatilim.utility.Driver;
import org.openqa.selenium.support.ui.FluentWait;
import java.time.Duration;
import java.util.*;
import static org.halkKatilim.constant.Config.reloadAppiumPortMap;
import static org.halkKatilim.utility.helpers.DevicePoolManager.getPool;

public final class DeviceWaitUtils {

    private DeviceWaitUtils() {}

    public static boolean hasPortFor(String deviceTag) {
        Config.reloadAppiumPortMap();
        String normalizedTag = deviceTag.toLowerCase();
        Map<String, String> entries = Config.getAppiumPortEntries();
        return entries.containsKey(normalizedTag);
    }

    public static void waitForPortMap(String deviceTag) {
        new FluentWait<>(deviceTag)
                .withTimeout(Duration.ofSeconds(20))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(Exception.class)
                .until(DeviceWaitUtils::isPortMapReady);
        FrameworkLogger.info("✅ Port map hazır: " + deviceTag);
    }

    private static boolean isPortMapReady(String deviceTag) {
        reloadAppiumPortMap();
        Platform platform = Driver.getPlatformForThread();
        DevicePoolManager.reloadPools(platform, Collections.singletonList(deviceTag));
        return getPool(platform).stream()
                .filter(Objects::nonNull)
                .filter(d -> deviceTag.equalsIgnoreCase(d.getOrDefault("tag", "")))
                .findFirst()
                .map(d -> Optional.ofNullable(ensureUdid(d, deviceTag, platform))
                        .map(Config::getAppiumPortForUdid)
                        .isPresent())
                .orElseGet(() -> {
                    FrameworkLogger.warn("⚠️ UDID bulunamadı (tag=" + deviceTag + ")");
                    return false;
                });
    }

    private static String ensureUdid(Map<String, String> device, String deviceTag, Platform platform) {
        String udid = device.get("appium:udid");
        if (udid != null && !udid.isEmpty()) {
            return udid;
        }
        if (platform != Platform.ANDROID) {
            return null;
        }
        String runtimeUdid = Config.resolveAndroidUdidForTag(deviceTag);
        System.out.println("Test_4");
        if (runtimeUdid == null || runtimeUdid.isEmpty()) {
            FrameworkLogger.error("❌ UDID boş/çözülemedi → tag=" + deviceTag);
            return null;
        }
        FrameworkLogger.info("ℹ️ Injected runtime UDID → tag=" + deviceTag + " → " + runtimeUdid);
        device.put("appium:udid", runtimeUdid);
        return runtimeUdid;
    }
}