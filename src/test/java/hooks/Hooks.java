package hooks;

import factory.BaseClass;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.util.Properties;

public class Hooks {

    WebDriver driver;
    Properties properties;

    @Before
    public void setup() throws IOException {
        driver = BaseClass.initializeBrowser();

        properties = BaseClass.getProperties();
        driver.get(properties.getProperty("appURL"));
        driver.manage().window().maximize();
    }

    @After
    public void tearDown(Scenario scenario) {
        driver.quit();
    }

    @AfterStep
    public void addScreenshot(Scenario scenario) {
        //this is for cucumber junit report
        if(scenario.isFailed()) {
            TakesScreenshot takeScreenshot = (TakesScreenshot) driver;
            byte[] screenshot = takeScreenshot.getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", scenario.getName()); //this will add the screenshot directly to the cucumber report - cucumber junit report.

        }
    }
}
