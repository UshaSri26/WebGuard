package com.webguard.tests;

import com.webguard.pages.LoginPage;
import com.webguard.pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SearchTest extends BaseTest {

    @Test(description = "Verify real-time filtering search functionality on dashboard table")
    public void testDashboardAssetSearch() {
        openUrl(getProperty("baseUrl"));
        
        LoginPage loginPage = new LoginPage();
        loginPage.login("admin", "admin123");
        
        HomePage homePage = new HomePage();
        Assert.assertTrue(homePage.isWelcomeMessageDisplayed(), "Dashboard should be accessed successfully.");

        // Case 1: Initial load should show all 6 security assets
        Assert.assertEquals(homePage.getVisibleRowsCount(), 6, "Initially 6 assets should be displayed.");

        // Case 2: Search specific name (Endpoint Shield)
        homePage.search("Endpoint");
        Assert.assertEquals(homePage.getVisibleRowsCount(), 1, "Only 1 matching row should display.");
        Assert.assertFalse(homePage.isNoResultsDisplayed(), "No-results banner should not display.");

        // Case 3: Search common status (Active)
        homePage.search("Active");
        Assert.assertEquals(homePage.getVisibleRowsCount(), 4, "4 active status assets should display.");

        // Case 4: Search nonexistent query
        homePage.search("InvalidSecureAsset");
        Assert.assertEquals(homePage.getVisibleRowsCount(), 0, "No asset rows should display.");
        Assert.assertTrue(homePage.isNoResultsDisplayed(), "No-results banner should display.");

        // Case 5: Clear search and return all assets
        homePage.search("");
        Assert.assertEquals(homePage.getVisibleRowsCount(), 6, "All 6 assets should show again.");
    }
}
