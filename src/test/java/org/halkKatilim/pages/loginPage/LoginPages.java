package org.halkKatilim.pages.loginPage;

import lombok.extern.slf4j.Slf4j;
import org.halkKatilim.deviceConfig.DeviceContext;
import org.halkKatilim.enums.Language;
import org.halkKatilim.enums.NavigationGates;
import org.halkKatilim.enums.UserType;
import org.halkKatilim.enums.corporate.CorporateCustomer;
import org.halkKatilim.enums.retail.RetailCustomer;
import org.halkKatilim.interfaces.CustomerCapable;
import org.halkKatilim.pages.BasePages;
import org.halkKatilim.pages.menu.MenuPages;
import org.halkKatilim.testData.retail.moneyTransfer.CustomerEntry;

import static org.halkKatilim.pages.loginPage.LoginPageText.*;
import static org.halkKatilim.utility.assertionUtil.enums.AssertionKey.BUTTON_LOGIN_ITEM;
import static org.halkKatilim.utility.assertionUtil.enums.AssertionKey.LOGIN;

@Slf4j
public class LoginPages extends BasePages {

    private record LoginContext(CustomerCapable customer, Runnable credentialFiller) {}

    public void loginToApplication(String customerKey, String langKey, String userTypeKey) {
        UserType userType = UserType.valueOf(userTypeKey.toUpperCase());
        Language language = Language.valueOf(langKey);

        log.info(LOG_LOGIN_START, userType, customerKey, langKey);
        DeviceContext.setCurrentUserType(userType);
        DeviceContext.setLanguage(language);

        handleInitialLanguageSelection(language);
        performLoginSequence(userType, customerKey);
    }

    public void loginToApplicationAsCorporateUser(String customerKey, String userTypeKey) {
        UserType userType = UserType.valueOf(userTypeKey.toUpperCase());
        log.info(LOG_DIRECT_LOGIN_CORPORATE_USER, customerKey);

        DeviceContext.setCurrentUserType(userType);
        performLoginSequence(userType, customerKey);
    }

    private void performLoginSequence(UserType userType, String customerKey) {
        LoginContext context = prepareLoginContext(userType, customerKey);

        appiumUtil.clickElement("buttonLoginItem");
        context.credentialFiller().run();

        appiumUtil.clickElement("buttonActivationItem")
                .waitUntilElementLoad("inputSmsOtp");

        completeOtpVerification(context.customer().getSmsCode());
        DeviceContext.setCustomer(new CustomerEntry<>(userType, context.customer()));
    }

    private void handleInitialLanguageSelection(Language language) {
        appiumUtil
                .clickElement("continueButtonItem")
                .clickElement("buttonLanguageItem")
                .clickByText("languageListItem", language.getDisplay());
    }

    private LoginContext prepareLoginContext(UserType userType, String customerKey) {
        return switch (userType) {
            case RETAIL -> new LoginContext(RetailCustomer.valueOf(customerKey),
                    () -> fillRetailCredentials(RetailCustomer.valueOf(customerKey)));
            case CORPORATE -> new LoginContext(CorporateCustomer.valueOf(customerKey),
                    () -> fillCorporateCredentials(CorporateCustomer.valueOf(customerKey)));
        };
    }

    private void fillRetailCredentials(RetailCustomer customer) {
        log.info(LOG_LOGIN_RETAIL_FLOW, customer.name());
        appiumUtil
                .clearAndFillInputWithScroll("inputCustomerNumber", customer.getNumber())
                .clearAndFillInputWithScroll("inputPassword", customer.getPassword());
    }

    private void fillCorporateCredentials(CorporateCustomer customer) {
        log.info(LOG_LOGIN_CORPORATE_FLOW, customer.name());
        appiumUtil
                .clickElement("corporateUserTab")
                .waitUntilElementLoad("inputMsisdnNumber")
                .clearAndFillInputWithScroll("inputCustomerNumber", customer.getNumber())
                .clearAndFillInputWithScroll("inputMsisdnNumber", customer.getMsisdn())
                .clearAndFillInputWithEnter("inputPassword", customer.getPassword())
                .hideKeyboardIfNeeded();
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
        log.info(LOG_LOGOUT_START, customerKey, langKey, userTypeKey);
        loginToApplication(customerKey, langKey, userTypeKey);
        logOutUsingLanguage(langKey);
        BUTTON_LOGIN_ITEM.runAssertion();
        log.info(LOG_LOGOUT_COMPLETED);
    }

    public void logOutUsingLanguage(String langKey){
        Language language = Language.valueOf(langKey);
        new MenuPages().openMainMenu();
        appiumUtil.clickElement("logoutButtonItem")
                .clickByAnyText("logoutButtonItem", language.getLogoutTexts());
    }
}