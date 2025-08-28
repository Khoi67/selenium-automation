package pages;

import org.openqa.selenium.*;

public class AddEmployeePage extends BasePage {

    private By addEmployeeMenu = By.xpath("//a[text()='Add Employee']");
    private By addEmployeeHeader = By.xpath("//h6[text()='Add Employee']");
    private By firstNameField = By.name("firstName");
    private By lastNameField = By.name("lastName");
    private By employeeIdField = By.xpath("//label[text()='Employee Id']/../following-sibling::div/input");
    private By employeeIdError = By.xpath("//label[text()='Employee Id']/../following-sibling::span");
    private By submitBtn = By.xpath("//button[@type='submit']");
    private By personalDetailsHeader = By.xpath("//h6[text()='Personal Details']");

    public AddEmployeePage(WebDriver driver) {
        super(driver);
    }
    public void goToAddEmployeePage() {
        click(addEmployeeMenu);
    }
    public void addEmployee(String firstName, String lastName) {
        waitForVisibility(addEmployeeHeader); // đảm bảo trang đã load

        type(firstNameField, firstName);
        type(lastNameField, lastName);

        // Đặt lại Employee ID
        WebElement empIdInput = find(employeeIdField);
        empIdInput.sendKeys(Keys.CONTROL + "a");
        empIdInput.sendKeys(Keys.DELETE);
        empIdInput.sendKeys(firstName);

        click(submitBtn);
    }
    public String getEmployeeIdError() {
        try {
            return getText(employeeIdError);
        } catch (TimeoutException e) {
            return "";
        }
    }
    public boolean checkingAdd() {
        return isDisplayed(personalDetailsHeader, 10);
    }
}
