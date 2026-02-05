package org.halkKatilim.stepDefs;

import io.cucumber.java.en.When;
import org.halkKatilim.pages.Pages;

public class HomepageSteps {
    Pages pages = new Pages();

    @When("Go to {string} from Homepage")
    public void goToOptionFromHomepage(String option) {
<<<<<<< HEAD
        navigateToHomePageOption(option);
=======
        pages.getHomePage().goToOptionFromHomepage(option);
>>>>>>> b5bcdeda588b8b989818c31796b8516e7cb3dadf
    }

    @When("Go to Random Account from Homepage with assertion using {string}")
    public void goToRandomElementsCheckWithText(String order) {
<<<<<<< HEAD
        selectRandomAccountAndVerifyBalanceText(order);
=======
        pages.getHomePage().goToRandomElementsCheckWithText(order);
>>>>>>> b5bcdeda588b8b989818c31796b8516e7cb3dadf
    }

    @When("Go to Random Last Transactions")
    public void goToRandomLastActivities() {
<<<<<<< HEAD
        openRandomLastTransactionAndVerifySlip();
=======
        pages.getHomePage().goToRandomLastActivities();
>>>>>>> b5bcdeda588b8b989818c31796b8516e7cb3dadf
    }

    @When("Verify currency and amount change on My Assets from Homepage")
    public void verifyAssetsCurrencyToggle() {
<<<<<<< HEAD
        toggleAssetsCurrencyAndVerifyAmount();
=======
        pages.getHomePage().verifyAssetsCurrencyToggle();
>>>>>>> b5bcdeda588b8b989818c31796b8516e7cb3dadf
    }
}
