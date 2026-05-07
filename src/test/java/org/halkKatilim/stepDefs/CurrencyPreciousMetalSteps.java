package org.halkKatilim.stepDefs;

import org.halkKatilim.utility.context.PageContext;
import org.halkKatilim.pages.currencyPreciousMetal.CurrencyPreciousMetalPages;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import static org.halkKatilim.enums.TradeConfig.*;

public class CurrencyPreciousMetalSteps {

    private CurrencyPreciousMetalPages currencyPreciousMetalPages() {
        return PageContext.get().get(CurrencyPreciousMetalPages.class);
    }

    @When("Click {string} sell button")
    public void clickSellButton(String currencyAction) {
        currencyPreciousMetalPages().clickSellButton(currencyAction);
    }

    @When("Click sell button")
    public void clickSellButton() {
        currencyPreciousMetalPages().clickSellButton();
    }

    @When("Click {string} buy button")
    public void clickBuyButton(String currencyAction) {
        currencyPreciousMetalPages().clickBuyButton(currencyAction);
    }

    @When("Click buy button")
    public void clickBuyButton() {
        currencyPreciousMetalPages().clickBuyButton();
    }

    @And("Click approve button")
    public void clickApproveButton() {
        currencyPreciousMetalPages().clickApproveButton();
    }

    @When("Click the EUR Sell button for the {string} currency pair")
    public void clickTheEURSellButton(String currency) {
        currencyPreciousMetalPages().clickTheEURSellButtonForTheEURUSDCurrencyPair();
    }

    @When("Click the EUR Buy button for the {string} currency pair")
    public void clickTheEURBuyButton(String currency) {
        currencyPreciousMetalPages().clickTheEURBuyButtonForTheCurrencyPair();
    }

    // ---------------- INPUT ----------------

    @And("Enter amount for {string}")
    public void enterAmountFor(String type) {
        currencyPreciousMetalPages().enterCurrencyAmount(InputType.valueOf(type.toUpperCase()));
    }

    @And("Enter TL amount")
    public void enterTLAmount() {
        currencyPreciousMetalPages().enterTLAmount();
    }

    // ---------------- VERIFY ----------------

    @Then("Verify the {string} equivalent amount is correct")
    public void verifySellForeign(String currency) {
        currencyPreciousMetalPages()
                .verifyExchange(TradeType.SELL, InputType.FOREIGN, mapCurrency(currency), false);
    }

    @Then("Verify the TL equivalent amount is correct for {string}")
    public void verifySellTl(String currency) {
        currencyPreciousMetalPages()
                .verifyExchange(TradeType.SELL, InputType.TL, mapCurrency(currency), false);
    }

    @Then("Verify the {string} equivalent amount is correct for buy")
    public void verifyBuyForeign(String currency) {
        currencyPreciousMetalPages()
                .verifyExchange(TradeType.BUY, InputType.FOREIGN, mapCurrency(currency), true);
    }

    @Then("Verify the TL equivalent buy amount is correct for {string}")
    public void verifyBuyTl(String currency) {
        currencyPreciousMetalPages()
                .verifyExchange(TradeType.BUY, InputType.TL, mapCurrency(currency), false);
    }

    // ---------------- SUCCESS ----------------

    @Then("Verify currency transaction success message is displayed")
    public void verifyCurrencyTransactionSuccessMessageIsDisplayed() {
        currencyPreciousMetalPages().verifyCurrencyTransactionSuccessMessageIsDisplayed();
    }

    // ---------------- MAPPER ----------------

    private CurrencyType mapCurrency(String currency) {
        return currency.equalsIgnoreCase("CURRENCY")
                ? CurrencyType.CURRENCY
                : CurrencyType.PRECIOUS_METAL;
    }
}