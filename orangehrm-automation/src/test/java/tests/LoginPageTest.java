package tests;

import base.BaseTest;
import helper.CommonActions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;
import pages.LoginPage;

public class LoginPageTest extends BaseTest {

    LoginPage loginPage;
    CommonActions commonActions;

    @Test(description = "Verify whether user is able to login to the application with valid credentials")
    public void verifyLoginWithValidCredentials() {
        loginPage = new LoginPage(driver);
        commonActions = new CommonActions(driver);
        loginPage.loginToApplication(prop.getProperty("Username"), prop.getProperty("Password"));
        commonActions.verifyAssertTrue(loginPage.loginFlag(),
                "User is able to login to the application",
                "User is not able to login to the application");

    }

    @Test(description = "Verify whether user is not able to login to the application with invalid credentials")
    public void verifyLoginWithInValidCredentials() {
        loginPage = new LoginPage(driver);
        commonActions = new CommonActions(driver);
        loginPage.loginToApplication(prop.getProperty("InvalidUsername"), prop.getProperty("InvalidPassword"));
        commonActions.verifyAssertTrue(loginPage.invalidLoginFlag(),
                "User is not able to login with invalid credentials",
                "User is able to login to the application with invalid credentials");

    }

    @Test(description = "Verify whether it is having validations for empty username and password")
    public void verifyEmptyUsernamePassword() {
        loginPage = new LoginPage(driver);
        commonActions = new CommonActions(driver);
        loginPage.loginToApplication("", "");
        commonActions.verifyAssertTrue(loginPage.emptyLoginFlag(),
                "Showing error message on login with empty credentials",
                "Not showing error message on login with empty credentials");

    }

    @Test(description = "Verify whether forgot password link is functional")
    public void verifyForgotPasswordLink() {
        loginPage = new LoginPage(driver);
        commonActions = new CommonActions(driver);
        loginPage.clickOnForgotPasswordLink();
        commonActions.verifyAssertTrue(loginPage.forgotPasswordFlag(),
                "Forgot password link is functional",
                "Forgot password link is not functional");

    }

    @Test(description = "Verify whether user is able to logout from the application")
    public void verifyLogout() {
        loginPage = new LoginPage(driver);
        commonActions = new CommonActions(driver);
        loginPage.logoutFromApplication(prop.getProperty("Username"), prop.getProperty("Password"));
        commonActions.verifyAssertTrue(loginPage.loginPageFlag(),
                "User is able to logout from the application",
                "User is not able to logout from the application");

    }
}
