package test;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.ConfigReader;

import static utils.LogUtils.logger;

public class BaseTest {
    WebDriver driver;

    @BeforeMethod
    public void setup() {
        logger.info("========== START TEST ==========");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
/*        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(300));*/
/*Sau khi tạo xong ConfigReader thì thay đổi cách lấy URL như sau:
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");*/
        driver.get(ConfigReader.getPropValue("url"));
    }

    @AfterMethod
    public void tearDown() {
        logger.info("========== END TEST ==========");
        driver.quit();
    }
}
