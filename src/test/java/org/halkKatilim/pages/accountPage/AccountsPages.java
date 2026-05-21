package org.halkKatilim.pages.accountPage;

import org.halkKatilim.deviceConfig.DeviceContext;
import org.halkKatilim.enums.StepsText;
import org.halkKatilim.interfaces.CustomerCapable;
import org.openqa.selenium.WebElement;
import java.util.Arrays;
import java.util.List;
import static org.halkKatilim.pages.accountPage.AccountsPagesText.*;
import static org.halkKatilim.utility.assertionUtil.enums.AssertionKey.ACCOUNTS;
import static org.halkKatilim.utility.assertionUtil.enums.AssertionKey.SUCCESS_ACCOUNTS;
import static org.testng.Assert.assertTrue;
import lombok.RequiredArgsConstructor;
import org.halkKatilim.utility.appiumUtil.AppiumUtil;

@RequiredArgsConstructor
public class AccountsPages {

    private final AppiumUtil appiumUtil;

    public AccountsPages enterMinimumAmountForMaturity() {
        appiumUtil.waitUntilElementLoad("continueButtonMaturityItemAccounts");
        List<String> minAmounts = appiumUtil.getTextElements("minAmountForMaturityAccounts");
        String minAmount = appiumUtil.findMinAmount(minAmounts);

        appiumUtil
                .fillInputKeyboard("inputAmountAccounts", minAmount, true, true)
                .clickElement("continueButtonMaturityItemAccounts");

        return this;
    }

    public AccountsPages confirmWithOtp() {
        confirmApproval();

        CustomerCapable customer = DeviceContext.getCustomer(DeviceContext.getCurrentUserType());

        appiumUtil
                .waitUntilElementLoad("inputSmsOtp")
                .fillInputKeyboard("inputSmsOtp", customer.getSmsCode(), true, true)
                .clickElement("smsOtpButtonSendItem");

        return this;
    }

    public AccountsPages confirmApproval() {
        appiumUtil.clickElementWithScroll("approveButtonItemAccounts");
        return this;
    }

    public String generateAccountName() {
        return "Otomasyon_" + appiumUtil.generateNumber(4);
    }

    private boolean isInvestmentSelection(String accountType, String moneyCurrency) {
        return StepsText.INVESTMENT_ACCOUNT.matches(accountType)
                && StepsText.INVESTMENT_CURRENCY.matches(moneyCurrency);
    }

    public AccountsPages selectAccount(String accountType, String moneyCurrency) {
        final String currencyButtonKey =
                StepsText.SELECT_ACCOUNT.matches(accountType)
                        ? "moneyCurrencyButtonItemInvestmentAccounts"
                        : "moneyCurrencyButtonItemAccounts";

        appiumUtil.clickByText("productNameAccounts", accountType);

        if (!isInvestmentSelection(accountType, moneyCurrency)) {
            appiumUtil
                    .clickElement(currencyButtonKey)
                    .clickByText("selectAccountOptionDropdown", moneyCurrency);
        }

        return this;
    }

    public AccountsPages selectMaturityAccount(String accountType, String moneyCurrency, String maturityDate) {

        boolean isGoldCurrency = StepsText.MINE_CURRENCY.matches(moneyCurrency);

        boolean shouldSelectMaturity =
                (isGoldCurrency && StepsText.GOLD_MATURITY_DATE.matches(maturityDate))
                        || (!isGoldCurrency && StepsText.STANDARD_MATURITY_DATE.matches(maturityDate));

        selectAccount(accountType, moneyCurrency);

        appiumUtil.clickElement("maturityDateButtonItemAccounts")
                .clickByText("selectAccountOptionDropdown", maturityDate);

        if (shouldSelectMaturity) {
            appiumUtil
                    .clickElement("maturityPeriodButtonItemAccounts")
                    .clickElement("selectAccountOptionDropdownMaturityPeriod");
        }

        return this;
    }

    public AccountsPages fillAccountName(String accountName) {
        appiumUtil.fillInputKeyboard("inputAccountNameAccounts", accountName, true, true);
        return this;
    }

    public AccountsPages proceedAccountCreation(String accountType) {

        final String accountContinueButtonKey =
                StepsText.INVESTMENT_ACCOUNT.matches(accountType)
                        ? "continueButtonItemInvestmentAccounts"
                        : "continueButtonItemAccounts";

        appiumUtil
                .clickElement("switchAccounts")
                .clickElementWithScroll(accountContinueButtonKey);

        return this;
    }

    public void verifyAccountSummary(String accountName, String moneyCurrency) {
        List<String> actual = appiumUtil.getTextElements("verifyTextValueAccounts");
        List<String> expected = List.of(accountName, moneyCurrency);
        ACCOUNTS.runAssertionInList(actual, expected);
    }

    public void verifySuccessMessage() {
        appiumUtil.waitUntilElementLoad("verifySuccessAccounts");
        String actual = appiumUtil.getTextElement("verifySuccessAccounts");
        appiumUtil.getAssertion().assertTextInDisplayTexts(actual, SUCCESS_ACCOUNTS);
    }

    private void assertElementTextContainsAny(WebElement element, String... expectedParts) {
        String actualText = element.getText().trim();

        assertTrue(
                Arrays.stream(expectedParts).anyMatch(actualText::contains),
                "Actual text [" + actualText + "] does not contain any expected values"
        );
    }

    public void verifyCorporateRoleAuthorizationErrorMessage() {
        assertElementTextContainsAny(
                appiumUtil.findElementSilent("corporateRoleAuthorizationError"),
                CORPORATE_ROLE_AUTHORIZATION_ERROR_TURKISH,
                CORPORATE_ROLE_AUTHORIZATION_ERROR_ENGLISH
        );
    }

    public AccountsPages clickOpenAccountButton() {
        appiumUtil.clickElement("openAccountButtonAccounts");
        return this;
    }

    public AccountsPages clickAccountType(String accountType) {
        appiumUtil.clickByText("accountTab", accountType);
        return this;
    }
}
