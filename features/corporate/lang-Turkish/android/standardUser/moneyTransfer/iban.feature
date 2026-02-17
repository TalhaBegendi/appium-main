@devices=pixel7
Feature: Money Transfer flows language Turkish - ANDROID - CORPORATE

  Background:
    Given Login as "CORPORATE" customer "STANDARD_USER_2" using "TURKISH" language
    Given Go to "Para Transferi > Başka Hesaba (Havale / EFT / FAST) > IBAN" from Menu with assertion "MONEY_TRANSFER_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: CORPORATE - Kayıtlı İşlem ile Aynı Gün IBAN Transferinin Onaya Gönderilmesi
    When Click the make from saved transactions button
    And Selects one of the saved transactions from the list
    And Enter transaction amount as "2" TL and description
    Then The selected transaction details should be displayed correctly
    When Click continue button on the Another Account page
    Then The transaction details should be displayed correctly on the Verification page
    When Click confirm button on confirmation page
    And Enter the OTP code
    Then The transaction should be successfully sent for approval