@devices=pixel7
Feature: Money Transfer flows language Turkish

  Background:
    Given Login as "CORPORATE" customer "STANDARD_USER_2" using "ENGLISH" language
    When Go to "Money Transfer > To Another Account (Wire Transfer / EFT / FAST) > Account" from Menu with assertion "MONEY_TRANSFER_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"
