@devices=pixel7
Feature: Settings flows language English

  Background:
    Given Login as "RETAIL" customer "OPTION_USER" using "ENGLISH" language

  Scenario: Language Settings Changed
    Given Go to "Settings > Language Settings" from Menu with assertion "SELECT_LANGUAGE_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"
    And Select "Turkish" as language option
    Then Language should be changed successfully

  Scenario: Information Sharing Options Settings
    When Go to "Settings > Permission Preferences > Information Sharing Options" from Menu with assertion "SELECT_LANGUAGE_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"
    And Switch options Information Sharing Options

  Scenario: Personal Data Permission Settings
    When Go to "Settings > Permission Preferences > Personal Data Permission" from Menu with assertion "SELECT_LANGUAGE_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"
    And Switch options Permission to Share Personal Data

  Scenario: Security Settings > Restrictions > Financing
    When Go to "Settings > Security Settings > Restrictions > Financing" from Menu with assertion "SELECT_LANGUAGE_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"
    Then Pages should be opened successfully

  Scenario: Security Settings > Restrictions > Credit Card
    When Go to "Settings > Security Settings > Restrictions > Credit Card" from Menu with assertion "SELECT_LANGUAGE_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"
    Then Pages should be opened successfully

  Scenario: Security Settings > Restrictions > Money Transfer
    When Go to "Settings > Security Settings > Restrictions > Money Transfer" from Menu with assertion "SELECT_LANGUAGE_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"
    Then Pages should be opened successfully

  Scenario: Security Settings > Registered Devices
    When Go to "Settings > Security Settings > Registered Devices" from Menu with assertion "SELECT_LANGUAGE_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"
    Then Pages should be opened successfully

  Scenario: Password Settings > Unblock SIM Card
    When Go to "Settings > Password Settings > Unblock SIM Card" from Menu with assertion "SELECT_LANGUAGE_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"
    Then Pages should be opened successfully

  Scenario: Password Settings > Biometric Verification
    When Go to "Settings > Password Settings > Biometric Verification" from Menu with assertion "SELECT_LANGUAGE_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"
    Then Pages should be opened successfully

  Scenario: Notification Settings Changed
    When Go to "Settings > Notification Settings" from Menu with assertion "SELECT_LANGUAGE_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"
    And Switch to Permission
    Then Success Switch to Permission

  Scenario: Channel Activity Management Changed
    When Go to "Settings > Channel Activity Management" from Menu with assertion "SELECT_LANGUAGE_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"
    And Switch to Permission
    Then Pages should be opened successfully

  Scenario: Payment Request Preferences Changed
    When Go to "Settings > Payment Request Preferences" from Menu with assertion "SELECT_LANGUAGE_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"
    And Switch to Permission
    Then Pages should be opened successfully

  Scenario: Settings > Open Banking Settings > Open Banking Channel
    When Go to "Settings > Open Banking Settings > Open Banking Channel" from Menu with assertion "SELECT_LANGUAGE_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"
    And Switch to Permission
    Then Pages should be opened successfully

  Scenario: Settings > Open Banking Settings > My Open Banking Permissions
    When Go to "Settings > Open Banking Settings > My Open Banking Permissions" from Menu with assertion "SELECT_LANGUAGE_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"
    Then Pages should be opened successfully

  Scenario: Settings > Open Banking Settings > My Consent Pending Approval
    When Go to "Settings > Open Banking Settings > My Consent Pending Approval" from Menu with assertion "SELECT_LANGUAGE_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"
    Then Open Banking Permission steps
    Then Pages should be opened successfully

  Scenario: Ayarlar > Profilim > Telephone No Update
    When Go to "Settings > My Profile" from Menu with assertion "SELECT_LANGUAGE_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"
    And Update button Phone Number
    Then Pages should be opened successfully

  Scenario: Settings > My Profile > Email Update
    When Go to "Settings > My Profile" from Menu with assertion "SELECT_LANGUAGE_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"
    And Update button Email
    Then Profile should be updated successfully

  Scenario: Settings > My Profile > Address Update
    When Go to "Settings > My Profile" from Menu with assertion "SELECT_LANGUAGE_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"
    And Update button Address
    Then Profile should be updated successfully

  Scenario: Settings > My Profile > My Employment Details
    When Go to "Settings > My Profile" from Menu with assertion "SELECT_LANGUAGE_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"
    And Opens My Employment Details Page
    Then Pages should be opened successfully

  Scenario: Settings > My Profile > Planned Monthly Transactions
    When Go to "Settings > My Profile" from Menu with assertion "SELECT_LANGUAGE_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"
    And Opens Planned Monthly Transactions Page
    Then Pages should be opened successfully
