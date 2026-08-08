package test.Stage2_DataProvider_ThreadLocal_PageManager;

import base.BaseTest;
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
/*Đã làm được trong class này:
- Tách Page và Test ra theo POM, từng test độc lập
- Lấy dữ liệu từ file config.properties (tạo file config.properties -> Tạo class ConfigReader trong src/main/java/utils
để đọc config -> mỗi lần dùng ko cần khai báo, ko cần new, chấm dùng thẳng là được vì là method public static)
- Thêm Logger để in log dễ nhìn (Tạo file log4j2.xml trong src/main/resources -> Tạo class LogUtils
từ class có sẵn Logger của log4j -> ở class hay page muốn dùng thì ko khai báo, no new, dùng thẳng vì method là public static void)
!!!Tuy nhiên data vẫn phải hardcode trong từng test -> Thiết lập thêm DataProvider ở ver2*/

public class AdminTest_V1_RunSequentialWithHardcodedData extends BaseTest {
/*B1: Khai báo biến PageObject thành biến toàn cục của Class (Instance Variable). Khi khai báo ở đây, tất cả các
hàm nằm bên trong class AdminTest (bao gồm cả @BeforeMethod và @Test) đều có quyền nhìn thấy và sử dụng những biến này.*/
    AdminPage adminPage;
    SoftAssert softAssert;
    SideMenu sideMenu;
    EditUserPage editUserPage;

/*B2: Tạo 1 hàm BeforeMethod để new mới giá trị cho biến toàn cục. Mỗi test method đều dùng lại được các Page Object
mà không phải new nhiều lần trong từng @Test.
BeforeMethod của BaseTest và BeforeMethod của AdminTest có thể cùng có mà ko ra lỗi.
Thứ tự chạy BeforeMethod (BaseTest) -> BeforeMethod (AdminTest) -> @Test (AdminTest)*/
    @BeforeMethod
    public void LoginSuccess() {
        softAssert = new SoftAssert();
/*Ở đây không phải khai báo biến mới, chỉ gán giá trị cho biến đã có thôi. Nên không có tên Class ở đầu ntn nữa
AdminPage adminPage = new AdminPage(driver);*/
        adminPage = new AdminPage(driver);
        sideMenu = new SideMenu(driver);
        editUserPage = new EditUserPage(driver);
        LoginPage loginPage = new LoginPage(driver);
/* Sau khi xong class ConfigReader thì có thể thay bằng ntn để ko bị hardcode, chỉ cần thay đổi giá trị ở file config.properties là xong
        loginPage.login("Admin", "admin123");*/
        loginPage.login(ConfigReader.getPropValue("username"), ConfigReader.getPropValue("password"));
        sideMenu.clickAdmin();
    }
/*
!!!CHÚ Ý 1: Tuyệt đối không gộp vừa khai báo biến vừa new ngoài method, nếu làm vậy Java khởi tạo các biến toàn cục
(Instance Variables) của Class trước và chạy những biến này trước tiên.
Java chạy đến dòng CommonPage commonPage = new CommonPage(driver) -> để tạo được CommonPage, nó bắt buộc phải truyền giá trị của biến driver vào.
Tuy nhiên, lúc này hàm @BeforeMethod (nơi chứa lệnh driver = new ChromeDriver()) chưa hề được TestNG gọi chạy -> biến
driver lúc này hoàn toàn chưa có dữ liệu, giá trị của nó mặc định là null.
=> đang truyền một giá trị null vào hàm khởi tạo của CommonPage. Khi cấu trúc bên trong của Page Object cố gắng sử dụng
biến driver này (hoặc thư viện Selenium như PageFactory cố scan element trên driver bị null), hệ thống sẽ lập tức crash
và báo lỗi NullPointerException hoặc Driver must be set ngay lập tức.

public class AdminTest_V1_RunSequentialWithHardcodedData extends BaseTest {
    AdminPage adminPage = new AdminPage(driver);;
    CommonPage commonPage = new CommonPage(driver);

!!!CHÚ Ý 2: Tuyệt đối không gộp vừa khai báo biến vừa new trong @BeforeTest, vì lúc này các biến được khai báo sẽ là
biến cục bộ (Local Variable). Biến này chỉ sinh ra và tồn tại trong phạm vi cặp dấu ngoặc nhọn { } của hàm
LoginSuccess(). Ngay khi hàm này chạy xong, biến adminPage đó sẽ lập tức bị xóa khỏi bộ nhớ.
    @BeforeMethod
    public void LoginSuccess() {
        SoftAssert softAssert = new SoftAssert();
        AdminPage adminPage = new AdminPage(driver);
        CommonPage commonPage = new CommonPage(driver);
        LoginPage loginPage = new LoginPage(driver);
    }*/

    @Test
    public void TC01_NavigateToUsersPage () {
        Assert.assertTrue(sideMenu.getTitle().contains("User"), "Navigate to wrong site");
/*        System.out.println("Navigate to Users site"); Thay System.out.println = logger*/
        logger.info("Navigate to Users site");
    }

    @Test
    public void TC02_UsernameSearchBox_Valid() {
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
    public void TC03_UserRole_Valid()  {
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
//        System.out.println("Số kết quả tìm kiếm hiện có là " + quan +"và kết quả là"+roles);
        logger.info("Số kết quả tìm kiếm hiện có là " + quan +"và kết quả là"+roles);
        softAssert.assertAll();
    }

    @Test
    public void TC04_EmployeeName_Valid() {
//        adminPage.navigateUserScreen();
        String keyword = "mandaa";
        adminPage.typeName(keyword);
        //Check xem trong list gợi ý có trùng với keyword
        adminPage.getSearchNames();
        List<String> suggestNames = adminPage.getSearchNames();
        for(String name : suggestNames){
            softAssert.assertTrue(name.toLowerCase().contains(keyword.toLowerCase()),"Suggest name doesn't contain keyword");
        }
//        System.out.println("Số kết quả tìm kiếm đề xuất là cụ thể là \n" + suggestNames);
        logger.info("Số kết quả tìm kiếm đề xuất là cụ thể là \n" + suggestNames);
        softAssert.assertAll();
        /*        adminPage.searchName(); nếu chạy cả 2 lệnh thì sẽ bị lặp thao tác vì ở dưới mặc dù là gán giá trị
         * nhưng thực chất cũng là chạy thêm 1 lần nữa. Lúc này thì tất nhiên bị kéo xuống dươ màn hình rồi nên bị lỗi */
        String searchName = adminPage.getOptFirstName();
        adminPage.selectFirstName();
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
    public void TC05_ButtonResetOK(){
        String keyword = "manda";
        adminPage.typeName(keyword);
        adminPage.selectFirstName();
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
    public void TC06_ButtonDeleteSelected() /*throws InterruptedException*/ {
        adminPage.isSearchResultDisplayed();
        String userName = "asdfdgf";
        adminPage.checkUser(userName);
        Assert.assertTrue(adminPage.isUserChecked(userName), "Username chưa được chọn");
//        System.out.println("Username tương ứng đã được chọn");
        logger.info("Username tương ứng đã được chọn");
    }

    @Test
    public void TC07_DeleteSelectedUser() {
        adminPage.isSearchResultDisplayed();
        String userName = "asdfdgf";
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
//ko search mà điều khiển màn hình tới username tương ứng trong list. Lấy thông tin từ row ra xem có hiển thị đúng không.
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

