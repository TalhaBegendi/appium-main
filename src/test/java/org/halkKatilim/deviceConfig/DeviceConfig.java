package org.halkKatilim.deviceConfig;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class DeviceConfig {

    private final String defaultDevice;
    private final Map<String, List<String>> platform;
    private final Map<String, List<String>> groups;
    private final Map<String, String> deviceToPlatform;

    @JsonCreator
    public DeviceConfig(
            @JsonProperty("defaultDevice") String defaultDevice,
            @JsonProperty("platforms") Map<String, List<String>> platforms,
            @JsonProperty("groups") Map<String, List<String>> groups) {
        this.defaultDevice = defaultDevice;
        this.platform = platforms != null ? Map.copyOf(platforms) : Map.of();
        this.groups = groups != null ? Map.copyOf(groups) : Map.of();
        this.deviceToPlatform = buildDeviceToPlatformIndex(platforms);
    }

    private static Map<String, String> buildDeviceToPlatformIndex(Map<String, List<String>> platforms) {
        if (platforms == null) {
            return Map.of();
        }
        return platforms.entrySet().stream()
                .flatMap(entry -> entry.getValue().stream()
                        .map(device -> Map.entry(device, entry.getKey())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
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
        String platform = deviceToPlatform.get(device);
        if (platform == null) {
            throw new IllegalArgumentException("Unknown device: " + device);
        }
        return platform;
    }

    public List<String> group(String key) {
        return groups.get(key);
    }
}