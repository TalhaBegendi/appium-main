package org.halkKatilim.pages.loginPage;

import lombok.extern.slf4j.Slf4j;
import org.halkKatilim.deviceConfig.DeviceContext;
import org.halkKatilim.enums.Language;
import org.halkKatilim.enums.NavigationGates;
import org.halkKatilim.enums.UserType;
import org.halkKatilim.enums.corporate.CorporateCustomer;
import org.halkKatilim.enums.retail.RetailCustomer;
import org.halkKatilim.pages.BasePages;
import org.halkKatilim.pages.menu.MenuPages;

import static org.halkKatilim.pages.loginPage.LoginPageText.*;
import static org.halkKatilim.utility.assertionUtil.enums.AssertionKey.BUTTON_LOGIN_ITEM;
import static org.halkKatilim.utility.assertionUtil.enums.AssertionKey.LOGIN;

@Slf4j
public class LoginPages extends BasePages {

    public void loginToApplication(String customerKey, String langKey, String userTypeKey) {

        UserType userType = UserType.valueOf(userTypeKey.toUpperCase());
        log.info(LOG_LOGIN_START, userType, customerKey, langKey);

        switch (userType) {
            case RETAIL -> loginWithRetailCustomer(customerKey, langKey);
            case CORPORATE -> loginWithCorporateCustomer(customerKey, langKey);
        }
    }

    private void loginWithRetailCustomer(String customerKey, String langKey) {

        RetailCustomer customer = RetailCustomer.valueOf(customerKey);
        Language language = Language.valueOf(langKey);

        log.info(LOG_LOGIN_RETAIL_FLOW, customerKey, language);

        executeCommonLoginFlow(language, () -> appiumUtil
                .clearAndFillInputWithScroll("inputCustomerNumber", customer.getNumber())
                .clearAndFillInputWithScroll("inputPassword", customer.getPassword())
        );

        completeOtpVerification(customer.getSmsCode());
    }

    private void loginWithCorporateCustomer(String customerKey, String langKey) {

        CorporateCustomer customer = CorporateCustomer.valueOf(customerKey);
        Language language = Language.valueOf(langKey);
        log.info(LOG_LOGIN_CORPORATE_FLOW, customerKey, language);

        executeCommonLoginFlow(language, () -> appiumUtil
                .clickElement("corporateUserTab")
                .waitUntilElementLoad("inputMsisdnNumber")
                .clearAndFillInputWithScroll("inputCustomerNumber", customer.getNumber())
                .clearAndFillInputWithScroll("inputMsisdnNumber", customer.getMsisdn())
                .clearAndFillInputWithEnter("inputPassword", customer.getPassword())
                .hideKeyboardIfNeeded()
        );

        completeOtpVerification(customer.getSmsCode());
    }

    private void executeCommonLoginFlow(Language language, Runnable fillCredentials) {
        DeviceContext.setLanguage(language);
        appiumUtil
                .clickElement("continueButtonItem")
                .clickElement("buttonLanguageItem")
                .clickByText("languageListItem", language.getDisplay())
                .clickElement("buttonLoginItem");
        fillCredentials.run();
        appiumUtil
                .clickElement("buttonActivationItem")
                .waitUntilElementLoad("inputSmsOtp");
        log.info(LOG_LOGIN_COMMON_FLOW_COMPLETED, language);
    }

    private void completeOtpVerification(String smsCode) {
        log.info(LOG_OTP_FLOW_START);
        appiumUtil
                .waitUntilElementLoad("smsOtpComponent")
                .clearAndFillInputWithScroll("inputSmsOtp", smsCode)
                .clickElement("smsOtpButtonSendItem")
                .autoHandleNavigationGates(NavigationGates.Context.LOGIN);
        LOGIN.runAssertion();
        log.info(LOG_OTP_FLOW_COMPLETED);
    }

    public void logoutFromApplication(String customerKey, String langKey, String userTypeKey) {
        Language language = Language.valueOf(langKey);
        log.info(LOG_LOGOUT_START, customerKey, language, userTypeKey);
        loginToApplication(customerKey, langKey, userTypeKey);
        new MenuPages().openMainMenu();
        appiumUtil
                .clickElement("logoutButtonItem")
                .clickByAnyText("logoutButtonItem", language.getLogoutTexts());

        BUTTON_LOGIN_ITEM.runAssertion();
        log.info(LOG_LOGOUT_COMPLETED);
    }
}