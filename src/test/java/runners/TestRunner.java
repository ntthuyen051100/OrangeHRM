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
