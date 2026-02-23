@devices=15
Feature: Homepage flows language English  - IOS

  Background:
    Given Login as "CORPORATE" customer "STANDARD_USER" using "ENGLISH" language

  Scenario: Go to My Assets from Homepage
    When Go to "My Assets" from Homepage

  Scenario: Go to Last 10 Activities from Homepage
    When Go to "Last 10 Activities" from Homepage

  Scenario: Go to Random Account from My Accounts on Homepage
    When Go to Random Account from Homepage with assertion using "NAME"

  Scenario: Go to Random Last Activities from Homepage
    When Go to "Last 10 Activities" from Homepage
    And Go to Random Last Transactions

  Scenario: Verify currency and amount change on My Assets from Homepage
    When Go to "My Assets" from Homepage
    Then Verify currency and amount change on My Assets from Homepage
