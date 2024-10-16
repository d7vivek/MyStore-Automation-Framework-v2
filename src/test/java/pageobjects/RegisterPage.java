package pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RegisterPage {

	WebDriver driver;


	public RegisterPage(WebDriver driver) {

		this.driver=driver;
		PageFactory.initElements(driver, this);

	}
	@FindBy(id = "input-firstname")
    private WebElement firstNameField;

    @FindBy(id = "input-lastname")
    private WebElement lastNameField;

    @FindBy(id = "input-email")
    private WebElement emailField;

    @FindBy(id = "input-telephone")
    private WebElement telephoneField;

    @FindBy(id = "input-password")
    private WebElement passwordField;

    @FindBy(id = "input-confirm")
    private WebElement confirmPasswordField;

    @FindBy(xpath = "//input[@name='newsletter'][@value='1']")
    private WebElement yesNewsletter;

    @FindBy(name = "agree")
    private WebElement privacyPolicy;

    @FindBy(xpath = "//input[@value='Continue']")
    private WebElement submitButton;

    @FindBy(xpath = "//div[contains(text(),'First Name must be between 1 and 32 characters!')]")
    private WebElement firstNameErrorMessage;

    @FindBy(xpath = "//div[contains(text(),'Last Name must be between 1 and 32 characters!')]")
    private WebElement lastNameErrorMessage;

    @FindBy(xpath = "//div[contains(text(),'E-Mail Address does not appear to be valid!')]")
    private WebElement emailErrorMessage;

    @FindBy(xpath = "//div[contains(text(),'Telephone must be between 3 and 32 characters!')]")
    private WebElement telephoneErrorMessage;

    @FindBy(xpath = "//div[contains(text(),'Password must be between 4 and 20 characters!')]")
    private WebElement passwordErrorMessage;

    @FindBy(xpath = "//div[@class='alert alert-danger alert-dismissible']")
    private WebElement privacyPolicyErrorMessage;



	public RegisterPage enterFirstNameToRegister(String FirstName) {
		firstNameField.sendKeys(FirstName);
		return new RegisterPage(driver);
	}

	public RegisterPage enterLastNametoRegister(String LastName) {
		lastNameField.sendKeys(LastName);
		return new RegisterPage(driver);
	}

	public RegisterPage enterEmailToRegister(String email) {
		emailField.sendKeys(email);
		return new RegisterPage(driver);

	}

	public RegisterPage enterTeliphone(String teliphone) {
		telephoneField.sendKeys(teliphone);
		return new RegisterPage(driver);
	}

	public RegisterPage enterPassword(String password) {
		passwordField.sendKeys(password);
		return new RegisterPage(driver);

	}

	public RegisterPage enterCnfPassword(String cnfPassword) {
		confirmPasswordField.sendKeys(cnfPassword);
		return new RegisterPage(driver);

	}

	public RegisterPage clickOnYesNewsLetter() {
		yesNewsletter.click();
		return new RegisterPage(driver);

	}

	public RegisterPage CheckOnPrivacyPolicy() {
		privacyPolicy.click();
		return new RegisterPage(driver);

	}

	public AccountSuccessPage clickOnSubmitButton() {
		submitButton.click();
		return new AccountSuccessPage(driver);
	}

	public String retrivalfirstNameError() {

		return firstNameErrorMessage.getText();
	}

	public String retriveLastNameError() {
		return lastNameErrorMessage.getText();

	}

	public String retriveEmailError() {
		return emailErrorMessage.getText();
	}

	public String retriveTeliphoneError() {

		return telephoneErrorMessage.getText();
	}

	public String retrivePasswordError() {
		return passwordErrorMessage.getText();

	}

	public String retrivePrivacyPolicyError() {
		return privacyPolicyErrorMessage.getText();
	}

}
