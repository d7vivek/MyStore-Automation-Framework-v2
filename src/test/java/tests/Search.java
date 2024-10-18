package tests;

import java.io.FileInputStream;
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

import pageobjects.HomePage;
import pageobjects.SearchPage;
import Reports.BaseTest;

public class Search extends BaseTest {

    WebDriver driver = null;
    Properties prop = null;
    HomePage homePage = null;
    SearchPage searchPage = null;

    @BeforeMethod
    public void setup() {
        // Initialize Properties
        prop = new Properties();
        try {
            prop.load(new FileInputStream("C:\\selenium-workspace\\practice-workspace\\javapractice\\pomPfProject\\src\\test\\java\\properties\\projectData.properties"));
        } catch (Exception e) {
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
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        // Log test results in Extent Reports
        if (test != null) {
            if (result.getStatus() == ITestResult.FAILURE) {
                test.fail("Test failed: " + result.getName());
                test.fail("Failure reason: " + result.getThrowable());
            } else if (result.getStatus() == ITestResult.SUCCESS) {
                test.pass("Test passed: " + result.getName());
            } else if (result.getStatus() == ITestResult.SKIP) {
                test.skip("Test skipped: " + result.getName());
            }
        }

        // Close the browser after the test
        if (driver != null) {
            driver.quit();
        }
    }

    @Test(priority = 1)
    public void searchForValidProduct() {
        test = extent.createTest("Search for Valid Product");  // Log the test in Extent Report

        homePage = new HomePage(driver);
        searchPage = homePage.enterIntoSearchField(prop.getProperty("validproduct")).clickOnSearchButton();

        boolean isProductDisplayed = searchPage.displaystatusofValidProduct();
        Assert.assertTrue(isProductDisplayed, "Product status is not displayed as expected.");
    }

    @Test(priority = 2)
    public void searchWithInvalidProduct() {
        test = extent.createTest("Search with Invalid Product");  // Log the test in Extent Report

        homePage = new HomePage(driver);
        searchPage = homePage.enterIntoSearchField(prop.getProperty("invalidproduct")).clickOnSearchButton();

        String expectedNoProductMessage = "There is no product that matches the search criteria.";
        String actualNoProductMessage = searchPage.retriveNoProductmessage();

        Assert.assertEquals(actualNoProductMessage, expectedNoProductMessage, "No product message is not as expected.");
    }

    @Test(priority = 3)
    public void searchWithNoProduct() {
        test = extent.createTest("Search with No Product");  // Log the test in Extent Report

        homePage = new HomePage(driver);
        searchPage = homePage.clickOnSearchButton();

        String expectedNoProductMessage = "There is no product that matches the search criteria.";
        String actualNoProductMessage = searchPage.retriveNoProductmessage();

        Assert.assertEquals(actualNoProductMessage, expectedNoProductMessage, "No product message is not as expected.");
    }
}
