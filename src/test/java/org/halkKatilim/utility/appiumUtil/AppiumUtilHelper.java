package org.halkKatilim.utility.appiumUtil;


import lombok.NoArgsConstructor;
import org.halkKatilim.constant.SelectorInfo;
import org.halkKatilim.enums.NavigationGates;
import org.halkKatilim.enums.Platform;
import org.halkKatilim.enums.SwipeDirection;
import org.halkKatilim.enums.TextSource;
import org.halkKatilim.pages.BasePages;
import org.halkKatilim.utility.Driver;
import org.halkKatilim.utility.assertionUtil.enums.AssertionKey;
import org.halkKatilim.utility.assertionUtil.enums.AssertionPrefix;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.RemoteWebElement;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.halkKatilim.utility.appiumUtil.AppiumUtilText.ELEMENT_LIST_NOT_FOUND;
import static org.halkKatilim.utility.appiumUtil.AppiumUtilText.ELEMENT_NOT_FOUND;
import static org.halkKatilim.utility.helpers.FrameworkLogger.*;

@NoArgsConstructor
public class AppiumUtilHelper extends BasePages {

    <T> T handleWait(Supplier<T> action, Predicate<T> validator, String errorMessage) {
        try {
            return Optional.ofNullable(action.get())
                    .filter(validator)
                    .orElseThrow(() -> new NoSuchElementException(errorMessage));
        } catch (Exception e) {
            logErrorAndFail("❌ " + errorMessage, e);
            return null;
        }
    }

    <T> T handleFind(Function<By, T> finder, String key) {
        try {
            return Optional.ofNullable(selector.getSelectorInfo(key))
                    .map(SelectorInfo::getBy)
                    .map(finder)
                    .orElseThrow(() -> new NoSuchElementException("⚠️ Element bulunamadı: " + key));
        } catch (Exception e) {
            logErrorAndFail("⚠️ Element bulunamadı: " + key, e);
            return null;
        }
    }

    void scrollDown() {
        if (Driver.getPlatformForThread() == Platform.ANDROID) {
            scrollDownAndroid();
        } else {
            scrollDownIOS();
        }
    }

    private void scrollDownAndroid() {
        Dimension size = appiumDriver.manage().window().getSize();
        appiumDriver.executeScript("mobile: scrollGesture", Map.of("left", size.width / 4, "top", size.height / 4, "width", size.width / 2, "height", size.height / 2, "direction", "down", "percent", 0.85));
    }

    private void scrollDownIOS() {
        appiumDriver.executeScript("mobile: swipe", Map.of("direction", "up"));
    }

    void swipeLeftAndroid(WebElement element) {
        Rectangle r = element.getRect();
        appiumDriver.executeScript("mobile: swipeGesture", Map.of("left", r.getX(), "top", r.getY(), "width", r.getWidth(), "height", r.getHeight(), "direction", "left", "percent", 0.85));
    }

    void swipeLeftIOS(WebElement cell) {

        Rectangle rect = cell.getRect();

        int y = rect.y + rect.height / 2;
        int startX = rect.x + rect.width - 30;
        int endX = rect.x + 40;

        PointerInput finger =
                new PointerInput(PointerInput.Kind.TOUCH, "finger");

        Sequence seq = new Sequence(finger, 1);

        // parmak koy
        seq.addAction(finger.createPointerMove(
                Duration.ZERO,
                PointerInput.Origin.viewport(),
                startX, y));

        seq.addAction(finger.createPointerDown(
                PointerInput.MouseButton.LEFT.asArg()));

        // 🔥 KRİTİK — hafif bekle
        seq.addAction(new Pause(finger, Duration.ofMillis(180)));

        // yavaş çek
        seq.addAction(finger.createPointerMove(
                Duration.ofMillis(200),
                PointerInput.Origin.viewport(),
                endX, y));

        seq.addAction(finger.createPointerUp(
                PointerInput.MouseButton.LEFT.asArg()));

        appiumDriver.perform(List.of(seq));
    }


    static final TextSource[] DEFAULT_TEXT_ORDER = {TextSource.TEXT, TextSource.LABEL, TextSource.NAME, TextSource.CONTENT_DESC};

    String resolveText(WebElement e, TextSource source) {
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

    private final Map<String, By> byCache = new ConcurrentHashMap<>(128);

    List<WebElement> resolveElements(String key) {
        By by = byCache.computeIfAbsent(key, k -> selector.getSelectorInfo(k).getBy());
        try {
            return appiumDriver.findElements(by);
        } catch (Exception e) {
            return List.of();
        }
    }

    Optional<WebElement> findMatchingElement(List<WebElement> elements, String expected, TextSource... order) {
        return elements.stream()
                .filter(e -> {
                    String actual = getElementTextSmart(e, order);
                    return actual != null &&
                            expected.equalsIgnoreCase(hardAssertion.normalizeText(actual));
                })
                .findFirst();
    }

    String getLastVisibleText(List<WebElement> elements, TextSource... order) {
        return IntStream.range(0, elements.size())
                .mapToObj(i -> elements.get(elements.size() - 1 - i))
                .map(e -> getElementTextSmart(e, order))
                .filter(text -> text != null && !text.isBlank())
                .map(hardAssertion::normalizeText)
                .findFirst()
                .orElse(null);
    }

    String getElementTextSmart(WebElement e, TextSource... order) {
        TextSource[] effectiveOrder = (order == null || order.length == 0) ? DEFAULT_TEXT_ORDER : order;

        return Arrays.stream(effectiveOrder)
                .map(source -> resolveText(e, source))
                .filter(value -> value != null && !value.isBlank())
                .findFirst()
                .orElse(null);
    }

    AssertionKey parseKey(String rawKey) {
        String key = rawKey.trim();
        try {
            return AssertionKey.valueOf(key);
        } catch (Exception e) {
            return null;
            //throw new IllegalArgumentException("❌ Unknown assertion key: " + key);
        }
    }

    boolean hasProgress(Set<String> previous, Set<String> current) {
        return previous.isEmpty() || !previous.equals(current);
    }

    Set<String> snapshotOf(List<WebElement> elements, TextSource... order) {
        return elements.stream()
                .map(e -> getElementTextSmart(e, order))
                .filter(Objects::nonNull)
                .map(hardAssertion::normalizeText)
                .collect(Collectors.toSet());
    }

    WebElement pickRandomElement(String key) {
        try {
            return Optional.ofNullable(safeFindElementsAndWait(key))
                    .filter(list -> !list.isEmpty())
                    .map(list -> list.get(ThreadLocalRandom.current().nextInt(list.size())))
                    .orElseThrow(() -> new NoSuchElementException(key));
        } catch (Exception e) {
            logErrorAndFail("❌ Rastgele seçim için element listesi boş: " + key, e);
            return null;
        }
    }

    void navigatePath(List<String> steps, String clickKey, String contextKey, AssertionPrefix prefix) {
        AtomicBoolean first = new AtomicBoolean(true);
        steps.forEach(option -> {
            navigateSingle(clickKey, contextKey, prefix, option, first.getAndSet(false));
            log("📌", "%s seçildi.", option);
        });

        log("📌", "Navigasyon tamamlandı: %s", String.join(" -> ", steps));
        autoHandleNavigationGates(NavigationGates.Context.DEFAULT);
    }

    void autoHandleNavigationGates(NavigationGates.Context context) {
        EnumSet<NavigationGates.Gate> gates = context.getGates();
        withTempImplicitWait(Duration.ofMillis(2550), () -> {
            boolean gateFound = gates.stream()
                    .map(g -> selector.getElementInfoToBy(g.getKey()))
                    .filter(Objects::nonNull)
                    .anyMatch(by -> !appiumDriver.findElements(by).isEmpty());

            if (gateFound) {
                handleNavigationGates(gates);
            }
        });
    }

    void navigateSingle(String clickKey, String contextKey, AssertionPrefix prefix, String option, boolean doAssert) {
        performNavigation(clickKey, contextKey, prefix, option, doAssert);
    }

    void performNavigation(String clickKey, String contextKey, AssertionPrefix prefix, String option, boolean doAssert) {
        clickByText(clickKey, option);
        Optional.ofNullable(contextKey)
                .ifPresent(key -> runContext.setProperty(key, option));
        if (doAssert) {
            AssertionKey.resolve(prefix, option).runAssertion();
        }

        log("🟢", "%s sayfasına başarıyla geçildi.", option);
    }

    void handleNavigationGates(EnumSet<NavigationGates.Gate> gates) {
        withTempImplicitWait(Duration.ZERO, () ->
                waitUntilTimeout(3050, () -> tryHandleAnyGate(gates))
        );
    }

    private void waitUntilTimeout(long timeoutMs, Supplier<Boolean> action) {
        long end = System.currentTimeMillis() + timeoutMs;

        while (System.currentTimeMillis() < end) {
            if (action.get()) return;
            sleep(120);
        }
    }

    private boolean tryHandleAnyGate(EnumSet<NavigationGates.Gate> gates) {
        return gates.stream().anyMatch(this::tryHandleGate);
    }

    private boolean tryHandleGate(NavigationGates.Gate gate) {

        return Optional.ofNullable(selector.getElementInfoToBy(gate.getKey()))
                .map(by -> clickIfPresent(by, gate.name()))
                .orElse(false);
    }


    private boolean clickIfPresent(By by, String gateName) {

        return appiumDriver.findElements(by).stream()
                .findFirst()
                .map(el -> {
                    el.click();
                    log("🚪", "Gate kapatıldı → %s".formatted(gateName));
                    return true;
                })
                .orElse(false);
    }

    public void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }


    void withTempImplicitWait(Duration tempWait, Runnable action) {
        Duration original = appiumDriver.manage().timeouts().getImplicitWaitTimeout();
        try {
            appiumDriver.manage().timeouts().implicitlyWait(tempWait);
            action.run();
        } finally {
            appiumDriver.manage().timeouts().implicitlyWait(original);
        }
    }

    <T> T withTempImplicitWaitResult(Duration tempWait, Supplier<T> action) {
        Duration original = appiumDriver.manage().timeouts().getImplicitWaitTimeout();
        try {
            appiumDriver.manage().timeouts().implicitlyWait(tempWait);
            return action.get();
        } finally {
            appiumDriver.manage().timeouts().implicitlyWait(original);
        }
    }

    WebElement scrollUntilVisible(String key, int maxScroll) {
        return IntStream.range(0, maxScroll)
                .mapToObj(i -> Optional.ofNullable(findElementSilent(key))
                        .orElseGet(() -> {
                            scrollDown();
                            return null;
                        }))
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }

    WebElement scrollUntilTextVisible(String key, String targetText, int maxScroll) {
        return IntStream.range(0, maxScroll)
                .mapToObj(i -> {
                    List<WebElement> elements = appiumUtil.safeFindElementsAndWait(key);
                    Optional<WebElement> match = elements.stream()
                            .filter(e -> targetText.equals(e.getText()))
                            .findFirst();
                    if (match.isPresent()) {
                        try {
                            if (match.get().isDisplayed()) {
                                return match.get();
                            }
                        } catch (Exception ignored) {}
                    }
                    scrollDown();
                    return null;
                })
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }

    static BigDecimal toBigDecimal(String value) {
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

    List<String> parseSteps(String path) {
        return Arrays.stream(path.split(">"))
                .map(String::trim)
                .toList();
    }

    WebElement findElementByKeyWithoutAssert(String key) {
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

    WebElement findElementSilent(String key) {
        By by = selector.getElementInfoToBy(key);
        List<WebElement> elements = appiumDriver.findElements(by);
        return elements.isEmpty() ? null : elements.getFirst();
    }

    List<WebElement> findElementsSilent(String key) {
        try {
            return appiumDriver.findElements(selector.getElementInfoToBy(key));
        } catch (Exception e) {
            return List.of();
        }
    }

    List<WebElement> safeFindElementsAndWait(String key) {
        return handleWait(() -> handleFind(this::findElements, key), list -> list != null
                && !list.isEmpty(), ELEMENT_LIST_NOT_FOUND.formatted(key));
    }

    void clickByText(String key, String text) {
        String expected = hardAssertion.normalizeText(text);
        Set<String> previousSnapshot = Set.of();
        String lastEdgeText = null;
        while (true) {
            List<WebElement> elements = resolveElements(key);
            Optional<WebElement> match = findMatchingElement(elements, expected);
            if (match.isPresent()) {
                match.get().click();
                log("✅ Clicked by text → " + text);
                break;
            }
            String currentEdgeText = getLastVisibleText(elements);
            if (Objects.equals(lastEdgeText, currentEdgeText)) {
                logErrorAndFail("❌ Reached end, text not found: " + text);
                break;
            }
            lastEdgeText = currentEdgeText;
            Set<String> currentSnapshot = snapshotOf(elements);
            if (!hasProgress(previousSnapshot, currentSnapshot)) {
                logErrorAndFail("❌ Text not found: " + text);
                break;
            }
            previousSnapshot = currentSnapshot;
            scrollDown();
        }
    }

    void swipeOnElementAndroid(WebElement element, SwipeDirection direction) {

        Rectangle r = element.getRect();

        Map<String, Object> params = new HashMap<>();
        params.put("left", r.getX());
        params.put("top", r.getY());
        params.put("width", r.getWidth());
        params.put("height", r.getHeight());
        params.put("direction", direction.name().toLowerCase());
        params.put("percent", 0.75);

        appiumDriver.executeScript("mobile: swipeGesture", params);

        info("👉 Swipe " + direction + " executed via mobile: swipeGesture");
    }
}
