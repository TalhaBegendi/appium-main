package org.halkKatilim.utility;

import org.halkKatilim.utility.context.ExecutionContext;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import static org.halkKatilim.constant.Config.DEFAULT_WAIT;


public interface WaitConditions {


    static WebDriverWait getWebDriverWait(int time) {
        return new WebDriverWait(ExecutionContext.getDriver(), Duration.ofSeconds(time));
    }
    private void waitUntil(Runnable waitRunnable) {
        try {
            waitRunnable.run();
        } catch (Exception ignored) {}
    }

    default void waitForElementToBeVisible(WebElement element) {
        waitForElementToBeVisible(element, DEFAULT_WAIT);
    }

    default void waitForElementToBeVisible(WebElement element, int time) {
        waitUntil(() -> getWebDriverWait(time).until(ExpectedConditions.visibilityOf(element)));
    }

    default void waitForElementToBeVisible(By locator) {
        waitForElementToBeVisible(locator, DEFAULT_WAIT);
    }

    default void waitForElementToBeVisible(By locator, int time) {
        waitUntil(() -> getWebDriverWait(time).until(ExpectedConditions.visibilityOfElementLocated(locator)));
    }

    default void waitForElementToBeInvisible(WebElement element) {
        waitForElementToBeInvisible(element, DEFAULT_WAIT);
    }

    default void waitForElementToBeInvisible(WebElement element, int time) {
        waitUntil(() -> getWebDriverWait(time).until(ExpectedConditions.invisibilityOf(element)));
    }

    default void waitForElementToBeInvisible(By locator) {
        waitForElementToBeInvisible(locator, DEFAULT_WAIT);
    }

    default void waitForElementToBeInvisible(By locator, int time) {
        waitUntil(() -> getWebDriverWait(time).until(ExpectedConditions.invisibilityOfElementLocated(locator)));
    }

    default void waitForElementToBePresence(By locator) {
        waitForElementToBePresence(locator, DEFAULT_WAIT);
    }

    default void waitForElementToBePresence(By locator, int time) {
        waitUntil(() -> getWebDriverWait(time).until(ExpectedConditions.presenceOfElementLocated(locator)));
    }
    default void waitForElementToBeClickable(By locator) {
        waitForElementToBeClickable(locator, DEFAULT_WAIT);
    }

    default void waitForElementToBeClickable(By locator, int time) {
        waitUntil(() -> getWebDriverWait(time).until(ExpectedConditions.elementToBeClickable(locator)));
    }

    default void waitForElementToBeClickable(WebElement element) {
        waitForElementToBeClickable(element, DEFAULT_WAIT);
    }

    default void waitForElementToBeClickable(WebElement element, int time) {
        waitUntil(() -> getWebDriverWait(time).until(ExpectedConditions.elementToBeClickable(element)));
    }

    default void alertIsPresent() {
        alertIsPresent(DEFAULT_WAIT);
    }

    default void alertIsPresent(int time) {
        waitUntil(() -> getWebDriverWait(time).until(ExpectedConditions.alertIsPresent()));
    }

}
