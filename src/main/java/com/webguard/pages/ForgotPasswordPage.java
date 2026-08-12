package com.webguard.pages;

import org.openqa.selenium.By;

public class ForgotPasswordPage extends BasePage {
    private final By emailField = By.id("email");
    private final By resetBtn = By.id("resetBtn");
    private final By loginLink = By.id("loginLink");
    private final By messageBox = By.id("messageBox");

    public ForgotPasswordPage enterEmail(String email) {
        sendKeys(emailField, email, "Recovery Email Input");
        return this;
    }

    public void clickReset() {
        click(resetBtn, "Send Recovery Email Button");
    }

    public void resetPassword(String email) {
        enterEmail(email);
        clickReset();
    }

    public void clickLoginLink() {
        click(loginLink, "Login Page Redirect Link");
    }

    public String getMessageText() {
        return getText(messageBox);
    }

    public boolean isMessageDisplayed() {
        return isDisplayed(messageBox);
    }
}
