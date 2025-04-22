package helper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class CommonActions {

    private WebDriver driver;
    private WebDriverWait webDriverWait;

    Logger log = LogManager.getLogger(CommonActions.class);

    public CommonActions(WebDriver driver) {
        this.driver = driver;
    }

    public WebDriverWait waitUntilElementIsVisible(WebDriver driver, int sec, WebElement element) {
        webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(sec));
        webDriverWait.until(ExpectedConditions.visibilityOf(element));
        return webDriverWait;
    }

    public boolean isElementPresent(WebElement element) {
        try {
            boolean flag = element.isDisplayed();
            LoggerHelper.info("Element" + element.toString() + "Present on UI is:\t" + flag);
            return flag;
        } catch (Exception e) {
            System.out.println("UI Element is not present and exception is occurred " + e);
            return false;
        }
    }

    public void click(WebElement element) {
        if (isElementPresent(element)) {
            element.click();
        } else {
            LoggerHelper.info("Element is not present and click on element is not performed");
        }
    }

    public void waitFor(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            LoggerHelper.info("Exception details : " + e.getMessage());
        }
    }

    public void enterData(WebElement element, String data) {
        if (isElementPresent(element)) {
            element.sendKeys(data);
        } else {
            LoggerHelper.info("Element is not present and unable to send the data to element.");
        }
    }

    public void verifyAsserFailWithException(String msg, Error e) {
        System.out.println(failMsg(msg));
        LoggerHelper.info(e.getMessage());
        fail(e.getMessage());
        Assert.fail(failMsg(msg));
    }

    public void verifyAssertTrue(boolean condition, String passMsg, String failMsg) {
        try {
            Assert.assertTrue(condition, failMsg(failMsg));
            pass(passMsg);
        } catch (Error e) {
            verifyAsserFailWithException(failMsg, e);
        }

    }

    // Assertion Methods ................
    public String passMsg(String msg) {
        return "PASS :" + msg;
    }

    public String failMsg(String msg) {
        return "FAIL :" + msg;
    }

    public void pass(String msg) {
        LoggerHelper.info(passMsg(msg));
    }

    public void fail(String msg) {
        LoggerHelper.info(failMsg (msg));
    }

    public void info(String msg) {
        LoggerHelper.info(msg);
    }

    public void assertFail(String failMsg) {
        fail(failMsg);
        Assert.fail(failMsg);
    }

}
