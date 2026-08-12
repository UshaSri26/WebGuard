package com.webguard.pages;

import org.openqa.selenium.By;

public class LoginPage extends BasePage {
    private final By usernameField = By.id("username");
    private final By passwordField = By.id("password");
    private final By loginButton = By.id("loginBtn");
    private final By forgotPasswordLink = By.id("forgotPasswordLink");
    private final By registerLink = By.id("registerLink");
    private final By messageBox = By.id("messageBox");

    public LoginPage enterUsername(String username) {
        sendKeys(usernameField, username, "Username Input Box");
        return this;
    }

    public LoginPage enterPassword(String password) {
        sendKeys(passwordField, password, "Password Input Box");
        return this;
    }

    public void clickLogin() {
        click(loginButton, "Login Button");
    }

    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }

    public void clickForgotPassword() {
        click(forgotPasswordLink, "Forgot Password Link");
    }

    public void clickRegister() {
        click(registerLink, "Register Account Link");
    }

    public String getMessageText() {
        return getText(messageBox);
    }

    public boolean isMessageDisplayed() {
        return isDisplayed(messageBox);
    }
}
