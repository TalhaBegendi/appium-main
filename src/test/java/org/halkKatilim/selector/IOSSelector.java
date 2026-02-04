package org.halkKatilim.selector;

import io.appium.java_client.AppiumBy;
import org.halkKatilim.constant.ElementInfo;
import org.openqa.selenium.By;

public class IOSSelector implements Selector {

    @Override
    public By getElementInfoToBy(ElementInfo elementInfo) {
        return switch (elementInfo.getIosType()) {
            case "xpath" -> By.xpath(elementInfo.getIosValue());
            case "id" -> By.id(elementInfo.getIosValue());
            case "name" -> AppiumBy.name(elementInfo.getIosValue());
            case "classChain" -> AppiumBy.iOSClassChain(elementInfo.getIosValue());
            case "predicateString" -> AppiumBy.iOSNsPredicateString(elementInfo.getIosValue());
            case "accessibilityId" -> AppiumBy.accessibilityId(elementInfo.getIosValue());
            case "class" -> By.className(elementInfo.getIosValue());
            case "css" -> By.cssSelector(elementInfo.getIosValue());
            default -> null;
        };
    }

    @Override
    public int getElementInfoToIndex(ElementInfo elementInfo) {
        return elementInfo.getIosIndex();
    }
}
