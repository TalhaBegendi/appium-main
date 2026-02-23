package org.halkKatilim.enums.corporate;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.halkKatilim.interfaces.CustomerCapable;

@Getter
@RequiredArgsConstructor
public enum CorporateCustomer implements CustomerCapable {

    STANDARD_USER("1197","98008068122","121212","123456"),
    REQUESTER("1296","34264334076","121212","123456"),
    APPROVER("1296","86353118814","121212","123456"),
    NEW_ACCOUNT("1197","33428051118","121212","123456");

    private final String number;
    private final String msisdn;
    private final String password;
    private final String smsCode;
}