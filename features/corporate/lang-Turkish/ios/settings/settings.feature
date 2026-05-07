@devices=15
Feature: Settings Flows Language Turkish - IOS - CORPORATE

  Background:
    Given Login as "CORPORATE" customer "STANDARD_USER" using "TURKISH" language

  Scenario: Dil Degistirme
    Given Go to "Ayarlar > Dil Seçenekleri" from Menu
    And Select "English" as language option
    Then Language should be changed successfully

  Scenario: Bilgi Paylasim Secenekleri
    When Go to "Ayarlar > İzin Tercihleri > Bilgi Paylaşım Seçenekleri" from Menu
    And Switch options Information Sharing Options

  Scenario: Güvenlik Ayarları > Başarılı Girişler
    When Go to "Ayarlar > Güvenlik Ayarları > Başarılı Girişler" from Menu
    Then Pages should be opened successfully

  Scenario: Güvenlik Ayarları > Başarısız Giriş Denemeleri
    When Go to "Ayarlar > Güvenlik Ayarları > Başarısız Giriş Denemeleri" from Menu
    Then Pages should be opened successfully

  Scenario: Güvenlik Ayarları > Kısıtlar > Finansman
    When Go to "Ayarlar > Güvenlik Ayarları > Kısıtlar > Finansman" from Menu
    Then Pages should be opened successfully

  Scenario: Güvenlik Ayarları > Kısıtlar > Kredi Kartı
    When Go to "Ayarlar > Güvenlik Ayarları > Kısıtlar > Kredi Kartı" from Menu
    Then Pages should be opened successfully

  Scenario: Güvenlik Ayarları > Kısıtlar > Para Transferi
    When Go to "Ayarlar > Güvenlik Ayarları > Kısıtlar > Para Transferi" from Menu
    Then Pages should be opened successfully

  Scenario: Şifre Ayarları > SIM Bloke Kaldır
    When Go to "Ayarlar > Şifre Ayarları > SIM Bloke Kaldır" from Menu
    Then Pages should be opened successfully

  Scenario: Şifre Ayarları > Face ID / Touch ID ile Doğrula
    When Go to "Ayarlar > Şifre Ayarları > Face ID / Touch ID ile Doğrula" from Menu
    Then Pages should be opened successfully

  Scenario: Ayarlar > Profil bilgileri > Profilim
    When Go to "Ayarlar > Profil bilgileri > Profilim" from Menu
    Then Pages should be opened successfully

  Scenario: Ayarlar > Profil bilgileri > Kurumsal Profil
    When Go to "Ayarlar > Profil bilgileri > Kurumsal Profil" from Menu
    Then Pages should be opened successfully

  Scenario: Bildirim Ayarları Degistirme
    When Go to "Ayarlar > Bildirim Ayarları" from Menu
    And Switch to Permission
    Then Success Switch to Permission

  Scenario: Ayarlar > Açık Bankacılık Ayarları > Açık Bankacılık Kanalı
    When Go to "Ayarlar > Açık Bankacılık Ayarları > Açık Bankacılık Kanalı" from Menu
    And Switch to Permission
    Then Pages should be opened successfully

  Scenario: Ayarlar > Açık Bankacılık Ayarları > Açık Bankacılık İzinlerim
    When Go to "Ayarlar > Açık Bankacılık Ayarları > Açık Bankacılık İzinlerim" from Menu
    Then Open Banking Permission steps
    Then Pages should be opened successfully

  Scenario: Ödeme İsteği Tercihleri Degistirme
    When Go to "Ayarlar > Ödeme İsteği Tercihleri" from Menu
    And Switch to Permission
    Then Pages should be opened successfully
