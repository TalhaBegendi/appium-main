@devices=13mini
Feature: Approver Account flows language Turkish - ANDROID- CORPORATE

  Background:
    Given Login as "RETAIL" customer "MENU_MONEY_TRANSFER_USER" using "TURKISH" language

  Scenario: Apply the Classic Card
    When Go to "Kartlar > Kart Başvurusu" from Menu
    When Apply the "Bireysel Klasik" card
    And Click confirm button on confirmation page for Cards
    Then Card should be applied successfully

  Scenario: Apply the Debit Card
    When Go to "Kartlar > Kart Başvurusu" from Menu
    When Apply the "Bireysel debit" card
    And Click confirm button on confirmation page for Cards
    Then Card should be applied successfully


