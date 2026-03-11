package org.halkKatilim.pages.moneyTransfer.toAnotherAccount;

import org.halkKatilim.deviceConfig.DeviceContext;
import org.halkKatilim.enums.Platform;
import org.halkKatilim.pages.BasePages;
import org.halkKatilim.utility.Driver;
import org.openqa.selenium.WebElement;

import static org.halkKatilim.pages.moneyTransfer.toAnotherAccount.ToAnotherAccountText.*;
import static org.testng.AssertJUnit.assertEquals;

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

    public void verifyTransactionSentForApprovalTypeAccount() {
        appiumUtil.waitUntilElementLoad("moneyTransferSentForApprovalInfoText");
        Platform platform = Driver.getPlatformForThread();
        boolean isRetail = "RETAIL".equalsIgnoreCase(String.valueOf(DeviceContext.getCurrentUserType()));
        boolean isTurkish = "TURKISH".equalsIgnoreCase(String.valueOf(DeviceContext.getLanguage()));
        boolean isIOS = platform == Platform.IOS;
        String expectedMessage =
                isRetail
                        ? (isTurkish
                        ? (isIOS
                        ? TURKISH_MONEY_TRANSFER_SENT_FOR_APPROVAL_INFO_TEXT_ACCOUNT_RETAIL_IOS
                        : TURKISH_MONEY_TRANSFER_SENT_FOR_APPROVAL_INFO_TEXT_ACCOUNT_RETAIL)
                        : (isIOS
                        ? ENGLISH_MONEY_TRANSFER_SENT_FOR_APPROVAL_INFO_TEXT_ACCOUNT_RETAIL_IOS
                        : ENGLISH_MONEY_TRANSFER_SENT_FOR_APPROVAL_INFO_TEXT_ACCOUNT_RETAIL))
                        : (isTurkish
                        ? TURKISH_MONEY_TRANSFER_SENT_FOR_APPROVAL_INFO_TEXT_ACCOUNT_RETAIL_IOS
                        : ENGLISH_MONEY_TRANSFER_SENT_FOR_APPROVAL_INFO_TEXT_ACCOUNT_RETAIL);
        assertEquals(expectedMessage, appiumUtil.findElementSilent("moneyTransferSentForApprovalInfoText").getText());
    }

    public void clickAccountTab() {
        appiumUtil.clickElement("moneyTransferAccountTab");
    }
}