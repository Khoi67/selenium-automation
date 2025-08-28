# Automation Project
![CI](https://github.com/Khoi67/selenium-automation/actions/workflows/ui-tests.yml/badge.svg)

> **Tech-stack:**&nbsp; <img src="https://img.shields.io/badge/Java-21-blue?logo=openjdk" height="18"/> &nbsp;|&nbsp;
> <img src="https://img.shields.io/badge/Selenium-4-brightgreen?logo=selenium" height="18"/> &nbsp;|&nbsp;
> <img src="https://img.shields.io/badge/TestNG-7.x-orange" height="18"/>

**Trang demo:**  
[https://opensource-demo.orangehrmlive.com/web/index.php/auth/login](https://opensource-demo.orangehrmlive.com/web/index.php/auth/login)

<p align="center">
  <img src="docs/orangehrm-login.png" width="600" alt="OrangeHRM login screen">
</p>

---

## ✨ Tính năng chính
| Nhóm | Mô tả |
|------|-------|
| Selenium WebDriver 4 | Hỗ trợ Chrome / Firefox / Edge (WebDriverManager) |
| TestNG + DataProvider | Login data từ Excel, Create Employee data từ Faker |
| Báo cáo | Extent Report 5 (HTML), screenshot khi lỗi |
| Logging | Log4j2 console + file |
| CI | GitHub Actions (Ubuntu-latest) build & test, badge trạng thái |

---

## 🛠 Cài đặt

1. JDK 17+ (dự án dùng 21)
2. Intellij
3. Git

---

## 🚀 Chạy project

### 1. Từ **IntelliJ IDEA**

1. `File ➜ Open` → chọn thư mục project (có `pom.xml`).
2. IntelliJ tự tải Maven dependencies.
3. Mở file `testNG.xml`, bấm **Run** (biểu tượng ▶) ‑ hoặc:  
   • Mở class test (ví dụ `AddEmployeeTest`) → **Run …**.
4. Xem báo cáo: `reports/<Name>_Report.html`.

### 2. Từ command line

```bash
git clone https://github.com/Khoi67/selenium-automation.git
cd selenium-automation
mvn clean test              # chạy tất cả suite
mvn test -Dbrowser=firefox  # đổi trình duyệt
mvn test -Dheadless=true    # chế độ headless