package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CommonPage extends BasePage {
    //Header element
    By title = By.cssSelector(".oxd-topbar-header-title");
    By btnUpgrade = By.cssSelector(".oxd-glass-button.orangehrm-upgrade-button");
    By dropdownUser = By.cssSelector(".oxd-userdropdown-tab");
    By btnAbout = By.cssSelector("body > div:nth-child(3) > div:nth-child(1) > div:nth-child(1) > header:nth-child(2) > div:nth-child(1) > div:nth-child(3) > ul:nth-child(1) > li:nth-child(1) > ul:nth-child(2) > li:nth-child(1) > a:nth-child(1)");
    By dialogAbout = By.cssSelector("div[role='document']");
    By dialogAbout_title = By.cssSelector(".oxd-text.oxd-text--h6.orangehrm-main-title");
    By dialogAbout_close = By.cssSelector(".oxd-dialog-close-button.oxd-dialog-close-button-position");
    By btnSupport = By.cssSelector("body > div:nth-child(3) > div:nth-child(1) > div:nth-child(1) > header:nth-child(2) > div:nth-child(1) > div:nth-child(3) > ul:nth-child(1) > li:nth-child(1) > ul:nth-child(2) > li:nth-child(2) > a:nth-child(1)");
    By btnChangePw = By.cssSelector("body > div:nth-child(3) > div:nth-child(1) > div:nth-child(1) > header:nth-child(2) > div:nth-child(1) > div:nth-child(3) > ul:nth-child(1) > li:nth-child(1) > ul:nth-child(2) > li:nth-child(3) > a:nth-child(1)");
    By btnLogout = By.cssSelector("body > div:nth-child(3) > div:nth-child(1) > div:nth-child(1) > header:nth-child(2) > div:nth-child(1) > div:nth-child(3) > ul:nth-child(1) > li:nth-child(1) > ul:nth-child(2) > li:nth-child(4) > a:nth-child(1)");
    By btnHelp = By.cssSelector("button[title='Help']");
    //Side menu
    By logo = By.cssSelector("img[alt='client brand banner']");
    By btnArrow = By.cssSelector("button[role='none']");
    By admin = By.cssSelector("body > div:nth-child(3) > div:nth-child(1) > div:nth-child(1) > aside:nth-child(1) > nav:nth-child(1) > div:nth-child(2) > ul:nth-child(2) > li:nth-child(1) > a:nth-child(1)");
    By pim = By.cssSelector("body > div:nth-child(3) > div:nth-child(1) > div:nth-child(1) > aside:nth-child(1) > nav:nth-child(1) > div:nth-child(2) > ul:nth-child(2) > li:nth-child(2) > a:nth-child(1)");
    By leave = By.cssSelector("body > div:nth-child(3) > div:nth-child(1) > div:nth-child(1) > aside:nth-child(1) > nav:nth-child(1) > div:nth-child(2) > ul:nth-child(2) > li:nth-child(3) > a:nth-child(1)");
    By time = By.cssSelector("body > div:nth-child(3) > div:nth-child(1) > div:nth-child(1) > aside:nth-child(1) > nav:nth-child(1) > div:nth-child(2) > ul:nth-child(2) > li:nth-child(4) > a:nth-child(1)");
    By recruitment = By.cssSelector("body > div:nth-child(3) > div:nth-child(1) > div:nth-child(1) > aside:nth-child(1) > nav:nth-child(1) > div:nth-child(2) > ul:nth-child(2) > li:nth-child(5) > a:nth-child(1)");
    By myInfo = By.cssSelector("body > div:nth-child(3) > div:nth-child(1) > div:nth-child(1) > aside:nth-child(1) > nav:nth-child(1) > div:nth-child(2) > ul:nth-child(2) > li:nth-child(6) > a:nth-child(1)");
    By perf = By.cssSelector("body > div:nth-child(3) > div:nth-child(1) > div:nth-child(1) > aside:nth-child(1) > nav:nth-child(1) > div:nth-child(2) > ul:nth-child(2) > li:nth-child(7) > a:nth-child(1)");
    By myDashboard = By.cssSelector(".oxd-main-menu-item.active");
    By directory = By.cssSelector("body > div:nth-child(3) > div:nth-child(1) > div:nth-child(1) > aside:nth-child(1) > nav:nth-child(1) > div:nth-child(2) > ul:nth-child(2) > li:nth-child(9) > a:nth-child(1)");
    By maintenance = By.cssSelector("body > div:nth-child(3) > div:nth-child(1) > div:nth-child(1) > aside:nth-child(1) > nav:nth-child(1) > div:nth-child(2) > ul:nth-child(2) > li:nth-child(10) > a:nth-child(1)");
    By claim = By.cssSelector("body > div:nth-child(3) > div:nth-child(1) > div:nth-child(1) > aside:nth-child(1) > nav:nth-child(1) > div:nth-child(2) > ul:nth-child(2) > li:nth-child(11) > a:nth-child(1)");
    By buzz = By.cssSelector("body > div:nth-child(3) > div:nth-child(1) > div:nth-child(1) > aside:nth-child(1) > nav:nth-child(1) > div:nth-child(2) > ul:nth-child(2) > li:nth-child(12) > a:nth-child(1)");
    By searchBox = By.cssSelector("input[placeholder='Search']");
    By searchResultList = By.cssSelector("ul[class='oxd-main-menu']");
//    By searchResult = By.xpath("(//li[@class='oxd-main-menu-item-wrapper'])[1~12]")
    public CommonPage(WebDriver driver) {
        super(driver);
    }
    public String getTitle(){
        return getText(title);
    }
    public void clickBtnUpgrade(){
        click(btnUpgrade);
    }
    public void clickdDropdown() {
        click(dropdownUser);
    }
    public void clickBtnAbout(){
        clickdDropdown();
        click(btnAbout);
    }
    public boolean dialogAboutIsDisplayed(){
        return isDisplayed(dialogAbout);
    }

    public String dialogAboutTitleIs(){
        return getText(dialogAbout_title);
    }
    public void closeDialogAbout(){
        click(dialogAbout_close);
    }
    public boolean dialogAboutIsClosed(){
//        closeDialogAbout(); bị trùng với bước closeDialogAbout. Nếu lỡ thực hiện 2 method liên tục
//        sẽ bị lỗi
        return isNotDisplayed(dialogAbout);
    }
    public void clickBtnSupport(){
        clickdDropdown();
        click(btnSupport);
    }
    public void clickChangePw(){
        clickdDropdown();
        click(btnChangePw);
    }
    public void clickLogout(){
        clickdDropdown();
        click(btnLogout);
    }
    public void clickBtnHelp(){
        click(btnHelp);
    }
    public void clickLogo(){
        click(logo);
    }
    public void clickArrow(){
        click(btnArrow);
    }
    public void clickAdmin(){
        click(admin);
    }
    public void clickPim(){
        click(pim);
    }
    public void clickLeave(){
        click(leave);
    }
    public void clickTime (){
        click(time);
    }
    public void clickRecruitment (){
        click(recruitment);
    }
    public void clickMyInfo(){
        click(myInfo);
    }
    public void clickPerf(){
        click(perf);
    }
    public void clickMyDashboard (){
        click(myDashboard);
    }
    public void clickDirectory (){
        click(directory);
    }
    public void clickMaintenance (){
        click(maintenance);
    }
    public void backToBeforeScreen (){
        driver.navigate().back();
    }
    public void clickClaim () {
        click(claim);
    }
    public void clickBuzz () {
        click(buzz);
    }
    public void clickSearchBox () {
        if (isDisplayed(searchBox))
            click(searchBox);
        else {
            clickArrow();
            click(searchBox);
        }
    }
    public void inputSeachBox(String keyword){
        sendKeys(searchBox, keyword);
    }
/* Đây là logic khi xử lý search box dynamic, hiện tại cần refactor thêm vì ở đây mình làm luôn việc
của class test là verify dữ liệu luôn rồi. Đúng class Page chỉ nên chứa UI và thao tác, verify để test làm

    public void verifySearchResult(String keyword){
        List<WebElement> list = driver.findElements(searchResultList);
        //Set<String> searchResult = driver.findElements(searchResultList)
        List<String> searchResult = new ArrayList<>();
        for (WebElement element : driver.findElements(searchResultList)) {
            searchResult.add(element.getText());
            }
        int count = 0;
        List<String> matchedResults = new ArrayList<>();
        for (String result : searchResult) {
            if (result.contains(keyword)) {
                count++;
                matchedResults.add(result);
            }
        }
        System.out.println("The search keyword have "+ count +" matching results. The results are " + matchedResults);
    }*/
    public List<String> getSearchResults() {
        List<String> results = new ArrayList<>();
        for (WebElement element : driver.findElements(searchResultList)) {
            results.add(element.getText());
        }
        return results;
    }
}
