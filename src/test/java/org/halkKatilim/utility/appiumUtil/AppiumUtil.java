package org.halkKatilim.utility.appiumUtil;

import lombok.*;
import org.halkKatilim.enums.NavigationGates;
import org.halkKatilim.enums.Platform;
import org.halkKatilim.enums.TextSource;
import org.halkKatilim.utility.Driver;
import org.halkKatilim.utility.WaitConditions;
import org.halkKatilim.utility.assertionUtil.enums.AssertionKey;
import org.halkKatilim.utility.assertionUtil.enums.AssertionPrefix;
import org.openqa.selenium.*;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.*;
import java.util.NoSuchElementException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import org.halkKatilim.pages.BasePages;

import static org.halkKatilim.utility.appiumUtil.AppiumUtilText.*;
import static org.halkKatilim.utility.assertionUtil.enums.AssertionKey.ASSETS;
import static org.halkKatilim.utility.helpers.FrameworkLogger.*;
import static org.testng.Assert.fail;

@RequiredArgsConstructor
public class AppiumUtil extends BasePages implements WaitConditions {

    AppiumUtilHelper appiumUtilHelper = new AppiumUtilHelper();

    public record StepResult(List<String> steps, AssertionKey key) { }

    public StepResult navigateWithAssertion(String path, String assertion, String clickKey, String contextKey, AssertionPrefix prefix) {
        List<String> steps = navigate(path, clickKey, contextKey, prefix);
        AssertionKey key = appiumUtilHelper.parseAssertion(assertion);
        autoHandleNavigationGates(NavigationGates.Context.DEFAULT);
        return new StepResult(steps, key);
    }

    public AppiumUtil autoHandleNavigationGates(NavigationGates.Context context) {
       appiumUtilHelper.autoHandleNavigationGates(context);
        return this;
    }

    public List<String> navigate(String path, String clickKey, String contextKey, AssertionPrefix prefix) {
        List<String> steps = appiumUtilHelper.parseSteps(path);
        appiumUtilHelper.navigatePath(steps, clickKey, contextKey, prefix);
        return steps;
    }

    public void ifExistClickByKey(String key) {
        Optional.ofNullable(appiumUtilHelper.findElementByKeyWithoutAssert(key)).ifPresent(element -> {
            info(String.format(ELEMENT_CLICKED, key));
            element.click();
        });
        waitBySecond(1);
    }

    public AppiumUtil waitBySecond(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
            info(String.format(WAITED_SECONDS, seconds));
        } catch (InterruptedException e) {
            logErrorAndFail("❌ Thread sleep error", e);
        }
        return this;
    }

    public AppiumUtil waitUntilElementLoad(String key) {
        By by = selector.getElementInfoToBy(key);
        waitForElementToBePresence(by);
        Optional.ofNullable(appiumUtilHelper.findElement(by)).filter(WebElement::isDisplayed).ifPresentOrElse(el -> info(String.format(ELEMENT_VISIBLE, key)), () -> fail(String.format(ELEMENT_NOT_VISIBLE, key)));
        return this;
    }

    public WebElement safeFindElementAndWait(String key) {
        return appiumUtilHelper.handleFind(appiumUtilHelper::findElement, key);
    }

    public WebElement findElementSilent(String key) {
       return appiumUtilHelper.findElementSilent(key);
    }

    public List<WebElement> findElementsSilent(String key) {
        return  appiumUtilHelper.findElementsSilent(key);
    }

    public List<WebElement> safeFindElementsAndWait(String key) {
        return appiumUtilHelper.safeFindElementsAndWait(key);
    }

    public AppiumUtil clickElement(String key) {
        WebElement element = safeFindElementAndWait(key);
        if (element == null) return this;
        try {
            element.click();
            info("🖱️ Tıklandı → " + key);
        } catch (Exception e) {
            logErrorAndFail("❌ Elemente tıklanamadı: " + key, e);
        }
        return this;
    }

    public AppiumUtil clickWebElement(WebElement element) {
        element.click();
        return this;
    }

    public AppiumUtil clickLastElement(String key) {
        List<WebElement> elements = safeFindElementsAndWait(key);

        if (elements == null || elements.isEmpty()) {
            logErrorAndFail("⚠️ Element listesi boş, son elemana tıklanamadı: " + key, null);
            return this;
        }

        WebElement lastElement = elements.getLast();
        try {
            lastElement.click();
            info("🖱️ Son elemana tıklandı → " + key);
        } catch (Exception e) {
            logErrorAndFail("❌ Son elemana tıklanamadı: " + key, e);
        }
        return this;
    }

    public AppiumUtil clickElementWithScroll(String key) {
        WebElement element = appiumUtilHelper.scrollUntilVisible(key, 8);
        if (element == null) {
            logErrorAndFail("❌ Element not found after scrolling: " + key);
            return this;
        }
        try {
            element.click();
            info("🖱️ Tıklandı (scroll ile) → " + key);
            return this;
        } catch (Exception e) {
            logErrorAndFail("❌ Elemente tıklanamadı: " + key, e);
            return this;
        }
    }

    public AppiumUtil hideKeyboardIfNeeded() {
        Platform platform = Driver.getPlatformForThread();
        try {
            switch (platform) {
                case ANDROID -> appiumDriver.executeScript("mobile: hideKeyboard");
                case IOS -> appiumDriver.executeScript("mobile: tap", Map.of("x", 387, "y", 550));
            }
        } catch (Exception e) {
            debug("⚠️ Keyboard gizlenemedi (muhtemelen zaten gizli): " + e.getMessage());
        }
        return this;
    }

    public AppiumUtil clearAndFillInputWithScroll(String key, String text) {
        WebElement element = appiumUtilHelper.scrollUntilVisible(key, 8);
        if (element == null) {
            logErrorAndFail("❌ Element not found after scrolling: " + key);
            return this;
        }
        try {
            element.click();
            element.clear();
            element.sendKeys(text);
            hideKeyboardIfNeeded();
            info("⌨️ '" + key + "' alanına '" + text + "' yazıldı");
            return this;

        } catch (Exception e) {
            logErrorAndFail("❌ '" + key + "' alanına yazılamadı", e);
            return this;
        }
    }

    public AppiumUtil clearAndFillInput(String key, String text) {
        WebElement element = appiumUtilHelper.findElementSilent(key);
        if (element == null) {
            logErrorAndFail("❌ Element not found after scrolling: " + key);
            return this;
        }
        try {
            element.click();
            element.clear();
            element.sendKeys(text);
            info("⌨️ '" + key + "' alanına '" + text + "' yazıldı");
            return this;

        } catch (Exception e) {
            logErrorAndFail("❌ '" + key + "' alanına yazılamadı", e);
            return this;
        }
    }


    public List<String> getTextElements(String key, TextSource... order) {
        List<WebElement> elements = appiumUtilHelper.findElementsSilent(key);
        return elements.stream().map(e -> appiumUtilHelper.getElementTextSmart(e, order)).filter(text -> text != null && !text.isBlank()).toList();
    }

    public AppiumUtil repeat(int times, Runnable action) {
        IntStream.range(0, times).forEach(i -> action.run());
        return this;
    }

    public AppiumUtil clickByText(String key, String text) {
        appiumUtilHelper.clickByText(key, text);
        return  this;
    }

    public void clickByAnyText(String key, String[] texts) {
        for (String text : texts) {
            try {
                appiumUtilHelper.clickByText(key, text);
                return;
            } catch (Exception ignored) {
            }
        }
        throw new NoSuchElementException("None of the texts found for key=" + key + " → " + Arrays.toString(texts));
    }

    public AppiumUtil clickRandomElement(String key) {
        WebElement element = appiumUtilHelper.pickRandomElement(key);
        if (element == null) return this;
        try {
            element.click();
            log("🎲 Rastgele tıklanan element → " + key);
        } catch (Exception e) {
            logErrorAndFail("❌ Rastgele elemana tıklanamadı: " + key, e);
        }
        return this;
    }

    public String clickRandomElementGetText(String key, TextSource... order) {
        WebElement element = appiumUtilHelper.pickRandomElement(key);
        if (element == null) return null;
        String text = appiumUtilHelper.getElementTextSmart(element, order);
        try {
            element.click();
            log("🎲 Rastgele tıklanan element (text alındı) → " + text);
        } catch (Exception e) {
            logErrorAndFail("❌ Rastgele elemana tıklanamadı: " + key, e);
        }
        return text;
    }

    public String getTextElement(String key, TextSource... order) {
        WebElement element = findElementSilent(key);
        return appiumUtilHelper.getElementTextSmart(element, order);
    }

    public void verifyAssetsCurrencyToggle(String amountKey, String toggleClickKey) {
        String beforeAmount = getTextElement(amountKey);
        clickElement(toggleClickKey);
        String afterAmount = getTextElement(amountKey);
        ASSETS.runAssertion(beforeAmount, afterAmount);
    }

    public boolean elementExistsSilent(String key) {
        try {
            return appiumUtilHelper.withTempImplicitWaitResult(Duration.ofMillis(2500), () -> {
                By by = selector.getElementInfoToBy(key);
                return !appiumDriver.findElements(by).isEmpty();
            });
        } catch (Exception ignored) {
            return false;
        }
    }

    public AppiumUtil clearAndFillInputWithEnter(String key, String text) {
        WebElement element = safeFindElementAndWait(key);
        if (element == null) return this;
        try {
            element.click();
            element.clear();
            element.sendKeys(text + Keys.ENTER);
            info("⌨️ '" + key + "' alanına '" + text + "' yazıldı");
        } catch (Exception e) {
            logErrorAndFail("❌ '" + key + "' alanına yazılamadı", e);
        }
        return this;
    }

    public String generateNumber(int length) {
        var random = ThreadLocalRandom.current();
        var result = new StringBuilder(length);
        result.append(random.nextInt(1, 10));
        for (int i = 1; i < length; i++) {
            result.append(random.nextInt(10));
        }
        return result.toString();
    }

    public String findMinAmount(List<String> values) {
        return values.stream()
                .map(AppiumUtilHelper::toBigDecimal)
                .min(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO)
                .toPlainString();
    }

    public void elementsNotExists(String key) {
        appiumUtilHelper.withTempImplicitWait(Duration.ofMillis(2500), () -> {
            By by = selector.getElementInfoToBy(key);
            List<WebElement> elements = appiumDriver.findElements(by);
            if (!elements.isEmpty()) {
                logErrorAndFail("❌ Element SHOULD NOT exist but found → " + key + " (count=" + elements.size() + ")");
            }
            log("✅ " + key + " → no elements found as expected");
        });
    }

    public AppiumUtil selectFromListByText(String listKey, String expectedText) {
        appiumUtil.findElementsSilent(listKey)
                .stream()
                .filter(e -> expectedText.equalsIgnoreCase(e.getText()))
                .findFirst()
                .ifPresent(WebElement::click);
        return this;
    }

    public AppiumUtil swipeRightOnElementAndroid(WebElement element) {
        appiumUtilHelper.swipeOnElementAndroid(element, RIGHT);
        return this;
    }

    public AppiumUtil swipeLeftOnElementAndroid(WebElement element) {
        appiumUtilHelper.swipeOnElementAndroid(element, LEFT);
        return this;
    }

    public AppiumUtil swipeUpOnElementAndroid(WebElement element) {
        appiumUtilHelper.swipeOnElementAndroid(element, UP);
        return this;
    }
}
