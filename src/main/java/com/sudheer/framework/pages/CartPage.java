package com.sudheer.framework.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CartPage extends BasePage {

    private static final By ITEM_NAMES = By.cssSelector(".cart_item .inventory_item_name");
    private static final By CHECKOUT = By.id("checkout");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public List<String> itemNames() {
        visible(By.className("cart_list"));
        return driver.findElements(ITEM_NAMES).stream().map(WebElement::getText).toList();
    }

    @Step("Proceed to checkout")
    public CheckoutPage checkout() {
        click(CHECKOUT);
        return new CheckoutPage(driver);
    }
}
