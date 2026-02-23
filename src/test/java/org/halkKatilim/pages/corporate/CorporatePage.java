package org.halkKatilim.pages.corporate;

import org.halkKatilim.pages.BasePages;
import org.halkKatilim.pages.moneyTransfer.toAnotherAccount.ToAnotherAccountIBANPages;
import org.openqa.selenium.WebElement;

import java.util.Arrays;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;


public class CorporatePage extends BasePages {

    ToAnotherAccountIBANPages toAnotherAccountIBANPages = new ToAnotherAccountIBANPages();
    private IbanTransactionData expectedIbanTransferData;

    public void enterIbanTransferDetailsToSendApprovalForToday(String customerType) {

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

    private void assertElementTextContainsAny(WebElement element, String... expectedParts) {
        String actualText = element.getText().trim();
        assertTrue(Arrays.stream(expectedParts).anyMatch(actualText::contains),
                "Actual text [" + actualText + "] does not contain any expected values");
    }

    public void approveTheTransactionSentForApproval() {
        appiumUtil.clickElement("firstTransactionItem")
                .clickElement("transactionApproveButton")
                .clickElement("transactionApproveConfirmationButton");
    }

    public void verifyTransactionSuccessMessageIsDisplayed() {
        assertElementTextContainsAny(appiumUtil.findElementSilent("transactionSuccessMessage"),
                "İşleminiz onaylandı.");
    }

    public void clickTab(String tabName) {
        appiumUtil.clickByText("tabItems", tabName)
                .waitBySecond(1);
    }

    public void clickBackAndMenuButton() {
        appiumUtil.clickElement("backButton");
    }
}

