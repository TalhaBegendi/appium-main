@devices=16e
Feature: Currency/Precious Metal Language English

  Background:
    Given Login as "RETAIL" customer "OPTION_USER" using "ENGLISH" language
    When Go to "Currency / Precious Metals > Currency / Precious Metals Buy / Sell" from Menu

  Scenario: Euro - Dollar Conversion - Buying Euro
    When Click the EUR Buy button for the "EUR/USD" currency pair
    And Enter amount for "FOREIGN"
    Then Verify the TL equivalent buy amount is correct for "CURRENCY"
    When Click buy button
    And Click approve button
    And Enter the OTP code
    Then Verify currency transaction success message is displayed

  Scenario: Euro - Dollar Conversion - Selling Euro
    When Click the EUR Sell button for the "EUR/USD" currency pair
    And Enter amount for "FOREIGN"
    Then Verify the TL equivalent amount is correct for "CURRENCY"
    When Click sell button
    And Click approve button
    And Enter the OTP code
    Then Verify currency transaction success message is displayed

  Scenario: Buying Dollars – Entering Amount in TRY
    When Click "USD BUY" buy button
    And Enter amount for "TL"
    Then Verify the "CURRENCY" equivalent amount is correct for buy
    When Click buy button
    And Click approve button
    And Enter the OTP code
    Then Verify currency transaction success message is displayed

  Scenario: Buying Dollars – Entering Amount in USD
    When Click "USD BUY" buy button
    And Enter amount for "FOREIGN"
    Then Verify the TL equivalent buy amount is correct for "CURRENCY"
    When Click buy button
    And Click approve button
    And Enter the OTP code
    Then Verify currency transaction success message is displayed

  Scenario: Dollar Selling Scenario – Entering Amount in TRY
    When Click "USD SELL" sell button
    And Enter amount for "TL"
    Then Verify the TL equivalent amount is correct for "CURRENCY"
    When Click sell button
    And Click approve button
    And Enter the OTP code
    Then Verify currency transaction success message is displayed

  Scenario: Dollar Selling Scenario - Entering Amount in USD
    When Click "USD SELL" sell button
    And Enter amount for "FOREIGN"
    Then Verify the "CURRENCY" equivalent amount is correct
    When Click sell button
    And Click approve button
    And Enter the OTP code
    Then Verify currency transaction success message is displayed

  Scenario: Buying Euro - Entering Amount in EUR
    When Click "EUR BUY" buy button
    And Enter amount for "FOREIGN"
    Then Verify the "CURRENCY" equivalent amount is correct for buy
    When Click buy button
    And Click approve button
    And Enter the OTP code
    Then Verify currency transaction success message is displayed

  Scenario: Buying Euro - Entering Amount in TRY
    When Click "EUR BUY" buy button
    And Enter amount for "TL"
    Then Verify the TL equivalent amount is correct for "CURRENCY"
    When Click buy button
    And Click approve button
    And Enter the OTP code
    Then Verify currency transaction success message is displayed

  Scenario: Euro Selling Scenario
    When Click "EUR SELL" sell button
    And Enter amount for "FOREIGN"
    Then Verify the "CURRENCY" equivalent amount is correct
    When Click sell button
    And Click approve button
    And Enter the OTP code
    Then Verify currency transaction success message is displayed

  Scenario: Euro Selling Scenario - Entering Amount in TRY
    When Click "EUR SELL" sell button
    And Enter amount for "TL"
    Then Verify the TL equivalent amount is correct for "CURRENCY"
    When Click sell button
    And Click approve button
    And Enter the OTP code
    Then Verify currency transaction success message is displayed

  Scenario: Buying Gold (Gram) - Entering Amount in Gold (Gram)
    When Click "ALT (gr) BUY" buy button
    And Enter amount for "FOREIGN"
    Then Verify the "PRECIOUS METAL" equivalent amount is correct for buy
    When Click buy button
    And Click approve button
    And Enter the OTP code
    Then Verify currency transaction success message is displayed

  Scenario: Buying Gold (Gram) - Entering Amount in TRY
    When Click "ALT (gr) BUY" buy button
    And Enter amount for "TL"
    Then Verify the TL equivalent amount is correct for "PRECIOUS METAL"
    When Click buy button
    And Click approve button
    And Enter the OTP code
    Then Verify currency transaction success message is displayed

  Scenario: Gold (Gram) Selling Scenario - Entering Amount in Gold (Gram)
    When Click "ALT (gr) SELL" sell button
    And Enter amount for "FOREIGN"
    Then Verify the "PRECIOUS METAL" equivalent amount is correct
    When Click sell button
    And Click approve button
    And Enter the OTP code
    Then Verify currency transaction success message is displayed

  Scenario: Gold (Gram) Selling Scenario - Entering Amount in TRY
    When Click "ALT (gr) SELL" sell button
    And Enter amount for "TL"
    Then Verify the TL equivalent amount is correct for "PRECIOUS_METAL"
    When Click sell button
    And Click approve button
    And Enter the OTP code
    Then Verify currency transaction success message is displayed

  Scenario: British Pound Selling Scenario - Entering Amount in TRY
    When Click "GBP SELL" sell button
    And Enter amount for "TL"
    Then Verify the TL equivalent amount is correct for "CURRENCY"
    When Click sell button
    And Click approve button
    And Enter the OTP code
    Then Verify currency transaction success message is displayed

  Scenario: Buying Quarter Gold - Entering Amount in TRY
    When Click "ZCeyrek BUY" buy button
    And Enter TL amount
    Then Verify the TL equivalent amount is correct for "PRECIOUS_METAL"
    When Click buy button
    And Click approve button
    And Enter the OTP code
    Then Verify currency transaction success message is displayed

  Scenario: Saudi Arabian Riyal Selling Scenario - Entering Amount in Saudi Arabian Riyal
    When Click "SAR SELL" sell button
    And Enter amount for "FOREIGN"
    Then Verify the "CURRENCY" equivalent amount is correct
    When Click sell button
    And Click approve button
    And Enter the OTP code
    Then Verify currency transaction success message is displayed