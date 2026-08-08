package base;

import drivers.DriverFactory;
import drivers.DriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import utils.ConfigReader;

import java.time.Duration;

import static utils.LogUtils.logger;

public class BaseTest_UsingDriverFactory {

    // Nhận tham số "browser" từ file testng-driverfactory.xml, mặc định là "chrome" nếu không truyền
    @BeforeMethod
    @Parameters("browser")
    public void setUp(String browser) {
        logger.info("========== START TEST ==========");
/*      WebDriver driver = new ChromeDriver();
Lúc này sẽ ko khai báo new driver như khi chạy trên ChromeDriver. Vì lúc này việc new browser nào sẽ được thực hiện bởi
method setDriver của class DriverFactory bên dưới, với browser sẽ map với file testng-driverfactory.xml*/
        DriverFactory.setDriver(browser);
        WebDriver driver = DriverFactory.getDriver();
        driver.manage().window().maximize();
        driver.get(ConfigReader.getPropValue("url"));
/*        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));*/
    }

    @AfterMethod
    public void tearDown() {
        logger.info("========== END TEST ==========");
        DriverFactory.quitDriver();
    }
}

