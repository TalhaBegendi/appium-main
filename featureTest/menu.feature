@devices=pixel7
Feature: Menu navigation flows language Turkish

  Background:
    Given Login as "RETAIL" customer "MENU_MONEY_TRANSFER_USER" using "TURKISH" language

  Scenario: Para Transferi > Başka Hesaba > TR Karekod
    When Go to "Para Transferi > Başka Hesaba (Havale / EFT / FAST) > TR Karekod" from Menu with assertion "TRANSFER_MONEY_QR_MENU_ITEM" of type "EQUAL" using "TEXT"