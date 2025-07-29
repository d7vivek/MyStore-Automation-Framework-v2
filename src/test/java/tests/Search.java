package tests;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import Base.BaseTest;
import Reports.ExtentManager;
import pageobjects.HomePage;
import pageobjects.SearchPage;

public class Search extends BaseTest {

    SearchPage searchPage = null;

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
        test.log(Status.INFO, "Search test teardown completed.");
    }
    @Test(priority = 1)
    public void searchForValidProduct() {
        test = extent.createTest("Search for Valid Product");  // Log the test in Extent Report
        ExtentManager.setTest(test); 
        
        homePage = new HomePage(driver);
        searchPage = homePage.enterIntoSearchField(prop.getProperty("validproduct")).clickOnSearchButton();

        boolean isProductDisplayed = searchPage.displaystatusofValidProduct();
        Assert.assertTrue(isProductDisplayed, "Product status is not displayed as expected.");
    }

    @Test(priority = 2)
    public void searchWithInvalidProduct() {
        test = extent.createTest("Search with Invalid Product");  // Log the test in Extent Report
        ExtentManager.setTest(test); 
        homePage = new HomePage(driver);
        searchPage = homePage.enterIntoSearchField(prop.getProperty("invalidproduct")).clickOnSearchButton();

        String expectedNoProductMessage = "There is no product that matches the search criteria.";
        String actualNoProductMessage = searchPage.retriveNoProductmessage();

        Assert.assertEquals(actualNoProductMessage, expectedNoProductMessage, "No product message is not as expected.");
    }

    @Test(priority = 3)
    public void searchWithNoProduct() {
        test = extent.createTest("Search with No Product");  // Log the test in Extent Report
        ExtentManager.setTest(test); 
        homePage = new HomePage(driver);
        searchPage = homePage.clickOnSearchButton();

        String expectedNoProductMessage = "There is no product that matches the search criteria.***";	//i have added three stars intentionally for test fail 
        String actualNoProductMessage = searchPage.retriveNoProductmessage();

        Assert.assertEquals(actualNoProductMessage, expectedNoProductMessage, "No product message is not as expected.");
    }
}
