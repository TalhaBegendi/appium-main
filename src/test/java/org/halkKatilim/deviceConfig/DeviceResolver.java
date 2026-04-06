package org.halkKatilim.deviceConfig;

import java.util.*;

public final class DeviceResolver {

    private final DeviceConfig config;

    public DeviceResolver(DeviceConfig config) {
        this.config = config;
    }

    public List<DeviceSpec> resolve(String key) {

        if ("default".equals(key)) {
            return List.of(single(config.defaultDevice()));
        }

        if (config.isGroup(key)) {
            return config.group(key).stream()
                    .map(this::single)
                    .toList();
        }

        if (config.isDevice(key)) {
            return List.of(single(key));
        }
        throw new IllegalArgumentException("Unknown or empty device group: " + key);
    }

    private DeviceSpec single(String device) {
        return new DeviceSpec(
                config.platformOf(device),
                device,
                config.isRealDevice(device)
        );
    }
}