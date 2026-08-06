package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

public class EditUserPage extends BasePage {
    public EditUserPage(WebDriver driver) {
        super(driver);
    }

    By title = By.cssSelector(".oxd-text.oxd-text--h6.orangehrm-main-title");
 //   By ddUserRole = By.xpath("(//div[@class='oxd-select-wrapper'])[1]");
    By employeeName = By.cssSelector("input[placeholder='Type for hints...']");
    By username = By.cssSelector("input[autocomplete='off']");
    By btnSave = By.cssSelector("button[type='submit']");
    By editForm = By.cssSelector(".orangehrm-card-container");
    By loadingOverlay = By.cssSelector("div[class='oxd-form-loader']");


    public boolean isTitleDisplayed (){
        return isDisplayed(title);
    }
    public boolean isFormDisplayed (){
        return isDisplayed(editForm);
    }
    public void setUsername (String editName) {
        isDisplayed(username);
        isNotDisplayed(loadingOverlay);
/*
        clear(username);
Cài đặt Autocomplete: Nếu ô nhập có thuộc tính autocomplete được bật, hàm clear() có thể không hoạt động như mong đợi.
 you can try an alternative text clearance by sending 'Ctrl+A+Delete' key combination using sendKeys method of the element's object:*/
        driver.findElement(username).sendKeys(Keys.chord(Keys.CONTROL,"a", Keys.DELETE));
        sendKeys(username, editName);
    }
    public void submitChanges (){
        isDisplayed(btnSave);
        isNotDisplayed(loadingOverlay);
        moveToElement(btnSave);
        click(btnSave);
    }
}
