package scripts;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import pages.LoginPage;
import utils.DriverFactory;

import java.time.Duration;

public class BaseTest {
    public WebDriver driver;

    @BeforeMethod
    public void setUp(){
        driver = DriverFactory.getDriver();
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php");
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
    }

    @AfterMethod
    public void tearDown(){
        if(driver != null){
            driver.quit();
        }
    }
}
