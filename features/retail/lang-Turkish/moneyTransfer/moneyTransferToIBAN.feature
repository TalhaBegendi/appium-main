@devices=pixel7
Feature: Money Transfer flows language Turkish

  Background:
    Given Login as "RETAIL" customer "ACCOUNT_USER" using "TURKISH" language
    Given Go to "Para Transferi > Başka Hesaba (Havale / EFT / FAST) > IBAN" from Menu

  Scenario: Kayıtlı İşlem ile Aynı Gün IBAN Transferinin Yapılması
    When Click the make from saved transactions button
    And Selects one of the saved transactions from the list
    And Enter transaction amount as "2" TL and description
    Then The selected transaction details should be displayed correctly
    When Click continue button on the Another Account page
    Then The transaction details should be displayed correctly on the Verification page
    When Click confirm button on confirmation page
    And Enter the OTP code with give permission
    Then The transaction should be successfully sent for approval

  Scenario: Bireysel Müşteri için Aynı Gün IBAN Transferinin Yapılması
    When Enter "RETAIL" customer transaction details for today
    When Click continue button on the Another Account page
    Then The transaction details should be displayed correctly on the Verification page
    When Click confirm button on confirmation page
    And Enter the OTP code with give permission
    Then The transaction should be successfully sent for approval

  Scenario: Kurumsal Müşteri için Aynı Gün IBAN Transferinin Yapılması
    When Enter "CORPORATE" customer transaction details for today
    When Click continue button on the Another Account page
    Then The transaction details should be displayed correctly on the Verification page
    When Click confirm button on confirmation page
    And Enter the OTP code with give permission
    Then The transaction should be successfully sent for approval

  Scenario: Bireysel Müşteri için İleri Tarihli IBAN Transferinin Yapılması
    When Enter "RETAIL" customer transaction details for "9" days later
    When Click continue button on the Another Account page
    When Click confirm button on confirmation page
    And Enter the OTP code with give permission
    Then The transaction should be for forward date completed successfully

  Scenario: Bireysel Müşteride Farklı Para Birimi Transferinin Engellenmesi
    When Enter "RETAIL" customer transaction details with different currency for today
    When Click continue button on the Another Account page
    Then The different currency error message should be displayed

  Scenario: Kullanıcıya Ait IBAN Girildiğinde Uyarı Mesajı Gösterilmesi
    When Enter fund transaction details for today
    Then Verify fund warning error should be displayed



