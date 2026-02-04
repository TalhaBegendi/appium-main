package org.halkKatilim.selector;


import io.appium.java_client.AppiumBy;
import org.halkKatilim.constant.ElementInfo;
import org.openqa.selenium.By;

public class AndroidSelector implements Selector {

    @Override
    public By getElementInfoToBy(ElementInfo elementInfo) {
        return switch (elementInfo.getAndroidType()) {
            case "css" -> By.cssSelector(elementInfo.getAndroidValue());
            case "id" -> By.id(elementInfo.getAndroidValue());
            case "xpath" -> By.xpath(elementInfo.getAndroidValue());
            case "class" -> By.className(elementInfo.getAndroidValue());
            case "text" -> By.linkText(elementInfo.getAndroidValue());
            case "accessibilityId" -> AppiumBy.accessibilityId(elementInfo.getAndroidValue());
            default -> null;
        };
    }

    @Override
    public int getElementInfoToIndex(ElementInfo elementInfo) {
        return elementInfo.getAndroidIndex();
    }
}
