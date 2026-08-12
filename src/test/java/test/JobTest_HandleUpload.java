package test;

import base.BaseTest;
import net.datafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pageobject.PageManager_RunSequential;
import utils.ConfigReader;
import java.io.File;
import static utils.LogUtils.logger;

/*Đã làm được trong class này:
- Viết scripts cho chức năng upload file: vào BasePage tới method uploadFile(File file) để xem logic: ko click vào nút upload,
mà sẽ truyển thẳng đường dẫn file có sẵn trong project vào luôn (thực hiện thêm trực tiếp file vào folder project
src/test/resources/UploadData tại máy local)
- Chỉ định thứ tự chạy của các method @Test trong 1 class bằng 1 trong 3 cách dưới:
  + Thêm priority ở ngay @Test: ko ghi thì mặc định = 0, chạy trước tất cả
  + Thêm dependsOnMethods: @Test(dependsOnMethods = {"addJobTitle"})-> method này chỉ đc chạy khi method addJobTitle Pass
  + Dùng testng.xml để sắp xếp thứ tự chạy
- Dùng dataFaker để gen data tự động: thêm dependency DataFaker vào pom.xml -> vào class Test muốn dùng tiến hành new mới
 Faker faker = new Faker(); -> sau đó dùng dấu chấm để truy cập vào hàm mình muốn sử dụng là xong
*/

/*Đây là phiên bản chạy JobTest với thiết lập basic, chỉ chạy tuần tự nên sẽ ko extends BasePage dùng DriverManager hay
DriverFactory. Chú ý khi dùng thì driver ở 2 class BaseTest và class PageManager phải fit nhau như sau
    - PageManager: Nếu dùng PageManager để đỡ việc new mới, thì check cẩn thận class này đang nhận giá trị đầu
vào là driver thường (PageManager_RunSequential) hay là DriverFactory, DriverManager (PageManager)
    -  BaseTest: Có 3 phiên bản BaseTest dành cho driver thường, driver của DriverManager, driver của DriverFactory
=> 2 class này giống nhau về kiểu driver thì khi BaseTest có driver xong thì truyền vào PageManager mới đúng => ko bị lỗi*/

public class JobTest_HandleUpload extends BaseTest {
/*  private final PageManager_RunSequential page = new PageManager_RunSequential(driver);
Lúc này vì PageManager giống như PageObject bth có driver là tham số đầu vào nên nếu để ngoài class @Test thì sẽ bị
bị lỗi null vì BaseTest chưa chạy để set giá trị driver = ChromeDriver. => đưa lệnh new vào @Test luôn
TUYỆT ĐỐI KHÔNG khai báo + new luôn trong mỗi @BeforeMethod vì khai báo trong đó sẽ chỉ thành biến cục bộ, các method sau ko reuse được*/
    PageManager_RunSequential page;

    @BeforeMethod
    public void moveToJobTitlesPage(){
        SoftAssert softAssert = new SoftAssert();
        /*KHÔNG ĐƯỢC LÀM KIỂU NÀY: PageManager_RunSequential page = new PageManager_RunSequential(driver);*/
        page = new PageManager_RunSequential(driver);
        page.loginPage().login(ConfigReader.getPropValue("username"), ConfigReader.getPropValue("password"));
        page.commonPage().clickAdmin();
        page.jobPage().clickDropDownJob();
        page.jobPage().clickJobTitilesOpt();
        Boolean moveToRightPage = page.jobPage().isJobTitlesPageDisplayed();
        Assert.assertTrue(moveToRightPage, "Move to wrong page");
        logger.info("Move to Job titles page");
    }
    //Handle upload function
    @Test(priority = 1)
    public void addJobTitle (){
        page.jobPage().clickBtnAdd();
        String actualUrl = driver.getCurrentUrl();
        String expectedUrl = "https://opensource-demo.orangehrmlive.com/web/index.php/admin/saveJobTitle";
        Assert.assertEquals(actualUrl,expectedUrl,"Move to wrong site");
        logger.info("Move to Add job title page");

        String jobTitle = "doctor";
/*        String jobTitle = faker.job().title();*/
        page.jobPage().typeJobTitle(jobTitle);

/*        String jobDescrip = "Doctrs work in hospital.";*/
        Faker faker = new Faker();
        String jobDescrip = faker.strangerThings().quote();
        page.jobPage().typeDiscription(jobDescrip);

        File file = new File("src/test/resources/UploadData/authentication_userMockData.csv");
        page.jobPage().uploadJobSpec(file);
        String uploadedFileName = page.jobPage().getUploadedFileName();
        Assert.assertEquals(uploadedFileName,"authentication_userMockData.csv","Uploaded fail");
        logger.info("Uploaded successfully");

        /*String note = "This is a note";*/
        String note = faker.harryPotter().character();
        page.jobPage().typeNote(note);

        page.jobPage().clickBtnSave();
        Boolean isMsgDisplayed = page.jobPage().isMsgSuccessfullySavedDisplayed();
        Assert.assertTrue(isMsgDisplayed,"Successfully added message is not displayed");
        logger.info("New job is added successfully");
        String actualUrl2 = driver.getCurrentUrl();
        String expectedUrl2 = "https://opensource-demo.orangehrmlive.com/web/index.php/admin/viewJobTitleList";
        Assert.assertEquals(actualUrl,expectedUrl,"Move to wrong site");
        logger.info("Return to Add job title page");
    }
    @Test(priority = 3)
    public void deleteJobTitle (){
        String jobTitle = "doctor";
        Boolean isJobDisplayed =page.jobPage().isJobTitleDisplayed(jobTitle);
        Assert.assertTrue(isJobDisplayed);
        page.jobPage().clickCheckbox(jobTitle);
        logger.info("Job title checkbox is selected");
        page.jobPage().clickBtnDeleteSelected();
        Assert.assertTrue(page.jobPage().isPopupConfirmDeleteDisplayed());
        logger.info("Confirm delete selected job popup is displayed");

        page.jobPage().clickBtnClosePopup();
        Assert.assertTrue(page.jobPage().isPopupConfirmDeleteClosed());
        logger.info("Confirm delete popup is closed after clicking button x");

        page.jobPage().clickBtnDeleteSelected();
        page.jobPage().clickBtnNoPopup();
        Assert.assertTrue(page.jobPage().isPopupConfirmDeleteClosed());
        logger.info("Confirm delete popup is closed after clicking button No");

        page.jobPage().clickBtnDeleteSelected();
        page.jobPage().clickBtnYesPopup();
        Assert.assertTrue(page.jobPage().isPopupConfirmDeleteClosed());
        logger.info("Confirm delete popup is closed after clicking button Yes");
        Assert.assertTrue(page.jobPage().isMsgSuccessfullySavedDeleted());
        logger.info("Job is deleted successfully");

        Assert.assertFalse(page.jobPage().isJobTitleDisplayed(jobTitle));
        logger.info("Deleted job is not displayed in table");
    }
    @Test(priority = 2)
    /*@Test(dependsOnMethods = {"addJobTitle"})*/
    public void editJobTitle (){
        String jobTitle = "doctor";
        page.jobPage().clickEditButton(jobTitle);
        logger.info("Click edit button of job's name is "+jobTitle);
        Assert.assertTrue(page.jobPage().isEditJobTitlePageDisplayed());
        logger.info("Navigate to edit job title page");
    }

}
