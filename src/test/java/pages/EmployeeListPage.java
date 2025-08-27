package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class EmployeeListPage {
    private WebDriver driver;

    // Locator cho search
    private By firstNameInput = By.xpath("//label[text()='Employee Name']/../following-sibling::div//input");
    private By searchButton = By.xpath("//button[@type='submit']");
    private By resultRows = By.xpath("//div[@class='oxd-table-body']/div"); // hàng trong table

    public EmployeeListPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isEmployeeInList(String firstName, String lastName) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // Nhập tên nhân viên vào ô search
            WebElement nameField = wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameInput));
            nameField.sendKeys(Keys.CONTROL + "a");
            nameField.sendKeys(Keys.DELETE);
            nameField.sendKeys(firstName + " " + lastName);

            // Bấm Search
            driver.findElement(searchButton).click();

            Thread.sleep(3000);
            List<WebElement> rows = wait.until(
                    ExpectedConditions.visibilityOfAllElementsLocatedBy(resultRows)
            );

            for (WebElement row : rows) {
                String firstnameFound = row.findElement(By.xpath(".//div[@role='cell'][3]")).getText().trim();
                String lastnameFound  = row.findElement(By.xpath(".//div[@role='cell'][4]")).getText().trim();

                if (firstnameFound.equalsIgnoreCase(firstName) && lastnameFound.equalsIgnoreCase(lastName)) {
                    return true;
                }
            }
            return false;
        } catch (TimeoutException e) {
            return false;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
