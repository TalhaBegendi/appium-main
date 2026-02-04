package org.halkKatilim.stepDefs;

import io.cucumber.java.en.When;
import org.halkKatilim.pages.Pages;

public class MenuSteps{

    Pages pages = new Pages();

    @When("Go to Menu")
    public void goToMenu() {
        pages.getMenuPage().goToMenu();
    }

    @When("Go to {string} from Menu")
    public void goToOptionFromMenu(String option) {
        pages.getMenuPage().goToOptionFromMenu(option);

    }

    @When("Go to {string} from Menu with assertion {string} of type {string} using {string}")
    public void goToFromMenuUnified(String path, String assertion, String assertionModeKey, String order) {
        pages.getMenuPage().goToFromMenuUnified(path, assertion, assertionModeKey, order);
    }
}