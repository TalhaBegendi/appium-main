package org.halkKatilim.stepDefs;

import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.halkKatilim.pages.Pages;

public class CurrencyPreciousMetalSteps {
    Pages pages = new Pages();


    @When("Click {string} sell button")
    public void clickSellButton(String currencyAction) {
        pages.getCurrencyPreciousMetalPages().clickSellButton(currencyAction);
    }

    @And("Enter amount for {string}")
    public void enterAmountFor(String currency) {
        pages.getCurrencyPreciousMetalPages().enterCurrencyAmount(currency);
    }

    @Then("Verify the {string} equivalent amount is correct")
    public void verifyTheEquivalentAmountIsCorrect(String currency) {
        pages.getCurrencyPreciousMetalPages().verifyTheEquivalentAmountIsCorrect(currency);
    }

    @When("Click sell button")
    public void clickSellButton() {
        pages.getCurrencyPreciousMetalPages().clickSellButton();
    }

    @When("Click buy button")
    public void clickBuyButton() {
        pages.getCurrencyPreciousMetalPages().clickBuyButton();
    }

    @And("Click approve button")
    public void clickApproveButton() {
        pages.getCurrencyPreciousMetalPages().clickApproveButton();

    }

    @Then("Verify currency transaction success message is displayed")
    public void verifyCurrencyTransactionSuccessMessageIsDisplayed() {
        pages.getCurrencyPreciousMetalPages().verifyCurrencyTransactionSuccessMessageIsDisplayed();
    }

    @When("Click {string} buy button")
    public void clickBuyButton(String currencyAction) {
        pages.getCurrencyPreciousMetalPages().clickBuyButton(currencyAction);
    }

    @And("Enter TL amount")
    public void enterTLAmount() {
        pages.getCurrencyPreciousMetalPages().enterTLAmount();
    }

    @Then("Verify the TL equivalent amount is correct for {string}")
    public void verifyTheTLEquivalentAmountIsCorrect(String currency) {
        pages.getCurrencyPreciousMetalPages().verifyTheTLEquivalentAmountIsCorrect(currency);
    }

    @Then("Verify the {string} equivalent amount is correct for buy")
    public void verifyTheEquivalentAmountIsCorrectForBuy(String currency) {
        pages.getCurrencyPreciousMetalPages().verifyTheEquivalentAmountIsCorrectForBuy(currency);
    }

    @Then("Verify the TL equivalent buy amount is correct for {string}")
    public void verifyTheTLEquivalentBuyAmountIsCorrectFor(String currency) {
        pages.getCurrencyPreciousMetalPages().verifyTheTlEquivalentAmountIsCorrectForBuy(currency);
    }

    @When("Click the EUR Sell button for the {string} currency pair")
    public void clickTheEURSellButtonForTheEURUSDCurrencyPair(String currency) {
        pages.getCurrencyPreciousMetalPages().clickTheEURSellButtonForTheEURUSDCurrencyPair();

    }

    @When("Click the EUR Buy button for the {string} currency pair")
    public void clickTheEURBuyButtonForTheCurrencyPair(String currency) {
        pages.getCurrencyPreciousMetalPages().clickTheEURBuyButtonForTheCurrencyPair();

    }
}
