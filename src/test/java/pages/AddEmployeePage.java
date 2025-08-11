package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AddEmployeePage {
    private WebDriver driver;

    public AddEmployeePage(WebDriver driver){
        this.driver = driver;
    }
    public void goToAddEmployeePage() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()=\"Add Employee\"]")));
        driver.findElement(By.xpath("//a[text()=\"Add Employee\"]")).click();

    }
    public void addEmployee(String firstName, String lastName) {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[text()=\"Add Employee\"]")));
        driver.findElement(By.name("firstName")).sendKeys(firstName);
        driver.findElement(By.name("lastName")).sendKeys(lastName);
        driver.findElement(By.xpath("//button[@type=\"submit\"]")).click();
    }
}
