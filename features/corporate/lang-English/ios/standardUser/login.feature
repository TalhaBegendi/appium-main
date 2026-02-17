@devices=15
Feature: Login flows language English - IOS

  Scenario: Success Login Scenario With Corporate User
    When Login as "CORPORATE" customer "STANDARD_USER_2" using "ENGLISH" language

  Scenario: Success Logout Scenario With Corporate User
    When Logout as "CORPORATE" customer "STANDARD_USER_2" using "ENGLISH" language

