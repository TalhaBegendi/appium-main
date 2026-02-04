@devices=16e
Feature: Homepage flows language Turkish

  Background:
    Given Login to App with Customer "HOMEPAGE_USER" using language "TURKISH"

  Scenario: Varlıklarım Sayfasına Git
    When Go to "Varlıklarım" from Homepage

  Scenario: Son 10 Hareket Sayfasına Git
    When Go to "Son 10 Hareket" from Homepage

  Scenario: Anasayfadan Rastgele Hesaba Git
    When Go to Random Account from Homepage with assertion using "NAME"

  Scenario: Son 10 Hareketlerden Rastgele İşleme Git
    When Go to "Son 10 Hareket" from Homepage
    And Go to Random Last Transactions

  Scenario: Varlıklarımda Döviz ve Tutar Değişimini Doğrula
    When Go to "Varlıklarım" from Homepage
    Then Verify currency and amount change on My Assets from Homepage