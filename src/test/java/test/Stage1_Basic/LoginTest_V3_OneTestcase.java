package test.Stage1_Basic;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;

import java.util.Set;

public class LoginTest_V3_OneTestcase extends BaseTest {

    @Test
    public void LoginFullFlow() throws InterruptedException {
        LoginPage loginPage = new LoginPage(driver);
//No info
        loginPage.login("", "");
        Assert.assertTrue(loginPage.title());
        System.out.println("Title correct");
        Assert.assertEquals(loginPage.msgNoName(), "Required");
        System.out.println("Name correct");
        Assert.assertEquals(loginPage.msgNoName(), "Required");
        System.out.println("Pw correct");
//ForgotYourPasswordLink_basedOnTitle
        loginPage.clickForgotPw();
        Assert.assertEquals(loginPage.getResetPwTittle(), "Reset Password");
        System.out.println("Navigate to the correct site");
//ForgotYourPasswordLink_basedOnURL
        String actualUrl = driver.getCurrentUrl();
        String expectedUrl = "https://opensource-demo.orangehrmlive.com/web/index.php/auth/requestPasswordResetCode";
        Assert.assertEquals(actualUrl, expectedUrl, "Trang web chưa chuyển hướng đến đúng URL mong muốn.");
        System.out.println("Navigate to the correct site");
        driver.navigate().back();
//OrangeHRMLink
        loginPage.clickLinkFooter();
        String current = driver.getWindowHandle();
        Set<String> windows = driver.getWindowHandles();
        for (String window : driver.getWindowHandles()) {
            if (!window.equals(current)) {
                driver.switchTo().window(window);
            }
        }
        String actualUrl2 = driver.getCurrentUrl();
        String expectedUrl2 = "https://orangehrm.com/";
        Assert.assertEquals(actualUrl2, expectedUrl2, "Trang web chưa chuyển hướng đến đúng URL mong muốn.");
        System.out.println("Navigate to the correct site");
        driver.switchTo().window(current);
//SnsLink
        loginPage.clickFbBtn();
        String current1 = driver.getWindowHandle();
        Set<String> windows1 = driver.getWindowHandles();
        for (String window : driver.getWindowHandles()) {
            if (!window.equals(current1)) {
                driver.switchTo().window(window);
            }
        }
        String actualUrl3 = driver.getCurrentUrl();
        String expectedUrl3 = "https://www.facebook.com/OrangeHRM/";
        Assert.assertEquals(actualUrl3, expectedUrl3, "Trang web chưa chuyển hướng đến đúng URL mong muốn.");
        System.out.println("Navigate to the correct site");
        driver.switchTo().window(current);
//InvalidCredential
        loginPage.login("abc", "123");
        Assert.assertEquals(loginPage.alertInvalidInfo(), "Invalid credentials");
        System.out.println("Alert is displayed correctly");
//LoginSuccessful_basedOnUrl () {
        loginPage.clearPw();
        loginPage.clearUsername();
        loginPage.login("Admin", "admin123");
        Thread.sleep(5000);
        String actualUrl4 = driver.getCurrentUrl();
        String expectedUrl4 = "https://opensource-demo.orangehrmlive.com/web/index.php/dashboard/index";
        Assert.assertEquals(actualUrl4, expectedUrl4, "Trang web chưa chuyển hướng đến đúng URL mong muốn.");
        System.out.println("Navigate to the correct site");
    }
}
