package org.halkKatilim.stepDefs;

import org.halkKatilim.utility.context.PageContext;
import org.halkKatilim.pages.savedTransactions.SavedTransactions;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class SavedTransactionsStepDefs {

    private SavedTransactions savedTransactions() {
        return PageContext.get().get(SavedTransactions.class);
    }

    @When("Select one of the saved transactions from the list")
    public void selectOneOfTheSavedTransactionsFromTheList() {
        savedTransactions().selectRandomSavedTransaction();
    }

    @When("Click add new saved transaction button")
    public void clickAddNewSavedTransactionButton() {
        savedTransactions().clickAddNewSavedTransactionButton();
    }

    @Then("Verify add new saved transaction screen is visible")
    public void verifyAddNewSavedTransactionScreenIsVisible() {
        savedTransactions().verifyAddNewSavedTransactionScreenIsVisible();
    }

    @And("Enter {string} as new saved transaction name")
    public void enterAsNewSavedTransactionName(String tranName) {
        savedTransactions().enterAsNewSavedTransactionName(tranName);
    }

    @And("Select {string} as transaction type")
    public void selectAsTransactionType(String tranType) {
        savedTransactions().selectAsTransactionType(tranType);
    }

    @And("Select {string} as money transfer category")
    public void selectAsMoneyTransferCategory(String moneyTranType) {
        savedTransactions().selectAsMoneyTransferCategory(moneyTranType);
    }

    @And("Select {string} as money transfer category for platform")
    public void selectAsMoneyTransferCategoryForPlatform(String moneyTranType) {
        savedTransactions().selectAsMoneyTransferCategoryForPlatform(moneyTranType);
    }

    @And("Select one own account as receiver")
    public void selectOneOwnAccountAsReceiver() {
        savedTransactions().selectOneOwnAccountAsReceiver();
    }

    @And("Click save button for saved transaction")
    public void clickSaveButtonForSavedTransaction() {
        savedTransactions().clickSaveButtonForSavedTransaction();
    }

    @Then("Saved transfer should be created successfully")
    public void successMessageIsDisplayed() {
        savedTransactions().successMessageIsDisplayed();
    }

    @Then("The transaction should be successfully sent for approval and Saved Transaction")
    public void verifyTransactionSentForApprovalAndSavedTransaction() {
        savedTransactions().verifyTransactionSentForApprovalTypeSavedTransaction();
    }

    @When("Click delete button for saved transaction named {string}")
    public void clickDeleteButtonForSavedTransactionNamed(String tranName) {
        savedTransactions().clickDeleteButtonForSavedTransactionNamed(tranName);
    }

    @Then("Verify delete confirmation popup is displayed")
    public void verifyDeleteConfirmationPopupIsDisplayed() {
        savedTransactions().verifyDeleteConfirmationPopupIsDisplayed();
    }

    @When("Click confirm delete button on delete popup")
    public void clickConfirmDeleteButtonOnDeletePopup() {
        savedTransactions().clickConfirmDeleteButtonOnDeletePopup();
    }

    @Then("Verify saved transaction is deleted successfully")
    public void verifySavedTransactionIsDeletedSuccessfully() {
        savedTransactions().verifySavedTransactionIsDeletedSuccessfully();
    }

    @And("Enter receiver IBAN")
    public void enterReceiverIBAN() {
        savedTransactions().enterReceiverIBAN();
    }

    @And("Select recipient bank as {string}")
    public void selectRecipientBankAs(String bankingName) {
        savedTransactions().selectRecipientBankAs(bankingName);
    }

    @And("Enter receiver account info")
    public void enterReceiverAccountInfo() {
        savedTransactions().enterReceiverAccountInfo();
    }

    @When("Into saved transactions search field")
    public void intoSavedTransactionsSearchField() {
        savedTransactions().intoSavedTransactionsSearchField();
    }

    @When("Enter {string} into saved transactions search field")
    public void enterIntoSavedTransactionsSearchField(String searchText) {
        savedTransactions().enterIntoSavedTransactionsSearchField(searchText);
    }

    @Then("All saved transactions matching displayed")
    public void allSavedTransactionsMatchingAreDisplayed() {
        savedTransactions().allSavedTransactionsMatchingAreDisplayed();
    }

    @Then("No search results found message is displayed")
    public void noSearchResultsFoundMessageIsDisplayed() {
        savedTransactions().noSearchResultsFoundMessageIsDisplayed();
    }

    @And("Enter own IBAN as receiver")
    public void enterOwnIBANAsReceiver() {
        savedTransactions().enterOwnIBANAsReceiver();
    }

    @And("Enter own account info for receiver")
    public void enterOwnAccountInfoForReceiver() {
        savedTransactions().enterOwnAccountInfoForReceiver();
    }

    @Then("Verify fund account warning error should be displayed")
    public void verifyFundAccountWarningErrorShouldBeDisplayed() {
        savedTransactions().verifyFundAccountWarningErrorShouldBeDisplayed();
    }

    @Then("Verify fund iban warning error should be displayed")
    public void verifyFundIbanWarningErrorShouldBeDisplayed() {
        savedTransactions().verifyFundIbanWarningErrorShouldBeDisplayed();
    }

    @And("Click confirm button on confirmation page for Saved Transaction")
    public void clickConfirmButtonSavedTransaction() {
        savedTransactions().clickConfirmButtonSavedTransaction();
    }

    @And("Click continue button on the Another Saved Transaction page")
    public void clickContinueButtonSavedTransaction() {
        savedTransactions().clickContinueButton();
    }
}
