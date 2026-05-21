package org.halkKatilim.stepDefs;

import io.appium.java_client.AppiumDriver;
import io.cucumber.java.*;
import org.halkKatilim.deviceConfig.DeviceContext;
import org.halkKatilim.deviceConfig.DeviceSpec;
import org.halkKatilim.utility.context.ContextLifecycleManager;
import org.halkKatilim.utility.context.ExecutionContext;
import org.halkKatilim.utility.helpers.*;
import static org.halkKatilim.constant.Config.ENABLE_REPORTING;
import static org.halkKatilim.utility.Driver.quitDriver;
import static org.halkKatilim.utility.Driver.setUpByConfig;

public class Hooks {

    @Before
    public void beforeScenario(Scenario scenario) {
        DeviceSpec device = DeviceContext.get();
        DeviceManager.prepare(device.device(), device.platform());
        setUpByConfig();
        AppiumDriver driver = ExecutionContext.getDriver();
        ContextLifecycleManager.start(driver);
        AppUtils.ensureAppInstalled(driver);
    }

    @After
    public void afterScenario(Scenario scenario) {
        if (ENABLE_REPORTING) {
            AllureAttachmentHelper.attachAllureHierarchy(scenario);
            if (scenario.isFailed()) {
                AllureAttachmentHelper.attachScreenshot(
                        ExecutionContext.getDriver(),
                        "Failure Screenshot");
            }
        }
        AppiumDriver driver = ExecutionContext.getDriver();
        AppUtils.handleTestEndAppTermination(driver);
        quitDriver();
        ContextLifecycleManager.clearAll();
    }
}