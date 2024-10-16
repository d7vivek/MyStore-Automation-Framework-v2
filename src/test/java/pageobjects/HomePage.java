package pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {


	WebDriver driver;


	public HomePage(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver,this);	//in every page object page this statement is important 
	}

	@FindBy(xpath="//span[text()='My Account']")
	private WebElement myAccountDropMenu;
	@FindBy(xpath="//a[text()='Login']")
	private WebElement loginOption;
	@FindBy(linkText = "Register")
	private WebElement registerOption;
	@FindBy(xpath = "//input[@placeholder='Search']")
	private WebElement searchBoxField;
	@FindBy(xpath = "//i[@class='fa fa-search']")
	private WebElement searchButton;

	public HomePage clickOnMyAccount() {
		myAccountDropMenu.click();
		return new HomePage(driver);
	}

	public LoginPage selectLoginOption() {
		loginOption.click();
		return new LoginPage(driver);
	}

	public RegisterPage slectRegisterOption() {
		registerOption.click();
		return new RegisterPage(driver);

	}

	public HomePage enterIntoSearchField(String productText) {
		searchBoxField.sendKeys(productText);
		return new HomePage(driver);
	}

	public SearchPage clickOnSearchButton() {
		searchButton.click();
		return new SearchPage(driver);
	}
}
