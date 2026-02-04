package org.halkKatilim.utility;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.halkKatilim.constant.Config;
import org.halkKatilim.enums.Platform;
import org.halkKatilim.utility.helpers.CapabilityFactory;
import org.halkKatilim.utility.helpers.DevicePoolManager;
import org.halkKatilim.utility.helpers.FrameworkLogger;

import java.net.URL;
import java.time.Duration;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;

import static org.halkKatilim.constant.Config.*;

public final class Driver {

    private static final ThreadLocal<AppiumDriver> APPIUM_DRIVER_THREAD = new ThreadLocal<>();
    private static final ThreadLocal<Map<String, String>> ASSIGNED_DEVICE_THREAD = new ThreadLocal<>();
    private static final ThreadLocal<Platform> PLATFORM_THREAD = new ThreadLocal<>();

    private Driver() {}

    /* =========================
       PLATFORM
       ========================= */

    public static void setPlatformForThread(Platform platform) {
        PLATFORM_THREAD.set(platform);
        platform.markUsed();
    }

    public static Platform getPlatformForThread() {
        return PLATFORM_THREAD.get();
    }

    /* =========================
       DRIVER LIFECYCLE
       ========================= */

    public static void setUpByConfig() {
        try {
            Platform platform = PLATFORM_THREAD.get();
            if (platform == null) {
                throw new IllegalStateException("Platform not set for current thread");
            }

            AppiumDriver driver = createDriver(GRID);
            driver.manage()
                    .timeouts()
                    .implicitlyWait(Duration.ofSeconds(DEFAULT_WAIT));

            APPIUM_DRIVER_THREAD.set(driver);
            logAssignedDevice();
        } catch (Exception e) {
            throw new RuntimeException("❌ Driver setup failed", e);
        }
    }

    public static AppiumDriver getDriver() {
        return APPIUM_DRIVER_THREAD.get();
    }

    public static void quitDriver() {
        AppiumDriver driver = APPIUM_DRIVER_THREAD.get();
        try {
            if (driver != null && driver.getSessionId() != null) {
                driver.quit();
                FrameworkLogger.info("✅ Driver quit");
            }
        } catch (Exception e) {
            FrameworkLogger.warn("⚠️ Driver quit warning → " + e.getMessage());
        } finally {
            releaseDeviceBackToPool();
            clearThreadLocals();
        }
    }

    /* =========================
       INTERNALS
       ========================= */

    private static AppiumDriver createDriver(boolean grid) {
        Platform platform = PLATFORM_THREAD.get();
        Map<String, String> device = pickDevice(platform);
        ASSIGNED_DEVICE_THREAD.set(device);

        URL url = resolveServerUrl(device, grid);

        FrameworkLogger.info(String.format(
                "🚀 Starting %s session (%s)",
                platform,
                grid ? "GRID" : "LOCAL"
        ));

        return (platform == Platform.ANDROID)
                ? new AndroidDriver(url, CapabilityFactory.forAndroid(device))
                : new IOSDriver(url, CapabilityFactory.forIOS(device));
    }

    private static URL resolveServerUrl(Map<String, String> device, boolean grid) {
        try {
            String url = grid
                    ? GRID_HUB_URL + "/wd/hub"
                    : String.format(
                    "%s:%d/wd/hub",
                    LOCAL_HUB_URL,
                    Optional.ofNullable(
                            Config.getAppiumPortForUdid(device.get("appium:udid"))
                    ).orElseThrow(() ->
                            new RuntimeException("Port mapping missing"))
            );

            FrameworkLogger.info("🔗 Connecting to → " + url);
            return new URL(url);

        } catch (Exception e) {
            throw new RuntimeException("URL resolution failed", e);
        }
    }

    private static Map<String, String> pickDevice(Platform platform) {
        Queue<Map<String, String>> pool = Optional
                .ofNullable(DevicePoolManager.getPool(platform))
                .filter(p -> !p.isEmpty())
                .orElseThrow(() ->
                        new IllegalStateException("No devices configured for " + platform));

        Map<String, String> device = Optional.ofNullable(pool.poll())
                .orElseThrow(() ->
                        new IllegalStateException("No available devices left for " + platform));

        FrameworkLogger.info(String.format(
                "📱 Selected → %s",
                device.getOrDefault("appium:deviceName", "UnknownDevice")
        ));

        return device;
    }

    private static void releaseDeviceBackToPool() {
        Map<String, String> assigned = ASSIGNED_DEVICE_THREAD.get();
        if (assigned == null) return;

        Platform platform = PLATFORM_THREAD.get();
        if (platform == null) return;

        Queue<Map<String, String>> pool = DevicePoolManager.getPool(platform);
        pool.offer(assigned);

        FrameworkLogger.info("🔁 Device released back to pool");
    }

    private static void clearThreadLocals() {
        APPIUM_DRIVER_THREAD.remove();
        ASSIGNED_DEVICE_THREAD.remove();
        PLATFORM_THREAD.remove();
    }

    public static void logAssignedDevice() {
        Map<String, String> device = ASSIGNED_DEVICE_THREAD.get();
        if (device == null) return;
        FrameworkLogger.info(String.format(
                "[DEVICE] %s (UDID=%s, Port=%s)",
                device.get("appium:deviceName"),
                device.get("appium:udid"),
                device.get("appium:appiumServerPort")
        ));
    }
}