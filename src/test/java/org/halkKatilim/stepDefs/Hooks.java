package org.halkKatilim.stepDefs;

import io.cucumber.java.*;
import org.halkKatilim.constant.SelectorType;
import org.halkKatilim.deviceConfig.DeviceContext;
import org.halkKatilim.deviceConfig.DeviceSpec;
import org.halkKatilim.enums.Platform;
import org.halkKatilim.pages.BasePages;
import org.halkKatilim.selector.SelectorFactory;
import org.halkKatilim.utility.Driver;
import org.halkKatilim.utility.appiumUtil.AppiumUtil;
import org.halkKatilim.utility.assertionUtil.enums.AssertionKey;
import org.halkKatilim.utility.assertionUtil.types.HardAssertion;
import org.halkKatilim.utility.helpers.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;

import static org.halkKatilim.constant.Config.ENABLE_REPORTING;
import static org.halkKatilim.utility.Driver.*;

public class Hooks extends BasePages {
    @Before
    public void beforeScenario(Scenario scenario) {
        DeviceSpec device = DeviceContext.get();
        DeviceManager.prepare(device.device(), device.platform());
        setUpByConfig();
        appiumDriver = Driver.getDriver();
        AppUtils.ensureAppInstalled(appiumDriver);
        initSelectorAndWait();
        appiumUtil = new AppiumUtil();
        hardAssertion = new HardAssertion(appiumUtil);
        for (AssertionKey k : AssertionKey.values()) {
            k.bind(hardAssertion);
        }
    }

    @AfterStep
    public void afterStep(Scenario scenario) {
        if (ENABLE_REPORTING) {
            AllureAttachmentHelper.attachAllureHierarchy(scenario);
            AllureAttachmentHelper.attachDeviceMetadata();
            if (scenario.isFailed()) {
                AllureAttachmentHelper.attachScreenshot(appiumDriver, "Failure Screenshot");
            }
        }
    }

    @After
    public void afterScenario() {
        quitDriver();
        DeviceContext.clear();
    }

    private void initSelectorAndWait() {

        selector = SelectorFactory.createElementHelper(
                Driver.getPlatformForThread() == Platform.ANDROID
                        ? SelectorType.ANDROID
                        : SelectorType.IOS
        );

        appiumFluentWait = new FluentWait<>(appiumDriver)
                .withTimeout(Duration.ofSeconds(4))
                .pollingEvery(Duration.ofMillis(250))
                .ignoring(NoSuchElementException.class);
    }
}