package scripts;

import com.github.javafaker.Faker;
import config.ConfigReader;
import listerners.ExtentReportListerner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.AddEmployeePage;
import pages.DashboardPage;
import pages.LoginPage;
import utils.TestData;


@Listeners
public class AddEmployeeTest extends BaseTest{
    private static final Logger logger = LogManager.getLogger(AddEmployeeTest.class);
    @DataProvider(name = "employeeData")
    public Object[][] getEmployeeData() {
        Faker faker = new Faker();
        Object[][] data = new Object[10][2];

        for (int i = 0; i < 10; i++) {
            data[i][0] = faker.name().firstName();
            data[i][1] = faker.name().lastName();
        }
        return data;
    }

    @Test(dataProvider = "employeeData")
    public void addEmployeeTest(String firstName, String lastName) {
        try {
            logger.info("Đăng nhập với tài khoản Admin");
            LoginPage loginPage = new LoginPage(driver);
            loginPage.loginSubmit(ConfigReader.get("username"), ConfigReader.get("password"));

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
            TestData.saveEmployee(firstName, lastName);
        } catch (Exception e) {
            logger.error("Lỗi {}", e.getMessage());
            throw e; // Vẫn ném lỗi để TestNG fail
        }
    }
}
