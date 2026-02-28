package saucedemo.base;

import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.ByteArrayInputStream;

public class TestListener implements ITestListener {
    @Override
    public void onTestFailure(ITestResult result) {
        Object instance = result.getInstance();

        // očekujemo da svi testovi nasleđuju BaseTest i imaju "driver"
        if (instance instanceof BaseTest) {
            WebDriver driver = ((BaseTest) instance).getDriver();

            if (driver != null) {
                try {
                    byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                    Allure.getLifecycle().addAttachment(
                            "Screenshot - FAIL",
                            "image/png",
                            "png",
                            new ByteArrayInputStream(screenshot)
                    );
                } catch (Exception e) {
                    Allure.addAttachment("Screenshot error", e.toString());
                }
            }
        }
    }
}
