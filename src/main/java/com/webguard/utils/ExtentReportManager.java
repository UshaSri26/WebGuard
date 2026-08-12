package com.webguard.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.File;

public class ExtentReportManager {
    private static ExtentReports extent;

    public static synchronized ExtentReports getReportInstance() {
        if (extent == null) {
            String reportPath = System.getProperty("user.dir") + File.separator + "reports" + File.separator + "ExtentReport.html";
            File reportDir = new File(System.getProperty("user.dir") + File.separator + "reports");
            if (!reportDir.exists()) {
                reportDir.mkdirs();
            }

            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
            sparkReporter.config().setTheme(Theme.DARK);
            sparkReporter.config().setDocumentTitle("WebGuard Test Execution Report");
            sparkReporter.config().setReportName("WebGuard Automation Suite Results");
            sparkReporter.config().setTimeStampFormat("yyyy-MM-dd HH:mm:ss");

            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);
            
            // Set System metadata info
            extent.setSystemInfo("Application", "WebGuard AUT");
            extent.setSystemInfo("Operating System", System.getProperty("os.name"));
            extent.setSystemInfo("Java Version", System.getProperty("java.version"));
            extent.setSystemInfo("Environment", "QA Local Sandbox");
            extent.setSystemInfo("Framework", "Page Object Model (POM)");
        }
        return extent;
    }
}
