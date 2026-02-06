package org.halkKatilim.pages.moneyTransfer.toAnotherAccount;

import org.halkKatilim.pages.BasePages;
import org.openqa.selenium.WebElement;

import static org.halkKatilim.pages.moneyTransfer.toAnotherAccount.ToAnotherAccountText.*;

public final class ToAnotherAccountAccountPages extends BasePages {

    ToAnotherAccountIBANPages toAnotherAccountIBANPage = new ToAnotherAccountIBANPages();

    public void enterTransactionDetailsToAccountForToday(String customerType) {
        boolean isCorporate = "CORPORATE".equalsIgnoreCase(customerType);
        String accountNumber = isCorporate
                ? CORPORATE_CUSTOMER_RECEIVER_ACCOUNT_NUMBER
                : RETAIL_CUSTOMER_RECEIVER_ACCOUNT_NUMBER;
        String accountSuffix = isCorporate
                ? CORPORATE_CUSTOMER_RECEIVER_ACCOUNT_NUMBER_SUFFIX
                : RETAIL_CUSTOMER_RECEIVER_ACCOUNT_NUMBER_SUFFIX;
        fillTransactionForm(accountNumber, accountSuffix);
    }

    private void fillTransactionForm(String accountNumber, String suffix) {
        appiumUtil.clearAndFillInputWithScroll("moneyTransferReceiverAccountNumberInputField", accountNumber)
        .clearAndFillInputWithScroll("moneyTransferReceiverAccountNumberSuffixInputField", suffix)
        .clearAndFillInputWithScroll("moneyTransferToAccountAmountField", TRANSACTION_AMOUNT)
        .clearAndFillInputWithScroll("moneyTransferToAccountDescriptionField", TRANSACTION_DESCRIPTION);
    }

    public void enterTransactionDetailsForToday(String customerType, String nextDay) {
        appiumUtil.clearAndFillInputWithScroll("moneyTransferReceiverAccountNumberInputField", CORPORATE_CUSTOMER_RECEIVER_ACCOUNT_NUMBER)
        .clearAndFillInputWithScroll("moneyTransferReceiverAccountNumberSuffixInputField", CORPORATE_CUSTOMER_RECEIVER_ACCOUNT_NUMBER_SUFFIX)
        .clearAndFillInputWithScroll("moneyTransferToAccountAmountField", TRANSACTION_AMOUNT)
        .clearAndFillInputWithScroll("moneyTransferToAccountDescriptionField", TRANSACTION_DESCRIPTION);
        WebElement tranDateElement = appiumUtil.findElementSilent("ibanPageTransactionDate");
        toAnotherAccountIBANPage.selectTransactionDate(tranDateElement,nextDay);
        appiumUtil.findElementSilent("moneyTransferDatePickerOkButton").click();
        appiumUtil.findElementSilent("moneyTransferAcceptOrderButton").click();

    }

    public void clickAccountTab() {
        appiumUtil.clickElement("moneyTransferAccountTab");
    }
}