package saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class ProductsPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By burgerMenuButton = By.id("react-burger-menu-btn");
    private final By logoutLink = By.id("logout_sidebar_link");
    private final By menuPanel = By.cssSelector(".bm-menu-wrap");

    private final By sortDropdown = By.className("product_sort_container");
    private final By inventoryItemNames = By.className("inventory_item_name");
    private final By inventoryItemPrices = By.className("inventory_item_price");

    private final By addBackpackBtn = By.id("add-to-cart-sauce-labs-backpack");
    private final By cartBadge = By.className("shopping_cart_badge");
    private final By cartLink = By.className("shopping_cart_link");

    public ProductsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public ProductsPage openMenu() {
        wait.until(ExpectedConditions.elementToBeClickable(burgerMenuButton)).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(menuPanel));
        return this;
    }

    public void clickLogout() {
        WebElement logout = wait.until(ExpectedConditions.elementToBeClickable(logoutLink));
        logout.click();
    }

    public ProductsPage addBackpackToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(addBackpackBtn)).click();
        return this;
    }

    public int getCartBadgeCount() {
        // ako nema badge-a, znači 0
        if (driver.findElements(cartBadge).isEmpty()) return 0;
        return Integer.parseInt(driver.findElement(cartBadge).getText().trim());
    }

    public CartPage openCart() {
        wait.until(ExpectedConditions.elementToBeClickable(cartLink)).click();
        return new CartPage(driver);
    }

    private final By removeBackpackBtn = By.id("remove-sauce-labs-backpack");

    public ProductsPage removeBackpackFromCart() {
        wait.until(ExpectedConditions.elementToBeClickable(removeBackpackBtn)).click();
        return this;
    }

    public boolean isCartBadgeVisible() {
        return !driver.findElements(cartBadge).isEmpty();
    }


    public boolean isAt() {
        return driver.getCurrentUrl().contains("inventory");
    }

    public ProductsPage selectSortByValue(String value) {
        WebElement dd = wait.until(ExpectedConditions.elementToBeClickable(sortDropdown));
        new org.openqa.selenium.support.ui.Select(dd).selectByValue(value);
        return this;
    }

    public List<String> getAllProductNames() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(inventoryItemNames));
        List<WebElement> els = driver.findElements(inventoryItemNames);
        List<String> names = new ArrayList<>();
        for (WebElement e : els) names.add(e.getText().trim());
        return names;
    }

    public List<Double> getAllProductPrices() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(inventoryItemPrices));
        List<WebElement> els = driver.findElements(inventoryItemPrices);
        List<Double> prices = new ArrayList<>();
        for (WebElement e : els) {
            prices.add(Double.parseDouble(e.getText().replace("$", "").trim()));
        }
        return prices;
    }
}
