@devices=pixel7
Feature: Homepage flows language Turkish - ANDROID

  Background:
    Given Login as "CORPORATE" customer "STANDARD_USER" using "TURKISH" language

  Scenario: Varlıklarım Sayfasına Git - CORPORATE
    When Go to "Varlıklarım" from Homepage

  Scenario: Son 10 Hareket Sayfasına Git - CORPORATE
    When Go to "Son 10 Hareket" from Homepage

  Scenario: Anasayfadan Rastgele Hesaba Git - CORPORATE
    When Go to Random Account from Homepage with assertion using "NAME"

  Scenario: Son 10 Hareketlerden Rastgele İşleme Git - CORPORATE
    When Go to "Son 10 Hareket" from Homepage
    And Go to Random Last Transactions

  Scenario: Varlıklarımda Döviz ve Tutar Değişimini Doğrula - CORPORATE
    When Go to "Varlıklarım" from Homepage
    Then Verify currency and amount change on My Assets from Homepage