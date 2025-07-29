package tests;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import Base.BaseTest;
import Reports.ExtentManager;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.By;
import java.time.Duration;

public class Login extends BaseTest {

    private static final String WARNING_MESSAGE = "Warning: No match for E-Mail Address and/or Password.";

    @BeforeMethod
    public void setupLogin() {
        super.setupBrowserOnly();
        loginPage = homePage.clickOnMyAccount().selectLoginOption();

        // Wait until the login page email field is visible
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("input-email")));
    }

    @AfterMethod
    public void tearDownCustom(ITestResult result) {
        super.tearDown(result);
        test.log(Status.INFO, "Login test teardown completed.");
    }

    @Test(priority = 1)
	public void loginWithValidCredentials() {		// keep this method without wait implemented in it 

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
        test = extent.createTest("Login with Invalid Username");
        ExtentManager.setTest(test);

        loginPage.enterLoginEmailAddress(prop.getProperty("invalidemail"))
                .enterLoginPassword(prop.getProperty("invalidpassword"))
                .clickOnLoginButton();

        // Wait for warning message
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".alert.alert-danger")));

        String actualWarning = loginPage.retriveWarningMessage();
        Assert.assertEquals(actualWarning, WARNING_MESSAGE, "Mismatch in warning message for invalid username.");
    }

    @Test(priority = 3)
    public void loginWithInvalidPassword() {
        test = extent.createTest("Login with Invalid Password");
        ExtentManager.setTest(test);

        loginPage.enterLoginEmailAddress(prop.getProperty("email"))
                .enterLoginPassword(prop.getProperty("invalidpassword"))
                .clickOnLoginButton();

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".alert.alert-danger")));

        String actualWarning = loginPage.retriveWarningMessage();
        Assert.assertEquals(actualWarning, WARNING_MESSAGE, "Mismatch in warning message for invalid password.");
    }

    @Test(priority = 4)
    public void loginWithInvalidUsernamePassword() {
        test = extent.createTest("Login with Invalid Username and Password");
        ExtentManager.setTest(test);

        loginPage.enterLoginEmailAddress(prop.getProperty("invalidemail"))
                .enterLoginPassword(prop.getProperty("invalidpassword"))
                .clickOnLoginButton();

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".alert.alert-danger")));

        String actualWarning = loginPage.retriveWarningMessage();
        Assert.assertEquals(actualWarning, WARNING_MESSAGE, "Mismatch in warning message for invalid credentials.");
    }

    @Test(priority = 5)
    public void loginWithBlankUsernamePassword() {
        test = extent.createTest("Login with Blank Username and Password");
        ExtentManager.setTest(test);

        loginPage.clickOnLoginButton();

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".alert.alert-danger")));

        String actualWarning = loginPage.retriveWarningMessage();
        Assert.assertEquals(actualWarning, WARNING_MESSAGE, "Mismatch in warning message for blank credentials.");
    }
}
