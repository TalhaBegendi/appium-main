package org.halkKatilim.enums.corporate;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CorporateCustomer {

    STANDARD_USER("1229","35614036930","121212","123456"),
    STANDARD_USER_2("1296","34264334076","121212","123456");

    private final String number;
    private final String msisdn;
    private final String password;
    private final String smsCode;
}