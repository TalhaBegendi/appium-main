package org.halkKatilim.stepDefs;

import org.halkKatilim.utility.context.PageContext;
import org.halkKatilim.pages.settingsPage.SettingsPages;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;


public class SettingsSteps {

    private SettingsPages settingsPages() {
        return PageContext.get().get(SettingsPages.class);
    }

    @And("Select {string} as language option")
    public void selectAsLanguageOption(String language) {
        settingsPages().selectAsLanguageOption(language);
    }

    @Then("Language should be changed successfully")
    public void successMessageIsDisplayed() {
        settingsPages().successMessageIsDisplayed();
    }

    @Then("Pages should be opened successfully")
    public void successPageTitleIsDisplayed() {
        settingsPages().successPageTitleIsDisplayed();
    }

    @Then("Profile should be opened some pages successfully")
    public void successProfilePageTitleIsDisplayed() {
        settingsPages().successProfilePageTitleIsDisplayed();
    }

    @Then("Profile should be updated successfully")
    public void successPageMessagesIsDisplayed() {
        settingsPages().successPageMessagesIsDisplayed();
    }

    @And("Switch options Information Sharing Options")
    public void switchInformationSharingOptions() {
        settingsPages().switchInformationSharingOptions();
    }

    @And("Switch to Permission")
    public void switchToPermission() {
        settingsPages().switchToPermission();
    }

    @Then("Success Switch to Permission")
    public void successSwitchToPermission() {
        settingsPages().successSwitchToPermission();
    }

    @And("Switch options Permission to Share Personal Data")
    public void switchToPermissionToSharePersonalData() {
        settingsPages().switchToPermissionToSharePersonalData();
    }

    @And("Open Banking Permission steps")
    public void openBankingPermission() {
        settingsPages().openBankingPermission();
    }

    @And("Update button Phone Number")
    public void phoneNumberUpdate() {
        settingsPages().phoneNumberUpdate();
    }

    @And("Update button Email")
    public void emailUpdate() {
        settingsPages().emailUpdate();
    }

    @And("Update button Address")
    public void addressUpdate() {
        settingsPages().addressUpdate();
    }

    @And("Opens My Employment Details Page")
    public void myEmploymentDetails() {
        settingsPages().myEmploymentDetails();
    }

    @And("Opens Planned Monthly Transactions Page")
    public void plannedMonthlyTransactions() {
        settingsPages().plannedMonthlyTransactions();
    }
}