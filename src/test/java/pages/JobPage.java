package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import javax.lang.model.util.Elements;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static utils.LogUtils.logger;

public class JobPage extends BasePage {

    public JobPage(WebDriver driver) {
        super(driver);
    }
//Add Job Title page - Handle Upload function
    //Job titles page
    By ddJob = By.xpath("(//span[@class='oxd-topbar-body-nav-tab-item' and contains(text(),'Job')])");
    By optJobTitles = By.xpath("(//ul/li/a[@class='oxd-topbar-body-nav-tab-link' and contains(text(),'Job Titles')])");
    By titleJobPage = By.xpath("(//h6[@class='oxd-text oxd-text--h6 orangehrm-main-title'])");
    By btnAdd = By.cssSelector("button[type='button'][class='oxd-button oxd-button--medium oxd-button--secondary']");
    By jobTitleList = By.cssSelector("div[class='oxd-table-card']");
    By btnDeleteSelected = By.cssSelector("button[class='oxd-button oxd-button--medium oxd-button--label-danger orangehrm-horizontal-margin']");
    By popupConfirmDelete =  By.cssSelector("div[role='document']");
    By btnClosePopup = By.cssSelector("button[class='oxd-dialog-close-button oxd-dialog-close-button-position']");
    By btnNoPopup = By.cssSelector("button[class='oxd-button oxd-button--medium oxd-button--ghost orangehrm-button-margin']");
    By btnYesPopup = By.cssSelector("button[class='oxd-button oxd-button--medium oxd-button--label-danger orangehrm-button-margin']");
    By msgSuccessfullyDeleted = By.cssSelector("div[class='oxd-toast oxd-toast--success oxd-toast-container--toast oxd-toast-list-leave-active oxd-toast-list-leave-to']");

    //Add job title page
    By textboxJobTitle = By.cssSelector("div[class='oxd-input-group oxd-input-field-bottom-space'] div input[class='oxd-input oxd-input--active']");
    By textboxDesciption = By.cssSelector("textarea[placeholder='Type description here']");
    By uploadJobSpec = By.cssSelector(".oxd-file-div.oxd-file-div--active");
    By uploadedFileName = By.cssSelector(".oxd-file-input-div");
    By textboxNote = By.cssSelector("textarea[placeholder='Add note']");
    By btnSave = By.cssSelector("button[type='submit']");
    By msgSuccessfullySaved = By.cssSelector("div[class='oxd-toast oxd-toast--success oxd-toast-container--toast'][aria-live='assertive']");

    //Edit Job Title Page
    By editJobPageTitle = By.cssSelector("h6[class='oxd-text oxd-text--h6 orangehrm-main-title']");



    //Job titles page
    public void clickDropDownJob() {
        isDisplayed(ddJob);
        click(ddJob);
    }
    public void clickJobTitilesOpt() {
        isDisplayed(optJobTitles);
        click(optJobTitles);
    }
    public boolean isJobTitlesPageDisplayed() {
        return isDisplayed(titleJobPage);
    }
    public void clickBtnAdd(){
        isDisplayed(btnAdd);
        click(btnAdd);
    }
    public boolean isJobTitleDisplayed(String jobTitleExpected) {
        isDisplayed(jobTitleList);
        moveToElement(jobTitleList);
        List<WebElement> jobs = getListOfElements(jobTitleList);
        for (WebElement job : jobs) {
            String jobTitle = job.findElement(By.xpath(".//div[@role='cell'][2]/div")).getText();
            logger.info("Job chạy từ " + jobTitle);
            if (jobTitle.equals(jobTitleExpected)) {
                logger.info("Found job with exact title");
                return true;
            }
        }
        System.out.println("There is no job with expected name");
        return false;
            }
    //Delete job
    public void clickCheckbox(String jobTitleExpected) {
        moveToElement(jobTitleList);
        List<WebElement> jobs = getListOfElements(jobTitleList);
        for (WebElement job : jobs) {
            String jobTitle = job.findElement(By.xpath(".//div[@role='cell'][2]/div")).getText();
            if (jobTitle.equals(jobTitleExpected)) {
                moveToWebElement(job);
/*                WebElement checkbox = job.findElement(By.cssSelector("input[type='checkbox']"));
                  checkbox.click();
element đúng nhưng khi run bị lỗi ElementClickInterceptedException: element click intercepted: Element <input data-v-6179b72a="" type="checkbox" value="8"> is not clickable at point (344, 362). Other element would receive the click: <i data-v-bddebfba="" data-v-6179b72a="" class="oxd-icon bi-check oxd-checkbox-input-icon"></i>
This error happens because you are trying to click the hidden HTML <input> checkbox directly, but the custom OrangeHRM
stylized icon (<i>) is layered over it and blocking the click. => nên thay vì click vào đúng element đó
1. click vào Target the parent element or the overlapping element that is actually visible to the user. */
                /*WebElement checkbox = job.findElement(By.cssSelector("div[class='oxd-table-card-cell-checkbox']"));
                checkbox.click();*/
/*2. Dùng js click thẳng vào, JavaScript bypasses Selenium's visibility and overlay checks, triggering the click directly on the DOM element.*/
                WebElement checkbox = job.findElement(By.cssSelector("input[type='checkbox']"));
                clickByJsElement(checkbox);
                logger.info("Checked the job's checkbox");
                break;
            }
        }
    }
    public void clickBtnDeleteSelected (){
        moveToElement(btnDeleteSelected);
        click(btnDeleteSelected);
    }
    public boolean isPopupConfirmDeleteDisplayed (){
        return isDisplayed(popupConfirmDelete);
    }
    public boolean isPopupConfirmDeleteClosed (){
        return isNotDisplayed(popupConfirmDelete);
    }
    public void clickBtnClosePopup(){
        click(btnClosePopup);
    }
    public void clickBtnNoPopup(){
        click(btnNoPopup);
    }
    public void clickBtnYesPopup(){
        click(btnYesPopup);
    }
    public boolean isMsgSuccessfullySavedDeleted(){
        return isDisplayed(msgSuccessfullyDeleted);
    }
    //Edit job
    public void clickEditButton (String jobNameExpected) {
        By rowByJobName = By.xpath("//div[@role='row'][.//div[@role='cell'][2]/div[contains(text(),'"+jobNameExpected+"')]]");
        WebElement row = wait.until(ExpectedConditions.visibilityOfElementLocated(rowByJobName));
        WebElement editButton = row.findElement(By.xpath(".//button[.//i[contains(@class,'bi-pencil-fill')]]"));
        wait.until(ExpectedConditions.elementToBeClickable(editButton)).click();
        }

    //Add job title page
    public void typeJobTitle (String job){
        sendKeys(textboxJobTitle, job);
    }
    public void typeDiscription (String passage){
        sendKeys(textboxDesciption, passage);
    }
    public void typeNote (String passage){
        sendKeys(textboxNote, passage);
    }
    public void clickBtnSave(){
        click(btnSave);
    }
    public boolean isMsgSuccessfullySavedDisplayed(){
        return isDisplayed(msgSuccessfullySaved);
    }
    /*CHÚ Ý NHẤT: HANDLE UPLOAD FILE*/
    public void uploadJobSpec (File file){
        uploadFile(file);
    }
    public String getUploadedFileName(){
        return getText(uploadedFileName);
    }

    //Edit Job Title Page
    public boolean isEditJobTitlePageDisplayed(){
        return isDisplayed(editJobPageTitle);
    }




}
