package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class EmployeeListPage extends BasePage{

    private By employeeNameInput = By.xpath("//label[text()='Employee Name']/../following-sibling::div//input");
    private By searchButton = By.xpath("//button[@type='submit']");
    private By resultRows = By.xpath("//div[@class='oxd-table-body']/div"); // từng dòng nhân viên

    public EmployeeListPage(WebDriver driver) {
        super(driver);
    }

    public boolean isEmployeeInList(String firstName, String lastName) {
        try {
            // Xóa sạch tên cũ trước khi search tên mới
            WebElement nameField = find(employeeNameInput);
            nameField.sendKeys(Keys.CONTROL + "a"); // Select all
            nameField.sendKeys(Keys.DELETE);        // Delete
            nameField.sendKeys(firstName + " " + lastName); // Nhập tên

            click(searchButton);

            // ⏱️ Đợi danh sách xuất hiện thay vì sleep
            wait.until(ExpectedConditions.visibilityOfElementLocated(resultRows));

            List<WebElement> rows = driver.findElements(resultRows);

            for (WebElement row : rows) {
                String firstnameFound = row.findElement(By.xpath(".//div[@role='cell'][3]")).getText().trim();
                String lastnameFound  = row.findElement(By.xpath(".//div[@role='cell'][4]")).getText().trim();

                if (firstnameFound.equalsIgnoreCase(firstName) &&
                        lastnameFound.equalsIgnoreCase(lastName)) {
                    return true;
                }
            }
            return false;
        } catch (TimeoutException e) {
            return false;
        }
    }
}
