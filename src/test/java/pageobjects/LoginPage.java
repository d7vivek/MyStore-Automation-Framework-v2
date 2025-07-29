package pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.WaitHelper;
public class LoginPage {

	WebDriver driver;
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		 new WaitHelper(driver, 15);
		PageFactory.initElements(driver, this);
	}
	@FindBy(id = "input-email")
	private WebElement emailField ;
	@FindBy(id = "input-password")
	private WebElement passwordField ;
	@FindBy(xpath = "//input[@value='Login']")
	private WebElement loginButton ;
	@FindBy(xpath = "//div[@class='alert alert-danger alert-dismissible']")
	private WebElement unsuccessfullLoginWarning ;

	public LoginPage enterLoginEmailAddress(String emailText) {
		emailField.sendKeys(emailText);
		return new LoginPage(driver);
	}

	public LoginPage enterLoginPassword(String passwordText) {
		passwordField.sendKeys(passwordText);
		return new LoginPage(driver);
	}

	public MyAccountPage clickOnLoginButton() {
		loginButton.click();
		return new MyAccountPage(driver);
	}

	public String retriveWarningMessage() {
		return unsuccessfullLoginWarning.getText();
	}
}
