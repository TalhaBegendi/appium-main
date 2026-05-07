package org.halkKatilim.stepDefs;

import org.halkKatilim.utility.context.PageContext;
import org.halkKatilim.pages.corporate.CorporatePage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CorporateSteps {

    private CorporatePage corporatePage() {
        return PageContext.get().get(CorporatePage.class);
    }

    @When("Enter {string} customer IBAN transfer details to send approval for today")
    public void enterCustomerIbanTransferDetailsToSendApprovalForToday(String customerType) {
        corporatePage().enterIbanTransferDetailsToSendApprovalForToday(customerType);
    }

    @Then("Verify the IBAN transfer details sent for approval are correct")
    public void verifyTheIBANTransferDetailsSentForApprovalAreCorrect() {
        corporatePage().verifyTheIBANTransferDetailsSentForApprovalAreCorrect();
    }

    @When("Delete the transaction sent for approval")
    public void deleteTheTransactionSentForApproval() {
        corporatePage().deleteTheTransactionSentForApproval();
    }

    @When("Approve the transaction sent for approval")
    public void approveTheTransactionSentForApproval() {
        corporatePage().approveTheTransactionSentForApproval();
    }

    @Then("Verify transaction success message is displayed")
    public void verifyTransactionSuccessMessageIsDisplayed() {
        corporatePage().verifyTransactionSuccessMessageIsDisplayed();
    }

    @And("Click {string} tab")
    public void clickTab(String tabName) {
        corporatePage().clickTab(tabName);
    }

    @And("Click back and menu button")
    public void clickBackAndMenuButton() {
        corporatePage().clickBackAndMenuButton();
    }

    @When("Reject the transaction sent for approval")
    public void rejectTheTransactionSentForApproval() {
        corporatePage().rejectTheTransactionSentForApproval();
    }

    @Then("Verify transaction rejection message is displayed")
    public void verifyTransactionRejectionMessageIsDisplayed() {
        corporatePage().verifyTransactionRejectionMessageIsDisplayed();
    }
}