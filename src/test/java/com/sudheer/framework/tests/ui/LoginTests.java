package com.sudheer.framework.tests.ui;

import com.sudheer.framework.base.BaseUiTest;
import com.sudheer.framework.pages.InventoryPage;
import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LoginTests extends BaseUiTest {

    @DataProvider(name = "invalidLogins")
    public Object[][] invalidLogins() {
        return new Object[][] {
                {"locked_out_user", "secret_sauce", "locked out"},
                {"standard_user", "wrong_password", "do not match"},
                {"", "secret_sauce", "Username is required"},
                {"standard_user", "", "Password is required"}
        };
    }

    @Test(groups = {"smoke", "ui"})
    @Description("A valid user lands on the products page")
    public void validUserCanLogin() {
        InventoryPage inventory = loginPage.loginAs("standard_user", "secret_sauce");
        Assert.assertTrue(inventory.isLoaded(), "Products page should be displayed after login");
    }

    @Test(dataProvider = "invalidLogins", groups = {"regression", "ui"})
    @Description("Invalid credentials show the expected error message")
    public void invalidLoginShowsError(String username, String password, String expectedFragment) {
        String error = loginPage.attemptLogin(username, password).errorMessage();
        Assert.assertTrue(error.contains(expectedFragment),
                "Expected error to contain '" + expectedFragment + "' but was: " + error);
    }
}
