package org.halkKatilim.selector;

import org.halkKatilim.constant.ElementInfo;
import org.halkKatilim.constant.SelectorInfo;
import org.openqa.selenium.By;

public interface Selector {

    default ElementInfo getElementInfo(String key) {
        return ElementRegistry.INSTANCE.findElementInfoByKey(key);
    }

    default By getElementInfoToBy(String key) {
        return getElementInfoToBy(getElementInfo(key));
    }


    default SelectorInfo getSelectorInfo(String key) {
        return new SelectorInfo(getElementInfoToBy(key), getElementInfoToIndex(key));
    }

    By getElementInfoToBy(ElementInfo elementInfo);

    int getElementInfoToIndex(ElementInfo elementInfo);

    default int getElementInfoToIndex(String key) {
        return getElementInfoToIndex(getElementInfo(key));
    }
}
