@devices=16e
Feature: Menu navigation flows language English

  Background:
    Given Login as "RETAIL" customer "MENU_USER" using "ENGLISH" language

  Scenario: Go to Home from Menu
    When Go to "Home" from Menu

  Scenario: Go to Accounts from Menu
    When Go to "Accounts" from Menu

  Scenario: Go to Money Transfer from Menu
    When Go to "Money Transfer" from Menu

  Scenario: Go to Saved Transaction from Menu
    When Go to "Saved Transaction" from Menu

  Scenario: Go to QR Code Transactions from Menu
    When Go to "QR Code Transactions" from Menu

  Scenario: Go to Currency / Precious Metals from Menu
    When Go to "Currency / Precious Metals" from Menu

  Scenario: Go to Cards from Menu
    When Go to "Cards" from Menu

  Scenario: Go to Financing from Menu
    When Go to "Financing" from Menu

  Scenario: Go to Investment from Menu
    When Go to "Investment" from Menu

  Scenario: Go to Check - Promissory Note from Menu
    When Go to "Check - Promissory Note" from Menu

  Scenario: Go to Documents from Menu
    When Go to "Documents" from Menu

  Scenario: Go to Settings from Menu
    When Go to "Settings" from Menu

  Scenario: Go to Applications from Menu
    When Go to "Applications" from Menu

  Scenario: Go to Notifications And Messages from Menu
    When Go to "Notifications And Messages" from Menu

  Scenario: Go to About from Menu
    When Go to "About" from Menu

  Scenario: Applications > My Card Applications
    When Go to "Applications > My Card Applications" from Menu with assertion "ACCOUNTS_TITLE_MENU" of type "EQUAL" using "NAME"

  Scenario: Go to My Accounts on Accounts from Menu
    When Go to "Accounts > My Accounts" from Menu with assertion "ACCOUNTS_TITLE_MENU" of type "EQUAL" using "NAME"

  Scenario: Go to Open Accounts on Accounts from Menu
    When Go to "Accounts > Open Account" from Menu with assertion "OPEN_ACCOUNTS_TITLE_MENU" of type "EQUAL" using "NAME"

  Scenario: Go to Favorite IBAN on Accounts from Menu
    When Go to "Accounts > Favorite IBAN" from Menu with assertion "ACCOUNTS_TITLE_MENU" of type "EQUAL" using "NAME"

  Scenario: Go to Easy Addresses on Accounts from Menu
    When Go to "Accounts > Easy Addresses" from Menu with assertion "EASY_ADDRESS_TITLE_MENU" of type "EQUAL" using "NAME"

  Scenario: Go to ATM Withdraw Money on QR Code Transactions from Menu
    When Go to "QR Code Transactions > ATM Withdraw Money" from Menu with assertion "WITHDRAW_MONEY_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: Go to ATM Deposit Money on QR Code Transactions from Menu
    When Go to "QR Code Transactions > ATM Deposit Money" from Menu with assertion "DEPOSIT_MONEY_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: Go to Pay With QR Code on QR Code Transactions from Menu
    When Go to "QR Code Transactions > Pay With QR Code" from Menu with assertion "TRANSFER_MONEY_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: Go to Send Money With QR Code on QR Code Transactions from Menu
    When Go to "QR Code Transactions > Send Money With QR Code" from Menu with assertion "TRANSFER_MONEY_QR_MENU_ITEM" of type "EQUAL" using "LABEL"


  Scenario: Go to Currency / Precious Metals Buy / Sell on Currency / Precious Metals from Menu
    When Go to "Currency / Precious Metals > Currency / Precious Metals Buy / Sell" from Menu with assertion "CURRENCY_METALS_SELL_TEXT_ITEM" of type "PRESENCE_THEN_EQUAL" using "NAME"

  Scenario: Go to My Orders on Currency / Precious Metals from Menu
    When Go to "Currency / Precious Metals > My Orders" from Menu with assertion "MY_ORDERS_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: Go to My Transactions on Currency / Precious Metals from Menu
    When Go to "Currency / Precious Metals > My Transactions" from Menu with assertion "MY_TRANSACTIONS_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: Go to Currency Reference Transactions on Currency / Precious Metals from Menu
    When Go to "Currency / Precious Metals > Currency Reference Transactions" from Menu with assertion "CURRENCY_REFERENCE_TRANSACTIONS_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: Go to Update Transaction Limits on Currency / Precious Metals from Menu
    When Go to "Currency / Precious Metals > Update Transaction Limits" from Menu with assertion "UPDATE_TRANSACTIONS_LIMIT_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: Go to My Cards on Cards from Menu
    When Go to "Cards > My Cards" from Menu with assertion "MY_CARD_APPLICATION_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: Go to Card Application on Cards from Menu
    When Go to "Cards > Card Application" from Menu with assertion "MY_CARD_APPLICATION_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: Go to My Card Application on Cards from Menu
    When Go to "Cards > My Card Applications" from Menu with assertion "MY_CARD_APPLICATION_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: Cards > Debt Payment > To My Card
    When Go to "Cards > Debt Payment > To My Card" from Menu with assertion "MONEY_CARDS_TITLE_MENU_ITEM" of type "EQUAL" using "CONTENT_DESC"

  Scenario: Cards > Debt Payment > To Other Card
    When Go to "Cards > Debt Payment > To Other Card" from Menu with assertion "MONEY_CARDS_TITLE_MENU_ITEM" of type "EQUAL" using "CONTENT_DESC"

  Scenario: Go to Expense Objection on Cards from Menu
    When Go to "Cards > Expense Objection" from Menu with assertion "DEBIT_PAYMENT_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"


  Scenario: Go to Loan Application on Financing from Menu
    When Go to "Financing > Loan Application" from Menu with assertion "LOAN_APPLICATION_MENU_ITEM" of type "PRESENCE" using ""

  Scenario: Go to My Loan Applications on Financing from Menu
    When Go to "Financing > My Loan Applications" from Menu with assertion "MY_LOAN_APPLICATIONS_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: Go to My Finances on Financing from Menu
    When Go to "Financing > My Finances" from Menu with assertion "MY_FINANCES_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: Go to Financing Installment Payment Application on Cards from Menu
    When Go to "Financing > Financing Installment Payment" from Menu with assertion "FINANCING_INSTALLMENT_PAYMENT_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: Go to Vehicle Loan Application on Financing from Menu
    When Go to "Financing > Loan Application > Vehicle Loan Application" from Menu with assertion "VEHICLE_LOAN_APPLICATION_MENU_TITLE" of type "EQUAL" using "NAME"

  Scenario: Go to Motorcycle Loan Application on Financing from Menu
    When Go to "Financing > Loan Application > Motorcycle Loan Application" from Menu with assertion "MOTORCYCLE_LOAN_APPLICATION_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: Go to Calculations on Financing from Menu
    When Go to "Financing > Loan Application > Calculations" from Menu with assertion "FINANCING_CALCULATION_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: Investment > My Portfolio (Stocks / Funds / Lease C.)
    When Go to "Investment > My Portfolio (Stocks / Funds / Lease C.)" from Menu with assertion "MY_PORTFOLIO_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: Investment > Buy / Sell (Stocks / Funds / Lease C.)
    When Go to "Investment > Buy / Sell (Stocks / Funds / Lease C.)" from Menu with assertion "BUY_SELL_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: Investment > My Transactions
    When Go to "Investment > My Transactions" from Menu with assertion "MY_TRANSACTIONS_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: Investment > My Orders
    When Go to "Investment > My Orders" from Menu with assertion "MY_ORDERS_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: Investment > My Investment Accounts
    When Go to "Investment > My Investment Accounts" from Menu with assertion "MY_INVESTMENT_ACCOUNTS_MENU_TITLE" of type "EQUAL" using "NAME"

  Scenario: Investment > Suitability Test
    When Go to "Investment > Suitability Test" from Menu with assertion "SUITABILITY_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: Check-Promissory Note > Cheque Transactions > My Checks
    When Go to "Check - Promissory Note > Cheque Transactions > My Checks" from Menu with assertion "MY_CHECK_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: Check-Promissory Note - Senet > Cheque Transactions > My Checkbooks
    When Go to "Check - Promissory Note > Cheque Transactions > My Checkbooks" from Menu with assertion "MY_CHECK_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: Check-Promissory Note - Senet > Cheque Transactions > My ChequeBook Applications
    When Go to "Check - Promissory Note > Cheque Transactions > My ChequeBook Applications" from Menu with assertion "MY_CHECK_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: Documents > My Documents
    When Go to "Documents > My Documents" from Menu with assertion "MY_DOCUMENTS_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"



  Scenario: Money Transfer > Last Transactions
    When Go to "Money Transfer > Last Transactions" from Menu with assertion "MONEY_TRANSFER_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: Money Transfer > Saved Money Transfers
    When Go to "Money Transfer > Saved Money Transfers" from Menu with assertion "MONEY_TRANSFER_TITLE_MENU_DISPLAY" of type "EQUAL" using "NAME"

  Scenario: Money Transfer > Between My Accounts
    When Go to "Money Transfer > Between My Accounts" from Menu with assertion "MONEY_TRANSFER_TITLE_MENU_DISPLAY" of type "EQUAL" using "NAME"

  Scenario: Money Transfer > To Another Account (Wire Transfer / EFT / FAST) > IBAN
    When Go to "Money Transfer > To Another Account (Wire Transfer / EFT / FAST) > IBAN" from Menu with assertion "MONEY_TRANSFER_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: Money Transfer > To Another Account (Wire Transfer / EFT / FAST) > Account
    When Go to "Money Transfer > To Another Account (Wire Transfer / EFT / FAST) > Account" from Menu with assertion "MONEY_TRANSFER_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: Money Transfer > To Another Account (Wire Transfer / EFT / FAST) > Telephone No
    When Go to "Money Transfer > To Another Account (Wire Transfer / EFT / FAST) > Telephone No" from Menu with assertion "MONEY_TRANSFER_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: Money Transfer > To Another Account (Wire Transfer / EFT / FAST) > Other
    When Go to "Money Transfer > To Another Account (Wire Transfer / EFT / FAST) > Other" from Menu with assertion "MONEY_TRANSFER_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: Money Transfer > Currency Transfer > Currency Transfers
    When Go to "Money Transfer > Currency Transfer > My Currency Transfers" from Menu with assertion "MONEY_TRANSFER_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: Money Transfer > My Money Transfer Instructions
    When Go to "Money Transfer > My Money Transfer Instructions" from Menu with assertion "MONEY_TRANSFER_TITLE_MENU_DISPLAY" of type "EQUAL" using "NAME"

  Scenario: Money Transfer > Transfer Limits
    When Go to "Money Transfer > Transfer Limits" from Menu with assertion "MONEY_TRANSFER_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: Money Transfer > To Card > To My Card
    When Go to "Money Transfer > To Card > To My Card" from Menu with assertion "MONEY_CARDS_TITLE_MENU_ITEM" of type "EQUAL" using "CONTENT_DESC"

  Scenario: Money Transfer > To Card > To Other Card
    When Go to "Money Transfer > To Card > To Other Card" from Menu with assertion "MONEY_CARDS_TITLE_MENU_ITEM" of type "EQUAL" using "CONTENT_DESC"

  Scenario: Money Transfer > Send to Mobile and Withdraw from ATM > Send Money
    When Go to "Money Transfer > Send to Mobile and Withdraw from ATM > Send Money" from Menu with assertion "MONEY_TRANSFER_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: Money Transfer > Send to Mobile and Withdraw from ATM > My Transactions
    When Go to "Money Transfer > Send to Mobile and Withdraw from ATM > My Transactions" from Menu with assertion "MONEY_TRANSFER_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: Money Transfer > Secure Payment Transactions > Secure Payment Registration
    When Go to "Money Transfer > Secure Payment Transactions > Secure Payment Registration" from Menu with assertion "MONEY_CARDS_SECURE_TITLE_MENU_DISPLAY" of type "EQUAL" using "NAME"

  Scenario: Money Transfer > Secure Payment Transactions > Secure Payment Transaction
    When Go to "Money Transfer > Secure Payment Transactions > Secure Payment Transaction" from Menu with assertion "MONEY_CARDS_SECURE_TITLE_MENU_DISPLAY" of type "EQUAL" using "NAME"

  Scenario: Money Transfer > To Another Account (Wire Transfer / EFT / FAST) > TR QR Code
    When Go to "Money Transfer > To Another Account (Wire Transfer / EFT / FAST) > TR QR Code" from Menu with assertion "TRANSFER_MONEY_QR_MENU_ITEM" of type "EQUAL" using "LABEL"
