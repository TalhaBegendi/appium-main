@devices=pixel7
Feature: Saved Transactions Flows Language Turkish

  Background:
    Given Login as "CORPORATE" customer "STANDARD_USER" using "TURKISH" language
    And Go to "Kayıtlı İşlemler" from Menu

  Scenario: Hesaplarım Arasında Yeni Kayıtlı Transfer Oluşturulması
    When Click add new saved transaction button
    Then Verify add new saved transaction screen is visible
    When Enter "virman" as new saved transaction name
    And Select "Para Transferi" as transaction type
    And Select "Hesaplarım Arası" as money transfer category
    And Select one own account as receiver
    And Click save button for saved transaction
    And Enter the OTP code
    Then Saved transfer should be created successfully