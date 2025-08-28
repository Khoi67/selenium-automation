package listerners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import utils.ExtentManager;
import utils.Screenshot;

public class TestListerner implements ITestListener {
    private static ExtentReports extent = ExtentManager.createInstance("ExtentReport.html");
    private static ThreadLocal<ExtentTest> testThread = new ThreadLocal<>();

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest test = extent.createTest(result.getMethod().getMethodName());
        testThread.set(test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        testThread.get().log(Status.PASS, "✅ Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        testThread.get().log(Status.FAIL, "❌ Test Failed: " + result.getThrowable());

        Object driverObj = result.getTestContext().getAttribute("driver");
        if (driverObj instanceof WebDriver) {
            WebDriver driver = (WebDriver) driverObj;
            String screenshotPath = Screenshot.captureScreenshot(driver, result.getMethod().getMethodName());
            if (screenshotPath != null) {
                testThread.get().addScreenCaptureFromPath(screenshotPath);
            }
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        testThread.get().log(Status.SKIP, "⚠ Test Skipped");
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush(); // Xuất file HTML
    }
}
