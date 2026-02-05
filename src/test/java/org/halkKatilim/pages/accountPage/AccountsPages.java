<<<<<<<< HEAD:src/test/java/org/halkKatilim/pages/accountPage/AccountsPages.java
package org.halkKatilim.pages.accountPage;
========
package org.halkKatilim.pages.accountsPage;
>>>>>>>> b5bcdeda588b8b989818c31796b8516e7cb3dadf:src/test/java/org/halkKatilim/pages/accountsPage/AccountsPages.java

import org.halkKatilim.deviceConfig.DeviceContext;
import org.halkKatilim.enums.retail.RetailCustomer;
import org.halkKatilim.enums.StepsText;
import org.halkKatilim.pages.BasePages;

import java.util.List;
import static org.halkKatilim.utility.assertionUtil.enums.AssertionKey.ACCOUNTS;
import static org.halkKatilim.utility.assertionUtil.enums.AssertionKey.SUCCESS_ACCOUNTS;

public class AccountsPages extends BasePages {

    public void enterMinimumAmountForMaturity() {
        List<String> minAmounts = appiumUtil.getTextElements("minAmountForMaturityAccounts");
        String minAmount = appiumUtil.findMinAmount(minAmounts);
        appiumUtil
                .clearAndFillInputWithScroll("inputAmountAccounts", minAmount)
                .clickElement("continueButtonMaturityItemAccounts");
    }

    public void confirmWithOtp() {
        RetailCustomer retailCustomer = DeviceContext.getCustomer();
        confirmApproval();
        appiumUtil
                .waitUntilElementLoad("inputSmsOtp")
                .clearAndFillInputWithScroll("inputSmsOtp", retailCustomer.getSmsCode())
                .clickElement("smsOtpButtonSendItem");
    }

    public void confirmApproval() {
        appiumUtil.clickElementWithScroll("approveButtonItemAccounts");
    }

    public String generateAccountName() {
        return "Otomasyon_" + appiumUtil.generateNumber(4);
    }

    public void selectAccount(String accountType, String moneyCurrency) {
        final String currencyButtonKey =
                StepsText.INVESTMENT_ACCOUNT.matches(accountType)
                        ? "moneyCurrencyButtonItemInvestmentAccounts"
                        : "moneyCurrencyButtonItemAccounts";
        appiumUtil
                .clickByText("productNameAccounts", accountType)
                .clickElement(currencyButtonKey)
                .clickByText("selectAccountOptionDropdown", moneyCurrency);
    }

    public void selectMaturityAccount(String accountType, String moneyCurrency, String maturityDate) {
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

    public void fillAccountName(String accountName) {
        appiumUtil.clearAndFillInputWithScroll("inputAccountNameAccounts", accountName);
    }

    public void proceedAccountCreation(String accountType) {
        final String accountContinueButtonKey =
                StepsText.INVESTMENT_ACCOUNT.matches(accountType)
                        ? "continueButtonItemInvestmentAccounts"
                        : "continueButtonItemAccounts";
        appiumUtil
                .clickElement("switchAccounts")
                .clickElement(accountContinueButtonKey);
    }

    public void verifyAccountSummary(String accountType, String accountName, String moneyCurrency) {
        List<String> actual = appiumUtil.getTextElements("verifyTextValueAccounts");
        List<String> expected = List.of(accountName, moneyCurrency);
        ACCOUNTS.runAssertionInList(actual, expected);
    }

    public void verifySuccessMessage() {
        String actual = appiumUtil.getTextElement("verifySuccessAccounts");
        hardAssertion.assertTextInDisplayTexts(actual, SUCCESS_ACCOUNTS);
    }
}
