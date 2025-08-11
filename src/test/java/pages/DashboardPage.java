package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class DashboardPage {
    private WebDriver driver;
    //constructor nhận webdriver
    public DashboardPage(WebDriver driver){
        this.driver = driver;
    }

    public void goToPimPage(){
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='PIM']")));
        driver.findElement(By.xpath("//span[text()='PIM']")).click();
    }
}
