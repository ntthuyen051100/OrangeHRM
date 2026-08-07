package listeners;

import driver.DriverManager;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.ITestContext;

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
/*    // 2.2 Định nghĩa hàm đính kèm log chữ (Stacktrace) nếu muốn xem chi tiết lỗi ngay trên ảnh
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

