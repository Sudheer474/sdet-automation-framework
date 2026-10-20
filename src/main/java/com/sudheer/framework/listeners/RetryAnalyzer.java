package com.sudheer.framework.listeners;

import com.sudheer.framework.config.ConfigReader;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

/**
 * Re-runs a failed test up to retryCount times (config.properties).
 * Retries are visible in the Allure report, so genuinely flaky tests can be found and fixed rather than hidden.
 */
public class RetryAnalyzer implements IRetryAnalyzer {

    private int attempts = 0;

    @Override
    public boolean retry(ITestResult result) {
        if (attempts < ConfigReader.getInt("retryCount")) {
            attempts++;
            return true;
        }
        return false;
    }
}
