@devices=16e
Feature: Accounts flows language English

  Background:
    When Login to App with Customer "ACCOUNT_USER" using language "ENGLISH"

  Scenario: Accounts > Open Account - Investment Account - Turkish Lira
    When Go to "Accounts > Open Account" from Menu with assertion "OPEN_ACCOUNTS_TITLE_MENU" of type "EQUAL" using "NAME"
    When Opens a "Investment Account" account with "Turkish Lira" currency

  Scenario: Accounts > Open Account - Investment Account - US Dollar
    When Go to "Accounts > Open Account" from Menu with assertion "OPEN_ACCOUNTS_TITLE_MENU" of type "EQUAL" using "NAME"
    When Opens a "Investment Account" account with "US Dollar" currency

