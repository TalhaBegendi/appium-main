@devices=pixel7
Feature: Saved Transactions Flows Language Turkish

  Background:
    Given Login as "CORPORATE" customer "STANDARD_USER" using "TURKISH" language
    And Go to "Kayıtlı İşlemler" from Menu

  Scenario: Kayıtlı Bir İşlem Üzerinden Para Transferi Talimatı Oluşturulması
    When Select one of the saved transactions from the list
    And Enter transaction amount as "5" TL and description
    When Click continue button on the Another Account page
    When Click confirm button on confirmation page
    And Enter the OTP code
    Then The transaction should be successfully sent for approval

  Scenario: Hesaplarım Arasında Yeni Kayıtlı Transfer Oluşturulması
    When Click add new saved transaction button
    Then Verify add new saved transaction screen is visible
    When Enter "virman" as new saved transaction name
    And Select "Para Transferi" as transaction type
    And Select "Hesaplarım Arası" as money transfer category
    And Select one own account as receiver
    And Click save button for saved transaction
    And Click confirm button on confirmation page
    And Enter the OTP code
    Then Saved transfer should be created successfully

  Scenario: Hesaplarım Arasındaki Kayıtlı Transferin Silinmesi
    When Click delete button for saved transaction named "virman"
    Then Verify delete confirmation popup is displayed
    When Click confirm delete button on delete popup
    Then Verify saved transaction is deleted successfully

  Scenario: IBAN’a Yeni Kayıtlı Transfer Oluşturulması
    When Click add new saved transaction button
    Then Verify add new saved transaction screen is visible
    When Enter "iban" as new saved transaction name
    And Select "Para Transferi" as transaction type
    And Select "Başka Hesaba (Havale / EFT / FAST)" as money transfer category
    And Select "IBAN'a" as money transfer category
    And Enter receiver IBAN
    And Click save button for saved transaction
    And Click confirm button on confirmation page
    And Enter the OTP code
    Then Saved transfer should be created successfully

  Scenario: IBAN’a Kayıtlı Transferin Silinmesi
    When Click delete button for saved transaction named "iban"
    Then Verify delete confirmation popup is displayed
    When Click confirm delete button on delete popup
    Then Verify saved transaction is deleted successfully


  Scenario: Hesap Numarasına Yeni Kayıtlı Transfer Oluşturulması
    When Click add new saved transaction button
    Then Verify add new saved transaction screen is visible
    When Enter "hesap numarası" as new saved transaction name
    And Select "Para Transferi" as transaction type
    And Select "Başka Hesaba (Havale / EFT / FAST)" as money transfer category
    And Select "Hesap Numarasına" as money transfer category
    And Select recipient bank as "HALK KATILIM BANKASI A.Ş."
    And Enter receiver account info
    And Click save button for saved transaction
    And Click confirm button on confirmation page
    And Enter the OTP code
    Then Saved transfer should be created successfully

  Scenario: Hesap Numarasına Kayıtlı Transferin Silinmesi
    When Click delete button for saved transaction named "hesap numarası"
    Then Verify delete confirmation popup is displayed
    When Click confirm delete button on delete popup
    Then Verify saved transaction is deleted successfully

  Scenario: Kayıtlı İşlemler Aramasında Eşleşen Sonuçların Listelenmesi
    When Enter "dene" into saved transactions search field
    Then All saved transactions matching "dene" are displayed

  Scenario: Kayıtlı İşlemler Aramasında Sonuç Bulunamaması
    When Enter "aa" into saved transactions search field
    Then No search results found message is displayed

  Scenario: Kendi IBAN’ı Girildiğinde Uyarı Mesajının Gösterilmesi
    When Click add new saved transaction button
    Then Verify add new saved transaction screen is visible
    When Enter "iban" as new saved transaction name
    And Select "Para Transferi" as transaction type
    And Select "Başka Hesaba (Havale / EFT / FAST)" as money transfer category
    And Select "IBAN'a" as money transfer category
    And Enter own IBAN as receiver
    Then Verify fund iban warning error should be displayed

  Scenario: Kendi Hesap Numarası Girildiğinde Uyarı Mesajının Gösterilmesi
    When Click add new saved transaction button
    Then Verify add new saved transaction screen is visible
    When Enter "hesap numarası" as new saved transaction name
    And Select "Para Transferi" as transaction type
    And Select "Başka Hesaba (Havale / EFT / FAST)" as money transfer category
    And Select "Hesap Numarasına" as money transfer category
    And Select recipient bank as "HALK KATILIM BANKASI A.Ş."
    And Enter own account info for receiver
    And Click save button for saved transaction
    Then Verify fund account warning error should be displayed
