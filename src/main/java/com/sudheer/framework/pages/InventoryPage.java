package com.sudheer.framework.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

public class InventoryPage extends BasePage {

    private static final By TITLE = By.className("title");
    private static final By CART_LINK = By.className("shopping_cart_link");
    private static final By CART_BADGE = By.className("shopping_cart_badge");

    public InventoryPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        return "Products".equals(textOf(TITLE));
    }

    @Step("Add '{productName}' to cart")
    public InventoryPage addToCart(String productName) {
        By addButton = By.xpath(
                "//div[@class='inventory_item']"
                + "[.//div[contains(@class,'inventory_item_name') and normalize-space()='" + productName + "']]"
                + "//button");
        click(addButton);
        return this;
    }

    public int cartCount() {
        try {
            return Integer.parseInt(driver.findElement(CART_BADGE).getText());
        } catch (NoSuchElementException e) {
            return 0;
        }
    }

    @Step("Open the cart")
    public CartPage openCart() {
        click(CART_LINK);
        return new CartPage(driver);
    }
}
