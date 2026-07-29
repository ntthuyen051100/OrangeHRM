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
    public void LoginSuccess() {
        softAssert = new SoftAssert();
        adminPage = new AdminPage(driver);
        commonPage = new CommonPage(driver);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("Admin", "admin123");
        commonPage.clickAdmin();
    }
    @Test
    public void TC01_NavigateToUsersPage (){
//        adminPage.navigateUserScreen();
        Assert.assertTrue(commonPage.getTitle().contains("User"), "Navigate to wrong site");
         System.out.println("Navigate to Users site");
    }

    @Test
    public void TC02_CheckSystemUsers_UsernameSearchBox_Valid() {
//        adminPage.navigateUserScreen();
        String keyword = "Admin";
        //Nhập keyword rồi check xem có hiện kết quả search không
        adminPage.searchUsername(keyword);
        Assert.assertTrue(adminPage.searchResultIsDisplayed());
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
        Assert.assertTrue(adminPage.searchResultIsDisplayed());
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
        adminPage.searchResultIsDisplayed();
        Assert.assertTrue(adminPage.searchResultIsDisplayed(),"Kết quả ko được hiển thị");
        adminPage.clearSearchResult();
        adminPage.searchResultIsNotDisplayed();
        Assert.assertTrue(adminPage.searchResultIsNotDisplayed(),"Kết quả vẫn hiển thị");
        System.out.println("Kết quả được xóa thành công");
//Trong thực tế thì khoảng thời gian searchResult biến mất chưa tới 1s nên lúc này chạy đúng.
//Còn nếu cho thêm thời gian thì search result table đã hiển thị lại rồi
    }
    @Test
    public void TC06_CheckSystemUsers_ButtonDeleteSelected() throws InterruptedException {
//Ý tưởng: khi result table hiển thị full, chọn 1 row có username trùng với keyword nhập vào, click ô select
//xóa ô đã chọn, rồi check xem username đó còn hiển thị nữa không, ko là OK
        adminPage.searchResultIsDisplayed();
        String userName = "tvmuSjqI";
        adminPage.checkUser(userName);
        Assert.assertTrue(adminPage.isUserChecked(userName), "Username chưa được chọn");
        System.out.println("Username tương ứng đã được chọn");
    }
}
