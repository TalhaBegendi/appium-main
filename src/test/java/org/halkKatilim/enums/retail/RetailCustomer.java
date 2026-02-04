package org.halkKatilim.enums.retail;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum RetailCustomer {

    MENU_USER("1006","121212","123456"),
    ACCOUNT_USER("1064","121212","123456"),
    HOMEPAGE_USER("1033","121212","123456"),
    LOGIN_USER("1033","121212","123456"),
    TEST_USER("1035","121212","123456"),
    NEW_USER("1017","121212","123456"),
    OPTION_USER("1005","121212","123456"),
    MENU_MONEY_TRANSFER_USER("1005","121212","123456");


    private final String number;
    private final String password;
    private final String smsCode;
}