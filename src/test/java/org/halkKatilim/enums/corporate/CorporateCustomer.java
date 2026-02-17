package org.halkKatilim.enums.corporate;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.halkKatilim.interfaces.CustomerCapable;

@Getter
@RequiredArgsConstructor
public enum CorporateCustomer implements CustomerCapable {

    STANDARD_USER("1229","35614036930","121212","123456"),
    STANDARD_USER_2("1296","34264334076","121212","123456");

    private final String number;
    private final String msisdn;
    private final String password;
    private final String smsCode;
}