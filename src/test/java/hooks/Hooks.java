package hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

//Thay thế cho class BaseTest ở testNG
public class Hooks {
    private static WebDriver driver;

    @Before
    public void setUp() {
        // Khởi tạo WebDriver (có thể tích hợp với WebDriverManager hoặc DriverFactory sẵn có của bạn)
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    public static WebDriver getDriver() {
        return driver;
    }
}
/*Cucumber Hooks nó giống như các Annotation trong TestNG thuần, cho phép chúng ta thực hiện các kịch bản hoặc thử nghiệm
 của mình theo một thứ tự và cho phép thiết lập các nội dung trong chính các bước thứ tự ấy.
Cụ thể trong Cucumber Hooks hỗ trợ chúng ta các ghi chú sau: @BeforeAll, @AfterAll, @Before, @After, @BeforeStep @AfterStep
✅ Các ghi chú trong Hooks
🔆 @BeforeAll và @AfterAll: sử dụng trạng thái static cho phương thức, không giống như các hook khác.
Trong phiên bản Cucumber 7.0.0 về sau thì BeforeAll và AfterAll được triển khai. BeforeAll chạy trước khi bất kỳ kịch bản
 nào được chạy và AfterAll chạy sau khi tất cả các kịch bản đã được thực thi.
@BeforeAll và @AfterAll tương tự như các chú thích @BeforeSuite và @AfterSuite của TestNG. Bạn có thể sử dụng các móc nối
 toàn cầu này khi cần thiết lập hoặc dọn dẹp toàn cục cái gì đó. Ví dụ: thiết lập các biến môi trường, cơ sở dữ liệu hoặc cấu hình reports và dọn dẹp các cookies...
🔆 @Before:
thực thi trước mọi kịch bản. ví dụ có thể khởi động trình duyệt trước mọi tình huống
🔆 @After
thực thi sau mọi kịch bản. Vd có thể tắt trình duyệt trước mọi tình huống
🔆 @BeforeStep
thực thi trước mỗi bước (mỗi step). Có thể sử dụng chú thích này để chụp ảnh màn hình trước mỗi bước hoặc ghi logs và bước trong reports
🔆 @AfterStep
thực thi sau mỗi bước. Có thể sử dụng chú thích này để chụp ảnh màn hình sau mỗi bước thất bại hoặc thành công tuỳ mình
đặt điều kiện hoặc ghi logs và bước trong reports
🔆 Thông số Scenario
Các phương thức được chú thích bằng hook annotation có thể chấp nhận một tham số kiểu Scenario:
@After
public void afterScenario(Scenario scenario) {
    // some code
}
Đối tượng của Scenario chứa thông tin về kịch bản hiện tại. Bao gồm tên kịch bản, số bước, tên của các bước và trạng thái
 (đạt hoặc không đạt). Điều này có thể hữu ích nếu chúng ta muốn thực hiện các hành động khác nhau đối với các bài kiểm
 tra đạt và không đạt. Có thể chụp màn hình hay ghi vào report chẳng hạn.

✅ Thực thi có điều kiện với các ghi chú
Hooks được xác định trên cả global và ảnh hưởng đến tất cả các kịch bản và bước. Tuy nhiên, với sự trợ giúp của các thẻ
thuộc tính Cucumber, chúng ta có thể xác định chính xác kịch bản nào của hook sẽ được thực hiện trước sau và gọi đúng điều kiện.
@Before(order=2, value="@Screenshots")
public void beforeScenario() {
    takeScreenshot();
}

Ví dụ trên thì nó sẽ chạy thứ 2 nghĩa là sau một @Before khác. Và nó sẽ áp dụng đối với Scenario nào có Tag là @Screenshot
*/
