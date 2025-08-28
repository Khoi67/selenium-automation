package scripts;

import config.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import utils.DriverFactory;
import utils.TestData;

import java.time.Duration;

public class BaseTest {
    public WebDriver driver;

    //Reset file employees.txt trước khi chạy suite
    @BeforeSuite
    public void clearDataFile() {
        TestData.resetEmployeesFile();
    }

    @BeforeMethod
    public void setUp(){
        driver = DriverFactory.getDriver();
        driver.get(ConfigReader.get("baseUrl"));
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
    }

    @AfterMethod
    public void tearDown(){
        DriverFactory.quitDriver();
    }
}
