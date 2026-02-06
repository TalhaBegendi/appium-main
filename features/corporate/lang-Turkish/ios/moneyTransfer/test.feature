@devices=15
Feature: Money Transfer flows language Turkish

  Background:
    Given Login as "CORPORATE" customer "STANDARD_USER_2" using "TURKISH" language
    And Go to "Para Transferi > Başka Hesaba (Havale / EFT / FAST)" from Menu with assertion "MONEY_TRANSFER_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"
    And Click account tab

  Scenario: Kurumsal Müşteri için İleri Tarihli Hesaba Para Transferinin Onaya Gönderilmesi
    When Enter "CORPORATE" customer transaction details for "9" days later
    When Click continue button on the Another Account page
    When Click confirm button on confirmation page
    And Enter the OTP code
    Then The transaction should be successfully sent for approval