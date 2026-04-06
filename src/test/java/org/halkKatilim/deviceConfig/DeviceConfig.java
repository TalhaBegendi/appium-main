package org.halkKatilim.deviceConfig;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public final class DeviceConfig {

    private final String defaultDevice;
    private final Map<String, List<String>> groups;
    private final Map<String, String> deviceToPlatform;
    private final Map<String, Boolean> deviceToIsRealStatus;

    @JsonCreator
    public DeviceConfig(
            @JsonProperty("defaultDevice") String defaultDevice,
            @JsonProperty("platforms") Map<String, Map<String, List<String>>> platforms,
            @JsonProperty("groups") Map<String, List<String>> groups) {

        this.defaultDevice = Objects.requireNonNull(defaultDevice, "defaultDevice required");

        // Normalize platforms
        Map<String, Map<String, List<String>>> normalizedPlatforms = platforms == null ? Map.of() : platforms;

        this.groups = toDeepUnmodifiable(groups);
        this.deviceToPlatform = buildDeviceToPlatformIndex(normalizedPlatforms);
        this.deviceToIsRealStatus = buildDeviceToIsRealStatus(normalizedPlatforms);
        validateDefaultDevice();
    }

    private static Map<String, List<String>> toDeepUnmodifiable(Map<String, List<String>> source) {
        if (source == null || source.isEmpty()) {
            return Map.of();
        }

        return source.entrySet()
                .stream()
                .collect(Collectors.toUnmodifiableMap(
                        Map.Entry::getKey,
                        e -> List.copyOf(
                                Objects.requireNonNullElse(
                                        e.getValue(),
                                        List.of()))));
    }

    private static Map<String, String> buildDeviceToPlatformIndex(
            Map<String, Map<String, List<String>>> platforms
    ) {
        Map<String, String> result = new HashMap<>();
        platforms.forEach((platformName, typeMap) -> {
            if (typeMap != null) {
                typeMap.values().forEach(deviceList -> {
                    if (deviceList != null) {
                        for (String deviceName : deviceList) {
                            result.put(deviceName, platformName);
                        }
                    }
                });
            }
        });
        return Map.copyOf(result);
    }

    private static Map<String, Boolean> buildDeviceToIsRealStatus(
            Map<String, Map<String, List<String>>> platforms
    ) {
        Map<String, Boolean> result = new HashMap<>();
        platforms.forEach((platformName, typeMap) -> {
            if (typeMap != null) {
                List<String> realDevices = typeMap.get("real");
                if (realDevices != null) {
                    for (String deviceName : realDevices) {
                        result.put(deviceName, true);
                    }
                }
                List<String> simulatorDevices = typeMap.get("simulator");
                if (simulatorDevices != null) {
                    for (String deviceName : simulatorDevices) {
                        result.put(deviceName, false);
                    }
                }
            }
        });
        return Map.copyOf(result);
    }

    private void validateDefaultDevice() {
        if (!deviceToPlatform.containsKey(defaultDevice)) {
            throw new IllegalStateException(
                    "Default device not mapped: " + defaultDevice);
        }
    }

    public String defaultDevice() {
        return defaultDevice;
    }

    public boolean isGroup(String key) {
        return groups.containsKey(key);
    }

    public boolean isDevice(String key) {
        return deviceToPlatform.containsKey(key);
    }

    public String platformOf(String device) {
        return Objects.requireNonNull(
                deviceToPlatform.get(device),
                "Unknown device: " + device);
    }

    public boolean isRealDevice(String device) {
        Boolean status = deviceToIsRealStatus.get(device);
        if (status == null) {
            throw new IllegalArgumentException("Unknown device for real status: " + device);
        }
        return status;
    }

    public List<String> group(String key) {
        return groups.getOrDefault(key, List.of());
    }
}