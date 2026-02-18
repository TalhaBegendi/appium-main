@devices=pixel7
Feature: Menu Language Turkish - ANDROID - CORPORATE

  Background:
    Given Login as "CORPORATE" customer "STANDARD_USER" using "TURKISH" language

  Scenario: CORPORATE - Ana Sayfaya Git
    When Go to "Ana Sayfa" from Menu

  Scenario: CORPORATE - Hesaplara Git
    When Go to "Hesaplar" from Menu

  Scenario: CORPORATE - Kartlara Git
    When Go to "Kartlar" from Menu

  Scenario: CORPORATE - Kayıtlı İşlemlere Git
    When Go to "Kayıtlı İşlemler" from Menu

  Scenario: CORPORATE - Para Transferine Git
    When Go to "Para Transferi" from Menu

  Scenario: CORPORATE - Karekod İşlemlerine Git
    When Go to "Karekod İşlemleri" from Menu

  Scenario: CORPORATE - Döviz / Kıymetli Maden İşlemlerine Git
    When Go to "Döviz / Kıymetli Maden" from Menu

  Scenario: CORPORATE - Finansmana Git
    When Go to "Finansman" from Menu

  Scenario: CORPORATE - Yatırıma Git
    When Go to "Yatırım" from Menu

  Scenario: CORPORATE - Çek - Senet İşlemlerine Git
    When Go to "Çek - Senet" from Menu

  Scenario: CORPORATE - Kurumsal'a Git
    When Go to "Kurumsal" from Menu

  Scenario: CORPORATE - POS'a Git
    When Go to "POS" from Menu

  Scenario: CORPORATE - Belgelere Git
    When Go to "Belgeler" from Menu

  Scenario: CORPORATE - Ayarlara Git
    When Go to "Ayarlar" from Menu

  Scenario: CORPORATE - Başvurular Sayfasına Git
    When Go to "Başvurular" from Menu

  Scenario: CORPORATE - Bildirimler ve Mesajlara Git
    When Go to "Bildirimler ve Mesajlar" from Menu

  Scenario: CORPORATE - E-Devlet'e Git
    When Go to "E-Devlet" from Menu

  Scenario: CORPORATE - Hakkında Sayfasına Git
    When Go to "Hakkında" from Menu

  Scenario: CORPORATE - Hesaplar > Hesaplarım
    When Go to "Hesaplar > Hesaplarım" from Menu with assertion "ACCOUNTS_TITLE_MENU" of type "EQUAL" using "NAME"

  Scenario: CORPORATE - Hesaplar > Hesap Aç
    When Go to "Hesaplar > Hesap Aç" from Menu with assertion "OPEN_ACCOUNTS_TITLE_MENU" of type "EQUAL" using "NAME"

  Scenario: CORPORATE - Hesaplar > Favori IBAN
    When Go to "Hesaplar > Favori IBAN" from Menu with assertion "ACCOUNTS_TITLE_MENU" of type "EQUAL" using "NAME"

  Scenario: CORPORATE - Hesaplar > Kolay Adreslerim
    When Go to "Hesaplar > Kolay Adreslerim" from Menu with assertion "EASY_ADDRESS_TITLE_MENU" of type "EQUAL" using "NAME"

  Scenario: CORPORATE - Kartlar > Kartlarım
    When Go to "Kartlar > Kartlarım" from Menu with assertion "MY_CARD_APPLICATION_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: CORPORATE - Kartlar > Kart Başvurusu
    When Go to "Kartlar > Kart Başvurusu" from Menu with assertion "MY_CARD_APPLICATION_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: CORPORATE - Kartlar > Kart Başvurularım
    When Go to "Kartlar > Kart Başvurularım" from Menu with assertion "MY_CARD_APPLICATION_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: CORPORATE - Kartlar > Harcama İtirazı
    When Go to "Kartlar > Harcama İtirazı" from Menu with assertion "DEBIT_PAYMENT_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: CORPORATE - Kartlar > Borç Öde > Kendi Kartıma
    When Go to "Kartlar > Borç Öde > Kendi Kartıma" from Menu with assertion "MONEY_CARDS_TITLE_MENU_ITEM" of type "EQUAL" using "CONTENT_DESC"

  Scenario: CORPORATE - Kartlar > Borç Öde > Başka Karta
    When Go to "Kartlar > Borç Öde > Başka Karta" from Menu with assertion "MONEY_CARDS_TITLE_MENU_ITEM" of type "EQUAL" using "CONTENT_DESC"

  Scenario: CORPORATE - Para Transferi > Son İşlemler
    When Go to "Para Transferi > Son İşlemler" from Menu with assertion "MONEY_TRANSFER_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: CORPORATE - Para Transferi > Hesaplarım Arası
    When Go to "Para Transferi > Hesaplarım Arası" from Menu with assertion "MONEY_TRANSFER_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: CORPORATE - Para Transferi > Kayıtlı Para Transferleri
    When Go to "Para Transferi > Kayıtlı Para Transferleri" from Menu with assertion "MONEY_TRANSFER_TITLE_MENU_DISPLAY" of type "EQUAL" using "NAME"

  Scenario: CORPORATE - Para Transferi > Başka Hesaba > IBAN
    When Go to "Para Transferi > Başka Hesaba (Havale / EFT / FAST) > IBAN" from Menu with assertion "MONEY_TRANSFER_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: CORPORATE - Para Transferi > Başka Hesaba > Hesap
    When Go to "Para Transferi > Başka Hesaba (Havale / EFT / FAST) > Hesap" from Menu with assertion "MONEY_TRANSFER_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: CORPORATE - Para Transferi > Başka Hesaba > Telefon No
    When Go to "Para Transferi > Başka Hesaba (Havale / EFT / FAST) > Telefon No" from Menu with assertion "MONEY_TRANSFER_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: CORPORATE - Para Transferi > Başka Hesaba > TR Karekod
    When Go to "Para Transferi > Başka Hesaba (Havale / EFT / FAST) > TR Karekod" from Menu with assertion "MONEY_TRANSFER_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: CORPORATE - Para Transferi > Karta > Kartıma
    When Go to "Para Transferi > Karta > Kartıma" from Menu with assertion "MONEY_CARDS_TITLE_MENU_ITEM" of type "EQUAL" using "CONTENT_DESC"

  Scenario: CORPORATE - Para Transferi > Karta > Başka Karta
    When Go to "Para Transferi > Karta > Başka Karta" from Menu with assertion "MONEY_CARDS_TITLE_MENU_ITEM" of type "EQUAL" using "CONTENT_DESC"

  Scenario: CORPORATE - Para Transferi > Döviz Transferlerim
    When Go to "Para Transferi > Döviz Transferi > Döviz Transferlerim" from Menu with assertion "MONEY_TRANSFER_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: CORPORATE - Para Transferi > Para Transferi Talimatlarım
    When Go to "Para Transferi > Para Transferi Talimatlarım" from Menu with assertion "MONEY_TRANSFER_TITLE_MENU_DISPLAY" of type "EQUAL" using "NAME"

  Scenario: CORPORATE - Karekod İşlemleri > ATM'den Para Çek
    When Go to "Karekod İşlemleri > ATM'den Para Çek" from Menu with assertion "WITHDRAW_MONEY_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: CORPORATE - Karekod İşlemleri > ATM'den Para Yatır
    When Go to "Karekod İşlemleri > ATM'den Para Yatır" from Menu with assertion "DEPOSIT_MONEY_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: CORPORATE - Karekod İşlemleri > Karekod İle Öde
    When Go to "Karekod İşlemleri > Karekod İle Öde" from Menu with assertion "TRANSFER_MONEY_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: CORPORATE - Karekod İşlemleri > Ödeme Al
    When Go to "Karekod İşlemleri > Ödeme Al" from Menu with assertion "TRANSFER_MONEY_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: CORPORATE - Döviz / Kıymetli Maden > Alış / Satış
    When Go to "Döviz / Kıymetli Maden > Döviz / Kıymetli Maden Alış / Satış" from Menu with assertion "CURRENCY_METALS_SELL_TEXT_ITEM" of type "PRESENCE_THEN_EQUAL" using "NAME"

  Scenario: CORPORATE - Döviz / Kıymetli Maden > Emirlerim
    When Go to "Döviz / Kıymetli Maden > Emirlerim" from Menu with assertion "MY_ORDERS_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: CORPORATE - Döviz / Kıymetli Maden > İşlemlerim
    When Go to "Döviz / Kıymetli Maden > İşlemlerim" from Menu with assertion "MY_TRANSACTIONS_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: CORPORATE - Döviz / Kıymetli Maden > Kur Referansı İşlemleri
    When Go to "Döviz / Kıymetli Maden > Kur Referansı İşlemleri" from Menu with assertion "CURRENCY_REFERENCE_TRANSACTIONS_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: CORPORATE - Döviz / Kıymetli Maden > Forward İşlemlerim
    When Go to "Döviz / Kıymetli Maden > Forward İşlemlerim" from Menu with assertion "FORWARD_TRANSACTIONS_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: CORPORATE - Finansman > Finansmanlarım
    When Go to "Finansman > Finansmanlarım" from Menu with assertion "MY_FINANCES_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: CORPORATE - Finansman > Finansman Taksit Öde
    When Go to "Finansman > Finansman Taksit Öde" from Menu with assertion "FINANCING_INSTALLMENT_PAYMENT_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: CORPORATE - Finansman > Teminat Mektubu > Teminat Mektubu Başvurusu
    When Go to "Finansman > Teminat Mektubu > Teminat Mektubu Başvurusu" from Menu with assertion "LETTER_OF_GUARANTEE_MENU_ITEM" of type "EQUAL" using "TEXT"

  Scenario: CORPORATE - Finansman > Teminat Mektubu > Teminat Mektubu Başvuru İzleme
    When Go to "Finansman > Teminat Mektubu > Teminat Mektubu Başvuru İzleme" from Menu with assertion "LETTER_OF_GUARANTEE_MENU_ITEM" of type "EQUAL" using "TEXT"

  Scenario: CORPORATE - Finansman > Finansman Limit ve Risk Bilgileri
    When Go to "Finansman > Finansman Limit ve Risk Bilgileri" from Menu with assertion "FINANCING_LIMIT_AND_RISK_INFORMATION" of type "EQUAL" using "NAME"

  Scenario: CORPORATE - Yatırım > Portföyüm
    When Go to "Yatırım > Portföyüm" from Menu with assertion "MY_PORTFOLIO_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: CORPORATE - Yatırım > Alış / Satış
    When Go to "Yatırım > Alış / Satış (Hisse / Fon / Kira S.)" from Menu with assertion "BUY_SELL_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: CORPORATE - Yatırım > İşlemlerim
    When Go to "Yatırım > İşlemlerim" from Menu with assertion "MY_TRANSACTIONS_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: CORPORATE - Yatırım > Emirlerim
    When Go to "Yatırım > Emirlerim" from Menu with assertion "MY_ORDERS_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: CORPORATE - Yatırım > Yatırım Hesaplarım
    When Go to "Yatırım > Yatırım Hesaplarım" from Menu with assertion "MY_INVESTMENT_ACCOUNTS_MENU_TITLE" of type "EQUAL" using "NAME"

  Scenario: CORPORATE - Yatırım > Uygunluk Testi
    When Go to "Yatırım > Uygunluk Testi" from Menu with assertion "SUITABILITY_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: CORPORATE - Çek - Senet > Çek İşlemleri > Çeklerim
    When Go to "Çek - Senet > Çek İşlemleri > Çeklerim" from Menu with assertion "MY_CHECK_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: CORPORATE - Çek - Senet > Çek İşlemleri > Çek Defterlerim
    When Go to "Çek - Senet > Çek İşlemleri > Çek Defterlerim" from Menu with assertion "MY_CHECK_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: CORPORATE - Çek - Senet > Çek İşlemleri > Çek Defter Başvurularım
    When Go to "Çek - Senet > Çek İşlemleri > Çek Defter Başvurularım" from Menu with assertion "MY_CHECK_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: CORPORATE - Kurumsal > İşlemler
    When Go to "Kurumsal > İşlemler" from Menu with assertion "CORPORATE_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: CORPORATE - Kurumsal > Kullanıcı Yönetimi
    When Go to "Kurumsal > Kullanıcı Yönetimi" from Menu with assertion "CORPORATE_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: CORPORATE - Kurumsal > Onay Grubu
    When Go to "Kurumsal > Onay Grubu" from Menu with assertion "CORPORATE_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: CORPORATE - POS > POS Çalışma Koşulları
    When Go to "POS > POS Çalışma Koşulları" from Menu with assertion "POS_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: CORPORATE - POS > POS Başvurularım
    When Go to "POS > POS Başvurularım" from Menu with assertion "POS_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: CORPORATE - POS > POS Hareketleri
    When Go to "POS > POS Hareketleri" from Menu with assertion "POS_TITLE_MENU_ITEM" of type "EQUAL" using "TEXT"

  Scenario: CORPORATE - Belgeler > Belgelerim
    When Go to "Belgeler > Belgelerim" from Menu with assertion "MY_DOCUMENTS_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: CORPORATE - Ayarlar > Dil Seçenekleri
    When Go to "Ayarlar > Dil Seçenekleri" from Menu with assertion "SETTINGS_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: CORPORATE - Ayarlar > İzin Tercihleri > Bilgi Paylaşım Seçenekleri
    When Go to "Ayarlar > İzin Tercihleri > Bilgi Paylaşım Seçenekleri" from Menu with assertion "SETTINGS_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: CORPORATE - Ayarlar > Güvenlik Ayarları > Giriş Kayıtları
    When Go to "Ayarlar > Güvenlik Ayarları > Giriş Kayıtları" from Menu with assertion "SETTINGS_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: CORPORATE - Ayarlar > Güvenlik Ayarları > Kısıtlar > Finansman
    When Go to "Ayarlar > Güvenlik Ayarları > Kısıtlar > Finansman" from Menu with assertion "SETTINGS_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: CORPORATE - Ayarlar > Güvenlik Ayarları > Kısıtlar > Kredi Kartı
    When Go to "Ayarlar > Güvenlik Ayarları > Kısıtlar > Kredi Kartı" from Menu with assertion "SETTINGS_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: CORPORATE - Ayarlar > Güvenlik Ayarları > Kısıtlar > Para Transferi
    When Go to "Ayarlar > Güvenlik Ayarları > Kısıtlar > Para Transferi" from Menu with assertion "SETTINGS_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: CORPORATE - Ayarlar > Şifre Ayarları > SIM Bloke Kaldır
    When Go to "Ayarlar > Şifre Ayarları > SIM Bloke Kaldır" from Menu with assertion "SETTINGS_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: CORPORATE - Ayarlar > Şifre Ayarları > Biyometrik Doğrulama
    When Go to "Ayarlar > Şifre Ayarları > Biyometrik Doğrulama" from Menu with assertion "SETTINGS_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: CORPORATE - Ayarlar > Profil Bilgileri > Kurumsal Profil
    When Go to "Ayarlar > Profil Bilgileri > Kurumsal Profil" from Menu with assertion "SETTINGS_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: CORPORATE - Ayarlar > Profil Bilgileri > Profilim
    When Go to "Ayarlar > Profil Bilgileri > Profilim" from Menu with assertion "SETTINGS_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: CORPORATE - Ayarlar > Bildirim Ayarları
    When Go to "Ayarlar > Bildirim Ayarları" from Menu with assertion "SETTINGS_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: CORPORATE - Ayarlar > Açık Bankacılık Ayarları > Açık Bankacılık Kanalı
    When Go to "Ayarlar > Açık Bankacılık Ayarları > Açık Bankacılık Kanalı" from Menu with assertion "SETTINGS_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: CORPORATE - Ayarlar > Açık Bankacılık Ayarları > Açık Bankacılık İzinlerim
    When Go to "Ayarlar > Açık Bankacılık Ayarları > Açık Bankacılık İzinlerim" from Menu with assertion "SETTINGS_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: CORPORATE - Ayarlar > Ödeme İsteği Tercihleri
    When Go to "Ayarlar > Ödeme İsteği Tercihleri" from Menu with assertion "SETTINGS_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: CORPORATE - Başvurular > Kart Başvurusu
    When Go to "Başvurular > Kart Başvurusu" from Menu with assertion "APPLICATIONS_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: CORPORATE - Başvurular > Teminat Mektubu > Teminat Mektubu Başvurusu
    When Go to "Başvurular > Teminat Mektubu > Teminat Mektubu Başvurusu" from Menu with assertion "APPLICATIONS_MENU_ITEM" of type "EQUAL" using "TEXT"

  Scenario: CORPORATE - Başvurular > Teminat Mektubu > Teminat Mektubu Başvuru İzleme
    When Go to "Başvurular > Teminat Mektubu > Teminat Mektubu Başvuru İzleme" from Menu with assertion "APPLICATIONS_MENU_ITEM" of type "EQUAL" using "TEXT"

  Scenario: CORPORATE - Başvurular > Referans Mektubu Başvurusu > Referans Mektubu Başvuru
    When Go to "Başvurular > Referans Mektubu Başvurusu > Referans Mektubu Başvuru" from Menu with assertion "APPLICATIONS_MENU_ITEM" of type "EQUAL" using "TEXT"

  Scenario: CORPORATE - Başvurular > Referans Mektubu > Referans Mektubu Başvuru İzleme
    When Go to "Başvurular > Referans Mektubu Başvurusu > Referans Mektubu Başvuru İzleme" from Menu with assertion "APPLICATIONS_MENU_ITEM" of type "EQUAL" using "TEXT"

  Scenario: CORPORATE - Başvurular > Kart Başvurularım
    When Go to "Başvurular > Kart Başvurularım" from Menu with assertion "APPLICATIONS_MENU_ITEM" of type "EQUAL" using "NAME"