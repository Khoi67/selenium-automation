package listerners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import scripts.BaseTest;
import utils.ExtentManager;
import utils.Screenshot;


public class ExtentReportListerner implements ITestListener {
    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    private String reportFileName;

    @Override
    public void onStart(ITestContext context) {
        String testName = context.getName();
        reportFileName = testName + "_Report.html";
        extent = ExtentManager.createInstance(reportFileName);
    }

    @Override
    public void onTestStart(ITestResult result) {
        test.set(extent.createTest(result.getMethod().getMethodName()));
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.get().log(Status.PASS, "✅ Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        test.get().log(Status.FAIL, result.getThrowable());
        try {
            WebDriver driver = ((BaseTest) result.getInstance()).driver;
            String path = Screenshot.captureScreenshot(driver, result.getMethod().getMethodName());
            if (path != null) test.get().addScreenCaptureFromPath(path);
        } catch (Exception ignored) {}
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test.get().log(Status.SKIP, "⚠️ Test Skipped");
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();                // ghi file *.html của suite
    }

    public static ExtentTest getTest() {
        return test.get();
    }
}
