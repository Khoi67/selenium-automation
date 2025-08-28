package utils;

import config.ConfigReader;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.UUID;

public class DriverFactory {
    private static final ThreadLocal<WebDriver> TL_DRIVER = new ThreadLocal<>();

    public static WebDriver getDriver() {
        if (TL_DRIVER.get() == null) {
            TL_DRIVER.set(initDriver());
        }
        return TL_DRIVER.get();
    }

    /* ───────────────────────── private helpers ───────────────────────── */
    private static WebDriver initDriver() {

        // ưu tiên system property, sau đó tới config.properties
        String browserName = System.getProperty("browser",
                ConfigReader.get("browser")).toLowerCase();

        boolean isHeadless = Boolean.getBoolean("headless")         // -Dheadless=true
                || System.getenv("CI") != null;           // auto headless trên CI

        switch (browserName) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions co = new ChromeOptions();
                co.addArguments("--remote-allow-origins=*");

                /* FIX: gán user-data-dir duy nhất cho mỗi phiên tránh xung đột */
                Path tmpProfile = createTempProfileDir("chrome");
                co.addArguments("--user-data-dir=" + tmpProfile.toString());

                if (isHeadless) co.addArguments("--headless=new", "--no-sandbox", "--disable-dev-shm-usage");

                return prepare(new ChromeDriver(co));

            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions fo = new FirefoxOptions();
                if (isHeadless) fo.addArguments("--headless");
                return prepare(new FirefoxDriver(fo));

            case "edge":
                WebDriverManager.edgedriver().setup();
                EdgeOptions eo = new EdgeOptions();
                if (isHeadless) eo.addArguments("--headless=new");
                eo.addArguments("--user-data-dir=" + createTempProfileDir("edge"));
                return prepare(new EdgeDriver(eo));

            default:
                throw new IllegalArgumentException("❌ Browser không hỗ trợ: " + browserName);
        }
    }

    private static WebDriver prepare(WebDriver driver) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
        driver.manage().window().maximize();
        return driver;
    }

    /** Tạo thư mục profile tạm duy nhất, auto xoá khi JVM đóng */
    private static Path createTempProfileDir(String prefix) {
        try {
            Path dir = Files.createTempDirectory(prefix + "-" + UUID.randomUUID());
            dir.toFile().deleteOnExit();
            return dir;
        } catch (Exception e) {
            throw new RuntimeException("Không tạo được thư mục profile tạm", e);
        }
    }

    /* ───────────────────────── quit ───────────────────────── */
    public static void quitDriver() {
        WebDriver driver = TL_DRIVER.get();
        if (driver != null) {
            driver.quit();
            TL_DRIVER.remove();
        }
    }
}
