package pageobject;

import driver.DriverManager;
import pages.AdminPage;
import pages.SideMenu;
import pages.EditUserPage;
import pages.LoginPage;

public class PageManager {
/*Sử dụng kỹ thuật Lazy Initialization qua hàm Getter.
Mỗi khi gọi page.loginPage(), một instance mới đi kèm driver chuẩn của luồng sẽ được sinh ra*/

    public LoginPage loginPage() {
        return new LoginPage(DriverManager.getDriver());
    }

    public SideMenu commonPage() {
        return new SideMenu(DriverManager.getDriver());
    }

    public AdminPage adminPage() {
        return new AdminPage(DriverManager.getDriver());
    }

    public EditUserPage editUserPage() {
        return new EditUserPage(DriverManager.getDriver());
    }

/*Sau này có thêm Page mới (ví dụ DashboardPage), bạn chỉ việc thêm 1 dòng ở đây:
public DashboardPage dashboardPage() { return new DashboardPage(DriverManager.getDriver()); }*/
}
