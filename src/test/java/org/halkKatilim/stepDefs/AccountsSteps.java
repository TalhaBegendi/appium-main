package org.halkKatilim.stepDefs;

import io.cucumber.java.en.When;
import org.halkKatilim.pages.AccountsPages;

public class AccountsSteps extends AccountsPages {

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
        selectMaturityAccount(accountType, moneyCurrency,maturityDate);
        fillAccountName(accountName);
        proceedAccountCreation(accountType);
        enterMinimumAmountForMaturity();
        verifyAccountSummary(accountType, accountName, moneyCurrency);
        confirmWithOtp();
        verifySuccessMessage();
    }
}