package org.halkKatilim.utility.assertionUtil.enums;

import lombok.Getter;
import org.halkKatilim.enums.DisplayText;
import org.halkKatilim.utility.assertionUtil.types.HardAssertion;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.halkKatilim.enums.DisplayText.*;
import static org.halkKatilim.utility.assertionUtil.enums.AssertionPrefix.*;
import static org.halkKatilim.utility.assertionUtil.enums.AssertionType.*;

@Getter
public enum AssertionKey {

    // ---------- LOGIN ----------
    LOGIN(LOGINPAGE,null,"pageTitleHomepage",EXISTS),
    BUTTON_LOGIN_ITEM(LOGINPAGE,null,"buttonLoginItem",SINGLE),

    // ---------- HOMEPAGE ----------
    MY_ASSETS(HOMEPAGE, MY_ASSETS_DISPLAY, "myAssetsTitleHomepage", SINGLE),
    MY_ACCOUNTS(HOMEPAGE, MY_ACCOUNTS_DISPLAY, "myAccountsTitleHomepage", SINGLE),
    LAST_TRANSACTIONS(HOMEPAGE, LAST_TRANSACTIONS_DISPLAY, "last10TransactionsTitleHomepage", EXISTS),

    // ---------- HOMEPAGE/ACCOUNT ----------
    ACCOUNTS(HOMEPAGE, null, null, EQUAL_TEXT),
    ASSETS(HOMEPAGE, null, null, NOT_EQUAL_TEXT),
    SUCCESS_ACCOUNTS(HOMEPAGE, SUCCESS_ACCOUNTS_DISPLAY, null, EQUAL_TEXT),

    // ---------- HOMEPAGE/LAST_ACTIVITIES ----------
    LAST_TRANSACTIONS_SLIP(HOMEPAGE, null, "last10TransactionsSlipHomepage", SINGLE),

    // ---------- MENU ----------
    MAIN_MENU(MENU, null, "menuTitleItem", EXISTS),
    HOME(MENU, HOME_DISPLAY, "menuTitleItem", NOT_EXISTS),
    ABOUT(MENU, ABOUT_DISPLAY, "aboutMenuInfoItem", EXISTS),
    SAVED_TRANSACTION(MENU, SAVED_TRANSACTION_DISPLAY, "savedTransactionMenuInfoButtonItem", SINGLE),
    NOTIFICATIONS_AND_MESSAGES(MENU, NOTIFICATIONS_AND_MESSAGES_DISPLAY, "notificationsAndMessagesMenuInfoItem", SINGLE),

    // ---------- MENU/ACCOUNT ----------
    ACCOUNTS_TITLE_MENU(MENU_ACCOUNT,null,"accountsTitleMenu",EQUAL_TEXT),
    EASY_ADDRESS_TITLE_MENU(MENU_ACCOUNT,EASY_ADDRESS_DISPLAY,"accountsTitleMenu",EQUAL_TEXT),
    OPEN_ACCOUNTS_TITLE_MENU(MENU_ACCOUNT,OPEN_ACCOUNTS_DISPLAY,"accountsTitleMenu",EQUAL_TEXT),

    // ---------- MENU/QR CODE TRANSACTIONS ----------
    WITHDRAW_MONEY_MENU_ITEM(MENU_QR,null,"withdrawAndDepositTitleMenu",EQUAL_TEXT),
    DEPOSIT_MONEY_MENU_ITEM(MENU_QR,null,"withdrawAndDepositTitleMenu",EQUAL_TEXT),
    TRANSFER_MONEY_MENU_ITEM(MENU_QR,TRANSFER_MONEY_DISPLAY,"withdrawAndDepositTitleMenu",EQUAL_TEXT),
    TRANSFER_MONEY_QR_MENU_ITEM(MENU_QR,TRANSFER_MONEY_DISPLAY,"transferQRBodyMenu",EQUAL_TEXT),

    // ---------- MENU/CURRENCY / PRECIOUS METALS ----------
    CURRENCY_METALS_SELL_BUTTON_ITEM(MENU_CURRENCY,BUY_SELL_DISPLAY,"currencyMetalsSellTitleMenu",EQUAL_TEXT),
    CURRENCY_METALS_SELL_TEXT_ITEM(MENU_CURRENCY, CURRENCY_METALS_SELL_TEXT_DISPLAY, "currencyMetalsSellBodyTextMenu", EQUAL_TEXT, CURRENCY_METALS_SELL_BUTTON_ITEM),
    MY_ORDERS_MENU_ITEM(MENU_CURRENCY,null,"currencyMetalsSellTitleMenu",EQUAL_TEXT),
    MY_TRANSACTIONS_MENU_ITEM(MENU_CURRENCY,null,"currencyMetalsSellTitleMenu",EQUAL_TEXT),
    CURRENCY_REFERENCE_TRANSACTIONS_MENU_ITEM(MENU_CURRENCY,CURRENCY_REFERENCE_DISPLAY,"currencyMetalsSellTitleMenu",EQUAL_TEXT),
    UPDATE_TRANSACTIONS_LIMIT_MENU_ITEM(MENU_CURRENCY,UPDATE_TRANSACTION_DISPLAY,"currencyMetalsSellTitleMenu",EQUAL_TEXT),


    // ---------- MENU/CARDS----------
    DEBIT_PAYMENT_TITLE_MENU_ITEM(MENU_CARDS,DEBIT_PAYMENT_DISPLAY,"myCardApplicationTitleMenu",EQUAL_TEXT),
    MY_CARD_APPLICATION_TITLE_MENU_ITEM(MENU_CARDS,null,"myCardApplicationTitleMenu",EQUAL_TEXT),

    // ---------- MENU/FINANCING----------
    MY_LOAN_APPLICATIONS_TITLE_MENU_ITEM(MENU_FINANCING,MY_LOAN_APPLICATION_DISPLAY,"financingTitleMenu",EQUAL_TEXT),
    MY_FINANCES_TITLE_MENU_ITEM(MENU_FINANCING,MY_FINANCES_DISPLAY,"financingTitleMenu",EQUAL_TEXT),
    FINANCING_INSTALLMENT_PAYMENT_TITLE_MENU_ITEM(MENU_FINANCING,null,"financingTitleMenu",EQUAL_TEXT),
    LOAN_APPLICATION_MENU_ITEM(MENU_FINANCING,null,"menuTitleItem",EXISTS),
    FINANCING_CALCULATION_MENU_ITEM(MENU_FINANCING,FINANCING_CALCULATION_DISPLAY,"financingTitleMenu",EQUAL_TEXT),
    MOTORCYCLE_LOAN_APPLICATION_MENU_ITEM(MENU_FINANCING,null,"financingTitleMenu",EQUAL_TEXT),
    VEHICLE_LOAN_APPLICATION_MENU_TEXT(MENU_FINANCING, VEHICLE_LOAN_APPLICATION_DISPLAY, "vehicleLoanApplicationBodyTextMenu", EQUAL_TEXT),
    VEHICLE_LOAN_APPLICATION_MENU_TITLE(MENU_FINANCING, VEHICLE_LOAN_TITLE_DISPLAY, "vehicleLoanApplicationTitleMenu", EQUAL_TEXT, VEHICLE_LOAN_APPLICATION_MENU_TEXT),

    // ---------- MENU/INVESTMENT----------
    MY_PORTFOLIO_MENU_ITEM(MENU_INVESTMENT,PORTFOLIO_DISPLAY,"myPortfolioTitleMenu",EQUAL_TEXT),
    BUY_SELL_MENU_ITEM(MENU_INVESTMENT,PORTFOLIO_DISPLAY,"myPortfolioTitleMenu",EQUAL_TEXT),
    MY_TRANSACTIONS_TITLE_MENU_ITEM(MENU_INVESTMENT,null,"myPortfolioTitleMenu",EQUAL_TEXT),
    MY_ORDERS_TITLE_MENU_ITEM(MENU_INVESTMENT,null,"myPortfolioTitleMenu",EQUAL_TEXT),
    SUITABILITY_MENU_ITEM(MENU_INVESTMENT,null,"myPortfolioTitleMenu",EQUAL_TEXT),
    MY_INVESTMENT_ACCOUNTS_MENU_TEXT(MENU_INVESTMENT,MY_INVESTMENT_ACCOUNTS_TEXT_DISPLAY,"myInvestmentAccountsMenuText",EQUAL_TEXT),
    MY_INVESTMENT_ACCOUNTS_MENU_TITLE(MENU_INVESTMENT,MY_INVESTMENT_ACCOUNTS_TITLE_DISPLAY,"myPortfolioTitleMenu",EQUAL_TEXT,MY_INVESTMENT_ACCOUNTS_MENU_TEXT),

    // ---------- MENU/CHECK----------
    MY_CHECK_TITLE_MENU_ITEM(MENU_CHECK_PROMISSORY_NOTE,MY_CHECK_DISPLAY,"myCheckbooksTitleMenu",EQUAL_TEXT),

    // ---------- MENU/DOCUMENTS----------
    MY_DOCUMENTS_TITLE_MENU_ITEM(MENU_MY_DOCUMENTS,null,"myDocumentsMenuText",EQUAL_TEXT),

    // ---------- MENU/MONEY TRANSFER----------
    MONEY_TRANSFER_TITLE_MENU_ITEM(MENU_MONEY_TRANSFER,null,"moneyTransferMenuText",EQUAL_TEXT),
    MONEY_TRANSFER_TITLE_MENU_DISPLAY(MENU_MONEY_TRANSFER,MONEY_TRANSFER_DISPLAY,"moneyTransferMenuText",EQUAL_TEXT),
    MONEY_CARDS_TITLE_MENU_ITEM(MENU_MONEY_TRANSFER,MONEY_TRANSFER_DISPLAY,"moneyTransferMyCardOptionMenuText",EQUAL_TEXT),
    MONEY_CARDS_TITLE_MENU_DISPLAY(MENU_MONEY_TRANSFER,MONEY_TRANSFER_DISPLAY,"moneyTransferMyCardMenuText",EQUAL_TEXT),
    MONEY_CARDS_SECURE_TITLE_MENU_DISPLAY(MENU_MONEY_TRANSFER,MONEY_TRANSFER_SECURE_DISPLAY,"moneyTransferMyCardMenuText",EQUAL_TEXT);



    private final AssertionPrefix prefix;
    private final DisplayText displayText;
    private final String elementKey;
    private final AssertionType type;
    private final AssertionKey fallback;

    AssertionKey(AssertionPrefix prefix,
                 DisplayText displayText,
                 String elementKey,
                 AssertionType type,
                 AssertionKey fallback) {
        this.prefix = prefix;
        this.displayText = displayText;
        this.elementKey = elementKey;
        this.type = type;
        this.fallback = fallback;
    }

    AssertionKey(AssertionPrefix prefix,
                 DisplayText displayText,
                 String elementKey,
                 AssertionType type) {
        this(prefix, displayText, elementKey, type, null);
    }

    private HardAssertion assertion;

    public void bind(HardAssertion assertion) {
        this.assertion = assertion;
    }

    public void runAssertion() {
        type.execute(this);
    }

    public AssertionKey runAssertion(String actual, String expected) {
        type.execute(this, actual, expected);
        return this;
    }

    public AssertionKey runAssertionInList(List<String> textList, List<String> expected) {
        List<String> normalizedTextList = textList.stream()
                .map(assertion::normalizeText)
                .toList();
        for (String expectedItem : expected) {
            String normalizedExpected = assertion.normalizeText(expectedItem);
            String found = normalizedTextList.stream()
                    .filter(text -> text.equals(normalizedExpected))
                    .findFirst()
                    .orElse(null);
            if (found == null) {
                runAssertion(null, expectedItem);
                return this;
            }
        }
        return this;
    }

    public static AssertionKey resolve(AssertionPrefix prefix, String display) {
        List<AssertionKey> filtered = Arrays.stream(values())
                .filter(k -> k.prefix == prefix)
                .toList();
        for (AssertionKey key : filtered) {
            if (key.getDisplayText() != null) {
                String[] texts = key.getDisplayText().getTexts();
                if (texts != null && Arrays.asList(texts).contains(display)) {
                    return key;
                }
            }
        }
        for (AssertionKey key : filtered) {
            if (key.getDisplayText() == null) {
                return key;
            }
        }
        throw new IllegalStateException(
                "Default key not defined for prefix: " + prefix + " (display=" + display + ")"
        );
    }
}