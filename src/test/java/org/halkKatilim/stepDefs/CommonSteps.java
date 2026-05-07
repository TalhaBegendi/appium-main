package org.halkKatilim.stepDefs;

import io.cucumber.java.en.When;
import org.halkKatilim.utility.appiumUtil.AppiumUtil;

public class CommonSteps {

    private final AppiumUtil appiumUtil = new AppiumUtil();

    @When("I enter username {string}")
    public void iEnterUsername(String username) {
        appiumUtil.fillInputKeyboard("usernameTxtFld", username, true, true);
    }

    @When("Click element {string}")
    public void clickElement(String key) {
        appiumUtil.clickElement(key);
    }

    @When("Fill {string} field with {string}")
    public void clearAndFillInput(String key, String text) {
        appiumUtil.fillInputKeyboard(key, text, true, true);
    }

    @When("Wait {int} seconds")
    public void waitSeconds(int seconds) {
        appiumUtil.waitBySecond(seconds);
    }

    @When("Click element by {string} with text {string}")
    public void clickByText(String key, String text) {
        appiumUtil.clickByText(key, text);
    }
}