package stepDefinitions;

import factory.BaseClass;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import pageObjects.LoginPage;

import java.util.Map;

public class RegistrationSteps {

    WebDriver driver;
    HomePage homePage;
    LoginPage loginPage;
    AccountRegistrationPage registrationPage;

    @Given("the user navigates to Register Account page")
    public void user_navigates_to_register_account_page() {
        homePage = new HomePage(BaseClass.getDriver());
        homePage.clickMyAccount();
        homePage.clickRegister();
    }

    @When("the user enters the details into below fields")
    public void user_enters_the_details_into_below_fields(DataTable dataTable) {

        Map<String, String> dataMap = dataTable.asMap(String.class, String.class);

        registrationPage = new AccountRegistrationPage(BaseClass.getDriver());
        registrationPage.setFirstName(dataMap.get("firstName"));
        registrationPage.setLastName((dataMap.get("lastName")));
        registrationPage.setEmail(BaseClass.randomAlphaNumeric()+"@mail.com");
        registrationPage.setTelephone(dataMap.get("telephone"));
        registrationPage.setPswd(dataMap.get("password"));
        registrationPage.setPwdConfirm(dataMap.get("password"));
    }

    @When("the user selects Privacy Policy")
    public void user_selects_privacy_policy() {
        registrationPage.setPrivPolicy();
    }

    @When("the user clicks on Continue button")
    public void user_selects_continue_button() {
        registrationPage.clickContinue();
    }
    @Then("the user account should get created successfully")
    public void user_account_should_get_created_successfully() {

        String confmsg = registrationPage.getConfirmationMsg();
        Assert.assertEquals(confmsg, "Your Account Has Been Created!");
    }

}
