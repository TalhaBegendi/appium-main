package org.halkKatilim.stepDefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.halkKatilim.pages.Pages;

public class MoneyTransferSteps {

    Pages pages = new Pages();

    @When("Click the make from saved transactions button")
    public void clickMakeFromSavedTransactionsButton() {
        pages.getToAnotherAccountIbanPage().clickMakeFromSavedTransaction();
    }

    @And("Selects one of the saved transactions from the list")
    public void selectRandomSavedTransaction() {
        pages.getToAnotherAccountIbanPage().selectRandomSavedTransaction();

    }

    @Then("The selected transaction details should be displayed correctly")
    public void verifyTransactionDetailsVisible() {
        pages.getToAnotherAccountIbanPage().verifyTransactionDetailsVisible();
    }


    @When("Click continue button on the Another Account page")
    public void clickContinueButton() {
        pages.getToAnotherAccountIbanPage().clickContinueButton();
    }

    @When("Enter {string} customer transaction details for today")
    public void enterCustomerTransactionDetailsForToday(String customerType) {
        pages.getToAnotherAccountIbanPage().enterTransactionDetailsForToday(customerType);
    }

    @Then("The transaction details should be displayed correctly on the Verification page")
    public void verifyOnVerificationPage() {
        pages.getToAnotherAccountIbanPage().verifyTransactionDetailsOnTheConfirmationPage();
    }

    @When("Click confirm button on confirmation page")
    public void clickConfirmButton() {
        pages.getToAnotherAccountIbanPage().clickConfirmButton();
    }

    @And("Enter the OTP code")
    public void enterOtpCode() {
        pages.getToAnotherAccountIbanPage().enterOtpCode("123456");
    }

    @Then("The transaction should be completed successfully")
    public void verifyTransactionSuccess() {
        pages.getToAnotherAccountIbanPage().verifyTransactionSuccess();
    }
    @Then("The transaction should be successfully sent for approval")
    public void verifyTransactionSentForApproval() {
        pages.getToAnotherAccountIbanPage().verifyTransactionSentForApproval();
    }

    @When("Enter {string} customer transaction details with different currency for today")
    public void enterCustomerTransactionDetailsWithDifferentCurrencyForToday(String customerType) {
        pages.getToAnotherAccountIbanPage().enterCustomerTransactionDetailsWithDifferentCurrencyForToday(customerType);
    }

    @Then("The different currency error message should be displayed")
    public void theDifferentCurrencyErrorMessageShouldBeDisplayed() {
        pages.getToAnotherAccountIbanPage().theDifferentCurrencyErrorMessageShouldBeDisplayed();
    }

    @Given("Enter {string} transaction details to account for today")
    public void enterTransactionDetailsToAccountForToday(String customerType) {
        pages.getToAnotherAccountAccountPage().enterTransactionDetailsToAccountForToday(customerType);
    }


    @When("Enter {string} customer transaction details for {string} days later")
    public void enterCustomerTransactionDetailsForDaysLater(String customerType, String nextDay) {
        pages.getToAnotherAccountIbanPage().enterCustomerTransactionDetailsForDaysLater(customerType,nextDay);
    }

    @And("Enter transaction amount as {string} TL and description")
    public void enterTransactionAmountAsAndDescription(String amount) {
        pages.getToAnotherAccountIbanPage().enterTransactionAmountAndDescription(amount);
    }

    @When("Enter fund transaction details for today")
    public void enterFundTransactionDetailsForToday() {
        pages.getToAnotherAccountIbanPage().enterFundTransactionDetailsForToday();
    }

    @When("Enter fund transaction details to account for today")
    public void enterFundTransactionDetailsToAccountForToday() {
        pages.getToAnotherAccountIbanPage().enterFundTransactionDetailsToAccountForToday();
    }

    @When("Enter {string} customer to account transaction details for {string} days later")
    public void enterCustomerToAccountTransactionDetailsForDaysLater(String customerType, String nextDay) {
        pages.getToAnotherAccountAccountPage().enterTransactionDetailsForToday(customerType,nextDay);

    }

    @Then("Verify fund warning error should be displayed")
    public void verifyFundWarningErrorShouldBeDisplayed() {
        pages.getToAnotherAccountIbanPage().verifyFundWarningErrorAsIsDisplayed();

    }

    @And("Click account tab")
    public void clickAccountTab() {
        pages.getToAnotherAccountAccountPage().clickAccountTab();
    }
}
