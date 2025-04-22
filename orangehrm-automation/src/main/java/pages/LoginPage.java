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


    // Public Actions
    public void loginToApplication(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
    }

    public void clickForgotPassword() {
        clickElement(ForgotPasswordLink, "Forgot Password link");
        commonActions.waitFor(2000);
    }

    public void logout(String username, String password) {
        loginToApplication(username, password);
        clickElement(ActionDropDown, "Actions dropdown");
        clickElement(LogoutButton, "Logout button");
    }

    // Verification Methods
    public boolean isDashboardDisplayed() {
        return isElementDisplayed(DashboardLabel);
    }

    public boolean isInvalidLoginMessageShown() {
        return textEquals(InvalidCredentialsError, "Invalid credentials");
    }

    public boolean isRequiredLabelShown() {
        return isElementDisplayed(RequiredLabel);
    }

    public boolean isResetPasswordDisplayed() {
        return isElementDisplayed(ResetPasswordLabel);
    }

    public boolean isLoginPageVisible() {
        return isElementDisplayed(LoginButton);
    }

    // Private helpers
    private void enterUsername(String username) {
        enterText(UsernameField, username, "Username");
    }

    private void enterPassword(String password) {
        enterText(PasswordField, password, "Password");
    }

    private void clickLoginButton() {
        clickElement(LoginButton, "Login button");
        commonActions.waitFor(10000);
    }

    private void enterText(WebElement element, String value, String fieldName) {
        commonActions.isElementPresent(element);
        commonActions.enterData(element, value);
        LoggerHelper.info("Entered " + fieldName + " : " + value);
    }

    private void clickElement(WebElement element, String elementName) {
        commonActions.isElementPresent(element);
        commonActions.click(element);
        LoggerHelper.info("Clicked on the " + elementName);
    }

    private boolean isElementDisplayed(WebElement element) {
        commonActions.isElementPresent(element);
        return element.isDisplayed();
    }

    private boolean textEquals(WebElement element, String expectedText) {
        commonActions.isElementPresent(element);
        return element.getText().equalsIgnoreCase(expectedText);
    }

}
