package org.halkKatilim.stepDefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.halkKatilim.pages.Pages;

public class CorporateSteps {

    Pages pages = new Pages();

    @When("Enter {string} customer IBAN transfer details to send approval for today")
    public void enterCustomerIbanTransferDetailsToSendApprovalForToday(String customerType) {
        pages.getCorporatePage().enterIbanTransferDetailsToSendApprovalForToday(customerType);
    }


    @Then("Verify the IBAN transfer details sent for approval are correct")
    public void verifyTheIBANTransferDetailsSentForApprovalAreCorrect() {
        pages.getCorporatePage().verifyTheIBANTransferDetailsSentForApprovalAreCorrect();
    }

    @When("Delete the transaction sent for approval")
    public void deleteTheTransactionSentForApproval() {
        pages.getCorporatePage().deleteTheTransactionSentForApproval();
    }

    @When("Approve the transaction sent for approval")
    public void approveTheTransactionSentForApproval() {
        pages.getCorporatePage().approveTheTransactionSentForApproval();
    }

    @Then("Verify transaction success message is displayed")
    public void verifyTransactionSuccessMessageIsDisplayed() {
        pages.getCorporatePage().verifyTransactionSuccessMessageIsDisplayed();
    }

    @And("Click {string} tab")
    public void clickTab(String tabName) {
        pages.getCorporatePage().clickTab(tabName);
    }

    @And("Click back and menu button")
    public void clickBackAndMenuButton() {
        pages.getCorporatePage().clickBackAndMenuButton();
    }

    @When("Reject the transaction sent for approval")
    public void rejectTheTransactionSentForApproval() {
        pages.getCorporatePage().rejectTheTransactionSentForApproval();
    }

    @Then("Verify transaction rejection message is displayed")
    public void verifyTransactionRejectionMessageIsDisplayed() {
        pages.getCorporatePage().verifyTransactionRejectionMessageIsDisplayed();
    }
}
