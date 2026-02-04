package org.halkKatilim.utility.assertionUtil.types;

import org.testng.asserts.SoftAssert;

import java.util.Collection;
import java.util.List;

import static java.lang.String.format;
import static org.halkKatilim.utility.assertionUtil.types.AssertionText.*;
import static org.halkKatilim.utility.helpers.FrameworkLogger.error;
import static org.halkKatilim.utility.helpers.FrameworkLogger.info;

public class SoftAssertion {

    private static final ThreadLocal<SoftAssert> softAssertThreadLocal = ThreadLocal.withInitial(SoftAssert::new);

    private SoftAssertion() {}

    public static void softAssertEquals(Object actual, Object expected) {
        softAssertEquals(actual, expected, format(ASSERT_EQUALS_DEFAULT_MESSAGE, expected, actual));
    }

    public static void softAssertEquals(Object actual, Object expected, String message) {
        info(format(ASSERT_EQUALS_LOG, message, actual, expected));
        try {
            softAssertThreadLocal.get().assertEquals(actual, expected, message);
        } catch (AssertionError e) {
            error(format(ASSERT_EQUALS_LOG, message, actual, expected));
        }
    }

    public static void softAssertNotEquals(Object actual, Object unexpected, String message) {
        info(format(ASSERT_NOT_EQUALS_LOG, message, actual, unexpected));
        try {
            softAssertThreadLocal.get().assertNotEquals(actual, unexpected, message);
        } catch (AssertionError e) {
            error(format(ASSERT_NOT_EQUALS_LOG, message, actual, unexpected));
        }
    }

    public static void softAssertNotEquals(Object actual, Object unexpected) {
        softAssertNotEquals(actual, unexpected, ASSERT_NOT_EQUALS_DEFAULT_MESSAGE);
    }

    public static void softAssertTrue(boolean condition, String message) {
        info(format(ASSERT_CONDITION_LOG, message, condition));
        try {
            softAssertThreadLocal.get().assertTrue(condition, message);
        } catch (AssertionError e) {
            error(format(ASSERT_CONDITION_LOG, message, condition));
        }
    }

    public static void softAssertTrue(boolean condition) {
        softAssertTrue(condition, ASSERT_TRUE_DEFAULT_MESSAGE);
    }

    public static void softAssertFalse(boolean condition, String message) {
        info(format(ASSERT_CONDITION_LOG, message, condition));
        try {
            softAssertThreadLocal.get().assertFalse(condition, message);
        } catch (AssertionError e) {
            error(format(ASSERT_CONDITION_LOG, message, condition));
        }
    }

    public static void softAssertFalse(boolean condition) {
        softAssertFalse(condition, ASSERT_FALSE_DEFAULT_MESSAGE);
    }

    public static void softAssertNull(Object object, String message) {
        info(format(ASSERT_NULL_LOG, message, object));
        try {
            softAssertThreadLocal.get().assertNull(object, message);
        } catch (AssertionError e) {
            error(format(ASSERT_NULL_LOG, message, object));
        }
    }

    public static void softAssertNull(Object object) {
        softAssertNull(object, ASSERT_NULL_DEFAULT_MESSAGE);
    }

    public static void softAssertNotNull(Object object, String message) {
        info(format(ASSERT_NULL_LOG, message, object));
        try {
            softAssertThreadLocal.get().assertNotNull(object, message);
        } catch (AssertionError e) {
            error(format(ASSERT_NULL_LOG, message, object));
        }
    }

    public static void softAssertNotNull(Object object) {
        softAssertNotNull(object, ASSERT_NOT_NULL_DEFAULT_MESSAGE);
    }

    public static void softAssertEqualsIgnoreCase(String actual, String expected, String message) {
        info(format(ASSERT_EQUALS_LOG, message, actual, expected));
        boolean result = actual != null && actual.equalsIgnoreCase(expected);
        try {
            softAssertThreadLocal.get().assertTrue(result, message);
        } catch (AssertionError e) {
            error(format(ASSERT_EQUALS_LOG, message, actual, expected));
        }
    }

    public static void softAssertEqualsIgnoreCase(String actual, String expected) {
        softAssertEqualsIgnoreCase(actual, expected, ASSERT_EQUALS_IGNORE_CASE_DEFAULT_MESSAGE);
    }

    public static void softAssertContains(String actual, String expectedSubstring, String message) {
        info(format(ASSERT_CONTAINS_LOG, message, actual, expectedSubstring));
        boolean result = actual != null && actual.contains(expectedSubstring);
        try {
            softAssertThreadLocal.get().assertTrue(result, message);
        } catch (AssertionError e) {
            error(format(ASSERT_CONTAINS_LOG, message, actual, expectedSubstring));
        }
    }

    public static void softAssertContains(String actual, String expectedSubstring) {
        softAssertContains(actual, expectedSubstring, ASSERT_CONTAINS_DEFAULT_MESSAGE);
    }

    public static <T> void softAssertListContains(List<T> list, T item, String message) {
        info(format(ASSERT_CONTAINS_LOG, message, list, item));
        boolean result = list != null && list.contains(item);
        try {
            softAssertThreadLocal.get().assertTrue(result, message);
        } catch (AssertionError e) {
            error(format(ASSERT_CONTAINS_LOG, message, list, item));
        }
    }

    public static <T> void softAssertListContains(List<T> list, T item) {
        softAssertListContains(list, item, ASSERT_LIST_CONTAINS_DEFAULT_MESSAGE);
    }

    public static void softAssertGreaterThan(int actual, int threshold, String message) {
        info(format(ASSERT_GREATER_THAN_LOG, message, actual, threshold));
        try {
            softAssertThreadLocal.get().assertTrue(actual > threshold, message);
        } catch (AssertionError e) {
            error(format(ASSERT_GREATER_THAN_LOG, message, actual, threshold));
        }
    }

    public static void softAssertLessThan(int actual, int threshold, String message) {
        info(format(ASSERT_LESS_THAN_LOG, message, actual, threshold));
        try {
            softAssertThreadLocal.get().assertTrue(actual < threshold, message);
        } catch (AssertionError e) {
            error(format(ASSERT_LESS_THAN_LOG, message, actual, threshold));
        }
    }

    public static void softAssertNotEmpty(String str, String message) {
        info(format(ASSERT_NOT_EMPTY_STRING_LOG, message, str));
        try {
            softAssertThreadLocal.get().assertTrue(str != null && !str.isEmpty(), message);
        } catch (AssertionError e) {
            error(format(ASSERT_NOT_EMPTY_STRING_LOG, message, str));
        }
    }

    public static void softAssertNotEmpty(Collection<?> collection, String message) {
        info(format(ASSERT_NOT_EMPTY_COLLECTION_LOG, message, collection));
        try {
            softAssertThreadLocal.get().assertTrue(collection != null && !collection.isEmpty(), message);
        } catch (AssertionError e) {
            error(format(ASSERT_NOT_EMPTY_COLLECTION_LOG, message, collection));
        }
    }

    public static void softAssertAll() {
        try {
            info(ASSERT_ALL_DEFAULT_MESSAGE);
            softAssertThreadLocal.get().assertAll();
        } catch (AssertionError e) {
            error(format(ASSERT_ALL_FAILED_MESSAGE, e.getMessage()));
            throw e;
        } finally {
            softAssertThreadLocal.remove();
        }
    }

    public static SoftAssert getSoftAssertAll() {
        return softAssertThreadLocal.get();
    }
}
