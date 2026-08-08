package base;

import drivers.DriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.ConfigReader;

import static utils.LogUtils.logger;

public class BaseTest_UsingDriverManager {
    /*protected WebDriver driver;*/
/*Khi chạy parrallel, Bỏ biến driver ở trên đầu class. Khai báo nó trực tiếp bên trong hàm setup().
Giải thích: Xem trong file*/

    @BeforeMethod
    public void setup() {
        logger.info("========== START TEST ==========");
        //Có 2 cách: C1: Khởi tạo web driver -> cho vào threadLoacal -> rồi dùng driver của threadLocal cấu hình
        //C2:  Khởi tạo web driver ->cấu hình trên biến web driver -> sau đó mới cho vào threadlocal

        // 1. Khởi tạo WebDriver mới/ cục bộ cho luồng hiện tại (không static)
        WebDriver driver = new ChromeDriver();
/*        //2. Đưa driver vào quản lý của ThreadLocal
        DriverManager.setDriver(driver);
        // 3. Sử dụng DriverManager.getDriver() để điều khiển trình duyệt của luồng hiện tại
        DriverManager.getDriver().manage().window().maximize();
        DriverManager.getDriver().get(ConfigReader.getPropValue("url"));*/

        // 2. Thực hiện các cấu hình mong muốn trực tiếp trên biến này
        driver.manage().window().maximize();
        driver.get(ConfigReader.getPropValue("url"));
        // 3. Đưa vào ThreadLocal để chia sẻ cho luồng hiện tại
        DriverManager.setDriver(driver);

/* DriverManager.getDriver() tương đương với driver
Khi chuyển sang sử dụng ThreadLocal để chạy song song (parallel), mọi thao tác điều khiển trình duyệt như phóng to
cửa sổ (maximize()) hay điều hướng URL (get()) không được gọi trực tiếp từ biến driver tĩnh/thông thường nữa,
mà bắt buộc phải thông qua hàm lấy driver từ ThreadLocal (DriverManager.getDriver()).*/
    }

    @AfterMethod
    public void tearDown() {
        logger.info("========== END TEST ==========");
        DriverManager.quitDriver();
    }
/*    public void tearDown(ITestResult result) {
        // 1. Kiểm tra xem bài test thuộc luồng hiện tại có bị FAIL hay không
        if (result.getStatus() == ITestResult.FAILURE) {
            logger.info("❌ Phát hiện testcase [" + result.getName() + "] bị lỗi ở luồng: " + Thread.currentThread().getId());

            if (DriverManager.getDriver() != null) {
                try {
                    // 2. Chụp ảnh màn hình dạng mảng Byte nguyên bản (Thread-safe tuyệt đối)
                    byte[] screenshot = ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.BYTES);

                    // 3. Sử dụng API gốc của Allure để đóng gói ghim thẳng vào file JSON kết quả
                    Allure.addAttachment("Màn hình lỗi thực tế lúc sập test", new ByteArrayInputStream(screenshot));
                    System.out.println("📸 Đã găm ảnh chụp màn hình vào Allure Report thành công trước khi đóng trình duyệt!");
                } catch (Exception e) {
                    System.err.println("Không thể thực hiện chụp ảnh: " + e.getMessage());
                }
            }
        }

        // 4. Sau khi đã chụp ảnh xong xuôi, mới an tâm đóng trình duyệt giải phóng luồng
        DriverManager.quitDriver();
    }*/
}

