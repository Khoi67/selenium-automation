package scripts;

import org.apache.commons.math3.analysis.function.Add;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    private static final Logger logger = LogManager.getLogger(AddEmployeeTest.class);
    @DataProvider(name = "employeeData")
    public Object[][] getEmployeeData() {
        String[] FIRST_NAMES = {
                "Liam", "Noah", "Oliver", "Elijah", "James",
                "William", "Benjamin", "Lucas", "Henry", "Alexander",
                "Emma", "Olivia", "Ava", "Sophia", "Isabella",
                "Mia", "Amelia", "Harper", "Evelyn", "Abigail"
        };

        String[] LAST_NAMES = {
                "Smith", "Johnson", "Williams", "Brown", "Jones",
                "Garcia", "Miller", "Davis", "Rodriguez", "Martinez",
                "Hernandez", "Lopez", "Gonzalez", "Wilson", "Anderson",
                "Thomas", "Taylor", "Moore", "Jackson", "Martin"
        };

        Object[][] data = new Object[10][2]; // 10 bộ dữ liệu
        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            String firstName = FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
            String lastName = LAST_NAMES[random.nextInt(LAST_NAMES.length)];
            data[i][0] = firstName;
            data[i][1] = lastName;
        }

        return data;
    }
    @Test(dataProvider = "employeeData")
    public void addEmployeeTest(String firstName, String lastName) {
        try {
            logger.info("Đăng nhập với tài khoản Admin");
            LoginPage loginPage = new LoginPage(driver);
            loginPage.loginSubmit("Admin",
                    "admin123");

            logger.info("Điều hướng đến trang PIM");
            DashboardPage dashboardPage = new DashboardPage(driver);
            dashboardPage.goToPimPage();

            logger.info("Mở trang Add Employee");
            AddEmployeePage addEmployeePage = new AddEmployeePage(driver);
            addEmployeePage.goToAddEmployeePage();

            logger.info("Đang nhập thông tin: {} {}", firstName, lastName);
            addEmployeePage.addEmployee(firstName, lastName);

            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[text()='Personal Details']")));

            boolean isAdded = driver.getCurrentUrl().contains("viewPersonalDetails");
            logger.info("Kết quả thêm: {}", isAdded);

            Assert.assertTrue(isAdded, "Failed to add employee: " + firstName + " " + lastName);
        } catch (Exception e) {
            logger.error("LỖI trong quá trình chạy test với dữ liệu: {} {} - Chi tiết lỗi: {}",
                    firstName, lastName, e.getMessage());
            throw e; // Vẫn ném lỗi để TestNG fail
        }
    }
}
