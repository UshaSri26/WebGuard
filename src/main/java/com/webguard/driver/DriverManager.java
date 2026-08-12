package com.webguard.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import java.time.Duration;

public class DriverManager {
    private static final ThreadLocal<WebDriver> dr = new ThreadLocal<>();

    public static WebDriver getDriver() {
        return dr.get();
    }

    public static void setDriver(WebDriver driver) {
        dr.set(driver);
    }

    public static void quitDriver() {
        if (getDriver() != null) {
            getDriver().quit();
            dr.remove();
        }
    }

    /**
     * Initializes the driver for the given browser.
     * Selenium 4 automatically resolves driver binaries using Selenium Manager.
     */
    public static void initDriver(String browser) {
        if (getDriver() == null) {
            WebDriver driver;
            String browserLower = browser.toLowerCase();
            
            if (browserLower.equals("chrome")) {
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--remote-allow-origins=*");
                // Run in headless if needed, but let's run normally so user can see it if run locally (headless is optional)
                // options.addArguments("--headless=new");
                driver = new ChromeDriver(options);
            } else if (browserLower.equals("edge")) {
                EdgeOptions options = new EdgeOptions();
                options.addArguments("--remote-allow-origins=*");
                driver = new EdgeDriver(options);
            } else {
                throw new IllegalArgumentException("Unsupported browser: " + browser);
            }

            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
            setDriver(driver);
        }
    }
}
