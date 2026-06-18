package org.halkKatilim.pages.corporate;
import org.halkKatilim.utility.assertionUtil.types.HardAssertion;

import org.halkKatilim.pages.moneyTransfer.toAnotherAccount.ToAnotherAccountIBANPages;
import org.openqa.selenium.WebElement;

import java.util.Arrays;

import static org.halkKatilim.pages.corporate.CorporatePageText.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import lombok.RequiredArgsConstructor;
import org.halkKatilim.utility.appiumUtil.AppiumUtil;


@RequiredArgsConstructor
public class CorporatePage  {

    private final AppiumUtil appiumUtil;

    ToAnotherAccountIBANPages toAnotherAccountIBANPages;
    IbanTransactionData expectedIbanTransferData;

    public void enterIbanTransferDetailsToSendApprovalForToday(String customerType) {
        toAnotherAccountIBANPages = new ToAnotherAccountIBANPages(appiumUtil);
        toAnotherAccountIBANPages.prepareAndFillTransactionFields(customerType);
        String receiverIban = appiumUtil.findElementSilent("ibanPageReceiverIbanInputField").getText().replace(" ", "");
        String amount = appiumUtil.findElementSilent("ibanPageTransactionAmount").getText();
        String tranDate = appiumUtil.findElementSilent("ibanPageTransactionDate").getText();

        expectedIbanTransferData = new IbanTransactionData(receiverIban, tranDate, amount);
    }

    public void verifyTheIBANTransferDetailsSentForApprovalAreCorrect() {

        String receiverIban = appiumUtil.findElementSilent("firstTransactionReceiverAccountIdentifier").getText();
        String amount = appiumUtil.findElementSilent("firstTransactionAmount").getText().split(" ")[0];
        String tranDate = appiumUtil.findElementSilent("firstTransactionDate").getText();

        IbanTransactionData actualIbanTransferData = new IbanTransactionData(receiverIban, tranDate, amount);
        assertEquals(actualIbanTransferData, expectedIbanTransferData,
                "IBAN transfer details on the corporate approval list do not match the entered values");
    }

    public void deleteTheTransactionSentForApproval() {
        appiumUtil.clickWebElement(appiumUtil.findElementsSilent("transactionItemList").getLast())
                .clickElement("transactionDeleteButton")
                .clickElement("transactionDeletePopupApproveButton");
    }

    public void approveTheTransactionSentForApproval() {
        appiumUtil.clickElement("firstTransactionItem")
                .clickElement("transactionApproveButton")
                .clickElement("transactionApproveConfirmationButton");
    }

    public void verifyTransactionSuccessMessageIsDisplayed() {
        appiumUtil.assertElementTextContainsAny(appiumUtil.findElementSilent("transactionSuccessMessage"),
                TURKISH_APPROVE_SUCCESS_MESSAGE);
    }

    public void clickTab(String tabName) {
        appiumUtil.clickByText("tabItems", tabName)
                .waitBySecond(1);
    }

    public void clickBackAndMenuButton() {
        appiumUtil.clickElement("backButton");
    }

    public void rejectTheTransactionSentForApproval() {
        appiumUtil.clickElement("firstTransactionItem")
                .clickElement("transactionRejectButton")
                .fillInputKeyboard("transactionRejectDescriptionField",REJECT_DESCRIPTION,false,false)
                .clickElement("transactionRejectDescriptionApproveButton");
    }

    public void verifyTransactionRejectionMessageIsDisplayed() {
        appiumUtil.assertElementTextContainsAny(appiumUtil.findElementSilent("transactionSuccessMessage"),
                TURKISH_REJECT_SUCCESS_MESSAGE);
    }
}

