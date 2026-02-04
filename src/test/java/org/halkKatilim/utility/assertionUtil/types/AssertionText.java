package org.halkKatilim.utility.assertionUtil.types;

public final class AssertionText {

    private AssertionText() {
    }

    static final String ASSERT_EQUALS_LOG = "%s | Actual: %s, Expected: %s";
    static final String ASSERT_NOT_EQUALS_LOG = "%s | Actual: %s, Unexpected: %s";
    static final String ASSERT_CONDITION_LOG = "%s | Condition: %s";
    static final String ASSERT_NULL_LOG = "%s | Object: %s";
    static final String ASSERT_CONTAINS_LOG = "%s | Actual: %s, Expected to contain: %s";
    static final String ASSERT_GREATER_THAN_LOG = "%s | Actual: %d, Threshold: %d";
    static final String ASSERT_LESS_THAN_LOG = "%s | Actual: %d, Threshold: %d";
    static final String ASSERT_NOT_EMPTY_STRING_LOG = "%s | String: %s";
    static final String ASSERT_NOT_EMPTY_COLLECTION_LOG = "%s | Collection: %s";
    static final String ASSERT_EQUALS_DEFAULT_MESSAGE = "Expected: %s, but got: %s";
    static final String ASSERT_NOT_EQUALS_DEFAULT_MESSAGE = "Expected values to be different.";
    static final String ASSERT_TRUE_DEFAULT_MESSAGE = "Expected condition to be true but was false.";
    static final String ASSERT_FALSE_DEFAULT_MESSAGE = "Expected condition to be false but was true.";
    static final String ASSERT_NULL_DEFAULT_MESSAGE = "Expected object to be null.";
    static final String ASSERT_NOT_NULL_DEFAULT_MESSAGE = "Expected object to be not null.";
    static final String ASSERT_EQUALS_IGNORE_CASE_DEFAULT_MESSAGE = "Strings do not match ignoring case.";
    static final String ASSERT_CONTAINS_DEFAULT_MESSAGE = "Expected string to contain substring.";
    static final String ASSERT_LIST_CONTAINS_DEFAULT_MESSAGE = "List does not contain expected item.";
    static final String ASSERT_ALL_DEFAULT_MESSAGE = "Soft assertions passed.";
    static final String ASSERT_ALL_FAILED_MESSAGE = "Soft assertions failed: %s";

}
