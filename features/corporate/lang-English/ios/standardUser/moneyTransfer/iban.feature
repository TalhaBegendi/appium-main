@devices=pixel7
Feature: Money Transfer flows language Turkish

  Background:
    Given Login as "CORPORATE" customer "STANDARD_USER" using "ENGLISH" language
    When Go to "Money Transfer > To Another Account (Wire Transfer / EFT / FAST) > IBAN" from Menu with assertion "MONEY_TRANSFER_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: Sending a Same-Day IBAN Transfer for Approval Using a Saved Transaction
    When Click the make from saved transactions button
    And Selects one of the saved transactions from the list
    And Enter transaction amount as "4" TL and description
    Then The selected transaction details should be displayed correctly
    When Click continue button on the Another Account page
    Then The transaction details should be displayed correctly on the Verification page
    When Click confirm button on confirmation page
    And Enter the OTP code
    Then The transaction should be successfully sent for approval

  Scenario: Sending a Same-Day IBAN Transfer for Approval for a Retail Customer
    When Enter "RETAIL" customer transaction details for today
    When Click continue button on the Another Account page
    Then The transaction details should be displayed correctly on the Verification page
    When Click confirm button on confirmation page
    And Enter the OTP code
    Then The transaction should be successfully sent for approval

  Scenario: Sending a Same-Day IBAN Transfer for Approval for a Corporate Customer
    When Enter "CORPORATE" customer transaction details for today
    When Click continue button on the Another Account page
    Then The transaction details should be displayed correctly on the Verification page
    When Click confirm button on confirmation page
    And Enter the OTP code
    Then The transaction should be successfully sent for approval

  Scenario: Sending a Future-Dated IBAN Transfer for Approval for a Corporate Customer
    When Enter "CORPORATE" customer transaction details for "9" days later
    When Click continue button on the Another Account page
    When Click confirm button on confirmation page
    And Enter the OTP code
    Then The transaction should be successfully sent for approval

  Scenario: Preventing a Cross-Currency Transfer for a Corporate Customer
    When Enter "CORPORATE" customer transaction details with different currency for today
    When Click continue button on the Another Account page
    Then The different currency error message should be displayed

  Scenario: Kullanıcıya Ait IBAN Girildiğinde Uyarı Mesajı Gösterilmesi
    When Enter fund transaction details for today
    Then Verify fund warning error should be displayed


