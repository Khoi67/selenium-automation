package pages;

import listerners.ExtentReportListerner;
import org.openqa.selenium.*;
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

        WebElement empId = driver.findElement(By.xpath("//label[text()='Employee Id']/../following-sibling::div/input"));
        empId.sendKeys(Keys.CONTROL + "a"); // chọn hết
        empId.sendKeys(Keys.DELETE);
        empId.sendKeys(firstName);

        driver.findElement(By.xpath("//button[@type=\"submit\"]")).click();
    }
    public String getEmployeeIdError() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
            WebElement errorMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//label[text()='Employee Id']/../following-sibling::span")
            ));
            return errorMsg.getText();
        } catch (TimeoutException e) {
            return ""; // không có lỗi
        }
    }
    public boolean checkingAdd() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[text()='Personal Details']")));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

}
