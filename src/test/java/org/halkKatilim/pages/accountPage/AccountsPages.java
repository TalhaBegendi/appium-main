package org.halkKatilim.pages.accountPage;

import org.halkKatilim.deviceConfig.DeviceContext;
import org.halkKatilim.enums.StepsText;
import org.halkKatilim.interfaces.CustomerCapable;
import org.halkKatilim.pages.BasePages;
import org.openqa.selenium.WebElement;

import java.util.Arrays;
import java.util.List;

import static org.halkKatilim.pages.accountPage.AccountsPagesText.*;
import static org.halkKatilim.utility.assertionUtil.enums.AssertionKey.ACCOUNTS;
import static org.halkKatilim.utility.assertionUtil.enums.AssertionKey.SUCCESS_ACCOUNTS;
import static org.testng.Assert.assertTrue;

public class AccountsPages extends BasePages {

    protected void enterMinimumAmountForMaturity() {
        List<String> minAmounts = appiumUtil.getTextElements("minAmountForMaturityAccounts");
        String minAmount = appiumUtil.findMinAmount(minAmounts);
        appiumUtil
                .clearAndFillInputWithScroll("inputAmountAccounts", minAmount)
                .clickElement("continueButtonMaturityItemAccounts");
    }

    protected void confirmWithOtp() {
        confirmApproval();
        CustomerCapable customer = DeviceContext.getCustomer(DeviceContext.getCurrentUserType());
        appiumUtil
                .waitUntilElementLoad("inputSmsOtp")
                .clearAndFillInputWithScroll("inputSmsOtp", customer.getSmsCode())
                .clickElement("smsOtpButtonSendItem");
    }

    protected void confirmApproval() {
        appiumUtil.clickElementWithScroll("approveButtonItemAccounts");
    }

    protected String generateAccountName() {
        return "Otomasyon_" + appiumUtil.generateNumber(4);
    }

    protected void selectAccount(String accountType, String moneyCurrency) {
        final String currencyButtonKey =
                StepsText.INVESTMENT_ACCOUNT.matches(accountType)
                        ? "moneyCurrencyButtonItemInvestmentAccounts"
                        : "moneyCurrencyButtonItemAccounts";
        appiumUtil
                .clickByText("productNameAccounts", accountType)
                .clickElement(currencyButtonKey)
                .clickByText("selectAccountOptionDropdown", moneyCurrency);
    }

    protected void selectMaturityAccount(String accountType, String moneyCurrency, String maturityDate) {
        boolean isGoldCurrency = StepsText.MINE_CURRENCY.matches(moneyCurrency);

        boolean shouldSelectMaturity = (isGoldCurrency && StepsText.GOLD_MATURITY_DATE.matches(maturityDate))
                || (!isGoldCurrency && StepsText.STANDARD_MATURITY_DATE.matches(maturityDate));

        selectAccount(accountType, moneyCurrency);
        appiumUtil.clickElement("maturityDateButtonItemAccounts")
                .clickByText("selectAccountOptionDropdown", maturityDate);

        if (shouldSelectMaturity) {
            appiumUtil
                    .clickElement("maturityPeriodButtonItemAccounts")
                    .clickElement("selectAccountOptionDropdownMaturityPeriod");
        }
    }

    protected void fillAccountName(String accountName) {
        appiumUtil.clearAndFillInputWithScroll("inputAccountNameAccounts", accountName);
    }

    protected void proceedAccountCreation(String accountType) {
        final String accountContinueButtonKey =
                StepsText.INVESTMENT_ACCOUNT.matches(accountType)
                        ? "continueButtonItemInvestmentAccounts"
                        : "continueButtonItemAccounts";
        appiumUtil
                .clickElement("switchAccounts")
                .clickElementWithScroll(accountContinueButtonKey);
    }

    protected void verifyAccountSummary(String accountType, String accountName, String moneyCurrency) {
        List<String> actual = appiumUtil.getTextElements("verifyTextValueAccounts");
        List<String> expected = List.of(accountName, moneyCurrency);
        ACCOUNTS.runAssertionInList(actual, expected);
    }

    protected void verifySuccessMessage() {
        String actual = appiumUtil.getTextElement("verifySuccessAccounts");
        hardAssertion.assertTextInDisplayTexts(actual, SUCCESS_ACCOUNTS);
    }

    private void assertElementTextContainsAny(WebElement element, String... expectedParts) {
        String actualText = element.getText().trim();
        assertTrue(Arrays.stream(expectedParts).anyMatch(actualText::contains),
                "Actual text [" + actualText + "] does not contain any expected values");
    }

    public void verifyCorporateRoleAuthorizationErrorMessage() {
        assertElementTextContainsAny(appiumUtil.findElementSilent("corporateRoleAuthorizationError"),
                CORPORATE_ROLE_AUTHORIZATION_ERROR_TURKISH,CORPORATE_ROLE_AUTHORIZATION_ERROR_ENGLISH);
    }

    public void clickOpenAccountButton() {
        appiumUtil.clickElement("openAccountButtonAccounts");
    }

    public void clickAccountType(String accountType) {
        appiumUtil.clickByText("accountTab",accountType);
    }
}
