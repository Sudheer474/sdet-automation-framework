package com.sudheer.framework.tests.ui;

import com.sudheer.framework.base.BaseUiTest;
import com.sudheer.framework.pages.CheckoutPage;
import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CheckoutE2ETest extends BaseUiTest {

    @Test(groups = {"smoke", "e2e", "ui"})
    @Description("End-to-end: login, add product, checkout and see order confirmation")
    public void userCanCompleteAnOrder() {
        CheckoutPage checkout = loginPage.loginAs("standard_user", "secret_sauce")
                .addToCart("Sauce Labs Backpack")
                .openCart()
                .checkout()
                .fillInformation("Test", "User", "411001")
                .finishOrder();

        Assert.assertEquals(checkout.confirmationMessage(), "Thank you for your order!");
    }
}
