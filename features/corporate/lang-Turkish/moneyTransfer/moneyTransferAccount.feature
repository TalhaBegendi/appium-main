@devices=pixel7
Feature: Money Transfer to account flows language Turkish - ANDROID - CORPORATE

  Background:
    Given Login as "CORPORATE" customer "STANDARD_USER" using "TURKISH" language
    Given Go to "Para Transferi > Başka Hesaba (Havale / EFT / FAST) > Hesap" from Menu

  Scenario: CORPORATE - Kayıtlı İşlem ile Aynı Gün Hesap Numarasına Transferinin Onaya Gönderilmesi
    When Click the make from saved transactions button
    And Selects one of the saved transactions from the list
    And Enter transaction amount as "2" TL and description
    When Click continue button on the Another Account page
    When Click confirm button on confirmation page
    And Enter the OTP code
    Then The transaction should be successfully sent for approval and Account

  Scenario: CORPORATE - Bireysel Müşteri için Aynı Gün Hesaba Transferinin Onaya Gönderilmesi
    When Enter "RETAIL" transaction details to account for today
    When Click continue button on the Another Account page
    When Click confirm button on confirmation page
    And Enter the OTP code
    Then The transaction should be successfully sent for approval and Account

  Scenario: CORPORATE - Kurumsal Müşteri için Aynı Gün Hesaba Transferinin Onaya Gönderilmesi
    When Enter "CORPORATE" transaction details to account for today
    When Click continue button on the Another Account page
    When Click confirm button on confirmation page
    And Enter the OTP code
    Then The transaction should be successfully sent for approval and Account
