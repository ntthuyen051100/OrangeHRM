package test;

import base.BaseTest;
import org.testng.annotations.Test;
import pages.SideMenu;
import pages.LoginPage;
import utils.ConfigReader;

import static utils.LogUtils.logger;

public class TestDemo123 extends BaseTest {
/*    CommonPage commonPage = new CommonPage(driver);
    LoginPage loginPage = new LoginPage(driver);*/

    @Test
    public void TC01() {
        SideMenu sideMenu = new SideMenu(driver);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(ConfigReader.getPropValue("username"), ConfigReader.getPropValue("password"));
        sideMenu.clickAdmin();
        logger.info("Bắt đầu test với keyword = ");
    }
}
//Test git conflict 
