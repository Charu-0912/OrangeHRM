package base;

import com.aventstack.chaintest.plugins.ChainTestListener;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

public class BaseTest {

    public static WebDriver driver;
    public static Properties prop;
    Logger log = LogManager.getLogger(BaseTest.class);

    public BaseTest() {

    }

    @BeforeClass
    public void loadPropertiesFile() {

        try {
            String filePath = "src/main/resources/config.properties";
            prop = new Properties();
            FileInputStream file = new FileInputStream(filePath);
            prop.load(file);
        } catch (FileNotFoundException e) {
            log.info("Config file not found: {}", e.getMessage());
        } catch (IOException e) {
            log.info("Error while reading the config file: {}", e.getMessage());
        }

    }

    @BeforeMethod
    public void browserInitialization(ITestResult result) {

        String browserName = prop.getProperty("BrowserName");
        if (browserName.equalsIgnoreCase("Chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        } else if (browserName.equalsIgnoreCase("Firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        } else {
            throw new InvalidArgumentException("Please select valid browser");
        }

        log.info("Launching {} browser", browserName);

        driver.get(prop.getProperty("URL"));
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

    }


    @AfterMethod
    public void afterMethod(ITestResult result) {
        String description = result.getMethod().getDescription();
        String testCaseName = (description != null && !description.isEmpty())
                ? description
                : result.getMethod().getMethodName();

        if (result.getStatus() == ITestResult.FAILURE) {
            ChainTestListener.log("Finished test: " + testCaseName + " — Status: FAILED");
            takeScreenShotAndAttach(result.getMethod().getMethodName());
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            ChainTestListener.log("Finished test: " + testCaseName + " — Status: PASSED");
        } else if (result.getStatus() == ITestResult.SKIP) {
            ChainTestListener.log("Finished test: " + testCaseName + " — Status: SKIPPED");
        }

        driver.quit();
    }

    public void takeScreenShotAndAttach(String methodName) {
        SimpleDateFormat formatter = new SimpleDateFormat("MM_dd_yyyy_hh_mm");
        String screenshotPath = System.getProperty("user.dir") + "\\Failed_Screenshots\\" +
                methodName + "_" + formatter.format(new Date()) + ".png";

        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            File destFile = new File(screenshotPath);
            FileUtils.copyFile(scrFile, destFile);

            byte[] imageBytes = FileUtils.readFileToByteArray(destFile);

            ChainTestListener.embed(imageBytes, "image/png");

        } catch (IOException e) {
            System.out.println("Exception occurred while saving or reading screenshot: " + e.getMessage());
        }

    }
}
