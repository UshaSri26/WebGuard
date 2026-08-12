package com.webguard.tests;

import com.webguard.pages.LoginPage;
import com.webguard.pages.HomePage;
import com.webguard.utils.ExcelReader;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @DataProvider(name = "loginData")
    public Object[][] getLoginData() {
        String filePath = "src/test/resources/testdata.xlsx";
        String sheetName = "LoginData";
        return ExcelReader.getTestData(filePath, sheetName);
    }

    @Test(dataProvider = "loginData", description = "Data-driven login test using Excel data provider")
    public void testLoginDataDriven(String username, String password, String expectedStatus) {
        openUrl(getProperty("baseUrl"));
        
        LoginPage loginPage = new LoginPage();
        loginPage.login(username, password);

        if (expectedStatus.equalsIgnoreCase("success")) {
            HomePage homePage = new HomePage();
            Assert.assertTrue(homePage.isWelcomeMessageDisplayed(), "Dashboard welcome message should be displayed.");
            Assert.assertTrue(homePage.getWelcomeMessage().contains(username), 
                    "Welcome message should contain username: " + username);
            
            // Clean up session by logging out
            homePage.clickLogout();
            Assert.assertTrue(loginPage.getCurrentPageUrl().contains("index.html"), 
                    "Should redirect back to login page after logout.");
        } else {
            Assert.assertTrue(loginPage.isMessageDisplayed(), "Error message should be displayed.");
            String message = loginPage.getMessageText();
            Assert.assertTrue(message.contains("Invalid") || message.contains("required"), 
                    "Error message should indicate invalid credentials or missing inputs.");
        }
    }

    @Test(description = "Verify validation errors for empty username and password")
    public void testLoginFieldValidations() {
        openUrl(getProperty("baseUrl"));
        
        LoginPage loginPage = new LoginPage();
        
        // Case 1: Empty username
        loginPage.login("", "somepass");
        Assert.assertEquals(loginPage.getMessageText(), "Username is required");

        // Case 2: Empty password
        loginPage.login("admin", "");
        Assert.assertEquals(loginPage.getMessageText(), "Password is required");
    }
}
