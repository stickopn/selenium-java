package saucedemo.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import saucedemo.base.BaseTest;
import saucedemo.pages.CartPage;
import saucedemo.pages.LoginPage;
import saucedemo.pages.ProductsPage;

public class RemoveFromCartTests extends BaseTest {
    @Test(priority = 1)
    public void removeItemFromCart() {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.typeUsername("standard_user")
                .typePassword("secret_sauce")
                .clickLogin();

        ProductsPage productsPage = new ProductsPage(getDriver());
        productsPage.addBackpackToCart();
        Assert.assertEquals(productsPage.getCartBadgeCount(), 1);

        productsPage.removeBackpackFromCart();

        Assert.assertFalse(productsPage.isCartBadgeVisible(), "Badge i dalje postoji nakon remove!");

        CartPage cartPage = productsPage.openCart();
        Assert.assertTrue(cartPage.isCartEmpty(), "Korpa nije prazna nakon remove!");
    }
}
