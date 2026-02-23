package org.halkKatilim.pages.corporate;

public record IbanTransactionData(
        String iban,
        String tranDate,
        String amount
) {}
