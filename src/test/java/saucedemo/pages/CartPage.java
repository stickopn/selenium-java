package saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage {
    private final WebDriver driver;

    public CartPage(WebDriver driver) {
        this.driver = driver;
    }

    private final By cartItemNames = By.className("inventory_item_name");

    public boolean hasItemNamed(String name) {
        return driver.findElements(cartItemNames)
                .stream()
                .anyMatch(e -> e.getText().trim().equals(name));
    }

    private final By cartItems = By.className("cart_item");

    public boolean isCartEmpty() {
        return driver.findElements(cartItems).isEmpty();
    }

    private final By checkoutBtn = By.id("checkout");

    public CheckoutInfoPage clickCheckout() {
        driver.findElement(checkoutBtn).click();
        return new CheckoutInfoPage(driver);
    }
}
