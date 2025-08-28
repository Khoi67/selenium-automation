package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage{

    private By usernameField = By.name("username");
    private By passwordField = By.name("password");
    private By loginButton = By.xpath("//button");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void loginSubmit(String username, String password) {
        type(usernameField, username);
        type(passwordField, password);
        click(loginButton);
    }

}
