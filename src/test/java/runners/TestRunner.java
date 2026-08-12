package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features",         // Đường dẫn tới thư mục chứa các file kịch bản (.feature)
        glue = {"stepdefinitions", "hooks"},              // Đường dẫn tới package chứa mã xử lý step và hooks
        plugin = {
                "pretty",
                "html:target/cucumber-reports/cucumber.html", // Xuất báo cáo HTML mặc định của Cucumber
                "json:target/cucumber-reports/cucumber.json"
        },
        monochrome = true                                 // Định dạng console log dễ đọc hơn
)
public class TestRunner extends AbstractTestNGCucumberTests {
/*    // Cấu hình dưới đây cho phép bạn chạy song song (Parallel) các Scenario bằng TestNG (tùy chọn)
    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }*/
}
/*-----------------------------------------------------------
@CucumberOptions annotation đóng vai trò như một "bộ điều khiển trung tâm". Nó giúp cấu hình mọi cài đặt từ việc tìm file
 test ở đâu, chạy những test case nào, cho đến việc xuất báo cáo (report) ra sao.
1. features (Đường dẫn đến file Gherkin)
- Ý nghĩa: Chỉ định đường dẫn tới thư mục hoặc file .feature chứa các kịch bản test bằng ngôn ngữ tự nhiên.
- Ví dụ: features = "src/test/resources/features" (Quét toàn bộ file trong thư mục này) hoặc chỉ định đích danh một
        file: features = "src/test/resources/features/login.feature".
2. glue (Đường dẫn kết nối code Java)
- Ý nghĩa: Chỉ định (các) package chứa code Java định nghĩa cho các step (StepDefinitions) và các hàm Setup/Teardown (Hooks).
Nếu thiếu key này, Cucumber sẽ báo lỗi không tìm thấy code thực thi (Undefined Steps).
- Ví dụ: glue = {"stepdefinitions", "com.example.hooks"}.
Nếu class StepDef nằm trong package "src/test/java", lúc này thư mục này nó hiểu là thư mục gốc, và không đặt nó nằm trong
package con nào nữa, thì có thể chổ này để trống "".
Nếu chúng ta để nó vào các package con thì phải ghi theo cú pháp ví dụ: "com.orangehrm.stepdefinitions" lưu ý là dùng
dấu chấm để phân cách các package.
3. plugin (Xuất báo cáo & Định dạng đầu ra)
- Ý nghĩa: Đăng ký các bộ tạo báo cáo (Reporters) hoặc các công cụ định dạng dữ liệu đầu ra khi chạy test.
- Các giá trị phổ biến:
    + "pretty": In chi tiết từng bước chạy của file feature lên console (màn hình log) trực quan, dễ nhìn.
    + "html:target/cucumber-report.html": Xuất ra file báo cáo dạng giao diện HTML mặc định của Cucumber.
    + "json:target/cucumber.json": Xuất ra file cấu trúc JSON (thường dùng để tích hợp với các công cụ tạo report xịn
      hơn như ExtentReport, Allure Report, hoặc đẩy lên Jenkins).
4. tags (Bộ lọc kịch bản test)
- Ý nghĩa: Giúp bạn chọn lọc chính xác những Scenario nào được phép chạy hoặc bị bỏ qua dựa trên các thẻ @ gán ở file feature.
- Ví dụ:
  + tags = "@Smoke" (Chỉ chạy các kịch bản có gắn thẻ @Smoke).
  + tags = "not @Regression" (Chạy tất cả, trừ những bài test có thẻ @Regression).
  + tags = "@Login and @Positive" (Chạy kịch bản thỏa mãn cả 2 thẻ).
5. monochrome (Làm sạch Log Console)
- Ý nghĩa: Nhận giá trị true hoặc false. Nếu đặt là true, nó sẽ tắt các ký tự mã màu (mã ANSI) khó đọc trên Console,
giúp log kết quả test hiển thị dưới dạng văn bản thuần túy, sạch sẽ và dễ đọc hơn trên một số IDE hoặc hệ thống CI/CD (như Jenkins).
- Ví dụ: monochrome = true.
----dưới đây thâý ít dùng
6. dryRun (Kiểm tra nhanh cú pháp)
- Ý nghĩa: Nhận giá trị true hoặc false (mặc định là false).
  + Khi đặt dryRun = true, Cucumber không kích hoạt browser/Selenium, mà chỉ quét qua toàn bộ file Feature xem có Step
  nào chưa được viết code Java (StepDefinition) hay không.
  + Giúp bạn kiểm tra nhanh (chỉ mất 1-2 giây) xem dự án có bị sót hay lệch từ ngữ nào giữa file Feature và code Java trước khi chạy thật.
7. objectFactory (Quản lý Dependency Injection)
- Ý nghĩa: Chỉ định class làm nhiệm vụ khởi tạo và chia sẻ dữ liệu giữa các Class Step Definitions (Dependency Injection)
 như PicoContainer, Spring, hoặc Guice. Nếu bạn dùng chia sẻ biến driver bằng PicoContainer, bạn có thể cấu hình tại đây
  (tuy nhiên thông thường Cucumber sẽ tự nhận diện nếu bạn đã thêm dependency vào pom.xml).
8. snippets (Kiểu sinh code mẫu)
- Ý nghĩa: Định dạng kiểu đặt tên hàm khi Cucumber tự động gợi ý code mẫu cho các Step chưa định nghĩa.
- Giá trị:
  + CucumberOptions.SnippetType.CAMELCASE: Sinh tên hàm kiểu iShouldSeeTheMenu().
  + CucumberOptions.SnippetType.UNDERSCORE: Sinh tên hàm kiểu i_should_see_the_menu().
*/
