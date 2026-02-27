package saucedemo.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

import java.util.HashMap;
import java.util.Map;

@Listeners({
        io.qameta.allure.testng.AllureTestNg.class,
        saucedemo.base.TestListener.class
})

public abstract class BaseTest {
    protected WebDriver driver;

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();

        // (opciono) stabilnije okruženje
        options.addArguments("--incognito");
        options.addArguments("--disable-notifications");

        // ako je CI (GitHub Actions automatski setuje CI=true)
        boolean isCI = "true".equalsIgnoreCase(System.getenv("CI"));

        if (isCI) {
            options.addArguments("--headless=new");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--window-size=1920,1080");
        } else {
            // lokalno: može maximize ili start-maximized
            options.addArguments("--start-maximized");
        }

        // ugasi password manager + leak detection popup
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        prefs.put("profile.password_manager_leak_detection", false);
        options.setExperimentalOption("prefs", prefs);

        driver = new ChromeDriver(options);

        // BITNO: ne maximize u CI/headless
        if (!isCI) {
            driver.manage().window().maximize(); // može i da izbrišeš skroz jer imaš --start-maximized
        }

        driver.get("https://www.saucedemo.com/");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
