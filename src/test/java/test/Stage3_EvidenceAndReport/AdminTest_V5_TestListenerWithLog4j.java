package test.Stage3_EvidenceAndReport;

import base.BaseTest_UsingDriverManager;
import data.AdminPageData;
import listeners.TestListener;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pageobject.PageManager;
import utils.ConfigReader;

import java.util.List;

import static utils.LogUtils.logger;
/*Đã làm được trong class này:
- Thêm Logger để in log dễ nhìn:
    + Các bước setup: Thêm dependency log4j-api + log4j-core -> Tạo file log4j2.xml trong src/main/resources -> Tạo
    class LogUtils từ class có sẵn Logger của log4j -> ở class hay page muốn dùng thì ko khai báo, no new, dùng thẳng vì
    method là public static void)
    + Các loại log và nên thêm vào class nào: TRACE, logger.debug (Class BasePage), logger.info (trong các page object,
    class test), WARN, logger.error (Ở các hàm try catch, để ở vế catch để bắt exception)
    + Cách để ẩn/ hiện log level tùy ý: sửa dòng <Root level="..."> trong log4j2.xml là được. Xem kỹ hơn tại log4j2.xml
- Thêm class TestListener (src/test/java/listeners/TestListener.java) implements ITestListener có sẵn của selenium. Khi dùng
    C1: chỉ cần thêm annotation @Listener (tênClassListener.class) trước class (ví dụ là class hiện tại)
    C2: dùng ở cấp độ Suite (ví dụ: testng-testlistener.xml)
    Xóa chú thích @Listener khỏi class TC đi -> thêm tag listeners + class name muốn chạy vào tệp XML -> run file xml đó.
!!!Cần thêm report -> V6*/

@Listeners(TestListener.class)
public class AdminTest_V5_TestListenerWithLog4j extends BaseTest_UsingDriverManager {
    private final PageManager page = new PageManager();

    @Test(dataProvider = "usernameKeywords", dataProviderClass = AdminPageData.class)
    public void TC01_UsernameSearchBox(String keyword, boolean expectedResult) throws InterruptedException {
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

    @Test(dataProvider = "userRoleDataV2", dataProviderClass = AdminPageData.class)
    public void TC02_UserRole(String role, boolean expectedResult) {
        SoftAssert softAssert = new SoftAssert();

        page.loginPage().login(ConfigReader.getPropValue("username"), ConfigReader.getPropValue("password"));
        page.commonPage().clickAdmin();
        logger.info("Bắt đầu test với role = " + role);
        page.adminPage().clickUserRole();
        boolean actualResult = page.adminPage().isUserRoleDisplayed(role);
        Assert.assertEquals(actualResult, expectedResult);
        if (actualResult) {
            page.adminPage().selectUserRole(role);
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
    public void TC03_EmployeeName_valid(String keyword) {
        SoftAssert softAssert = new SoftAssert();

        page.loginPage().login(ConfigReader.getPropValue("username"), ConfigReader.getPropValue("password"));
        page.commonPage().clickAdmin();
        page.adminPage().typeName(keyword);
        if (page.adminPage().hasSearchResult()) {
            List<String> suggestNames = page.adminPage().getSearchNames();
            for (String name : suggestNames) {
                softAssert.assertTrue((name.toLowerCase()).contains(keyword.toLowerCase()), "Suggest name doesn't contain keyword");
            }
            logger.info("(DropDown) Số kết quả tìm kiếm đề xuất là cụ thể cho keyword " + keyword + " là \n" + suggestNames);
            softAssert.assertAll();
            String selectedName = page.adminPage().getOptFirstName();
            page.adminPage().selectFirstName();
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
}

