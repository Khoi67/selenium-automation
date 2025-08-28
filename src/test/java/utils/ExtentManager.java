package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.File;

public class ExtentManager {
    // Tạo biến static ExtentReports để dùng toàn cục
    private static ExtentReports extent;
    // Hàm khởi tạo ExtentReports instance
    public static ExtentReports createInstance(String reportFileName) {

        // Tạo thư mục reports nếu chưa có
        File reportsDir = new File("reports");
        if (!reportsDir.exists()) {
            reportsDir.mkdirs();
            System.out.println("✅ [ExtentManager] created /reports");
        }

        String reportPath = "./reports/" + reportFileName;

        // Tạo đối tượng ExtentSparkReporter, dùng để xuất ra file HTML
        ExtentSparkReporter reporter = new ExtentSparkReporter(reportPath);

        // Thiết lập tiêu đề file HTML
        reporter.config().setDocumentTitle("Automation Test Report");

        // Thiết lập tên hiển thị cho báo cáo
        reporter.config().setReportName("Test Results");

        // Chọn theme cho báo cáo: STANDARD (trắng) hoặc DARK (đen)
        reporter.config().setTheme(Theme.STANDARD);

        reporter.config().setEncoding("UTF-8"); // Fix lỗi tiếng Việt

        // Khởi tạo đối tượng ExtentReports chính
        extent = new ExtentReports();

        // Gắn reporter đã cấu hình ở trên vào ExtentReports
        extent.attachReporter(reporter);

        // Gắn thông tin phụ thêm vào report (hiển thị ở phần trên cùng)
        extent.setSystemInfo("Tester", "Khoi Do");
        extent.setSystemInfo("Environment", "Production");
        extent.setSystemInfo("Build Number", "v1.0.2");

        // Trả về đối tượng ExtentReports
        return extent;
    }
}
