package org.halkKatilim.stepDefs;

import io.cucumber.java.en.When;
import org.halkKatilim.enums.TextSource;
import org.halkKatilim.pages.BasePages;
import org.halkKatilim.pages.homePage.HomePages;

import static org.halkKatilim.utility.assertionUtil.enums.AssertionKey.*;
import static org.halkKatilim.utility.assertionUtil.enums.AssertionPrefix.HOMEPAGE;

public class HomepageSteps extends HomePages {


    @When("Go to {string} from Homepage")
    public void goToOptionFromHomepage(String option) {
        goToOptionFromHomepagex(option);
    }

    @When("Go to Random Account from Homepage with assertion using {string}")
    public void goToRandomElementsCheckWithText(String order) {
        goToRandomElementsCheckWithTextx(order);
    }

    @When("Go to Random Last Transactions")
    public void goToRandomLastActivities() {
        goToRandomLastActivitiesx();
    }

    @When("Verify currency and amount change on My Assets from Homepage")
    public void verifyAssetsCurrencyToggle() {
        verifyAssetsCurrencyTogglex();
    }
}
