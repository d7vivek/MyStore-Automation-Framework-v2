package tests;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import Base.BaseTest;
import Reports.ExtentManager;

public class Login extends BaseTest { // Inherit from BaseTest


	private static final String WARNING_MESSAGE = "Warning: No match for E-Mail Address and/or Password.";

	@BeforeMethod
	public void setupLogin() {
		super.setupBrowserOnly();

		loginPage = homePage.clickOnMyAccount().selectLoginOption();
	}

	@AfterMethod
	public void tearDownCustom(ITestResult result) {
		super.tearDown(result);  // call BaseTest's method to ensure driver.quit() works

		// Optional: custom logs
		test.log(Status.INFO, "Login test teardown completed.");
	}

	@Test(priority = 1)
	public void loginWithValidCredentials() {

		test = extent.createTest("loginWithValidCredentials");
		ExtentManager.setTest(test);

		test = extent.createTest("Login with Valid Credentials"); // Create test in report
		myAccountPage = loginPage.enterLoginEmailAddress(prop.getProperty("email"))
				.enterLoginPassword(prop.getProperty("password"))
				.clickOnLoginButton();
		Assert.assertTrue(myAccountPage.LoggedInStatus());
	}

	@Test(priority = 2)
	public void loginWithInvalidUsername() {

		test = extent.createTest("loginWithInvalidUsername");
		ExtentManager.setTest(test);


		test = extent.createTest("Login with Invalid Username");
		loginPage.enterLoginEmailAddress(prop.getProperty("invalidemail"))
		.enterLoginPassword(prop.getProperty("invalidpassword"))
		.clickOnLoginButton();
		String actualWarning = loginPage.retriveWarningMessage();
		Assert.assertEquals(actualWarning, WARNING_MESSAGE, "There is a problem with the warning message");
	}

	@Test(priority = 3)
	public void loginWithInvalidPassword() {

		test = extent.createTest("loginWithInvalidPassword");
		ExtentManager.setTest(test); 

		loginPage.enterLoginEmailAddress(prop.getProperty("email"))
		.enterLoginPassword(prop.getProperty("invalidpassword"))
		.clickOnLoginButton();
		String actualWarning = loginPage.retriveWarningMessage();
		Assert.assertEquals(actualWarning, WARNING_MESSAGE, "There is a problem with the warning message");
	}

	@Test(priority = 4)
	public void loginWithInvalidUsernamePassword() {


		test = extent.createTest("Login with Invalid Username and Password");
		ExtentManager.setTest(test); 

		loginPage.enterLoginEmailAddress(prop.getProperty("invalidemail"))
		.enterLoginPassword(prop.getProperty("invalidpassword"))
		.clickOnLoginButton();
		String actualWarning = loginPage.retriveWarningMessage();
		Assert.assertEquals(actualWarning, WARNING_MESSAGE, "There is a problem with the warning message");
	}

	@Test(priority = 5)
	public void loginWithBlankUsernamePassword() {
		test = extent.createTest("Login with Blank Username and Password");
		ExtentManager.setTest(test); 

		loginPage.clickOnLoginButton();
		String actualWarning = loginPage.retriveWarningMessage();
		Assert.assertEquals(actualWarning, WARNING_MESSAGE, "There is a problem with the warning message");
	}
}
