package org.halkKatilim.deviceConfig;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.halkKatilim.constant.Config;
import org.halkKatilim.enums.Platform;
import org.halkKatilim.utility.helpers.FrameworkLogger;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public final class DeviceManagement {

    private DeviceManagement() {}

    private static final EnumMap<Platform, AtomicBoolean> USED =
            new EnumMap<>(Platform.class);

    static {
        for (Platform p : Platform.values()) {
            USED.put(p, new AtomicBoolean(false));
        }
    }

    public static void markUsed(Platform platform) {
        USED.get(platform).set(true);
    }

    public static boolean isUsed(Platform platform) {
        return USED.get(platform).get();
    }


    private static final Path BASE_PATH = Path.of("src", "test", "resources", "deviceConfig");
    private static final Map<Platform, Path> PATHS = Map.of(
            Platform.IOS, BASE_PATH.resolve("ios.json"),
            Platform.ANDROID, BASE_PATH.resolve("android.json")
    );

    private static final ObjectMapper MAPPER = new ObjectMapper()
            .findAndRegisterModules()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public static List<Map<String, String>> getDevices(Platform platform) {
        Path path = PATHS.get(platform);
        if (path == null) {
            FrameworkLogger.error("❌ No JSON path for platform=" + platform);
            return List.of();
        }
        return readDevices(path);
    }

    private static List<Map<String, String>> readDevices(Path path) {
        try {
            FrameworkLogger.debug("Reading device config: " + path);
            List<Map<String, String>> devices = MAPPER.readValue(path.toFile(), new TypeReference<>() {});
            FrameworkLogger.debug("Found " + devices.size() + " devices in " + path);
            return devices.stream()
                    .map(DeviceManagement::mergeWithDefaults)
                    .toList();
        } catch (IOException e) {
            FrameworkLogger.error("❌ Failed to read device file: " + path + " -> " + e.getMessage());
            return List.of();
        }
    }

    private static Map<String, String> mergeWithDefaults(Map<String, String> device) {
        Map<String, String> merged = new LinkedHashMap<>(Config.getDefaultCapsForPlatform());
        merged.putAll(device);
        FrameworkLogger.trace(() -> "[DeviceConfig] Final merged device map: " + merged);
        return merged;
    }
}