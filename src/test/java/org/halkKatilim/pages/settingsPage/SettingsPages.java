package org.halkKatilim.pages.settingsPage;

import lombok.extern.slf4j.Slf4j;
import org.halkKatilim.enums.NavigationGates;
import org.halkKatilim.enums.Platform;
import org.halkKatilim.utility.Driver;
import org.openqa.selenium.WebElement;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;
import static org.halkKatilim.pages.settingsPage.SettingsPageText.*;
import static org.testng.Assert.assertTrue;
import lombok.RequiredArgsConstructor;
import org.halkKatilim.utility.appiumUtil.AppiumUtil;

@Slf4j
@RequiredArgsConstructor
public class SettingsPages  {
    private final AppiumUtil appiumUtil;

    protected void clickSequence(String... keys) {
        Arrays.stream(keys).forEach(key -> appiumUtil.clickElement(key));
    }

    protected void runByPlatform(Runnable android, Runnable ios) {
        switch (Driver.getPlatformForThread()) {
            case ANDROID -> android.run();
            case IOS -> ios.run();
        }
    }

    protected void clickFirstAvailable(String... keys) {
        Arrays.stream(keys)
                .map(key -> appiumUtil.findElementSilent(key))
                .filter(Objects::nonNull)
                .findFirst()
                .ifPresent(WebElement::click);
    }

    protected void scrollAndClick(int scroll, String key) {
        appiumUtil.scrollToBottom(scroll).clickElement(key);
    }

    public void selectAsLanguageOption(String language) {
        appiumUtil.selectFromListByText("settingsLanguageList", language);
    }

    public void switchInformationSharingOptions() {
        appiumUtil.clickElementWithScroll("settingsInformationSharingSwitchFirst")
                .clickElementWithScroll("settingsInformationSharingSwitchSecond")
                .waitBySecond(1);
    }

    public void switchToPermissionToSharePersonalData() {
        runByPlatform(this::handleAndroidSwitches, this::handleIosSwitches);
        toggleFirstSwitchSequence();
    }

    private void handleAndroidSwitches() {
        appiumUtil.safeFindElementsAndWait("settingsSwitchApproveAndroid").getFirst().click();
        scrollAndClick(7, "settingsInformationSharingSwitchFirst");
        appiumUtil.safeFindElementsAndWait("settingsSwitchApproveAndroid").get(1).click();
    }

    private void handleIosSwitches() {
        appiumUtil.clickElement("settingsSwitchFirstOptionIOS");
        appiumUtil.scrollToBottom(11)
                .clickElement("settingsInformationSharingSwitchFirst")
                .clickElement("settingsSwitchSecondOptionIOS");
    }

    private void toggleFirstSwitchSequence() {
        int clickCount = Driver.getPlatformForThread() == Platform.IOS ? 3 : 2;
        appiumUtil.scrollToBottom(4);
        IntStream.range(0, clickCount)
                .forEach(i -> appiumUtil.clickElement("settingsInformationSharingSwitchFirst")
                        .waitBySecond(1));
    }

    public void switchToPermission() {
        clickFirstAvailable(
                "settingsInformationSharingSwitchFirst",
                "settingsClickSwitchPermission",
                "settingsSwitchChannelActiveManagement");
    }

    public void successSwitchToPermission() {
        appiumUtil.waitBySecond(1);
        List<WebElement> elements = appiumUtil.findElementsSilent("settingsSuccessSwitchPermission");
        appiumUtil.getAssertion().hardAssertNotEmpty(elements, "❌ Permission elementi bulunamadı.");
    }

    public void successMessageIsDisplayed() {
        appiumUtil.assertElementTextContainsAny(
                appiumUtil.safeFindElementAndWait("settingsSelectLanguageSuccessMessage"),
                TURKISH_SELECT_LANGUAGE_SUCCESS_MESSAGE);
    }

    public void successPageTitleIsDisplayed() {
        appiumUtil.autoHandleNavigationGates(NavigationGates.Context.DEFAULT);
        appiumUtil.assertElementTextContainsAny(
                appiumUtil.safeFindElementAndWait("settingsPageTitle"),
                TURKISH_FINANCE_SUCCESS_MESSAGE_TITLE,
                TURKISH_MONEY_TRANSFER_SUCCESS_MESSAGE_TITLE,
                TURKISH_CREDIT_CARD_SUCCESS_MESSAGE_TITLE,
                TURKISH_CREDIT_CARD_SUCCESS_MESSAGE_IOS_TITLE,
                TURKISH_SAVED_DEVICES_SUCCESS_MESSAGE_TITLE,
                TURKISH_SAVED_LOGIN_SUCCESS_MESSAGE_TITLE,
                TURKISH_PASSWORD_BLOKE_SUCCESS_MESSAGE_TITLE,
                TURKISH_VERIFY_BIOMETRIC_SUCCESS_MESSAGE_TITLE,
                TURKISH_CHANNEL_ACTIVE_MANAGEMENT_SUCCESS_MESSAGE_TITLE,
                TURKISH_PAYMENT_REQUEST_PREFERENCES_SUCCESS_MESSAGE_TITLE,
                TURKISH_OPENED_BANKING_CHANNEL_SUCCESS_MESSAGE_TITLE,
                TURKISH_CONSENT_PENDING_SUCCESS_MESSAGE_TITLE,
                TURKISH_OPEN_BANKING_PERMISSION_SUCCESS_MESSAGE_TITLE,
                TURKISH_PHONE_UPDATE_SUCCESS_MESSAGE_TITLE,
                TURKISH_PHONE_UPDATE_SUCCESS_MESSAGE_TITLE_IOS,
                TURKISH_MY_EMPLOYMENT_DETAILS_SUCCESS_MESSAGE_TITLE,
                TURKISH_PLANNED_MONTHLY_TRANSACTIONS_SUCCESS_MESSAGE_TITLE,
                TURKISH_PROFILE,
                TURKISH_CORPORATE_PROFILE,
                TURKISH_SUCCESS_LOGIN,
                TURKISH_UNSUCCESS_LOGIN_ATTEMPT,
                TURKISH_FACE_ID
        );
    }

    public void successProfilePageTitleIsDisplayed() {
        appiumUtil.autoHandleNavigationGates(NavigationGates.Context.DEFAULT)
        .assertElementTextContainsAny(
                appiumUtil.safeFindElementAndWait("settingsPageTitleMyEmployeeInfo"),
                TURKISH_MY_EMPLOYMENT_DETAILS_SUCCESS_MESSAGE_TITLE,
                TURKISH_PLANNED_MONTHLY_TRANSACTIONS_SUCCESS_MESSAGE_TITLE
        );
    }

    public void successPageMessagesIsDisplayed() {
        appiumUtil.autoHandleNavigationGates(NavigationGates.Context.DEFAULT)
        .assertElementTextContainsAny(
                appiumUtil.safeFindElementAndWait("settingsSuccessPageMessage"),
                TURKISH_EMAIL_UPDATE_SUCCESS_MESSAGE,
                TURKISH_ADDRESS_UPDATE_SUCCESS_MESSAGE);
    }

    public void openBankingPermission() {
        clickSequence(
                "settingsButtonPassiveItem",
                "settingsButtonActiveItem",
                "settingsButtonPaymentItem",
                "settingsButtonActiveItem",
                "settingsButtonPassiveItem"
        );
    }

    public void phoneNumberUpdate() {
        appiumUtil.clickElement("settingsPhoneButtonUpdate");
    }

    public void addressUpdate() {
        runByPlatform(this::addressUpdateAndroid, this::addressUpdateIOS);
    }

    private void addressUpdateAndroid() {
        clickSequence(
                "settingsAddressButtonUpdate",
                "settingsAddressButtonSelect",
                "settingsContinueButton",
                "settingsContinueButton",
                "confirmationPageSwitchPermission",
                "settingsApproveConfirmationButton"
        );
    }

    private void addressUpdateIOS() {
        clickSequence(
                "settingsAddressButtonUpdate",
                "settingsEditButton",
                "settingsAddressButtonSelect",
                "settingsApproveConfirmationButton"
        );
    }

    public void emailUpdate() {
        runByPlatform(this::updateEmailAndroid, this::updateEmailIOS);
    }

    private void updateEmailAndroid() {
        clickSequence(
                "settingsEmailButtonUpdate",
                "settingsContinueButton",
                "settingsContinueButton",
                "confirmationPageSwitchPermission",
                "settingsApproveConfirmationButton"
        );
    }

    private void updateEmailIOS() {
        appiumUtil.clickElement("settingsEmailButtonUpdate")
                .clickElement("settingsEditButton")
                .fillInputKeyboard("settingsEditMailText", appiumUtil.generateNumber(4) + EMAIL_DATA, false, false)
                .clickElement("settingsApproveConfirmationButton");
    }

    public void myEmploymentDetails() {
        appiumUtil.clickElement("settingsMyEmploymentDetails");
    }

    public void plannedMonthlyTransactions() {
        appiumUtil.clickElement("settingsPlannedMonthlyTransactions");
    }
}