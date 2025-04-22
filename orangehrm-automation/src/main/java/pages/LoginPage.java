package pages;

import helper.CommonActions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import helper.LoggerHelper;

public class LoginPage {

    private WebDriver driver;
    private CommonActions commonActions;

    public LoginPage(WebDriver driver) {

        this.driver = driver;
        commonActions = new CommonActions(driver);
        PageFactory.initElements(driver, this);

    }

    @FindBy(xpath = "//input[@placeholder='Username']")
    WebElement UsernameField;

    @FindBy(xpath = "//input[@placeholder='Password']")
    WebElement PasswordField;

    @FindBy(xpath = "//button[normalize-space()='Login']")
    WebElement LoginButton;

    @FindBy(xpath = "//h6[normalize-space()='Dashboard']")
    WebElement DashboardLabel;

    @FindBy(xpath = "//p[@class='oxd-text oxd-text--p oxd-alert-content-text']")
    WebElement InvalidCredentialsError;

    @FindBy(xpath = "//div[@class='orangehrm-login-slot-wrapper']//div[1]//div[1]//span[1]")
    WebElement RequiredLabel;

    @FindBy(xpath = "//p[@class='oxd-text oxd-text--p orangehrm-login-forgot-header']")
    WebElement ForgotPasswordLink;

    @FindBy(xpath = "//h6[normalize-space()='Reset Password']")
    WebElement ResetPasswordLabel;

    @FindBy(xpath = "//span[@class='oxd-userdropdown-tab']")
    WebElement ActionDropDown;

    @FindBy(xpath = "//a[normalize-space()='Logout']")
    WebElement LogoutButton;

    public void loginToApplication(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickOnLoginButton();
    }

    public void enterUsername(String username) {
        commonActions.isElementPresent(UsernameField);
        commonActions.enterData(UsernameField, username);
        LoggerHelper.info("Entered Username : " + username);
    }

    public void enterPassword(String password) {
        commonActions.isElementPresent(PasswordField);
        commonActions.enterData(PasswordField, password);
        LoggerHelper.info("Entered password in the password field");
    }

    public void clickOnLoginButton() {
        commonActions.isElementPresent(LoginButton);
        commonActions.click(LoginButton);
        commonActions.waitFor(10000);
        LoggerHelper.info("Clicked on the login button");
    }

    public void clickOnForgotPasswordLink() {
        commonActions.isElementPresent(ForgotPasswordLink);
        commonActions.click(ForgotPasswordLink);
        commonActions.waitFor(2000);
        LoggerHelper.info("Clicked on the forgot password link");
    }

    public void logoutFromApplication(String username, String password) {
        loginToApplication(username, password);
        commonActions.isElementPresent(ActionDropDown);
        commonActions.click(ActionDropDown);
        LoggerHelper.info("Clicked on the actions drop down");

        commonActions.isElementPresent(LogoutButton);
        commonActions.click(LogoutButton);
        LoggerHelper.info("Clicked on the logout button");
    }

    public boolean invalidLoginFlag() {
        return InvalidCredentialsError.getText().equalsIgnoreCase("Invalid credentials");
    }

    public boolean emptyLoginFlag() {
        return (RequiredLabel.isDisplayed());
    }

    public boolean loginFlag() {
        return (DashboardLabel.isDisplayed());
    }

    public boolean forgotPasswordFlag() {
        return ResetPasswordLabel.isDisplayed();
    }

    public boolean loginPageFlag() {
        return LoginButton.isDisplayed();
    }

}
