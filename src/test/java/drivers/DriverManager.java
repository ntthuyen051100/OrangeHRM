package drivers;

import org.openqa.selenium.WebDriver;

public class DriverManager {
    // 1. Khởi tạo ThreadLocal cho WebDriver
    private static ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    // 2. Hàm set driver cho luồng hiện tại
    public static void setDriver(WebDriver driver) {
        driverThreadLocal.set(driver);
    }

    // 3. Hàm lấy driver của luồng hiện tại để sử dụng
    public static WebDriver getDriver() {
        return driverThreadLocal.get();
    }

    // 4. Hàm đóng trình duyệt và xóa driver khỏi luồng sau khi chạy xong
    public static void quitDriver() {
        if (driverThreadLocal.get() != null) {
            driverThreadLocal.get().quit();
            driverThreadLocal.remove(); // Bắt buộc phải xóa để tránh rò rỉ bộ nhớ
        }
    }
}

