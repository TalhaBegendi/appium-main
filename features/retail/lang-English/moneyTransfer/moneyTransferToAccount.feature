@devices=16e
Feature: Money Transfer flows language English

  Background:
    Given Login as "RETAIL" customer "ACCOUNT_USER" using "ENGLISH" language
    When Go to "Money Transfer > To Another Account (Wire Transfer / EFT / FAST) > Account" from Menu with assertion "MONEY_TRANSFER_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: Sending a Same-Day IBAN Transfer for Approval Using a Saved Transaction
    When Click the make from saved transactions button
    And Selects one of the saved transactions from the list
    And Enter transaction amount as "2" TL and description
    When Click continue button on the Another Account page
    When Click confirm button on confirmation page
    And Enter the OTP code
    Then The transaction should be successfully sent for approval and Account

  Scenario: Performing Same-Day Account Transfer for a Retail Customer
    When Enter "RETAIL" transaction details to account for today
    When Click continue button on the Another Account page
    When Click confirm button on confirmation page
    And Enter the OTP code
    Then The transaction should be successfully sent for approval and Account

  Scenario: Performing Same-Day Account Transfer for a Corporate Customer
    When Enter "CORPORATE" transaction details to account for today
    When Click continue button on the Another Account page
    When Click confirm button on confirmation page
    And Enter the OTP code
    Then The transaction should be successfully sent for approval and Account

