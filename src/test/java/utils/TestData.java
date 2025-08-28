package utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Lưu – đọc danh sách nhân viên đã tạo trong quá trình chạy test.
 * 1 record = firstName,lastName
 */
public final class TestData {

    private static final Path FILE_PATH = Paths.get("lists", "employees.txt");

    /* ─────────────────────  INIT / CLEAR  ───────────────────── */

    /** Tạo mới (hoặc xoá nội dung) file employees.txt */
    public static void resetEmployeesFile() {
        try {
            // Tạo thư mục gốc nếu chưa có
            if (Files.notExists(FILE_PATH.getParent())) {
                Files.createDirectories(FILE_PATH.getParent());
            }
            // Ghi rỗng để reset
            Files.write(FILE_PATH, new byte[0], StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            System.out.println("✅ TestData – File employees.txt đã được reset.");
        } catch (IOException e) {
            System.err.println("❌ Không thể reset file employees.txt: " + e.getMessage());
        }
    }

    /* ─────────────────────  WRITE  ───────────────────── */

    /** Ghi thêm một nhân viên mới */
    public static synchronized void saveEmployee(String firstName, String lastName) {
        try {
            String line = firstName + "," + lastName + System.lineSeparator();
            Files.write(FILE_PATH, line.getBytes(StandardCharsets.UTF_8),
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.err.println("❌ Không thể ghi nhân viên vào file: " + e.getMessage());
        }
    }

    /* ─────────────────────  READ  ───────────────────── */

    /** Trả về danh sách nhân viên dạng List<String[]>  */
    public static List<String[]> loadEmployees() {
        List<String[]> employees = new ArrayList<>();
        if (Files.notExists(FILE_PATH)) return employees;   // Chưa có file ⇒ trả danh sách rỗng

        try {
            List<String> lines = Files.readAllLines(FILE_PATH, StandardCharsets.UTF_8);
            for (String line : lines) {
                if (line.trim().isEmpty()) continue;        // bỏ dòng trống
                employees.add(line.split(",", 2));          // [firstName, lastName]
            }
        } catch (IOException e) {
            System.err.println("❌ Không thể đọc file employees.txt: " + e.getMessage());
        }
        return employees;
    }

    /* ─────────────────────  Utility  ───────────────────── */

    /** Trả về tổng số nhân viên đã lưu */
    public static int size() {
        return loadEmployees().size();
    }

    // Ngăn không cho khởi tạo đối tượng
    private TestData() {}
}