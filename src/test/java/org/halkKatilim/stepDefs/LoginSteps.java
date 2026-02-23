package org.halkKatilim.stepDefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import org.halkKatilim.pages.Pages;

public class LoginSteps {

    Pages pages = new Pages();

    @When("Login as {string} customer {string} using {string} language")
    public void loginToApp(String userType, String customerKey, String langKey) {
        pages.getLoginPage().loginToApplication(customerKey, langKey, userType);
    }

    @When("Logout as {string} customer {string} using {string} language")
    public void logoutFromApp(String userType, String customerKey, String langKey) {
        pages.getLoginPage().logoutFromApplication(customerKey, langKey, userType);
    }

    @And("Log out from the application using {string} language")
    public void logOutFromTheApplicationUsingLanguage(String langKey) {
        pages.getLoginPage().logOutUsingLanguage(langKey);
    }

    @And("Login as {string} customer {string} role")
    public void loginAsCustomerRole(String customerKey, String userTypeKey) {
        pages.getLoginPage().loginToApplicationAsCorporateUser(customerKey, userTypeKey);
    }
}