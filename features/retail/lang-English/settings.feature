@devices=pixel7
Feature: Settings flows language English

  Background:
    Given Login as "RETAIL" customer "OPTION_USER" using "ENGLISH" language

  Scenario: Language Settings Changed
    Given Go to "Settings > Language Settings" from Menu
    And Select "Turkish" as language option
    Then Language should be changed successfully

  Scenario: Information Sharing Options Settings
    When Go to "Settings > Permission Preferences > Information Sharing Options" from Menu
    And Switch options Information Sharing Options

  Scenario: Personal Data Permission Settings
    When Go to "Settings > Permission Preferences > Personal Data Permission" from Menu
    And Switch options Permission to Share Personal Data

  Scenario: Security Settings > Restrictions > Financing
    When Go to "Settings > Security Settings > Restrictions > Financing" from Menu
    Then Pages should be opened successfully

  Scenario: Security Settings > Restrictions > Money Transfer
    When Go to "Settings > Security Settings > Restrictions > Money Transfer" from Menu
    Then Pages should be opened successfully

  Scenario: Security Settings > Registered Devices
    When Go to "Settings > Security Settings > Registered Devices" from Menu
    Then Pages should be opened successfully

  Scenario: Password Settings > Unblock SIM Card
    When Go to "Settings > Password Settings > Unblock SIM Card" from Menu
    Then Pages should be opened successfully

  @devices=pixel7
  Scenario: Password Settings > Biometric Verification
    When Go to "Settings > Password Settings > Biometric Verification" from Menu
    Then Pages should be opened successfully

  @devices=13mini
  Scenario: Password Settings > Verify with Face ID / Touch ID
    When Go to "Settings > Password Settings > Verify with Face ID / Touch ID" from Menu
    Then Pages should be opened successfully

  Scenario: Notification Settings Changed
    When Go to "Settings > Notification Settings" from Menu
    And Switch to Permission
    Then Success Switch to Permission

  Scenario: Channel Activity Management Changed
    When Go to "Settings > Channel Activity Management" from Menu
    And Switch to Permission
    Then Pages should be opened successfully

  Scenario: Payment Request Preferences Changed
    When Go to "Settings > Payment Request Preferences" from Menu
    And Switch to Permission
    Then Pages should be opened successfully

  Scenario: Settings > Open Banking Settings > Open Banking Channel
    When Go to "Settings > Open Banking Settings > Open Banking Channel" from Menu
    And Switch to Permission
    Then Pages should be opened successfully

  Scenario: Settings > Open Banking Settings > My Open Banking Permissions
    When Go to "Settings > Open Banking Settings > My Open Banking Permissions" from Menu
    Then Pages should be opened successfully

  Scenario: Settings > Open Banking Settings > My Consent Pending Approval
    When Go to "Settings > Open Banking Settings > My Consent Pending Approval" from Menu
    Then Open Banking Permission steps
    Then Pages should be opened successfully

  Scenario: Ayarlar > Profilim > Telephone No Update
    When Go to "Settings > My Profile" from Menu
    And Update button Phone Number
    Then Pages should be opened successfully

  Scenario: Settings > My Profile > Email Update
    When Go to "Settings > My Profile" from Menu
    And Update button Email
    Then Profile should be updated successfully

  Scenario: Settings > My Profile > Address Update
    When Go to "Settings > My Profile" from Menu
    And Update button Address
    Then Profile should be updated successfully

  Scenario: Settings > My Profile > My Employment Details
    When Go to "Settings > My Profile" from Menu
    And Opens My Employment Details Page
    Then Pages should be opened successfully

  Scenario: Settings > My Profile > Planned Monthly Transactions
    When Go to "Settings > My Profile" from Menu
    And Opens Planned Monthly Transactions Page
    Then Pages should be opened successfully