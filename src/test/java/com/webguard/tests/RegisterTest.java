package com.webguard.tests;

import com.webguard.pages.LoginPage;
import com.webguard.pages.RegisterPage;
import com.webguard.pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RegisterTest extends BaseTest {

    @Test(description = "Verify successful user registration and subsequent login")
    public void testSuccessfulRegistration() {
        openUrl(getProperty("registerUrl"));
        
        RegisterPage registerPage = new RegisterPage();
        String uniqueUser = "user_" + System.currentTimeMillis();
        
        // Complete registration form
        registerPage.register("Automation Test User", "testuser@webguard.org", uniqueUser, "superSecure99", "superSecure99");
        
        // Upon successful registration, index.html page is loaded with success banner
        LoginPage loginPage = new LoginPage();
        Assert.assertTrue(loginPage.getCurrentPageUrl().contains("index.html"), "Redirect to login page expected.");
        Assert.assertTrue(loginPage.isMessageDisplayed(), "Registration success banner should display.");
        Assert.assertTrue(loginPage.getMessageText().contains("successful"), "Success message mismatch.");

        // Login with new registered account
        loginPage.login(uniqueUser, "superSecure99");
        HomePage homePage = new HomePage();
        Assert.assertTrue(homePage.isWelcomeMessageDisplayed(), "Should successfully access dashboard.");
        Assert.assertTrue(homePage.getWelcomeMessage().contains(uniqueUser), "Dashboard username greeting mismatch.");
    }

    @Test(description = "Verify registration field validation checks")
    public void testRegistrationValidations() {
        openUrl(getProperty("registerUrl"));
        
        RegisterPage registerPage = new RegisterPage();

        // 1. Empty field checks
        registerPage.register("", "", "", "", "");
        Assert.assertEquals(registerPage.getMessageText(), "All fields are required");

        // 2. Password mismatch checks
        registerPage.register("Name", "test@mail.com", "username123", "passwordA", "passwordB");
        Assert.assertEquals(registerPage.getMessageText(), "Passwords do not match");

        // 3. Password length checks
        registerPage.register("Name", "test@mail.com", "username123", "123", "123");
        Assert.assertEquals(registerPage.getMessageText(), "Password must be at least 6 characters long");

        // 4. Duplicate username check (registering 'admin' which is already predefined)
        registerPage.register("Admin Admin", "admin@webguard.org", "admin", "admin12345", "admin12345");
        Assert.assertEquals(registerPage.getMessageText(), "Username is already taken");
    }
}
