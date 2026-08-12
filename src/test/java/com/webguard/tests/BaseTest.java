package com.webguard.tests;

import com.webguard.driver.DriverManager;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class BaseTest {
    protected static Properties config;

    static {
        config = new Properties();
        try (FileInputStream fis = new FileInputStream("src/main/resources/config.properties")) {
            config.load(fis);
            System.out.println("Configuration properties loaded successfully.");
        } catch (IOException e) {
            System.err.println("Could not load config.properties. Using system defaults.");
            e.printStackTrace();
        }
    }

    public static String getProperty(String key) {
        return config.getProperty(key);
    }

    @BeforeMethod
    @Parameters("browser")
    public void setUp(@Optional String browser) {
        // If browser parameter is not supplied via TestNG suite XML, read from config.properties
        if (browser == null || browser.trim().isEmpty()) {
            browser = getProperty("browser");
            if (browser == null) {
                browser = "chrome"; // Fallback default
            }
        }
        
        System.out.println("Initializing Web Driver for browser: " + browser);
        DriverManager.initDriver(browser);
    }

    @AfterMethod
    public void tearDown() {
        System.out.println("Quitting Web Driver session.");
        DriverManager.quitDriver();
    }

    protected WebDriver getDriver() {
        return DriverManager.getDriver();
    }

    protected void openUrl(String url) {
        getDriver().get(url);
        System.out.println("Navigated to page: " + url);
    }
}
