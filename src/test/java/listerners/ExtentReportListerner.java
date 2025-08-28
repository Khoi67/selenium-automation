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

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class ExtentReportListerner implements ITestListener {
    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    // Lưu tên file HTML của suite hiện tại (để gen index)
    private String reportFileName;

    @Override
    public void onStart(ITestContext context) {
        String testName = context.getName();               // <test name="...">
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

        try {
            generateIndexFile();       // tạo/cập nhật index.html
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* ========= Tạo index.html liệt kê toàn bộ báo cáo ========= */
    private void generateIndexFile() throws Exception {
        File reportsDir = new File("reports");
        if (!reportsDir.exists()) return;

        File[] htmlFiles = reportsDir.listFiles(f -> f.isFile() && f.getName().endsWith(".html"));
        if (htmlFiles == null || htmlFiles.length == 0) return;

        StringBuilder sb = new StringBuilder();
        sb.append("<html><head><meta charset='UTF-8'><title>Automation Reports</title>")
                .append("<style>body{font-family:Arial} ul{line-height:1.8}</style></head><body>")
                .append("<h2>Automation Test Reports</h2><ul>");

        for (File f : htmlFiles) {
            String name = f.getName();
            if ("index.html".equals(name)) continue;    // tránh tự liệt kê chính nó
            sb.append("<li><a href=\"").append(name).append("\">").append(name).append("</a></li>");
        }

        sb.append("</ul><p>Generated automatically.</p></body></html>");

        Path indexPath = new File(reportsDir, "index.html").toPath();
        Files.write(indexPath, sb.toString().getBytes(StandardCharsets.UTF_8));
        System.out.println("✅ Generated reports/index.html");
    }

    public static ExtentTest getTest() {
        return test.get();
    }
}
