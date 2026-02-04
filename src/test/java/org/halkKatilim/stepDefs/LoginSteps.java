package org.halkKatilim.stepDefs;

import io.cucumber.java.en.When;
import org.halkKatilim.pages.Pages;

public class LoginSteps {
    Pages pages = new Pages();

    @When("Login as {string} customer {string} using {string} language")
    public void loginToApp(String userType, String customerKey, String langKey) {
        pages.getLoginPage().loginToApp(customerKey, langKey, userType);
    }

    @When("Logout as {string} customer {string} using {string} language")
    public void logoutFromApp(String userType, String customerKey, String langKey) {
        pages.getLoginPage().logoutFromApp(customerKey, langKey, userType);
    }
}