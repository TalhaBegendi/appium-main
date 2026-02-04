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
public  class LoginPages extends BasePages {

    public LoginPages() {}

    public void loginToAppx(String customerKey, String langKey, String userTypeKey) {

        UserType userType = UserType.valueOf(userTypeKey.toUpperCase());
        log.info(LOG_LOGIN_START, userType, customerKey, langKey);

        switch (userType) {
            case RETAIL -> loginToAppWithRetailUser(customerKey, langKey);
            case CORPORATE -> loginToAppWithCorporateUser(customerKey, langKey);
        }
    }

    private void loginToAppWithRetailUser(String customerKey, String langKey) {

        RetailCustomer retailCustomer = RetailCustomer.valueOf(customerKey);
        Language lang = Language.valueOf(langKey);

        log.info(LOG_LOGIN_RETAIL_FLOW, customerKey, lang);

        loginCommonFlow(lang, () -> appiumUtil
                .clearAndFillInputWithScroll("inputCustomerNumber", retailCustomer.getNumber())
                .clearAndFillInputWithScroll("inputPassword", retailCustomer.getPassword()));

        completeOtpFlow(retailCustomer.getSmsCode());
    }

    private void loginToAppWithCorporateUser(String customerKey, String langKey) {

        CorporateCustomer corporateCustomer = CorporateCustomer.valueOf(customerKey);
        Language lang = Language.valueOf(langKey);

        log.info(LOG_LOGIN_CORPORATE_FLOW, customerKey, lang);

        loginCommonFlow(lang, () -> appiumUtil
                .clickElement("corporateUserTab")
                .clearAndFillInputWithScroll("inputCustomerNumber", corporateCustomer.getNumber())
                .clearAndFillInputWithScroll("inputMsisdnNumber", corporateCustomer.getMsisdn())
                .clearAndFillInputWithEnter("inputPassword", corporateCustomer.getPassword())
                .hideKeyboardIfNeeded());
        completeOtpFlow(corporateCustomer.getSmsCode());
    }

    private void completeOtpFlow(String smsCode) {

        log.info(LOG_OTP_FLOW_START);
        appiumUtil
                .waitUntilElementLoad("smsOtpComponent")
                .clearAndFillInputWithScroll("inputSmsOtp", smsCode)
                .clickElement("smsOtpButtonSendItem")
                .autoHandleNavigationGates(NavigationGates.Context.LOGIN);

        LOGIN.runAssertion();

        log.info(LOG_OTP_FLOW_COMPLETED);
    }

    private void loginCommonFlow(Language lang, Runnable fillUserFields) {

        DeviceContext.setLanguage(lang);

        appiumUtil
                .clickElement("continueButtonItem")
                .clickElement("buttonLanguageItem")
                .clickByText("languageListItem", lang.getDisplay())
                .clickElement("buttonLoginItem");

        fillUserFields.run();

        appiumUtil
                .clickElement("buttonActivationItem")
                .waitUntilElementLoad("inputSmsOtp");

        log.info(LOG_LOGIN_COMMON_FLOW_COMPLETED, lang);
    }

    public void logoutFromAppx(String customerKey, String langKey, String userTypeKey) {

        Language lang = Language.valueOf(langKey);
        log.info(LOG_LOGOUT_START, customerKey, lang, userTypeKey);

        loginToAppx(customerKey, langKey, userTypeKey);
        new MenuPages().goToMenux();

        appiumUtil.clickElement("logoutButtonItem")
                .clickByAnyText("logoutButtonItem", lang.getLogoutTexts());
        BUTTON_LOGIN_ITEM.runAssertion();

        log.info(LOG_LOGOUT_COMPLETED);
    }
}
