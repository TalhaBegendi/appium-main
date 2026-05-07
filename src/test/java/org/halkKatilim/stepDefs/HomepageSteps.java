package org.halkKatilim.stepDefs;

import io.cucumber.java.en.When;
import org.halkKatilim.pages.homePage.HomePages;
import org.halkKatilim.utility.context.PageContext;

public class HomepageSteps {

    private HomePages homePages() {
        return PageContext.get().get(HomePages.class);
    }

    @When("Go to {string} from Homepage")
    public void goToOptionFromHomepage(String option) {
        homePages().navigateToHomePageOption(option);
    }

    @When("Go to Random Account from Homepage with assertion using {string}")
    public void goToRandomElementsCheckWithText(String order) {
        homePages().selectRandomAccountAndVerifyBalanceText(order);
    }

    @When("Go to Random Last Transactions")
    public void goToRandomLastActivities() {
        homePages().openRandomLastTransactionAndVerifySlip();
    }

    @When("Verify currency and amount change on My Assets from Homepage")
    public void verifyAssetsCurrencyToggle() {
        homePages().toggleAssetsCurrencyAndVerifyAmount();
    }
}
