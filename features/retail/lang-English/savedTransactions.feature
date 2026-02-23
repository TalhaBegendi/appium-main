@devices=pixel7
Feature: Saved Transactions flows language English

  Background:
    Given Login as "RETAIL" customer "ACCOUNT_USER" using "ENGLISH" language
    And Go to "Saved Transaction" from Menu

  Scenario: Creating a New Saved Transfer Between My Accounts
    When Click add new saved transaction button
    Then Verify add new saved transaction screen is visible
    When Enter "avirman" as new saved transaction name
    And Select "Money Transfer" as transaction type
    And Select "Between My Accounts" as money transfer category
    And Select one own account as receiver
    And Click save button for saved transaction
    And Click confirm button on confirmation page for Saved Transaction
    And Enter the OTP code
    Then Saved transfer should be created successfully

  Scenario: Creating a Money Transfer Instruction via a Saved Transaction
    When Select one of the saved transactions from the list
    And Enter transaction amount as "5" TL and description
    When Click continue button on the Another Account page
    And Click confirm button on confirmation page
    And Enter the OTP code
    Then The transaction should be successfully sent for approval and Saved Transaction

  Scenario: Deleting a Saved Transfer Between My Accounts
    When Click delete button for saved transaction named "avirman"
    Then Verify delete confirmation popup is displayed
    When Click confirm delete button on delete popup
    Then Verify saved transaction is deleted successfully

  Scenario: Creating a New Saved Transfer to IBAN
    When Click add new saved transaction button
    Then Verify add new saved transaction screen is visible
    When Enter "iban" as new saved transaction name
    And Select "Money Transfer" as transaction type
    And Select "To Another Account (Wire Transfer / EFT / FAST)" as money transfer category
    And Select "to IBAN" as money transfer category
    And Enter receiver IBAN
    And Click save button for saved transaction
    And Click confirm button on confirmation page for Saved Transaction
    And Enter the OTP code
    Then Saved transfer should be created successfully

  Scenario: Deleting a Saved Transfer to IBAN
    When Click delete button for saved transaction named "iban"
    Then Verify delete confirmation popup is displayed
    When Click confirm delete button on delete popup
    Then Verify saved transaction is deleted successfully

  Scenario: Creating a New Saved Transfer to Account Number
    When Click add new saved transaction button
    Then Verify add new saved transaction screen is visible
    When Enter "hesap numarası" as new saved transaction name
    And Select "Money Transfer" as transaction type
    And Select "To Another Account (Wire Transfer / EFT / FAST)" as money transfer category
    And Select "Account" as money transfer category for platform
    And Select recipient bank as "HALK KATILIM BANKASI A.Ş."
    And Enter receiver account info
    And Click save button for saved transaction
    And Click confirm button on confirmation page for Saved Transaction
    And Enter the OTP code
    Then Saved transfer should be created successfully

  Scenario: Deleting a Saved Transfer to Account Number
    When Click delete button for saved transaction named "hesap numarası"
    Then Verify delete confirmation popup is displayed
    When Click confirm delete button on delete popup
    Then Verify saved transaction is deleted successfully

  Scenario: Displaying Matching Results in Saved Transactions Search
    When Into saved transactions search field
    Then All saved transactions matching displayed

  Scenario: No Results Found in Saved Transactions Search
    When Enter "aa" into saved transactions search field
    Then No search results found message is displayed

  Scenario: Displaying Warning Message When Own IBAN Is Entered
    When Click add new saved transaction button
    Then Verify add new saved transaction screen is visible
    When Enter "iban" as new saved transaction name
    And Select "Money Transfer" as transaction type
    And Select "To Another Account (Wire Transfer / EFT / FAST)" as money transfer category
    And Select "to IBAN" as money transfer category
    And Enter own IBAN as receiver
    Then Verify fund iban warning error should be displayed

  Scenario: Displaying Warning Message When Own Account Number Is Entered
    When Click add new saved transaction button
    Then Verify add new saved transaction screen is visible
    When Enter "hesap numarası" as new saved transaction name
    And Select "Money Transfer" as transaction type
    And Select "To Another Account (Wire Transfer / EFT / FAST)" as money transfer category
    And Select "Account" as money transfer category for platform
    And Select recipient bank as "HALK KATILIM BANKASI A.Ş."
    And Enter own account info for receiver
    And Click save button for saved transaction
    Then Verify fund account warning error should be displayed



