package org.halkKatilim.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum DisplayText {

    // ---------- HOMEPAGE ----------
    MY_ASSETS_DISPLAY(new String[]{
            "My Assets",
            "Varlıklarım"}),

    MY_ACCOUNTS_DISPLAY(new String[]{
            "My Accounts",
            "Hesaplarım"}),

    LAST_TRANSACTIONS_DISPLAY(new String[]{
            "Last 10 Activities",
            "Son 10 Hareket"}),


    // ---------- MENU/FINANCING----------
    VEHICLE_LOAN_APPLICATION_DISPLAY(new String[]{
            "Finansman başvurunuza olumlu yanıt veremiyoruz. Ayrıntılı bilgi almak ve diğer ürünlerimize başvuru yapmak için en yakın şubemizi ziyaret edebilirsiniz.",
            "We cannot provide a positive response to your financing application. You can visit our nearest branch for detailed information and to apply for our other products."}),

    VEHICLE_LOAN_TITLE_DISPLAY(new String[]{
            "Araç Bilgileri",
            "Vehicle Information"}),

    FINANCING_CALCULATION_DISPLAY(new String[]{
            "Finansman Hesapla",
            "Financing Calculation",
            "Calculate Financing"}),

    PORTFOLIO_DISPLAY(new String[]{
            "Portföyüm (Hisse/Fon/Kira S.)",
            "Portföyüm (Hisse / Fon / Kira S.)",
            "Alış / Satış",
            "My Portfolio (Stocks/Funds/Lease C.)",
            "Buy / Sell",}),


    MY_LOAN_APPLICATION_DISPLAY(new String[]{
            "Başvurularım",
            "My Applications"}),


    // ---------- MENU ----------
    ABOUT_DISPLAY(new String[]{
            "About",
            "Hakkında"}),

    SAVED_TRANSACTION_DISPLAY(new String[]{
            "Saved Transaction",
            "Kayıtlı İşlemler"}),

    NOTIFICATIONS_AND_MESSAGES_DISPLAY(new String[]{
            "Notifications And Messages",
            "Bildirimler ve Mesajlar"}),

    HOME_DISPLAY(new String[]{
            "Home",
            "Ana Sayfa"}),

    CURRENCY_METALS_SELL_TEXT_DISPLAY(new String[]{
            "Bugüne ait kur bilgileri bulunmamaktadır.",
            "29 numaralı dövizin şube maliyet kuru bulunamadı.",
            "No currency information available for today",
            "Branch cost currency rate could not be found for the currency no. 29"}),

    MY_INVESTMENT_ACCOUNTS_TEXT_DISPLAY(new String[]{
            "Yatırım hesabı açabilmek için uygunluk testi yapmanız gerekmektedir.",
            "İşleminizi şu an gerçekleştiremiyoruz, lütfen daha sonra tekrar deneyiniz.",
            "You must undergo suitability test in order to be able to open investment account.",
            "We can not process your transaction at the moment, please try again later."}),

    MY_INVESTMENT_ACCOUNTS_TITLE_DISPLAY(new String[]{
            "Yatırım Hesabı Aç",
            "İşleminizi şu an gerçekleştiremiyoruz, lütfen daha sonra tekrar deneyiniz.",
            "We can not process your transaction at the moment, please try again later."}),

    MY_CHECK_DISPLAY(new String[]{
            "Çek Defter Başvurularım",
            "My Cheque Book Aplications"}),

    EASY_ADDRESS_DISPLAY(new String[]{
            "Kolay Adreslerim",
            "Easy Addresses",
            "My Easy Addresses"}),

    OPEN_ACCOUNTS_DISPLAY(new String[]{
            "Hesap Aç",
            "Open An Account",
            "Open Account"}),

    SUCCESS_ACCOUNTS_DISPLAY(new String[]{
            "Hesabınız açıldı.",
            "Your account has been opened.",
            "Open Account"}),

    BUY_SELL_DISPLAY(new String[]{
            "Alış / Satış",
            "Buy / Sell",}),

    CURRENCY_REFERENCE_DISPLAY(new String[]{
            "Kur Referansı İşlemleri",
            "Exchange Rate Reference Transactions",}),

    UPDATE_TRANSACTION_DISPLAY(new String[]{
            "Transfer Limitlerini Güncelle",
            "Update Transfer Limits",}),

    DEBIT_PAYMENT_DISPLAY(new String[]{
            "Karta",
            "To the Card",
            "Harcama İtirazı",
            "Expense Objection",
            "Transaction Dispute",}),

    MY_FINANCES_DISPLAY(new String[]{
            "Finansmanlarım",
            "My Financings",}),

    MONEY_TRANSFER_SECURE_DISPLAY(new String[]{
            "Güvenli Ödeme Kaydı",
            "Güvenli Ödeme İşlemi",
            "Secure Payment Registration",
            "Secure Payment Transaction",}),

    MONEY_TRANSFER_DISPLAY(new String[]{
            "Kayıtlı İşlemler",
            "Talimatlarım",
            "Saved Transactions",
            "Karta",
            "Kendi Kartıma",
            "Başka Karta",
            "Debt Payment",
            "To the Card",
            "My Orders",
            "To My Card",
            "To Other Card",
            "My Instructions"}),

    TRANSFER_MONEY_DISPLAY(new String[]{
            "Karekod İle Öde",
            "Pay With QR Code",
            "Ödeme / Para Transferi Yap",
            "Pay / Money Transfer",
            "Para göndereceğiniz kişinin hesap bilgilerini içeren TR Karekodu okutunuz. İşleminizi, TR Karekod görselini galeriden seçerek de gerçekleştirebilirsiniz.",
            "Scan the QR code of the account of the person you are sending money to. You can also perform your transaction by selecting the QR code image from the gallery."});

    private final String[] texts;
}