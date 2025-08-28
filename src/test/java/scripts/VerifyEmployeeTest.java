package scripts;

import config.ConfigReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.DashboardPage;
import pages.EmployeeListPage;
import pages.LoginPage;

import java.util.List;

import static utils.TestData.loadEmployees;

@Listeners
public class VerifyEmployeeTest extends BaseTest{
    private static final Logger logger = LogManager.getLogger(VerifyEmployeeTest.class);

    @DataProvider(name = "employeeFileData")
    public static Object[][] getEmployeesFromFile() {
        List<String[]> employees = loadEmployees();

        Object[][] data = new Object[employees.size()][2];
        for (int i = 0; i < employees.size(); i++) {
            data[i][0] = employees.get(i)[0]; // firstName
            data[i][1] = employees.get(i)[1]; // lastName
        }
        return data;
    }
    @Test(dataProvider = "employeeFileData")
    public void verifyEmployeesInList(String firstName, String lastName) {
        try {
            logger.info("🔍 Verify nhân viên khi search: {} {}", firstName, lastName);

            LoginPage loginPage = new LoginPage(driver);
            loginPage.loginSubmit(ConfigReader.get("username"), ConfigReader.get("password"));

            DashboardPage dashboardPage = new DashboardPage(driver);
            dashboardPage.goToPimPage();

            EmployeeListPage employeeListPage = new EmployeeListPage(driver);
            boolean exists = employeeListPage.isEmployeeInList(firstName, lastName);

            Assert.assertTrue(exists, "Không tìm thấy nhân viên: " + firstName + " " + lastName);

            logger.info("Tìm kiếm thành công: {} {}", firstName, lastName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
