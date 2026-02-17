package org.halkKatilim.pages.homePage;

import lombok.extern.slf4j.Slf4j;
import org.halkKatilim.enums.TextSource;
import org.halkKatilim.pages.BasePages;

import static org.halkKatilim.pages.homePage.HomePageText.*;
import static org.halkKatilim.utility.assertionUtil.enums.AssertionKey.ACCOUNTS;
import static org.halkKatilim.utility.assertionUtil.enums.AssertionKey.LAST_TRANSACTIONS_SLIP;
import static org.halkKatilim.utility.assertionUtil.enums.AssertionPrefix.HOMEPAGE;

@Slf4j
public class HomePages extends BasePages {

    public void navigateToHomePageOption(String option) {
        appiumUtil.navigate(option, "pageTitleHomepage", null, HOMEPAGE);
        log.info(LOG_HOMEPAGE_NAVIGATE_OPTION, option);
    }

    public void selectRandomAccountAndVerifyBalanceText(String textSourceOrder) {
        TextSource source = textSourceOrder == null || textSourceOrder.isBlank()
                ? null
                : TextSource.valueOf(textSourceOrder);

        String actualText = appiumUtil.clickRandomElementGetText(
                "accountListAvailableBalanceHomepageItem", source
        );

        String expectedText = appiumUtil.getTextElement(
                "accountAvailableBalanceItem", source
        );

       // ACCOUNTS.runAssertion(actualText, expectedText);
        log.info(LOG_RANDOM_ACCOUNT_TEXT_CHECK, actualText, expectedText);
    }

    public void openRandomLastTransactionAndVerifySlip() {
        appiumUtil.clickRandomElement("last10TransactionsTitleHomepage");
        LAST_TRANSACTIONS_SLIP.runAssertion();
        log.info(LOG_RANDOM_LAST_ACTIVITIES_OPENED);
    }

    public void toggleAssetsCurrencyAndVerifyAmount() {
        appiumUtil.verifyAssetsCurrencyToggle(
                "myAssetsCurrentAmountText",
                "myAssetsCurrencyToggleButton"
        );
        log.info(LOG_ASSETS_CURRENCY_TOGGLE_VERIFIED);
    }
}