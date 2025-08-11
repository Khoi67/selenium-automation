package scripts;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;

public class LoginTest extends BaseTest{
    @Test
    public void loginTest() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginSubmit("Admin", "admin123");

        boolean isLogged = driver.getCurrentUrl().contains("dashboard");
        Assert.assertTrue(isLogged, "Test Failed");
    }
}
