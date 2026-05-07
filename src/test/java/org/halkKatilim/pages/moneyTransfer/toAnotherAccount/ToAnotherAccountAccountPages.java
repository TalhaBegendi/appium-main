package org.halkKatilim.pages.moneyTransfer.toAnotherAccount;

import org.halkKatilim.deviceConfig.DeviceContext;
import org.halkKatilim.enums.Platform;
import org.halkKatilim.utility.Driver;
import org.openqa.selenium.WebElement;
import static org.halkKatilim.pages.moneyTransfer.toAnotherAccount.ToAnotherAccountText.*;
import static org.testng.AssertJUnit.assertEquals;
import lombok.RequiredArgsConstructor;
import org.halkKatilim.utility.appiumUtil.AppiumUtil;

@RequiredArgsConstructor
public final class ToAnotherAccountAccountPages {

    private final AppiumUtil appiumUtil;

    ToAnotherAccountIBANPages toAnotherAccountIBANPage;

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
        appiumUtil.fillInputKeyboard("moneyTransferReceiverAccountNumberInputField", accountNumber, true, true)
        .fillInputKeyboard("moneyTransferReceiverAccountNumberSuffixInputField", suffix, true, true)
        .fillInputKeyboard("moneyTransferToAccountAmountField", TRANSACTION_AMOUNT, true, true)
        .fillInputKeyboard("moneyTransferToAccountDescriptionField", TRANSACTION_DESCRIPTION, true, true);
    }

    public void enterTransactionDetailsForToday(String customerType, String nextDay) {
        appiumUtil.fillInputKeyboard("moneyTransferReceiverAccountNumberInputField", CORPORATE_CUSTOMER_RECEIVER_ACCOUNT_NUMBER, true, true)
        .fillInputKeyboard("moneyTransferReceiverAccountNumberSuffixInputField", CORPORATE_CUSTOMER_RECEIVER_ACCOUNT_NUMBER_SUFFIX, true, true)
        .fillInputKeyboard("moneyTransferToAccountAmountField", TRANSACTION_AMOUNT, true, true)
        .fillInputKeyboard("moneyTransferToAccountDescriptionField", TRANSACTION_DESCRIPTION, true, true);
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