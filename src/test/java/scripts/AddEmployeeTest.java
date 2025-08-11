package scripts;

import org.apache.commons.math3.analysis.function.Add;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.AddEmployeePage;
import pages.DashboardPage;
import pages.LoginPage;

import java.time.Duration;
import java.util.Random;

public class AddEmployeeTest extends BaseTest{
    @DataProvider(name = "employeeData")
    public Object[][] getEmployeeData() {
        Object[][] data = new Object[10][2]; // 10 bộ dữ liệu, mỗi bộ 2 giá trị (firstName, lastName)
        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            data[i][0] = "First " + random.nextInt(1000);
            data[i][1] = "Last " + random.nextInt(1000);
        }
        return data;
    }
    @Test(dataProvider = "employeeData")
    public void addEmployeeTest(String firstName, String lastName) {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginSubmit("Admin", "admin123");

        DashboardPage dashboardPage = new DashboardPage(driver);
        dashboardPage.goToPimPage();

        AddEmployeePage addEmployeePage = new AddEmployeePage(driver);
        addEmployeePage.goToAddEmployeePage();

        addEmployeePage.addEmployee(firstName, lastName);

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[text()=\"Personal Details\"]")));
        boolean isAdded = driver.getCurrentUrl().contains("viewPersonalDetails");
        Assert.assertTrue(isAdded, "Failed to add employee: ");
    }
}
