package org.halkKatilim.utility.helpers;

import org.halkKatilim.constant.Config;
import org.halkKatilim.enums.Platform;
import org.halkKatilim.deviceConfig.DeviceManagement;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

public final class DevicePoolManager {

    private static final Map<Platform, Queue<Map<String, String>>> POOLS =
            new EnumMap<>(Platform.class);

    static {
        Arrays.stream(Platform.values())
                .forEach(p -> POOLS.put(p, new ConcurrentLinkedQueue<>()));
    }

    private DevicePoolManager() {}


    public static void reloadPools(Platform platform, List<String> requestedTags) {
        reloadPool(platform, requestedTags);
        FrameworkLogger.info(String.format(
                "🔄 Device pool reloaded → %s=%d", platform, getPool(platform).size()
        ));
    }

    public static Queue<Map<String, String>> getPool(Platform platform) {
        Queue<Map<String, String>> pool = POOLS.get(platform);
        if (pool == null) {
            throw new IllegalStateException("Pool not initialized for " + platform);
        }
        return pool;
    }

    // === Internal ===
    private static void reloadPool(Platform platform, List<String> requestedTags) {
        Queue<Map<String, String>> pool = POOLS.get(platform);
        pool.clear();
        pool.addAll(filterStarted(DeviceManagement.getDevices(platform), requestedTags, platform));
        FrameworkLogger.debug(String.format(
                "🔄 Pool reloaded for %s → %d active devices", platform, pool.size()
        ));
    }

    private static List<Map<String, String>> filterStarted(List<Map<String, String>> devices, List<String> requestedTags, Platform platform) {
        if (devices == null || devices.isEmpty()) {
            FrameworkLogger.error("❌ Device listesi boş → cihaz bulunamadı");
            return Collections.emptyList();
        }
        if (requestedTags == null || requestedTags.isEmpty()) {
            throw new IllegalArgumentException("❌ requestedTags boş olamaz!");
        }

        List<Map<String, String>> candidates;

        if (requestedTags.size() > devices.size()) {
            Map<String, Map<String, String>> deviceIndex = devices.stream()
                    .filter(Objects::nonNull)
                    .filter(d -> d.get("tag") != null)
                    .collect(Collectors.toMap(
                            d -> d.get("tag").toLowerCase(Locale.ROOT),
                            d -> d,
                            (d1, d2) -> d1
                    ));

            candidates = requestedTags.stream()
                    .map(tag -> deviceIndex.get(tag.toLowerCase(Locale.ROOT)))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

        } else {
            Set<String> tagSet = requestedTags.stream()
                    .filter(Objects::nonNull)
                    .map(t -> t.toLowerCase(Locale.ROOT))
                    .collect(Collectors.toSet());

            candidates = devices.stream()
                    .filter(Objects::nonNull)
                    .filter(d -> {
                        String tag = d.get("tag");
                        return tag != null && tagSet.contains(tag.toLowerCase(Locale.ROOT));
                    })
                    .collect(Collectors.toList());
        }

        List<Map<String, String>> filtered = candidates.stream()
                .map(d -> enrichDevice(d, platform))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        if (filtered.isEmpty()) {
            FrameworkLogger.warn("⚠️ Hiçbir cihaz requestedTags ile eşleşmedi → " + requestedTags);
        }
        return filtered;
    }

    // === MECBURİ FIX: platform ThreadLocal yerine parametre ===
    private static Map<String, String> enrichDevice(
            Map<String, String> device,
            Platform platform
    ) {
        String tag = device.get("tag");
        String udid = device.get("appium:udid");

        if (platform == Platform.ANDROID && (udid == null || udid.isEmpty())) {
            String runtimeUdid = Config.resolveAndroidUdidForTag(tag);
            if (runtimeUdid == null || runtimeUdid.isEmpty()) {
                FrameworkLogger.error("❌ Runtime UDID çözülemedi → tag=" + tag);
                return null;
            }
            FrameworkLogger.info("ℹ️ Injected runtime UDID → tag=" + tag + " → " + runtimeUdid);
            device.put("appium:udid", runtimeUdid);
            udid = runtimeUdid;
        }

        Integer port = Optional.ofNullable(udid)
                .filter(s -> !s.isEmpty())
                .map(Config::getAppiumPortForUdid)
                .orElse(null);

        if (port == null) {
            FrameworkLogger.warn("⚠️ Skipped device → tag=" + tag
                    + (udid == null || udid.isEmpty()
                    ? " (UDID boş, platform=" + platform + ")"
                    : " (Appium port yok, udid=" + udid + ")"));
            return null;
        }

        device.put("appium:appiumServerPort", String.valueOf(port));
        return device;
    }
}