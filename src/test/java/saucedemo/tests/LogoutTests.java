package saucedemo.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import saucedemo.base.BaseTest;
import saucedemo.pages.LoginPage;
import saucedemo.pages.ProductsPage;

public class LogoutTests extends BaseTest {
    @Test
    public void logoutTest() {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.typeUsername("standard_user")
                .typePassword("secret_sauce")
                .clickLogin();

        ProductsPage productsPage = new ProductsPage(getDriver());
        productsPage.openMenu().clickLogout();

        Assert.assertTrue(getDriver().getCurrentUrl().contains("saucedemo.com/"));
    }
}
