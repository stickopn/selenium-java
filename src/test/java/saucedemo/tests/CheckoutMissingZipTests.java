package saucedemo.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import saucedemo.base.BaseTest;
import saucedemo.pages.*;

public class CheckoutMissingZipTests extends BaseTest {
    @Test(priority = 1)
    public void checkoutWithoutZipShowsError() {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.typeUsername("standard_user")
                .typePassword("secret_sauce")
                .clickLogin();


        ProductsPage productsPage = new ProductsPage(getDriver());
        productsPage.addBackpackToCart();
        CartPage cartPage = productsPage.openCart();


        CheckoutInfoPage infoPage = cartPage.clickCheckout();

        infoPage.typeFirstName("Pera")
                .typeLastName("Peric")
                // infoPage.typePostalCode("")
                .clickContinueExpectError();

        Assert.assertTrue(infoPage.hasError(), "Očekujem error banner kad fali ZIP!");
        Assert.assertTrue(getDriver().getCurrentUrl().contains("checkout-step-one"),
                "Treba da ostanemo na checkout-step-one kad fali ZIP");

        String error = infoPage.getErrorText();
        Assert.assertTrue(
                error.toLowerCase().contains("postal code") || error.toLowerCase().contains("zip"),
                "Neocekivan error text: " + error
        );

        Assert.assertTrue(
                getDriver().getCurrentUrl().contains("checkout-step-one"),
                "Trebalo bi da ostanemo na checkout-step-one kad fali ZIP!"
        );
    }

    @Test(priority = 2)
    public void checkoutWithoutFirstNameShowsError() {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.typeUsername("standard_user")
                .typePassword("secret_sauce")
                .clickLogin();

        ProductsPage productsPage = new ProductsPage(getDriver());
        productsPage.addBackpackToCart();

        CartPage cartPage = productsPage.openCart();
        CheckoutInfoPage infoPage = cartPage.clickCheckout();

        infoPage
                // namerno preskacemo first name
                .typeLastName("Peric")
                .typePostalCode("11000")
                .clickContinueExpectError(); // ostaje na step-one

        Assert.assertTrue(infoPage.hasError(), "Ocekivao sam error banner, ali ga nema.");
        Assert.assertTrue(infoPage.getErrorText().contains("First Name is required"),
                "Pogresan error tekst: " + infoPage.getErrorText());

        Assert.assertTrue(getDriver().getCurrentUrl().contains("checkout-step-one"),
                "Trebalo bi da ostanemo na checkout-step-one.");
    }

    @Test(priority = 3)
    public void checkoutFinishShowsThankYou() {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.typeUsername("standard_user")
                .typePassword("secret_sauce")
                .clickLogin();

        ProductsPage productsPage = new ProductsPage(getDriver());
        productsPage.addBackpackToCart();

        CartPage cartPage = productsPage.openCart();
        CheckoutInfoPage infoPage = cartPage.clickCheckout();

        CheckoutOverviewPage overviewPage = infoPage
                .typeFirstName("Pera")
                .typeLastName("Peric")
                .typePostalCode("11000")
                .clickContinue();

        Assert.assertTrue(overviewPage.isAt(), "Nismo na Checkout Overview strani.");

        CheckoutCompletePage completePage = overviewPage.clickFinish();
        Assert.assertTrue(completePage.isAt(), "Nismo na checkout complete strani.");
        Assert.assertTrue(completePage.getHeaderText().toUpperCase().contains("THANK YOU"),
                "Nema 'Thank you' poruke. Header: " + completePage.getHeaderText());
    }
}
