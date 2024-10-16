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
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pageobjects.HomePage;
import pageobjects.LoginPage;
import pageobjects.MyAccountPage;

public class Login {

	WebDriver driver = null; // Corrected the type of driver
	Properties prop = new Properties();
	HomePage homePage;
	LoginPage loginPage; 
	MyAccountPage myAccountPage;

	@BeforeMethod
	public void setup() {
		// Load properties file
		File propFile = new File("C:\\selenium-workspace\\practice-workspace\\javapractice\\pomPfProject\\src\\test\\java\\properties\\projectData.properties");
		try (FileInputStream fis = new FileInputStream(propFile)) {
			prop.load(fis);
		} catch (IOException e) {
			e.printStackTrace();
		}

		String browserName = prop.getProperty("browser");

		if (browserName.equalsIgnoreCase("Chrome")) {
			driver = new ChromeDriver();
		} else if (browserName.equalsIgnoreCase("Edge")) {
			driver = new EdgeDriver();
		}

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.get(prop.getProperty("url"));

		homePage = new HomePage(driver);
		loginPage = homePage.clickOnMyAccount().selectLoginOption(); // Initialize loginPage // this is also a method chaining mechanism 
	}

	@AfterMethod
	public void tearDown() {
		if (driver != null) {
			driver.quit(); // Changed to quit() to close all browser windows and end the WebDriver session
		}
	}

	@Test(priority = 1)
	public void loginWithValidCredentials() {
		
		myAccountPage = loginPage.enterLoginEmailAddress(prop.getProperty("email"))
				.enterLoginPassword(prop.getProperty("password"))
				.clickOnLoginButton();  // Initialize myAccountPage
		Assert.assertTrue(myAccountPage.LoggedInStatus());

	}

	@Test(priority = 2)
	public void loginWithInvalidUsername() {
		loginPage.enterLoginEmailAddress(prop.getProperty("invalidemail"))
		.enterLoginPassword(prop.getProperty("invalidpassword"))
		.clickOnLoginButton();
		//above we using method chaining mechanism
		String expectedWarning = "Warning: No match for E-Mail Address and/or Password.";
//		String expectedWarning = "Warning: Your account has exceeded allowed number of login attempts. Please try again in 1 hour.";
		String actualWarning = loginPage.retriveWarningMessage(); // Corrected method name
		Assert.assertEquals(actualWarning, expectedWarning, "There is a problem with the warning message");
	}

	@Test(priority = 3)
	public void loginWithInvalidPassword() {
		loginPage.enterLoginEmailAddress(prop.getProperty("email"))
				 .enterLoginPassword( prop.getProperty("invalidpassword"))
				 .clickOnLoginButton();
		// you can write this in a single line also , for readability is written in this format
		String expectedWarning = "Warning: No match for E-Mail Address and/or Password.";
		String actualWarning = loginPage.retriveWarningMessage(); // Corrected method name
		Assert.assertEquals(actualWarning, expectedWarning, "There is a problem with the warning message");
	}

	@Test(priority = 4)
	public void loginWithInvalidUsernamePassword() {
		loginPage.enterLoginEmailAddress(prop.getProperty("invalidemail"))
				 .enterLoginPassword(prop.getProperty("invalidpassword"))
				 .clickOnLoginButton();
		// you can write this in a single line also , for readability is written in this format
		String expectedWarning = "Warning: No match for E-Mail Address and/or Password.";
		String actualWarning = loginPage.retriveWarningMessage(); // Corrected method name
		Assert.assertEquals(actualWarning, expectedWarning, "There is a problem with the warning message");
	}

	@Test(priority = 5)
	public void loginWithBlankUsernamePassword() {
		loginPage.clickOnLoginButton();
		String expectedWarning = "Warning: No match for E-Mail Address and/or Password.";
		String actualWarning = loginPage.retriveWarningMessage(); // Corrected method name
		Assert.assertEquals(actualWarning, expectedWarning, "There is a problem with the warning message");
	}
}
