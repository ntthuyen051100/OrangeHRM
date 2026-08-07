package test.Stage3_EvidenceAndReport;

import base.BaseTest;
import listeners.AllureListener;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.AdminPage;
import pages.EditUserPage;
import pages.LoginPage;
import pages.SideMenu;
import utils.ConfigReader;

import java.util.List;

import static utils.LogUtils.logger;
/*Đã làm được trong class này:
-  Thêm allure report: tải allure về -> thêm vào env path của local -> thêm vào dependency tại pom.xml -> tạo 1 class
testlistener (src/test/java/listeners/AllureListener.java) thêm @Attachment của allure để xuất ra report html được.
   Cách chỉ định class nào chạy để in report giống với như testListener
      + Chỉ định ngay tại class đó bằng cách thêm @Listeners(AllureListener.class)
      + Tạo 1 file testng.xml (suites/testng-allure.xml), thêm tag listener và thêm name vào class
   NẾU ĐÃ CHẠY ALLURE TRƯỚC ĐÓ THÌ NHỚ XÓA THỦ CÔNG file allure-results của lần chạy trước để report không bị lấy dữ liệu lần chạy truớc
   Khi run thành công (bằng 1 trong 2 cách trên), mở terminal, gõ lệnh "allure serve allure-results" -> tạo ra 1 link html và tự mở đến report đã chạy
!!!Bị lỗi chưa có screenshot khi bị fail, không có step rõ ràng, vẫn phải xóa allure-results thủ công???
-> V7?*/

/*@Listeners(AllureListener.class)*/
public class AdminTest_V6_AllureReport extends BaseTest {
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

    @Test
    public void TC01_NavigateToUsersPage () {
        Assert.assertTrue(sideMenu.getTitle().contains("User"), "Navigate to wrong site");
        logger.info("Navigate to Users site");
    }

    @Test
    public void TC02_UsernameSearchBox_Valid() {
        String keyword = "Admin";
        adminPage.searchUsername(keyword);
        Assert.assertTrue(adminPage.isSearchResultDisplayed());
        List<String> users = adminPage.getUsernameSearchList(keyword);
        for(String user : users){
            softAssert.assertEquals(user,keyword);
            System.out.println("Kết quả tìm kiếm hiện có là " +user);
        }
        softAssert.assertAll();
    }

    @Test
    public void TC03_UserRole_Valid()  {
        adminPage.clickUserRoleAdmin();
        Assert.assertTrue(adminPage.isSearchResultDisplayed());
        List<String> roles = adminPage.getRoleSearchResult();
        int quan = 0;
        for(String role : roles){
            quan ++;
            softAssert.assertEquals(role,"Admin");

        }
        logger.info("Số kết quả tìm kiếm hiện có là " + quan +"và kết quả là"+roles);
        softAssert.assertAll();
    }

    @Test
    public void TC04_EmployeeName_Valid() {
        String keyword = "mandaa";
        adminPage.typeName(keyword);
        adminPage.getSearchNames();
        List<String> suggestNames = adminPage.getSearchNames();
        for(String name : suggestNames){
            softAssert.assertTrue(name.toLowerCase().contains(keyword.toLowerCase()),"Suggest name doesn't contain keyword");
        }
        logger.info("Số kết quả tìm kiếm đề xuất là cụ thể là \n" + suggestNames);
        softAssert.assertAll();
        String searchName = adminPage.getOptFirstName();
        adminPage.selectFirstName();
        adminPage.getNameSearchResult();
        List<String> searchNameList = adminPage.getNameSearchResult();
        for(String name : searchNameList){
            softAssert.assertEquals(name,searchName,"Result name doesn't match the searched name");
        }
        logger.info("Số kết quả tìm kiếm đề xuất là cụ thể là \n" + searchNameList);
        softAssert.assertAll();
    }

    @Test
    public void TC05_ButtonResetOK(){
        String keyword = "manda";
        adminPage.typeName(keyword);
        adminPage.selectFirstName();
        adminPage.isSearchResultDisplayed();
        Assert.assertTrue(adminPage.isSearchResultDisplayed(),"Kết quả ko được hiển thị");
        adminPage.clearSearchResult();
        adminPage.isSearchResultNotDisplayed();
        Assert.assertTrue(adminPage.isSearchResultNotDisplayed(),"Kết quả vẫn hiển thị");
        logger.info("Kết quả được xóa thành công");
  }

    @Test
       public void TC06_ButtonDeleteSelected()  {
        adminPage.isSearchResultDisplayed();
        String userName = "asdfdgf";
        adminPage.checkUser(userName);
        Assert.assertTrue(adminPage.isUserChecked(userName), "Username chưa được chọn");
        logger.info("Username tương ứng đã được chọn");
    }

    @Test
    public void TC07_DeleteSelectedUser() {
        adminPage.isSearchResultDisplayed();
        String userName = "asdfdgf";
        adminPage.checkUser(userName);
        adminPage.clickDeleteSelectedButton();
        Assert.assertTrue(adminPage.isPopupDeleteDisplayed(), "PopUp is not displayed");
        logger.info("Delete Popup is displayed");
        adminPage.closePopupDelete();
        Assert.assertTrue(adminPage.isPopupDeleteClosed(), "PopUp is displayed");
        logger.info("(Close button) Delete Popup is closed");

        adminPage.clickDeleteSelectedButton();
        adminPage.notDelete();
        Assert.assertTrue(adminPage.isPopupDeleteClosed(), "PopUp is displayed");
        logger.info("(No delete button) Delete Popup is closed");

        adminPage.clickDeleteSelectedButton();
        adminPage.deleteSelected();
        Assert.assertTrue(adminPage.isPopupDeleteClosed(), "PopUp is displayed");
        logger.info("(Yes, delete button) Delete Popup is closed");
        Assert.assertTrue(adminPage.isDeleteSuccessMessageDisplayed(),"Delete Message is not displayed");
        logger.info("Delete success message is displayed");

        adminPage.isSearchResultDisplayed();
        adminPage.checkUserAfterDelete(userName);
        Assert.assertTrue(adminPage.checkUserAfterDelete(userName));
        logger.info("Selected user is deleted");
    }

    @Test
    public void TC08_EditValidUserInfo()  {
        String username = "ABC123DEF";
        adminPage.clickEditButton(username);
        Assert.assertTrue(editUserPage.isTitleDisplayed(),"Navigate to the wrong site");
        System.out.println("Navigate to edit user page");
        Assert.assertTrue(editUserPage.isFormDisplayed(),"Edit form is not displayed");
        System.out.println("Edit form is displayed");
        String newUsername = "TestAuto202178";
        editUserPage.setUsername(newUsername);
        editUserPage.submitChanges();
        Assert.assertTrue(adminPage.isChangedUsernameDisplayedInCurrentPage(newUsername));
        System.out.println("Done");
    }
}

