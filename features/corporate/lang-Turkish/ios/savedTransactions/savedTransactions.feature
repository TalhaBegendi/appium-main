@devices=15
Feature: Saved Transactions Flows Language Turkish - IOS - CORPORATE

  Background:
    Given Login as "CORPORATE" customer "STANDARD_USER" using "TURKISH" language
    And Go to "Kayıtlı İşlemler" from Menu

  Scenario: CORPORATE - Hesap Numarasına Yeni Kayıtlı Transfer Oluşturulması
    When Click add new saved transaction button
    Then Verify add new saved transaction screen is visible
    When Enter "hesap numarası" as new saved transaction name
    And Select "Para Transferi" as transaction type
    And Select "Başka Hesaba (Havale / EFT / FAST)" as money transfer category
    And Select "Hesap No" as money transfer category
    And Select recipient bank as "HALK KATILIM BANKASI A.Ş."
    And Enter receiver account info
    And Click save button for saved transaction
    And Enter the OTP code
    Then Saved transfer should be created successfully

  Scenario: CORPORATE - Kendi Hesap Numarası Girildiğinde Uyarı Mesajının Gösterilmesi
    When Click add new saved transaction button
    Then Verify add new saved transaction screen is visible
    When Enter "hesap numarası" as new saved transaction name
    And Select "Para Transferi" as transaction type
    And Select "Başka Hesaba (Havale / EFT / FAST)" as money transfer category
    And Select "Hesap No" as money transfer category
    And Select recipient bank as "HALK KATILIM BANKASI A.Ş."
    And Enter own account info for receiver
    And Click save button for saved transaction
    Then Verify fund account warning error should be displayed
