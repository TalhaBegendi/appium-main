package org.halkKatilim.utility.context;

import io.appium.java_client.AppiumDriver;
import org.halkKatilim.constant.SelectorType;
import org.halkKatilim.enums.Platform;
import org.halkKatilim.selector.Selector;
import org.halkKatilim.selector.SelectorFactory;
import org.halkKatilim.utility.Driver;
import org.halkKatilim.utility.appiumUtil.AppiumUtil;
import org.halkKatilim.utility.assertionUtil.types.HardAssertion;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.FluentWait;
import org.halkKatilim.deviceConfig.DeviceContext;
import java.time.Duration;

public final class ContextLifecycleManager {

    private ContextLifecycleManager() {}

    public static void start(AppiumDriver driver) {
        AppiumUtil util = new AppiumUtil();
        ExecutionContext.init(
                buildWait(driver),
                buildSelector(),
                util,
                new HardAssertion(util)
        );
        PageContext.init(util);
    }

    public static void clearAll() {
        safeClear(ScenarioRunContext::clear);
        safeClear(PageContext::clear);
        safeClear(ExecutionContext::clear);
        safeClear(DeviceContext::clear);
    }

    private static void safeClear(Runnable action) {
        try {
            action.run();
        } catch (Exception ignored) {}
    }

    private static FluentWait<AppiumDriver> buildWait(AppiumDriver driver) {
        return new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(4))
                .pollingEvery(Duration.ofMillis(250))
                .ignoring(NoSuchElementException.class);
    }

    private static Selector buildSelector() {
        Platform platform = Driver.getPlatformForThread();
        SelectorType selectorType = (platform == Platform.ANDROID)
                ? SelectorType.ANDROID
                : SelectorType.IOS;
        return SelectorFactory.createElementHelper(selectorType);
    }
}