@devices=pixel7
Feature: Accounts flows language English

  Background:
    Given Login to App with Customer "OPTION_USER" using language "ENGLISH"

  Scenario: Accounts > My Accounts - Turkish Lira
    When Go to "Accounts > My Accounts" from Menu with assertion "ACCOUNTS_TITLE_MENU" of type "EQUAL" using "NAME"
    When Click element "openAccountButtonAccounts"
    When Opens a "Checking Account" account with "Turkish Lira" currency

  Scenario: Accounts > My Accounts - US Dollar
    When Go to "Accounts > My Accounts" from Menu with assertion "ACCOUNTS_TITLE_MENU" of type "EQUAL" using "NAME"
    When Click element "openAccountButtonAccounts"
    When Opens a "Checking Account" account with "US Dollar" currency

  Scenario: Accounts > My Accounts - Euro
    When Go to "Accounts > My Accounts" from Menu with assertion "ACCOUNTS_TITLE_MENU" of type "EQUAL" using "NAME"
    When Click element "openAccountButtonAccounts"
    When Opens a "Checking Account" account with "Euro" currency

  Scenario: Accounts > My Accounts - Ruby
    When Go to "Accounts > My Accounts" from Menu with assertion "ACCOUNTS_TITLE_MENU" of type "EQUAL" using "NAME"
    When Click element "openAccountButtonAccounts"
    When Opens a "Checking Account" account with "Ruby" currency

  Scenario: My Accounts > Digital Participation Account - Turkish Lira - 3 Months
    When Go to "Accounts > My Accounts" from Menu with assertion "ACCOUNTS_TITLE_MENU" of type "EQUAL" using "NAME"
    When Click element "openAccountButtonAccounts"
    When Opens a "Digital Participation Account" account with "Turkish Lira" currency and "3 Months" maturity date

  Scenario: My Accounts > Digital Participation Account - Turkish Lira - Daily 32-999 Days
    When Go to "Accounts > My Accounts" from Menu with assertion "ACCOUNTS_TITLE_MENU" of type "EQUAL" using "NAME"
    When Click element "openAccountButtonAccounts"
    When Opens a "Digital Participation Account" account with "Turkish Lira" currency and "Daily (32-999 Days)" maturity date

  Scenario: My Accounts > Digital Participation Account - Turkish Lira - Daily Profit 2-29 Days
    When Go to "Accounts > My Accounts" from Menu with assertion "ACCOUNTS_TITLE_MENU" of type "EQUAL" using "NAME"
    When Click element "openAccountButtonAccounts"
    When Opens a "Digital Participation Account" account with "Turkish Lira" currency and "Daily Profit (2-29 Days)" maturity date

  Scenario: My Accounts > Digital Participation Account - US Dollar - 3 Months
    When Go to "Accounts > My Accounts" from Menu with assertion "ACCOUNTS_TITLE_MENU" of type "EQUAL" using "NAME"
    When Click element "openAccountButtonAccounts"
    When Opens a "Digital Participation Account" account with "US Dollar" currency and "3 Months" maturity date

  Scenario: My Accounts > Digital Participation Account - US Dollar - Daily 32-999 Days
    When Go to "Accounts > My Accounts" from Menu with assertion "ACCOUNTS_TITLE_MENU" of type "EQUAL" using "NAME"
    When Click element "openAccountButtonAccounts"
    When Opens a "Digital Participation Account" account with "US Dollar" currency and "Daily (32-999 Days)" maturity date

  Scenario: My Accounts > Digital Participation Account - US Dollar - Daily Profit 2-29 Days
    When Go to "Accounts > My Accounts" from Menu with assertion "ACCOUNTS_TITLE_MENU" of type "EQUAL" using "NAME"
    When Click element "openAccountButtonAccounts"
    When Opens a "Digital Participation Account" account with "US Dollar" currency and "Daily Profit (2-29 Days)" maturity date

  Scenario: My Accounts > Digital Participation Account - Euro - 3 Months
    When Go to "Accounts > My Accounts" from Menu with assertion "ACCOUNTS_TITLE_MENU" of type "EQUAL" using "NAME"
    When Click element "openAccountButtonAccounts"
    When Opens a "Digital Participation Account" account with "Euro" currency and "3 Months" maturity date

  Scenario: My Accounts > Digital Participation Account - Euro - Daily 32-999 Days
    When Go to "Accounts > My Accounts" from Menu with assertion "ACCOUNTS_TITLE_MENU" of type "EQUAL" using "NAME"
    When Click element "openAccountButtonAccounts"
    When Opens a "Digital Participation Account" account with "Euro" currency and "Daily (32-999 Days)" maturity date

  Scenario: My Accounts > Digital Participation Account - Euro - Daily Profit 2-29 Days
    When Go to "Accounts > My Accounts" from Menu with assertion "ACCOUNTS_TITLE_MENU" of type "EQUAL" using "NAME"
    When Click element "openAccountButtonAccounts"
    When Opens a "Digital Participation Account" account with "Euro" currency and "Daily Profit (2-29 Days)" maturity date

  Scenario: My Accounts > Digital Participation Account - Ruby - 3 Months
    When Go to "Accounts > My Accounts" from Menu with assertion "ACCOUNTS_TITLE_MENU" of type "EQUAL" using "NAME"
    When Click element "openAccountButtonAccounts"
    When Opens a "Digital Participation Account" account with "Ruby" currency and "3 Months" maturity date

  Scenario: My Accounts > Digital Participation Account - Ruby - Breach Maturity
    When Go to "Accounts > My Accounts" from Menu with assertion "ACCOUNTS_TITLE_MENU" of type "EQUAL" using "NAME"
    When Click element "openAccountButtonAccounts"
    When Opens a "Digital Participation Account" account with "Ruby" currency and "Breach Maturity" maturity date

  Scenario: Accounts > My Accounts - Investment Account - Turkish Lira
    When Go to "Accounts > My Accounts" from Menu with assertion "ACCOUNTS_TITLE_MENU" of type "EQUAL" using "NAME"
    When Click element "openAccountButtonAccounts"
    When Opens a "Investment Account" account with "Turkish Lira" currency

  Scenario: Accounts > My Accounts - Investment Account - US Dollar
    When Go to "Accounts > My Accounts" from Menu with assertion "ACCOUNTS_TITLE_MENU" of type "EQUAL" using "NAME"
    When Click element "openAccountButtonAccounts"
    When Opens a "Investment Account" account with "US Dollar" currency

  Scenario: Accounts > My Accounts - Investment Account - Euro
    When Go to "Accounts > My Accounts" from Menu with assertion "ACCOUNTS_TITLE_MENU" of type "EQUAL" using "NAME"
    When Click element "openAccountButtonAccounts"
    When Opens a "Investment Account" account with "Euro" currency

  Scenario: Accounts > My Accounts - Investment Account - Ruby
    When Go to "Accounts > My Accounts" from Menu with assertion "ACCOUNTS_TITLE_MENU" of type "EQUAL" using "NAME"
    When Click element "openAccountButtonAccounts"
    When Opens a "Investment Account" account with "Ruby" currency

  Scenario: Accounts > Open Account - Turkish Lira
    When Go to "Accounts > Open Account" from Menu with assertion "OPEN_ACCOUNTS_TITLE_MENU" of type "EQUAL" using "NAME"
    When Opens a "Checking Account" account with "Turkish Lira" currency

  Scenario: Accounts > Open Account - US Dollar
    When Go to "Accounts > Open Account" from Menu with assertion "OPEN_ACCOUNTS_TITLE_MENU" of type "EQUAL" using "NAME"
    When Opens a "Checking Account" account with "US Dollar" currency

  Scenario: Accounts > Open Account - Euro
    When Go to "Accounts > Open Account" from Menu with assertion "OPEN_ACCOUNTS_TITLE_MENU" of type "EQUAL" using "NAME"
    When Opens a "Checking Account" account with "Euro" currency

  Scenario: Accounts > Open Account - Ruby
    When Go to "Accounts > Open Account" from Menu with assertion "OPEN_ACCOUNTS_TITLE_MENU" of type "EQUAL" using "NAME"
    When Opens a "Checking Account" account with "Ruby" currency

  Scenario: Open Account > Digital Participation Account - Turkish Lira - 3 Months
    When Go to "Accounts > Open Account" from Menu with assertion "OPEN_ACCOUNTS_TITLE_MENU" of type "EQUAL" using "NAME"
    When Opens a "Digital Participation Account" account with "Turkish Lira" currency and "3 Months" maturity date

  Scenario: Open Account > Digital Participation Account - Turkish Lira - Daily 32-999 Days
    When Go to "Accounts > Open Account" from Menu with assertion "OPEN_ACCOUNTS_TITLE_MENU" of type "EQUAL" using "NAME"
    When Opens a "Digital Participation Account" account with "Turkish Lira" currency and "Daily (32-999 Days)" maturity date

  Scenario: Open Account > Digital Participation Account - Turkish Lira - Daily Profit 2-29 Days
    When Go to "Accounts > Open Account" from Menu with assertion "OPEN_ACCOUNTS_TITLE_MENU" of type "EQUAL" using "NAME"
    When Opens a "Digital Participation Account" account with "Turkish Lira" currency and "Daily Profit (2-29 Days)" maturity date

  Scenario: Open Account > Digital Participation Account - US Dollar - 3 Months
    When Go to "Accounts > Open Account" from Menu with assertion "OPEN_ACCOUNTS_TITLE_MENU" of type "EQUAL" using "NAME"
    When Opens a "Digital Participation Account" account with "US Dollar" currency and "3 Months" maturity date

  Scenario: Open Account > Digital Participation Account - US Dollar - Daily 32-999 Days
    When Go to "Accounts > Open Account" from Menu with assertion "OPEN_ACCOUNTS_TITLE_MENU" of type "EQUAL" using "NAME"
    When Opens a "Digital Participation Account" account with "US Dollar" currency and "Daily (32-999 Days)" maturity date

  Scenario: Open Account > Digital Participation Account - US Dollar - Daily Profit 2-29 Days
    When Go to "Accounts > Open Account" from Menu with assertion "OPEN_ACCOUNTS_TITLE_MENU" of type "EQUAL" using "NAME"
    When Opens a "Digital Participation Account" account with "US Dollar" currency and "Daily Profit (2-29 Days)" maturity date

  Scenario: Open Account > Digital Participation Account - Euro - 3 Months
    When Go to "Accounts > Open Account" from Menu with assertion "OPEN_ACCOUNTS_TITLE_MENU" of type "EQUAL" using "NAME"
    When Opens a "Digital Participation Account" account with "Euro" currency and "3 Months" maturity date

  Scenario: Open Account > Digital Participation Account - Euro - Daily 32-999 Days
    When Go to "Accounts > Open Account" from Menu with assertion "OPEN_ACCOUNTS_TITLE_MENU" of type "EQUAL" using "NAME"
    When Opens a "Digital Participation Account" account with "Euro" currency and "Daily (32-999 Days)" maturity date

  Scenario: Open Account > Digital Participation Account - Euro - Daily Profit 2-29 Days
    When Go to "Accounts > Open Account" from Menu with assertion "OPEN_ACCOUNTS_TITLE_MENU" of type "EQUAL" using "NAME"
    When Opens a "Digital Participation Account" account with "Euro" currency and "Daily Profit (2-29 Days)" maturity date

  Scenario: Open Account > Digital Participation Account - Ruby - 3 Months
    When Go to "Accounts > Open Account" from Menu with assertion "OPEN_ACCOUNTS_TITLE_MENU" of type "EQUAL" using "NAME"
    When Opens a "Digital Participation Account" account with "Ruby" currency and "3 Months" maturity date

  Scenario: Open Account > Digital Participation Account - Ruby - Breach Maturity
    When Go to "Accounts > Open Account" from Menu with assertion "OPEN_ACCOUNTS_TITLE_MENU" of type "EQUAL" using "NAME"
    When Opens a "Digital Participation Account" account with "Ruby" currency and "Breach Maturity" maturity date

  Scenario: Accounts > Open Account - Investment Account - Turkish Lira
    When Go to "Accounts > Open Account" from Menu with assertion "OPEN_ACCOUNTS_TITLE_MENU" of type "EQUAL" using "NAME"
    When Opens a "Investment Account" account with "Turkish Lira" currency

  Scenario: Accounts > Open Account - Investment Account - US Dollar
    When Go to "Accounts > Open Account" from Menu with assertion "OPEN_ACCOUNTS_TITLE_MENU" of type "EQUAL" using "NAME"
    When Opens a "Investment Account" account with "US Dollar" currency

  Scenario: Accounts > Open Account - Investment Account - Euro
    When Go to "Accounts > Open Account" from Menu with assertion "OPEN_ACCOUNTS_TITLE_MENU" of type "EQUAL" using "NAME"
    When Opens a "Investment Account" account with "Euro" currency

  Scenario: Accounts > Open Account - Investment Account - Ruby
    When Go to "Accounts > Open Account" from Menu with assertion "OPEN_ACCOUNTS_TITLE_MENU" of type "EQUAL" using "NAME"
    When Opens a "Investment Account" account with "Ruby" currency
