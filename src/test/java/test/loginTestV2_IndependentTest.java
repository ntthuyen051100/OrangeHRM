package test;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;

import java.util.Set;

public class loginTestV2_IndependentTest extends BaseTest {

    @Test
    public void TC01_LoginFailWithBlankInfo (){

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("","");

        Assert.assertTrue(loginPage.title());
        System.out.println("Title correct");
        Assert.assertEquals(loginPage.msgNoName(), "Required");
        System.out.println("Name correct");
        Assert.assertEquals(loginPage.msgNoName(), "Required");
        System.out.println("Pw correct");
    }

    @Test
    public void TC02_LoginFailWithInvalidCredential () {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("abc","123");

        Assert.assertTrue(loginPage.title());
        System.out.println("Title correct");
        Assert.assertEquals(loginPage.alertInvalidInfo(),"Invalid credentials");
        System.out.println("Alert is displayed correctly");
    }

    @Test
    public void TC03_ForgotYourPasswordLink_basedOnTitle () {
        LoginPage loginPage = new LoginPage(driver);
        Assert.assertTrue(loginPage.title());
        loginPage.clickForgotPw();
        Assert.assertEquals(loginPage.getResetPwTittle(),"Reset Password");
        System.out.println("Navigate to the correct site");
    }

    @Test
    public void TC03_ForgotYourPasswordLink_basedOnURL () {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.clickForgotPw();
        String actualUrl = driver.getCurrentUrl();

        // 4. Kiểm tra URL có chính xác không
        String expectedUrl = "https://opensource-demo.orangehrmlive.com/web/index.php/auth/requestPasswordResetCode";
        Assert.assertEquals(actualUrl, expectedUrl, "Trang web chưa chuyển hướng đến đúng URL mong muốn.");

        System.out.println("Navigate to the correct site");
    }


    @Test
    public void TC04_OrangeHRMLink () {

        LoginPage loginPage = new LoginPage(driver);
        loginPage.clickLinkFooter();

        //Test xem di chuyển tab mới đúng không
        String current = driver.getWindowHandle();
        //Lấy tất cả danh sách các tab hiện mở
        Set<String> windows = driver.getWindowHandles();
        //Di chuyển sang tab mới bằng câu lệnh for lồng if
        for(String window : driver.getWindowHandles()){
            if(!window.equals(current)){
                driver.switchTo().window(window);
            }
        }
        //Lấy URL của tab mới rồi so với URL đúng. Nếu OK thì in lệnh
        String actualUrl = driver.getCurrentUrl();
        String expectedUrl = "https://orangehrm.com/";
        Assert.assertEquals(actualUrl, expectedUrl, "Trang web chưa chuyển hướng đến đúng URL mong muốn.");
        System.out.println("Navigate to the correct site");
        //Sau khi check xong thì quay lại tab/ window chính
        driver.switchTo().window(current);
    }

    @Test
    public void TC05_SnsLink () {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.title();
        loginPage.clickFbBtn();
        //Phải thực hiện kéo màn hình xuống vì màn hình hiện tại che ko thấy sns button


        //Test xem di chuyển tab mới đúng không
        String current = driver.getWindowHandle();
        Set<String> windows = driver.getWindowHandles();
        for(String window : driver.getWindowHandles()){
            if(!window.equals(current)){
                driver.switchTo().window(window);
            }
        }
        String actualUrl = driver.getCurrentUrl();
        String expectedUrl = "https://www.facebook.com/OrangeHRM/";
        Assert.assertEquals(actualUrl, expectedUrl, "Trang web chưa chuyển hướng đến đúng URL mong muốn.");
        System.out.println("Navigate to the correct site");
        driver.switchTo().window(current);
    }

    @Test
    public void TC06_LoginSuccessful_basedOnUrl () {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("Admin","admin123");

        String actualUrl = driver.getCurrentUrl();
        String expectedUrl = "https://opensource-demo.orangehrmlive.com/web/index.php/dashboard/index";
        Assert.assertEquals(actualUrl, expectedUrl, "Trang web chưa chuyển hướng đến đúng URL mong muốn.");
        System.out.println("Navigate to the correct site");
    }

    @Test
    public void TC07_LoginSuccessful_basedOnTitle(){
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("Admin","admin123");

        Assert.assertEquals(loginPage.dashboardTitle(),"Dashboard","Trang web chưa chuyển hướng đến đúng URL mong muốn.");
        System.out.println("Navigate to the correct site");
    }
}
