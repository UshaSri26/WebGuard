package com.webguard.tests;

import com.webguard.pages.ForgotPasswordPage;
import com.webguard.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ForgotPasswordTest extends BaseTest {

    @Test(description = "Verify password recovery workflow with valid and invalid email inputs")
    public void testForgotPasswordRecovery() {
        openUrl(getProperty("forgotUrl"));
        
        ForgotPasswordPage forgotPage = new ForgotPasswordPage();

        // Case 1: Empty email address submission
        forgotPage.resetPassword("");
        Assert.assertEquals(forgotPage.getMessageText(), "Email address is required");

        // Case 2: Invalid email address format submission (no '@' sign)
        forgotPage.resetPassword("invalidemail");
        Assert.assertEquals(forgotPage.getMessageText(), "Please enter a valid email address");

        // Case 3: Valid email address submission
        forgotPage.resetPassword("user@webguard.org");
        Assert.assertTrue(forgotPage.getMessageText().contains("Recovery link sent"), "Success message mismatch.");

        // Case 4: Back to Login link
        forgotPage.clickLoginLink();
        LoginPage loginPage = new LoginPage();
        Assert.assertTrue(loginPage.getCurrentPageUrl().contains("index.html"), "Redirect back to login page failed.");
    }
}
