package org.halkKatilim.stepDefs;

import org.halkKatilim.utility.context.PageContext;
import org.halkKatilim.pages.moneyTransfer.toAnotherAccount.ToAnotherAccountAccountPages;
import org.halkKatilim.pages.moneyTransfer.toAnotherAccount.ToAnotherAccountIBANPages;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.halkKatilim.enums.ContinueButtonVariant;

public class MoneyTransferSteps {

    private ToAnotherAccountIBANPages ibanPage() {
        return PageContext.get().get(ToAnotherAccountIBANPages.class);
    }

    private ToAnotherAccountAccountPages accountPage() {
        return PageContext.get().get(ToAnotherAccountAccountPages.class);
    }

    @When("Click the make from saved transactions button")
    public void clickMakeFromSavedTransactionsButton() {
        ibanPage().clickMakeFromSavedTransaction();
    }

    @And("Selects one of the saved transactions from the list")
    public void selectRandomSavedTransaction() {
        ibanPage().selectRandomSavedTransaction();
    }

    @Then("The selected transaction details should be displayed correctly")
    public void verifyTransactionDetailsVisible() {
        ibanPage().verifyTransactionDetailsVisible();
    }

    @When("Click continue button on the Another Account page")
    public void clickContinueButton() {
        ibanPage().clickContinueButton(ContinueButtonVariant.DEFAULT,false);
    }

    @When("Click money transfer case continue button on the Another Account page")
    public void clickTestContinueButton() {
        ibanPage().clickContinueButton(ContinueButtonVariant.IOS_RETAIL_TEST,true);
    }

    @When("Enter {string} customer transaction details for today")
    public void enterCustomerTransactionDetailsForToday(String customerType) {
        ibanPage().enterTransactionDetailsForToday(customerType);
    }

    @Then("The transaction details should be displayed correctly on the Verification page")
    public void verifyOnVerificationPage() {
        ibanPage().verifyTransactionDetailsOnTheConfirmationPage();
    }

    @When("Click confirm button on confirmation page")
    public void clickConfirmButton() {
        ibanPage().clickConfirmButton();
    }

    @And("Enter the OTP code")
    public void enterOtpCode() {
        ibanPage().confirmWithOtp();
    }

    @Then("The transaction should be completed successfully")
    public void verifyTransactionSuccess() {
        ibanPage().verifyTransactionSuccess();
    }

    @Then("The transaction should be for forward date completed successfully")
    public void verifyTransactionSuccessForwardDate() {
        ibanPage().verifyTransactionSuccessForwardDate();
    }

    @Then("The transaction should be successfully sent for approval")
    public void verifyTransactionSentForApproval() {
        ibanPage().verifyTransactionSentForApproval();
    }

    @Then("The transaction should be successfully sent for approval and Account")
    public void verifyTransactionSentForApprovalAndAccount() {
        accountPage().verifyTransactionSentForApprovalTypeAccount();
    }

    @When("Enter {string} customer transaction details with different currency for today")
    public void enterCustomerTransactionDetailsWithDifferentCurrencyForToday(String customerType) {
        ibanPage().enterCustomerTransactionDetailsWithDifferentCurrencyForToday(customerType);
    }

    @Then("The different currency error message should be displayed")
    public void theDifferentCurrencyErrorMessageShouldBeDisplayed() {
        ibanPage().theDifferentCurrencyErrorMessageShouldBeDisplayed();
    }

    @Given("Enter {string} transaction details to account for today")
    public void enterTransactionDetailsToAccountForToday(String customerType) {
        accountPage().enterTransactionDetailsToAccountForToday(customerType);
    }

    @When("Enter {string} customer transaction details for {string} days later")
    public void enterCustomerTransactionDetailsForDaysLater(String customerType, String nextDay) {
        ibanPage().enterCustomerTransactionDetailsForDaysLater(customerType, nextDay);
    }

    @And("Enter transaction amount as {string} TL and description")
    public void enterTransactionAmountAsAndDescription(String amount) {
        ibanPage().enterTransactionAmountAndDescription(amount);
    }

    @When("Enter fund transaction details for today")
    public void enterFundTransactionDetailsForToday() {
        ibanPage().enterFundTransactionDetailsForToday();
    }

    @When("Enter fund transaction details to account for today")
    public void enterFundTransactionDetailsToAccountForToday() {
        ibanPage().enterFundTransactionDetailsToAccountForToday();
    }

    @When("Enter {string} customer to account transaction details for {string} days later")
    public void enterCustomerToAccountTransactionDetailsForDaysLater(String customerType, String nextDay) {
        accountPage().enterTransactionDetailsForToday(customerType, nextDay);
    }

    @Then("Verify fund warning error should be displayed")
    public void verifyFundWarningErrorShouldBeDisplayed() {
        ibanPage().verifyFundWarningErrorAsIsDisplayed();
    }

    @And("Click account tab")
    public void clickAccountTab() {
        accountPage().clickAccountTab();
    }

    @When("Click close message button")
    public void clickCloseMessageButton() {
        ibanPage().clickCloseMessageButton();
    }
}
