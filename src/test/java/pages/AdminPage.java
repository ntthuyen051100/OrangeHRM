package pages;

import base.BasePage;
import models.UserInfo;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class AdminPage extends BasePage {

    public AdminPage(WebDriver driver) {
        super(driver);
    }

    By tabUserMng = By.cssSelector(".oxd-topbar-body-nav-tab.--parent.--visited");
    By btnUsers = By.cssSelector("ul[role='menu'] li");
    By btnReset = By.cssSelector("button[class='oxd-button oxd-button--medium oxd-button--ghost']");
    By btnSearch = By.cssSelector("button[type='submit']");

    By usernameSearchBox = By.cssSelector("div[class='oxd-input-group oxd-input-field-bottom-space'] div input[class='oxd-input oxd-input--active']");
    //Cả 2 cách dưới đều tìm locator của bảng kết quả tìm kiếm. Tuy nhiên tableSearchResults là tìm nguyên
    //cái bảng, rows là tìm TẤT CẢ rows trong bảng. Nên nếu khi quét để xemn search có trong kết quả không
    //thì việc duyệt theo từng row sẽ ok hơn.
    By tableSearchResult = By.cssSelector("div[class='oxd-table-body']");
    By rows = By.xpath("//div[@class='oxd-table-card']/div[@role='row']");
//  By rows1 = By.cssSelector("div[class='oxd-table-card'] > div");

    By DdUserRole = By.cssSelector("body > div:nth-child(3) > div:nth-child(1) > div:nth-child(2) > div:nth-child(2) > div:nth-child(1) > div:nth-child(1) > div:nth-child(3) > form:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > div:nth-child(2) > div:nth-child(1)");
    By optSelect = By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div[1]/div[2]/form/div[1]/div/div[2]/div/div[2]/div/div[2]/div[1]");
    By optAdmin = By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div[1]/div[2]/form/div[1]/div/div[2]/div/div[2]/div/div[2]/div[2]");
    By optEss = By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div[1]/div[2]/form/div[1]/div/div[2]/div/div[2]/div/div[2]/div[3]");

    By nameSearchBox = By.cssSelector("input[placeholder='Type for hints...']");
    By optNames = By.cssSelector("div[role='listbox']");
    By optName = By.xpath("(//div[@role='option' and @class='oxd-autocomplete-option'])[1]/span");
    //Có thể viết gọn hơn thành //div[@role='option'])[1]/span miễn là khi search trên DOM trong devTools hiện 1 kết quả là được.
    //Giải thích: // - tìm khắp DOM thấy chỗ nào role='option', lấy div thứ 1. Rồi trong div đó chứa tag span chứa keyword
    // nên ta có lệnh /span là đi tiếp xuống chỗ tag con là span.

    By btnDeleteSelected = By.cssSelector("button[class='oxd-button oxd-button--medium oxd-button--label-danger orangehrm-horizontal-margin']");

    By tabJob = By.cssSelector("li[class='--active oxd-topbar-body-nav-tab --parent --visited'] span[class='oxd-topbar-body-nav-tab-item']");
    By btnJobTitle = By.xpath("//a[normalize-space()='Job Titles']");
    By btnPayGrade = By.xpath("//a[normalize-space()='Pay Grades']");
    By btnEmployStatus = By.xpath("//a[normalize-space()='Employment Status']");
    By btnJobCate =  By.xpath("//a[normalize-space()='Job Categories']");
    By btnWorkShift =  By.xpath("//a[normalize-space()='Work Shifts']");

    By tabOrg =  By.cssSelector("li[class='--active oxd-topbar-body-nav-tab --parent'] span[class='oxd-topbar-body-nav-tab-item']");
    By btnGenInfo =  By.xpath("//a[normalize-space()='General Information']");
    By btnLocation =  By.xpath("//a[normalize-space()='Locations']");
    By btnStructure =  By.xpath("//a[normalize-space()='Structure']");

    By tabQualifications =  By.xpath("//span[normalize-space()='Qualifications']");
    By btnSkill =  By.xpath("//a[normalize-space()='Skills']");
    By btnEdu = By.xpath("//a[normalize-space()='Education']");
    By btnLicense = By.xpath("//a[normalize-space()='Licenses']");
    By btnLang = By.xpath("//a[normalize-space()='Languages']");
    By btnMbs = By.xpath("//a[normalize-space()='Memberships']");

    By tabNationalities = By.xpath("//a[normalize-space()='Nationalities']");

    By tabCorBranding = By.xpath("//a[normalize-space()='Corporate Branding']");

    By tabConfig = By.xpath("//a[normalize-space()='Configuration']");
    By btnEmailConfig = By.xpath("//a[normalize-space()='Email Configuration']");
    By btnEmailSub = By.xpath("//a[normalize-space()='Email Subscriptions']");
    By btnLocalization = By.xpath("//a[normalize-space()='Localization']");
    By btnLangPack = By.xpath("//a[normalize-space()='Language Packages']");
    By btnModules = By.xpath("//a[normalize-space()='Modules']");
    By btnAuthen = By.xpath("//a[normalize-space()='Social Media Authentication']");
    By btnOAuth = By.xpath("//a[normalize-space()='Register OAuth Client']");
    By btnLdap = By.xpath("//a[normalize-space()='LDAP Configuration']");

    By tabMore = By.xpath("//span[normalize-space()='More']");

    //User screen
/*
    public void navigateUserScreen (){
*/
/*        if(isNotDisplayed(tabUserMng))
            click(tabMore);*//*

        click(tabUserMng);
        click(btnUsers);
    }
*/

    //Search
    public void searchUsername (String keyword){
        isDisplayed(tabUserMng);
        sendKeys(usernameSearchBox, keyword);
        enter();
    }

    public boolean searchResultIsDisplayed(){
        moveToPageBottom();
        return isDisplayed(tableSearchResult);
    }

    public List<String> getUsernameSearchList (String keyword)  {
/*      JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0,document.body.scrollHeight)");*/
        isDisplayed(By.xpath("(//div[contains(text(),'"+keyword+"')])[1]"));
 //     js.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("")));
        List<String> usnList = new ArrayList<>();
        List<WebElement> list = driver.findElements(rows);
        for (WebElement userName : list) {
//            String username = userName.findElement(By.xpath("(//div[contains(text(),'"+keyword+"')])[1]")).getText();
            String username = userName.findElement(By.xpath(".//div[@role='cell'][2]/div")).getText();
            usnList.add(username);
        }
        return usnList;
    }
/*    Cách phức tạp hơn, tạo thành 1 object rồi so sánh theo object
public List<UserInfo> getSearchList(String usernme, String rl, String nm) {
        List<UserInfo> usersList = new ArrayList<>();
        List<WebElement> list = driver.findElements(tableSearchResults);
        for (WebElement userName : list) {
            String username = userName.findElement(By.xpath("(//div[contains(text(),'"+usernme+"')])[1]")).getText();
            String role = userName.findElement(By.xpath("(//div[contains(text(),'"+rl+"')])[2]")).getText();
            String name = userName.findElement(By.xpath("(//div[contains(text(),'"+nm+"')])[1]")).getText();
            String status = userName.findElement(By.xpath("(//div[@class='header'][normalize-space()='Status'])[1]/following-sibling::div")).getText();
            usersList.add(new UserInfo(username, role, name, status)
            );
        }
        return usersList;
    }*/
    //Userrole DropDown
    public void clickUserRoleAdmin (){
        click(DdUserRole);
        click(optAdmin);
        click(btnSearch);
    }
    public List<String> getRoleSearchResult () throws InterruptedException {
        Thread.sleep(3000);
        List<String> roleList = new ArrayList<>();
        List<WebElement> list = driver.findElements(rows);
        for (WebElement roleName : list) {
            String rolename = roleName.findElement(By.xpath(".//div[@role='cell'][3]/div")).getText();
            roleList.add(rolename);
        }
        return roleList;
    }

    //Employee name
    public void typeName (String keyword) {
        sendKeys(nameSearchBox, keyword);
        isDisplayed(optNames);
    }
    public List<String> getSearchNames () throws InterruptedException {
        Thread.sleep(5000);
        List<String> nameList = new ArrayList<>();
        List<WebElement> names = driver.findElements(optNames);
        for (WebElement name : names) {
            String employeename = name.getText();
            nameList.add(employeename);
        }
        return nameList;
    }
    public String searchName () {
        isDisplayed(optName);
        String keyword2 = getText(optName);
        click(optName);
        click(btnSearch);
        moveToPageBottom();
        isDisplayed(tableSearchResult);
        return keyword2;
    }
    public List<String> getNameSearchResult () throws InterruptedException {
        Thread.sleep(3000);
        List<String> nameList = new ArrayList<>();
        List<WebElement> names = driver.findElements(rows);
        for (WebElement name : names) {
            String employeename = name.findElement(By.xpath(".//div[@role='cell'][4]/div")).getText();
            nameList.add(employeename);
        }
        return nameList;
    }

    //Button clear
    public void clearSearchResult (){
        moveToElement(btnReset);
        isDisplayed(btnReset);
        click(btnReset);
    }
    public boolean searchResultIsNotDisplayed(){
        moveToPageBottom();
        return isNotDisplayed(tableSearchResult);
    }

    //Delete selected
    public void checkUser(String usernameExpected) {
        moveToElement(rows);
        List<WebElement> rowList = driver.findElements(rows);
        for (WebElement row : rowList) {
            String username = row.findElement(By.xpath(".//div[@role='cell'][2]/div")).getText();
            System.out.println("Username chạy từ" +username);
            if (username.equals(usernameExpected)) {
               WebElement checkbox = row.findElement(By.cssSelector("div[class='oxd-table-card-cell-checkbox']"));
    //            WebElement checkbox = row.findElement(By.cssSelector("input[type='checkbox']"));
                if (!checkbox.isSelected()) {
                    checkbox.click();
                    System.out.println("xác nhận đến ước nay chua");
                }
                break;
            }
        }
    }
    public boolean isUserChecked(String usernameExpected) throws InterruptedException {
        moveToElement(rows);
        List<WebElement> rowList = driver.findElements(rows);
        for (WebElement row : rowList) {
            String username = row.findElement(By.xpath(".//div[@role='cell'][2]/div")).getText();
            System.out.println("Username chạy từ" +username);
            if (username.equals(usernameExpected)) {
                System.out.println("xác nhận đến bước nay chua");
                Thread.sleep(3000);
                return row.findElement(By.cssSelector("input[type='checkbox']")).isSelected();
//Chú ý:
            }
        }
        return false;
    }
}
/*    public void navigateJobTitleScreen (){
        if(isNotDisplayed(tabJob)){
            click(tabMore);
            click(tabJob);}
        else click(tabJob);
        click(btnJobTitle);
    }*/