package tests;

import base.BaseTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;

public class UnitTest extends BaseTest {

    Logger log = LogManager.getLogger(UnitTest.class);
    LoginPage loginPage;

    @Test(description = "Verify whether user is able to run the demo test")
    public void unitTest() {
        loginPage = new LoginPage(driver);
        loginPage.loginToApplication(prop.getProperty("Username"), prop.getProperty("Password"));
        Assert.assertTrue(false);

    }

}
