@devices=15
Feature: Saved Transactions Flows Language Turkish

  Background:
    Given Login as "CORPORATE" customer "STANDARD_USER" using "TURKISH" language
    And Go to "Kayıtlı İşlemler" from Menu

  Scenario: Kayıtlı Bir İşlem Üzerinden Para Transferi Talimatı Oluşturulması
    When Select one of the saved transactions from the list
    And Enter transaction amount as "5" TL and description
    When Click continue button on the Another Account page
    When Click confirm button on confirmation page
    And Enter the OTP code
    Then The transaction should be successfully sent for approval
