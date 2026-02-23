package org.halkKatilim.stepDefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.halkKatilim.pages.Pages;
import org.halkKatilim.pages.accountPage.AccountsPages;

public class AccountsSteps extends AccountsPages {

    Pages pages = new Pages();

    @When("Opens a {string} account with {string} currency")
    public void openDemandAccount(String accountType, String moneyCurrency) {
        String accountName = generateAccountName();
        selectAccount(accountType, moneyCurrency);
        fillAccountName(accountName);
        proceedAccountCreation(accountType);
        verifyAccountSummary(accountType, accountName, moneyCurrency);
        confirmApproval();
        verifySuccessMessage();
    }

    @When("Opens a {string} account with {string} currency and {string} maturity date")
    public void openMaturityAccount(String accountType, String moneyCurrency, String maturityDate) {
        String accountName = generateAccountName();
        selectMaturityAccount(accountType, moneyCurrency, maturityDate);
        fillAccountName(accountName);
        proceedAccountCreation(accountType);
        enterMinimumAmountForMaturity();
        verifyAccountSummary(accountType, accountName, moneyCurrency);
        confirmWithOtp();
        verifySuccessMessage();
    }

    @And("Click open account button")
    public void clickOpenAccountButton() {
        pages.getAccountsPage().clickOpenAccountButton();
    }

    @Then("Verify corporate role authorization error message")
    public void verifyCorporateRoleAuthorizationErrorMessage() {
        pages.getAccountsPage().verifyCorporateRoleAuthorizationErrorMessage();

    }

    @And("Click {string} account type")
    public void clickAccountType(String accountType) {
        pages.getAccountsPage().clickAccountType(accountType);
    }
}