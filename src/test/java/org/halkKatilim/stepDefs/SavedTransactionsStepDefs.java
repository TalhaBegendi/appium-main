package org.halkKatilim.stepDefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.halkKatilim.pages.Pages;

public class SavedTransactionsStepDefs {

    Pages pages = new Pages();

    @When("Select one of the saved transactions from the list")
    public void selectOneOfTheSavedTransactionsFromTheList() {
        pages.getSavedTransactions().selectRandomSavedTransaction();
    }

    @When("Click add new saved transaction button")
    public void clickAddNewSavedTransactionButton() {
        pages.getSavedTransactions().clickAddNewSavedTransactionButton();
    }

    @Then("Verify add new saved transaction screen is visible")
    public void verifyAddNewSavedTransactionScreenIsVisible() {
        pages.getSavedTransactions().verifyAddNewSavedTransactionScreenIsVisible();
    }

    @And("Enter {string} as new saved transaction name")
    public void enterAsNewSavedTransactionName(String tranName) {
        pages.getSavedTransactions().enterAsNewSavedTransactionName(tranName);
    }

    @And("Select {string} as transaction type")
    public void selectAsTransactionType(String tranType) {
        pages.getSavedTransactions().selectAsTransactionType(tranType);
    }

    @And("Select {string} as money transfer category")
    public void selectAsMoneyTransferCategory(String moneyTranType) {
        pages.getSavedTransactions().selectAsMoneyTransferCategory(moneyTranType);
    }

    @And("Select {string} as money transfer category for platform")
    public void selectAsMoneyTransferCategoryForPlatform(String moneyTranType) {
        pages.getSavedTransactions().selectAsMoneyTransferCategoryForPlatform(moneyTranType);
    }

    @And("Select one own account as receiver")
    public void selectOneOwnAccountAsReceiver() {
        pages.getSavedTransactions().selectOneOwnAccountAsReceiver();
    }

    @And("Click save button for saved transaction")
    public void clickSaveButtonForSavedTransaction() {
        pages.getSavedTransactions().clickSaveButtonForSavedTransaction();
    }

    @Then("Saved transfer should be created successfully")
    public void successMessageIsDisplayed() {
        pages.getSavedTransactions().successMessageIsDisplayed();
    }

    @When("Click delete button for saved transaction named {string}")
    public void clickDeleteButtonForSavedTransactionNamed(String tranName) {
        pages.getSavedTransactions().clickDeleteButtonForSavedTransactionNamed(tranName);
    }

    @Then("Verify delete confirmation popup is displayed")
    public void verifyDeleteConfirmationPopupIsDisplayed() {
        pages.getSavedTransactions().verifyDeleteConfirmationPopupIsDisplayed();
    }

    @When("Click confirm delete button on delete popup")
    public void clickConfirmDeleteButtonOnDeletePopup() {
        pages.getSavedTransactions().clickConfirmDeleteButtonOnDeletePopup();
    }

    @Then("Verify saved transaction is deleted successfully")
    public void verifySavedTransactionIsDeletedSuccessfully() {
        pages.getSavedTransactions().verifySavedTransactionIsDeletedSuccessfully();
    }

    @And("Enter receiver IBAN")
    public void enterReceiverIBAN() {
        pages.getSavedTransactions().enterReceiverIBAN();
    }

    @And("Select recipient bank as {string}")
    public void selectRecipientBankAs(String bankingName) {
        pages.getSavedTransactions().selectRecipientBankAs(bankingName);
    }

    @And("Enter receiver account info")
    public void enterReceiverAccountInfo() {
        pages.getSavedTransactions().enterReceiverAccountInfo();
    }

    @When("Into saved transactions search field")
    public void intoSavedTransactionsSearchField() {
        pages.getSavedTransactions().intoSavedTransactionsSearchField();
    }

    @When("Enter {string} into saved transactions search field")
    public void enterIntoSavedTransactionsSearchField(String searchText) {
        pages.getSavedTransactions().enterIntoSavedTransactionsSearchField(searchText);
    }

    @Then("All saved transactions matching displayed")
    public void allSavedTransactionsMatchingAreDisplayed() {
        pages.getSavedTransactions().allSavedTransactionsMatchingAreDisplayed();
    }

    @Then("No search results found message is displayed")
    public void noSearchResultsFoundMessageIsDisplayed() {
        pages.getSavedTransactions().noSearchResultsFoundMessageIsDisplayed();
    }

    @And("Enter own IBAN as receiver")
    public void enterOwnIBANAsReceiver() {
        pages.getSavedTransactions().enterOwnIBANAsReceiver();
    }

    @And("Enter own account info for receiver")
    public void enterOwnAccountInfoForReceiver() {
        pages.getSavedTransactions().enterOwnAccountInfoForReceiver();
    }

    @Then("Verify fund account warning error should be displayed")
    public void verifyFundAccountWarningErrorShouldBeDisplayed() {
        pages.getSavedTransactions().verifyFundAccountWarningErrorShouldBeDisplayed();
    }

    @Then("Verify fund iban warning error should be displayed")
    public void verifyFundIbanWarningErrorShouldBeDisplayed() {
        pages.getSavedTransactions().verifyFundIbanWarningErrorShouldBeDisplayed();
    }

    @And("Click confirm button on confirmation page for Saved Transaction")
    public void clickConfirmButtonSavedTransaction() {
        pages.getSavedTransactions().clickConfirmButtonSavedTransaction();
    }
}
