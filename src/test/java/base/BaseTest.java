package base;

import driver.DriverManager;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.ConfigReader;

import java.io.ByteArrayInputStream;

import static utils.LogUtils.logger;

public class BaseTest {
    protected WebDriver driver;

    @BeforeMethod
    public void setup() {
        logger.info("========== START TEST ==========");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        /*        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(300));*/
/*Sau khi tạo xong ConfigReader thì thay đổi cách lấy URL như sau:
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");*/
        driver.get(ConfigReader.getPropValue("url"));
    }

    @AfterMethod
    public void tearDown() {
        logger.info("========== END TEST ==========");
        driver.quit();
    }
/*    public void tearDown(ITestResult result) {
        // 1. Kiểm tra xem bài test thuộc luồng hiện tại có bị FAIL hay không
        if (result.getStatus() == ITestResult.FAILURE) {
            logger.info("❌ Phát hiện testcase [" + result.getName() + "] bị lỗi ở luồng: " + Thread.currentThread().getId());

            if (driver != null) {
                try {
                    // 2. Chụp ảnh màn hình dạng mảng Byte nguyên bản (Thread-safe tuyệt đối)
                    byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);

                    // 3. Sử dụng API gốc của Allure để đóng gói ghim thẳng vào file JSON kết quả
                    Allure.addAttachment("Màn hình lỗi thực tế lúc sập test", new ByteArrayInputStream(screenshot));
                    logger.info("📸 Đã găm ảnh chụp màn hình vào Allure Report thành công trước khi đóng trình duyệt!");
                } catch (Exception e) {
                    System.err.println("Không thể thực hiện chụp ảnh: " + e.getMessage());
                }
            }
        }

        // 4. Sau khi đã chụp ảnh xong xuôi, mới an tâm đóng trình duyệt giải phóng luồng
        DriverManager.quitDriver();
    }*/
}
