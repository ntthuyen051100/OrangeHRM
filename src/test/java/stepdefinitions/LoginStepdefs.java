package stepdefinitions;

import hooks.Hooks;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import pages.LoginPage;
import utils.ConfigReader;

public class LoginStepdefs {
    LoginPage loginPage = new LoginPage(Hooks.getDriver());
    @Given("Navigate to login page")
    public void navigateToLoginPage() {
        Hooks.getDriver().get(ConfigReader.getPropValue("url"));
/*        throw new PendingException();*/
    }

    @When("Enter username {string} and password {string}")
    public void enterUsernameAndPassword(String arg0, String arg1) {
        loginPage.inputUsername(arg0);
        loginPage.inputPw(arg1);
    }

    @And("Click the Login button")
    public void clickTheLoginButton() {
        loginPage.clickLogin();
    }

    @Then("Navigate to the Dashboard page")
    public void navigateToTheDashboardPage() {
        String actualUrl = Hooks.getDriver().getCurrentUrl();
        String expectedUrl = "https://opensource-demo.orangehrmlive.com/web/index.php/dashboard/index";
        Assert.assertEquals(actualUrl, expectedUrl, "Trang web chưa chuyển hướng đến đúng URL mong muốn.");
        System.out.println("Navigate to the correct site");
    }
}
