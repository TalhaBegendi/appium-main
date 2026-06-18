package org.halkKatilim.utility.appiumUtil;

import lombok.RequiredArgsConstructor;
import org.halkKatilim.enums.NavigationGates;
import org.halkKatilim.enums.TextSource;
import org.halkKatilim.utility.Driver;
import org.halkKatilim.utility.WaitConditions;
import org.halkKatilim.utility.assertionUtil.types.HardAssertion;
import org.openqa.selenium.*;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.*;
import java.util.NoSuchElementException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.IntStream;
import static org.halkKatilim.utility.appiumUtil.AppiumUtilText.*;
import static org.halkKatilim.utility.assertionUtil.enums.AssertionKey.ASSETS;
import static org.halkKatilim.utility.helpers.FrameworkLogger.*;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

@RequiredArgsConstructor
public class AppiumUtil implements WaitConditions {

    private final AppiumUtilHelper helper;

    public AppiumUtil() {
        this(new AppiumUtilHelper());
    }

    public HardAssertion getAssertion() {
        return helper.getAssertion();
    }

    public void assertElementTextContainsAny(WebElement element, String... expectedParts) {
        String actualText = element.getText().trim();
        assertTrue(Arrays.stream(expectedParts).anyMatch(actualText::contains),
                "Actual text [" + actualText + "] does not contain any expected values");
    }

    public void navigate(String path, String clickKey) {
        List<String> steps = helper.parseSteps(path);
        for (String step : steps) {
            helper.clickByText(clickKey, step);
        }
        helper.autoHandleNavigationGates(NavigationGates.Context.DEFAULT);
        info("🟢 Navigasyon tamamlandı: " + path);
    }

    public AppiumUtil autoHandleNavigationGates(NavigationGates.Context context) {
        helper.autoHandleNavigationGates(context);
        return this;
    }

    public AppiumUtil waitBySecond(int seconds) {
        helper.sleep(seconds * 1000L);
        info(String.format(WAITED_SECONDS, seconds));
        return this;
    }

    public AppiumUtil waitUntilElementLoad(String key) {
        for (int i = 0; i < 7; i++) {
            boolean isVisible = helper.withTempImplicit(Duration.ZERO, () -> {
                try {
                    WebElement el = helper.findElementSilent(key);
                    return el != null && el.isDisplayed();
                } catch (Exception e) {
                    return false;
                }
            });
            if (isVisible) {
                info(String.format(ELEMENT_VISIBLE, key));
                return this;
            }
            sleep(1300);
        }
        fail(String.format(ELEMENT_NOT_VISIBLE, key));
        return this;
    }

    public AppiumUtil sleep(long ms) {
        helper.sleep(ms);
        return this;
    }


    public WebElement safeFindElementAndWait(String key) {
        return helper.handleFind(helper::findElement, key);
    }

    public WebElement findElementSilent(String key) {
        return helper.findElementSilent(key);
    }

    public List<WebElement> findElementsSilent(String key) {
        return helper.findElementsSilent(key);
    }

    public List<WebElement> safeFindElementsAndWait(String key) {
        return helper.safeFindElementsAndWait(key);
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

    public AppiumUtil clickElementWithScroll(String key) {
        WebElement element = helper.scrollUntilVisible(key, 8);
        if (element == null) {
            logErrorAndFail("❌ Element not found after scrolling: " + key);
            return this;
        }
        try {
            element.click();
            info("🖱️ Tıklandı (scroll ile) → " + key);
        } catch (Exception e) {
            logErrorAndFail("❌ Elemente tıklanamadı: " + key, e);
        }
        return this;
    }

    public AppiumUtil clickElementTextWithScroll(String key, String targetText) {
        WebElement element = helper.scrollUntilTextVisible(key, targetText, 8);
        if (element == null) {
            logErrorAndFail("❌ Element not found after scrolling → key: " + key + " text: " + targetText);
            return this;
        }
        try {
            element.click();
            info("🖱️ Tıklandı (scroll + text) → " + targetText);
        } catch (Exception e) {
            logErrorAndFail("❌ Elemente tıklanamadı → " + targetText, e);
        }
        return this;
    }

    public AppiumUtil clickByText(String key, String text) {
        helper.clickByText(key, text);
        return this;
    }

    public void clickByAnyText(String key, String[] texts) {
        for (String text : texts) {
            try {
                helper.clickByText(key, text);
                return;
            } catch (Exception ignored) {
            }
        }
        throw new NoSuchElementException(
                "None of the texts found for key=" + key + " → " + Arrays.toString(texts));
    }

    public AppiumUtil clickRandomElement(String key) {
        WebElement element = helper.pickRandomElement(key);
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
        WebElement element = helper.pickRandomElement(key);
        if (element == null) return null;
        String text = helper.getElementTextSmart(element, order);
        try {
            element.click();
            log("🎲 Rastgele tıklanan element (text alındı) → " + text);
        } catch (Exception e) {
            logErrorAndFail("❌ Rastgele elemana tıklanamadı: " + key, e);
        }
        return text;
    }

    public AppiumUtil selectFromListByText(String listKey, String expectedText) {
        return selectFromList(listKey, expectedText, WebElement::getText);
    }

    public AppiumUtil selectFromListByLabel(String listKey, String expectedName) {
        return selectFromList(listKey, expectedName, element -> element.getAttribute("label"));
    }

    private AppiumUtil selectFromList(String listKey, String expectedValue, Function<WebElement, String> valueProvider) {
        safeFindElementsAndWait(listKey)
                .stream()
                .filter(item -> normalize(valueProvider.apply(item)).equalsIgnoreCase(expectedValue))
                .findFirst()
                .ifPresent(WebElement::click);
        return this;
    }

    private String normalize(String value) {
        return value == null ? "" : value.replace('\u00A0', ' ').trim();
    }

    public AppiumUtil fillInputKeyboard(String key, String text, boolean scroll, boolean hideKeyboard) {
        WebElement element = scroll
                ? helper.scrollUntilVisible(key, 8)
                : helper.findElementSilent(key);

        if (element == null) {
            logErrorAndFail("❌ Element not found: " + key);
            return this;
        }
        fillInput(element, text, hideKeyboard);
        return this;
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

    public AppiumUtil clearAndFillInputSmart(String key, String value) {
        return switch (Driver.getPlatformForThread()) {
            case IOS -> fillInputKeyboard(key, value, false, true);
            case ANDROID -> fillInputKeyboard(key, value, false, false);
        };
    }

    private void fillInput(WebElement element, String text, boolean hideKeyboard) {
        element.click();
        element.clear();
        element.sendKeys(text);
        if (hideKeyboard) hideKeyboardIfNeeded();
    }

    public AppiumUtil hideKeyboardIfNeeded() {
        helper.hideKeyboard(Driver.getPlatformForThread());
        return this;
    }

    public AppiumUtil scrollToBottom(int count) {
        IntStream.range(0, count).forEach(i -> helper.scrollDown());
        return this;
    }

    public AppiumUtil swipeLeftOnElementAndroid(WebElement element) {
        helper.swipeLeftAndroid(element);
        return this;
    }

    public AppiumUtil swipeLeftOnElementIOS(WebElement element) {
        helper.swipeLeftIOS(element);
        return this;
    }

    public String getTextElement(String key, TextSource... order) {
        return helper.getElementTextSmart(findElementSilent(key), order);
    }

    public List<String> getTextElements(String key, TextSource... order) {
        return helper.findElementsSilent(key).stream()
                .map(e -> helper.getElementTextSmart(e, order))
                .filter(text -> text != null && !text.isBlank())
                .toList();
    }

    public void elementsNotExists(String key) {
        helper.withTempImplicit(Duration.ofMillis(2500), () -> {
            List<WebElement> elements = helper.driver().findElements(helper.getElementInfoToBy(key));
            if (!elements.isEmpty()) {
                logErrorAndFail("❌ Element SHOULD NOT exist but found → " + key
                        + " (count=" + elements.size() + ")");
            }
            log("✅ " + key + " → no elements found as expected");
        });
    }

    public void verifyAssetsCurrencyToggle(String amountKey, String toggleClickKey) {
        String beforeAmount = getTextElement(amountKey);
        clickElement(toggleClickKey);
        String afterAmount = getTextElement(amountKey);
        ASSETS.runAssertion(beforeAmount, afterAmount);
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
}
