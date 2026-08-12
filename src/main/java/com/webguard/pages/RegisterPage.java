package com.webguard.pages;

import org.openqa.selenium.By;

public class RegisterPage extends BasePage {
    private final By fullNameField = By.id("fullName");
    private final By emailField = By.id("email");
    private final By usernameField = By.id("username");
    private final By passwordField = By.id("password");
    private final By confirmPasswordField = By.id("confirmPassword");
    private final By registerBtn = By.id("registerBtn");
    private final By loginLink = By.id("loginLink");
    private final By messageBox = By.id("messageBox");

    public RegisterPage enterFullName(String name) {
        sendKeys(fullNameField, name, "Full Name Input");
        return this;
    }

    public RegisterPage enterEmail(String email) {
        sendKeys(emailField, email, "Email Input");
        return this;
    }

    public RegisterPage enterUsername(String username) {
        sendKeys(usernameField, username, "Username Input");
        return this;
    }

    public RegisterPage enterPassword(String password) {
        sendKeys(passwordField, password, "Password Input");
        return this;
    }

    public RegisterPage enterConfirmPassword(String confirmPassword) {
        sendKeys(confirmPasswordField, confirmPassword, "Confirm Password Input");
        return this;
    }

    public void clickRegister() {
        click(registerBtn, "Register Button");
    }

    public void register(String name, String email, String user, String pass, String confirmPass) {
        enterFullName(name);
        enterEmail(email);
        enterUsername(user);
        enterPassword(pass);
        enterConfirmPassword(confirmPass);
        clickRegister();
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
