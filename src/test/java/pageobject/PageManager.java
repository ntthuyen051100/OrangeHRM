package pageobject;

import drivers.DriverFactory;
import drivers.DriverManager;
import pages.AdminPage;
import pages.SideMenu;
import pages.EditUserPage;
import pages.LoginPage;

public class PageManager {
/*Sử dụng kỹ thuật Lazy Initialization qua hàm Getter.
Mỗi khi gọi page.loginPage(), một instance mới đi kèm driver chuẩn của luồng sẽ được sinh ra*/

/*    Tùy vào mình muốn chạy parallel trên driver tiêu chuẩn là chrome thì dùng DriverManager,
nếu muốn dùng browser khác thì dùng DriverFactory*/
    public LoginPage loginPage() {
/*        return new LoginPage(DriverManager.getDriver());*/
        return new LoginPage(DriverFactory.getDriver());
    }

    public SideMenu commonPage() {
/*        return new SideMenu(DriverManager.getDriver());*/
        return new SideMenu(DriverFactory.getDriver());
    }

    public AdminPage adminPage() {
/*        return new AdminPage(DriverManager.getDriver());*/
        return new AdminPage(DriverFactory.getDriver());
    }

    public EditUserPage editUserPage() {
/*        return new EditUserPage(DriverManager.getDriver());*/
        return new EditUserPage(DriverFactory.getDriver());
    }

/*Sau này có thêm Page mới (ví dụ DashboardPage), bạn chỉ việc thêm 1 dòng ở đây:
public DashboardPage dashboardPage() { return new DashboardPage(DriverManager.getDriver()); }*/
}
