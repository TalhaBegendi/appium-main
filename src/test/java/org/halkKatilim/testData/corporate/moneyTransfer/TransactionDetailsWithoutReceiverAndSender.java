package org.halkKatilim.testData.corporate.moneyTransfer;

public record TransactionDetailsWithoutReceiverAndSender(
        String transactionDate,
        String transactionAmount,
        String paymentType,
        String transactionDescription
) {}
