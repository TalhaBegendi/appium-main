package org.halkKatilim.stepDefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.halkKatilim.pages.Pages;

import static org.halkKatilim.enums.TradeConfig.*;

public class CurrencyPreciousMetalSteps {

    Pages pages = new Pages();

    // ---------------- ACTIONS ----------------

    @When("Click {string} sell button")
    public void clickSellButton(String currencyAction) {
        pages.getCurrencyPreciousMetalPages().clickSellButton(currencyAction);
    }

    @When("Click sell button")
    public void clickSellButton() {
        pages.getCurrencyPreciousMetalPages().clickSellButton();
    }

    @When("Click {string} buy button")
    public void clickBuyButton(String currencyAction) {
        pages.getCurrencyPreciousMetalPages().clickBuyButton(currencyAction);
    }

    @When("Click buy button")
    public void clickBuyButton() {
        pages.getCurrencyPreciousMetalPages().clickBuyButton();
    }

    @And("Click approve button")
    public void clickApproveButton() {
        pages.getCurrencyPreciousMetalPages().clickApproveButton();
    }

    @When("Click the EUR Sell button for the {string} currency pair")
    public void clickTheEURSellButton(String currency) {
        pages.getCurrencyPreciousMetalPages().clickTheEURSellButtonForTheEURUSDCurrencyPair();
    }

    @When("Click the EUR Buy button for the {string} currency pair")
    public void clickTheEURBuyButton(String currency) {
        pages.getCurrencyPreciousMetalPages().clickTheEURBuyButtonForTheCurrencyPair();
    }

    // ---------------- INPUT ----------------

    @And("Enter amount for {string}")
    public void enterAmountFor(String currency) {
        pages.getCurrencyPreciousMetalPages().enterCurrencyAmount(currency);
    }

    @And("Enter TL amount")
    public void enterTLAmount() {
        pages.getCurrencyPreciousMetalPages().enterTLAmount();
    }

    // ---------------- VERIFY (ENGINE MAPPING) ----------------

    // SELL + FOREIGN
    @Then("Verify the {string} equivalent amount is correct")
    public void verifySellForeign(String currency) {
        pages.getCurrencyPreciousMetalPages()
                .verifyExchange(TradeType.SELL, InputType.FOREIGN, mapCurrency(currency), false);
    }

    // SELL + TL
    @Then("Verify the TL equivalent amount is correct for {string}")
    public void verifySellTl(String currency) {
        pages.getCurrencyPreciousMetalPages()
                .verifyExchange(TradeType.SELL, InputType.TL, mapCurrency(currency), false);
    }

    // BUY + FOREIGN
    @Then("Verify the {string} equivalent amount is correct for buy")
    public void verifyBuyForeign(String currency) {
        pages.getCurrencyPreciousMetalPages()
                .verifyExchange(TradeType.BUY, InputType.FOREIGN, mapCurrency(currency), true);
    }

    // BUY + TL  🔥 (en kritik case)
    @Then("Verify the TL equivalent buy amount is correct for {string}")
    public void verifyBuyTl(String currency) {
        pages.getCurrencyPreciousMetalPages()
                .verifyExchange(TradeType.BUY, InputType.TL, mapCurrency(currency), false);
    }

    // ---------------- SUCCESS ----------------

    @Then("Verify currency transaction success message is displayed")
    public void verifyCurrencyTransactionSuccessMessageIsDisplayed() {
        pages.getCurrencyPreciousMetalPages().verifyCurrencyTransactionSuccessMessageIsDisplayed();
    }

    // ---------------- MAPPER ----------------

    private CurrencyType mapCurrency(String currency) {
        return currency.equalsIgnoreCase("CURRENCY")
                ? CurrencyType.CURRENCY
                : CurrencyType.PRECIOUS_METAL;
    }
}