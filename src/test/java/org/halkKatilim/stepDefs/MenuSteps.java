package org.halkKatilim.stepDefs;

import io.cucumber.java.en.When;
import org.halkKatilim.pages.menu.MenuPages;
import org.halkKatilim.utility.context.PageContext;

public class MenuSteps {

    private MenuPages menuPages() {
        return PageContext.get().get(MenuPages.class);
    }

    @When("Go to Menu")
    public void goToMenu() {
        menuPages().openMainMenu();
    }

    @When("Go to {string} from Menu")
    public void goToOptionFromMenu(String option) {
        menuPages().navigateToMenuOption(option);
    }
}