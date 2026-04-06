package org.halkKatilim.enums.retail;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.halkKatilim.interfaces.CustomerCapable;

@RequiredArgsConstructor
@Getter
public enum RetailCustomer implements CustomerCapable {

    MENU_USER("1063","121212","123456"),
    ACCOUNT_USER("1006","121212","123456"),
    HOMEPAGE_USER("1033","121212","123456"),
    LOGIN_USER("1033","121212","123456"),
    TEST_USER("1035","121212","123456"),
    NEW_USER("1017","121212","123456"),
    OPTION_USER("1019","121212","123456"),
    MENU_MONEY_TRANSFER_USER("1005","121212","123456");

    private final String number;
    private final String password;
    private final String smsCode;
}