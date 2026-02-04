package org.halkKatilim.pages;

import io.appium.java_client.AppiumDriver;
import org.halkKatilim.selector.Selector;
import org.halkKatilim.utility.appiumUtil.AppiumUtil;
import org.halkKatilim.utility.assertionUtil.types.HardAssertion;
import org.halkKatilim.utility.context.ScenarioRunContext;
import org.openqa.selenium.support.ui.FluentWait;

public abstract class BasePages {

    protected static AppiumDriver appiumDriver;
    protected static FluentWait<AppiumDriver> appiumFluentWait;
    protected static Selector selector;
    protected static AppiumUtil appiumUtil;
    protected static HardAssertion hardAssertion;
    protected final ScenarioRunContext runContext = new ScenarioRunContext();
}

