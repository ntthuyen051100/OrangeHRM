package test;

import base.BaseTest_UsingDriverManager;
import data.AdminPageData;
import driver.DriverManager;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.AdminPage;
import pages.SideMenu;
import pages.LoginPage;
import utils.ConfigReader;

import java.util.List;

import static utils.LogUtils.logger;
/*Đã làm được trong class này:
- Tại AdminPageData thêm parallel = true tại @DataProvider tại những data cho phép chạy parallel, khi chạy test những method
dùng data này thì có bao nhiêu data sẽ chạy hết trong 1 lần, ở method ko thay đổi nội dung gì
- Tạo Class DriverManager (để từng WebDriver chạy trong từng thread khác nhau, ko đè đữ liệu lẫn nhau khi chạy song song)
tiếp theo tạo 1 class BaseTest (để cho từng web driver vào threadLoacal)
- Khai báo + new mới page object dùng driver trong threadLocal thay vì driver bth + đặt trong từng method @test (lý giải ở dưới)
- Tại file testng.xml để thêm parallel = "methods" thread-count="2" trong div suite name -> khi run file testng thì sẽ 2 methods cùng 1 lần
!!!Tuy nhiên phải lặp lại việc new mới từng page object trong mỗi method @test -> V4: PageManager */

public class AdminTest_V3_DataProviderParallelWithThreadLocal extends BaseTest_UsingDriverManager {
/* !!!CHÚ Ý: khi set chạy parallel thì phải tiến hành khai báo + new mới page object trong từng method @test
thì mới đảm bảo được tính độc lập dữ liệu trong từng thread.
-> như dưới đây là ko được
    AdminPage adminPage;
    SoftAssert softAssert;
    CommonPage commonPage;
    EditUserPage editUserPage;
    LoginPage loginPage;

    @BeforeMethod
    public void LoginSuccess() {
         softAssert = new SoftAssert();
         adminPage = new AdminPage(DriverManager.getDriver());
         commonPage = new CommonPage(DriverManager.getDriver());
         loginPage = new LoginPage(DriverManager.getDriver());
        loginPage.login(ConfigReader.getPropValue("username"), ConfigReader.getPropValue("password"));
        commonPage.clickAdmin();
    }*/

    @Test(dataProvider = "usernameKeywordsV2", dataProviderClass = AdminPageData.class)
    public void TC02_UsernameSearchBox(String keyword, boolean expectedResult) throws InterruptedException {
        SoftAssert softAssert = new SoftAssert();
        AdminPage adminPage = new AdminPage(DriverManager.getDriver());
        SideMenu sideMenu = new SideMenu(DriverManager.getDriver());
        LoginPage loginPage = new LoginPage(DriverManager.getDriver());
        loginPage.login(ConfigReader.getPropValue("username"), ConfigReader.getPropValue("password"));
        sideMenu.clickAdmin();
        logger.info("Bắt đầu test với keyword = " + keyword);
        adminPage.searchUsername(keyword);
        Thread.sleep(2000);
        boolean actualResult = adminPage.isSearchResultDisplayed2(keyword);
        Assert.assertEquals(actualResult, expectedResult);
        if (actualResult) {
            List<String> users = adminPage.getUsernameSearchList(keyword);
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
        AdminPage adminPage = new AdminPage(DriverManager.getDriver());
        SideMenu sideMenu = new SideMenu(DriverManager.getDriver());
        LoginPage loginPage = new LoginPage(DriverManager.getDriver());
        loginPage.login(ConfigReader.getPropValue("username"), ConfigReader.getPropValue("password"));
        sideMenu.clickAdmin();
        logger.info("Bắt đầu test với role = " + role);
        adminPage.clickUserRole();
        boolean actualResult = adminPage.isUserRoleDisplayed(role);
        // Assert kết quả sau mỗi lượt chạy
        Assert.assertEquals(actualResult, expectedResult);
        //Check xem trong kết quả có trùng với keyword
        if (actualResult) {
            adminPage.selectUserRole(role);
            //Lấy list name của kết quả để khi in ra log sẽ hiện những username có role tương ứng. Còn roles
            //thì vẫn phải chạy for để xác nhận xem khớp vs điều kiện tìm kiếm chưa
            List<String> names = adminPage.getNameSearchResult();
            List<String> roles = adminPage.getRoleSearchResult();
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
        AdminPage adminPage = new AdminPage(DriverManager.getDriver());
        SideMenu sideMenu = new SideMenu(DriverManager.getDriver());
        LoginPage loginPage = new LoginPage(DriverManager.getDriver());
        loginPage.login(ConfigReader.getPropValue("username"), ConfigReader.getPropValue("password"));
        sideMenu.clickAdmin();
        adminPage.typeName(keyword);
        if (adminPage.hasSearchResult()) {
            //Check xem trong list gợi ý có trùng với keyword
            List<String> suggestNames = adminPage.getSearchNames();
            for (String name : suggestNames) {
                softAssert.assertTrue((name.toLowerCase()).contains(keyword.toLowerCase()), "Suggest name doesn't contain keyword");
            }
            logger.info("(DropDown) Số kết quả tìm kiếm đề xuất là cụ thể cho keyword " + keyword + " là \n" + suggestNames);
            softAssert.assertAll();
            //Lấy text của lựa chọn 1, sau đó Select lựa chọn đầu tiên
            String selectedName = adminPage.getOptFirstName();
            adminPage.selectFirstName();
            //Nếu xuất hiện message thông báo ko có user tồn tại thì in ra thẳng luôn "ko có kết quả".
            // Nếu ko có msg thì tiến hành compare từ khóa với kết quar trong table
            boolean isRecordDisplayed = adminPage.isMsgNoRecordsFoundDisplayed(selectedName);
            if (!isRecordDisplayed) {
                //Check xem trong table result
                adminPage.getNameSearchResult();
                List<String> searchNameList = adminPage.getNameSearchResult();
                for (String name : searchNameList) {
                    softAssert.assertEquals(name, selectedName, "Result name doesn't match the searched name");
                }
                logger.info("(Table) Số kết quả tìm kiếm đề xuất là cụ thể cho " + selectedName + " là \n" + searchNameList);
                softAssert.assertAll();
            } else logger.info("Không có kết quả tìm kiếm cho " + selectedName);
        }
    }
}

