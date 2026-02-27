package saucedemo.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import saucedemo.base.BaseTest;
import saucedemo.pages.LoginPage;
import saucedemo.pages.ProductsPage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SortTests extends BaseTest {
    @Test(priority = 1)
    public void sortByNameAZ() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.typeUsername("standard_user")
                .typePassword("secret_sauce")
                .clickLogin();

        ProductsPage productsPage = new ProductsPage(driver);
        Assert.assertTrue(productsPage.isAt(), "Nismo na Products stranici posle logina!");

        productsPage.selectSortByValue("az");

        List<String> actual = productsPage.getAllProductNames();
        List<String> expected = new ArrayList<>(actual);
        Collections.sort(expected);

        Assert.assertEquals(actual, expected, "Sort A->Z nije dobar!");
    }

    @Test(priority = 2)
    public void sortByPriceLowToHigh() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.typeUsername("standard_user")
                .typePassword("secret_sauce")
                .clickLogin();

        ProductsPage productsPage = new ProductsPage(driver);
        Assert.assertTrue(productsPage.isAt(), "Nismo na Products stranici posle logina!");

        productsPage.selectSortByValue("lohi");

        List<Double> actual = productsPage.getAllProductPrices();
        List<Double> expected = new ArrayList<>(actual);
        Collections.sort(expected);

        Assert.assertEquals(actual, expected, "Sort price low->high nije dobar!");
    }
}
