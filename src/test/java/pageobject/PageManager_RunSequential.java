package pageobject;

import drivers.DriverManager;
import org.openqa.selenium.WebDriver;
import pages.*;

/*Đây là phiên bản basic, dành cho việc run test tuần tự, ko parallel nên sẽ ko dùng ThreadLocal -> dùng như 1 PageObject
bình thường, có khai báo WebDriver chứ không lazy initiation đợi nhận khởi tạo như class PageManager được */
public class PageManager_RunSequential {
    private final WebDriver driver;

    // Nhận driver từ BaseTest truyền vào khi test bắt đầu chạy
    public PageManager_RunSequential(WebDriver driver) {
        this.driver = driver;
    }

    // Đảm bảo Lazy Initialization: Chỉ tạo mới LoginPage khi hàm này được gọi
    public LoginPage loginPage() {
        return new LoginPage(driver);
    }

    public SideMenu commonPage() {
        return new SideMenu(driver);
    }

    public AdminPage adminPage() {
        return new AdminPage(driver);
    }

    public EditUserPage editUserPage() {
        return new EditUserPage(driver);
    }

    public JobPage jobPage() {
        return new JobPage(driver);
    }

}
