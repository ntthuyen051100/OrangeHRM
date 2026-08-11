package pageobject;

import drivers.DriverFactory;
import drivers.DriverManager;
import pages.*;

/*Đây là phiên bản PageManager dành riêng cho việc chạy parallel của DriverManager, chạy parallel trên
nhiều browser DriverFactory*/
public class PageManager {
/*Sử dụng kỹ thuật Lazy Initialization qua hàm Getter.
Mỗi khi gọi page.loginPage(), một instance mới đi kèm driver chuẩn của luồng sẽ được sinh ra*/
/*Tại đây sẽ KHÔNG KHAI BÁO Driver gì cả, không giống như các class Page khác sẽ là
    Webdriver driver;
    ThreadLocal<WebDriver> driver = new ThreadLocal<>();
Cũng không extends BasePage để dùng constructor khởi tạo cứng driver này => chắc chắn sẽ dính lỗi NullPointerException ngay lập tức.
    public AdminPage(WebDriver driver) {super(driver);}

=> Dù có khai báo biến ngoài @Test (đồng nghĩa với sẽ tạo biến trước cả @BeforeMethod của BaseTest (chứa việc khởi tạo
driver) thì
    - Khi TestNG load class AdminTest_V5_...: Nó thực thi lệnh new PageManager(). Lúc này Java chỉ cấp phát một vùng nhớ
cho đối tượng PageManager. Nó KHÔNG hề chạy các hàm bên trong như loginPage() hay commonPage().
     - Khi vào đến @BeforeMethod: Hệ thống chạy DriverFactory.setDriver(browser) → Lúc này Driver mới chính thức được tạo
và đưa vào ThreadLocal.
     - Khi vào đến @Test: Bạn gọi page.loginPage(). Lúc này dòng lệnh (1) mới chính thức kích hoạt. Nó đi tìm
DriverFactory.getDriver(). Vì @BeforeMethod đã chạy trước đó rồi, nên lúc này Driver đã tồn tại → Kết quả là KHÔNG BỊ NULL.
*/

/*    Tùy vào mình muốn chạy parallel trên driver tiêu chuẩn là chrome thì dùng DriverManager,
nếu muốn dùng browser khác thì dùng DriverFactory*/
    public LoginPage loginPage() {
        return new LoginPage(DriverManager.getDriver());
/*        return new LoginPage(DriverFactory.getDriver());*/
    }

    public SideMenu commonPage() {
        return new SideMenu(DriverManager.getDriver());
/*        return new SideMenu(DriverFactory.getDriver());*/
    }

    public AdminPage adminPage() {
        return new AdminPage(DriverManager.getDriver());
/*        return new AdminPage(DriverFactory.getDriver());*/
    }

    public EditUserPage editUserPage() {
        return new EditUserPage(DriverManager.getDriver());
/*        return new EditUserPage(DriverFactory.getDriver());*/
    }
    public JobPage jobPage () {
        return new JobPage(DriverManager.getDriver());
        /*        return new EditUserPage(DriverFactory.getDriver());*/
    }

/*Sau này có thêm Page mới (ví dụ DashboardPage), bạn chỉ việc thêm 1 dòng ở đây:
public DashboardPage dashboardPage() { return new DashboardPage(DriverManager.getDriver()); }*/
}
