package org.halkKatilim.testData.corporate.moneyTransfer.iban;

public record TransactionDetailsWithSenderInfo(
        String senderAccountNumber,
        String senderAccountBalance,
        String receiverIban,
        String receiverName,
        String transactionDate,
        String transactionAmount,
        String paymentType,
        String transactionDescription
        ) {}
