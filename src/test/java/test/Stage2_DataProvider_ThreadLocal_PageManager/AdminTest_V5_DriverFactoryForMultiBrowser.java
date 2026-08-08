package test.Stage2_DataProvider_ThreadLocal_PageManager;

import base.BaseTest_UsingDriverFactory;
import data.AdminPageData;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pageobject.PageManager;
import utils.ConfigReader;

import java.util.List;

import static utils.LogUtils.logger;
/*Đã làm được trong class này:
B1: Tạo class DriverFactory ở src/test/java/drivers/DriverFactory để viết method chạy parallel trên nhiều luồng + trên
nhiều browser khác nhau (edge, chrome, firefox)
B2: Tạo class BaseTest (BaseTest_UsingDriverFactory) src/test/java/base/BaseTest_UsingDriverFactory dành riêng cho
driver tạo ra từ DriverFactory.
B3: Tạo class test kế thừa từ BaseTest_UsingDriverFactory. Khi muốn tương tác với browser, gọi DriverFactory.getDriver()
mà cũng chỉ dùng trong class test ở hàm new mới page object với tham số đầu vào là driver thôi. Xem tại AdminTest_V3_DataProviderParallelWithThreadLocalDriverManager
    Ở class dưới vì mình đã dùng PageManager tự động new mới tất cả pageObject rồi nên muốn chỉnh việc này thì vào class
    PageManager chỉnh lại driver đầu vào thành DriverFactory thôi. (đã note ở class PageManager). Code giữ nguyên
B4: Tạo file suites/testng-parallelwithmultibrowsers.xml để run test parallel trên nhiều browser.
    Lúc này các value trong parameter trên file testng sẽ map vào @Parameter ở class BaseTest_UsingDriverFactory
    -> truyền dữ liệu vào run class test hiện tại
*/

public class AdminTest_V5_DriverFactoryForMultiBrowser extends BaseTest_UsingDriverFactory {
    private final PageManager page = new PageManager();

    @Test(dataProvider = "usernameKeywords", dataProviderClass = AdminPageData.class)
    public void TC02_UsernameSearchBox(String keyword, boolean expectedResult) throws InterruptedException {
        SoftAssert softAssert = new SoftAssert();
        page.loginPage().login(ConfigReader.getPropValue("username"), ConfigReader.getPropValue("password"));
        page.commonPage().clickAdmin();
        logger.info("Bắt đầu test với keyword = " + keyword);
        page.adminPage().searchUsername(keyword);
        Thread.sleep(2000);
        boolean actualResult = page.adminPage().isSearchResultDisplayed2(keyword);
        Assert.assertEquals(actualResult, expectedResult);
        if (actualResult) {
            List<String> users = page.adminPage().getUsernameSearchList(keyword);
            for (String user : users) {
                softAssert.assertEquals(user, keyword);
                logger.info("Kết quả tìm kiếm hiện có với username keyword " + keyword + "là " + user);
            }
            softAssert.assertAll();
        } else logger.info("với username keyword " + keyword + " không có kết quả tìm kiếm");
    }
}

/*    @Test(dataProvider = "userRoleDataV2", dataProviderClass = AdminPageData.class)
    public void TC03_UserRole(String role, boolean expectedResult) {
        SoftAssert softAssert = new SoftAssert();

        page.loginPage().login(ConfigReader.getPropValue("username"), ConfigReader.getPropValue("password"));
        page.commonPage().clickAdmin();
        logger.info("Bắt đầu test với role = " + role);
        page.adminPage().clickUserRole();
        boolean actualResult = page.adminPage().isUserRoleDisplayed(role);
        // Assert kết quả sau mỗi lượt chạy
        Assert.assertEquals(actualResult, expectedResult);
        //Check xem trong kết quả có trùng với keyword
        if (actualResult) {
            page.adminPage().selectUserRole(role);
            //Lấy list name của kết quả để khi in ra log sẽ hiện những username có role tương ứng. Còn roles
            //thì vẫn phải chạy for để xác nhận xem khớp vs điều kiện tìm kiếm chưa
            List<String> names = page.adminPage().getNameSearchResult();
            List<String> roles = page.adminPage().getRoleSearchResult();
            int quan = 0;
            for (String eachRole : roles) {
                quan++;
                softAssert.assertEquals(eachRole, role);
            }
            logger.info("Số kết quả tìm kiếm hiện có với role = " + role + " là " + quan + " và kết quả là \n" + names);
            softAssert.assertAll();
        } else logger.info("Với role " + role + " không tồn tại nên không có kết quả");
        logger.info("Kết thúc test với role = " + role);
    }

    @Test(dataProvider = "employeeNameValidKeywordsV2", dataProviderClass = AdminPageData.class)
    public void TC04_EmployeeName_valid(String keyword) {
        SoftAssert softAssert = new SoftAssert();

        page.loginPage().login(ConfigReader.getPropValue("username"), ConfigReader.getPropValue("password"));
        page.commonPage().clickAdmin();
        page.adminPage().typeName(keyword);
        if (page.adminPage().hasSearchResult()) {
            //Check xem trong list gợi ý có trùng với keyword
            List<String> suggestNames = page.adminPage().getSearchNames();
            for (String name : suggestNames) {
                softAssert.assertTrue((name.toLowerCase()).contains(keyword.toLowerCase()), "Suggest name doesn't contain keyword");
            }
            logger.info("(DropDown) Số kết quả tìm kiếm đề xuất là cụ thể cho keyword " + keyword + " là \n" + suggestNames);
            softAssert.assertAll();
            //Lấy text của lựa chọn 1, sau đó Select lựa chọn đầu tiên
            String selectedName = page.adminPage().getOptFirstName();
            page.adminPage().selectFirstName();
            //Nếu xuất hiện message thông báo ko có user tồn tại thì in ra thẳng luôn "ko có kết quả".
            // Nếu ko có msg thì tiến hành compare từ khóa với kết quar trong table
            boolean isRecordDisplayed = page.adminPage().isMsgNoRecordsFoundDisplayed(selectedName);
            if (!isRecordDisplayed) {
                //Check xem trong table result
                page.adminPage().getNameSearchResult();
                List<String> searchNameList = page.adminPage().getNameSearchResult();
                for (String name : searchNameList) {
                    softAssert.assertEquals(name, selectedName, "Result name doesn't match the searched name");
                }
                logger.info("(Table) Số kết quả tìm kiếm đề xuất là cụ thể cho " + selectedName + " là \n" + searchNameList);
                softAssert.assertAll();
            } else logger.info("Không có kết quả tìm kiếm cho " + selectedName);
        }
    }
}*/

