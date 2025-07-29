package utils;

import java.time.Duration;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WaitHelper {
    private WebDriverWait wait;

    public WaitHelper(WebDriver driver, int timeoutInSeconds) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
    }

    public void waitForElementToBeClickable(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public void waitForElementToBeVisible(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public void waitForElementToBeVisible(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void waitForElementToBeClickable(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public void waitForPageTitle(String expectedTitle) {
        wait.until(ExpectedConditions.titleContains(expectedTitle));
    }

    public void waitForUrl(String expectedUrl) {
        wait.until(ExpectedConditions.urlContains(expectedUrl));
    }

    public void waitForAlert() {
        wait.until(ExpectedConditions.alertIsPresent());
    }
}
