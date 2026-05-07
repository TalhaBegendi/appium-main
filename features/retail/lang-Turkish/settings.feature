@devices=pixel7
Feature: Settings flows language Turkish

  Background:
    Given Login as "RETAIL" customer "OPTION_USER" using "TURKISH" language

  Scenario: Dil Degistirme
    Given Go to "Ayarlar > Dil Seçenekleri" from Menu
    And Select "English" as language option
    Then Language should be changed successfully

  Scenario: Bilgi Paylasim Secenekleri
    When Go to "Ayarlar > İzin Tercihleri > Bilgi Paylaşım Seçenekleri" from Menu
    And Switch options Information Sharing Options

  Scenario: Kisisel Veri Paylasim Izni
    When Go to "Ayarlar > İzin Tercihleri > Kişisel Veri Paylaşım İzni" from Menu
    And Switch options Permission to Share Personal Data

  Scenario: Güvenlik Ayarları > Kısıtlar > Finansman
    When Go to "Ayarlar > Güvenlik Ayarları > Kısıtlar > Finansman" from Menu
    Then Pages should be opened successfully

  Scenario: Güvenlik Ayarları > Kısıtlar > Para Transferi
    When Go to "Ayarlar > Güvenlik Ayarları > Kısıtlar > Para Transferi" from Menu
    Then Pages should be opened successfully

  Scenario: Güvenlik Ayarları > Kayıtlı Cihazlar
    When Go to "Ayarlar > Güvenlik Ayarları > Kayıtlı Cihazlar" from Menu
    Then Pages should be opened successfully

  Scenario: Şifre Ayarları > SIM Bloke Kaldır
    When Go to "Ayarlar > Şifre Ayarları > SIM Bloke Kaldır" from Menu
    Then Pages should be opened successfully

  Scenario: Bildirim Ayarları Degistirme
    When Go to "Ayarlar > Bildirim Ayarları" from Menu
    And Switch to Permission
    Then Success Switch to Permission

  Scenario: Kanal Aktiflik Yönetimi Degistirme
    When Go to "Ayarlar > Kanal Aktiflik Yönetimi" from Menu
    And Switch to Permission
    Then Pages should be opened successfully

  Scenario: Ayarlar > Profilim > Telefon No Güncelle
    When Go to "Ayarlar > Profilim" from Menu
    And Update button Phone Number
    Then Pages should be opened successfully

  Scenario: Ayarlar > Profilim > Email Güncelle
    When Go to "Ayarlar > Profilim" from Menu
    And Update button Email
    Then Profile should be updated successfully

  Scenario: Ayarlar > Profilim > Adres Güncelle
    When Go to "Ayarlar > Profilim" from Menu
    And Update button Address
    Then Profile should be updated successfully

  Scenario: Ayarlar > Profilim > Çalışma Bilgilerim
    When Go to "Ayarlar > Profilim" from Menu
    And Opens My Employment Details Page
    Then Pages should be opened successfully

  Scenario: Ayarlar > Profilim > Planlanan Aylık İşlem Bilgisi
    When Go to "Ayarlar > Profilim" from Menu
    And Opens Planned Monthly Transactions Page
    Then Pages should be opened successfully

  @devices=pixel7
  Scenario: Şifre Ayarları > Biyometrik Doğrulama
    When Go to "Ayarlar > Şifre Ayarları > Biyometrik Doğrulama" from Menu
    Then Pages should be opened successfully

  @devices=13mini
  Scenario: Şifre Ayarları > Touch ID ile Doğrula
    When Go to "Ayarlar > Şifre Ayarları > Face ID / Touch ID ile Doğrula" from Menu
    Then Pages should be opened successfully

  Scenario: Ödeme İsteği Tercihleri Degistirme
    When Go to "Ayarlar > Ödeme İsteği Tercihleri" from Menu
    And Switch to Permission
    Then Pages should be opened successfully

  Scenario: Ayarlar > Açık Bankacılık Ayarları > Açık Bankacılık Kanalı
    When Go to "Ayarlar > Açık Bankacılık Ayarları > Açık Bankacılık Kanalı" from Menu
    And Switch to Permission
    Then Pages should be opened successfully

  Scenario: Ayarlar > Açık Bankacılık Ayarları > Onayda Bekleyen Rızalarım
    When Go to "Ayarlar > Açık Bankacılık Ayarları > Onayda Bekleyen Rızalarım" from Menu
    Then Pages should be opened successfully

  Scenario: Ayarlar > Açık Bankacılık Ayarları > Açık Bankacılık İzinlerim
    When Go to "Ayarlar > Açık Bankacılık Ayarları > Açık Bankacılık İzinlerim" from Menu
    Then Open Banking Permission steps
    Then Pages should be opened successfully