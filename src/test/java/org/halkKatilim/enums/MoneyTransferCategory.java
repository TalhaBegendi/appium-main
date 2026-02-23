package org.halkKatilim.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.halkKatilim.deviceConfig.DeviceContext;
import org.halkKatilim.utility.Driver;

@Getter
@RequiredArgsConstructor
public enum MoneyTransferCategory {

    ACCOUNT(
            "Hesap Numarasına",   // ANDROID TR
            "to Account Number",     // ANDROID EN
            "Hesap No",           // IOS TR
            "Account Number"          // IOS EN
    );

    private final String androidTr;
    private final String androidEn;
    private final String iosTr;
    private final String iosEn;

    public String resolve() {

        Platform platform = Driver.getPlatformForThread();
        Language language = DeviceContext.getLanguage();

        return switch (platform) {

            case ANDROID ->
                    language == Language.TURKISH
                            ? androidTr
                            : androidEn;

            case IOS ->
                    language == Language.TURKISH
                            ? iosTr
                            : iosEn;
        };
    }
}
