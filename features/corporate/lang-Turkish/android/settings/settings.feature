@devices=pixel7
Feature: Settings Flows Language Turkish - ANDROID - CORPORATE

  Background:
    Given Login as "CORPORATE" customer "STANDARD_USER" using "TURKISH" language

  Scenario: Dil Degistirme
    Given Go to "Ayarlar > Dil Seçenekleri" from Menu with assertion "SELECT_LANGUAGE_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"
    And Select "English" as language option
    Then Language should be changed successfully

  Scenario: Bilgi Paylasim Secenekleri
    When Go to "Ayarlar > İzin Tercihleri > Bilgi Paylaşım Seçenekleri" from Menu with assertion "SELECT_LANGUAGE_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"
    And Switch options Information Sharing Options

  Scenario: Güvenlik Ayarları > Giriş Kayıtları
    When Go to "Ayarlar > Güvenlik Ayarları > Giriş Kayıtları" from Menu with assertion "SELECT_LANGUAGE_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"
    Then Pages should be opened successfully

  Scenario: Güvenlik Ayarları > Kısıtlar > Finansman
    When Go to "Ayarlar > Güvenlik Ayarları > Kısıtlar > Finansman" from Menu with assertion "SELECT_LANGUAGE_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"
    Then Pages should be opened successfully

  Scenario: Güvenlik Ayarları > Kısıtlar > Kredi Kartı
    When Go to "Ayarlar > Güvenlik Ayarları > Kısıtlar > Kredi Kartı" from Menu with assertion "SELECT_LANGUAGE_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"
    Then Pages should be opened successfully

  Scenario: Güvenlik Ayarları > Kısıtlar > Para Transferi
    When Go to "Ayarlar > Güvenlik Ayarları > Kısıtlar > Para Transferi" from Menu with assertion "SELECT_LANGUAGE_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"
    Then Pages should be opened successfully

  Scenario: Şifre Ayarları > SIM Bloke Kaldır
    When Go to "Ayarlar > Şifre Ayarları > SIM Bloke Kaldır" from Menu with assertion "SELECT_LANGUAGE_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"
    Then Pages should be opened successfully

  Scenario: Şifre Ayarları > Biyometrik Doğrulama
    When Go to "Ayarlar > Şifre Ayarları > Biyometrik Doğrulama" from Menu with assertion "SELECT_LANGUAGE_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"
    Then Pages should be opened successfully

  Scenario: Ayarlar > Profil bilgileri > Profilim
    When Go to "Ayarlar > Profil bilgileri > Profilim" from Menu with assertion "SELECT_LANGUAGE_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"
    Then Pages should be opened successfully

  Scenario: Ayarlar > Profil bilgileri > Kurumsal Profil
    When Go to "Ayarlar > Profil bilgileri > Kurumsal Profil" from Menu with assertion "SELECT_LANGUAGE_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"
    Then Pages should be opened successfully
  Scenario: Bildirim Ayarları Degistirme
    When Go to "Ayarlar > Bildirim Ayarları" from Menu with assertion "SELECT_LANGUAGE_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"
    And Switch to Permission
    Then Success Switch to Permission

  Scenario: Ayarlar > Açık Bankacılık Ayarları > Açık Bankacılık Kanalı
    When Go to "Ayarlar > Açık Bankacılık Ayarları > Açık Bankacılık Kanalı" from Menu with assertion "SELECT_LANGUAGE_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"
    And Switch to Permission
    Then Pages should be opened successfully

  Scenario: Ayarlar > Açık Bankacılık Ayarları > Açık Bankacılık İzinlerim
    When Go to "Ayarlar > Açık Bankacılık Ayarları > Açık Bankacılık İzinlerim" from Menu with assertion "SELECT_LANGUAGE_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"
    Then Open Banking Permission steps
    Then Pages should be opened successfully

  Scenario: Ödeme İsteği Tercihleri Degistirme
    When Go to "Ayarlar > Ödeme İsteği Tercihleri" from Menu with assertion "SELECT_LANGUAGE_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"
    And Switch to Permission
    Then Pages should be opened successfully