@devices=pixel7
Feature: Settings flows language Turkish

  Background:
    Given Login as "RETAIL" customer "OPTION_USER" using "TURKISH" language

  Scenario: Dil Degistirme
    Given Go to "Ayarlar > Dil Seçenekleri" from Menu with assertion "SELECT_LANGUAGE_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"
    And Select "English" as language option
    Then Language should be changed successfully

  Scenario: Bilgi Paylasim Secenekleri
    When Go to "Ayarlar > İzin Tercihleri > Bilgi Paylaşım Seçenekleri" from Menu with assertion "SELECT_LANGUAGE_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"
    And Switch options Information Sharing Options

  Scenario: Kisisel Veri Paylasim Izni
    When Go to "Ayarlar > İzin Tercihleri > Kişisel Veri Paylaşım İzni" from Menu with assertion "SELECT_LANGUAGE_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"
    And Switch options Permission to Share Personal Data

  Scenario: Güvenlik Ayarları > Kısıtlar > Finansman
    When Go to "Ayarlar > Güvenlik Ayarları > Kısıtlar > Finansman" from Menu with assertion "SELECT_LANGUAGE_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"
    Then Pages should be opened successfully with "Finansman" title

  Scenario: Güvenlik Ayarları > Kısıtlar > Kredi Kartı
    When Go to "Ayarlar > Güvenlik Ayarları > Kısıtlar > Kredi Kartı" from Menu with assertion "SELECT_LANGUAGE_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"
    Then Pages should be opened successfully with "Kredi Kartı" title

  Scenario: Güvenlik Ayarları > Kısıtlar > Para Transferi
    When Go to "Ayarlar > Güvenlik Ayarları > Kısıtlar > Para Transferi" from Menu with assertion "SELECT_LANGUAGE_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"
    Then Pages should be opened successfully with "Para Transferi" title

  Scenario: Güvenlik Ayarları > Kayıtlı Cihazlar
    When Go to "Ayarlar > Güvenlik Ayarları > Kayıtlı Cihazlar" from Menu with assertion "SELECT_LANGUAGE_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"
    Then Pages should be opened successfully with "Kayıtlı Cihazlar" title

  Scenario: Şifre Ayarları > SIM Bloke Kaldır
    When Go to "Ayarlar > Şifre Ayarları > SIM Bloke Kaldır" from Menu with assertion "SELECT_LANGUAGE_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"
    Then Pages should be opened successfully with "SIM Bloke Kaldır" title

  Scenario: Şifre Ayarları > Biyometrik Doğrulama
    When Go to "Ayarlar > Şifre Ayarları > Biyometrik Doğrulama" from Menu with assertion "SELECT_LANGUAGE_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"
    Then Pages should be opened successfully with "Biyometrik Doğrulama" title

  Scenario: Bildirim Ayarları Degistirme
    When Go to "Ayarlar > Bildirim Ayarları" from Menu with assertion "SELECT_LANGUAGE_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"
    And Switch to Permission
    Then Success Switch to Permission

  Scenario: Kanal Aktiflik Yönetimi Degistirme
    When Go to "Ayarlar > Kanal Aktiflik Yönetimi" from Menu with assertion "SELECT_LANGUAGE_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"
    And Switch to Permission
    Then Pages should be opened successfully with "Kanal Aktiflik Yönetimi" title

  Scenario: Ödeme İsteği Tercihleri Degistirme
    When Go to "Ayarlar > Ödeme İsteği Tercihleri" from Menu with assertion "SELECT_LANGUAGE_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"
    And Switch to Permission
    Then Pages should be opened successfully with "Ödeme İsteği Tercihleri" title

  Scenario: Ayarlar > Açık Bankacılık Ayarları > Açık Bankacılık Kanalı
    When Go to "Ayarlar > Açık Bankacılık Ayarları > Açık Bankacılık Kanalı" from Menu with assertion "SELECT_LANGUAGE_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"
    And Switch to Permission
    Then Pages should be opened successfully with "Açık Bankacılık Kanalı" title

  Scenario: Ayarlar > Açık Bankacılık Ayarları > Onayda Bekleyen Rızalarım
    When Go to "Ayarlar > Açık Bankacılık Ayarları > Onayda Bekleyen Rızalarım" from Menu with assertion "SELECT_LANGUAGE_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"
    Then Pages should be opened successfully with "Onayda Bekleyen Rızalarım" title

  Scenario: Ayarlar > Açık Bankacılık Ayarları > Açık Bankacılık İzinlerim
    When Go to "Ayarlar > Açık Bankacılık Ayarları > Açık Bankacılık İzinlerim" from Menu with assertion "SELECT_LANGUAGE_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"
    Then Open Banking Permission steps
    Then Pages should be opened successfully with "Açık Bankacılık İzinlerim" title

  Scenario: Ayarlar > Profilim > Telefon No Güncelle
    When Go to "Ayarlar > Profilim" from Menu with assertion "SELECT_LANGUAGE_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"
    And Update button Phone Number
    Then Pages should be opened successfully with "Telefon No Güncelle" title

  Scenario: Ayarlar > Profilim > Email Güncelle
    When Go to "Ayarlar > Profilim" from Menu with assertion "SELECT_LANGUAGE_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"
    And Update button Email
    Then Profile should be updated successfully

  Scenario: Ayarlar > Profilim > Adres Güncelle
    When Go to "Ayarlar > Profilim" from Menu with assertion "SELECT_LANGUAGE_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"
    And Update button Address
    Then Profile should be updated successfully

  Scenario: Ayarlar > Profilim > Çalışma Bilgilerim
    When Go to "Ayarlar > Profilim" from Menu with assertion "SELECT_LANGUAGE_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"
    And Opens My Employment Details Page
    Then Pages should be opened successfully with "Çalışma Bilgilerim" title

  Scenario: Ayarlar > Profilim > Planlanan Aylık İşlem Bilgisi
    When Go to "Ayarlar > Profilim" from Menu with assertion "SELECT_LANGUAGE_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"
    And Opens Planned Monthly Transactions Page
    Then Pages should be opened successfully with "Planlanan Aylık İşlem Bilgisi" title