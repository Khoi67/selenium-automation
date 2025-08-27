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

        //tạo biến số dòng dữ liệu (không bao gồm dòng tiêu đề)
        int rowCount = 4;

        //tạo mảng 2 chiều để lưu dữ liệu
        Object[][] data = new Object[rowCount][3];

        for(int i = 0; i < rowCount; i++){
            //lấy dữ liệu cho username
            data[i][0] = ExcelReader.getCellData(filePath, sheetName, i + 1, 0);

            //lấy dữ liệu cho password
            data[i][1] = ExcelReader.getCellData(filePath, sheetName, i + 1, 1);

            //expectedResult
            data[i][2] = ExcelReader.getCellData(filePath, sheetName, i + 1, 2);
        }
        //trả dữ liệu ra ngoài
        return data;
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
