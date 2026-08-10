package base;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import utils.ConfigReader;

import java.io.File;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static utils.LogUtils.logger;

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
        logger.debug("Click element " + locator);
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
    public boolean isAppearedInDOM(By locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator)).isDisplayed();
    }
    public boolean isSelected (By locator){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isSelected();
    }
    public void enter() {
        action.sendKeys(Keys.ENTER).build().perform();
    }
    public void moveToElement(By locator){
/*        isAppearedInDOM(locator);*/
        isDisplayed(locator);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({block:'center'});",driver.findElement(locator));
    }
    public void moveToWebElement(WebElement element){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({block:'center'});",element);
    }
    public void moveToPageBottom(){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0,document.body.scrollHeight)");
    }
    public void clickByJsLocator(By locator){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();",driver.findElement(locator));
    }
    public void clickByJsElement(WebElement element){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();",element);
    }
    public List<WebElement> getListOfElements(By locator){
        return driver.findElements(locator);
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

/*    Handle Upload file (dưới là 1 luồng hoàn chỉnh, tuy nhiên vì chỉ viết cho action upload thôi nên chỉ viết về action,
gán giá trị, logic sẽ đẩy sang class page và class test của màn hình test tương ứng)
Chú ý:
- Không click vào phần tử input: Khi chạy automation, việc click trực tiếp vào nút "Browse/Chọn file" sẽ mở cửa sổ
    chọn file của hệ điều hành (Windows Explorer/Finder). Các công cụ như Selenium không thể tương tác với cửa sổ OS này.
    Bí quyết là dùng thẳng lệnh setInputFiles (Playwright) hoặc sendKeys (Selenium) vào thẻ <input>.
- Xử lý Input bị ẩn (Hidden Input): Nhiều UI hiện đại ẩn thẻ <input type="file"> đi và thiết kế một nút bấm khác đẹp hơn
    đè lên. Script trên vẫn hoạt động tốt vì automation tìm theo DOM selector chứ không phụ thuộc vào việc thẻ đó có
    hiển thị trên màn hình hay không.*/
    public void uploadFile(File file) {
        /* 1. Lấy đường dẫn của file trong dự án (bước gán giá trị cho file sẽ chuyển sang class Test tương ứng, ko
        hardcode ở đây)
        File file = new File("src/test/resources/UploadData/authentication_userMockData.csv");*/
        String absolutePath = file.getAbsolutePath();

/*        2. Tìm thẻ input[type='file'], vì phần lớn field nào upload cũng có attribute này nên hardcode luôn cũng ok.
Không muốn hardcode như này thì bỏ nội dung By.css... thay bằng biến locator, và khai báo vào parameter của class
By locator bên cạnh File file luôn cũng đc. */
        WebElement fileInput = driver.findElement(By.cssSelector("input[type='file']"));

        // 3. Dùng sendKeys để đẩy đường dẫn file vào input (Không click vào input)
        fileInput.sendKeys(absolutePath);

/*        // 4. Click nút Submit/Upload nếu có
        driver.findElement(By.id("submit-upload")).click();

       // 5. Kiểm tra kết quả thành công (nếu có msg)
        WebElement msg = driver.findElement(By.className("upload-success-message"));
        Assert.assertTrue(msg.isDisplayed());
        Assert.assertEquals(msg.getText(), "File uploaded successfully!");*/

    }
    }