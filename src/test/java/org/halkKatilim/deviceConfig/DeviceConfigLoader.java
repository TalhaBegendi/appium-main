package org.halkKatilim.deviceConfig;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;

public final class DeviceConfigLoader {

    private static final String RESOURCE_PATH = "deviceConfig/device.json";
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private DeviceConfigLoader() {}

    public static DeviceConfig load() {
        return Holder.INSTANCE;
    }

    private static final class Holder {
        private static final DeviceConfig INSTANCE = loadInternal();
    }

    private static DeviceConfig loadInternal() {
        try (InputStream is = DeviceConfigLoader.class
                             .getClassLoader()
                             .getResourceAsStream(RESOURCE_PATH)) {
            if (is == null) {
                throw new IllegalStateException(
                        "Device config not found on classpath: " + RESOURCE_PATH);
            }
            return MAPPER.readValue(is, DeviceConfig.class);
        } catch (Exception e) {
            throw new IllegalStateException(
                    "Failed to load device config: " + RESOURCE_PATH, e
            );
        }
    }
}