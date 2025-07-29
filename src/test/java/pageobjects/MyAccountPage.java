package pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utils.WaitHelper;

public class MyAccountPage {
	
	WebDriver driver;
	public MyAccountPage(WebDriver driver) {
		this.driver = driver;
		 new WaitHelper(driver, 15);
		PageFactory.initElements(driver, this);
	}
	@FindBy(linkText = "Edit your account information")
	private WebElement editYourAccountInformation;

	public boolean  LoggedInStatus() {
		 return editYourAccountInformation.isDisplayed();
	}
}
