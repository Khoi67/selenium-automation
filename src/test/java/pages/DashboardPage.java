package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Objects;

public class DashboardPage extends BasePage {
    private By pimMenu = By.xpath("//span[text()='PIM']");

    public DashboardPage(WebDriver driver) {
        super(driver);
    }

    public void goToPimPage() {
        click(pimMenu); // sử dụng method từ BasePage: có sẵn wait bên trong
    }
}
