package test.Stage1_Basic;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.SideMenu;
import pages.LoginPage;

import java.util.List;
import java.util.Set;

public class SideMenuTest extends BaseTest {
    SideMenu sideMenu;
    SoftAssert softAssert;
/* Lý do tại sao phải khai báo đây, vì ở method LoginSuccess trong class @BeforeMethod CỦA CommonPage
    thì commonPage được new mới là biến cục bộ, chỉ tồn tại trong method LoginSuccess. Khi thực hiện xong
    thì biến đó tự biến mất. Muốn để dùng như biến toàn cục thì phải khai baáo 1 biến ngoài ko gán giá tri
    tiến hanh gán giá trị trong method đó rồi những @Test khác đều có the reuse lại được*/

/*  @BeforeClass
    Không dùng beforeClass đây vì class se thực hiện trước method dù là method của class cha đi chăng nữa
Lúc này, vì không có giá trị đc gán cho biến driver (vì việc gán gtri cho biến driver nằm ở beforeMethod của BaseTest)
cho class nên sẽ bị null. Lúc này thay bằng @BeforeMethod vẫn OK, ko sợ xung đột giữa cha con vì lúc này cha vẫn
chay trước con*/

    @BeforeMethod
    public void LoginSuccess() {
        softAssert = new SoftAssert();
        sideMenu = new SideMenu(driver);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("Admin", "admin123");
/* Ở đây chỉ có loginPage khi new phải khai báo class gốc vì loginPage đây new với giá trị mới hoàn toàn, biến cục bộ,
end method này là clear. Còn softAssert commonPage tuyệt đối ko đc khai báo class gốc lên đầu vì nếu vậy xem như new mới
1 biến có giá trị mới khác với biến toàn cục khai báo ngoài method. Mục đích của mình để reuse lại cho những testcase dưới.
Nên ở đây không khai class thì sẽ là biểu thức gán giá trị bth thôi -> OK */
        Assert.assertEquals(sideMenu.getTitle(),"Dashboard","Navigate to the wrong page");
        System.out.println("After login, navigate to Dashboard page");
    }

    @Test
    public void TC01_HeaderElementNavigateRight() {
        sideMenu.clickBtnUpgrade();
        //Check BtnUpgrade di chuyen dun
        String current = driver.getWindowHandle();
        Set<String> tabs = driver.getWindowHandles();
        for (String tab : driver.getWindowHandles()) {
            if (!tab.equals(current)) {
                driver.switchTo().window(tab);
            }
        }
        String actualUrl = driver.getCurrentUrl();
        String expectedUrl = "https://orangehrm.com/open-source/upgrade-to-advanced";
        softAssert.assertEquals(actualUrl, expectedUrl, "Trang web chưa chuyển hướng đến đúng URL mong muốn.");
        System.out.println("Open the correct tab");
        //Sau khi check xong thì quay lại tab/ window chính
        driver.close();
        driver.switchTo().window(current);

        //Check button Help
        sideMenu.clickBtnHelp();
        String current2 = driver.getWindowHandle();
        Set<String> tabs2 = driver.getWindowHandles();
        for (String tab : driver.getWindowHandles()) {
            if (!tab.equals(current2)) {
                driver.switchTo().window(tab);
            }
        }
        String actualHelpUrl = driver.getCurrentUrl();
        String expectedHelpUrl = "https://starterhelp.orangehrm.com/hc/en-us";
        softAssert.assertEquals(actualUrl, expectedUrl, "Trang web chưa chuyển hướng đến đúng URL mong muốn.");
        System.out.println("Open the correct tab");
        driver.close();
        driver.switchTo().window(current);


        //Check cac mục ở trong dropdown
//        commonPage.clickdDropdown();
        //Check button About
        sideMenu.clickBtnAbout();
        softAssert.assertTrue(sideMenu.dialogAboutIsDisplayed(), "Dialog is not displayed");
        softAssert.assertEquals(sideMenu.dialogAboutTitleIs(), "About");
        sideMenu.closeDialogAbout();
        softAssert.assertTrue(sideMenu.dialogAboutIsClosed());
        System.out.println("About Dialog is closed");

        //Check button support
//        commonPage.clickdDropdown();
        sideMenu.clickBtnSupport();
        String actualSupportURL = driver.getCurrentUrl();
//        softAssert.assertTrue(currentSupport.contentEquals("https://opensource-demo.orangehrmlive.com/web/index.php/help/support"));
        String expectedSupportURL = "https://opensource-demo.orangehrmlive.com/web/index.php/help/support";
        softAssert.assertEquals(actualSupportURL, expectedSupportURL, "Trang web chưa chuyển hướng đến đúng URL mong muốn.");
        System.out.println("Navigate to the correct site");
        softAssert.assertAll();

        //Check button ChangePw
//        Thread.sleep(5000);
//        commonPage.clickdDropdown();
        sideMenu.clickChangePw();
        String actualChangePwURL = driver.getCurrentUrl();
//        softAssert.assertTrue(currentSupport.contentEquals("https://opensource-demo.orangehrmlive.com/web/index.php/help/support"));
        softAssert.assertTrue(actualChangePwURL.contains("updatePassword"));
        System.out.println("Navigate to the correct site");

        //Check button Logout
        sideMenu.clickLogout();
//        Thread.sleep(5000);
        String actualLogoutURL = driver.getCurrentUrl();
        softAssert.assertTrue(actualLogoutURL.contentEquals("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login"));
        System.out.println("Navigate to the correct site");
        softAssert.assertAll();
    }

    @Test
    public void TC02_ClickTheLogoThenNavigateRight() {
        sideMenu.clickLogo();
        String actualUrl = driver.getCurrentUrl();
        String expectedUrl = "https://orangehrm.com/";
        Assert.assertEquals(actualUrl, expectedUrl, "Navigate to the wrong site");
        System.out.println("Navigate to the right site");
    }
    @Test
    //Với TC này nếu có thể dùng csv để chạy nhiều kết quả là ok, search tồn tại, ko tô tại
    public void TC03_SearchBoxWithValidInputData(){
        String keyword = "ASH";
        //Click seach box
        sideMenu.clickSearchBox();
        sideMenu.inputSeachBox(keyword);
        //Check xem khu vực kết quả có hiển thị đúng không
//      commonPage.verifySearchResult("A");
        List<String> results = sideMenu.getSearchResults();
        for(String result : results){
            Assert.assertTrue(result.contains(keyword.toLowerCase()),"Result is incorrect: " + result);
        }
        System.out.println("With keyword is " +keyword+". The search keyword have "+ results.size() +" matching results. The results are " + results);
    }
    @Test
    public void TC04_SearchBoxWithInValidInputData(){
        String keyword = "xxxaaaaiiii";
        sideMenu.clickSearchBox();
        sideMenu.inputSeachBox(keyword);
        List<String> results = sideMenu.getSearchResults();
        for(String result : results){
            Assert.assertFalse(result.contains(keyword.toLowerCase()),"Result is incorrect: " + result);
        }
        System.out.println("With keyword is " +keyword+". The search keyword have 0 matching result.");
    }
    @Test
    public void TC04_SideMenuElementNavigateRight() throws InterruptedException {
        sideMenu.clickAdmin();
        softAssert.assertTrue(sideMenu.getTitle().contains("Admin"),"Navigate to the wrong page");
        sideMenu.clickPim();
        softAssert.assertEquals(sideMenu.getTitle(),"PIM","Navigate to the wrong page");
        sideMenu.clickLeave();
        softAssert.assertEquals(sideMenu.getTitle(),"Leave","Navigate to the wrong page");
        sideMenu.clickTime();
        softAssert.assertTrue(sideMenu.getTitle().contains("Time"),"Navigate to the wrong page");
        sideMenu.clickRecruitment();
        softAssert.assertEquals(sideMenu.getTitle(),"Recruitment","Navigate to the wrong page");
        sideMenu.clickMyInfo();
        softAssert.assertTrue(driver.getCurrentUrl().contains("viewPersonalDetails"),"Navigate to the wrong page");
        sideMenu.clickPerf();
        softAssert.assertTrue(sideMenu.getTitle().contains("Performance"),"Navigate to the wrong page");
        sideMenu.clickMyDashboard();
        softAssert.assertEquals(sideMenu.getTitle(),"Dashboard","Navigate to the wrong page");
        sideMenu.clickDirectory();
        softAssert.assertEquals(sideMenu.getTitle(),"Directory","Navigate to the wrong page");
        sideMenu.clickMaintenance();
        softAssert.assertTrue(driver.getCurrentUrl().contains("maintenance"),"Navigate to the wrong page");
        sideMenu.backToBeforeScreen();
        sideMenu.clickClaim();
        softAssert.assertEquals(sideMenu.getTitle(),"Claim","Navigate to the wrong page");
        sideMenu.clickBuzz();
        softAssert.assertEquals(sideMenu.getTitle(),"Buzz","Navigate to the wrong page");

        softAssert.assertAll("All header elements are navigate to right site");
    }
}

