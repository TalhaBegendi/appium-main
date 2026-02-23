@devices=pixel7
Feature: Approve or decline corporate transaction flows language Turkish - ANDROID - CORPORATE

  Background:
    Given Login as "CORPORATE" customer "REQUESTER" using "TURKISH" language
    Given Go to "Para Transferi > Başka Hesaba (Havale / EFT / FAST) > IBAN" from Menu with assertion "MONEY_TRANSFER_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"

  Scenario: CORPORATE - Bireysel Müşteri için Aynı Gün Onaya Gönderilen IBAN Transferinin Onaylanması

    When Enter "RETAIL" customer IBAN transfer details to send approval for today
    When Click continue button on the Another Account page
    When Click confirm button on confirmation page
    And Enter the OTP code
    Then The transaction should be successfully sent for approval
    When Click close message button
    And Log out from the application using "TURKISH" language
    And Login as "APPROVER" customer "CORPORATE" role
    And Go to "Kurumsal > İşlemler" from Menu with assertion "CORPORATE_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"
    Then Verify the IBAN transfer details sent for approval are correct
    When Approve the transaction sent for approval
    And Enter the OTP code
    Then Verify transaction success message is displayed
    When Click close message button
    And Click back and menu button
    And Log out from the application using "TURKISH" language
    And Login as "REQUESTER" customer "CORPORATE" role
    And Go to "Kurumsal > İşlemler" from Menu with assertion "CORPORATE_TITLE_MENU_ITEM" of type "EQUAL" using "NAME"
    And Click "Onaylanan" tab
    Then Verify the IBAN transfer details sent for approval are correct


