package org.halkKatilim.constant;

import org.halkKatilim.utility.helpers.FrameworkLogger;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public final class Config {

    private static final Properties baseProps = new Properties();

    static {
        try {
            String configPath = System.getProperty("user.dir") + "/env/default/appium.properties";
            baseProps.load(new FileInputStream(configPath));
        } catch (IOException e) {
            FrameworkLogger.warn("⚠️ appium.properties okunamadı, default değerler kullanılacak.");
        }
    }

    // --- Helpers ---
    private static String envOrProp(String envKey, String propKey, String defaultVal) {
        String env = System.getenv(envKey);
        if (env != null && !env.trim().isEmpty())
            return env.trim();

        String prop = baseProps.getProperty(propKey);
        if (prop != null && !prop.trim().isEmpty())
            return prop.trim();

        return defaultVal;
    }

    private static boolean envOrPropBool(String envKey, String propKey, boolean defaultVal) {
        return Boolean.parseBoolean(envOrProp(envKey, propKey, String.valueOf(defaultVal)));
    }

    private static long envOrPropLong(String envKey, String propKey, long def) {
        try {
            return Long.parseLong(envOrProp(envKey, propKey, String.valueOf(def)));
        } catch (NumberFormatException e) {
            return def;
        }
    }

    private static File runtimeFile(String name, String suffix) {
        return new File("target/runtime/" + name + suffix);
    }

    private static String hubUrl(String key, String defaultVal) {
        return envOrProp(key, key, defaultVal);
    }

    public static final String PLATFORM_NAME_RAW = envOrProp("PLATFORM", "PlatformName", "ios").toLowerCase(Locale.ROOT);

    // ==============================
    // Grid / Local
    // ==============================
    public static final boolean GRID = envOrPropBool("GRID", "GRID", false);
    public static final boolean PARALLEL = envOrPropBool("PARALLEL", "PARALLEL", false);

    // ==============================
    // Allure Raporlama
    // ==============================
    public static final boolean ENABLE_REPORTING = envOrPropBool("ENABLE_REPORTING", "ENABLE_REPORTING", true);

    public static final File SERIAL_MARKER_ALL = runtimeFile("serial.grid", ".started");
    public static final File ALL_LOCK = runtimeFile("grid", ".lock");
    public static final File CLEANUP_COUNTER = runtimeFile("cleanup", ".counter");

    public static final String GRID_HUB_URL = hubUrl("GRID_HUB_URL", "");
    public static final String LOCAL_HUB_URL = hubUrl("LOCAL_HUB_URL", "");

    public static final String APPIUM_PORT_MAP = envOrProp("APPIUM_PORT_MAP", "APPIUM_PORT_MAP",
            System.getProperty("user.dir") + "/target/runtime/appium_ports.properties");

    // ==============================
    // Appium Port Mapping
    // ==============================
    private static final ConcurrentMap<String, String> APPIUM_PORTS = new ConcurrentHashMap<>();

    private static long lastModifiedTime = 0L;

    public static Map<String, String> getAppiumPortEntries() {
        return APPIUM_PORTS;
    }

    public static synchronized void reloadAppiumPortMap() {
        Path path = Paths.get(APPIUM_PORT_MAP);
        File file = path.toFile();
        long modified = file.lastModified();

        if (modified == lastModifiedTime && !APPIUM_PORTS.isEmpty()) {
            return;
        }

        try (InputStream is = Files.newInputStream(path)) {
            Properties tmp = new Properties();
            tmp.load(is);

            APPIUM_PORTS.clear();
            tmp.forEach((k, v) -> {
                String key = k.toString().trim();
                String value = v.toString().trim();
                APPIUM_PORTS.put(key, value);
            });
            lastModifiedTime = modified;

            if (APPIUM_PORTS.isEmpty()) {
                FrameworkLogger.error("❌ appium_ports.properties yüklendi ama boş!");
            }
        } catch (IOException e) {
            if (!Files.exists(path)) {
                FrameworkLogger.debug("ℹ️ appium_ports.properties henüz yok (ilk başlatma)");
            } else {
                FrameworkLogger.error("❌ appium_ports.properties okunamadı: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public static Integer getAppiumPortForUdid(String udid) {
        if (udid == null || udid.isEmpty()) {
            FrameworkLogger.error("❌ getAppiumPortForUdid called with null/empty UDID");
            return null;
        }
        try {
            return APPIUM_PORTS.values().stream()
                    .filter(Objects::nonNull)
                    .filter(value -> value.startsWith(udid + "="))
                    .map(value -> value.split("=", 2))
                    .filter(parts -> parts.length == 2)
                    .map(parts -> parts[1].trim())
                    .map(Integer::parseInt)
                    .findFirst()
                    .orElse(null);
        } catch (NumberFormatException e) {
            FrameworkLogger.error("❌ Invalid port number for UDID=" + udid + " → " + e.getMessage());
            return null;
        }
    }

    // Real Device Config is now handled dynamically per-thread based on device configuration.

    // ==============================
    // Common Capabilities
    // ==============================
    public static final int DEFAULT_WAIT = 3;
    public static final boolean APP_NO_RESET = envOrPropBool("APP_NO_RESET", "APP_NO_RESET", false);
    public static final boolean FULL_RESET = false;

    public static final boolean HEADLESS = envOrPropBool("HEADLESS", "HEADLESS", false);
    public static final String LANGUAGE = envOrProp("LANGUAGE", "LANGUAGE", "en");
    public static final String LOCALE = envOrProp("LOCALE", "LOCALE", "US");

    public static final boolean RECORD_VIDEO = envOrPropBool("RECORD_VIDEO", "RECORD_VIDEO", false);
    public static final boolean AUTO_ACCEPT_ALERTS = envOrPropBool("AUTO_ACCEPT_ALERTS", "AUTO_ACCEPT_ALERTS", true);
    public static final boolean AUTO_DISMISS_ALERTS = envOrPropBool("AUTO_DISMISS_ALERTS", "AUTO_DISMISS_ALERTS",
            false);

    // Capability keys
    public static final String CAP_RECORD_VIDEO = "appium:recordVideo";
    public static final String CAP_AUTO_ACCEPT_ALERTS = "appium:autoAcceptAlerts";
    public static final String CAP_AUTO_DISMISS_ALERTS = "appium:autoDismissAlerts";

    // ==============================
    // Appium platform sabitleri
    // ==============================
    public static final String ANDROID_AUTOMATION_NAME = "UiAutomator2";
    public static final String IOS_AUTOMATION_NAME = "XCUITest";

    // ==============================
    // iOS
    // ==============================
    public static final String IOS_APP_PATH = envOrProp("IOS_APP_PATH", "IOS_APP_PATH", "");
    public static final String IOS_BUNDLE_ID = envOrProp("IOS_BUNDLE_ID", "IOS_BUNDLE_ID", "");

    // ==============================
    // Android
    // ==============================
    public static final String ANDROID_APP_PATH = envOrProp("ANDROID_APP_PATH", "ANDROID_APP_PATH", "");
    public static final String APP_PACKAGE = envOrProp("APP_PACKAGE", "APP_PACKAGE", "");

    public static final long NEW_COMMAND_TIMEOUT = envOrPropLong("NEW_COMMAND_TIMEOUT", "NEW_COMMAND_TIMEOUT", 30000L);

    private Config() {
    }

    public static Map<String, String> getDefaultCapsForPlatform() {
        return Map.of(
                "appium:automationName",
                switch (PLATFORM_NAME_RAW) {
                    case "ios" -> IOS_AUTOMATION_NAME;
                    case "android" -> ANDROID_AUTOMATION_NAME;
                    default -> throw new IllegalArgumentException("❌ Unknown platform: " + PLATFORM_NAME_RAW);
                });
    }

    public static String resolveAndroidUdidForTag(String tag) {
        reloadAppiumPortMap();
        if (APPIUM_PORTS.isEmpty()) {
            FrameworkLogger.error("❌ Port map boş, UDID çözülemedi (tag=" + tag + ")");
            return null;
        }
        String normTag = tag.toLowerCase(Locale.ROOT).replace("_", "").trim();
        return APPIUM_PORTS.entrySet().stream()
                .filter(entry -> entry.getKey() != null)
                .map(entry -> new AbstractMap.SimpleEntry<>(
                        entry.getKey().split(":")[0].toLowerCase(Locale.ROOT).replace("_", "").trim(),
                        entry.getValue()))
                .filter(e -> e.getKey().equalsIgnoreCase(normTag))
                .map(Map.Entry::getValue)
                .filter(Objects::nonNull)
                .map(value -> value.split("=", 2))
                .filter(parts -> parts.length >= 1)
                .map(parts -> parts[0].trim()) // UDID
                .findFirst()
                .map(udid -> {
                    FrameworkLogger.info("✅ Resolved UDID (tag=" + tag + ") → " + udid);
                    return udid;
                })
                .orElseGet(() -> {
                    FrameworkLogger.warn("⚠️ UDID bulunamadı (tag=" + tag + ")");
                    return null;
                });
    }
}