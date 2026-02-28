package saucedemo.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseTest {

    private static final ThreadLocal<WebDriver> driverTL = new ThreadLocal<>();

    protected WebDriver getDriver() {
        return driverTL.get();
    }

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();

        // stabilnije okruženje (može ostati)
        options.addArguments("--incognito");
        options.addArguments("--disable-notifications");

        // ✅ CI/headless safe
        // (radi i lokalno, ali ako želiš da lokalno vidiš browser, kasnije ćemo dodati toggle)
        boolean headless = Boolean.parseBoolean(System.getProperty("headless", "false"));

        if (headless) {
            options.addArguments("--headless=new");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--window-size=1920,1080");
        } else {
            options.addArguments("--start-maximized");
        }

        // ugasi password manager/leak popup
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        prefs.put("profile.password_manager_leak_detection", false);
        options.setExperimentalOption("prefs", prefs);

        WebDriver driver = new ChromeDriver(options);
        driverTL.set(driver);

        getDriver().get("https://www.saucedemo.com/");
        // nemoj maximize u headless-u (nije potrebno i zna da pravi čudne greške)
        // getDriver().manage().window().maximize();

        System.out.println("Thread ID: " + Thread.currentThread().getId());
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        WebDriver driver = getDriver();
        if (driver != null) {
            driver.quit();
        }
        driverTL.remove();
    }
}