package org.halkKatilim.utility.helpers;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.options.XCUITestOptions;
import io.appium.java_client.remote.options.BaseOptions;
import org.halkKatilim.deviceConfig.DeviceContext;
import org.halkKatilim.enums.Platform;
import org.halkKatilim.utility.Driver;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import static org.halkKatilim.constant.Config.*;

public final class CapabilityFactory {

    public static XCUITestOptions forIOS(Map<String, String> device) {
        XCUITestOptions options = new XCUITestOptions()
                .setAutomationName(IOS_AUTOMATION_NAME)
                .setAutoAcceptAlerts(true)
                .setAutoDismissAlerts(true)
                .amend("useNewWDA", false)
                .amend("wdaLaunchTimeout", 60000)
                .amend("wdaConnectionTimeout", 60000)
                .amend("appium:waitForQuiescence", false);
        applyCommonOptions(options);
        applyDeviceCapabilities(options, device, "iPhone");

        return options;
    }

    public static UiAutomator2Options forAndroid(Map<String, String> device) {
        UiAutomator2Options options = new UiAutomator2Options()
                .setAutomationName(ANDROID_AUTOMATION_NAME)
                .setNewCommandTimeout(Duration.ofMillis(NEW_COMMAND_TIMEOUT))
                .setIsHeadless(HEADLESS)
                .amend("autoGrantPermissions", true)
                .amend("ignoreHiddenApiPolicyError", true)
                .amend("disableWindowAnimation", true)
                .amend("forceLocationManager", true);
        applyCommonOptions(options);
        applyDeviceCapabilities(options, device, "Android");
        return options;
    }

    private static void applyCommonOptions(BaseOptions<?> options) {
        options.setPlatformName(Driver.getPlatformForThread() == Platform.ANDROID ? "android" : "ios")
                .setNoReset(DeviceContext.get().isRealDevice() || APP_NO_RESET)
                .setFullReset(FULL_RESET)
                .amend(CAP_RECORD_VIDEO, RECORD_VIDEO)
                .amend(CAP_AUTO_ACCEPT_ALERTS, AUTO_ACCEPT_ALERTS)
                .amend(CAP_AUTO_DISMISS_ALERTS, AUTO_DISMISS_ALERTS)
                .setCapability("autoWebview", false);
        if (options instanceof XCUITestOptions iosOpts) {
            iosOpts.setLanguage(LANGUAGE).setLocale(LOCALE);
        }
    }

    private static <T extends BaseOptions<T>> void applyDeviceCapabilities(T options, Map<String, String> device, String defaultName) {
        Map<String, String> safeDevice = getStringStringMap(options, device, defaultName);
        safeDevice.forEach((key, value) -> {
            boolean isAllowedCap =
                    key.endsWith("Port") ||
                            key.contains("BundleId") ||
                            (DeviceContext.get().isRealDevice() && (
                                    key.equals("appium:xcodeOrgId") ||
                                            key.equals("appium:xcodeSigningId")));
            if (notEmpty(value) && isAllowedCap) {
                Object parsed = value.matches("\\d+") ? Integer.parseInt(value) : value;
                options.setCapability(key, parsed);
            }
        });
    }

    private static <T extends BaseOptions<T>> Map<String, String> getStringStringMap(T options, Map<String, String> device, String defaultName) {
        Map<String, String> safeDevice = device == null ? Map.of() : device;
        List<String> baseKeys = List.of(
                "appium:deviceName",
                "appium:platformVersion",
                "appium:udid"
        );
        Map<String, String> defaults = Map.of(
                "appium:deviceName", defaultName,
                "appium:platformVersion", "latest",
                "appium:udid", ""
        );
        baseKeys.forEach(k -> {
            String value = safeDevice.get(k);
            options.setCapability(k, notEmpty(value) ? value : defaults.get(k));
        });
        return safeDevice;
    }

    private static boolean notEmpty(String v) {
        return v != null && !v.trim().isEmpty();
    }

    public static void grantRuntimePermissions(AppiumDriver driver, String appId) {
        if (Driver.getPlatformForThread() == Platform.ANDROID) {
            String udid = driver.getCapabilities().getCapability("udid").toString();
            grantAndroidPermissions(udid, appId);
            return;
        }
        if (Driver.getPlatformForThread() == Platform.IOS) {
            grantIOSPermission(appId);
        }
    }

    public static void grantIOSPermission(String bundleId) {
        try {
            grant("camera", bundleId);
            grant("microphone", bundleId);
            grant("photos", bundleId);
            grant("location", bundleId);
        } catch (Exception ignored) {
        }
    }

    private static void grant(String service, String bundleId) throws Exception {
        new ProcessBuilder(
                "xcrun", "simctl", "privacy", "booted",
                "grant", service, bundleId).redirectErrorStream(true)
                .start()
                .waitFor();
    }

    public static void grantAndroidPermissions(String udid, String pkg) {
        List<String> permissions = List.of(
                "android.permission.POST_NOTIFICATIONS",
                "android.permission.ACCESS_FINE_LOCATION",
                "android.permission.ACCESS_COARSE_LOCATION",
                "android.permission.ACCESS_BACKGROUND_LOCATION",
                "android.permission.CAMERA");
        permissions.forEach(perm -> runAdb(udid, "pm", "grant", pkg, perm));
        runAdb(udid, "settings", "put", "secure", "location_mode", "3"); // High Accuracy
        runAdb(udid, "appops", "set", pkg, "ACCESS_FINE_LOCATION", "allow"); // optional
        runAdb(udid, "appops", "set", pkg, "CAMERA", "allow");
    }

    private static void runAdb(String udid, String... commands) {
        try {
            List<String> cmd = new ArrayList<>();
            cmd.add("adb");
            cmd.add("-s");
            cmd.add(udid);
            cmd.add("shell");
            cmd.addAll(List.of(commands));
            new ProcessBuilder(cmd)
                    .redirectErrorStream(true)
                    .start()
                    .waitFor();
        } catch (Exception ignored) {
        }
    }
}