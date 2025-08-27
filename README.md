# Selenium Automation Java

Dự án kiểm thử tự động sử dụng **Java + Selenium + TestNG** trên trang [OrangeHRM Demo](https://opensource-demo.orangehrmlive.com/).

## 🚀 Chức năng

### 1. **Đăng nhập**
- Đọc dữ liệu tài khoản từ **file Excel**.
- Thực hiện đăng nhập với nhiều bộ dữ liệu (**Data-driven testing**).
- Xác minh:
    - Đăng nhập thành công với thông tin hợp lệ → hiển thị Dashboard.
    - Đăng nhập thất bại với thông tin sai → hiển thị thông báo lỗi.

### 2. **Thêm nhân viên**
- Sử dụng **Java Faker** để sinh ngẫu nhiên thông tin nhân viên.
- Lưu thông tin nhân viên vào file .txt khi thêm thành công.
- Xác minh nhân viên được tạo thành công.

### 3. **Tìm kiếm nhân viên**
- Truy xuất dữ liệu từ trong file .txt.
- Xác minh dữ liệu khớp với thông tin đã tạo.

### 4. **Tiện ích**
- Xóa dữ liệu đã tạo trong file .txt khi bắt đầu chạy Suit.
- Sinh **ExtentReport** sau khi chạy test để hiển thị kết quả **Pass/Fail**.
- Tự động chụp **screenshot khi test thất bại** và gắn vào báo cáo.

## 🛠 Công nghệ sử dụng
- **JDK:** 21
- **IDE:** IntelliJ IDEA
- **Framework:** Selenium, TestNG

## 📦 Cài đặt & Chạy thử
- Clone project:
  ```bash
  git clone https://github.com/Khoi67/selenium-automation.git
  
- Chạy testNG.xml
