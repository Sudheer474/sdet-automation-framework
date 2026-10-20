package com.sudheer.framework.base;

import com.sudheer.framework.driver.DriverFactory;
import com.sudheer.framework.pages.LoginPage;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public abstract class BaseUiTest {

    protected LoginPage loginPage;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        DriverFactory.initDriver();
        loginPage = new LoginPage(DriverFactory.getDriver()).open();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}
