@devices=pixel7
Feature: Money Transfer flows language English

  Background:
    Given Login as "RETAIL" customer "ACCOUNT_USER" using "ENGLISH" language
    When Go to "Money Transfer > To Another Account (Wire Transfer / EFT / FAST) > IBAN" from Menu

  Scenario: Performing Same-Day IBAN Transfer Using a Saved Transaction
    When Click the make from saved transactions button
    And Selects one of the saved transactions from the list
    And Enter transaction amount as "2" TL and description
    Then The selected transaction details should be displayed correctly
    When Click continue button on the Another Account page
    Then The transaction details should be displayed correctly on the Verification page
    When Click confirm button on confirmation page
    And Enter the OTP code
    Then The transaction should be successfully sent for approval

  Scenario: Performing Same-Day IBAN Transfer for Retail Customer
    When Enter "RETAIL" customer transaction details for today
    When Click continue button on the Another Account page
    Then The transaction details should be displayed correctly on the Verification page
    When Click confirm button on confirmation page
    And Enter the OTP code
    Then The transaction should be successfully sent for approval

  Scenario: Performing Same-Day IBAN Transfer for Corporate Customer
    When Enter "CORPORATE" customer transaction details for today
    When Click continue button on the Another Account page
    Then The transaction details should be displayed correctly on the Verification page
    When Click confirm button on confirmation page
    And Enter the OTP code
    Then The transaction should be successfully sent for approval

  Scenario: Performing Future-Dated IBAN Transfer for Retail Customer
    When Enter "RETAIL" customer transaction details for "9" days later
    When Click continue button on the Another Account page
    When Click confirm button on confirmation page
    And Enter the OTP code
    Then The transaction should be for forward date completed successfully

  Scenario: Preventing Transfer with Different Currency for Retail Customer
    When Enter "RETAIL" customer transaction details with different currency for today
    When Click continue button on the Another Account page
    Then The different currency error message should be displayed

  Scenario: Displaying a Warning Message When the User’s Own IBAN Is Entered
    When Enter fund transaction details for today
    Then Verify fund warning error should be displayed



