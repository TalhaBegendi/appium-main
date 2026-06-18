@devices=13mini
Feature: Approver Account flows language Turkish - ANDROID- CORPORATE

  Background:
    Given Login as "RETAIL" customer "ACCOUNT_USER" using "TURKISH" language

  Scenario: Apply the Classic Card on Credit Cards
    When Go to "Kartlar > Kartlarım" from Menu
    And Click apply card button in "credit"
    When Apply the "Bireysel Klasik" card
    And Click confirm button on confirmation page for Cards
    Then Card should be applied successfully

  Scenario: Apply the Debit Card on Credit Cards
    When Go to "Kartlar > Kartlarım" from Menu
    And Click apply card button in "credit"
    When Apply the "Bireysel debit" card
    And Click confirm button on confirmation page for Cards
    Then Card should be applied successfully

  Scenario: Apply the Classic Card on Debit Cards
    When Go to "Kartlar > Kartlarım" from Menu
    And Click apply card button in "credit"
    When Apply the "Bireysel Klasik" card
    And Click confirm button on confirmation page for Cards
    Then Card should be applied successfully

  Scenario: Apply the Debit Card on Debit Cards
    When Go to "Kartlar > Kartlarım" from Menu
    And Click apply card button in "credit"
    When Apply the "Bireysel debit" card
    And Click confirm button on confirmation page for Cards
    Then Card should be applied successfully

  Scenario: Pay Debt for My Card
    When Go to "Kartlar > Borç Öde" from Menu
    When Pay the debt for "my" card
    And Click continue button for pay debt
    And Click confirm button on confirmation page for Cards
    And Enter the OTP code
    Then Card should be applied successfully

  Scenario: Pay Debt for Other Card
    When Go to "Kartlar > Borç Öde" from Menu
    When Pay the debt for "other" card
    And Click continue button for pay debt
    And Click confirm button on confirmation page for Cards
    And Enter the OTP code
    Then Card should be applied successfully

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


