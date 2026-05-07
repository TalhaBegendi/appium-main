package org.halkKatilim.utility.assertionUtil.enums;

import lombok.Getter;
import org.halkKatilim.enums.DisplayText;
import org.halkKatilim.utility.assertionUtil.types.HardAssertion;
import org.halkKatilim.utility.context.ExecutionContext;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.halkKatilim.enums.DisplayText.*;
import static org.halkKatilim.utility.assertionUtil.enums.AssertionType.*;

@Getter
public enum AssertionKey {

    // ---------- LOGIN ----------
    LOGIN(null,"pageTitleHomepage",EXISTS),
    BUTTON_LOGIN_ITEM(null,"buttonLoginItem",SINGLE),

    // ---------- HOMEPAGE/ACCOUNT ----------
    ACCOUNTS(null, null, EQUAL_TEXT),
    ASSETS(null, null, NOT_EQUAL_TEXT),
    SUCCESS_ACCOUNTS(SUCCESS_ACCOUNTS_DISPLAY, null, EQUAL_TEXT),

    // ---------- HOMEPAGE/LAST_ACTIVITIES ----------
    LAST_TRANSACTIONS_SLIP(null, "last10TransactionsSlipHomepage", SINGLE),

    // ---------- MENU ----------
    MAIN_MENU(null, "menuTitleItem", EXISTS);

    private final DisplayText displayText;
    private final String elementKey;
    private final AssertionType type;

    AssertionKey(DisplayText displayText,
                 String elementKey,
                 AssertionType type) {
        this.displayText = displayText;
        this.elementKey = elementKey;
        this.type = type;
    }

    public void runAssertion() {
        type.execute(this);
    }

    public AssertionKey runAssertion(String actual, String expected) {
        type.execute(this, actual, expected);
        return this;
    }

    public AssertionKey runAssertionInList(List<String> textList, List<String> expected) {
        HardAssertion currentAssertion = ExecutionContext.getAssertion();
        List<String> normalizedTextList = textList.stream()
                .map(currentAssertion::normalizeText)
                .toList();
        for (String expectedItem : expected) {
            String normalizedExpected = currentAssertion.normalizeText(expectedItem);
            String found = normalizedTextList.stream()
                    .filter(text -> text.equals(normalizedExpected))
                    .findFirst()
                    .orElse(null);
            if (found == null) {
                runAssertion(null, expectedItem);
                return this;
            }
        }
        return this;
    }
}