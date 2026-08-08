package drivers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

//DriverFactory là phiên bản update của DriveManager khi vừa mở được nhiều luồng trên nhiều trình duyệt khác nhau
public class DriverFactory {
    // 1. Khởi tạo ThreadLocal cho WebDriver để quản lý WebDriver độc lập cho từng luồng (Thread)
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    // 2. Hàm set driver cho luồng hiện tại
    public static void setDriver(String browser) {
        WebDriver driverInstance;

        switch (browser.toLowerCase().trim()) {
            case "chrome":
                driverInstance = new ChromeDriver();
                break;
            case "firefox":
                driverInstance = new FirefoxDriver();
                break;
            case "edge":
                driverInstance = new EdgeDriver();
                break;
            default:
                System.out.println("Trình duyệt '" + browser + "' không hợp lệ. Tự động chạy với Chrome.");
                driverInstance = new ChromeDriver();
                break;
        }

        driver.set(driverInstance);
    }
    // 3. Hàm lấy driver của luồng hiện tại để sử dụng
    public static WebDriver getDriver() {
        return driver.get();
    }
    // 4. Hàm đóng trình duyệt và xóa driver khỏi luồng sau khi chạy xong
    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove(); // Xóa driver khỏi ThreadLocal sau khi đóng
        }
    }
}
