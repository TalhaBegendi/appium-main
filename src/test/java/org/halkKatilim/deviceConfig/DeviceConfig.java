package org.halkKatilim.deviceConfig;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public final class DeviceConfig {

    private final String defaultDevice;
    private final Map<String, List<String>> groups;
    private final Map<String, String> deviceToPlatform;

    @JsonCreator
    public DeviceConfig(
            @JsonProperty("defaultDevice") String defaultDevice,
            @JsonProperty("platforms") Map<String, List<String>> platforms,
            @JsonProperty("groups") Map<String, List<String>> groups) {

        this.defaultDevice = Objects.requireNonNull(defaultDevice, "defaultDevice required");
        Map<String, List<String>> normalizedPlatforms = toDeepUnmodifiable(platforms);
        this.groups = toDeepUnmodifiable(groups);
        this.deviceToPlatform = buildDeviceToPlatformIndex(normalizedPlatforms);
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
            Map<String, List<String>> platforms
    ) {
        return platforms.entrySet()
                .stream()
                .flatMap(entry ->
                        entry.getValue().stream()
                                .map(device ->
                                        Map.entry(device, entry.getKey())
                                )
                )
                .collect(Collectors.toUnmodifiableMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue
                ));
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

    public List<String> group(String key) {
        return groups.getOrDefault(key, List.of());
    }
}