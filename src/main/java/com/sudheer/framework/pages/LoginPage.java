package com.sudheer.framework.pages;

import com.sudheer.framework.config.ConfigReader;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    private static final By USERNAME = By.id("user-name");
    private static final By PASSWORD = By.id("password");
    private static final By LOGIN_BUTTON = By.id("login-button");
    private static final By ERROR = By.cssSelector("[data-test='error']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @Step("Open the login page")
    public LoginPage open() {
        driver.get(ConfigReader.get("baseUrl"));
        return this;
    }

    @Step("Log in as {username}")
    public InventoryPage loginAs(String username, String password) {
        attemptLogin(username, password);
        return new InventoryPage(driver);
    }

    @Step("Attempt login as {username}")
    public LoginPage attemptLogin(String username, String password) {
        type(USERNAME, username);
        type(PASSWORD, password);
        click(LOGIN_BUTTON);
        return this;
    }

    public String errorMessage() {
        return textOf(ERROR);
    }
}
