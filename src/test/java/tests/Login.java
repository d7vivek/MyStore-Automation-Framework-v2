package tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import pageobjects.HomePage;
import pageobjects.LoginPage;
import pageobjects.MyAccountPage;
import Reports.BaseTest;

public class Login extends BaseTest { // Inherit from BaseTest

	WebDriver driver = null;
	Properties prop = new Properties();
	HomePage homePage;
	LoginPage loginPage;
	MyAccountPage myAccountPage;

	private static final String WARNING_MESSAGE = "Warning: No match for E-Mail Address and/or Password.";

	@BeforeMethod
	public void setup() {
		// Load properties file
		File propFile = new File("src/test/java/properties/projectData.properties");
		try (FileInputStream fis = new FileInputStream(propFile)) {
			prop.load(fis);
		} catch (IOException e) {
			e.printStackTrace();
		}

		String browserName = prop.getProperty("browser");

		// Browser initialization using switch statement
		switch (browserName.toLowerCase()) {
		case "chrome":
			driver = new ChromeDriver();
			break;
		case "edge":
			driver = new EdgeDriver();
			break;
		default:
			throw new IllegalArgumentException("Browser not supported: " + browserName);
		}

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.get(prop.getProperty("url"));

		homePage = new HomePage(driver);
		loginPage = homePage.clickOnMyAccount().selectLoginOption();
	}

	@AfterMethod
	public void tearDown(ITestResult result) {
		// Log test results in Extent Reports
		if (result.getStatus() == ITestResult.FAILURE) {
			test.log(Status.FAIL, MarkupHelper.createLabel(result.getName() + " - Test Case Failed", ExtentColor.RED));
			test.fail(result.getThrowable());
		} else if (result.getStatus() == ITestResult.SUCCESS) {
			test.log(Status.PASS, MarkupHelper.createLabel(result.getName() + " - Test Case Passed", ExtentColor.GREEN));
		} else if (result.getStatus() == ITestResult.SKIP) {
			test.log(Status.SKIP, MarkupHelper.createLabel(result.getName() + " - Test Case Skipped", ExtentColor.ORANGE));
			test.skip(result.getThrowable());
		}

		if (driver != null) {
			driver.quit();
		}
	}

	@Test(priority = 1)
	public void loginWithValidCredentials() {
		test = extent.createTest("Login with Valid Credentials"); // Create test in report
		myAccountPage = loginPage.enterLoginEmailAddress(prop.getProperty("email"))
				.enterLoginPassword(prop.getProperty("password"))
				.clickOnLoginButton();
		Assert.assertTrue(myAccountPage.LoggedInStatus());
	}

	@Test(priority = 2)
	public void loginWithInvalidUsername() {
		test = extent.createTest("Login with Invalid Username");
		loginPage.enterLoginEmailAddress(prop.getProperty("invalidemail"))
		.enterLoginPassword(prop.getProperty("invalidpassword"))
		.clickOnLoginButton();
		String actualWarning = loginPage.retriveWarningMessage();
		Assert.assertEquals(actualWarning, WARNING_MESSAGE, "There is a problem with the warning message");
	}

	@Test(priority = 3)
	public void loginWithInvalidPassword() {
		test = extent.createTest("Login with Invalid Password");
		loginPage.enterLoginEmailAddress(prop.getProperty("email"))
		.enterLoginPassword(prop.getProperty("invalidpassword"))
		.clickOnLoginButton();
		String actualWarning = loginPage.retriveWarningMessage();
		Assert.assertEquals(actualWarning, WARNING_MESSAGE, "There is a problem with the warning message");
	}

	@Test(priority = 4)
	public void loginWithInvalidUsernamePassword() {
		test = extent.createTest("Login with Invalid Username and Password");
		loginPage.enterLoginEmailAddress(prop.getProperty("invalidemail"))
		.enterLoginPassword(prop.getProperty("invalidpassword"))
		.clickOnLoginButton();
		String actualWarning = loginPage.retriveWarningMessage();
		Assert.assertEquals(actualWarning, WARNING_MESSAGE, "There is a problem with the warning message");
	}

	@Test(priority = 5)
	public void loginWithBlankUsernamePassword() {
		test = extent.createTest("Login with Blank Username and Password");
		loginPage.clickOnLoginButton();
		String actualWarning = loginPage.retriveWarningMessage();
		Assert.assertEquals(actualWarning, WARNING_MESSAGE, "There is a problem with the warning message");
	}
}
