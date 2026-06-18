@devices=pixel7
Feature: Money Transfer flows language Turkish

  Background:
    Given Login as "RETAIL" customer "ACCOUNT_USER" using "TURKISH" language
    Given Go to "Para Transferi > Başka Hesaba (Havale / EFT / FAST) > Hesap" from Menu

  Scenario: Kayıtlı İşlem ile Aynı Gün Hesap Numarasına Transferinin Yapılması
    When Click the make from saved transactions button
    And Selects one of the saved transactions from the list
    And Enter transaction amount as "2" TL and description
    When Click money transfer case continue button on the Another Account page
    When Click confirm button on confirmation page
    And Enter the OTP code with give permission
    Then The transaction should be successfully sent for approval and Account

  Scenario: Bireysel Müşteri için Aynı Gün Hesaba Transferinin Yapılması
    When Enter "RETAIL" transaction details to account for today
    When Click money transfer case continue button on the Another Account page
    When Click confirm button on confirmation page
    And Enter the OTP code with give permission
    Then The transaction should be successfully sent for approval and Account

  Scenario: Kurumsal Müşteri için Aynı Gün Hesaba Transferinin Yapılması
    When Enter "CORPORATE" transaction details to account for today
    When Click money transfer case continue button on the Another Account page
    When Click confirm button on confirmation page
    And Enter the OTP code with give permission
    Then The transaction should be successfully sent for approval and Account

