package com.sudheer.framework.listeners;

import com.sudheer.framework.driver.DriverFactory;
import io.qameta.allure.Allure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.ByteArrayInputStream;

/** Logs test lifecycle and attaches a screenshot to the Allure report when a UI test fails. */
public class TestListener implements ITestListener {

    private static final Logger LOG = LogManager.getLogger(TestListener.class);

    @Override
    public void onTestStart(ITestResult result) {
        LOG.info("START  {}", result.getMethod().getQualifiedName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        LOG.info("PASS   {}", result.getMethod().getQualifiedName());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        LOG.warn("SKIP   {}", result.getMethod().getQualifiedName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        LOG.error("FAIL   {} - {}", result.getMethod().getQualifiedName(), result.getThrowable().getMessage());
        WebDriver driver = DriverFactory.getDriver();
        if (driver instanceof TakesScreenshot screenshotter) {
            byte[] png = screenshotter.getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment("Failure screenshot", "image/png", new ByteArrayInputStream(png), "png");
        }
    }
}
