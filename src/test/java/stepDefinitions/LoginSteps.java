package stepDefinitions;

import factory.BaseClass;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import utilities.DataReader;

import java.util.HashMap;
import java.util.List;

public class LoginSteps {

    WebDriver driver;
    HomePage homePage;
    LoginPage loginPage;
    MyAccountPage myAccPage;

    List<HashMap<String, String>> dataMap; //Data driven

    @Given("the user navigates to login page")
    public void user_navigates_to_login_page() {

        BaseClass.getLogger().info("Go to my account-->Click on Login..");
        homePage = new HomePage(BaseClass.getDriver());

        homePage.clickMyAccount();
        homePage.clickLogin();
    }

    @When("user enters email as {string} and password as {string}")
    public void user_enters_email_as_and_password_as(String email, String pwd) {
        BaseClass.getLogger().info("Entering email and password..");
         loginPage = new LoginPage(BaseClass.getDriver());
         loginPage.enterTxtEmailAddress(email);
         loginPage.enterTxtPwd(pwd);
    }

    @When("the user clicks on the Login button")
    public void click_on_login_button() {
        loginPage.clickSubmit();
        BaseClass.getLogger().info("clicked on login button...");
    }

    @Then("the user should be redirected to the MyAccount Page")
    public void user_navigates_to_my_account_page() {
        myAccPage = new MyAccountPage(BaseClass.getDriver());
        boolean targetPage = myAccPage.myAccountPageVerification();

        Assert.assertTrue(targetPage);
    }

   // ****** Data Driven Test ******
    @Then("the user should be redirected to the MyAccount Page by passing email and password with excel row {string}")
    public void check_user_navigates_to_my_account_page_by_passing_email_and_password_with_excel_data(String rows) {
        dataMap = DataReader.data(System.getProperty("user.dir")+"\\testData\\Opencart_LoginData.xlsx", "Sheet1");

        int index = Integer.parseInt(rows)-1;
        String email = dataMap.get(index).get("Username");
        String pwd = dataMap.get(index).get("Password");
        String exp_result = dataMap.get(index).get("Res");

        loginPage = new LoginPage(BaseClass.getDriver());
        loginPage.enterTxtEmailAddress(email);
        loginPage.enterTxtPwd(pwd);
        loginPage.clickSubmit();

        myAccPage = new MyAccountPage(BaseClass.getDriver());
        try
        {
            boolean targetPage= myAccPage.myAccountPageVerification();
            //System.out.println("target page: "+ targetPage);
            if(exp_result.equals("Valid"))
            {
                if(targetPage==true)
                {
                    MyAccountPage myaccpage=new MyAccountPage(BaseClass.getDriver());
                    myaccpage.clickLogoutRightSideOptions();
                    Assert.assertTrue(true);
                }
                else
                {
                    Assert.assertTrue(false);
                }
            }

            if(exp_result.equals("Invalid"))
            {
                if(targetPage==true)
                {
                    myAccPage.clickLogoutRightSideOptions();
                    Assert.assertTrue(false);
                }
                else
                {
                    Assert.assertTrue(true);
                }
            }
        }
        catch(Exception e)
        {
            Assert.assertTrue(false);
        }
    }
}
