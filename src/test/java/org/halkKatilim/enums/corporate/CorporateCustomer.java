package org.halkKatilim.enums.corporate;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.halkKatilim.interfaces.CustomerCapable;

@Getter
@RequiredArgsConstructor
public enum CorporateCustomer implements CustomerCapable {

    STANDARD_USER("1197","98008068122","121212","123456"),
    REQUESTER("100046","33056414736","121212","123456"),
    APPROVER("100046","3285446736","121212","123456");

    private final String number;
    private final String msisdn;
    private final String password;
    private final String smsCode;
}