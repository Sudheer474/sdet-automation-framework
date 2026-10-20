package com.sudheer.framework.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutPage extends BasePage {

    private static final By FIRST_NAME = By.id("first-name");
    private static final By LAST_NAME = By.id("last-name");
    private static final By POSTAL_CODE = By.id("postal-code");
    private static final By CONTINUE = By.id("continue");
    private static final By FINISH = By.id("finish");
    private static final By CONFIRMATION = By.className("complete-header");

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    @Step("Enter shipping details and continue")
    public CheckoutPage fillInformation(String firstName, String lastName, String postalCode) {
        type(FIRST_NAME, firstName);
        type(LAST_NAME, lastName);
        type(POSTAL_CODE, postalCode);
        click(CONTINUE);
        return this;
    }

    @Step("Finish the order")
    public CheckoutPage finishOrder() {
        click(FINISH);
        return this;
    }

    public String confirmationMessage() {
        return textOf(CONFIRMATION);
    }
}
