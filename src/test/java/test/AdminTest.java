package test;

import models.UserInfo;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.AdminPage;
import pages.CommonPage;
import pages.LoginPage;

import java.util.List;

public class AdminTest extends BaseTest{
    AdminPage adminPage;
    SoftAssert softAssert;
    CommonPage commonPage;

    @BeforeMethod
    public void LoginSuccess() throws InterruptedException {
        softAssert = new SoftAssert();
        adminPage = new AdminPage(driver);
        commonPage = new CommonPage(driver);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("Admin", "admin123");
        commonPage.clickAdmin();
    }

    @Test
    public void TC01_NavigateToUsersPage () {
        Assert.assertTrue(commonPage.getTitle().contains("User"), "Navigate to wrong site");
         System.out.println("Navigate to Users site");
    }

    @Test
    public void TC02_CheckSystemUsers_UsernameSearchBox_Valid() {
//        adminPage.navigateUserScreen();
        String keyword = "Admin";
        //Nhập keyword rồi check xem có hiện kết quả search không
        adminPage.searchUsername(keyword);
        Assert.assertTrue(adminPage.isSearchResultDisplayed());
        //Check xem trong kết quả có trùng với keyword
        List<String> users = adminPage.getUsernameSearchList(keyword);
        for(String user : users){
            softAssert.assertEquals(user,keyword);
            System.out.println("Kết quả tìm kiếm hiện có là " +user);
        }
        softAssert.assertAll();
    }

    @Test
    public void TC03_CheckSystemUsers_UserRole_Valid() throws InterruptedException {
//        adminPage.navigateUserScreen();
        adminPage.clickUserRoleAdmin();
        Assert.assertTrue(adminPage.isSearchResultDisplayed());
        //Check xem trong kết quả có trùng với keyword
        List<String> roles = adminPage.getRoleSearchResult();
        int quan = 0;
        for(String role : roles){
            quan ++;
            softAssert.assertEquals(role,"Admin");

        }
        System.out.println("Số kết quả tìm kiếm hiện có là " + quan +"và kết quả là"+roles);
        softAssert.assertAll();
    }

    @Test
    public void TC04_CheckSystemUsers_EmployeeName_Valid() throws InterruptedException {
//        adminPage.navigateUserScreen();
        String keyword = "S";
        adminPage.typeName(keyword);
        //Check xem trong list gợi ý có trùng với keyword
        adminPage.getSearchNames();
        List<String> suggestNames = adminPage.getSearchNames();
        for(String name : suggestNames){
            softAssert.assertTrue(name.contains(keyword.toLowerCase()),"Suggest name doesn't contain keyword");
        }
        System.out.println("Số kết quả tìm kiếm đề xuất là cụ thể là \n" + suggestNames);
        softAssert.assertAll();
/*        adminPage.searchName(); nếu chạy cả 2 lệnh thì sẽ bị lặp thao tác vì ở dưới mặc dù là gán giá trị
* nhưng thực chất cũng là chạy thêm 1 lần nữa. Lúc này thì tất nhiên bị kéo xuống dươ màn hình rồi nên bị lỗi */
        String searchName = adminPage.searchName();
        adminPage.getNameSearchResult();
        List<String> searchNameList = adminPage.getNameSearchResult();
        for(String name : searchNameList){
            softAssert.assertEquals(name,searchName,"Result name doesn't match the searched name");
        }
        System.out.println("Số kết quả tìm kiếm đề xuất là cụ thể là \n" + searchNameList);
        softAssert.assertAll();
    }

    @Test
    public void TC05_CheckSystemUsers_ButtonResetOK() throws InterruptedException {
        String keyword = "S";
        adminPage.typeName(keyword);
        adminPage.searchName();
        adminPage.isSearchResultDisplayed();
        Assert.assertTrue(adminPage.isSearchResultDisplayed(),"Kết quả ko được hiển thị");
        adminPage.clearSearchResult();
        adminPage.isSearchResultNotDisplayed();
        Assert.assertTrue(adminPage.isSearchResultNotDisplayed(),"Kết quả vẫn hiển thị");
        System.out.println("Kết quả được xóa thành công");
//Trong thực tế thì khoảng thời gian searchResult biến mất chưa tới 1s nên lúc này khó xác định được liền
    }

    @Test
    //Ý tưởng: khi result table hiển thị full, chọn 1 row có username trùng với keyword nhập vào, click ô select
//xóa ô đã chọn, rồi check xem username đó còn hiển thị nữa không, ko là OK
    public void TC06_CheckSystemUsers_ButtonDeleteSelected() throws InterruptedException {
        adminPage.isSearchResultDisplayed();
        String userName = "FMLName";
        adminPage.checkUser(userName);
        Assert.assertTrue(adminPage.isUserChecked(userName), "Username chưa được chọn");
        System.out.println("Username tương ứng đã được chọn");
    }

    @Test
    public void TC07_CheckSystemUser_DeleteSelectedUser() {
        adminPage.isSearchResultDisplayed();
        String userName = "FMLName";
        adminPage.checkUser(userName);
        adminPage.clickDeleteSelectedButton();
        Assert.assertTrue(adminPage.isPopupDeleteDisplayed(), "PopUp is not displayed");
        System.out.println("Delete Popup is displayed");
        adminPage.closePopupDelete();
        Assert.assertTrue(adminPage.isPopupDeleteClosed(), "PopUp is displayed");
        System.out.println("(Close button) Delete Popup is closed");

        adminPage.clickDeleteSelectedButton();
        adminPage.notDelete();
        Assert.assertTrue(adminPage.isPopupDeleteClosed(), "PopUp is displayed");
        System.out.println("(No delete button) Delete Popup is closed");

        adminPage.clickDeleteSelectedButton();
        adminPage.deleteSelected();
        Assert.assertTrue(adminPage.isPopupDeleteClosed(), "PopUp is displayed");
        System.out.println("(Yes, delete button) Delete Popup is closed");
        Assert.assertTrue(adminPage.isDeleteSuccessMessageDisplayed(),"Delete Message is not displayed");
        System.out.println("Delete success message is displayed");
        adminPage.isSearchResultDisplayed();
        adminPage.checkUserAfterDelete(userName);
        Assert.assertTrue(adminPage.checkUserAfterDelete(userName));
        System.out.println("Selected user is deleted");
    }

    @Test
    //Ý tưởng: search username, chọn edit thông tin username. Sau đó quay lại màn hình SystemUser
//ko search mà điều khiển màn hình tới username tương ứng trong list. Lấy thông tin từ row ra xem
//có hiển thị đúng không.
    public void TC08_CheckSystemUser_EditUserInfo(){
        String username = "";
        adminPage.checkUser(username);
        Assert.assertTrue(adminPage.isSearchResultDisplayed());
        //Check xem trong kết quả có trùng với keyword
        List<String> users = adminPage.getUsernameSearchList(username);
        for(String user : users){
            System.out.println("Kết quả tìm kiếm hiện có là " +user);
        }
    }
}
