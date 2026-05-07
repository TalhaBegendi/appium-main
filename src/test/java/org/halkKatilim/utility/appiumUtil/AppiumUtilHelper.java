package org.halkKatilim.utility.appiumUtil;

import io.appium.java_client.AppiumDriver;
import org.halkKatilim.constant.SelectorInfo;
import org.halkKatilim.enums.NavigationGates;
import org.halkKatilim.enums.Platform;
import org.halkKatilim.enums.TextSource;
import org.halkKatilim.selector.Selector;
import org.halkKatilim.utility.Driver;
import org.halkKatilim.utility.assertionUtil.types.HardAssertion;
import org.halkKatilim.utility.context.ExecutionContext;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.FluentWait;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import static org.halkKatilim.utility.appiumUtil.AppiumUtilText.ELEMENT_NOT_FOUND;
import static org.halkKatilim.utility.helpers.FrameworkLogger.*;


public class AppiumUtilHelper {

    AppiumDriver driver() { return ExecutionContext.getDriver();}

    Selector selector() {
        return ExecutionContext.getSelector();
    }

    FluentWait<AppiumDriver> waits() { return ExecutionContext.getFluentWait();}

    HardAssertion getAssertion() {
        return ExecutionContext.getAssertion();
    }

    By getElementInfoToBy(String key) {
        return BY_CACHE.computeIfAbsent(key, k -> selector().getSelectorInfo(k).getBy());
    }

    public void hideKeyboard(Platform platform) {
        try {
            switch (platform) {
                case ANDROID -> driver().executeScript("mobile: hideKeyboard");
                case IOS -> driver().executeScript("mobile: tap", Map.of("x", 11, "y", 476));
            }
        } catch (Exception e) {
            debug("⚠️ Keyboard gizlenemedi: " + e.getMessage());
        }
    }

    private final Map<String, By> BY_CACHE = new ConcurrentHashMap<>(256);

    static final TextSource[] DEFAULT_TEXT_ORDER = {
            TextSource.TEXT, TextSource.LABEL, TextSource.NAME, TextSource.CONTENT_DESC
    };

    String resolveText(WebElement e, TextSource source) {
        Platform platform = Driver.getPlatformForThread();
        try {
            if (platform == Platform.ANDROID) {
                if (source == TextSource.TEXT) return e.getAttribute("text");
                if (source == TextSource.CONTENT_DESC) return e.getAttribute("content-desc");
                return null;
            } else {
                if (source == TextSource.LABEL) return e.getAttribute("label");
                if (source == TextSource.NAME) return e.getAttribute("name");
                if (source == TextSource.TEXT) return e.getAttribute("value");
                return null;
            }
        } catch (Exception ignored) { return null; }
    }

    String getElementTextSmart(WebElement e, TextSource... order) {
        TextSource[] effectiveOrder = (order == null || order.length == 0) ? DEFAULT_TEXT_ORDER : order;
        return Arrays.stream(effectiveOrder)
                .map(source -> resolveText(e, source))
                .filter(value -> value != null && !value.isBlank())
                .findFirst()
                .orElse(null);
    }


    List<WebElement> resolveElements(String key) {
        try {
            return driver().findElements(getElementInfoToBy(key));
        } catch (Exception e) {
            return List.of();
        }
    }


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
            return Optional.ofNullable(selector().getSelectorInfo(key))
                    .map(SelectorInfo::getBy)
                    .map(finder)
                    .orElseThrow(() -> new NoSuchElementException("⚠️ Element bulunamadı: " + key));
        } catch (Exception e) {
            logErrorAndFail("⚠️ Element bulunamadı: " + key, e);
            return null;
        }
    }

    public WebElement findElement(By by) {
        return handleWait(
                () -> waits().until(d -> {
                    try {
                        WebElement el = d.findElement(by);
                        return el.isDisplayed() ? el : null;
                    } catch (Exception e) {
                        return null;
                    }
                }),
                el -> el.isDisplayed() && el.isEnabled(),
                ELEMENT_NOT_FOUND.formatted(by)
        );
    }

    public List<WebElement> findElements(By by) {
        try {
            return driver().findElements(by).stream().limit(20).toList();
        } catch (Exception e) {
            return List.of();
        }
    }

    WebElement findElementSilent(String key) {
        By by = selector().getElementInfoToBy(key);
        List<WebElement> elements = driver().findElements(by);
        return elements.isEmpty() ? null : elements.getFirst();
    }

    List<WebElement> findElementsSilent(String key) {
        try {
            return driver().findElements(selector().getElementInfoToBy(key));
        } catch (Exception e) {
            return List.of();
        }
    }

    List<WebElement> safeFindElementsAndWait(String key) {
        return waits().until(driver -> {
            List<WebElement> list = handleFind(this::findElements, key);
            return (list != null && !list.isEmpty())
                    ? list
                    : null;
        });
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


    <T> T withTempImplicit(Duration tempWait, Supplier<T> action) {
        Duration original = driver().manage().timeouts().getImplicitWaitTimeout();
        try {
            driver().manage().timeouts().implicitlyWait(tempWait);
            return action.get();
        } finally {
            driver().manage().timeouts().implicitlyWait(original);
        }
    }

    void withTempImplicit(Duration tempWait, Runnable action) {
        withTempImplicit(tempWait, () -> { action.run(); return null; });
    }

    void scrollDown() {
        if (Driver.getPlatformForThread() == Platform.ANDROID) {
            scrollDownAndroid();
        } else {
            scrollDownIOS();
        }
    }

    private void scrollDownAndroid() {
        Dimension size = driver().manage().window().getSize();
        driver().executeScript("mobile: scrollGesture", Map.of(
                "left", size.width / 4,
                "top", size.height / 4,
                "width", size.width / 2,
                "height", size.height / 2,
                "direction", "down",
                "percent", 0.85
        ));
    }

    private void scrollDownIOS() {
        driver().executeScript("mobile: swipe", Map.of("direction", "up"));
    }

    WebElement scrollUntilVisible(String key, int maxScroll) {
        return IntStream.range(0, maxScroll)
                .mapToObj(i -> Optional.ofNullable(findElementSilent(key))
                        .orElseGet(() -> { scrollDown(); return null; }))
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }


    WebElement scrollUntilTextVisible(String key, String targetText, int maxScroll) {
        return IntStream.range(0, maxScroll)
                .mapToObj(i -> {
                    Optional<WebElement> match = findElementsSilent(key).stream()
                            .filter(e -> targetText.equals(e.getText()))
                            .findFirst();
                    if (match.isPresent()) {
                        try {
                            if (match.get().isDisplayed()) return match.get();
                        } catch (Exception ignored) {}
                    }
                    scrollDown();
                    return null;
                })
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }


    void swipeLeftAndroid(WebElement element) {
        Rectangle r = element.getRect();
        driver().executeScript("mobile: swipeGesture", Map.of(
                "left", r.getX(),
                "top", r.getY(),
                "width", r.getWidth(),
                "height", r.getHeight(),
                "direction", "left",
                "percent", 0.85
        ));
    }

    void swipeLeftIOS(WebElement element) {
        driver().executeScript("mobile: swipe", Map.of(
                "element", ((RemoteWebElement) element).getId(),
                "direction", "left"
        ));
    }

    void autoHandleNavigationGates(NavigationGates.Context context) {
        List<NavigationGates.Gate> gates = context.getGates()
                .stream()
                .toList();
        withTempImplicit(Duration.ZERO, () -> {
            long end = System.currentTimeMillis() + 2550;
            while (System.currentTimeMillis() < end) {
                Optional<NavigationGates.Gate> foundGate = gates.stream()
                        .filter(gate -> {
                            By by = selector().getElementInfoToBy(gate.getKey());
                            return by != null && !driver().findElements(by).isEmpty();
                        })
                        .findFirst();
                if (foundGate.isPresent()) {
                    handleNavigationGates(EnumSet.of(foundGate.get()));
                    return;
                }
                sleep(250);
            }});
    }

    void handleNavigationGates(EnumSet<NavigationGates.Gate> gates) {
        withTempImplicit(Duration.ZERO, () ->
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
        return Optional.ofNullable(getElementInfoToBy(gate.getKey()))
                .map(by -> clickIfPresent(by, gate.name()))
                .orElse(false);
    }

    private boolean clickIfPresent(By by, String gateName) {
        return driver().findElements(by).stream()
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

    boolean hasProgress(Set<String> previous, Set<String> current) {
        return previous.isEmpty() || !previous.equals(current);
    }

    List<String> parseSteps(String path) {
        return Arrays.stream(path.split(">"))
                .map(String::trim)
                .toList();
    }


    void clickByText(String key, String text) {
        String expected = getAssertion().normalizeText(text);
        Set<String> previousSnapshot = Set.of();
        String lastEdgeText = null;
        while (true) {
            List<WebElement> elements = resolveElements(key);
            if (elements.isEmpty()) {
                logErrorAndFail("❌ Element list empty: " + key);
                return;
            }
            Optional<WebElement> match = findMatchingElement(elements, expected);
            if (match.isPresent()) {
                match.get().click();
                log("✅ Clicked by text → " + text);
                return;
            }
            List<String> texts = extractNormalizedTexts(elements);
            String currentEdgeText = texts.isEmpty()
                    ? null
                    : texts.getLast();
            if (Objects.equals(lastEdgeText, currentEdgeText)) {
                logErrorAndFail("❌ Reached end, text not found: " + text);
                return;
            }
            lastEdgeText = currentEdgeText;
            Set<String> currentSnapshot = new HashSet<>(texts);
            if (!hasProgress(previousSnapshot, currentSnapshot)) {
                logErrorAndFail("❌ Text not found (no progress): " + text);
                return;
            }
            previousSnapshot = currentSnapshot;
            scrollDown();
        }
    }

    private List<String> extractNormalizedTexts(List<WebElement> elements) {
        List<String> texts = new ArrayList<>(elements.size());
        for (WebElement element : elements) {
            String text = getElementTextSmart(element);
            if (text == null || text.isBlank()) {
                continue;
            }
            texts.add(getAssertion().normalizeText(text));
        }
        return texts;
    }

    private Optional<WebElement> findMatchingElement(List<WebElement> elements, String expected) {
        for (WebElement element : elements) {
            String text = getElementTextSmart(element);
            if (text == null || text.isBlank()) {
                continue;
            }
            String normalized = getAssertion().normalizeText(text);
            if (expected.equals(normalized)) {
                return Optional.of(element);
            }
        }
        return Optional.empty();
    }

    static BigDecimal toBigDecimal(String value) {
        if (value == null || value.isBlank()) return BigDecimal.ZERO;
        String digitsOnly = value.replaceAll("[^0-9.,]", "");
        int lastDot   = digitsOnly.lastIndexOf('.');
        int lastComma = digitsOnly.lastIndexOf(',');
        int decIdx    = Math.max(lastDot, lastComma);
        String intPart = (decIdx == -1 ? digitsOnly : digitsOnly.substring(0, decIdx))
                .replaceAll("[^0-9]", "");
        return intPart.isEmpty() ? BigDecimal.ZERO : new BigDecimal(intPart);
    }
}
