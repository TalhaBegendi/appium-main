package org.halkKatilim.utility.helpers;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.InteractsWithApps;
import org.halkKatilim.constant.Config;
import org.halkKatilim.enums.Platform;
import org.halkKatilim.utility.Driver;

public final class AppUtils {

    public static void ensureAppInstalled(AppiumDriver driver) {
        if (driver == null) {
            FrameworkLogger.error("❌ Driver is null, cannot check app installation");
            return;
        }
        try {
            InteractsWithApps apps = (InteractsWithApps) driver;
            Platform platform = Driver.getPlatformForThread();
            final String appId =
                    platform == Platform.ANDROID
                            ? Config.APP_PACKAGE
                            : Config.IOS_BUNDLE_ID;
            final String appPath =
                    platform == Platform.ANDROID
                            ? Config.ANDROID_APP_PATH
                            : Config.IOS_APP_PATH;
            if (apps.isAppInstalled(appId)) {
                if (!Config.APP_NO_RESET) {
                    apps.removeApp(appId);
                    apps.installApp(appPath);
                    FrameworkLogger.info("🔄 Re-installed app → " + appPath);
                } else {
                    apps.terminateApp(appId);
                    FrameworkLogger.info("🛑 Terminated app → " + appId);
                }
            } else {
                apps.installApp(appPath);
                FrameworkLogger.info("📦 Installed app → " + appPath);
            }

            CapabilityFactory.grantRuntimePermissions(driver, appId);
            apps.activateApp(appId);
            FrameworkLogger.info("▶️ Launched app → " + appId);
            Thread.sleep(1950);
        } catch (Exception e) {
            FrameworkLogger.error("❌ App lifecycle failed: " + e.getMessage());
        }
    }
}