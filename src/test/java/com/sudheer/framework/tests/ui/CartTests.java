package com.sudheer.framework.tests.ui;

import com.sudheer.framework.base.BaseUiTest;
import com.sudheer.framework.pages.CartPage;
import com.sudheer.framework.pages.InventoryPage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class CartTests extends BaseUiTest {

    @Test(groups = {"regression", "ui"})
    public void addedItemsAppearInCartAndBadge() {
        InventoryPage inventory = loginPage.loginAs("standard_user", "secret_sauce")
                .addToCart("Sauce Labs Backpack")
                .addToCart("Sauce Labs Bike Light");

        Assert.assertEquals(inventory.cartCount(), 2, "Cart badge should show 2 items");

        CartPage cart = inventory.openCart();
        Assert.assertEquals(cart.itemNames(),
                List.of("Sauce Labs Backpack", "Sauce Labs Bike Light"));
    }
}
