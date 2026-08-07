package listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import static utils.LogUtils.logger;

/*Đây là phiên bản đơn giản, cơ bản của TestListener, hỗ trợ in ra log của TC nào fail, pass thôi.
Nâng cấp hơn thì sẽ bổ sung thêm các câu lệnh chụp màn hình khi fail. Nâng cấp hơn là dùng dependency Allure report
tích hợp vào class này để thêm tính năng xuất report với data dựa vào method dưới -> đổi tên thành AllureListener
=> về bản chất thì 2 class TestListener và AllureListener là anh em, cách áp dụng để ra log/report cũng giống nhau
C1: Triển khai cấp độ class: Xem tại AdminTest_V5_TestListener.java
    Thêm @Listeners(PackageName.ClassName.class) ngay trước class testcase mình muốn in log/ report. Nhớ chấm class ở
    đuôi. Nếu trỏ đến package rồi thì không cần để package vào cũng được.
C2: Triển khai ITestListener ở cấp độ Suite (ví dụ: testng-testlistener.xml)
    Đầu tiên, xóa chú thích @Listener khỏi class TC đi và thêm nó vào tệp XML. */

public class TestListener implements ITestListener {
    @Override
    public void onFinish(ITestContext result) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onStart(ITestContext result) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onTestFailure(ITestResult result) {
        logger.error("Đây là test case bị fail: " + result.getName());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        logger.warn("Đây là test case bị skip: " + result.getName());
    }

    @Override
    public void onTestStart(ITestResult result) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        logger.info("Đây là test case pass: " + result.getName());
    }
}
