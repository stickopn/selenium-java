package saucedemo.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import saucedemo.base.BaseTest;
import saucedemo.pages.LoginPage;

public class InvalidLoginTests extends BaseTest {
    @Test
    public void invalidLoginShowsErrorMessage() {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.typeUsername("standard_user")
                .typePassword("pogresna_sifra")
                .clickLogin();

        Assert.assertTrue(loginPage.hasError(), "Ocekivali smo error box, ali ga nema!");

        String error = loginPage.getErrorText();
        Assert.assertTrue(
                error.toLowerCase().contains("username and password do not match"),
                "Neocekivan error text: " + error
        );

        Assert.assertTrue(getDriver().getCurrentUrl().contains("saucedemo.com"),
                "URL nije sa saucedemo domena");
        Assert.assertFalse(getDriver().getCurrentUrl().contains("inventory"),
                "Ne bi smeli da udjemo na inventory sa pogresnim loginom!");
    }
}
