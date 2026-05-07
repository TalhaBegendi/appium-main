@devices=pixel7
Feature: Requester Account flows language English - ANDROID- CORPORATE

  Background:
    Given Login as "CORPORATE" customer "REQUESTER" using "TURKISH" language

  Scenario: CORPORATE - Girişçi Rolünde Cari Hesap Açma Yetki Hatası
    When Go to "Accounts > My Accounts" from Menu
    And Click open account button
    Then Verify corporate role authorization error message

  Scenario: CORPORATE - Girişçi Rolünde Katılım Hesabı Açma Yetki Hatası
    When Go to "Hesaplar > Hesaplarım" from Menu
    And Click "Katılma Hesaplarım" account type
    And Click open account button
    Then Verify corporate role authorization error message