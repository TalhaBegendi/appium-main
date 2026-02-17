@devices=pixel7
Feature: Saved Transactions Flows Language English

  Background:
    When Login as "CORPORATE" customer "STANDARD_USER_2" using "ENGLISH" language
    When Go to "Saved Transaction" from Menu

  Scenario: Kayıtlı İşlemlerden Rastgele Bir işlem Gerçekleştirme --Kaldı
    When Select one of the saved transactions from the list
    And Enter transaction amount as "5" TL and description
    When Click continue button on the Another Account page
    When Click confirm button on confirmation page
    And Enter the OTP code
    Then The transaction should be successfully sent for approval

  Scenario: Hesaplar Arası Yeni Kayıtlı Hesaplarım Arası Havale Oluşturma
    When Click add new saved transaction button
    Then Verify add new saved transaction screen is visible
    When Enter "fund" as new saved transaction name
    And Select "Money Transfer" as transaction type
    And Select "Between My Accounts" as money transfer category
    And Select one own account as receiver
    And Click save button for saved transaction
    And Click confirm button on confirmation page
    And Enter the OTP code
    Then Saved transfer should be created successfully

  Scenario: Kayıtlı Hesaplarım Arası İşlem Silme
    When Click delete button for saved transaction named "fund"
    Then Verify delete confirmation popup is displayed
    When Click confirm delete button on delete popup
    Then Verify saved transaction is deleted successfully

  Scenario: Başka Hesaba (Havale / EFT / FAST) Yeni Kayıtlı IBAN'a Havale Oluşturma
    When Click add new saved transaction button
    Then Verify add new saved transaction screen is visible
    When Enter "iban" as new saved transaction name
    And Select "Money Transfer" as transaction type
    And Select "To Another Account (Wire Transfer / EFT / FAST)" as money transfer category
    And Select "To IBAN" as money transfer category
    And Enter receiver IBAN
    And Click save button for saved transaction
    And Click confirm button on confirmation page
    And Enter the OTP code
    Then Saved transfer should be created successfully

  Scenario: Kayıtlı Başka Hesaba (Havale / EFT / FAST) IBAN'a Havale İşlem Silme
    When Click delete button for saved transaction named "iban"
    Then Verify delete confirmation popup is displayed
    When Click confirm delete button on delete popup
    Then Verify saved transaction is deleted successfully


  Scenario: Başka Hesaba (Havale / EFT / FAST) Yeni Kayıtlı Hesaba Havale Oluşturma
    When Click add new saved transaction button
    Then Verify add new saved transaction screen is visible
    When Enter "to account number" as new saved transaction name
    And Select "Money Transfer" as transaction type
    And Select "To Another Account (Wire Transfer / EFT / FAST)" as money transfer category
    And Select "To Account Number" as money transfer category
    And Select recipient bank as "HALK KATILIM BANKASI A.Ş."
    And Enter receiver account info
    And Click save button for saved transaction
    And Click confirm button on confirmation page
    And Enter the OTP code
    Then Saved transfer should be created successfully

  Scenario: Kayıtlı Başka Hesaba (Havale / EFT / FAST) Hesaba Havale İşlem Silme
    When Click delete button for saved transaction named "to account number"
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
    And Select "Money Transfer" as transaction type
    And Select "To Another Account (Wire Transfer / EFT / FAST)" as money transfer category
    And Select "To IBAN" as money transfer category
    And Enter own IBAN as receiver
    Then Verify fund iban warning error should be displayed

  Scenario: Kendi Hesap Numarası Girildiğinde Uyarı Mesajının Gösterilmesi
    When Click add new saved transaction button
    Then Verify add new saved transaction screen is visible
    When Enter "to account number" as new saved transaction name
    And Select "Money Transfer" as transaction type
    And Select "To Another Account (Wire Transfer / EFT / FAST)" as money transfer category
    And Select "To Account Number" as money transfer category
    And Select recipient bank as "HALK KATILIM BANKASI A.Ş."
    And Enter own account info for receiver
    And Click save button for saved transaction
    Then Verify fund account warning error should be displayed