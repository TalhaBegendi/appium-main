package org.halkKatilim.utility.helpers;

import io.appium.java_client.AppiumDriver;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;
import io.qameta.allure.model.Label;
import org.halkKatilim.deviceConfig.DeviceContext;
import org.halkKatilim.deviceConfig.DeviceSpec;
import org.halkKatilim.enums.Language;
import static org.halkKatilim.constant.Config.ENABLE_REPORTING;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.Locale;
import static io.qameta.allure.util.ResultsUtils.createParentSuiteLabel;
import static org.openqa.selenium.OutputType.BYTES;

public final class AllureAttachmentHelper {

    private AllureAttachmentHelper() {}

    public static void attachAllureHierarchy(Scenario scenario) {
        if (!ENABLE_REPORTING) return;
        if (!DeviceContext.isSet()) return;
        DeviceSpec device = DeviceContext.get();
        String platform = device.platform().toUpperCase(Locale.ROOT);
        String deviceName = device.device();
        Language lang = DeviceContext.getLanguage();
        String baseKey = scenario.getUri().toString() + "|" + scenario.getName() + "|" + lang.name();
        String executionKey = baseKey + "|" + platform + "|" + deviceName;
        Allure.getLifecycle().updateTestCase(tr -> {
            tr.setName(scenario.getName()
                            + " [" + lang.getDisplay()
                            + " | " + platform
                            + " | " + deviceName + "]");
            tr.setFullName(executionKey);
            tr.setTestCaseId(md5(baseKey));
            tr.setHistoryId(md5(executionKey));
            tr.getLabels().removeIf(l ->
                    "parentSuite".equals(l.getName()) ||
                            "suite".equals(l.getName()) ||
                            "subSuite".equals(l.getName())
            );
            tr.getLabels().add(createParentSuiteLabel(resolveFeatureName(scenario)));
            tr.getLabels().add(new Label().setName("platform").setValue(platform));
            tr.getLabels().add(new Label().setName("device").setValue(deviceName));
            tr.getLabels().add(new Label().setName("language").setValue(lang.getDisplay()));
        });
    }

    public static void attachDeviceMetadata() {
        if (!ENABLE_REPORTING) return;
        if (!DeviceContext.isSet()) return;
        DeviceSpec device = DeviceContext.get();
        String metadata = "Device: " + device.device() + "\n" + "Platform: " + device.platform();
        Allure.addAttachment("Device Info", "text/plain", metadata);
    }

    public static void attachScreenshot(AppiumDriver driver, String name) {
        if (!ENABLE_REPORTING) return;
        try {
            byte[] screenshot =
                    driver.getScreenshotAs(BYTES);
            Allure.addAttachment(name, "image/png", new ByteArrayInputStream(screenshot), ".png");
        } catch (Exception ignored) {
        }
    }

    private static String resolveFeatureName(Scenario scenario) {
        try {
            Path path = Paths.get(scenario.getUri());
            return capitalize(
                    path.getFileName()
                            .toString()
                            .replace(".feature", "")
            );
        } catch (Exception e) {
            return "Feature";
        }
    }

    private static String capitalize(String text) {
        if (text == null || text.isEmpty()) return text;
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }

    private static String md5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (Exception e) {
            return String.valueOf(input.hashCode());
        }
    }
}