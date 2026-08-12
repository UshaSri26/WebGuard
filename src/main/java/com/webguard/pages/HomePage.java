package com.webguard.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class HomePage extends BasePage {
    private final By welcomeUser = By.id("welcomeUser");
    private final By logoutBtn = By.id("logoutBtn");
    private final By searchField = By.id("searchField");
    private final By searchBtn = By.id("searchBtn");
    private final By noResults = By.id("noResults");
    private final By assetRows = By.cssSelector("#assetsBody tr");

    public String getWelcomeMessage() {
        return getText(welcomeUser);
    }

    public boolean isWelcomeMessageDisplayed() {
        return isDisplayed(welcomeUser);
    }

    public void clickLogout() {
        click(logoutBtn, "Sign Out Button");
    }

    public HomePage enterSearchQuery(String query) {
        sendKeys(searchField, query, "Search Field");
        return this;
    }

    public void clickSearch() {
        click(searchBtn, "Search Button");
    }

    public void search(String query) {
        enterSearchQuery(query);
        clickSearch();
    }

    public int getVisibleRowsCount() {
        List<WebElement> rows = driver.findElements(assetRows);
        int count = 0;
        for (WebElement row : rows) {
            if (row.isDisplayed()) {
                count++;
            }
        }
        System.out.println("Number of visible asset rows in dashboard table: " + count);
        return count;
    }

    public boolean isNoResultsDisplayed() {
        return isDisplayed(noResults);
    }
}
