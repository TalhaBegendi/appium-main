@devices=15
Feature: Menu Language Turkish

  Background:
    Given Login as "CORPORATE" customer "STANDARD_USER_2" using "TURKISH" language
    And Go to "Kayıtlı İşlemler" from Menu

  Scenario: Hesaplarım Arasındaki Kayıtlı Transferin Silinmesi
    When Click delete button for saved transaction named "virman"
    Then Verify delete confirmation popup is displayed
    When Click confirm delete button on delete popup
    Then Verify saved transaction is deleted successfully