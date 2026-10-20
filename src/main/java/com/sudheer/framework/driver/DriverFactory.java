package com.sudheer.framework.driver;

import com.sudheer.framework.config.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.Map;

/**
 * Creates one WebDriver per thread so test classes can run in parallel safely.
 * Selenium Manager (built into Selenium 4.6+) downloads the matching browser driver automatically.
 */
public final class DriverFactory {

    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    private DriverFactory() { }

    public static void initDriver() {
        String browser = ConfigReader.get("browser").toLowerCase();
        boolean headless = ConfigReader.getBoolean("headless");
        WebDriver driver;

        if ("firefox".equals(browser)) {
            FirefoxOptions options = new FirefoxOptions();
            if (headless) {
                options.addArguments("-headless");
            }
            driver = new FirefoxDriver(options);
        } else {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--no-sandbox", "--disable-dev-shm-usage", "--window-size=1920,1080");
            if (headless) {
                options.addArguments("--headless=new");
            }
            // Stop Chrome's password manager popups from blocking the page
            options.setExperimentalOption("prefs", Map.of(
                    "credentials_enable_service", false,
                    "profile.password_manager_enabled", false));
            driver = new ChromeDriver(options);
        }
        DRIVER.set(driver);
    }

    public static WebDriver getDriver() {
        return DRIVER.get();
    }

    public static void quitDriver() {
        WebDriver driver = DRIVER.get();
        if (driver != null) {
            driver.quit();
            DRIVER.remove();
        }
    }
}
