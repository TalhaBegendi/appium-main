package org.halkKatilim.utility.assertionUtil.types;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.halkKatilim.enums.TextSource;
import org.halkKatilim.utility.appiumUtil.AppiumUtil;
import org.halkKatilim.utility.assertionUtil.enums.AssertionKey;
import org.openqa.selenium.WebElement;
import java.util.*;
import java.util.stream.Collectors;
import static org.halkKatilim.utility.helpers.FrameworkLogger.error;
import static org.halkKatilim.utility.helpers.FrameworkLogger.info;
import static org.halkKatilim.utility.helpers.FrameworkLogger.log;
import static org.halkKatilim.utility.helpers.FrameworkLogger.logErrorAndFail;
import static org.testng.Assert.*;
import static java.lang.String.format;
import static org.halkKatilim.utility.assertionUtil.types.AssertionText.*;

@RequiredArgsConstructor
public class HardAssertion {

    @Getter
    private final AppiumUtil appiumUtil;

    public String normalizeText(String text) {
        if (text == null) return null;
        return text.trim().replace("\u00A0", " ").replaceAll("\\s+", " ");
    }

    public void hardAssertEquals(Object actual, Object expected) {
        String normActual = normalizeText(String.valueOf(actual));
        String normExpected = normalizeText(String.valueOf(expected));
        hardAssertEquals(normActual, normExpected, format(ASSERT_EQUALS_DEFAULT_MESSAGE, normExpected, normActual));
    }

    public void hardAssertEquals(Object actual, Object expected, String message) {
        info(format(ASSERT_EQUALS_LOG, message, actual, expected));
        try {
            assertEquals(actual, expected, message);
        } catch (AssertionError e) {
            error(format(ASSERT_EQUALS_LOG, message, actual, expected));
            throw e;
        }
    }

    public void hardAssertNotEquals(Object actual, Object unexpected) {
        hardAssertNotEquals(actual, unexpected, ASSERT_NOT_EQUALS_DEFAULT_MESSAGE);
    }

    public void hardAssertNotEquals(Object actual, Object unexpected, String message) {
        info(format(ASSERT_NOT_EQUALS_LOG, message, actual, unexpected));
        try {
            assertNotEquals(actual, unexpected, message);
        } catch (AssertionError e) {
            error(format(ASSERT_NOT_EQUALS_LOG, message, actual, unexpected));
            throw e;
        }
    }

    public static void hardAssertTrue(boolean condition) {
        hardAssertTrue(condition, ASSERT_TRUE_DEFAULT_MESSAGE);
    }

    public static void hardAssertTrue(boolean condition, String message) {
        info(format(ASSERT_CONDITION_LOG, message, condition));
        try {
            assertTrue(condition, message);
        } catch (AssertionError e) {
            error(format(ASSERT_CONDITION_LOG, message, condition));
            throw e;
        }
    }

    public static void hardAssertFalse(boolean condition) {
        hardAssertFalse(condition, ASSERT_FALSE_DEFAULT_MESSAGE);
    }

    public static void hardAssertFalse(boolean condition, String message) {
        info(format(ASSERT_CONDITION_LOG, message, condition));
        try {
            assertFalse(condition, message);
        } catch (AssertionError e) {
            error(format(ASSERT_CONDITION_LOG, message, condition));
            throw e;
        }
    }

    public static void hardAssertNull(Object object) {
        hardAssertNull(object, ASSERT_NULL_DEFAULT_MESSAGE);
    }

    public static void hardAssertNull(Object object, String message) {
        info(format(ASSERT_NULL_LOG, message, object));
        try {
            assertNull(object, message);
        } catch (AssertionError e) {
            error(format(ASSERT_NULL_LOG, message, object));
            throw e;
        }
    }

    public static void hardAssertNotNull(Object object) {
        hardAssertNotNull(object, ASSERT_NOT_NULL_DEFAULT_MESSAGE);
    }

    public static void hardAssertNotNull(Object object, String message) {
        info(format(ASSERT_NULL_LOG, message, object));
        try {
            assertNotNull(object, message);
        } catch (AssertionError e) {
            error(format(ASSERT_NULL_LOG, message, object));
            throw e;
        }
    }

    public static void hardAssertContains(String actual, String expectedSubstring) {
        hardAssertContains(actual, expectedSubstring, ASSERT_CONTAINS_DEFAULT_MESSAGE);
    }

    public static void hardAssertContains(String actual, String expectedSubstring, String message) {
        info(format(ASSERT_CONTAINS_LOG, message, actual, expectedSubstring));
        try {
            assertTrue(actual != null && actual.contains(expectedSubstring), message);
        } catch (AssertionError e) {
            error(format(ASSERT_CONTAINS_LOG, message, actual, expectedSubstring));
            throw e;
        }
    }

    public static void hardAssertGreaterThan(int actual, int threshold) {
        hardAssertGreaterThan(actual, threshold, ASSERT_GREATER_THAN_LOG);
    }

    public static void hardAssertGreaterThan(int actual, int threshold, String message) {
        info(format(ASSERT_GREATER_THAN_LOG, message, actual, threshold));
        try {
            assertTrue(actual > threshold, message);
        } catch (AssertionError e) {
            error(format(ASSERT_GREATER_THAN_LOG, message, actual, threshold));
            throw e;
        }
    }

    public static void hardAssertLessThan(int actual, int threshold) {
        hardAssertLessThan(actual, threshold, ASSERT_LESS_THAN_LOG);
    }

    public static void hardAssertLessThan(int actual, int threshold, String message) {
        info(format(ASSERT_LESS_THAN_LOG, message, actual, threshold));
        try {
            assertTrue(actual < threshold, message);
        } catch (AssertionError e) {
            error(format(ASSERT_LESS_THAN_LOG, message, actual, threshold));
            throw e;
        }
    }

    public static void hardAssertNotEmpty(String str) {
        hardAssertNotEmpty(str, ASSERT_NOT_EMPTY_STRING_LOG);
    }

    public static void hardAssertNotEmpty(String str, String message) {
        info(format(ASSERT_NOT_EMPTY_STRING_LOG, message, str));
        try {
            assertTrue(str != null && !str.isEmpty(), message);
        } catch (AssertionError e) {
            error(format(ASSERT_NOT_EMPTY_STRING_LOG, message, str));
            throw e;
        }
    }

    public static void hardAssertNotEmpty(Collection<?> collection) {
        hardAssertNotEmpty(collection, ASSERT_NOT_EMPTY_COLLECTION_LOG);
    }

    public static void hardAssertNotEmpty(Collection<?> collection, String message) {
        info(format(ASSERT_NOT_EMPTY_COLLECTION_LOG, message, collection));
        try {
            assertTrue(collection != null && !collection.isEmpty(), message);
        } catch (AssertionError e) {
            error(format(ASSERT_NOT_EMPTY_COLLECTION_LOG, message, collection));
            throw e;
        }
    }

    public boolean assertElementExistsSilent(String key) {
        return appiumUtil.elementExistsSilent(key);
    }

    public void assertElementsExists(String key) {
        List<WebElement> elements = appiumUtil.safeFindElementsAndWait(key);
        assertFalse(elements.isEmpty(), "❌ Element not found for key: " + key);
        log("✅ Element(s) found for key: " + key + " (count=" + elements.size() + ")");
    }

    public void assertElementExist(String key) {
        WebElement element = appiumUtil.safeFindElementAndWait(key);
        assertNotNull(element, "❌ Element not found for key: " + key);
        log("✅ Element found for key: " + key);
    }

    public void assertElementsNotExists(String key) {
        appiumUtil.elementsNotExists(key);
    }

    public void verifyText(List<String> steps, AssertionKey key, TextSource... order) {
        Set<String> expected = steps.stream().map(this::normalizeText).filter(Objects::nonNull).collect(Collectors.toSet());
        Set<String> actual = appiumUtil.getTextElements(key.getElementKey(), order).stream().map(this::normalizeText).filter(Objects::nonNull).collect(Collectors.toSet());
        Set<String> intersection = new HashSet<>(actual);
        intersection.retainAll(expected);
        if (!intersection.isEmpty()) return;
        if (actual.stream().anyMatch(text -> matchesDisplayText(text, key))) return;
        AssertionKey fallback = key.getFallback();
        if (fallback != null) {
            Set<String> fallbackActual = appiumUtil.getTextElements(fallback.getElementKey(), order).stream().map(this::normalizeText).filter(Objects::nonNull).collect(Collectors.toSet());
            if (fallbackActual.stream().anyMatch(text -> matchesDisplayText(text, fallback))) return;
        }
        logErrorAndFail("❌ TEXT assertion failed → expected ANY of: %s → actual: %s".formatted(expected, actual));
    }

    private boolean matchesDisplayText(String actual, AssertionKey key) {
        if (key.getDisplayText() == null || key.getDisplayText().getTexts() == null) return false;
        return Arrays.stream(key.getDisplayText().getTexts())
                .map(this::normalizeText)
                .anyMatch(t -> t.equalsIgnoreCase(actual));
    }

    public void assertTextInDisplayTexts(String actual, AssertionKey key) {
        String normalizedActual = normalizeText(actual);
        String[] texts = key.getDisplayText().getTexts();
        if (texts == null || texts.length == 0) {
            logErrorAndFail("❌ No display texts defined for key: " + key.name());
        }
        boolean match = Arrays.stream(texts)
                .map(this::normalizeText)
                .anyMatch(t -> t.equals(normalizedActual));
        if (!match) {
            logErrorAndFail(
                    "❌ Text not matched. Actual: '" + actual + "' Expected any of: " + Arrays.toString(texts));
        }
        log("✅ Success text matched for key: " + key.name());
    }
}
