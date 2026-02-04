package org.halkKatilim.pages.homePage;

import lombok.extern.slf4j.Slf4j;
import org.halkKatilim.enums.TextSource;
import org.halkKatilim.pages.BasePages;

import static org.halkKatilim.pages.homePage.HomePageText.*;
import static org.halkKatilim.utility.assertionUtil.enums.AssertionKey.ACCOUNTS;
import static org.halkKatilim.utility.assertionUtil.enums.AssertionKey.LAST_TRANSACTIONS_SLIP;
import static org.halkKatilim.utility.assertionUtil.enums.AssertionPrefix.HOMEPAGE;

@Slf4j
public  class HomePages extends BasePages {

    public HomePages() {}

    public void goToOptionFromHomepagex(String option) {
        appiumUtil.navigate(option, "pageTitleHomepage", null, HOMEPAGE);
        log.info(LOG_HOMEPAGE_NAVIGATE_OPTION, option);
    }

    public void goToRandomElementsCheckWithTextx(String order) {
        TextSource source = order == null || order.isBlank() ? null : TextSource.valueOf(order);

        String actualText = appiumUtil.clickRandomElementGetText("accountListAvailableBalanceHomepageItem", source);
        String expectedText = appiumUtil.getTextElement("accountAvailableBalanceItem", source);

        ACCOUNTS.runAssertion(actualText, expectedText);

        log.info(LOG_RANDOM_ACCOUNT_TEXT_CHECK, actualText, expectedText);
    }

    public void goToRandomLastActivitiesx() {
        appiumUtil.clickRandomElement("last10TransactionsTitleHomepage");
        LAST_TRANSACTIONS_SLIP.runAssertion();

        log.info(LOG_RANDOM_LAST_ACTIVITIES_OPENED);
    }

    public void verifyAssetsCurrencyTogglex() {
        appiumUtil.verifyAssetsCurrencyToggle("myAssetsCurrentAmountText", "myAssetsCurrencyToggleButton");
        log.info(LOG_ASSETS_CURRENCY_TOGGLE_VERIFIED);
    }
}
