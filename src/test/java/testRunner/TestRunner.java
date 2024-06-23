package testRunner;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
        //features= {".//Features/"},
        //features = {".//Features/Login.feature"},
        //features = {".//Features/Registration.feature"},
        //features= {".//Features/LoginDDTExcel.feature"},
        features= {".//Features/Login.feature",".//Features/Registration.feature"},
        //features= {"@target/rerun.txt"}, //to execute failed test features
        glue = {"stepDefinitions", "hooks"}, //specified is the package name with step definition files
        plugin = {"pretty", "html:reports/myreport.html", //for junit reports
                "rerun:target/rerun.txt", //default folder created at run-time to store failed scenarios.
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:" //for cucumber report. Usage: To begin using the adapter, add the com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter plugin to the runner.
        },

        dryRun = false,    // checks mapping between scenario steps and step definition methods
        monochrome = true,    // to avoid junk characters in output
        publish = true   // to publish report in cucumber server
        //tags="@sanity"  // this will execute scenarios tagged with @sanity
        //tags="@regression"
        //tags="@sanity and @regression" //Scenarios tagged with both @sanity and @regression
        //tags="@sanity and not @regression" //Scenarios tagged with @sanity but not tagged with @regression
        //tags="@sanity or @regression" //Scenarios tagged with either @sanity or @regression
)
public class TestRunner {

}
