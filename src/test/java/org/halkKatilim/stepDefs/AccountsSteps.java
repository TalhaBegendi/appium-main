package org.halkKatilim.stepDefs;

import org.halkKatilim.utility.context.PageContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.halkKatilim.pages.accountPage.AccountsPages;

public class AccountsSteps {

    private AccountsPages accountsPages() {
        return PageContext.get().get(AccountsPages.class);
    }

    @When("Opens a {string} account with {string} currency")
    public void openDemandAccount(String accountType, String moneyCurrency) {
        String accountName = accountsPages().generateAccountName();
        accountsPages().selectAccount(accountType, moneyCurrency)
        .fillAccountName(accountName)
        .proceedAccountCreation(accountType)
        .verifyAccountSummary(accountName, moneyCurrency);
        accountsPages().confirmApproval()
        .verifySuccessMessage();
    }

    @When("Opens a {string} account with {string} currency and {string} maturity date")
    public void openMaturityAccount(String accountType, String moneyCurrency, String maturityDate) {
        String accountName = accountsPages().generateAccountName();
        accountsPages().selectMaturityAccount(accountType, moneyCurrency, maturityDate)
                .fillAccountName(accountName)
                .proceedAccountCreation(accountType)
                .enterMinimumAmountForMaturity()
                .verifyAccountSummary(accountName, moneyCurrency);
        accountsPages().confirmWithOtp()
        .verifySuccessMessage();
    }

    @And("Click open account button")
    public void clickOpenAccountButton() {
        accountsPages().clickOpenAccountButton();
    }

    @Then("Verify corporate role authorization error message")
    public void verifyCorporateRoleAuthorizationErrorMessage() {
        accountsPages().verifyCorporateRoleAuthorizationErrorMessage();

    }

    @And("Click {string} account type")
    public void clickAccountType(String accountType) {
        accountsPages().clickAccountType(accountType);
    }
}