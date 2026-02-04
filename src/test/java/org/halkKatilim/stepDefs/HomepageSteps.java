package org.halkKatilim.stepDefs;

import io.cucumber.java.en.When;
import org.halkKatilim.pages.Pages;

public class HomepageSteps {
    Pages pages = new Pages();

    @When("Go to {string} from Homepage")
    public void goToOptionFromHomepage(String option) {
        pages.getHomePage().goToOptionFromHomepage(option);
    }

    @When("Go to Random Account from Homepage with assertion using {string}")
    public void goToRandomElementsCheckWithText(String order) {
        pages.getHomePage().goToRandomElementsCheckWithText(order);
    }

    @When("Go to Random Last Transactions")
    public void goToRandomLastActivities() {
        pages.getHomePage().goToRandomLastActivities();
    }

    @When("Verify currency and amount change on My Assets from Homepage")
    public void verifyAssetsCurrencyToggle() {
        pages.getHomePage().verifyAssetsCurrencyToggle();
    }
}
