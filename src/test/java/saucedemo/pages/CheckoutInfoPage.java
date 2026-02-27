package saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CheckoutInfoPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By firstName = By.id("first-name");
    private final By lastName  = By.id("last-name");
    private final By postal    = By.id("postal-code");
    private final By continueBtn = By.id("continue");

    private final By errorBanner = By.cssSelector("[data-test='error']");

    public CheckoutInfoPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private void typeStable(By locator, String text) {
        for (int i = 0; i < 3; i++) {
            WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

            el.click();
            el.sendKeys(Keys.chord(Keys.CONTROL, "a"));
            el.sendKeys(Keys.BACK_SPACE);
            el.sendKeys(text);

            try {
                wait.until(d -> text.equals(d.findElement(locator).getAttribute("value")));
                return; // uspeh
            } catch (org.openqa.selenium.TimeoutException ignored) {
                // probaj opet
            }
        }
        throw new AssertionError("Nije uspeo stabilan unos za locator: " + locator + " text=" + text);
    }


    public CheckoutInfoPage typeFirstName(String value) {
        typeStable(firstName, value);
        return this;
    }

    public CheckoutInfoPage typeLastName(String value) {
        typeStable(lastName, value);
        return this;
    }

    public CheckoutInfoPage typePostalCode(String value) {
        typeStable(postal, value);
        return this;
    }

    public CheckoutInfoPage clearFirstName() {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(firstName));
        el.clear();
        return this;
    }

    public CheckoutOverviewPage continueToOverview() {
        wait.until(ExpectedConditions.elementToBeClickable(continueBtn)).click();
        wait.until(ExpectedConditions.urlContains("checkout-step-two"));
        return new CheckoutOverviewPage(driver);
    }

    public CheckoutInfoPage clickContinueExpectError() {
        wait.until(ExpectedConditions.elementToBeClickable(continueBtn)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(errorBanner));

        return this;
    }

    public CheckoutOverviewPage clickContinue() {
        wait.until(ExpectedConditions.elementToBeClickable(continueBtn)).click();
        return new CheckoutOverviewPage(driver);
    }

    public boolean hasError() {
        return driver.findElements(errorBanner).size() > 0;
    }

    public String getErrorText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(errorBanner))
                .getText()
                .trim();
    }
}
