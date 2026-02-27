package saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CheckoutOverviewPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By finishBtn = By.id("finish");

    public CheckoutCompletePage clickFinish() {
        wait.until(ExpectedConditions.elementToBeClickable(finishBtn)).click();
        return new CheckoutCompletePage(driver);
    }

    private final By title = By.className("title");

    public CheckoutOverviewPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public String getTitleText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(title)).getText();
    }

    public boolean isAt() {
        return getTitleText().equals("Checkout: Overview");
    }
}
