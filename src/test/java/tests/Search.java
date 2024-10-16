package tests;

import java.io.File;
import java.io.FileInputStream;
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
import pageobjects.SearchPage;

public class Search {

    WebDriver driver = null;
    Properties prop = null;
    HomePage homePage = null;
    SearchPage searchPage = null;

    @BeforeMethod
    public void setup() {
        prop = new Properties();
        try {
            prop.load(new FileInputStream(new File("C:\\selenium-workspace\\practice-workspace\\javapractice\\pomPfProject\\src\\test\\java\\properties\\projectData.properties")));
        } catch (Exception e) {
            e.printStackTrace();
        }

        String browserName = prop.getProperty("browser");

        if (browserName.equalsIgnoreCase("Chrome")) {
            driver = new ChromeDriver();
        } else if (browserName.equals("Edge")) {
            driver = new EdgeDriver();
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get(prop.getProperty("url"));

//        homePage = new HomePage(driver);
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test(priority = 1)
    public void searchForValidProduct() {
        homePage = new HomePage(driver);
        searchPage = homePage.enterIntoSearchField(prop.getProperty("validproduct")).clickOnSearchButton();
        Assert.assertTrue(searchPage.displaystatusofValidProduct());
    }

    @Test(priority = 2)
    public void searchWithInvalidProduct() {
        homePage = new HomePage(driver);

        searchPage = homePage.enterIntoSearchField(prop.getProperty("invalidproduct")).clickOnSearchButton();
        String expectedNoProductMessage ="There is no product that matches the search criteria.";
        String actualNoProductMessage = searchPage.retriveNoProductmessage();
        Assert.assertEquals(actualNoProductMessage, expectedNoProductMessage);
    }

    @Test(priority = 3)
    public void searchWithNoProduct() {
        homePage = new HomePage(driver);

        searchPage = homePage.clickOnSearchButton();
        String expectedNoProductMessage ="There is no product that matches the search criteria.";
        String actualNoProductMessage = searchPage.retriveNoProductmessage();
        Assert.assertEquals(actualNoProductMessage, expectedNoProductMessage);
    }
}
