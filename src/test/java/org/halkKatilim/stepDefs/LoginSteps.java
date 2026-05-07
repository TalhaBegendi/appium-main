package org.halkKatilim.stepDefs;

import org.halkKatilim.utility.context.PageContext;
import org.halkKatilim.pages.loginPage.LoginPages;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;

public class LoginSteps {

    private LoginPages loginPage() {
        return PageContext.get().get(LoginPages.class);
    }

    @When("Login as {string} customer {string} using {string} language")
    public void loginToApp(String userType, String customerKey, String langKey) {
        loginPage().loginToApplication(customerKey, langKey, userType);
    }

    @When("Logout as {string} customer {string} using {string} language")
    public void logoutFromApp(String userType, String customerKey, String langKey) {
        loginPage().logoutFromApplication(customerKey, langKey, userType);
    }

    @And("Log out from the application using {string} language")
    public void logOutFromTheApplicationUsingLanguage(String langKey) {
        loginPage().logOutUsingLanguage(langKey);
    }

    @And("Login as {string} customer {string} role")
    public void loginAsCustomerRole(String customerKey, String userTypeKey) {
        loginPage().loginToApplicationAsCorporateUser(customerKey, userTypeKey);
    }
}