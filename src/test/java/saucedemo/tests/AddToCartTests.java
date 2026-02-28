package saucedemo.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import saucedemo.base.BaseTest;
import saucedemo.pages.CartPage;
import saucedemo.pages.LoginPage;
import saucedemo.pages.ProductsPage;

public class AddToCartTests extends BaseTest {
    @Test
    public void addOneItemToCart() throws InterruptedException {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.typeUsername("standard_user")
                .typePassword("secret_sauce")
                .clickLogin();

        ProductsPage productsPage = new ProductsPage(getDriver());
        productsPage.addBackpackToCart();

        Assert.assertEquals(productsPage.getCartBadgeCount(), 1, "Badge count nije 1!");

        CartPage cartPage = productsPage.openCart();
        Assert.assertTrue(cartPage.hasItemNamed("Sauce Labs Backpack"), "Backpack nije u korpi!");

//        Provera za paralelno izvrsavanje testova
        Thread.sleep(2000);
        System.out.println("Thread ID: " + Thread.currentThread().getId());
    }
}
