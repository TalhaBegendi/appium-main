package org.halkKatilim.stepDefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.halkKatilim.pages.Pages;

public class SettingsSteps {

    Pages pages = new Pages();

    @And("Select {string} as language option")
    public void selectAsLanguageOption(String language) {
        pages.getSettingsPages().selectAsLanguageOption(language);
    }

    @Then("Language should be changed successfully")
    public void successMessageIsDisplayed() {
        pages.getSettingsPages().successMessageIsDisplayed();
    }

    @Then("Profile should be opened some pages successfully")
    public void successProfilePageTitleIsDisplayed() {
        pages.getSettingsPages().successProfilePageTitleIsDisplayed();
    }

    @Then("Profile should be updated successfully")
    public void successPageMessagesIsDisplayed() {
        pages.getSettingsPages().successPageMessagesIsDisplayed();
    }

    @And("Switch options Information Sharing Options")
    public void switchInformationSharingOptions() {
        pages.getSettingsPages().switchInformationSharingOptions();
    }

    @And("Switch to Permission")
    public void switchToPermission() {
        pages.getSettingsPages().switchToPermission();
    }

    @Then("Success Switch to Permission")
    public void successSwitchToPermission() {
        pages.getSettingsPages().successSwitchToPermission();
    }

    @And("Switch options Permission to Share Personal Data")
    public void switchToPermissionToSharePersonalData() {
        pages.getSettingsPages().switchToPermissionToSharePersonalData();
    }

    @And("Open Banking Permission steps")
    public void openBankingPermission() {
        pages.getSettingsPages().openBankingPermission();
    }

    @And("Update button Phone Number")
    public void phoneNumberUpdate() {
        pages.getSettingsPages().phoneNumberUpdate();
    }

    @And("Update button Email")
    public void emailUpdate() {
        pages.getSettingsPages().emailUpdate();
    }

    @And("Update button Address")
    public void addressUpdate() {
        pages.getSettingsPages().addressUpdate();
    }

    @And("Opens My Employment Details Page")
    public void myEmploymentDetails() {
        pages.getSettingsPages().myEmploymentDetails();
    }

    @And("Opens Planned Monthly Transactions Page")
    public void plannedMonthlyTransactions() {
        pages.getSettingsPages().plannedMonthlyTransactions();
    }

    @Then("Pages should be opened successfully with {string} title")
    public void pagesShouldBeOpenedSuccessfullyWithTitle(String title) {
        pages.getSettingsPages().pagesShouldBeOpenedSuccessfullyWithTitle(title);
    }
}