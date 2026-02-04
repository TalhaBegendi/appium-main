package org.halkKatilim.stepDefs;

import io.cucumber.java.en.When;
import org.halkKatilim.pages.loginPage.LoginPages;

public class LoginSteps extends LoginPages {

    @When("Login as {string} customer {string} using {string} language")
    public void loginToApp(String userType, String customerKey, String langKey) {
        loginToAppx(customerKey, langKey, userType);
    }

    @When("Logout as {string} customer {string} using {string} language")
    public void logoutFromApp(String userType, String customerKey, String langKey) {
        logoutFromAppx(customerKey, langKey, userType);
    }
}