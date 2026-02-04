package org.halkKatilim.utility.assertionUtil.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AssertionPrefix {

    LOGINPAGE("Login"),
    MENU("Menu"),
    HOMEPAGE("Homepage"),

    MENU_ACCOUNT("Account"),
    MENU_QR("QR Code Transaction"),
    MENU_CURRENCY("Currency / Precious Metals"),
    MENU_CARDS("Cards"),
    MENU_FINANCING("Financing"),
    MENU_INVESTMENT("Investment"),
    MENU_TRANSACTIONS("My Transactions"),
    MENU_CHECK_PROMISSORY_NOTE("Check Promissory Note"),
    MENU_MY_DOCUMENTS("My Documents"),
    MENU_MONEY_TRANSFER("Money Transfer");

    private final String value;
}
