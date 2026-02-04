package org.halkKatilim.enums;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Getter
public enum StepsText {

    MINE_CURRENCY(
            "Altın",
            "Gold",
            "Yakut",
            "Ruby"
    ),

    GOLD_MATURITY_DATE(
            "Kırık Vadeli",
            "Breach Maturity"
    ),

    STANDARD_MATURITY_DATE(
            "Günlük (32-999 Gün)",
            "Günlük Kazançlı (2-29 Gün)",
            "Daily Profit (2-29 Days)",
            "Daily (32-999 Days)"
    ),

    INVESTMENT_ACCOUNT(
            "Yatırım Hesabı",
                    "Investment Account"
    );

    private final Set<String> texts;

    StepsText(String... texts) {
        this.texts = Arrays.stream(texts)
                .map(String::toLowerCase)
                .collect(Collectors.toUnmodifiableSet());
    }

    public boolean matches(String value) {
        return value != null && texts.contains(value.toLowerCase());
    }
}
