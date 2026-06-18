package org.halkKatilim.stepDefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.halkKatilim.pages.cards.CardsPages;
import org.halkKatilim.utility.context.PageContext;

public class CardsSteps {

    private CardsPages cardsPages() {
        return PageContext.get().get(CardsPages.class);
    }

    @When("Apply the {string} card")
    public void applyCard(String cardType) {
        cardsPages().applyCard(cardType);
    }

    @And("Click apply card button in {string}")
    public void applyCardButton(String cardCategory) {
        cardsPages().applyCardButton(cardCategory);
    }

    @When("Pay the debt for {string} card")
    public void payDebt(String cardOwner) {
        cardsPages().payDebt(cardOwner);
    }

    @When("Click confirm button on confirmation page for Cards")
    public void continueApplyCard() {
        cardsPages().continueApplyCard();
    }

    @When("Click continue button for pay debt")
    public void continuePayDebtMyCard() {
        cardsPages().continuePayDebtMyCard();
    }

    @Then("Card should be applied successfully")
    public void successPageTitleIsDisplayed() {
        cardsPages().successMessageIsDisplayed();
    }
}