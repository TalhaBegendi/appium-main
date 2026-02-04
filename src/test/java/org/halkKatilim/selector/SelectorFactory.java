package org.halkKatilim.selector;

import org.halkKatilim.constant.SelectorType;

public class SelectorFactory {

    private SelectorFactory() {}

    public static Selector createElementHelper(SelectorType selectorType) {
        return switch (selectorType) {
            case ANDROID -> new AndroidSelector();
            case IOS -> new IOSSelector();
        };
    }
}
