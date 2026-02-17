@devices=15
Feature: Homepage flows language English  - IOS

  Background:
    Given Login as "CORPORATE" customer "STANDARD_USER_2" using "ENGLISH" language

  Scenario: Go to My Assets from Homepage
    When Go to "My Assets" from Homepage

  Scenario: Go to Random Account from My Accounts on Homepage
    When Go to Random Account from Homepage with assertion using "NAME"
