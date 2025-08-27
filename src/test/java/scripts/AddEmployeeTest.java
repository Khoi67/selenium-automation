package scripts;

import listerners.ExtentReportListerner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.AddEmployeePage;
import pages.DashboardPage;
import pages.LoginPage;

import java.time.Duration;
import java.util.Random;

@Listeners
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
            loginPage.loginSubmit("Admin", "admin123");

            logger.info("Điều hướng đến trang PIM");
            DashboardPage dashboardPage = new DashboardPage(driver);
            dashboardPage.goToPimPage();

            logger.info("Mở trang Add Employee");
            AddEmployeePage addEmployeePage = new AddEmployeePage(driver);
            addEmployeePage.goToAddEmployeePage();

            logger.info("Đang nhập thông tin: {} {}", firstName, lastName);
            addEmployeePage.addEmployee(firstName, lastName);

            // Kiểm tra lỗi Employee Id
            String error = addEmployeePage.getEmployeeIdError();
            Assert.assertTrue(error.isEmpty(), error);

            // Kiểm tra đã add thành công
            boolean isAdded = addEmployeePage.checkingAdd();
            Assert.assertTrue(isAdded, "Thêm thất bại!!!");

            logger.info("Thêm thành công!!!");
            ExtentReportListerner.getTest().pass("✅ Thêm thành công: " + firstName + " " + lastName);

        } catch (Exception e) {
            logger.error("Lỗi {}", e.getMessage());
            throw e; // Vẫn ném lỗi để TestNG fail
        }
    }
}
