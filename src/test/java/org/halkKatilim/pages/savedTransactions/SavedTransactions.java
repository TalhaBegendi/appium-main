package org.halkKatilim.pages.savedTransactions;

import org.halkKatilim.deviceConfig.DeviceContext;
import org.halkKatilim.enums.Language;
import org.halkKatilim.enums.MoneyTransferCategory;
import org.halkKatilim.enums.Platform;
import org.halkKatilim.enums.UserType;
import org.halkKatilim.pages.BasePages;
import org.halkKatilim.utility.Driver;
import org.halkKatilim.utility.context.ScenarioRunContext;
import org.openqa.selenium.WebElement;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.IntStream;

import static org.halkKatilim.enums.Platform.IOS;
import static org.halkKatilim.pages.savedTransactions.SavedTransactionsText.*;
import static org.testng.Assert.*;

public class SavedTransactions extends BasePages {

    public void selectRandomSavedTransaction() {
        appiumUtil.clickRandomElement("savedTransactionsList");
    }

    public void clickAddNewSavedTransactionButton() {
        appiumUtil.clickElement("savedTransactionsAddNewTransactionButton");
    }

    public void verifyAddNewSavedTransactionScreenIsVisible() {
        assertElementTextContainsAny(appiumUtil.safeFindElementAndWait("savedTransactionsAddNewTransactionPageTitle"),
                TURKISH_NEW_SAVED_TRANSACTION_PAGE_TITLE, ENGLISH_NEW_SAVED_TRANSACTION_PAGE_TITLE);
    }

    public void enterAsNewSavedTransactionName(String tranName) {
        appiumUtil.clearAndFillInputWithScroll("savedTransactionsNewTransactionNameField", tranName);
    }

    public void selectAsTransactionType(String tranType) {
        appiumUtil.selectFromListByText("savedTransactionsNewTransactionTypeList", tranType);
    }

    public void selectAsMoneyTransferCategory(String moneyTranType) {
        appiumUtil.waitUntilElementLoad("savedTransactionsMoneyTransferCloseButton")
                .selectFromListByText("savedTransactionsMoneyTransferTypeList", moneyTranType);
    }

    public void selectAsMoneyTransferCategoryForPlatform(String moneyTranType) {
        String resolved = MoneyTransferCategory
                .valueOf(moneyTranType.toUpperCase())
                .resolve();
        selectAsMoneyTransferCategory(resolved);
    }

    public void selectOneOwnAccountAsReceiver() {
        appiumUtil.clickElement("savedTransactionsMoneyTransferReceiverDropdown")
                .clickRandomElement("savedTransactionsMoneyTransferReceiverList");
    }

    public void clickSaveButtonForSavedTransaction() {
        appiumUtil.waitBySecond(1)
        .clickElement("savedTransactionsSaveButton");
    }

    public void successMessageIsDisplayed() {
        assertElementTextContainsAny(appiumUtil.findElementSilent("savedTransactionsSuccessMessage"),
                ENGLISH_SUCCESS_MESSAGE, TURKISH_SUCCESS_MESSAGE);
    }

    public void clickDeleteButtonForSavedTransactionNamed(String tranName) {
        List<WebElement> names = appiumUtil.findElementsSilent("savedTransactionsNameList");
        int index = findIndexByText(names, tranName);
        Platform platform = Driver.getPlatformForThread();
        switch (platform) {
            case ANDROID -> {
                appiumUtil.swipeLeftOnElementAndroid(names.get(index));
                List<WebElement> deleteButtons = appiumUtil.findElementsSilent("savedTransactionsDeleteButtonsAndroid");
                deleteButtons.get(index).click();
            }
            case IOS -> {
                appiumUtil.swipeLeftOnElementIOS(names.get(index));
                List<WebElement> deleteButtons = appiumUtil.findElementsSilent("savedTransactionsDeleteButtonsAndroid");
                deleteButtons.get(index).click();
            }
        }
    }

    private int findIndexByText(List<WebElement> elements, String expectedText) {
        return IntStream.range(0, elements.size())
                .filter(i -> expectedText.equals(elements.get(i).getText()))
                .findFirst()
                .orElse(0);
    }

    public void verifyDeleteConfirmationPopupIsDisplayed() {
        assertTrue(appiumUtil.findElementSilent("savedTransactionsDeletePopup").isDisplayed());
    }

    public void clickConfirmDeleteButtonOnDeletePopup() {
        appiumUtil.clickElement("savedTransactionsDeletePopupDeleteButton");
    }

    public void verifySavedTransactionIsDeletedSuccessfully() {
        assertElementTextContainsAny(appiumUtil.findElementSilent("savedTransactionsDeleteTransactionSuccessMessage"),
                TURKISH_DELETE_TRANSACTION_SUCCESS_MESSAGE, ENGLISH_DELETE_TRANSACTION_SUCCESS_MESSAGE);
    }

    public void enterReceiverIBAN() {
        appiumUtil.waitBySecond(1)
                .clearAndFillInputHideKeyboard("savedTransactionsIBANInputField", RETAIL_CUSTOMER_RECEIVER_IBAN);
    }

    public void selectRecipientBankAs(String bankingName) {
        appiumUtil.clickElement("savedTransactionsReceiverBranchSelection")
                .selectFromListByText("savedTransactionsReceiverBranchList", bankingName);

    }

    public void enterReceiverAccountInfo() {
        appiumUtil.clearAndFillInput("savedTransactionsReceiverAccountNumber", RETAIL_CUSTOMER_RECEIVER_ACCOUNT_NUMBER)
                .clearAndFillInputHideKeyboard("savedTransactionsReceiverAccountNumberSuffix", RETAIL_CUSTOMER_RECEIVER_ACCOUNT_NUMBER_SUFFIX);
    }

    public void intoSavedTransactionsSearchField() {
        String searchText = appiumUtil.findElementsSilent("savedTransactionsNameList").getFirst().getText();
        appiumUtil.clearAndFillInput("savedTransactionsSearchBar", searchText)
                .waitBySecond(1);
        runContext.setProperty("searchText",searchText);
    }

    public void enterIntoSavedTransactionsSearchField(String searchText) {
        appiumUtil.clearAndFillInput("savedTransactionsSearchBar", searchText)
                .waitBySecond(1);
    }

    public void allSavedTransactionsMatchingAreDisplayed() {
        String expected = hardAssertion.normalizeText(runContext.getProperty("searchText"));
        appiumUtil.findElementsSilent("savedTransactionsNameList")
                .stream()
                .map(e -> hardAssertion.normalizeText(e.getText()))
                .filter(text -> !text.equals(expected))
                .findFirst()
                .ifPresent(text ->
                        fail("""
                        ❌ Arama sonucu uyuşmuyor
                        Beklenen : "%s"
                        Bulunan  : "%s"
                        """.formatted(expected, text)));
    }

    public void noSearchResultsFoundMessageIsDisplayed() {
        assertElementTextContainsAny(appiumUtil.safeFindElementAndWait("savedTransactionsSearchErrorMessage"),
                TURKISH_SEARCH_ERROR_MESSAGE, ENGLISH_SEARCH_ERROR_MESSAGE);
    }

    public void enterOwnIBANAsReceiver() {
        appiumUtil.waitBySecond(1);
        String iban = DeviceContext.getCurrentUserType() == UserType.RETAIL
                ? FUND_CUSTOMER_RECEIVER_IBAN_RETAIL
                : FUND_CUSTOMER_RECEIVER_IBAN_CORPORATE;
        appiumUtil.clearAndFillInputSmart("savedTransactionsIBANInputField", iban);
    }

    public void enterOwnAccountInfoForReceiver() {
        appiumUtil.clearAndFillInput("savedTransactionsReceiverAccountNumber", FUND_ACCOUNT_NUMBER)
                .clearAndFillInputHideKeyboard("savedTransactionsReceiverAccountNumberSuffix", FUND_ACCOUNT_NUMBER_SUFFIX);
    }

    public void verifyFundAccountWarningErrorShouldBeDisplayed() {
        assertElementTextContainsAny(appiumUtil.findElementSilent("moneyTransferFundWarningPopupMessage"),
                TURKISH_FUND_ACCOUNT_NUMBER_ERROR_MESSAGE, ENGLISH_FUND_ACCOUNT_NUMBER_ERROR_MESSAGE);
    }

    public void verifyFundIbanWarningErrorShouldBeDisplayed() {
        String expectedMessage = resolveFundIbanErrorMessage();
        assertTrue(appiumUtil.findElementSilent("moneyTransferFundWarningPopupMessage")
                        .getText()
                        .contains(expectedMessage)
        );
    }

    private void assertElementTextContainsAny(WebElement element, String... expectedParts) {
        String actualText = element.getText().trim();
        assertTrue(Arrays.stream(expectedParts).anyMatch(actualText::contains),
                "Actual text [" + actualText + "] does not contain any expected values");
    }

    public String resolveFundIbanErrorMessage() {
        Platform platform = Driver.getPlatformForThread();
        Language language = DeviceContext.getLanguage();
        return switch (platform) {
            case ANDROID ->
                    language == Language.TURKISH
                            ? TURKISH_FUND_IBAN_ERROR_MESSAGE_ANDROID
                            : ENGLISH_FUND_IBAN_ERROR_MESSAGE;
            case IOS ->
                    language == Language.TURKISH
                            ? TURKISH_FUND_IBAN_ERROR_MESSAGE_IOS
                            : ENGLISH_FUND_IBAN_ERROR_MESSAGE;
        };
    }

    public void clickConfirmButtonSavedTransaction() {
        Platform platform = Driver.getPlatformForThread();
        if (platform == Platform.ANDROID) {
            appiumUtil.clickElement("confirmationPageConfirmButton");
        }
    }
}
