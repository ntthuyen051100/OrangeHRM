package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;

import static utils.LogUtils.logger;

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
    //cái bảng, là container, rows là tìm TẤT CẢ rows trong bảng. Nên nếu khi quét để xemn search có trong kết quả không
    //thì việc duyệt theo từng row sẽ ok hơn.
    By tableSearchResult = By.cssSelector("div[class='oxd-table-body']");
    By rows = By.xpath("//div[@class='oxd-table-card']/div[@role='row']");
//  By rows1 = By.cssSelector("div[class='oxd-table-card'] > div");
    By btnNextPage = By.xpath("//button[@class='oxd-pagination-page-item oxd-pagination-page-item--previous-next']/i[@class='oxd-icon bi-chevron-right']");

    By DdUserRole = By.cssSelector("body > div:nth-child(3) > div:nth-child(1) > div:nth-child(2) > div:nth-child(2) > div:nth-child(1) > div:nth-child(1) > div:nth-child(3) > form:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > div:nth-child(2) > div:nth-child(1)");
    By optSelect = By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div[1]/div[2]/form/div[1]/div/div[2]/div/div[2]/div/div[2]/div[1]");
    By optAdmin = By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div[1]/div[2]/form/div[1]/div/div[2]/div/div[2]/div/div[2]/div[2]");
    By optEss = By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div[1]/div[2]/form/div[1]/div/div[2]/div/div[2]/div/div[2]/div[3]");

    By nameSearchBox = By.cssSelector("input[placeholder='Type for hints...']");
    By optNames = By.cssSelector("div[role='listbox']>div>span");
    By optLoading = By.xpath("(//div[@role='option' and contains(text(),'Searching')])");
    By option = By.xpath("(//div[@role='option'])");;
    By optNoRecord = By.xpath("(//div[@role='option' and contains(text(),'No Records Found')])");;
    By msgInvalidName = By.xpath("//span[@class='oxd-text oxd-text--span oxd-input-field-error-message oxd-input-group__message']");
    By optFirstName = By.xpath("(//div[@role='option' and @class='oxd-autocomplete-option'])[1]/span");
    //Có thể viết gọn hơn thành //div[@role='option'])[1]/span miễn là khi search trên DOM trong devTools hiện 1 kết quả là được.
    //Giải thích: // - tìm khắp DOM thấy chỗ nào role='option', lấy div thứ 1. Rồi trong div đó chứa tag span chứa keyword
    // nên ta có lệnh /span là đi tiếp xuống chỗ tag con là span.
    By msgNoRecordsFound = By.xpath("//div[@class='oxd-toast oxd-toast--info oxd-toast-container--toast']");

    By btnDeleteSelected = By.cssSelector("button[class='oxd-button oxd-button--medium oxd-button--label-danger orangehrm-horizontal-margin']");
    By popupDelete = By.cssSelector("div[role='document']");
    By btnClosePopup = By.xpath("//button[normalize-space()='×']");
    By btnCancelDelete = By.cssSelector("button[class='oxd-button oxd-button--medium oxd-button--ghost orangehrm-button-margin']");
    By btnYesDelete = By.cssSelector("button[class='oxd-button oxd-button--medium oxd-button--label-danger orangehrm-button-margin']");
    By msgDeleteSuccess = By.id("oxd-toaster_1");

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
//       if(isNotDisplayed(tabUserMng))
//            click(tabMore);
        click(tabUserMng);
        click(btnUsers);
    }
*/
    //Search result
    public boolean isSearchResultDisplayed()  {
        isDisplayed(rows);
        moveToElement(rows);
        return isDisplayed(rows);
    }
    public boolean isSearchResultDisplayed2(String keyword) {
        try {
            isDisplayed(rows);
            logger.info("With "+ keyword + " has results");
            return true;
        } catch (TimeoutException e) {
            logger.info(keyword + " does not exist.");
            return false;
        }
    }
    public boolean isSearchResultNotDisplayed(){
        return isNotDisplayed(rows);
    }
    public void chooseOneFromSearchList (){
        isDisplayed(rows);
        moveToElement(rows);
    }

    //User name Search
    public void searchUsername (String keyword){
        isDisplayed(tabUserMng);
        sendKeys(usernameSearchBox, keyword);
        enter();
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
    public List<UserInfo> getSearchList(String username, String rl, String nm) {
            List<UserInfo> usersList = new ArrayList<>();
            List<WebElement> list = driver.findElements(tableSearchResults);
            for (WebElement userName : list) {
                String username = userName.findElement(By.xpath("(//div[contains(text(),'"+username+"')])[1]")).getText();
                String role = userName.findElement(By.xpath("(//div[contains(text(),'"+rl+"')])[2]")).getText();
                String name = userName.findElement(By.xpath("(//div[contains(text(),'"+nm+"')])[1]")).getText();
                String status = userName.findElement(By.xpath("(//div[@class='header'][normalize-space()='Status'])[1]/following-sibling::div")).getText();
                usersList.add(new UserInfo(username, role, name, status)
                );
            }
            return usersList;
        }*/
    //Userrole DropDown
    public boolean isUserRoleDisplayed (String userRole){
        try {
            isDisplayed(By.xpath("//div[@role='option']/span[normalize-space()='" + userRole + "']"));
            logger.info(userRole + " is displayed");
            return true;
        } catch (TimeoutException e) {
            logger.info(userRole + " does not exist.");
            return false;
        }
    }
    public void clickUserRole (){
        click(DdUserRole);
    }
    public void selectUserRole (String userRole){
        click(By.xpath("//div[@role='option']/span[normalize-space()='" + userRole + "']"));
        click(btnSearch);
    }
    public void clickUserRoleAdmin (){
        click(DdUserRole);
        click(optAdmin);
        click(btnSearch);
    }
    public List<String> getRoleSearchResult () {
        isDisplayed(rows);
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
        isNotDisplayed(optLoading);
/*        isDisplayed(optNames);*/
    }
    public boolean hasSearchResult() {
        try{
            isDisplayed(optNames);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
    public List<String> getSearchNames () {
        List<String> nameList = new ArrayList<>();
        List<WebElement> names = driver.findElements(optNames);
        for (WebElement name : names) {
            String employeename = name.getText();
            nameList.add(employeename);
        }
        return nameList;
    }
    public String getOptFirstName (){
        return getText(optFirstName);
    }
    public void selectFirstName () {
        isDisplayed(optFirstName);
        click(optFirstName);
        click(btnSearch);
    }
    public boolean isMsgNoRecordsFoundDisplayed (String keyword){
        try {
            isDisplayed(msgNoRecordsFound);
            logger.info("With "+ keyword + ", there is no employee name");
            return true;
        } catch (TimeoutException e) {
            logger.info("With "+ keyword + ", there are exist employee name");
            return false;
        }
    }
    public List<String> getNameSearchResult () {
        moveToPageBottom();
        isDisplayed(rows);
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
        return isNotDisplayed(rows);
    }

    //Delete selected
    public void checkUser(String usernameExpected) {
        moveToElement(rows);
        List<WebElement> rowList = driver.findElements(rows);
        for (WebElement row : rowList) {
            String username = row.findElement(By.xpath(".//div[@role='cell'][2]/div")).getText();
            System.out.println("Username chạy từ " +username);
            if (username.equals(usernameExpected)) {
                System.out.println("Found user with exact username");
                WebElement checkbox = row.findElement(By.cssSelector("div[class='oxd-table-card-cell-checkbox']"));
                //            WebElement checkbox = row.findElement(By.cssSelector("input[type='checkbox']"));
                if (!checkbox.isSelected()) {
                    checkbox.click();
                    System.out.println("Checked the username's checkbox");
                }
                break;
            }
        }
    }
    public boolean isUserChecked(String usernameExpected) /*throws InterruptedException*/ {
        moveToElement(rows);
        List<WebElement> rowList = driver.findElements(rows);
        for (WebElement row : rowList) {
            String username = row.findElement(By.xpath(".//div[@role='cell'][2]/div")).getText();
            System.out.println("Username chạy từ" +username);
            if (username.equals(usernameExpected)) {
                System.out.println("xác nhận đến bước nay chua");
//                Thread.sleep(3000);
                return row.findElement(By.cssSelector("input[type='checkbox']")).isSelected();
            }
        }
        return false;
    }
    public void clickDeleteSelectedButton (){
        moveToElement(btnDeleteSelected);
        click(btnDeleteSelected);
    }
    public boolean isPopupDeleteDisplayed(){
        return isDisplayed(popupDelete);
    }
    public boolean isPopupDeleteClosed(){
        return isNotDisplayed(popupDelete);
    }
    public void closePopupDelete (){
        click(btnClosePopup);
    }
    public void notDelete (){
        click(btnCancelDelete);
    }
    public void deleteSelected (){
        click(btnYesDelete);
    }
    public boolean isDeleteSuccessMessageDisplayed (){
        return isDisplayed(msgDeleteSuccess);
    }
    public boolean checkUserAfterDelete (String deletedUsername) {
        moveToElement(rows);
        List<WebElement> rowList = driver.findElements(rows);
        for (WebElement row : rowList) {
            String username = row.findElement(By.xpath(".//div[@role='cell'][2]/div")).getText();
            System.out.println("Verify deleted username flow chạy từ" +username);
            if (username.equals(deletedUsername)) {
                System.out.println("Vẫn tồn tại username bị xóa");
                return false; }
        }
        System.out.println("Username đã được xóa thành công");
        return true;
    }

    //Edit Selected User Info
    public void clickEditButton (String usernameExpected) {
        moveToElement(rows);
        By rowByUsername = By.xpath("//div[@role='row'][.//div[@role='cell'][2]/div[normalize-space()='"+usernameExpected+"']]");
        WebElement row = wait.until(ExpectedConditions.visibilityOfElementLocated(rowByUsername));
        WebElement editButton = row.findElement(By.xpath(".//button[.//i[contains(@class,'bi-pencil-fill')]]"));
        wait.until(ExpectedConditions.elementToBeClickable(editButton)).click();

//
/*        List<WebElement> rowList = driver.findElements(rows);
        for (WebElement row : rowList) {
            String username = row.findElement(By.xpath(".//div[@role='cell'][2]/div")).getText();
            System.out.println("Username chạy từ" +username);
            if (username.equals(usernameExpected)) {
                System.out.println("Found user with exact username 2");*/
//                WebElement btnEdit = row.findElement(By.xpath(".//button[.//i[contains(@class,'bi-pencil-fill')]]"));
//               WebElement btnEdit = row.findElement(By.xpath(".//button[@class='oxd-icon-button oxd-table-cell-action-space'][2]"));
//               WebElement btnEdit = row.findElement(By.xpath(".//button[@type='button'][2]"));
//                wait.until(ExpectedConditions.elementToBeClickable(editButton)).click();
    }
    public boolean isChangedUsernameDisplayedInCurrentPage (String editUserName){
        //Đây mới chỉ quét kết quả trên trang hiện tại thôi, còn lỡ có nhiều trang thì phải tìm cách khác
        isDisplayed(rows);
        System.out.println("Navigate back to User System page");
        moveToElement(rows);
        // Đây là cho 1 trang hiện tại
        List<WebElement> rowList = driver.findElements(rows);
        for (WebElement row : rowList) {
            String username = row.findElement(By.xpath(".//div[@role='cell'][2]/div")).getText();
            System.out.println("Verify deleted username flow chạy từ" +username);
            if (username.equals(editUserName)) {
                System.out.println("Username is changed successfully");
                return true;
            }
        }
        System.out.println("Username is not changed");
        return false;
    }
    public boolean isChangedUsernameDisplayedWithPages(String usernameExpected) {
        while (true) {
            // 1. Lấy tất cả row của page hiện tại
            List<WebElement> rowList = driver.findElements(rows);
            // 2. Quét từng row ở trang hiện tại
            for (WebElement row : rowList) {
                String username = row.findElement(By.xpath(".//div[@role='cell'][2]/div")).getText();
                System.out.println("Checking username: " + username);
                if (username.equals(usernameExpected)) {
                    System.out.println("Username is found: " + usernameExpected);
                    return true;
                }
            }
            // 3. Không tìm thấy ở page hiện tại
            // Kiểm tra còn trang tiếp theo không
            moveToPageBottom();
            if (isDisplayed(btnNextPage)) {
                WebElement nextButton = driver.findElement(btnNextPage);
                if (!nextButton.isEnabled()) {
                    System.out.println("Username is not found in all pages");
                    return false;
                }
                // 4. Sang page tiếp theo
                nextButton.click();
                // 5. Chờ table load lại
                wait.until(ExpectedConditions.visibilityOfElementLocated(rows));
            }
        }
    }
}

/*    public void navigateJobTitleScreen (){
        if(isNotDisplayed(tabJob)){
            click(tabMore);
            click(tabJob);}
        else click(tabJob);
        click(btnJobTitle);
    }*/