package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class EmployeeListPage extends BasePage{

    private By employeeNameInput = By.xpath("//label[text()='Employee Name']/../following-sibling::div//input");
    private By searchButton = By.xpath("//button[@type='submit']");
    private By resultRows   = By.xpath("//div[@class='oxd-table-body']/div");
    private By emptyMessage = By.xpath("//span[text()='No Records Found']");

    public EmployeeListPage(WebDriver driver) {
        super(driver);
    }

    public boolean isEmployeeInList(String first, String last) {
        // clear + nhập
        WebElement box = find(employeeNameInput);
        box.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE, first + " " + last);
        click(searchButton);

        // chờ bảng hoặc message
        wait.until(ExpectedConditions.or(
                ExpectedConditions.visibilityOfElementLocated(resultRows),
                ExpectedConditions.visibilityOfElementLocated(emptyMessage)
        ));

        // nếu có message “No Records Found” thì return false ngay
        if (isDisplayed(emptyMessage, 5)) return false;

        // duyệt lại hàng (fetch mới để tránh stale)
        for (WebElement row : driver.findElements(resultRows)) {
            String fn = row.findElement(By.xpath(".//div[@role='cell'][3]")).getText().trim();
            String ln = row.findElement(By.xpath(".//div[@role='cell'][4]")).getText().trim();
            if (fn.equalsIgnoreCase(first) && ln.equalsIgnoreCase(last))
                return true;
        }
        return false;
    }}
