package test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;

public class BaseTest {
    WebDriver driver;

    @BeforeMethod
    public void setup() {
        //logger.info("========== START TEST ==========");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
/*        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(300));*/
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
    }

    @AfterMethod
    public void tearDown() {
        //logger.info("========== END TEST ==========");
        driver.quit();
    }
}
