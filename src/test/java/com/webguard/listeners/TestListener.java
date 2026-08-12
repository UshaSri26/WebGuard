package com.webguard.listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.webguard.driver.DriverManager;
import com.webguard.utils.ExtentReportManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {
    private static final ExtentReports extent = ExtentReportManager.getReportInstance();
    private static final ThreadLocal<ExtentTest> testThread = new ThreadLocal<>();

    @Override
    public synchronized void onStart(ITestContext context) {
        System.out.println("Test Suite started: " + context.getName());
    }

    @Override
    public synchronized void onFinish(ITestContext context) {
        System.out.println("Test Suite completed: " + context.getName());
        extent.flush();
    }

    @Override
    public synchronized void onTestStart(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        System.out.println("Test Started: " + testName);
        ExtentTest test = extent.createTest(testName, result.getMethod().getDescription());
        for (String group : result.getMethod().getGroups()) {
            test.assignCategory(group);
        }
        testThread.set(test);
    }

    @Override
    public synchronized void onTestSuccess(ITestResult result) {
        System.out.println("Test Passed: " + result.getMethod().getMethodName());
        testThread.get().log(Status.PASS, "Test passed successfully.");
    }

    @Override
    public synchronized void onTestFailure(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        System.err.println("Test Failed: " + testName);
        
        ExtentTest test = testThread.get();
        test.log(Status.FAIL, "Test failed: " + result.getThrowable());

        WebDriver driver = DriverManager.getDriver();
        if (driver != null) {
            try {
                String base64Screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
                test.fail("Screenshot on failure:", 
                        MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());
                System.out.println("Screenshot attached to report for failed test: " + testName);
            } catch (Exception e) {
                System.err.println("Failed to capture and attach screenshot.");
                e.printStackTrace();
            }
        }
    }

    @Override
    public synchronized void onTestSkipped(ITestResult result) {
        System.out.println("Test Skipped: " + result.getMethod().getMethodName());
        testThread.get().log(Status.SKIP, "Test skipped: " + result.getThrowable());
    }

    @Override
    public synchronized void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // Not used
    }
}
