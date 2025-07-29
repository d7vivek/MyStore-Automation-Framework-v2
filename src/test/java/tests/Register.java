package tests;

import java.util.Date;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import Base.BaseTest;
import Reports.ExtentManager;
import pageobjects.AccountSuccessPage;
import pageobjects.RegisterPage;

public class Register extends BaseTest {  // Extend from BaseTest to inherit ExtentReports

    RegisterPage registerpage = null ;
    AccountSuccessPage accountsuccesspage = null;
    
        
    @BeforeMethod
    public void setupRegister() {
        super.setupBrowserOnly();  // initialize browser and homepage

        // Now go to Register Page
        registerpage = homePage.clickOnMyAccount().slectRegisterOption();
    }
    
    @AfterMethod
    public void tearDownCustom(ITestResult result) {
        super.tearDown(result);  // call BaseTest's method to ensure driver.quit() works

        // Optional: custom logs
        test.log(Status.INFO, "Register test teardown completed.");
    }

    @Test(priority = 1)
    public void registerWithAllfield() {
        test = extent.createTest("Register with All Fields Test");  // New test in report
        ExtentManager.setTest(test); 
        
        accountsuccesspage = registerpage.enterFirstNameToRegister(prop.getProperty("firstname"))
                .enterLastNametoRegister(prop.getProperty("lastname"))
                .enterEmailToRegister(genrateNewEmail())
                .enterTeliphone(prop.getProperty("teliphone"))
                .enterPassword(prop.getProperty("password"))
                .enterCnfPassword(prop.getProperty("password"))
                .clickOnYesNewsLetter()
                .CheckOnPrivacyPolicy()
                .clickOnSubmitButton();  // Log details of the registration process

        // Validate the expected outcome
        String expectedPageHeading = "Your Account Has Been Created!";
        String actualPageHeading = accountsuccesspage.retriveAccountSucessPageHeading();
        Assert.assertEquals(expectedPageHeading, actualPageHeading);

        // Log the test result
        test.log(Status.PASS, "Register with all fields completed successfully.");
    }

    @Test(priority = 2)
    public void registerWithMandatoryField() {
        test = extent.createTest("Register with Mandatory Fields Test");
        ExtentManager.setTest(test); 
        
        accountsuccesspage = registerpage.enterFirstNameToRegister(prop.getProperty("firstname"))
                .enterLastNametoRegister(prop.getProperty("lastname"))
                .enterEmailToRegister(genrateNewEmail())
                .enterTeliphone(prop.getProperty("teliphone"))
                .enterPassword(prop.getProperty("password"))
                .enterCnfPassword(prop.getProperty("password"))
                .clickOnYesNewsLetter()
                .CheckOnPrivacyPolicy().clickOnSubmitButton();

        String expectedPageHeading = "Your Account Has Been Created!";
        String actualPageHeading = accountsuccesspage.retriveAccountSucessPageHeading();
        Assert.assertEquals(actualPageHeading, expectedPageHeading);

        // Log the result in the report
        test.log(Status.PASS, "Register with mandatory fields completed successfully.");
    }

    @Test(priority = 3)
    public void registerWithoutEnteringAnyDetails() {
        test = extent.createTest("Register without Entering Any Details Test");
        ExtentManager.setTest(test); 
        
        registerpage.clickOnSubmitButton();

        String expectedError = "First Name must be between 1 and 32 characters!";
        String actualFirstNameError = registerpage.retrivalfirstNameError();
        Assert.assertEquals(actualFirstNameError, expectedError);

        String expectedLastNameError = "Last Name must be between 1 and 32 characters!";
        String actualLastNameError = registerpage.retriveLastNameError();
        Assert.assertEquals(actualLastNameError, expectedLastNameError);

        String expectedEmailError = "E-Mail Address does not appear to be valid!";
        String actualEmailError = registerpage.retriveEmailError();
        Assert.assertEquals(actualEmailError, expectedEmailError);

        String expectedTeliphoneError = "Telephone must be between 3 and 32 characters!";
        String actualTeliphoneError = registerpage.retriveTeliphoneError();
        Assert.assertEquals(actualTeliphoneError, expectedTeliphoneError);

        String expectedPasswordError = "Password must be between 4 and 20 characters!";
        String actualPasswordError = registerpage.retrivePasswordError();
        Assert.assertEquals(actualPasswordError, expectedPasswordError);

        String expectedPolicyError = "Warning: You must agree to the Privacy Policy!";
        String actualPolicyError = registerpage.retrivePrivacyPolicyError();
        Assert.assertEquals(actualPolicyError, expectedPolicyError);

        // Log the result in the report
        test.log(Status.PASS, "Validation of all mandatory fields passed successfully.");
    }

    public String genrateNewEmail() {
        Date date = new Date();
        return "ram" + date.toString().replaceAll(" ", "_").replace(":", "_") + "@gmail.com";
    }
}
