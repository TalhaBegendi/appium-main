package org.halkKatilim.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Language {
    TURKISH("Türkçe", new String[]{"Güvenli Çıkış", "Tamam"}),
    ENGLISH("English", new String[]{"Secure Logout", "OK"});

    @Getter private final String display;
    @Getter private final String[] logoutTexts;
}