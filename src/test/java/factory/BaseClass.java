package factory;

import com.fasterxml.jackson.core.io.JsonStringEncoder;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.Properties;

public class BaseClass {
    static WebDriver driver;
    static Logger logger;
    static Properties properties;
    private static JsonStringEncoder NoopSpanExporter;

    public static WebDriver initializeBrowser() throws IOException {
       if (getProperties().getProperty("execution_env").equalsIgnoreCase("remote")) {
           DesiredCapabilities capabilities = new DesiredCapabilities();

           //os
           if (getProperties().getProperty("os").equalsIgnoreCase("windows")) {
               capabilities.setPlatform(Platform.WINDOWS);
           } else if (getProperties().getProperty("os").equalsIgnoreCase("mac")) {
               capabilities.setPlatform(Platform.MAC);
           } else {
               System.out.println("No matching OS.");
           }
           //browser
           switch (getProperties().getProperty("browser").toLowerCase()) {
               case "chrome":
                   capabilities.setBrowserName("chrome");
                   break;
               case "edge":
                   capabilities.setBrowserName("MicrosoftEdge");
                   break;
               default:
                   System.out.println("No matching browser.");
           }


           driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);
       }
       else if (getProperties().getProperty("execution_env").equalsIgnoreCase("local")) {
           switch (getProperties().getProperty("browser").toLowerCase()) {
               case "chrome":
                   System.setProperty("webdriver.chrome.silentOutput","true");
                   ChromeOptions chromeOptions = new ChromeOptions();
                   chromeOptions.addArguments("--remote-allow-origins=*","ignore-certificate-errors");
                   WebDriverManager.chromedriver().setup();


                   driver = new ChromeDriver(chromeOptions);
                   break;
               case "edge":
                   System.setProperty("webdriver.edge.silentOutput", "true");
                   EdgeOptions edgeOptions = new EdgeOptions();
                   edgeOptions.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS,true);
                   edgeOptions.addArguments("--remote-allow-origins=*");
                   driver = new EdgeDriver(edgeOptions);
                   break;
               default:
                   System.out.println("No matching browser.");
                   driver = null;
           }
       }
       driver.manage().deleteAllCookies();
       driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
       driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));

       return driver;
   }

   public static WebDriver getDriver() {
       return driver;
   }

   public static Properties getProperties() throws IOException {
       FileReader file = new FileReader(System.getProperty("user.dir") + "\\src\\test\\resources\\config.properties");
       properties = new Properties();
       properties.load(file);
       return properties;
   }

   public static Logger getLogger() {
       logger = LogManager.getLogger();
       return logger; //(Log4j)
   }

   public static String randomString() {
       String randomAlphabeticString = RandomStringUtils.randomAlphabetic(5);
       return  randomAlphabeticString;
   }

   public static String randomAlphaNumeric() {
       String stringAlphaNumeric = RandomStringUtils.randomAlphanumeric(10);
       return stringAlphaNumeric;
   }

}
