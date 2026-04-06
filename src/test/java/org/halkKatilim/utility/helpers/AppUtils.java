package org.halkKatilim.utility.helpers;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.InteractsWithApps;
import org.halkKatilim.deviceConfig.DeviceContext;
import org.halkKatilim.enums.Platform;
import org.halkKatilim.utility.Driver;
import static org.halkKatilim.constant.Config.*;

public final class AppUtils {

    public static String getCurrentAppId() {
        return Driver.getPlatformForThread() == Platform.ANDROID
                ? APP_PACKAGE
                : IOS_BUNDLE_ID;
    }

    public static String getCurrentAppPath() {
        return Driver.getPlatformForThread() == Platform.ANDROID
                ? ANDROID_APP_PATH
                : IOS_APP_PATH;
    }

    public static void ensureAppInstalled(AppiumDriver driver) {
        if (driver == null) {
            FrameworkLogger.error("❌ Driver is null, cannot check app installation");
            return;
        }

        try {
            InteractsWithApps apps = (InteractsWithApps) driver;
            String appId = getCurrentAppId();
            String appPath = getCurrentAppPath();

            if (DeviceContext.get().isRealDevice()) {
                handleRealDevice(apps, driver, appId);
            } else {
                handleSimulator(apps, driver, appId, appPath);
            }

            waitForAppReady(apps, appId);

        } catch (Exception e) {
            FrameworkLogger.error("❌ App lifecycle failed: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private static void handleRealDevice(InteractsWithApps apps, AppiumDriver driver, String appId) {
        apps.terminateApp(appId);
        launchApp(apps, driver, appId);
        FrameworkLogger.info("📱 (Real Device) Activated pre-installed app → " + appId);
    }

    private static void handleSimulator(InteractsWithApps apps, AppiumDriver driver,
                                        String appId, String appPath) {

        if (!apps.isAppInstalled(appId)) {
            apps.installApp(appPath);
            FrameworkLogger.info("📦 Installed app → " + appPath);

        } else if (APP_NO_RESET) {
            apps.removeApp(appId);
            apps.installApp(appPath);
            FrameworkLogger.info("🔄 Re-installed app → " + appPath);

        } else {
            apps.terminateApp(appId);
            FrameworkLogger.info("🛑 Terminated app → " + appId);
        }

        launchApp(apps, driver, appId);
        FrameworkLogger.info("▶️ (Simulator) Launched app → " + appId);
    }

    private static void launchApp(InteractsWithApps apps, AppiumDriver driver, String appId) {
        CapabilityFactory.grantRuntimePermissions(driver, appId);
        apps.activateApp(appId);
    }

    private static void waitForAppReady(InteractsWithApps apps, String appId) {
        long timeout = System.currentTimeMillis() + 5000;

        while (System.currentTimeMillis() < timeout) {
            try {
                if (apps.queryAppState(appId).toString().contains("RUNNING")) {
                    return;
                }
                Thread.sleep(300);
            } catch (Exception ignored) {}
        }

        FrameworkLogger.warn("⚠️ App may not be fully ready → " + appId);
    }

    public static void handleTestEndAppTermination(AppiumDriver driver) {
        if (DeviceContext.get().isRealDevice() && driver != null) {
            try {
                String appId = getCurrentAppId();
                ((InteractsWithApps) driver).terminateApp(appId);
                FrameworkLogger.info("🛑 (Real Device) App terminated -> " + appId);
            } catch (Exception e) {
                FrameworkLogger.warn("⚠️ Could not terminate app: " + e.getMessage());
            }
        }
    }
}