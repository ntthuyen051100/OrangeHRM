package test;

import base.BaseTest;
import data.AdminPageData;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.AdminPage;
import pages.SideMenu;
import pages.EditUserPage;
import pages.LoginPage;
import utils.ConfigReader;

import java.util.List;

import static utils.LogUtils.logger;
/*Đã tạo 1 class chỉ chứa data AdminPageData, dùng annotation @DataProvider ở class data, ở @test cần dùng thêm nội dung
sau @Test khai báo name của DataProvider tương ứng, class data tương ứng. Lúc này 1 method có thể chạy nhiều data liên tục
-> Tuy nhiên nếu muốn khi chạy method trong 1 lần với tất cả data đó -> V3: Parallel + ThreadLocal  */

public class AdminTest_V2_RunSequentialWithDataProvider extends BaseTest {
    AdminPage adminPage;
    SoftAssert softAssert;
    SideMenu sideMenu;
    EditUserPage editUserPage;

    @BeforeMethod
    public void LoginSuccess() {
       softAssert = new SoftAssert();
        adminPage = new AdminPage(driver);
        sideMenu = new SideMenu(driver);
        editUserPage = new EditUserPage(driver);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(ConfigReader.getPropValue("username"), ConfigReader.getPropValue("password"));
        sideMenu.clickAdmin();
    }

    // Gọi chính xác tên DataProvider và Class chứa nó
    @Test(dataProvider = "usernameKeywords", dataProviderClass = AdminPageData.class)
    public void TC01_UsernameSearchBox(String keyword, boolean expectedResult) throws InterruptedException {
        logger.info("Bắt đầu test với role = " + keyword);
        adminPage.searchUsername(keyword);
        Thread.sleep(2000);
/*Cách 1: Trong trường hợp CHỈ CHECK DATA VALID thôi thì ntn là ok. Tại sao data NG ko dùng cách này được? Vì lúc này
 data mình đưa thẳng vào parameter của locator rồi. Thì khi tới lệnh isSearchResultDisplayed dù mình mong muốn
 ra false (aka ko tìm được kết quả) cũng ko được vì locator không dò được -> lỗi timeout.
 Kỹ hơn: Nếu muốn test invalid thì không dùng dropdown, mà dùng control cho phép nhập tự do (textbox, autocomplete...)
 hoặc mock dữ liệu. Với dropdown chuẩn, người dùng không thể chọn một giá trị không tồn tại,
 nên việc đưa "ABC" vào DataProvider để click là không phản ánh đúng luồng sử dụng của ứng dụng.

        boolean actualResult = adminPage.isSearchResultDisplayed();
        Assert.assertEquals(actualResult, expectedResult);
        if (actualResult) {
            List<String> users = adminPage.getUsernameSearchList(keyword);
            for (String user : users) {
                softAssert.assertEquals(user, keyword);-
                logger.info("Kết quả tìm kiếm hiện có với username keyword "+keyword+"là " + user);
            }
            softAssert.assertAll();
        }else logger.info("với username keyword "+keyword+"không có kết quả tìm kiếm");*/

/*cách 2: Nếu từ đầu đã xác định có data false thì tiến hành ntn để lúc lấy locator ko bị lỗi.
        if(expectedResult) {
            boolean actualResult = adminPage.isSearchResultDisplayed();
            Assert.assertEquals(actualResult, expectedResult);
            List<String> users = adminPage.getUsernameSearchList(keyword);
            for (String user : users) {
                softAssert.assertEquals(user, keyword);
                logger.info("Kết quả tìm kiếm hiện có với username keyword "+keyword+"là " + user);
            }
            softAssert.assertAll();
        } else {
            boolean actualResult = adminPage.isSearchResultNotDisplayed();
            Assert.assertTrue(actualResult);
            logger.info("với username keyword "+keyword+"không có kết quả tìm kiếm");
        };*/

/*Cách 3: Dùng được khi muốn assert cả valid + unvalid data.
Thay đổi hàm isSearchResultDisplayed2 có try catch để dù ra exception vẫn có thể dùng được mà không bị fail giữa chừng*/
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

    // Gọi chính xác tên DataProvider và Class chứa nó
    @Test(dataProvider = "userRoleData", dataProviderClass = AdminPageData.class)
    public void TC02_UserRole(String role, boolean expectedResult) {
        logger.info("Bắt đầu test với role = " + role);
        adminPage.clickUserRole();
/*Cách 1: Chỉ chạy với data valid thì làm cách này
        //Set kết quả xem bảng resutl có hiển thị không
        adminPage.clickUserRole(role);
        boolean actualResult = adminPage.isSearchResultDisplayed();
        // Assert kết quả sau mỗi lượt chạy
        Assert.assertEquals(actualResult, expectedResult);
        //Check xem trong kết quả có trùng với keyword
        if (expectedResult) {
            List<String> roles = adminPage.getRoleSearchResult();
            int quan = 0;
            for (String eachRole : roles) {
                quan++;
                softAssert.assertEquals(eachRole, role);
            }
            logger.info("Số kết quả tìm kiếm hiện có với role = "+role+" là " + quan + "và kết quả là \n" + roles);
            softAssert.assertAll();
        }
        else logger.info("Với role "+role+"không tồn tại nên không có kết quả");
        logger.info("Kết thúc test với role = "+role);*/
/*Cách 2: Dùng được khi muốn assert cả valid + unvalid data.
Thay đổi hàm isSearchResultDisplayed2 có try catch để dù ra exception vẫn có thể dùng được mà không bị fail giữa chừng*/
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

    @Test(dataProvider = "employeeNameValidKeywords", dataProviderClass = AdminPageData.class)
    public void TC03_EmployeeName_valid(String keyword) {
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

