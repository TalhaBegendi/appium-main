package org.halkKatilim.testData.corporate.moneyTransfer.iban;

public record IbanCustomerTransactionData(
        String iban,
        String amount,
        String description
) {}
