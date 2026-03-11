@devices=15
Feature: Approver Account flows language Turkish - IOS- CORPORATE

  Background:
    Given Login as "CORPORATE" customer "NEW_ACCOUNT" using "TURKISH" language

  Scenario: Hesaplarım > Cari Hesap Aç - Türk Lirası
    When Go to "Hesaplar > Hesaplarım" from Menu with assertion "ACCOUNTS_TITLE_MENU" of type "EQUAL" using "NAME"
    And Click open account button
    And Opens a "Cari Hesap" account with "Türk Lirası" currency

  Scenario: Hesaplarım > Cari Hesap Aç - Amerikan Doları
    When Go to "Hesaplar > Hesaplarım" from Menu with assertion "ACCOUNTS_TITLE_MENU" of type "EQUAL" using "NAME"
    And Click open account button
    And Opens a "Cari Hesap" account with "Amerikan Doları" currency

  Scenario: Hesaplarım > Cari Hesap Aç - Euro
    When Go to "Hesaplar > Hesaplarım" from Menu with assertion "ACCOUNTS_TITLE_MENU" of type "EQUAL" using "NAME"
    And Click open account button
    And Opens a "Cari Hesap" account with "Euro" currency

  Scenario: Hesaplarım > Cari Hesap Aç - Yakut
    When Go to "Hesaplar > Hesaplarım" from Menu with assertion "ACCOUNTS_TITLE_MENU" of type "EQUAL" using "NAME"
    And Click open account button
    And Opens a "Cari Hesap" account with "Yakut" currency

  Scenario: Hesaplarım > Dijital Katılma Hesabı - Türk Lirası - 3 Aylık
    When Go to "Hesaplar > Hesaplarım" from Menu with assertion "ACCOUNTS_TITLE_MENU" of type "EQUAL" using "NAME"
    And Click open account button
    When Opens a "Dijital Katılma Hesabı" account with "Türk Lirası" currency and "3 Aylık" maturity date

  Scenario: Hesaplarım > Dijital Katılma Hesabı - Türk Lirası - Günlük 32-999 Gün
    When Go to "Hesaplar > Hesaplarım" from Menu with assertion "ACCOUNTS_TITLE_MENU" of type "EQUAL" using "NAME"
    And Click open account button
    When Opens a "Dijital Katılma Hesabı" account with "Türk Lirası" currency and "Günlük (32-999 Gün)" maturity date

  Scenario: Hesaplarım > Dijital Katılma Hesabı - Türk Lirası - Günlük Kazançlı 2-29 Gün
    When Go to "Hesaplar > Hesaplarım" from Menu with assertion "ACCOUNTS_TITLE_MENU" of type "EQUAL" using "NAME"
    And Click open account button
    When Opens a "Dijital Katılma Hesabı" account with "Türk Lirası" currency and "Günlük Kazançlı (2-29 Gün)" maturity date

  Scenario: Hesaplarım > Dijital Katılma Hesabı - Amerikan Doları - 3 Aylık
    When Go to "Hesaplar > Hesaplarım" from Menu with assertion "ACCOUNTS_TITLE_MENU" of type "EQUAL" using "NAME"
    And Click open account button
    When Opens a "Dijital Katılma Hesabı" account with "Amerikan Doları" currency and "3 Aylık" maturity date

  Scenario: Hesaplarım > Dijital Katılma Hesabı - Amerikan Doları - Günlük 32-999 Gün
    When Go to "Hesaplar > Hesaplarım" from Menu with assertion "ACCOUNTS_TITLE_MENU" of type "EQUAL" using "NAME"
    And Click open account button
    When Opens a "Dijital Katılma Hesabı" account with "Amerikan Doları" currency and "Günlük (32-999 Gün)" maturity date

  Scenario: Hesaplarım > Dijital Katılma Hesabı - Amerikan Doları - Günlük Kazançlı 2-29 Gün
    When Go to "Hesaplar > Hesaplarım" from Menu with assertion "ACCOUNTS_TITLE_MENU" of type "EQUAL" using "NAME"
    And Click open account button
    When Opens a "Dijital Katılma Hesabı" account with "Amerikan Doları" currency and "Günlük Kazançlı (2-29 Gün)" maturity date

  Scenario: Hesaplarım > Dijital Katılma Hesabı - Euro - 3 Aylık
    When Go to "Hesaplar > Hesaplarım" from Menu with assertion "ACCOUNTS_TITLE_MENU" of type "EQUAL" using "NAME"
    And Click open account button
    When Opens a "Dijital Katılma Hesabı" account with "Euro" currency and "3 Aylık" maturity date

  Scenario: Hesaplarım > Dijital Katılma Hesabı - Euro - Günlük 32-999 Gün
    When Go to "Hesaplar > Hesaplarım" from Menu with assertion "ACCOUNTS_TITLE_MENU" of type "EQUAL" using "NAME"
    And Click open account button
    When Opens a "Dijital Katılma Hesabı" account with "Euro" currency and "Günlük (32-999 Gün)" maturity date

  Scenario: Hesaplarım > Dijital Katılma Hesabı - Euro - Günlük Kazançlı 2-29 Gün
    When Go to "Hesaplar > Hesaplarım" from Menu with assertion "ACCOUNTS_TITLE_MENU" of type "EQUAL" using "NAME"
    And Click open account button
    When Opens a "Dijital Katılma Hesabı" account with "Euro" currency and "Günlük Kazançlı (2-29 Gün)" maturity date

  Scenario: Hesaplarım > Dijital Katılma Hesabı - Yakut - 3 Aylık
    When Go to "Hesaplar > Hesaplarım" from Menu with assertion "ACCOUNTS_TITLE_MENU" of type "EQUAL" using "NAME"
    And Click open account button
    When Opens a "Dijital Katılma Hesabı" account with "Yakut" currency and "3 Aylık" maturity date

  Scenario: Hesaplarım > Dijital Katılma Hesabı - Yakut - Kırık Vadeli
    When Go to "Hesaplar > Hesaplarım" from Menu with assertion "ACCOUNTS_TITLE_MENU" of type "EQUAL" using "NAME"
    And Click open account button
    When Opens a "Dijital Katılma Hesabı" account with "Yakut" currency and "Kırık Vadeli" maturity date

  Scenario: Hesaplarım > Hesap Aç - Cari Hesap - Türk Lirası
    When Go to "Hesaplar > Hesap Aç" from Menu with assertion "ACCOUNTS_TITLE_MENU" of type "EQUAL" using "NAME"
    When Opens a "Cari Hesap" account with "Türk Lirası" currency

  Scenario: Hesaplarım > Hesap Aç - Cari Hesap - Amerikan Doları
    When Go to "Hesaplar > Hesap Aç" from Menu with assertion "ACCOUNTS_TITLE_MENU" of type "EQUAL" using "NAME"
    When Opens a "Cari Hesap" account with "Amerikan Doları" currency

  Scenario: Hesaplarım > Hesap Aç - Cari Hesap  - Euro
    When Go to "Hesaplar > Hesap Aç" from Menu with assertion "ACCOUNTS_TITLE_MENU" of type "EQUAL" using "NAME"
    When Opens a "Cari Hesap" account with "Euro" currency

  Scenario: Hesaplarım > Hesap Aç - Cari Hesap  - Yakut
    When Go to "Hesaplar > Hesap Aç" from Menu with assertion "ACCOUNTS_TITLE_MENU" of type "EQUAL" using "NAME"
    When Opens a "Cari Hesap" account with "Yakut" currency

  Scenario: Hesaplarım > Hesap Aç > Dijital Katılma Hesabı - Türk Lirası - 3 Aylık
    When Go to "Hesaplar > Hesap Aç" from Menu with assertion "ACCOUNTS_TITLE_MENU" of type "EQUAL" using "NAME"
    When Opens a "Dijital Katılma Hesabı" account with "Türk Lirası" currency and "3 Aylık" maturity date

  Scenario: Hesaplarım > Hesap Aç > Dijital Katılma Hesabı - Türk Lirası - Günlük 32-999 Gün
    When Go to "Hesaplar > Hesap Aç" from Menu with assertion "ACCOUNTS_TITLE_MENU" of type "EQUAL" using "NAME"
    When Opens a "Dijital Katılma Hesabı" account with "Türk Lirası" currency and "Günlük (32-999 Gün)" maturity date

  Scenario: Hesaplarım > Hesap Aç > Dijital Katılma Hesabı - Türk Lirası - Günlük Kazançlı 2-29 Gün
    When Go to "Hesaplar > Hesap Aç" from Menu with assertion "ACCOUNTS_TITLE_MENU" of type "EQUAL" using "NAME"
    When Opens a "Dijital Katılma Hesabı" account with "Türk Lirası" currency and "Günlük Kazançlı (2-29 Gün)" maturity date

  Scenario: Hesaplarım > Hesap Aç > Dijital Katılma Hesabı - Amerikan Doları - 3 Aylık
    When Go to "Hesaplar > Hesap Aç" from Menu with assertion "ACCOUNTS_TITLE_MENU" of type "EQUAL" using "NAME"
    When Opens a "Dijital Katılma Hesabı" account with "Amerikan Doları" currency and "3 Aylık" maturity date

  Scenario: Hesaplarım > Hesap Aç > Dijital Katılma Hesabı - Amerikan Doları - Günlük 32-999 Gün
    When Go to "Hesaplar > Hesap Aç" from Menu with assertion "ACCOUNTS_TITLE_MENU" of type "EQUAL" using "NAME"
    When Opens a "Dijital Katılma Hesabı" account with "Amerikan Doları" currency and "Günlük (32-999 Gün)" maturity date

  Scenario: Hesaplarım > Hesap Aç > Dijital Katılma Hesabı - Amerikan Doları - Günlük Kazançlı 2-29 Gün
    When Go to "Hesaplar > Hesap Aç" from Menu with assertion "ACCOUNTS_TITLE_MENU" of type "EQUAL" using "NAME"
    When Opens a "Dijital Katılma Hesabı" account with "Amerikan Doları" currency and "Günlük Kazançlı (2-29 Gün)" maturity date

  Scenario: HHesaplarım > Hesap Aç > Dijital Katılma Hesabı - Euro - 3 Aylık
    When Go to "Hesaplar > Hesap Aç" from Menu with assertion "ACCOUNTS_TITLE_MENU" of type "EQUAL" using "NAME"
    When Opens a "Dijital Katılma Hesabı" account with "Euro" currency and "3 Aylık" maturity date

  Scenario: Hesaplarım > Hesap Aç > Dijital Katılma Hesabı - Euro - Günlük 32-999 Gün
    When Go to "Hesaplar > Hesap Aç" from Menu with assertion "ACCOUNTS_TITLE_MENU" of type "EQUAL" using "NAME"
    When Opens a "Dijital Katılma Hesabı" account with "Euro" currency and "Günlük (32-999 Gün)" maturity date

  Scenario: Hesaplarım > Hesap Aç > Dijital Katılma Hesabı - Euro - Günlük Kazançlı 2-29 Gün
    When Go to "Hesaplar > Hesap Aç" from Menu with assertion "ACCOUNTS_TITLE_MENU" of type "EQUAL" using "NAME"
    When Opens a "Dijital Katılma Hesabı" account with "Euro" currency and "Günlük Kazançlı (2-29 Gün)" maturity date

  Scenario: Hesaplarım > Hesap Aç > Dijital Katılma Hesabı - Yakut - 3 Aylık
    When Go to "Hesaplar > Hesap Aç" from Menu with assertion "ACCOUNTS_TITLE_MENU" of type "EQUAL" using "NAME"
    When Opens a "Dijital Katılma Hesabı" account with "Yakut" currency and "3 Aylık" maturity date

  Scenario: Hesaplarım > Hesap Aç > Dijital Katılma Hesabı - Yakut - Kırık Vadeli
    When Go to "Hesaplar > Hesap Aç" from Menu with assertion "ACCOUNTS_TITLE_MENU" of type "EQUAL" using "NAME"
    When Opens a "Dijital Katılma Hesabı" account with "Yakut" currency and "Kırık Vadeli" maturity date

  Scenario: Hesaplarım > Hesap Aç - Yatırım Hesabı - Türk Lirası
    When Go to "Hesaplar > Hesap Aç" from Menu with assertion "ACCOUNTS_TITLE_MENU" of type "EQUAL" using "NAME"
    When Opens a "Yatırım Hesabı" account with "Türk Lirası" currency

  Scenario: Hesaplarım > Hesap Aç - Yatırım Hesabı - Amerikan Doları
    When Go to "Hesaplar > Hesap Aç" from Menu with assertion "ACCOUNTS_TITLE_MENU" of type "EQUAL" using "NAME"
    When Opens a "Yatırım Hesabı" account with "Amerikan Doları" currency

  Scenario: Hesaplarım > Hesap Aç - Yatırım Hesabı - Euro
    When Go to "Hesaplar > Hesap Aç" from Menu with assertion "ACCOUNTS_TITLE_MENU" of type "EQUAL" using "NAME"
    When Opens a "Yatırım Hesabı" account with "Euro" currency

  Scenario: Hesaplarım > Hesap Aç - Yatırım Hesabı - Yakut
    When Go to "Hesaplar > Hesap Aç" from Menu with assertion "ACCOUNTS_TITLE_MENU" of type "EQUAL" using "NAME"
    When Opens a "Yatırım Hesabı" account with "Yakut" currency