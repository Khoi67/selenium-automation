package scripts;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.LoginPage;
import utils.ExcelReader;

@Listeners
public class LoginTest extends BaseTest{
    private static final Logger logger = LogManager.getLogger(LoginTest.class);
    @DataProvider(name = "loginData") // cung cấp dữ liệu đầu vào cho các @test
    public Object[][] loginData() {
        String filePath = "src/test/resources/loginData.xlsx";
        String sheetName = "Sheet1";

        return ExcelReader.getData(filePath, sheetName);
    }

    @Test(dataProvider = "loginData")
    public void loginTest(String username, String password, String expectedResult) {
        try {
            logger.info("Test login với {}/{}", username, password);
            LoginPage loginPage = new LoginPage(driver);
            loginPage.loginSubmit(username, password);

            Boolean isLogged = driver.getCurrentUrl().contains("dashboard");
            logger.info("Kết quả thực tế: {}", isLogged);
            Assert.assertEquals(isLogged, Boolean.parseBoolean(expectedResult), "Test fail");
            logger.info("Test pass");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
