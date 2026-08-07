package listeners;

import driver.DriverManager;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.ITestContext;

import java.io.ByteArrayInputStream;

import static utils.LogUtils.logger;

//Khác với class TestListener cơ bản là có thêm annotation @Attachment của Allure
public class AllureListener implements ITestListener {

    // 1. Hàm tự động chụp ảnh màn hình và đính kèm vào Allure Report
    // Thẻ @Attachment giúp Allure nhận diện mảng byte trả về là một file đính kèm
    @Attachment(value = "Page screenshot on failure", type = "image/png")
    public byte[] saveScreenshotPNG() {
        // Cực kỳ quan trọng: Lấy chính xác Driver từ ThreadLocal của luồng hiện tại đang bị lỗi
        return ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.BYTES);
    }

    // 2. Hàm tự động ghi log văn bản vào Allure Report
    @Attachment(value = "{0}", type = "text/plain")
    public String saveTextLog(String message) {
        return message;
    }
/* // 2.2 Định nghĩa hàm đính kèm log chữ (Stacktrace) nếu muốn xem chi tiết lỗi ngay trên ảnh
    @Attachment(value = "Chi tiết lỗi hệ thống", type = "text/plain")
    public String saveTextLog(String message) {
        return message;
    }*/

    @Override
    public void onTestStart(ITestResult result) {
        logger.info("=== BẮT ĐẦU CHẠY TEST CASE: " + result.getName() + " ===");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        logger.info("=== TEST CASE: " + result.getName() + " -> PASSED ===");
    }

    // Lắng nghe khi bài test bị thất bại
    @Override
    public void onTestFailure(ITestResult result) {
        logger.info("=== TEST CASE: " + result.getName() + " -> FAILED ===");

/*Có 2 cách để đính kèm ảnh/ log vào report allure trên html:
* C1: Viết 2 method @Attachment phía trên, ở event onTestFailure dùng hàm đã viết ghép vào cho đúng logic là được*/
        try {
            // Kiểm tra xem trình duyệt của luồng hiện tại có thực sự đang mở không (Tránh lỗi Null)
            if (DriverManager.getDriver() != null) {
                saveScreenshotPNG(); // Tự động kích hoạt chụp ảnh và đẩy vào Allure của luồng đó
            }
        } catch (Exception e) {
            logger.error("Không thể chụp ảnh màn hình: " + e.getMessage());
        }
        // Đính kèm luôn lý do lỗi (Exception message) vào báo cáo để tiện phân tích
        if (result.getThrowable() != null) {
            saveTextLog(result.getThrowable().getMessage());
        }
    }
/*  C2: chạy bằng hàm Allure.addAttachment() chạy trực tiếp bằng code thuần. Khi pick chạy bằng hàm Allure.addAttachment()
thì sẽ không dùng method có @Attachment phía trên (ẩn đi).
       try {
            // Kiểm tra Driver còn sống
            if (DriverManager.getDriver() != null) {
                logger.info(">>> Đang tiến hành chụp ảnh màn hình từ Listener...");

                // 1. Chụp ảnh lấy mảng byte trực tiếp từ Selenium
                byte[] screenshotBytes = ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.BYTES);

                // 2. Ép Allure ghi file ảnh này xuống đĩa cứng ngay lập tức không qua trung gian
                Allure.addAttachment("Page screenshot on failure", "image/png", new ByteArrayInputStream(screenshotBytes), ".png");

                logger.info(">>> ĐÃ ĐẨY XONG ẢNH VÀO THƯ MỤC ALLURE-RESULTS! <<<");
            } else {
                logger.info(">>> KHÔNG THỂ CHỤP ẢNH: DriverManager.getDriver() đang bị NULL");
            }
        } catch (Exception e) {
            logger.info(">>> LỖI PHÁT SINH KHI CHỤP ẢNH: " + e.getMessage());
            e.printStackTrace();
        }

        // Đính kèm Log text lỗi vào Allure
        if (result.getThrowable() != null) {
            Allure.addAttachment("Error Log", "text/plain", result.getThrowable().getMessage(), ".txt");
        }
    }*/


    @Override
    public void onTestSkipped(ITestResult result) {
        logger.info("=== TEST CASE: " + result.getName() + " -> SKIPPED ===");
    }

    @Override
    public void onStart(ITestContext context) {
        logger.info("=== BẮT ĐẦU CHẠY SUITE: " + context.getName() + " ===");
    }

    @Override
    public void onFinish(ITestContext context) {
        logger.info("=== HOÀN THÀNH CHẠY SUITE: " + context.getName() + " ===");
    }
}

