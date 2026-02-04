@devices=16e
Feature: Login flows language English

  Scenario: Login from App
    When Login as "RETAIL" customer "MENU_USER" using "ENGLISH" language

  Scenario: Logout
    When Logout as "RETAIL" customer "LOGIN_USER" using "ENGLISH" language
