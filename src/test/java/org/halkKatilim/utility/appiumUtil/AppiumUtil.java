package org.halkKatilim.utility.appiumUtil;

import io.appium.java_client.AppiumBy;
import lombok.*;
import org.halkKatilim.constant.SelectorInfo;
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
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.halkKatilim.pages.BasePages;

import static org.halkKatilim.utility.appiumUtil.AppiumUtilText.*;
import static org.halkKatilim.utility.assertionUtil.enums.AssertionKey.ASSETS;
import static org.halkKatilim.utility.helpers.FrameworkLogger.*;
import static org.testng.Assert.fail;

@RequiredArgsConstructor
public class AppiumUtil extends BasePages implements WaitConditions {

    public record StepResult(List<String> steps, AssertionKey key) {
    }

    public StepResult navigateWithAssertion(String path, String assertion, String clickKey, String contextKey, AssertionPrefix prefix) {
        List<String> steps = navigate(path, clickKey, contextKey, prefix);
        AssertionKey key = parseAssertion(assertion);
        autoHandleNavigationGates(NavigationGates.Context.DEFAULT);
        return new StepResult(steps, key);
    }

    public void withTempImplicitWait(Duration tempWait, Runnable action) {
        Duration original = appiumDriver.manage().timeouts().getImplicitWaitTimeout();
        try {
            appiumDriver.manage().timeouts().implicitlyWait(tempWait);
            action.run();
        } finally {
            appiumDriver.manage().timeouts().implicitlyWait(original);
        }
    }

    public <T> T withTempImplicitWaitResult(Duration tempWait, Supplier<T> action) {
        Duration original = appiumDriver.manage().timeouts().getImplicitWaitTimeout();
        try {
            appiumDriver.manage().timeouts().implicitlyWait(tempWait);
            return action.get();
        } finally {
            appiumDriver.manage().timeouts().implicitlyWait(original);
        }
    }

    public AppiumUtil autoHandleNavigationGates(NavigationGates.Context context) {
        EnumSet<NavigationGates.Gate> gates = context.getGates();
        withTempImplicitWait(Duration.ofMillis(2550), () -> {
            boolean gateFound = gates.stream().map(g -> selector.getElementInfoToBy(g.getKey())).filter(Objects::nonNull).anyMatch(by -> !appiumDriver.findElements(by).isEmpty());
            if (gateFound) {
                handleNavigationGates(gates);
            }
        });
        return this;
    }

    public List<String> navigate(String path, String clickKey, String contextKey, AssertionPrefix prefix) {
        List<String> steps = parseSteps(path);
        navigatePath(steps, clickKey, contextKey, prefix);
        return steps;
    }

    private void navigatePath(List<String> steps, String clickKey, String contextKey, AssertionPrefix prefix) {
        for (int i = 0; i < steps.size(); i++) {
            boolean doAssert = (i == 0);
            String option = steps.get(i);
            navigateSingle(clickKey, contextKey, prefix, option, doAssert);
            log("📌", "%s seçildi.", option);
        }
        log("📌", "Navigasyon tamamlandı: %s", String.join(" -> ", steps));
        autoHandleNavigationGates(NavigationGates.Context.DEFAULT);
    }

    private void navigateSingle(String clickKey, String contextKey, AssertionPrefix prefix, String option, boolean doAssert) {
        performNavigation(clickKey, contextKey, prefix, option, doAssert);
    }

    private void performNavigation(String clickKey, String contextKey, AssertionPrefix prefix, String option, boolean doAssert) {
        clickByText(clickKey, option);
        if (contextKey != null) {
            runContext.setProperty(contextKey, option);
        }
        if (doAssert) {
            AssertionKey.resolve(prefix, option).runAssertion();
        }
        log("🟢", "%s sayfasına başarıyla geçildi.", option);
    }

    public List<String> parseSteps(String path) {
        return Arrays.stream(path.split(">")).map(String::trim).toList();
    }

    public void ifExistClickByKey(String key) {
        Optional.ofNullable(findElementByKeyWithoutAssert(key)).ifPresent(element -> {
            info(String.format(ELEMENT_CLICKED, key));
            element.click();
        });
        waitBySecond(1);
    }

    public WebElement findElementByKeyWithoutAssert(String key) {
        SelectorInfo selectorInfo = selector.getSelectorInfo(key);
        return selectorInfo.getIndex() > 0 ? findElements(selectorInfo.getBy()).get(selectorInfo.getIndex()) : findElement(selectorInfo.getBy());
    }

    public WebElement findElement(By by) {
        return handleWait(() -> appiumFluentWait.until(appiumDriver -> {
            try {
                WebElement el = appiumDriver.findElement(by);
                return el.isDisplayed() ? el : null;
            } catch (Exception e) {
                return null;
            }
        }), el -> el.isDisplayed() && el.isEnabled(), ELEMENT_NOT_FOUND.formatted(by));
    }

    public List<WebElement> findElements(By by) {
        try {
            return appiumDriver.findElements(by).stream().limit(20).toList();
        } catch (Exception e) {
            return List.of();
        }
    }

    private <T> T handleWait(Supplier<T> action, Predicate<T> validator, String errorMessage) {
        try {
            T result = action.get();
            if (result == null || !validator.test(result)) {
                throw new NoSuchElementException(errorMessage);
            }
            return result;
        } catch (Exception e) {
            logErrorAndFail("❌ " + errorMessage, e);
            return null;
        }
    }

    public void waitBySecond(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
            info(String.format(WAITED_SECONDS, seconds));
        } catch (InterruptedException e) {
            logErrorAndFail("❌ Thread sleep error", e);
        }
    }

    public AppiumUtil waitUntilElementLoad(String key) {
        By by = selector.getElementInfoToBy(key);
        waitForElementToBePresence(by);
        Optional.ofNullable(findElement(by)).filter(WebElement::isDisplayed).ifPresentOrElse(el -> info(String.format(ELEMENT_VISIBLE, key)), () -> fail(String.format(ELEMENT_NOT_VISIBLE, key)));
        return this;
    }

    private <T> T handleFind(Function<By, T> finder, String key) {
        try {
            By by = selector.getSelectorInfo(key).getBy();
            return finder.apply(by);
        } catch (Exception e) {
            logErrorAndFail("⚠️ Element bulunamadı: " + key, e);
            return null;
        }
    }

    public WebElement safeFindElementAndWait(String key) {
        return handleFind(this::findElement, key);
    }

    public WebElement findElementSilent(String key) {
        By by = selector.getElementInfoToBy(key);
        List<WebElement> elements = appiumDriver.findElements(by);
        return elements.isEmpty() ? null : elements.getFirst();
    }

    public List<WebElement> findElementsSilent(String key) {
        try {
            return appiumDriver.findElements(selector.getElementInfoToBy(key));
        } catch (Exception e) {
            return List.of();
        }
    }

    public List<WebElement> safeFindElementsAndWait(String key) {
        return handleWait(() -> handleFind(this::findElements, key), list -> list != null && !list.isEmpty(), ELEMENT_LIST_NOT_FOUND.formatted(key));
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

    private WebElement scrollUntilVisible(String key, int maxScroll) {
        for (int i = 0; i < maxScroll; i++) {
            WebElement element = findElementSilent(key);
            if (element != null) {
                return element;
            }
            scrollDown();
        }
        return null;
    }

    public AppiumUtil clickElementWithScroll(String key) {
        WebElement element = scrollUntilVisible(key, 8);
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

    private void scrollDown() {
        if (Driver.getPlatformForThread() == Platform.ANDROID) {
            scrollDownAndroid();
        } else {
            scrollDownIOS();
        }
    }

    public AppiumUtil hideKeyboardIfNeeded() {
        Platform platform = Driver.getPlatformForThread();
        try {
            switch (platform) {
                case ANDROID -> appiumDriver.executeScript("mobile: hideKeyboard");
                case IOS -> {
                    appiumDriver.executeScript("mobile: tap", Map.of("x", 387, "y", 550));
                }
            }
        } catch (Exception e) {
            debug("⚠️ Keyboard gizlenemedi (muhtemelen zaten gizli): " + e.getMessage());
        }
        return this;
    }


    public AppiumUtil clearAndFillInputWithScroll(String key, String text) {

        WebElement element = scrollUntilVisible(key, 8);
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


    private static final TextSource[] DEFAULT_TEXT_ORDER = {TextSource.TEXT, TextSource.LABEL, TextSource.NAME, TextSource.CONTENT_DESC};

    private String resolveText(WebElement e, TextSource source) {
        try {
            return switch (source) {
                case TEXT -> e.getAttribute("text");
                case LABEL -> e.getAttribute("label");
                case NAME -> e.getAttribute("name");
                case CONTENT_DESC -> e.getAttribute("content-desc");
            };
        } catch (Exception ignored) {
            return null;
        }
    }

    public String getElementTextSmart(WebElement e, TextSource... order) {
        TextSource[] effectiveOrder = (order == null || order.length == 0) ? DEFAULT_TEXT_ORDER : order;
        for (TextSource source : effectiveOrder) {
            String value = resolveText(e, source);
            if (value != null && !value.isBlank()) {
                return value;
            }
        }
        return null;
    }

    public List<String> getTextElements(String key, TextSource... order) {
        List<WebElement> elements = findElementsSilent(key);
        return elements.stream().map(e -> getElementTextSmart(e, order)).filter(text -> text != null && !text.isBlank()).toList();
    }

    public AppiumUtil repeat(int times, Runnable action) {
        IntStream.range(0, times).forEach(i -> action.run());
        return this;
    }

    public AppiumUtil clickByText(String key, String text) {
        String expected = hardAssertion.normalizeText(text);
        Set<String> previousSnapshot = Set.of();
        String lastEdgeText = null;
        while (true) {
            List<WebElement> elements = resolveElements(key);
            Optional<WebElement> match = findMatchingElement(elements, expected);
            if (match.isPresent()) {
                match.get().click();
                log("✅ Clicked by text → " + text);
                return this;
            }
            String currentEdgeText = getLastVisibleText(elements);
            if (Objects.equals(lastEdgeText, currentEdgeText)) {
                logErrorAndFail("❌ Reached end, text not found: " + text);
                return this;
            }
            lastEdgeText = currentEdgeText;
            Set<String> currentSnapshot = snapshotOf(elements);
            if (!hasProgress(previousSnapshot, currentSnapshot)) {
                logErrorAndFail("❌ Text not found: " + text);
                return this;
            }
            previousSnapshot = currentSnapshot;
            scrollDown();
        }
    }

    private final Map<String, By> byCache = new ConcurrentHashMap<>();

    private List<WebElement> resolveElements(String key) {
        By by = byCache.computeIfAbsent(key, k -> selector.getSelectorInfo(k).getBy());
        try {
            return appiumDriver.findElements(by);
        } catch (Exception e) {
            return List.of();
        }
    }


    private Optional<WebElement> findMatchingElement(List<WebElement> elements, String expected, TextSource... order) {
        return elements.stream().filter(e -> {
            String actual = getElementTextSmart(e, order);
            return actual != null && expected.equalsIgnoreCase(hardAssertion.normalizeText(actual));
        }).findFirst();
    }

    private String getLastVisibleText(List<WebElement> elements, TextSource... order) {
        for (int i = elements.size() - 1; i >= 0; i--) {
            String text = getElementTextSmart(elements.get(i), order);
            if (text != null && !text.isBlank()) {
                return hardAssertion.normalizeText(text);
            }
        }
        return null;
    }

    private boolean hasProgress(Set<String> previous, Set<String> current) {
        if (previous.isEmpty()) return true;
        return !previous.equals(current);
    }

    public void clickByAnyText(String key, String[] texts) {
        for (String text : texts) {
            try {
                clickByText(key, text);
                return;
            } catch (Exception ignored) {
            }
        }
        throw new NoSuchElementException("None of the texts found for key=" + key + " → " + Arrays.toString(texts));
    }

    private void scrollDownAndroid() {
        Dimension size = appiumDriver.manage().window().getSize();
        appiumDriver.executeScript("mobile: scrollGesture", Map.of("left", size.width / 4, "top", size.height / 4, "width", size.width / 2, "height", size.height / 2, "direction", "down", "percent", 0.85));
    }

    private void scrollDownIOS() {
        appiumDriver.executeScript("mobile: swipe", Map.of("direction", "up"));
    }

    private Set<String> snapshotOf(List<WebElement> elements, TextSource... order) {
        return elements.stream().map(e -> getElementTextSmart(e, order)).filter(Objects::nonNull).map(hardAssertion::normalizeText).collect(Collectors.toSet());
    }

    private WebElement pickRandomElement(String key) {
        List<WebElement> elements = safeFindElementsAndWait(key);
        if (elements.isEmpty()) {
            logErrorAndFail("❌ Rastgele seçim için element listesi boş: " + key);
            return null;
        }
        return elements.get(ThreadLocalRandom.current().nextInt(elements.size()));
    }

    public AppiumUtil clickRandomElement(String key) {
        WebElement element = pickRandomElement(key);
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
        WebElement element = pickRandomElement(key);
        if (element == null) return null;
        String text = getElementTextSmart(element, order);
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
        return getElementTextSmart(element, order);
    }

    private AssertionKey parseKey(String rawKey) {
        String key = rawKey.trim();
        try {
            return AssertionKey.valueOf(key);
        } catch (Exception e) {
            throw new IllegalArgumentException("❌ Unknown assertion key: " + key);
        }
    }

    public AssertionKey parseAssertion(String value) {
        if (value == null || value.isBlank()) throw new IllegalArgumentException("❌ Assertion value cannot be empty");
        return parseKey(value);
    }

    public List<AssertionKey> parseAssertions(String value) {
        if (value == null || value.isBlank()) return List.of();
        String[] parts = value.split(",");
        List<AssertionKey> keys = new ArrayList<>(parts.length);
        for (String part : parts) {
            keys.add(parseKey(part));
        }
        return keys;
    }

    public void verifyAssetsCurrencyToggle(String amountKey, String toggleClickKey) {
        String beforeAmount = getTextElement(amountKey);
        clickElement(toggleClickKey);
        String afterAmount = getTextElement(amountKey);
        ASSETS.runAssertion(beforeAmount, afterAmount);
    }

    private void handleNavigationGates(EnumSet<NavigationGates.Gate> gates) {
        withTempImplicitWait(Duration.ZERO, () -> {
            final long timeout = System.currentTimeMillis() + 3050;
            while (System.currentTimeMillis() < timeout) {
                for (NavigationGates.Gate gate : gates) {
                    try {
                        By by = selector.getElementInfoToBy(gate.getKey());
                        if (by == null) {
                            continue;
                        }
                        List<WebElement> els = appiumDriver.findElements(by);
                        if (!els.isEmpty()) {
                            els.get(0).click();
                            log("🚪", "Gate kapatıldı → %s".formatted(gate.name()));
                            return;
                        }
                    } catch (Exception ignored) {
                    }
                }
                try {
                    Thread.sleep(120);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        });
    }

    public void elementsNotExists(String key) {
        withTempImplicitWait(Duration.ofMillis(2500), () -> {
            By by = selector.getElementInfoToBy(key);
            List<WebElement> elements = appiumDriver.findElements(by);
            if (!elements.isEmpty()) {
                logErrorAndFail("❌ Element SHOULD NOT exist but found → " + key + " (count=" + elements.size() + ")");
            }
            log("✅ " + key + " → no elements found as expected");
        });
    }

    public boolean elementExistsSilent(String key) {
        try {
            return withTempImplicitWaitResult(Duration.ofMillis(2500), () -> {
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
        return values.stream().map(AppiumUtil::toBigDecimal).min(BigDecimal::compareTo).orElse(BigDecimal.ZERO).toPlainString();
    }

    private static BigDecimal toBigDecimal(String value) {
        if (value == null || value.isBlank()) {
            return BigDecimal.ZERO;
        }
        int cutIndex = -1;
        for (int i = value.length() - 1; i >= 0; i--) {
            char c = value.charAt(i);
            if (c == '.' || c == ',') {
                cutIndex = i;
                break;
            }
        }
        StringBuilder digits = new StringBuilder();
        int limit = cutIndex == -1 ? value.length() : cutIndex;
        for (int i = 0; i < limit; i++) {
            char c = value.charAt(i);
            if (c >= '0' && c <= '9') {
                digits.append(c);
            }
        }
        return digits.isEmpty() ? BigDecimal.ZERO : new BigDecimal(digits.toString());
    }
}
