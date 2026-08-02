package test;

import data.AdminPageData;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.AdminPage;
import pages.CommonPage;
import pages.EditUserPage;
import pages.LoginPage;
import utils.ConfigReader;

import java.util.List;

import static utils.LogUtils.logger;

public class AdminTest_V2_UsingDataProvider extends BaseTest{
    AdminPage adminPage;
    SoftAssert softAssert;
    CommonPage commonPage;
    EditUserPage editUserPage;

    @BeforeMethod
    public void LoginSuccess() throws InterruptedException {
        softAssert = new SoftAssert();
        adminPage = new AdminPage(driver);
        commonPage = new CommonPage(driver);
        editUserPage = new EditUserPage(driver);
        LoginPage loginPage = new LoginPage(driver);
/* Sau khi xong class ConfigReader thì có thể thay bằng ntn để ko bị hardcode, chỉ cần thay đổi giá trị ở file config.properties là xong
        loginPage.login("Admin", "admin123");*/
        loginPage.login(ConfigReader.getPropValue("username"), ConfigReader.getPropValue("password"));
        commonPage.clickAdmin();
    }

    // Gọi chính xác tên DataProvider và Class chứa nó
    @Test(dataProvider = "usernameKeywords", dataProviderClass = AdminPageData.class)
    public void TC02_CheckSystemUsers_UsernameSearchBox_Valid(String keyword, boolean expectedResult) {
        logger.info("Bắt đầu test với role = " + keyword);
        adminPage.searchUsername(keyword);
//Giải thích C1: Nếu từ đầu đã xác định có data false thì tiến hành ntn để lúc lấy locator ko bị lỗi
/*        if(expectedResult) {
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
 /* Trong trường hợp chỉ check valid thôi thì ntn là ok. Tại sao data NG ko dùng cách này được? Vì lúc này
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
                softAssert.assertEquals(user, keyword);
                logger.info("Kết quả tìm kiếm hiện có với username keyword "+keyword+"là " + user);
            }
            softAssert.assertAll();
        }else logger.info("với username keyword "+keyword+"không có kết quả tìm kiếm");*/

//Giải thích C2: Thay đổi hàm isSearchResultDisplayed2 có try catch để dù ra exception vẫn có thể dùng được mà không bị fail giữa chừng
        boolean actualResult = adminPage.isSearchResultDisplayed2(keyword);
        Assert.assertEquals(actualResult, expectedResult);
        if (actualResult) {
            List<String> users = adminPage.getUsernameSearchList(keyword);
            for (String user : users) {
                softAssert.assertEquals(user, keyword);
                logger.info("Kết quả tìm kiếm hiện có với username keyword "+keyword+"là " + user);
            }
            softAssert.assertAll();
        }else logger.info("với username keyword "+keyword+"không có kết quả tìm kiếm");
    }

    // Gọi chính xác tên DataProvider và Class chứa nó
    @Test(dataProvider = "userRoleValidData", dataProviderClass = AdminPageData.class)
    public void TC03_CheckSystemUsers_UserRole_Valid(String role, boolean expectedResult) {
        logger.info("Bắt đầu test với role = "+role);
        adminPage.clickUserRole(role);
        //Set kết quả xem bảng resutl có hiển thị không
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
        logger.info("Kết thúc test với role = "+role);
    }

    @Test
    public void TC04_CheckSystemUsers_EmployeeName_Valid()  {
//        adminPage.navigateUserScreen();
        String keyword = "S";
        adminPage.typeName(keyword);
        //Check xem trong list gợi ý có trùng với keyword
        adminPage.getSearchNames();
        List<String> suggestNames = adminPage.getSearchNames();
        for(String name : suggestNames){
            softAssert.assertTrue(name.contains(keyword.toLowerCase()),"Suggest name doesn't contain keyword");
        }
//        System.out.println("Số kết quả tìm kiếm đề xuất là cụ thể là \n" + suggestNames);
        logger.info("Số kết quả tìm kiếm đề xuất là cụ thể là \n" + suggestNames);
        softAssert.assertAll();
        /*        adminPage.searchName(); nếu chạy cả 2 lệnh thì sẽ bị lặp thao tác vì ở dưới mặc dù là gán giá trị
         * nhưng thực chất cũng là chạy thêm 1 lần nữa. Lúc này thì tất nhiên bị kéo xuống dươ màn hình rồi nên bị lỗi */
        String searchName = adminPage.searchName();
        adminPage.getNameSearchResult();
        List<String> searchNameList = adminPage.getNameSearchResult();
        for(String name : searchNameList){
            softAssert.assertEquals(name,searchName,"Result name doesn't match the searched name");
        }
//        System.out.println("Số kết quả tìm kiếm đề xuất là cụ thể là \n" + searchNameList);
        logger.info("Số kết quả tìm kiếm đề xuất là cụ thể là \n" + searchNameList);
        softAssert.assertAll();
    }

    @Test
    public void TC05_CheckSystemUsers_ButtonResetOK() {
        String keyword = "S";
        adminPage.typeName(keyword);
        adminPage.searchName();
        adminPage.isSearchResultDisplayed();
        Assert.assertTrue(adminPage.isSearchResultDisplayed(),"Kết quả ko được hiển thị");
        adminPage.clearSearchResult();
        adminPage.isSearchResultNotDisplayed();
        Assert.assertTrue(adminPage.isSearchResultNotDisplayed(),"Kết quả vẫn hiển thị");
//        System.out.println("Kết quả được xóa thành công");
        logger.info("Kết quả được xóa thành công");
//Trong thực tế thì khoảng thời gian searchResult biến mất chưa tới 1s nên lúc này khó xác định được liền
    }

    @Test
    //Ý tưởng: khi result table hiển thị full, chọn 1 row có username trùng với keyword nhập vào, click ô select
//xóa ô đã chọn, rồi check xem username đó còn hiển thị nữa không, ko là OK
    public void TC06_CheckSystemUsers_ButtonDeleteSelected() {
        adminPage.isSearchResultDisplayed();
        String userName = "somethingsdf";
        adminPage.checkUser(userName);
        Assert.assertTrue(adminPage.isUserChecked(userName), "Username chưa được chọn");
//        System.out.println("Username tương ứng đã được chọn");
        logger.info("Username tương ứng đã được chọn");
    }

    @Test
    public void TC07_CheckSystemUser_DeleteSelectedUser() {
        adminPage.isSearchResultDisplayed();
        String userName = "somethingsdf";
        adminPage.checkUser(userName);
        adminPage.clickDeleteSelectedButton();
        Assert.assertTrue(adminPage.isPopupDeleteDisplayed(), "PopUp is not displayed");
//        System.out.println("Delete Popup is displayed");
        logger.info("Delete Popup is displayed");
        adminPage.closePopupDelete();
        Assert.assertTrue(adminPage.isPopupDeleteClosed(), "PopUp is displayed");
//        System.out.println("(Close button) Delete Popup is closed");
        logger.info("(Close button) Delete Popup is closed");

        adminPage.clickDeleteSelectedButton();
        adminPage.notDelete();
        Assert.assertTrue(adminPage.isPopupDeleteClosed(), "PopUp is displayed");
//        System.out.println("(No delete button) Delete Popup is closed");
        logger.info("(No delete button) Delete Popup is closed");

        adminPage.clickDeleteSelectedButton();
        adminPage.deleteSelected();
        Assert.assertTrue(adminPage.isPopupDeleteClosed(), "PopUp is displayed");
//        System.out.println("(Yes, delete button) Delete Popup is closed");
        logger.info("(Yes, delete button) Delete Popup is closed");
        Assert.assertTrue(adminPage.isDeleteSuccessMessageDisplayed(),"Delete Message is not displayed");
//        System.out.println("Delete success message is displayed");
        logger.info("Delete success message is displayed");
        adminPage.isSearchResultDisplayed();
        adminPage.checkUserAfterDelete(userName);
        Assert.assertTrue(adminPage.checkUserAfterDelete(userName));
//        System.out.println("Selected user is deleted");
        logger.info("Selected user is deleted");
    }

    @Test
    //Ý tưởng: search username, chọn edit thông tin username. Sau đó quay lại màn hình SystemUser
//ko search mà điều khiển màn hình tới username tương ứng trong list. Lấy thông tin từ row ra xem
//có hiển thị đúng không.
    public void TC08_CheckSystemUser_EditUserInfo() throws InterruptedException {
        String username = "FML20009800";
        adminPage.clickEditButton(username);
        Assert.assertTrue(editUserPage.isTitleDisplayed(),"Navigate to the wrong site");
        System.out.println("Navigate to edit user page");
        Assert.assertTrue(editUserPage.isFormDisplayed(),"Edit form is not displayed");
        System.out.println("Edit form is displayed");
        String newUsername = "TestAuto202178";
        editUserPage.setUsername(newUsername);
        editUserPage.submitChanges();
        Thread.sleep(5000);
        Assert.assertTrue(adminPage.isChangedUsernameDisplayed(newUsername));
        System.out.println("Done");
    }
}

