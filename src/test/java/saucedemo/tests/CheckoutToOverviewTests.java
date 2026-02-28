package saucedemo.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import saucedemo.base.BaseTest;
import saucedemo.pages.*;

public class CheckoutToOverviewTests extends BaseTest {
    @Test
    public void checkoutToOverview() {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.typeUsername("standard_user")
                .typePassword("secret_sauce")
                .clickLogin();

        ProductsPage productsPage = new ProductsPage(getDriver());
        productsPage.addBackpackToCart();

        CartPage cartPage = productsPage.openCart();

        CheckoutInfoPage infoPage = cartPage.clickCheckout();

        CheckoutOverviewPage overview =
                infoPage.typeFirstName("Pera")
                        .typeLastName("Peric")
                        .typePostalCode("11000")
                        .continueToOverview();

        Assert.assertTrue(overview.isAt());

        System.out.println("URL: " + getDriver().getCurrentUrl());

        if (infoPage.hasError()) {
            System.out.println("ERROR: " + infoPage.getErrorText());
        }

        System.out.println("URL: " + getDriver().getCurrentUrl());
        System.out.println("TITLE: " + overview.getTitleText());
        Assert.assertTrue(overview.isAt(), "Nismo na Checkout: Overview stranici!");
    }
}
