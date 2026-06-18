package org.halkKatilim.pages.cards;

import lombok.RequiredArgsConstructor;
import org.halkKatilim.utility.appiumUtil.AppiumUtil;
import static org.halkKatilim.pages.cards.CardsPagesText.*;

@RequiredArgsConstructor
public class CardsPages {

    private final AppiumUtil appiumUtil;

    private void selectOption(String option) {
        switch (option.toLowerCase()) {
            case "credit", "my" -> appiumUtil.clickElement("firstOptionButton");

            case "debit", "other" -> appiumUtil.clickElement("secondOptionButton");

            default -> throw new IllegalArgumentException("Unsupported option: " + option);
        }
    }

    public CardsPages applyCard(String cardType) {
        appiumUtil.waitUntilElementLoad("cardTypesList")
                .selectFromListByLabel("cardTypesList", cardType);
        return this;
    }

    public CardsPages applyCardButton(String cardCategory) {
        selectOption(cardCategory);
        appiumUtil.clickElement("applyForCardButton");
        return this;
    }

    public CardsPages payDebt(String cardOwner) {
        selectOption(cardOwner);
        switch (cardOwner.toLowerCase()) {
            case "other" -> fillDebtForOtherCard();
            case "my" -> fillDebtForMyCard();
            default -> throw new IllegalArgumentException(
                    "Unsupported card owner: " + cardOwner);
        }
        return this;
    }

    private void fillDebtForMyCard() {
        appiumUtil
                .waitUntilElementLoad("inputAmountMyCard")
                .fillInputKeyboard("inputAmountMyCard", PAY_DEBT_AMOUNT, false, true);
    }

    private void fillDebtForOtherCard() {
        appiumUtil
                .waitUntilElementLoad("otherCardNumber")
                .fillInputKeyboard("otherCardNumber", OTHER_CARD_NO, false, true)
                .fillInputKeyboard("inputAmountOtherCard", PAY_DEBT_AMOUNT, false, true);
    }

    public CardsPages continueApplyCard() {
        appiumUtil.clickElement("continueApplyCardButton");
        return this;
    }

    public CardsPages continuePayDebtMyCard() {
        appiumUtil.clickElement("payDebtMyCardContinueButton");
        return this;
    }

    public void successMessageIsDisplayed() {
        appiumUtil.assertElementTextContainsAny(appiumUtil.safeFindElementAndWait("DEGISECEK"),
                CORPORATE_ROLE_AUTHORIZATION_ERROR_TURKISH, CORPORATE_ROLE_AUTHORIZATION_ERROR_ENGLISH);
    }

}
