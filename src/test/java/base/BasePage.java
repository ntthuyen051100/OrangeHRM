package base;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import utils.ConfigReader;

import java.time.Duration;
import java.util.List;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    Actions action;

    // Khởi tạo Wait một lần duy nhất tại Constructor
    public BasePage(WebDriver driver) {
    this.driver = driver;
/*Sau khi có file config.properties + class ConfigReader thì có thể chỉnh lại thời gian wait theo trog file config như sau
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(50));*/
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(Long.parseLong(ConfigReader.getPropValue("implicitlyWait_timeout"))));
        this.action = new Actions(driver);
    }
    // class để rút gọn những action của driver
/*    public void setWait(By locator){
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }*/
    public void click(By locator) {
/*        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        driver.findElement(locator).click();*/
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).click();
    }

    public void sendKeys(By locator, String text) {
/*        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        driver.findElement(locator).sendKeys(text);*/
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).sendKeys(text);
    }

    public void clear(By locator) {
/*        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        driver.findElement(locator).clear();*/
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).clear();
    }

    public String getText(By locator) {
/*        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        return driver.findElement(locator).getText();*/
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText();
    }
    //Chú ý đặc biệt hàm này
    public boolean isDisplayed(By locator) {
/*        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        return driver.findElement(locator).isDisplayed();*/
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isDisplayed();
    }
    public boolean isNotDisplayed(By locator) {
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }
    public boolean isSelected (By locator){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isSelected();
    }
    public void enter() {
        action.sendKeys(Keys.ENTER).build().perform();
    }
    public void moveToElement(By locator){
        isDisplayed(locator);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({block:'center'});",driver.findElement(locator));
    }
    public void moveToPageBottom(){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0,document.body.scrollHeight)");
    }
    public void clickByJs(By locator){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();",driver.findElement(locator));
    }
    //Handle Data Table
    public void checkContainsSearchTableByColumn(int column, String value) throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        List<WebElement> totalRows = driver.findElements(By.xpath("//div[@id='example_wrapper']//tbody/tr"));
        Thread.sleep(1);
        System.out.println("Số kết quả cho từ khóa (" + value + "): " + totalRows.size());

        for (int i = 1; i <= totalRows.size(); i++) {
            boolean res = false;
            WebElement title = driver.findElement(By.xpath("//div[@id='example_wrapper']//tbody/tr[" + i + "]/td[" + column + "]"));
            // js.executeScript("arguments[0].scrollIntoView(true);", title);
            res = title.getText().toUpperCase().contains(value.toUpperCase());
            System.out.println("Dòng thứ " + i + ": " + res + " - " + title.getText());
            Assert.assertTrue(res, "Dòng thứ " + i + " (" + title.getText() + ")" + " không chứa giá trị " + value);
        }
    }

}