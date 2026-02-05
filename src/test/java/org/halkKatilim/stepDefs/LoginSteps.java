package org.halkKatilim.stepDefs;

import io.cucumber.java.en.When;
import org.halkKatilim.pages.Pages;

public class LoginSteps {
    Pages pages = new Pages();

    @When("Login as {string} customer {string} using {string} language")
    public void loginToApp(String userType, String customerKey, String langKey) {
<<<<<<< HEAD
        loginToApplication(customerKey, langKey, userType);
=======
        pages.getLoginPage().loginToApp(customerKey, langKey, userType);
>>>>>>> b5bcdeda588b8b989818c31796b8516e7cb3dadf
    }

    @When("Logout as {string} customer {string} using {string} language")
    public void logoutFromApp(String userType, String customerKey, String langKey) {
<<<<<<< HEAD
        logoutFromApplication(customerKey, langKey, userType);
=======
        pages.getLoginPage().logoutFromApp(customerKey, langKey, userType);
>>>>>>> b5bcdeda588b8b989818c31796b8516e7cb3dadf
    }
}