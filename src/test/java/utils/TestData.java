package utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TestData {
    private static final String FILE_PATH = "lists/employees.txt";

    // 🔹 Xóa hoặc reset file trước khi chạy test suite
    public static void clearEmployeesFile() {
        try {
            File file = new File(FILE_PATH);
            if (file.exists()) {
                new FileWriter(file, false).close(); // ghi rỗng
                System.out.println("✅ Đã reset file employees.txt");
            } else {
                file.getParentFile().mkdirs(); // tạo folder nếu chưa có
                file.createNewFile();          // tạo file mới
                System.out.println("✅ Đã tạo file employees.txt mới");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 🔹 Lưu nhân viên mới vào file
    public static void saveEmployee(String firstName, String lastName) {
        try (FileWriter fw = new FileWriter(FILE_PATH, true)) {
            fw.write(firstName + "," + lastName + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String[]> loadEmployees() {
        List<String[]> employees = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                employees.add(parts);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return employees;
    }
}
