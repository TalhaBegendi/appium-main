package org.halkKatilim.stepDefs;

import io.cucumber.java.en.When;
import org.halkKatilim.pages.Pages;

public class MenuSteps{

    Pages pages = new Pages();

    @When("Go to Menu")
    public void goToMenu() {
<<<<<<< HEAD
        openMainMenu();
=======
        pages.getMenuPage().goToMenu();
>>>>>>> b5bcdeda588b8b989818c31796b8516e7cb3dadf
    }

    @When("Go to {string} from Menu")
    public void goToOptionFromMenu(String option) {
<<<<<<< HEAD
        navigateToMenuOption(option);
=======
        pages.getMenuPage().goToOptionFromMenu(option);
>>>>>>> b5bcdeda588b8b989818c31796b8516e7cb3dadf

    }

    @When("Go to {string} from Menu with assertion {string} of type {string} using {string}")
    public void goToFromMenuUnified(String path, String assertion, String assertionModeKey, String order) {
<<<<<<< HEAD
        navigateMenuPathAndVerify(path, assertion, assertionModeKey, order);
=======
        pages.getMenuPage().goToFromMenuUnified(path, assertion, assertionModeKey, order);
>>>>>>> b5bcdeda588b8b989818c31796b8516e7cb3dadf
    }
}