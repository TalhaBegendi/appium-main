@devices=pixel7
Feature: Login flows language Turkish - ANDROID

  Scenario: Success Login Scenario With Corporate User
    When Login as "CORPORATE" customer "STANDARD_USER" using "TURKISH" language

  Scenario: Success Logout Scenario With Corporate User
    When Logout as "CORPORATE" customer "STANDARD_USER" using "TURKISH" language

