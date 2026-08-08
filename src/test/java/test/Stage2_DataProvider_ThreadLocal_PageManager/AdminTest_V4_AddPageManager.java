package test.Stage2_DataProvider_ThreadLocal_PageManager;

import base.BaseTest_UsingDriverManager;
import data.AdminPageData;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pageobject.PageManager;
import utils.ConfigReader;

import java.util.List;

import static utils.LogUtils.logger;
/*Đã làm được trong class này:
- Tạo PageManager ở src/test/java/pageobject để hết những method khởi tạo + new mới mọi Page hiện có vào
- Refactor tại class PageTest: khai báo + new pageObject, gọi hàm của 1 page nào đó qua PageManager
!!!Lúc này khi run test đã gần OK -> thêm report + nếu chạy parallel trên nhiều trình duyệt như thế nào*/

public class AdminTest_V4_AddPageManager extends BaseTest_UsingDriverManager {
/*B1: Khai báo PageManager (nơi chứa new tất cả các page) ngay ngoài rìa Class Test*/
    private final PageManager page = new PageManager();

    @Test(dataProvider = "usernameKeywords", dataProviderClass = AdminPageData.class)
    public void TC02_UsernameSearchBox(String keyword, boolean expectedResult) throws InterruptedException {
        // Tự tạo SoftAssert cục bộ trong hàm để an toàn luồng
        SoftAssert softAssert = new SoftAssert();

/*B2: xóa phần khai báo + new trong @Test. Lúc này gọi qua 'page' thay vì 'new' thủ công từng Page
        AdminPage adminPage = new AdminPage(DriverManager.getDriver());
        CommonPage commonPage = new CommonPage(DriverManager.getDriver());
        LoginPage loginPage = new LoginPage(DriverManager.getDriver());*/
/*B3: Đổi cách gọi hàm ở từng pageObject bằng cách thay biến page đó với method của PageManager với tên màn hình tương ứng
        loginPage.login(ConfigReader.getPropValue("username"), ConfigReader.getPropValue("password"));
        -> page.loginPage().login(...)
        commonPage.clickAdmin();
        -> page.commonPage().clickAdmin();
        adminPage.searchUsername(keyword);
        -> page.adminPage().searchUsername(keyword);*/

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
}

