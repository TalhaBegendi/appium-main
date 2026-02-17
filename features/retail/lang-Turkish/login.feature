@devices=pixel7
Feature: Login flows language Turkish

  Scenario: Login from App
    When Login as "RETAIL" customer "MENU_MONEY_TRANSFER_USER" using "TURKISH" language

  Scenario: Logout
    When Logout as "RETAIL" customer "MENU_MONEY_TRANSFER_USER" using "TURKISH" language