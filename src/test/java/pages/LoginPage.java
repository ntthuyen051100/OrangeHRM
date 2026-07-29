package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    By txtTitle = By.cssSelector(".oxd-text.oxd-text--h5.orangehrm-login-title");
    By txtUsername = By.xpath ("//input[starts-with(@class,'oxd-input') and @name='username']");
    By txtPassword = By.xpath("//input[starts-with(@class,'oxd-input') and @name='password']");
    By btnLogin = By.cssSelector("button[type='submit']");
    By msgNoName = By.cssSelector("body > div:nth-child(3) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(2) > div:nth-child(3) > form:nth-child(2) > div:nth-child(2) > div:nth-child(1) > span:nth-child(3)");
    By msgNoPw = By.cssSelector("body > div:nth-child(3) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(2) > div:nth-child(3) > form:nth-child(2) > div:nth-child(3) > div:nth-child(1) > span:nth-child(3)");
    By linkForgotPw = By.cssSelector(".oxd-text.oxd-text--p.orangehrm-login-forgot-header");
    By txtResetPw = By.cssSelector(".oxd-text.oxd-text--h6.orangehrm-forgot-password-title");
    By alertInvalidInfo = By.cssSelector(".oxd-alert-content.oxd-alert-content--error");
    By linkFooter = By.cssSelector("a[href='http://www.orangehrm.com']");
    By btnFb = By.cssSelector("a[href='https://www.facebook.com/OrangeHRM/']");
    By titleDashboard = By.cssSelector(".oxd-text.oxd-text--h6.oxd-topbar-header-breadcrumb-module");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    //  Dùng lại những hàm từ BasePage
    public void inputUsername (String username) {
        sendKeys(txtUsername, username);
    }
    public void inputPw (String pw){
        sendKeys(txtPassword, pw);
    }
    public void clickLogin (){
        click(btnLogin);
    }
//    3 hàm trên gộp lại thành 1 hàm login chung
    public void login(String username, String pw){
        inputUsername(username);
        inputPw(pw);
        clickLogin();
    }
    public boolean title(){
        return isDisplayed(txtTitle);
    }
    public String msgNoName(){
         return getText(msgNoName);
    }
    public String msgNoPw(){
        return getText(msgNoPw);
    }
    public String alertInvalidInfo(){
        return getText(alertInvalidInfo);
    }
    public void clickForgotPw(){
        click(linkForgotPw);
    }
    public String getResetPwTittle (){
        return getText(txtResetPw);
    }
    public void clickLinkFooter(){
        click(linkFooter);
    }
    public void clickFbBtn(){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(btnFb));
        click(btnFb);
    }
    public String dashboardTitle(){
        return getText(titleDashboard);
    }
}
