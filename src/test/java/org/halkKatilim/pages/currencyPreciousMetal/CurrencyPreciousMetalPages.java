package org.halkKatilim.pages.currencyPreciousMetal;

import org.halkKatilim.enums.Platform;
import org.halkKatilim.utility.Driver;
import org.openqa.selenium.WebElement;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import static org.halkKatilim.enums.TradeConfig.*;
import static org.halkKatilim.pages.currencyPreciousMetal.CurrencyPreciousMetalPagesText.*;
import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.assertEquals;
import lombok.RequiredArgsConstructor;
import org.halkKatilim.utility.appiumUtil.AppiumUtil;

@RequiredArgsConstructor
public class CurrencyPreciousMetalPages  {

    private final AppiumUtil appiumUtil;

    public void clickSellButton(String currencyAction) {
        appiumUtil.clickElementTextWithScroll("currencyPreciousMetalSellButtonList", currencyAction);
    }

    public void clickBuyButton(String currencyAction) {
        appiumUtil.clickElementTextWithScroll("currencyPreciousMetalBuyButtonList", currencyAction);
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

    public void clickTheEURSellButtonForTheEURUSDCurrencyPair() {
        appiumUtil.clickElementWithScroll("euroSellButton");
    }

    public void clickTheEURBuyButtonForTheCurrencyPair() {
        appiumUtil.clickElementWithScroll("euroBuyButton");
    }

    // ---------------- INPUT ----------------

    public void enterCurrencyAmount(InputType type) {
        boolean isTL = type == InputType.TL;
        String inputKey = isTL
                ? "currencyPreciousMetalTLInputField"
                : "currencyPreciousMetalCurrencyInputField";

        appiumUtil.waitUntilElementLoad(inputKey);
        fillAmountWithPlatformCheck(
                inputKey,
                isTL
                        ? TL_CURRENCY_AMOUNT
                        : FOREIGN_CURRENCY_AMOUNT
        );
    }

    public void enterTLAmount() {
            String digits = appiumUtil
                    .safeFindElementAndWait("currencyPreciousMetalBuyAmount")
                    .getText()
                    .replaceAll("[^0-9]", "");
            String firstFive = digits.length() > 4
                    ? digits.substring(0, 4)
                    : digits;
        int amount = Integer.parseInt(firstFive) + 1;
        fillAmountWithPlatformCheck("currencyPreciousMetalTLInputField", amount);
    }

    private void fillAmountWithPlatformCheck(String inputKey, double amount) {
        if (Driver.getPlatformForThread() == Platform.IOS) {
            fill(inputKey, amount);
            return;
        }
        List<WebElement> elements = retryFindElements("currencyPreciousMetalCurrencyAccountVerify");
        if (elements.size() == 1) {
            fill(inputKey, amount);
            return;
        }
        throw new RuntimeException("❌ Expected exactly 1 element but found: " + elements.size());
    }

    private void fill(String key, double amount) {
        appiumUtil.fillInputKeyboard(key, String.valueOf(amount), false, true);
    }

    private List<WebElement> retryFindElements(String key) {
        List<WebElement> elements = appiumUtil.safeFindElementsAndWait(key);
        return (elements == null || elements.isEmpty())
                ? appiumUtil.findElementsSilent(key)
                : elements;
    }

    // ---------------- FINAL VERIFY ENGINE ----------------

    public void verifyExchange(TradeType tradeType,
                               InputType inputType,
                               CurrencyType currencyType,
                               boolean includeTax) {

        boolean isCurrency = currencyType == CurrencyType.CURRENCY;

        // 🔹 RATE (BUY / SELL)
        String rateKey = tradeType == TradeType.BUY
                ? "currencyPreciousMetalBuyAmount"
                : "currencyPreciousMetalSellAmount";

        double rate = parseLocalizedAmount(
                appiumUtil.findElementSilent(rateKey).getText()
        );

        // 🔹 INPUT AMOUNT
        double inputAmount = inputType == InputType.TL
                ? getTlAmount()
                : getForeignAmount();

        // 🔹 ACTUAL RESULT (UI)
        double actualAmount = inputType == InputType.TL
                ? getForeignAmount()
                : getTlAmount();

        // 🔹 EXPECTED CALCULATION
        double expectedAmount = calculateExpected(inputAmount, rate, inputType);

        // 🔹 TAX (sadece FOREIGN → TL için mantıklı)
        if (includeTax && inputType == InputType.FOREIGN) {
            expectedAmount += findBmsvKmv();
        }

        double delta = expectedAmount * (isCurrency ? 0.01 : 0.25);

        assertEquals(expectedAmount, actualAmount, delta);
    }

    // ---------------- CORE CALCULATION ----------------

    private double calculateExpected(double inputAmount,
                                     double rate,
                                     InputType inputType) {

        return switch (inputType) {

            case TL -> BigDecimal.valueOf(inputAmount)
                    .divide(BigDecimal.valueOf(rate), 2, RoundingMode.HALF_UP)
                    .doubleValue();   // TL → USD

            case FOREIGN -> BigDecimal.valueOf(inputAmount)
                    .multiply(BigDecimal.valueOf(rate))
                    .setScale(2, RoundingMode.HALF_UP)
                    .doubleValue();   // USD → TL
        };
    }

    // ---------------- HELPERS ----------------

    private double getTlAmount() {
        return parseLocalizedAmount(
                appiumUtil.findElementSilent("currencyPreciousMetalTLInputField").getText()
        );
    }

    private double getForeignAmount() {
        return parseLocalizedAmount(
                appiumUtil.findElementSilent("currencyPreciousMetalCurrencyInputField").getText()
        );
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

    // ---------------- ASSERTIONS ----------------

    public void verifyCurrencyTransactionSuccessMessageIsDisplayed() {
        assertElementTextContainsAny(
                appiumUtil.findElementSilent("currencyPreciousSuccessMessage"),
                TURKISH_CURRENCY_SUCCESS_MESSAGE
        );
    }

    private void assertElementTextContainsAny(WebElement element, String... expectedParts) {
        String actualText = element.getText().trim();
        assertTrue(Arrays.stream(expectedParts).anyMatch(actualText::contains),
                "Actual text [" + actualText + "] does not contain any expected values");
    }
}