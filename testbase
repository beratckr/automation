package com.cybertek.tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.cybertek.pages.LoginPage;
import com.cybertek.utilities.BrowserUtils;
import com.cybertek.utilities.ConfigurationReader;
import com.cybertek.utilities.Driver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class TestBase {

    protected WebDriver driver;
    protected Actions actions;
    protected WebDriverWait wait;


    protected ExtentReports report;
    protected ExtentHtmlReporter htmlReporter;
    protected ExtentTest extentLogger;


    @BeforeTest
    public void setUpTest() {
        report = new ExtentReports();

        String filePath = System.getProperty("user.dir") + "/test-output/report.html";
        htmlReporter = new ExtentHtmlReporter(filePath);
        report.attachReporter(htmlReporter);

        htmlReporter.config().setReportName("Vytrack automated test reports");

        report.setSystemInfo("Environment", "QA3");
        report.setSystemInfo("OS", System.getProperty("os.name"));
        report.setSystemInfo("Browser", ConfigurationReader.get("browser"));
    }

    @AfterTest
    public void tearDownTest() {
        report.flush();
    }


    @BeforeMethod
    public void setUpMethod() throws InterruptedException {
        // initilializes the webdriver object in test base class using the Driver utility
        driver = Driver.get();

        // setting implicit wait --> when elements not found, it will keep trying to find it for 10 seconds
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        // set up the explicit wait object.
        wait = new WebDriverWait(driver, 10);

        // Actions class enable advanced interactions like double click, drag drop ...
        actions = new Actions(driver);

        // initilializes the webdriver object in test base class using the Driver utility
        driver = Driver.get();

        // setting implicit wait --> when elements not found, it will keep trying to find it for 10 seconds
//        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        // set up the explicit wait object.
        wait = new WebDriverWait(driver, 10);

        // Actions class enable advanced interactions like double click, drag drop ...
        actions = new Actions(driver);


    }

    @AfterMethod
    public void tearDownMethod(ITestResult result) throws InterruptedException, IOException {
        // if the test failed
        if (result.getStatus() == ITestResult.FAILURE) {
            // record the failed test
            extentLogger.fail(result.getName());
            // take screen shot and add to report0
            String screenshotLocation = BrowserUtils.getScreenshot(result.getName());
            extentLogger.addScreenCaptureFromPath(screenshotLocation);
            // capture the exception
            extentLogger.fail(result.getThrowable());
        } else if (result.getStatus() == ITestResult.SKIP) {
            extentLogger.skip("Test case skipper: " + result.getName());
        }

//        Thread.sleep(4000);
        Driver.closeDriver();
    }


}
