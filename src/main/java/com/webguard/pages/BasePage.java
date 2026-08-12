package com.webguard.pages;

import com.webguard.driver.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage() {
        this.driver = DriverManager.getDriver();
        // Default wait of 10 seconds
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    protected WebElement waitForElementVisible(By by) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    protected WebElement waitForElementClickable(By by) {
        return wait.until(ExpectedConditions.elementToBeClickable(by));
    }

    protected void click(By by, String elementName) {
        waitForElementClickable(by).click();
        System.out.println("Clicked on: " + elementName);
    }

    protected void sendKeys(By by, String text, String elementName) {
        WebElement element = waitForElementVisible(by);
        element.clear();
        element.sendKeys(text);
        System.out.println("Entered text '" + text + "' in: " + elementName);
    }

    protected String getText(By by) {
        String text = waitForElementVisible(by).getText();
        System.out.println("Retrieved text: '" + text + "'");
        return text;
    }

    protected boolean isDisplayed(By by) {
        try {
            boolean visible = waitForElementVisible(by).isDisplayed();
            System.out.println("Element presence check (By: " + by.toString() + ") is: " + visible);
            return visible;
        } catch (Exception e) {
            System.out.println("Element not visible (By: " + by.toString() + ")");
            return false;
        }
    }

    public String getCurrentPageUrl() {
        return driver.getCurrentUrl();
    }
}
