package saucedemo.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import saucedemo.base.BaseTest;
import saucedemo.pages.LoginPage;

public class LoginTests extends BaseTest {
    @Test
    public void loginTest() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.typeUsername("standard_user")
                .typePassword("secret_sauce")
                .clickLogin();

        Assert.assertTrue(driver.getCurrentUrl().contains("inventory"));
    }
}
