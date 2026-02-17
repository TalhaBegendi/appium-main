@device=16e
Feature: Menu navigation flows language Turkish

  Background:
    Given Login as "RETAIL" customer "MENU_USER" using "TURKISH" language

  Scenario: Ana Sayfaya Git
    When Go to "Ana Sayfa" from Menu

  Scenario: Hesaplara Git
    When Go to "Hesaplar" from Menu

  Scenario: Para Transferine Git
    When Go to "Para Transferi" from Menu

  Scenario: Kayıtlı İşlemlere Git
    When Go to "Kayıtlı İşlemler" from Menu

  Scenario: Karekod İşlemlerine Git
    When Go to "Karekod İşlemleri" from Menu

  Scenario: Döviz / Kıymetli Maden İşlemlerine Git
    When Go to "Döviz / Kıymetli Maden" from Menu

  Scenario: Kartlara Git
    When Go to "Kartlar" from Menu

  Scenario: Finansmana Git
    When Go to "Finansman" from Menu

  Scenario: Yatırıma Git
    When Go to "Yatırım" from Menu

  Scenario: Çek - Senet İşlemlerine Git
    When Go to "Çek - Senet" from Menu

  Scenario: Belgelere Git
    When Go to "Belgeler" from Menu

  Scenario: Ayarlara Git
    When Go to "Ayarlar" from Menu

  Scenario: Bildirimler ve Mesajlara Git
    When Go to "Bildirimler ve Mesajlar" from Menu

  Scenario: Hakkında Sayfasına Git
    When Go to "Hakkında" from Menu

  Scenario: Başvurular Sayfasına Git
    When Go to "Başvurular" from Menu

  Scenario: Başvurular > Kart Başvurularım
    When Go to "Başvurular > Kart Başvurularım" from Menu with assertion "ACCOUNTS_TITLE_MENU" of type "EQUAL" using "NAME"

  Scenario: Hesaplar > Hesaplarım
    When Go to "Hesaplar > Hesaplarım" from Menu with assertion "ACCOUNTS_TITLE_MENU" of type "EQUAL" using "NAME"

  Scenario: Hesaplar > Hesap Aç
    When Go to "Hesaplar > Hesap Aç" from Menu with assertion "OPEN_ACCOUNTS_TITLE_MENU" of type "EQUAL" using "NAME"

  Scenario: Hesaplar > Favori IBAN
    When Go to "Hesaplar > Favori IBAN" from Menu with assertion "ACCOUNTS_TITLE_MENU" of type "EQUAL" using "NAME"

  Scenario: Hesaplar > Kolay Adreslerim
    When Go to "Hesaplar > Kolay Adreslerim" from Menu with assertion "EASY_ADDRESS_TITLE_MENU" of type "EQUAL" using "NAME"

  Scenario: Karekod İşlemleri > ATM'den Para Çek
    When Go to "Karekod İşlemleri > ATM'den Para Çek" from Menu with assertion "WITHDRAW_MONEY_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: Karekod İşlemleri > ATM'den Para Yatır
    When Go to "Karekod İşlemleri > ATM'den Para Yatır" from Menu with assertion "DEPOSIT_MONEY_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: Karekod İşlemleri > Karekod İle Öde
    When Go to "Karekod İşlemleri > Karekod İle Öde" from Menu with assertion "TRANSFER_MONEY_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: Karekod İşlemleri > Karekod İle Para Gönder
    When Go to "Karekod İşlemleri > Karekod İle Para Gönder" from Menu with assertion "TRANSFER_MONEY_QR_MENU_ITEM" of type "EQUAL" using "LABEL"


  Scenario: Döviz / Kıymetli Maden > Alış / Satış
    When Go to "Döviz / Kıymetli Maden > Döviz / Kıymetli Maden Alış / Satış" from Menu with assertion "CURRENCY_METALS_SELL_TEXT_ITEM" of type "PRESENCE_THEN_EQUAL" using "NAME"

  Scenario: Döviz / Kıymetli Maden > Emirlerim
    When Go to "Döviz / Kıymetli Maden > Emirlerim" from Menu with assertion "MY_ORDERS_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: Döviz / Kıymetli Maden > İşlemlerim
    When Go to "Döviz / Kıymetli Maden > İşlemlerim" from Menu with assertion "MY_TRANSACTIONS_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: Döviz / Kıymetli Maden > Kur Referansı İşlemleri
    When Go to "Döviz / Kıymetli Maden > Kur Referansı İşlemleri" from Menu with assertion "CURRENCY_REFERENCE_TRANSACTIONS_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: Döviz / Kıymetli Maden > İşlem Limitlerini Güncelle
    When Go to "Döviz / Kıymetli Maden > İşlem Limitlerini Güncelle" from Menu with assertion "UPDATE_TRANSACTIONS_LIMIT_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: Kartlar > Kartlarım
    When Go to "Kartlar > Kartlarım" from Menu with assertion "MY_CARD_APPLICATION_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: Kartlar > Kart Başvurusu
    When Go to "Kartlar > Kart Başvurusu" from Menu with assertion "MY_CARD_APPLICATION_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: Kartlar > Kart Başvurularım
    When Go to "Kartlar > Kart Başvurularım" from Menu with assertion "MY_CARD_APPLICATION_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: Kartlar > Harcama İtirazı
    When Go to "Kartlar > Harcama İtirazı" from Menu with assertion "DEBIT_PAYMENT_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: Kartlar > Borç Öde > Kendi Kartıma
    When Go to "Kartlar > Borç Öde > Kendi Kartıma" from Menu with assertion "MONEY_CARDS_TITLE_MENU_ITEM" of type "EQUAL" using "CONTENT_DESC"

  Scenario: Kartlar > Borç Öde > Başka Karta
    When Go to "Kartlar > Borç Öde > Başka Karta" from Menu with assertion "MONEY_CARDS_TITLE_MENU_ITEM" of type "EQUAL" using "CONTENT_DESC"

  Scenario: Finansman > Finansman Başvurusu
    When Go to "Finansman > Finansman Başvurusu" from Menu with assertion "LOAN_APPLICATION_MENU_ITEM" of type "PRESENCE" using ""

  Scenario: Finansman > Başvurularım
    When Go to "Finansman > Başvurularım" from Menu with assertion "MY_LOAN_APPLICATIONS_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: Finansman > Finansmanlarım
    When Go to "Finansman > Finansmanlarım" from Menu with assertion "MY_FINANCES_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: Finansman > Finansman Taksit Öde
    When Go to "Finansman > Finansman Taksit Öde" from Menu with assertion "FINANCING_INSTALLMENT_PAYMENT_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: Finansman > Araç Finansmanı Başvurusu
    When Go to "Finansman > Finansman Başvurusu > Araç Finansmanı Başvurusu" from Menu with assertion "VEHICLE_LOAN_APPLICATION_MENU_TITLE" of type "EQUAL" using "NAME"

  Scenario: Finansman > Motosiklet Finansmanı Başvurusu
    When Go to "Finansman > Finansman Başvurusu > Motosiklet Finansmanı Başvurusu" from Menu with assertion "MOTORCYCLE_LOAN_APPLICATION_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: Finansman > Hesaplamalar
    When Go to "Finansman > Finansman Başvurusu > Hesaplamalar" from Menu with assertion "FINANCING_CALCULATION_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: Yatırım > Portföyüm
    When Go to "Yatırım > Portföyüm" from Menu with assertion "MY_PORTFOLIO_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: Yatırım > Alış / Satış
    When Go to "Yatırım > Alış / Satış (Hisse / Fon / Kira S.)" from Menu with assertion "BUY_SELL_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: Yatırım > İşlemlerim
    When Go to "Yatırım > İşlemlerim" from Menu with assertion "MY_TRANSACTIONS_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: Yatırım > Emirlerim
    When Go to "Yatırım > Emirlerim" from Menu with assertion "MY_ORDERS_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: Yatırım > Yatırım Hesaplarım
    When Go to "Yatırım > Yatırım Hesaplarım" from Menu with assertion "MY_INVESTMENT_ACCOUNTS_MENU_TITLE" of type "EQUAL" using "NAME"

  Scenario: Yatırım > Uygunluk Testi
    When Go to "Yatırım > Uygunluk Testi" from Menu with assertion "SUITABILITY_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: Çek - Senet > Çek İşlemleri > Çeklerim
    When Go to "Çek - Senet > Çek İşlemleri > Çeklerim" from Menu with assertion "MY_CHECK_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: Çek - Senet > Çek İşlemleri > Çek Defterlerim
    When Go to "Çek - Senet > Çek İşlemleri > Çek Defterlerim" from Menu with assertion "MY_CHECK_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: Çek - Senet > Çek İşlemleri > Çek Defter Başvurularım
    When Go to "Çek - Senet > Çek İşlemleri > Çek Defter Başvurularım" from Menu with assertion "MY_CHECK_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: Belgeler > Belgelerim
    When Go to "Belgeler > Belgelerim" from Menu with assertion "MY_DOCUMENTS_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"


  Scenario: Para Transferi > Son İşlemler
    When Go to "Para Transferi > Son İşlemler" from Menu with assertion "MONEY_TRANSFER_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: Para Transferi > Hesaplarım Arası
    When Go to "Para Transferi > Hesaplarım Arası" from Menu with assertion "MONEY_TRANSFER_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: Para Transferi > Kayıtlı Para Transferleri
    When Go to "Para Transferi > Kayıtlı Para Transferleri" from Menu with assertion "MONEY_TRANSFER_TITLE_MENU_DISPLAY" of type "EQUAL" using "NAME"

  Scenario: Para Transferi > Başka Hesaba > IBAN
    When Go to "Para Transferi > Başka Hesaba (Havale / EFT / FAST) > IBAN" from Menu with assertion "MONEY_TRANSFER_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: Para Transferi > Başka Hesaba > Hesap
    When Go to "Para Transferi > Başka Hesaba (Havale / EFT / FAST) > Hesap" from Menu with assertion "MONEY_TRANSFER_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: Para Transferi > Başka Hesaba > Telefon No
    When Go to "Para Transferi > Başka Hesaba (Havale / EFT / FAST) > Telefon No" from Menu with assertion "MONEY_TRANSFER_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: Para Transferi > Başka Hesaba > Diğer
    When Go to "Para Transferi > Başka Hesaba (Havale / EFT / FAST) > Diğer" from Menu with assertion "MONEY_TRANSFER_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: Para Transferi > Döviz Transferlerim
    When Go to "Para Transferi > Döviz Transferi > Döviz Transferlerim" from Menu with assertion "MONEY_TRANSFER_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: Para Transferi > Para Transferi Talimatlarım
    When Go to "Para Transferi > Para Transferi Talimatlarım" from Menu with assertion "MONEY_TRANSFER_TITLE_MENU_DISPLAY" of type "EQUAL" using "NAME"

  Scenario: Para Transferi > Transfer Limitleri
    When Go to "Para Transferi > Transfer Limitleri" from Menu with assertion "MONEY_TRANSFER_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: Para Transferi > Karta > Kartıma
    When Go to "Para Transferi > Karta > Kartıma" from Menu with assertion "MONEY_CARDS_TITLE_MENU_ITEM" of type "EQUAL" using "CONTENT_DESC"

  Scenario: Para Transferi > Karta > Başka Karta
    When Go to "Para Transferi > Karta > Başka Karta" from Menu with assertion "MONEY_CARDS_TITLE_MENU_ITEM" of type "EQUAL" using "CONTENT_DESC"

  Scenario: Para Transferi > Cebe Gönder > Para Gönder
    When Go to "Para Transferi > Cebe Gönder ATM'den Çek > Para Gönder" from Menu with assertion "MONEY_TRANSFER_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: Para Transferi > Cebe Gönder > İşlemlerim
    When Go to "Para Transferi > Cebe Gönder ATM'den Çek > İşlemlerim" from Menu with assertion "MONEY_TRANSFER_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: Para Transferi > Güvenli Ödeme Kaydı
    When Go to "Para Transferi > Güvenli Ödeme İşlemleri > Güvenli Ödeme Kaydı" from Menu with assertion "MONEY_CARDS_SECURE_TITLE_MENU_DISPLAY" of type "EQUAL" using "NAME"

  Scenario: Para Transferi > Güvenli Ödeme İşlemi
    When Go to "Para Transferi > Güvenli Ödeme İşlemleri > Güvenli Ödeme İşlemi" from Menu with assertion "MONEY_CARDS_SECURE_TITLE_MENU_DISPLAY" of type "EQUAL" using "NAME"

  Scenario: Para Transferi > Başka Hesaba > TR Karekod
    When Go to "Para Transferi > Başka Hesaba (Havale / EFT / FAST) > TR Karekod" from Menu with assertion "TRANSFER_MONEY_QR_MENU_ITEM" of type "EQUAL" using "LABEL"