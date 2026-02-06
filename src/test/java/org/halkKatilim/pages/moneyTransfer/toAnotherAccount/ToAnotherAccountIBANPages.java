package org.halkKatilim.pages.moneyTransfer.toAnotherAccount;

import lombok.extern.slf4j.Slf4j;
import org.halkKatilim.enums.Platform;
import org.halkKatilim.enums.UserType;
import org.halkKatilim.pages.BasePages;
import org.halkKatilim.testData.corporate.moneyTransfer.iban.IbanCustomerTransactionData;
import org.halkKatilim.testData.corporate.moneyTransfer.iban.IbanSavedTransactionDetails;
import org.halkKatilim.testData.corporate.moneyTransfer.iban.TransactionDetailsWithSenderInfo;
import org.halkKatilim.testData.corporate.moneyTransfer.TransactionDetailsWithoutReceiverAndSender;
import org.halkKatilim.utility.Driver;
import org.openqa.selenium.WebElement;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import static org.halkKatilim.pages.moneyTransfer.toAnotherAccount.ToAnotherAccountText.*;
import static org.halkKatilim.utility.helpers.FrameworkLogger.debug;
import static org.testng.AssertJUnit.assertEquals;

@Slf4j
public final class ToAnotherAccountIBANPages extends BasePages {

    IbanSavedTransactionDetails ibanSavedTransactionDetails;
    TransactionDetailsWithSenderInfo ibanPageTransactionDetailsWithSenderInfo;
    TransactionDetailsWithSenderInfo confirmationPageTransactionDetailsWithSenderInfo;

    public void clickMakeFromSavedTransaction() {
        appiumUtil.clickElement("makeFromSavedTransactionsItem");
    }

    public void selectRandomSavedTransaction() {
        List<WebElement> savedTransactionsList = appiumUtil.findElementsSilent("savedTransactionsList");
        int index = new Random().nextInt(savedTransactionsList.size());
        WebElement savedTransactionReceiverName = appiumUtil.findElementsSilent("savedTransactionsReceiverNameList").get(index);
        WebElement savedTransactionReceiverAccountNumber = appiumUtil.findElementsSilent("savedTransactionsReceiverAccountNumberList").get(index);
        ibanSavedTransactionDetails = new IbanSavedTransactionDetails(maskToLettersWithSpaces(savedTransactionReceiverName.getText()), savedTransactionReceiverAccountNumber.getText());
        appiumUtil.clickWebElement(savedTransactionsList.get(index));
    }

    private String maskToLettersWithSpaces(String text) {
        return text
                .replaceAll("[^\\p{L}]", " ")
                .replaceAll("\\s+", " ")
                .trim();
    }

    private String normalizeAmount(String amount) {
        return amount.replace(" TL", "").trim();
    }


    public void clickContinueButton() {
        appiumUtil.clickElementWithScroll("moneyTransferContinueButton")
                .ifExistClickByKey("ibanPageFirstTransactionAcceptButton");

    }

    public void verifyTransactionDetailsVisible() {

        String receiverIban = appiumUtil.findElementSilent("ibanPageReceiverIbanInputField").getText();
        String receiverName = maskToLettersWithSpaces(appiumUtil.findElementSilent("ibanPageReceiverNameField").getText());
        String senderAccountNumber = appiumUtil.findElementSilent("ibanPageSenderAccountNumber").getText();
        String senderAccountBalance = appiumUtil.findElementSilent("ibanPageSenderAccountBalance").getText();

        TransactionDetailsWithoutReceiverAndSender common = getCommonTransactionDetails("ibanPage");

        ibanPageTransactionDetailsWithSenderInfo = new TransactionDetailsWithSenderInfo(senderAccountNumber, senderAccountBalance, receiverIban, receiverName,
                common.transactionDate(), common.transactionAmount(), common.paymentType(), common.transactionDescription());

        assertEquals(ibanSavedTransactionDetails.receiverIban(), receiverIban);
        assertEquals(ibanSavedTransactionDetails.receiverName(), receiverName);
    }

    public void verifyTransactionDetailsOnTheConfirmationPage() {

        String receiverIban = findConfirmationPageReceiverIban();
        String receiverName = findConfirmationPageReceiverName();
        String senderAccountNumber = findConfirmationPageSenderAccountNumber();
        String senderAccountBalance = findConfirmationPageSenderBalance();

        TransactionDetailsWithoutReceiverAndSender common = getCommonTransactionDetails("confirmationPage");

        confirmationPageTransactionDetailsWithSenderInfo = new TransactionDetailsWithSenderInfo(senderAccountNumber, senderAccountBalance, receiverIban, receiverName,
                common.transactionDate(), common.transactionAmount(), common.paymentType(), common.transactionDescription());

        assertEquals(ibanPageTransactionDetailsWithSenderInfo, confirmationPageTransactionDetailsWithSenderInfo);
    }

    private String findConfirmationPageSenderBalance() {
        return resolveByPlatform(
                () -> appiumUtil.findElementSilent("confirmationPageSenderInfo")
                        .getText().split("Bakiye: ")[1],
                () -> appiumUtil.findElementSilent("confirmationPageSenderBalance").getText()
        ).orElse("");
    }

    private String findConfirmationPageSenderAccountNumber() {
        return resolveByPlatform(
                () -> appiumUtil.findElementSilent("confirmationPageSenderInfo")
                        .getText().split(" ")[0],
                () -> appiumUtil.findElementSilent("confirmationPageSenderAccountNumber")
                        .getText().split(" ")[0]
        ).orElse("");
    }

    private String findConfirmationPageReceiverName() {
        return resolveByPlatform(
                () -> {
                    String info = appiumUtil.findElementSilent("confirmationPageReceiverInfo").getText();
                    return maskToLettersWithSpaces(info.split("\n")[0]);
                },
                () -> maskToLettersWithSpaces(
                        appiumUtil.findElementSilent("confirmationPageReceiverName").getText()
                )
        ).orElse("");
    }

    private String findConfirmationPageReceiverIban() {
        return resolveByPlatform(
                () -> appiumUtil.findElementSilent("confirmationPageReceiverInfo")
                        .getText().split("\n")[1],
                () -> appiumUtil.findElementSilent("confirmationPageReceiverIban")
                        .getText()
        ).orElse("");
    }

    private Optional<String> resolveByPlatform(Supplier<String> androidSupplier, Supplier<String> iosSupplier) {
        try {
            return Optional.ofNullable(
                    switch (Driver.getPlatformForThread()) {
                        case ANDROID -> androidSupplier.get();
                        case IOS -> iosSupplier.get();
                    }
            );
        } catch (Exception e) {
            debug(e.getMessage());
            return Optional.empty();
        }
    }

    private TransactionDetailsWithoutReceiverAndSender getCommonTransactionDetails(String pagePrefix) {

        String transactionDate = appiumUtil.findElementSilent(pagePrefix + "TransactionDate").getText();
        String transactionAmount = normalizeAmount(appiumUtil.findElementSilent(pagePrefix + "TransactionAmount").getText());
        String paymentType = appiumUtil.findElementSilent(pagePrefix + "PaymentType").getText();
        String transactionDescription = appiumUtil.findElementSilent(pagePrefix + "TransactionDescription").getText();

        return new TransactionDetailsWithoutReceiverAndSender(
                transactionDate, transactionAmount, paymentType, transactionDescription);
    }

    public void clickConfirmButton() {
        appiumUtil.clickElement("confirmationPageConfirmButton");
    }

    public void enterOtpCode(String otpCode) {
        appiumUtil
                .waitUntilElementLoad("smsOtpComponent")
                .clearAndFillInputWithScroll("inputSmsOtp", otpCode)
                .clickElement("smsOtpButtonSendItem");
    }

    public void verifyTransactionSuccess() {
        appiumUtil.waitUntilElementLoad("moneyTransferSuccessMessage");
        assertEquals(TURKISH_MONEY_TRANSFER_SUCCESS_MESSAGE, appiumUtil.findElementSilent("moneyTransferSuccessMessage").getText());
    }

    public void givePermissionForSameDayTransaction() {
        appiumUtil.clickElement("confirmationPageSwitchPermission")
                .clickElement("confirmationPageDialogAcceptButton");
    }

    public void enterTransactionDetailsForToday(String customerType) {

        UserType accountType = UserType.valueOf(customerType.toUpperCase());
        IbanCustomerTransactionData data = getCustomerTransactionData(accountType);
        fillTransactionFields(data);

        String receiverIban = appiumUtil.findElementSilent("ibanPageReceiverIbanInputField").getText();
        String receiverName = maskToLettersWithSpaces(appiumUtil.findElementSilent("ibanPageReceiverNameField").getText());
        String senderAccountNumber = appiumUtil.findElementSilent("ibanPageSenderAccountNumber").getText();
        String senderAccountBalance = appiumUtil.findElementSilent("ibanPageSenderAccountBalance").getText();

        TransactionDetailsWithoutReceiverAndSender common = getCommonTransactionDetails("ibanPage");

        ibanPageTransactionDetailsWithSenderInfo = new TransactionDetailsWithSenderInfo(senderAccountNumber, senderAccountBalance, receiverIban, receiverName,
                common.transactionDate(), common.transactionAmount(), common.paymentType(), common.transactionDescription());
    }

    private IbanCustomerTransactionData getCustomerTransactionData(UserType accountType) {
        return switch (accountType) {
            case RETAIL -> new IbanCustomerTransactionData(
                    RETAIL_CUSTOMER_RECEIVER_IBAN, TRANSACTION_AMOUNT, TRANSACTION_DESCRIPTION);
            case CORPORATE -> new IbanCustomerTransactionData(
                    CORPORATE_CUSTOMER_RECEIVER_IBAN, TRANSACTION_AMOUNT, TRANSACTION_DESCRIPTION);
        };
    }

    private void fillTransactionFields(IbanCustomerTransactionData data) {
        appiumUtil.clearAndFillInputWithScroll("ibanPageReceiverIbanInputField", data.iban())
                .clearAndFillInputWithScroll("ibanPageTransactionAmount", data.amount())
                .clearAndFillInputWithScroll("ibanPageTransactionDescription", data.description());
    }

    public void verifyTransactionSentForApproval() {
        assertEquals(TURKISH_MONEY_TRANSFER_SENT_FOR_APPROVAL_INFO_TEXT, appiumUtil.findElementSilent("moneyTransferSentForApprovalInfoText").getText());
    }

    public void enterCustomerTransactionDetailsWithDifferentCurrencyForToday(String customerType) {
        IbanCustomerTransactionData data = new IbanCustomerTransactionData(
                CORPORATE_CUSTOMER_RECEIVER_IBAN_DIFFERENT_CURRENCY, TRANSACTION_AMOUNT, TRANSACTION_DESCRIPTION);
        fillTransactionFields(data);
    }

    public void theDifferentCurrencyErrorMessageShouldBeDisplayed() {
        Platform platform = Driver.getPlatformForThread();
        String message = platform == Platform.ANDROID
                ? TURKISH_DIFFERENT_CURRENCY_ERROR_MESSAGE
                : TURKISH_DIFFERENT_CURRENCY_ERROR_MESSAGE_IOS;
        assertEquals(message, appiumUtil.findElementSilent("differentCurrencyErrorMessage").getText());
    }

    public void enterCustomerTransactionDetailsForDaysLater(String customerType, String nextDay) {
        IbanCustomerTransactionData data = new IbanCustomerTransactionData(
                CORPORATE_CUSTOMER_RECEIVER_IBAN, TRANSACTION_AMOUNT, TRANSACTION_DESCRIPTION);
        fillTransactionFields(data);
        WebElement tranDateElement = appiumUtil.findElementSilent("ibanPageTransactionDate");
        selectTransactionDate(tranDateElement, nextDay);
        appiumUtil.findElementSilent("moneyTransferDatePickerOkButton").click();
        appiumUtil.clickElementWithScroll("moneyTransferAcceptOrderButton");
    }

    public void selectTransactionDate(WebElement tranDateElement, String nextDay) {
        Platform platform = Driver.getPlatformForThread();

        String date = tranDateElement.getText();
        String tranDate = getDatePlusDaysFromUI(date, nextDay);
        String tranMonth = getFullMonthName(tranDate);
        int day = getDayNumber(tranDate);
        String year = getYear(tranDate);
        tranDateElement.click();
        switch (platform) {
            case ANDROID -> {
                goToCorrectMonth(tranMonth);
                selectDayFromPicker(day);
            }
            case IOS -> {
                selectDateFromIOSPicker(day, tranMonth, year);
            }
        }
    }

    private void selectDateFromIOSPicker(int day, String month, String year) {

        List<WebElement> datePickerComponentList = appiumUtil.findElementsSilent("moneyTransferIosIbanPageDatePickerComponentList");

        datePickerComponentList.get(0).sendKeys(String.valueOf(day));
        datePickerComponentList.get(1).sendKeys(month);
        datePickerComponentList.get(2).sendKeys(year);
    }

    private String getYear(String formattedDate) {
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.forLanguageTag("tr"));
        LocalDate date = LocalDate.parse(formattedDate, formatter);
        return String.valueOf(date.getYear());
    }


    private void goToCorrectMonth(String targetMonth) {

        WebElement nextMonthBtn =
                appiumUtil.findElementSilent("moneyTransferDatePickerNextMonthArrow");
        boolean reached = IntStream.rangeClosed(1, 12)
                .anyMatch(i -> {
                    String currentMonth = appiumUtil
                            .findElementSilent("moneyTransferDatePickerMonthField")
                            .getText()
                            .toLowerCase();
                    return currentMonth.contains(targetMonth.toLowerCase()) || clickAndContinue(nextMonthBtn);
                });
        if (!reached) {
            throw new RuntimeException("Hedef aya ulaşılamadı: " + targetMonth);
        }
    }

    private boolean clickAndContinue(WebElement btn) {
        btn.click();
        appiumUtil.waitBySecond(1);
        return false;
    }


    private String getDatePlusDaysFromUI(String dateText, String daysToAdd) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate baseDate = LocalDate.parse(dateText, inputFormatter);
        LocalDate newDate = baseDate.plusDays(Long.parseLong(daysToAdd));
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.forLanguageTag("tr"));
        return newDate.format(outputFormatter);
    }

    private String getFullMonthName(String formattedDate) {

        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.forLanguageTag("tr"));
        LocalDate date = LocalDate.parse(formattedDate, inputFormatter);
        DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MMMM", Locale.forLanguageTag("tr"));
        return date.format(monthFormatter);
    }

    private int getDayNumber(String formattedDate) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.forLanguageTag("tr"));
        LocalDate date = LocalDate.parse(formattedDate, formatter);
        return date.getDayOfMonth();
    }

    private void selectDayFromPicker(int targetDay) {

        appiumUtil.findElementsSilent("moneyTransferDatePickerDays").stream()
                .filter(e -> e.getText().trim().equals(String.valueOf(targetDay)))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Takvimde gün bulunamadı: " + targetDay))
                .click();
    }

    public void enterTransactionAmountAndDescription(String amount) {
        appiumUtil.clearAndFillInputWithScroll("ibanPageTransactionDescription", TRANSACTION_DESCRIPTION)
                .clearAndFillInputWithScroll("ibanPageTransactionAmount", amount);
    }

    public void enterFundTransactionDetailsForToday() {
        appiumUtil.clearAndFillInputWithScroll("ibanPageReceiverIbanInputField", FUND_CUSTOMER_RECEIVER_IBAN);
    }

    public void verifyFundWarningErrorAsIsDisplayed() {
        Platform platform = Driver.getPlatformForThread();
        String errorMessage = platform == ANDROID ? TURKISH_FUND_WARNING_ERROR_MESSAGE : TURKISH_FUND_WARNING_ERROR_MESSAGE_IOS;
        assertEquals(appiumUtil.findElementSilent("moneyTransferFundWarningPopupMessage").getText(), errorMessage);
    }

    public void enterFundTransactionDetailsToAccountForToday() {
        appiumUtil.clearAndFillInputWithScroll("moneyTransferFundWarningPopupMessage", FUND_ACCOUNT_NUMBER);
    }
}