package org.halkKatilim.pages.savedTransactions;
import org.halkKatilim.deviceConfig.DeviceContext;
import org.halkKatilim.enums.*;
import org.halkKatilim.interfaces.CustomerCapable;
import org.halkKatilim.utility.Driver;
import org.halkKatilim.utility.context.ScenarioRunContext;
import org.openqa.selenium.WebElement;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import static org.halkKatilim.pages.savedTransactions.SavedTransactionsText.*;
import static org.testng.Assert.*;
import static org.testng.AssertJUnit.assertEquals;
import lombok.RequiredArgsConstructor;
import org.halkKatilim.utility.appiumUtil.AppiumUtil;

@RequiredArgsConstructor
public class SavedTransactions  {
    private final AppiumUtil appiumUtil;

    public void selectRandomSavedTransaction() {
        appiumUtil.safeFindElementsAndWait("savedTransactionsList").getFirst().click();
    }

    public void clickAddNewSavedTransactionButton() {
        appiumUtil.clickElement("savedTransactionsAddNewTransactionButton");
    }

    public void verifyAddNewSavedTransactionScreenIsVisible() {
        assertElementTextContainsAny(appiumUtil.safeFindElementAndWait("savedTransactionsAddNewTransactionPageTitle"),
                TURKISH_NEW_SAVED_TRANSACTION_PAGE_TITLE, ENGLISH_NEW_SAVED_TRANSACTION_PAGE_TITLE, ENGLISH_NEW_SAVED_TRANSACTION_PAGE_TITLE_IOS);
    }

    public void enterAsNewSavedTransactionName(String tranName) {
        appiumUtil.fillInputKeyboard("savedTransactionsNewTransactionNameField", tranName, true, true);
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
                List<WebElement> deleteButtons = appiumUtil.findElementsSilent("savedTransactionsDeleteButtonIos");
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
                .fillInputKeyboard("savedTransactionsIBANInputField", RETAIL_CUSTOMER_RECEIVER_IBAN, true, true);
    }

    public void selectRecipientBankAs(String bankingName) {
        appiumUtil.clickElement("savedTransactionsReceiverBranchSelection")
                .selectFromListByText("savedTransactionsReceiverBranchList", bankingName);

    }

    public void enterReceiverAccountInfo() {
        appiumUtil.fillInputKeyboard("savedTransactionsReceiverAccountNumber", RETAIL_CUSTOMER_RECEIVER_ACCOUNT_NUMBER, false, true)
                .fillInputKeyboard("savedTransactionsReceiverAccountNumberSuffix", RETAIL_CUSTOMER_RECEIVER_ACCOUNT_NUMBER_SUFFIX, false, true);
    }

    public void intoSavedTransactionsSearchField() {
        String searchText = appiumUtil.findElementsSilent("savedTransactionsNameList").getFirst().getText();
        Platform platform = Driver.getPlatformForThread();
        if (platform == Platform.IOS){
            appiumUtil.clickElement("savedTransactionsSearchBarForIOS");
        }
        appiumUtil.fillInputKeyboard("savedTransactionsSearchBar", searchText, false, false)
                .waitBySecond(1);
        ScenarioRunContext.set("searchText", searchText);
    }

    public void enterIntoSavedTransactionsSearchField(String searchText) {
        Platform platform = Driver.getPlatformForThread();
        if (platform == Platform.IOS){
            appiumUtil.clickElement("savedTransactionsSearchBarForIOS");
        }
        appiumUtil.fillInputKeyboard("savedTransactionsSearchBar", searchText, false, false)
                .waitBySecond(1);
    }

    public void allSavedTransactionsMatchingAreDisplayed() {
        String expected = appiumUtil.getAssertion().normalizeText(ScenarioRunContext.get("searchText"));
        String actual = appiumUtil.findElementsSilent("savedTransactionsNameList")
                .stream()
                .map(e -> appiumUtil.getAssertion().normalizeText(e.getText()))
                .findFirst()
                .orElseThrow(() -> new AssertionError("❌ Hiç sonuç bulunamadı"));
        if (!actual.equals(expected)) {
            fail("""
            ❌ İlk sonuç uyuşmuyor
            Beklenen : "%s"
            Bulunan  : "%s"
            """.formatted(expected, actual));
        }
    }

    public void noSearchResultsFoundMessageIsDisplayed() {
        assertElementTextContainsAny(appiumUtil.safeFindElementAndWait("savedTransactionsSearchErrorMessage"),
                TURKISH_SEARCH_ERROR_MESSAGE, ENGLISH_SEARCH_ERROR_MESSAGE, ENGLISH_SEARCH_ERROR_MESSAGE_IOS);
    }

    public void enterOwnIBANAsReceiver() {
        appiumUtil.waitBySecond(1);
        String iban = DeviceContext.getCurrentUserType() == UserType.RETAIL
                ? FUND_CUSTOMER_RECEIVER_IBAN_RETAIL
                : FUND_CUSTOMER_RECEIVER_IBAN_CORPORATE;
        appiumUtil.clearAndFillInputSmart("savedTransactionsIBANInputField", iban);
    }

    public void enterOwnAccountInfoForReceiver() {
        CustomerCapable customer = DeviceContext.getCurrentCustomer();
        String customerKey = customer.getNumber();
        appiumUtil.fillInputKeyboard("savedTransactionsReceiverAccountNumber", customerKey ,false, true)
                .fillInputKeyboard("savedTransactionsReceiverAccountNumberSuffix", FUND_ACCOUNT_NUMBER_SUFFIX, false, true);
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
            case ANDROID -> language == Language.TURKISH
                    ? TURKISH_FUND_IBAN_ERROR_MESSAGE_ANDROID
                    : ENGLISH_FUND_IBAN_ERROR_MESSAGE;
            case IOS -> language == Language.TURKISH
                    ? TURKISH_FUND_IBAN_ERROR_MESSAGE_IOS
                    : ENGLISH_FUND_IBAN_ERROR_MESSAGE_IOS;
        };
    }

    public void clickConfirmButtonSavedTransaction() {
        Platform platform = Driver.getPlatformForThread();
        if (platform == Platform.ANDROID) {
            appiumUtil.clickElement("confirmationPageConfirmButton");
        }
    }

    public void verifyTransactionSentForApprovalTypeSavedTransaction() {
        appiumUtil.waitUntilElementLoad("moneyTransferSentForApprovalInfoText");
        Platform platform = Driver.getPlatformForThread();
        boolean isTurkish = "TURKISH".equalsIgnoreCase(String.valueOf(DeviceContext.getLanguage()));
        boolean isIOS = platform == Platform.IOS;
        String expectedMessage =
                isTurkish
                        ? (isIOS
                           ? TURKISH_SUCCESS_MESSAGE_IOS
                           : TURKISH_SUCCESS_MESSAGE_AND)
                        : (ENGLISH_SUCCESS_MESSAGE_AND);
        assertEquals(expectedMessage, appiumUtil.findElementSilent("moneyTransferSentForApprovalInfoText").getText());
    }

    public void clickContinueButton() {
        Platform platform = Driver.getPlatformForThread();
        boolean isRetail = DeviceContext.getCurrentUserType() == UserType.RETAIL;
        String buttonKey = switch (platform) {
            case ANDROID -> "moneyTransferContinueButton";
            case IOS -> isRetail
                    ? "savedTransactionContinueButtoniOSRetail"
                    : "moneyTransferContinueButtoniOS";
        };
        appiumUtil.clickElement(buttonKey);
        if (platform == Platform.ANDROID) {
            appiumUtil.autoHandleNavigationGates(NavigationGates.Context.MONEY_TRANSFER);
        }
    }
}
