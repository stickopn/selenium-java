package saucedemo.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import saucedemo.base.BaseTest;
import saucedemo.pages.LoginPage;
import saucedemo.pages.ProductsPage;

public class LogoutTests extends BaseTest {
    @Test
    public void logoutTest() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.typeUsername("standard_user")
                .typePassword("secret_sauce")
                .clickLogin();

        ProductsPage productsPage = new ProductsPage(driver);
        productsPage.openMenu().clickLogout();

        Assert.assertTrue(driver.getCurrentUrl().contains("saucedemo.com/"));
    }
}
