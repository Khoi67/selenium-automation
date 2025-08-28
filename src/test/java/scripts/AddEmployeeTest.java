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
        Object[][] data = new Object[3][2];

        for (int i = 0; i < 3; i++) {
            data[i][0] = faker.name().firstName();
            data[i][1] = faker.name().lastName();
        }
        return data;
    }

    @Test(dataProvider = "employeeData")
    public void addEmployeeTest(String firstName, String lastName) {
        try {
            logger.info("===== 🧪 Bắt đầu test thêm Employee: {} {} =====", firstName, lastName);

            // Login
            LoginPage loginPage = new LoginPage(driver);
            loginPage.loginSubmit(ConfigReader.get("username"), ConfigReader.get("password"));
            logger.info("✅ Đăng nhập thành công");

            // Truy cập PIM
            DashboardPage dashboardPage = new DashboardPage(driver);
            dashboardPage.goToPimPage();
            logger.info("✅ Đã vào module PIM");

            // Mở trang Add
            AddEmployeePage addEmployeePage = new AddEmployeePage(driver);
            addEmployeePage.goToAddEmployeePage();
            logger.info("✅ Đã vào trang Add Employee");

            // Điền dữ liệu
            logger.info("✍️ Nhập thông tin nhân viên: {} {}", firstName, lastName);
            addEmployeePage.addEmployee(firstName, lastName);

            // Kiểm tra lỗi ID
            String error = addEmployeePage.getEmployeeIdError();
            Assert.assertTrue(error.isEmpty(), "❌ Lỗi ID: " + error);

            // Kiểm tra vào trang Personal Detail thành công
            boolean isAdded = addEmployeePage.checkingAdd();
            Assert.assertTrue(isAdded, "❌ Đã không thêm được nhân viên!");

            logger.info("🎉 Nhân viên {} {} đã được thêm thành công!", firstName, lastName);

            // Lưu thông tin ra file/tạm bộ nhớ
            TestData.saveEmployee(firstName, lastName);

        } catch (Exception e) {
            logger.error("❗ Exception: {}", e.getMessage(), e);
            throw e; // re-throw để TestNG fail
        }
    }
}
