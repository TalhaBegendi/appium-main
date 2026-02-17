package org.halkKatilim.testData.retail.moneyTransfer;

import org.halkKatilim.enums.UserType;

public record CustomerEntry<T>(
        UserType type,
        T customer
) {}