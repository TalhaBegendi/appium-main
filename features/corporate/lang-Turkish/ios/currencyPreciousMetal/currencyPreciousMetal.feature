@devices=15
Feature: Currency/Precious Metal Language Turkish - IOS - CORPORATE

  Background:
    When Login as "CORPORATE" customer "NEW_ACCOUNT" using "TURKISH" language
    When Go to "Döviz / Kıymetli Maden > Döviz / Kıymetli Maden Alış / Satış" from Menu with assertion "CURRENCY_METALS_SELL_TEXT_ITEM" of type "PRESENCE_THEN_EQUAL" using "NAME"

  Scenario: Euro - Dolar Dönüşümü - Euro Alma
    When Click the EUR Buy button for the "EUR/USD" currency pair
    And Enter amount for "CURRENCY"
    Then Verify the TL equivalent buy amount is correct for "CURRENCY"
    When Click buy button
    And Click approve button
    And Enter the OTP code
    Then Verify currency transaction success message is displayed

  Scenario: Euro - Dolar Dönüşümü - Euro Satma
    When Click the EUR Sell button for the "EUR/USD" currency pair
    And Enter amount for "CURRENCY"
    Then Verify the TL equivalent buy amount is correct for "CURRENCY"
    When Click buy button
    And Click approve button
    And Enter the OTP code
    Then Verify currency transaction success message is displayed

  Scenario: Dolar Alma - TL Girerek
    When Click "USD AL" buy button
    And Enter TL amount
    Then Verify the "CURRENCY" equivalent amount is correct for buy
    When Click buy button
    And Click approve button
    And Enter the OTP code
    Then Verify currency transaction success message is displayed

  Scenario: Dolar Alma - Dolar Girerek
    When Click "USD AL" buy button
    And Enter amount for "CURRENCY"
    Then Verify the TL equivalent buy amount is correct for "CURRENCY"
    When Click buy button
    And Click approve button
    And Enter the OTP code
    Then Verify currency transaction success message is displayed

  Scenario: Dolar Satma Senaryosu - TL Girerek
    When Click "USD SAT" sell button
    And Enter TL amount
    Then Verify the TL equivalent amount is correct for "CURRENCY"
    When Click sell button
    And Click approve button
    And Enter the OTP code
    Then Verify currency transaction success message is displayed

  Scenario: Dolar Satma Senaryosu - Dolar Girerek
    When Click "USD SAT" sell button
    And Enter amount for "CURRENCY"
    Then Verify the "CURRENCY" equivalent amount is correct
    When Click sell button
    And Click approve button
    And Enter the OTP code
    Then Verify currency transaction success message is displayed

  Scenario: Euro Alma - Euro Girerek
    When Click "EUR AL" buy button
    And Enter amount for "CURRENCY"
    Then Verify the "CURRENCY" equivalent amount is correct for buy
    When Click buy button
    And Click approve button
    And Enter the OTP code
    Then Verify currency transaction success message is displayed

  Scenario: Euro Alma - TL Girerek
    When Click "EUR AL" buy button
    And Enter TL amount
    Then Verify the TL equivalent amount is correct for "CURRENCY"
    When Click buy button
    And Click approve button
    And Enter the OTP code
    Then Verify currency transaction success message is displayed

  Scenario: Euro Satma Senaryosu
    When Click "EUR SAT" sell button
    And Enter amount for "CURRENCY"
    Then Verify the "CURRENCY" equivalent amount is correct
    When Click sell button
    And Click approve button
    And Enter the OTP code
    Then Verify currency transaction success message is displayed

  Scenario: Euro Satma Senaryosu - TL Girerek
    When Click "EUR SAT" sell button
    And Enter TL amount
    Then Verify the TL equivalent amount is correct for "CURRENCY"
    When Click sell button
    And Click approve button
    And Enter the OTP code
    Then Verify currency transaction success message is displayed

  Scenario: Gram Altın Alma - Gram Altın Girerek
    When Click "ALT (gr) AL" buy button
    And Enter amount for "PRECIOUS METAL"
    Then Verify the "PRECIOUS METAL" equivalent amount is correct for buy
    When Click buy button
    And Click approve button
    And Enter the OTP code
    Then Verify currency transaction success message is displayed

  Scenario: Gram Altın Alma - TL Girerek
    When Click "ALT (gr) AL" buy button
    And Enter TL amount
    Then Verify the TL equivalent amount is correct for "PRECIOUS METAL"
    When Click buy button
    And Click approve button
    And Enter the OTP code
    Then Verify currency transaction success message is displayed

  Scenario: Gram Altın Satma Senaryosu - Gram Altın girerek
    When Click "ALT (gr) SAT" sell button
    And Enter amount for "PRECIOUS METAL"
    Then Verify the "PRECIOUS METAL" equivalent amount is correct
    When Click sell button
    And Click approve button
    And Enter the OTP code
    Then Verify currency transaction success message is displayed

  Scenario: Gram Altın Satma Senaryosu - Tl girerek
    When Click "ALT (gr) SAT" sell button
    And Enter TL amount
    Then Verify the TL equivalent amount is correct for "PRECIOUS_METAL"
    When Click sell button
    And Click approve button
    And Enter the OTP code
    Then Verify currency transaction success message is displayed

  Scenario: Gümüş Alma - Gümüş Girerek
    When Click "GMS (gr) AL" buy button
    And Enter amount for "PRECIOUS METAL"
    Then Verify the "PRECIOUS METAL" equivalent amount is correct for buy
    When Click buy button
    And Click approve button
    And Enter the OTP code
    Then Verify currency transaction success message is displayed

  Scenario: Gümüş Alma - TL Girerek
    When Click "GMS (gr) AL" buy button
    And Enter TL amount
    Then Verify the TL equivalent amount is correct for "PRECIOUS METAL"
    When Click buy button
    And Click approve button
    And Enter the OTP code
    Then Verify currency transaction success message is displayed

  Scenario: Gümüş Satma Senaryosu
    When Click "GMS (gr) SAT" sell button
    And Enter amount for "PRECIOUS METAL"
    Then Verify the "PRECIOUS METAL" equivalent amount is correct
    When Click sell button
    And Click approve button
    And Enter the OTP code
    Then Verify currency transaction success message is displayed

  Scenario: Gümüş Satma Senaryosu - Tl girerek
    When Click "GMS (gr) SAT" sell button
    And Enter TL amount
    Then Verify the TL equivalent amount is correct for "PRECIOUS_METAL"
    When Click sell button
    And Click approve button
    And Enter the OTP code
    Then Verify currency transaction success message is displayed

  Scenario: Sterlin Alma - Sterlin Girerek
    When Click "GBP AL" buy button
    And Enter amount for "CURRENCY"
    Then Verify the "CURRENCY" equivalent amount is correct for buy
    When Click buy button
    And Click approve button
    And Enter the OTP code
    Then Verify currency transaction success message is displayed

  Scenario: Sterlin Alma - TL Girerek
    When Click "GBP AL" buy button
    And Enter TL amount
    Then Verify the TL equivalent buy amount is correct for "CURRENCY"
    When Click buy button
    And Click approve button
    And Enter the OTP code
    Then Verify currency transaction success message is displayed

  Scenario: Sterlin Satma Senaryosu - Sterlin Girerek
    When Click "GBP SAT" sell button
    And Enter amount for "CURRENCY"
    Then Verify the "CURRENCY" equivalent amount is correct
    When Click sell button
    And Click approve button
    And Enter the OTP code
    Then Verify currency transaction success message is displayed

  Scenario: Sterlin Satma Senaryosu - TL Girerek
    When Click "GBP SAT" sell button
    And Enter TL amount
    Then Verify the "CURRENCY" equivalent amount is correct
    When Click sell button
    And Click approve button
    And Enter the OTP code
    Then Verify currency transaction success message is displayed

  Scenario: Çeyrek Altın Alma - TL Girerek
    When Click "ZCeyrek AL" sell button
    And Enter TL amount
    Then Verify the TL equivalent amount is correct for "PRECIOUS_METAL"
    When Click sell button
    And Click approve button
    And Enter the OTP code
    Then Verify currency transaction success message is displayed

  Scenario: Çeyrek Altın Alma - Çeyrek Altın Girerek
    When Click "ZCeyrek AL" buy button
    And Enter amount for "PRECIOUS METAL"
    Then Verify the "PRECIOUS METAL" equivalent amount is correct for buy
    When Click buy button
    And Click approve button
    And Enter the OTP code
    Then Verify currency transaction success message is displayed

  Scenario: Çeyrek Altın Satma Senaryosu - Çeyrek Altın Girerek
    When Click "ZCeyrek SAT" sell button
    And Enter amount for "PRECIOUS METAL"
    Then Verify the "PRECIOUS METAL" equivalent amount is correct
    When Click sell button
    And Click approve button
    And Enter the OTP code
    Then Verify currency transaction success message is displayed

  Scenario: Çeyrek Altın Satma Senaryosu - TL Girerek
    When Click "ZCeyrek SAT" sell button
    And Enter TL amount
    Then Verify the "PRECIOUS METAL" equivalent amount is correct
    When Click sell button
    And Click approve button
    And Enter the OTP code
    Then Verify currency transaction success message is displayed

  Scenario: Kuveyt Dinarı Satma Senaryosu - Kuveyt Dinarı Girerek
    When Click "KWD SAT" sell button
    And Enter amount for "CURRENCY"
    Then Verify the "CURRENCY" equivalent amount is correct
    When Click sell button
    And Click approve button
    And Enter the OTP code
    Then Verify currency transaction success message is displayed

  Scenario: Suudi Arabistan Riyali Satma Senaryosu - Suudi Arabistan Riyali Girerek
    When Click "KWD SAT" sell button
    And Enter amount for "CURRENCY"
    Then Verify the "CURRENCY" equivalent amount is correct
    When Click sell button
    And Click approve button
    And Enter the OTP code
    Then Verify currency transaction success message is displayed