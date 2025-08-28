# Selenium-Automation ‑ OrangeHRM
![UI Tests](https://github.com/<USER>/<REPO>/actions/workflows/ui-tests.yml/badge.svg)

Framework tự động hoá giao diện (UI‐Automation) viết bằng **Java 21 + Selenium 4 + TestNG**  
• Áp dụng Page-Object-Model  
• Data-Driven (Excel + Faker)  
• Báo cáo **ExtentReport** (+ tuỳ chọn Allure)  
• CI/CD GitHub Actions  
• Chụp screenshot & ghi log4j2

---

## ✨ Tính năng chính
| Nhóm | Tính năng |
|------|-----------|
| Core | Selenium WebDriver, TestNG Runner, Page Object, BasePage (wait/click/type) |
| Data | Apache POI đọc Excel (`loginData.xlsx`) + JavaFaker sinh dữ liệu động |
| Báo cáo | ExtentReport HTML + screenshot, Log4j2 console/file <br>*(tuỳ chọn add Allure)* |
| Utilities | DriverFactory (Chrome/Firefox/Edge, headless), Screenshot, ExcelReader, TestData |
| CI | GitHub Actions chạy test trên Ubuntu-latest, badge trạng thái ngay trên README |

---

## 🛠 Chuẩn bị

| Phần mềm | Phiên bản gợi ý |
|----------|-----------------|
| JDK      | 17 + (project dùng 21) |
| Maven    | 3.9 + |
| Git      | latest |
| Chrome / Firefox / Edge | Tương ứng driver quản lý bởi **WebDriverManager** |

---

## 🚀 Cách chạy cục bộ

```bash
# clone repo
git clone https://github.com/<USER>/<REPO>.git
cd selenium-automation

# chạy toàn bộ TestNG suite
mvn clean test

# tuỳ chọn:
mvn test -Dbrowser=firefox          # đổi trình duyệt
mvn test -Dheadless=true            # chế độ headless (CI)