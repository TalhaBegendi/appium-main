package org.halkKatilim.stepDefs;

import io.cucumber.java.en.When;
import org.halkKatilim.pages.Pages;
import org.halkKatilim.pages.accountsPage.AccountsPages;

public class AccountsSteps{

    Pages pages = new Pages();


    @When("Opens a {string} account with {string} currency")
    public void openDemandAccount(String accountType, String moneyCurrency) {
        String accountName = pages.getAccountsPages().generateAccountName();
        pages.getAccountsPages().selectAccount(accountType, moneyCurrency);
        pages.getAccountsPages().fillAccountName(accountName);
        pages.getAccountsPages().proceedAccountCreation(accountType);
        pages.getAccountsPages().verifyAccountSummary(accountType, accountName, moneyCurrency);
        pages.getAccountsPages().confirmApproval();
        pages.getAccountsPages().verifySuccessMessage();
    }

    @When("Opens a {string} account with {string} currency and {string} maturity date")
    public void openMaturityAccount(String accountType, String moneyCurrency, String maturityDate) {
        String accountName = pages.getAccountsPages().generateAccountName();
        pages.getAccountsPages().selectMaturityAccount(accountType, moneyCurrency,maturityDate);
        pages.getAccountsPages().fillAccountName(accountName);
        pages.getAccountsPages().proceedAccountCreation(accountType);
        pages.getAccountsPages(). enterMinimumAmountForMaturity();
        pages.getAccountsPages().verifyAccountSummary(accountType, accountName, moneyCurrency);
        pages.getAccountsPages().confirmWithOtp();
        pages.getAccountsPages().verifySuccessMessage();
    }
}