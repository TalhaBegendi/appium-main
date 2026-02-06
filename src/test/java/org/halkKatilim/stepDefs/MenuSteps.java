package org.halkKatilim.stepDefs;

import io.cucumber.java.en.When;
import org.halkKatilim.pages.menu.MenuPages;

public class MenuSteps extends MenuPages {

    @When("Go to Menu")
    public void goToMenu() {
        openMainMenu();
    }

    @When("Go to {string} from Menu")
    public void goToOptionFromMenu(String option) {
        navigateToMenuOption(option);

    }

    @When("Go to {string} from Menu with assertion {string} of type {string} using {string}")
    public void goToFromMenuUnified(String path, String assertion, String assertionModeKey, String order) {
        navigateMenuPathAndVerify(path, assertion, assertionModeKey, order);
    }
}