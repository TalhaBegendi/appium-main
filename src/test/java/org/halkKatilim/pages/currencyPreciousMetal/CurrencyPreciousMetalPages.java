package org.halkKatilim.pages.currencyPreciousMetal;

import org.halkKatilim.pages.BasePages;
import org.openqa.selenium.WebElement;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Locale;

import static org.halkKatilim.pages.currencyPreciousMetal.CurrencyPreciousMetalPagesText.*;
import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.assertEquals;

public class CurrencyPreciousMetalPages extends BasePages {

    public void clickSellButton(String currencyAction) {
        appiumUtil.selectFromListByText("currencyPreciousMetalSellButtonList", currencyAction);
    }

    public void clickBuyButton(String currencyAction) {
        appiumUtil.selectFromListByText("currencyPreciousMetalBuyButtonList", currencyAction);
    }

    public void enterCurrencyAmount(String currency) {
        int currencyAmount = currency.equals(CURRENCY_TEXT) ? CURRENCY_AMOUNT : PRECIOUS_METAL_AMOUNT;
        appiumUtil.clearAndFillInput("currencyPreciousMetalCurrencyInputField", String.valueOf(currencyAmount));
    }

    public void verifyTheEquivalentAmountIsCorrect(String currency) {
        boolean isCurrency = currency.equals(CURRENCY_TEXT);
        double currencyAmount = isCurrency ? CURRENCY_AMOUNT : PRECIOUS_METAL_AMOUNT;
        double sellAmount = parseLocalizedAmount(appiumUtil.findElementSilent("currencyPreciousMetalSellAmount").getText());
        verifyEquivalentAmount(isCurrency, currencyAmount, false, sellAmount);
    }

    public void verifyTheTLEquivalentAmountIsCorrect(String currency) {
        boolean isCurrency = currency.equals(CURRENCY_TEXT);
        double currencyAmount = parseLocalizedAmount(appiumUtil.findElementSilent("currencyPreciousMetalCurrencyInputField").getText());
        double sellAmount = parseLocalizedAmount(appiumUtil.findElementSilent("currencyPreciousMetalSellAmount").getText());
        verifyEquivalentAmount(isCurrency, currencyAmount, false, sellAmount);
    }

    public void verifyTheEquivalentAmountIsCorrectForBuy(String currency) {
        boolean isCurrency = currency.equals(CURRENCY_TEXT);
        double currencyAmount = isCurrency ? CURRENCY_AMOUNT : PRECIOUS_METAL_AMOUNT;
        double buyAmount = parseLocalizedAmount(appiumUtil.findElementSilent("currencyPreciousMetalBuyAmount").getText());
        verifyEquivalentAmount(isCurrency, currencyAmount, true, buyAmount);
    }

    public void verifyTheTlEquivalentAmountIsCorrectForBuy(String currency) {
        boolean isCurrency = currency.equals(CURRENCY_TEXT);
        double currencyAmount = parseLocalizedAmount(appiumUtil.findElementSilent("currencyPreciousMetalCurrencyInputField").getText());
        double buyAmount = parseLocalizedAmount(appiumUtil.findElementSilent("currencyPreciousMetalBuyAmount").getText());
        verifyEquivalentAmount(isCurrency, currencyAmount, true, buyAmount);
    }

    private void verifyEquivalentAmount(boolean isCurrency, double currencyAmount, boolean includeTax, double amount) {

        double expectedAmount = BigDecimal.valueOf(amount)
                .multiply(BigDecimal.valueOf(currencyAmount))
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();

        double actualAmount = BigDecimal.valueOf(parseLocalizedAmount(appiumUtil.findElementSilent("currencyPreciousMetalTLInputField").getText())
        ).setScale(2, RoundingMode.HALF_UP).doubleValue();

        if (includeTax) {
            actualAmount += findBmsvKmv();
        }

        double delta = expectedAmount * (isCurrency ? 0.01 : 0.25);
        assertEquals(expectedAmount, actualAmount, delta);
    }


    private double findBmsvKmv() {

        return BigDecimal.valueOf(
                parseLocalizedAmount(appiumUtil.findElementSilent("bmsvKmvInputField").getText())
        ).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    private double parseLocalizedAmount(String rawText) {
        try {
            String cleaned = rawText.replaceAll("[^0-9,.]", "");
            NumberFormat format = NumberFormat.getInstance(Locale.forLanguageTag("tr-TR"));
            return format.parse(cleaned).doubleValue();
        } catch (ParseException e) {
            throw new RuntimeException("Amount parse edilemedi: " + rawText, e);
        }
    }

    public void clickSellButton() {
        appiumUtil.clickElement("currencyPreciousSellButton");
    }

    public void clickBuyButton() {
        appiumUtil.clickElementWithScroll("currencyPreciousBuyButton");
    }

    public void clickApproveButton() {
        appiumUtil.clickElement("currencyPreciousApproveButton");
    }

    public void verifyCurrencyTransactionSuccessMessageIsDisplayed() {
        assertElementTextContainsAny(appiumUtil.findElementSilent("currencyPreciousSuccessMessage"), TURKISH_CURRENCY_SUCCESS_MESSAGE);
    }

    private void assertElementTextContainsAny(WebElement element, String... expectedParts) {
        String actualText = element.getText().trim();
        assertTrue(Arrays.stream(expectedParts).anyMatch(actualText::contains),
                "Actual text [" + actualText + "] does not contain any expected values");
    }

    public void enterTLAmount() {
        appiumUtil.clearAndFillInput("currencyPreciousMetalTLInputField", String.valueOf(TL_AMOUNT));
    }


    public void clickTheEURSellButtonForTheEURUSDCurrencyPair() {
        appiumUtil.clickElementWithScroll("euroSellButton");
    }

    public void clickTheEURBuyButtonForTheCurrencyPair() {
        appiumUtil.clickElementWithScroll("euroBuyButton");
    }
}
