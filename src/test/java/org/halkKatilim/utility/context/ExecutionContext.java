package org.halkKatilim.utility.context;

import io.appium.java_client.AppiumDriver;
import lombok.experimental.UtilityClass;
import org.halkKatilim.selector.Selector;
import org.halkKatilim.utility.Driver;
import org.halkKatilim.utility.appiumUtil.AppiumUtil;
import org.halkKatilim.utility.assertionUtil.types.HardAssertion;
import org.openqa.selenium.support.ui.FluentWait;

@UtilityClass
public class ExecutionContext {

    private final ThreadLocal<FluentWait<AppiumDriver>> WAIT = new ThreadLocal<>();
    private final ThreadLocal<Selector> SELECTOR = new ThreadLocal<>();
    private final ThreadLocal<AppiumUtil> UTIL = new ThreadLocal<>();
    private final ThreadLocal<HardAssertion> ASSERTION = new ThreadLocal<>();

    public void init(FluentWait<AppiumDriver> wait,
                     Selector selector,
                     AppiumUtil util,
                     HardAssertion assertion) {

        if (WAIT.get() != null) {
            throw new IllegalStateException("ExecutionContext already initialized!");
        }

        WAIT.set(wait);
        SELECTOR.set(selector);
        UTIL.set(util);
        ASSERTION.set(assertion);
    }

    public AppiumDriver getDriver() {
        AppiumDriver d = Driver.getDriver();
        if (d == null) throw new IllegalStateException("Driver not initialized!");
        return d;
    }

    public FluentWait<AppiumDriver> getFluentWait() {
        FluentWait<AppiumDriver> w = WAIT.get();
        if (w == null) {
            throw new IllegalStateException("Wait not initialized!");
        }
        return w;
    }

    public Selector getSelector() {
        Selector s = SELECTOR.get();
        if (s == null) throw new IllegalStateException("Selector not initialized!");
        return s;
    }

    public AppiumUtil getUtil() {
        AppiumUtil u = UTIL.get();
        if (u == null) throw new IllegalStateException("Util not initialized!");
        return u;
    }

    public HardAssertion getAssertion() {
        HardAssertion a = ASSERTION.get();
        if (a == null) throw new IllegalStateException("Assertion not initialized!");
        return a;
    }

    public void clear() {
        WAIT.remove();
        SELECTOR.remove();
        UTIL.remove();
        ASSERTION.remove();
    }
}