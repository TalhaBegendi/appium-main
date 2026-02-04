package org.halkKatilim.testData.corporate.moneyTransfer.account;

public record AccountTransactionDetailsWithoutSender(
        String recipientAccountNo,
        String recipientAccountSuffixNo,
        String amount,
        String description,
        String receiverNameField
){}