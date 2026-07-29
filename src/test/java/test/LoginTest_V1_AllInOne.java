package test;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;

import java.time.Duration;
import java.util.Set;

public class LoginTest_V1_AllInOne {
    WebDriver driver;
    //để sau xem lại
    // private static final Logger logger = (Logger) LogManager.getLogger(LoginTest.class);


    By txtTitle = By.xpath("//h5[normalize-space()='Login']");
    By txtUsername = By.xpath ("/html[1]/body[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[2]/form[1]/div[1]/div[1]/div[2]/input[1]");
    By txtPassword = By.cssSelector("input[placeholder='Password']");
    By btnLogin = By.cssSelector("button[type='submit']");
    By msgNoName = By.cssSelector("body > div:nth-child(3) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(2) > div:nth-child(3) > form:nth-child(2) > div:nth-child(2) > div:nth-child(1) > span:nth-child(3)");
    By msgNoPw = By.cssSelector("body > div:nth-child(3) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(2) > div:nth-child(3) > form:nth-child(2) > div:nth-child(3) > div:nth-child(1) > span:nth-child(3)");
    By linkForgotPw = By.cssSelector(".oxd-text.oxd-text--p.orangehrm-login-forgot-header");
    By alertInvalidInfo = By.cssSelector(".oxd-alert-content.oxd-alert-content--error");
    By link = By.cssSelector("a[href='http://www.orangehrm.com']");
    By btnFb = By.cssSelector("a[href='https://www.facebook.com/OrangeHRM/']");

    @BeforeMethod
    public void setup() {
        //logger.info("========== START TEST ==========");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(300));
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
    }

//    Rút những hàm của driver sang BasePage và LoginPage
    @Test
    public void TC01_LoginFailWithBlankInfo () throws InterruptedException {
        Thread.sleep(5000);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(txtTitle));

        driver.findElement(txtUsername).sendKeys(" ");
        driver.findElement(txtPassword).sendKeys(" ");
        driver.findElement(btnLogin).click();
        String msgName_expected = driver.findElement(msgNoName).getText();
        String msgPw_expected = driver.findElement(msgNoPw).getText();
        WebElement title_expected = driver.findElement(txtTitle);

        Assert.assertTrue(title_expected.isDisplayed());
        System.out.println("Title correct");
        Assert.assertEquals(msgName_expected,"Required");
        System.out.println("Name correct");
        Assert.assertEquals(msgPw_expected, "Required");
        System.out.println("Pw correct");
    }

    @Test
    public void TC02_LoginFailWithInvalidCredential () {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(txtTitle));

        driver.findElement(txtUsername).sendKeys("abc");
        driver.findElement(txtPassword).sendKeys("123");
        driver.findElement(btnLogin).click();

      wait.until(ExpectedConditions.visibilityOf(driver.findElement(alertInvalidInfo)));
//        Không nên dùng lệnh visibilityOf(WebElement element) vì lệnh này Nhận vào WebElement
//        nên Element phải tồn tại trước mới dùng. Nên dùng lệnh visibilityOfElementLocated(By locator)
//        vì Nhận vào By, Selenium sẽ tự đi tìm element
        wait.until(ExpectedConditions.visibilityOfElementLocated(alertInvalidInfo));
        String alert_expected = driver.findElement(alertInvalidInfo).getText();

        Assert.assertEquals(alert_expected,"Invalid credentials");
        System.out.println("Alert is displayed correctly");

    }

    @Test
    public void TC03_ForgotYourPasswordLink_basedOnTitle () {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(txtTitle));

        driver.findElement(linkForgotPw).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".oxd-text.oxd-text--h6.orangehrm-forgot-password-title")));

        System.out.println("Navigate to the correct site");
    }

    @Test
    public void TC03_ForgotYourPasswordLink_basedOnURL () throws InterruptedException {
        Thread.sleep(5000);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(txtTitle));

        driver.findElement(linkForgotPw).click();

        String actualUrl = driver.getCurrentUrl();
        System.out.println("URL hiện tại là: " + actualUrl);

        // 4. Kiểm tra URL có chính xác không
        String expectedUrl = "https://opensource-demo.orangehrmlive.com/web/index.php/auth/requestPasswordResetCode";
        Assert.assertEquals(actualUrl, expectedUrl, "Trang web chưa chuyển hướng đến đúng URL mong muốn.");

        System.out.println("Navigate to the correct site");
    }


    @Test
    public void TC04_OrangeHRMLink () {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(txtTitle));

        //Test xem di chuyển tab mới đúng không
        String current = driver.getWindowHandle();
        driver.findElement(link).click();
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
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(txtTitle));
        //Phải thực hiện kéo màn hình xuống vì màn hình hiện tại che ko thấy sns button
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(btnFb));

        //Test xem di chuyển tab mới đúng không
        String current = driver.getWindowHandle();
        driver.findElement(btnFb).click();
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
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(txtTitle));

        driver.findElement(txtUsername).sendKeys("Admin");
        driver.findElement(txtPassword).sendKeys("admin123");
        driver.findElement(btnLogin).click();

        String actualUrl = driver.getCurrentUrl();
        String expectedUrl = "https://opensource-demo.orangehrmlive.com/web/index.php/dashboard/index";
        Assert.assertEquals(actualUrl, expectedUrl, "Trang web chưa chuyển hướng đến đúng URL mong muốn.");
        System.out.println("Navigate to the correct site");
    }

    @Test
    public void TC07_LoginSuccessful_basedOnTitle(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(txtTitle));

        driver.findElement(txtUsername).sendKeys("Admin");
        driver.findElement(txtPassword).sendKeys("admin123");
        driver.findElement(btnLogin).click();

       By title2 = By.cssSelector(".oxd-text.oxd-text--h6.oxd-topbar-header-breadcrumb-module");
       wait.until(ExpectedConditions.visibilityOfElementLocated(title2));
       Assert.assertTrue(driver.findElement(title2).isDisplayed(),"Trang web chưa chuyển hướng đến đúng URL mong muốn.");
       System.out.println("Navigate to the correct site");
    }

    @AfterMethod
    public void tearDown() {
        //logger.info("========== END TEST ==========");
        driver.quit();
    }

}
