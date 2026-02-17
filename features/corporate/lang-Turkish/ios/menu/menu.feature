@devices=15
Feature: Menu Language Turkish - IOS - CORPORATE

  Background:
    Given Login as "CORPORATE" customer "STANDARD_USER_2" using "TURKISH" language

  Scenario: CORPORATE - Ayarlar > Şifre Ayarları > Face ID / Touch ID ile Doğrula
    When Go to "Ayarlar > Şifre Ayarları > Face ID / Touch ID ile Doğrula" from Menu with assertion "SETTINGS_MENU_ITEM" of type "EQUAL" using "NAME"


