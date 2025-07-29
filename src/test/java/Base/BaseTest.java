package Base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import pageobjects.HomePage;
import pageobjects.LoginPage;
import pageobjects.MyAccountPage;
import pageobjects.RegisterPage;

public class BaseTest {

	//for global before and after method
	public static String reportTimestamp;  // ✅ Add this

	public WebDriver driver = null;
	protected Properties prop = new Properties();
	protected HomePage homePage;
	protected LoginPage loginPage;
	protected MyAccountPage myAccountPage;
	//--registerPage
	protected RegisterPage registerpage;

	// Global objects for Extent Reports
	public static ExtentSparkReporter htmlReporter;
	public static ExtentReports extent;
	public static ExtentTest test;

	@BeforeSuite
	public void setupExtentReport() {



		reportTimestamp = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
		String reportPath = System.getProperty("user.dir") + "/test-output/ExtentReport_" + reportTimestamp + ".html";

		htmlReporter = new ExtentSparkReporter(reportPath);
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);

		extent = new ExtentReports();
		//		ExtentManager.setExtentReports(extent);

		// Setting the time stamp for the report name to make it unique
		String timestamp = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
		String reportPath1 = System.getProperty("user.dir") + "/test-output/ExtentReport_" + timestamp + ".html";

		// Initialize ExtentSparkReporter
		htmlReporter = new ExtentSparkReporter(reportPath1);
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);

		// Adding system information to the report
		extent.setSystemInfo("Host Name", "Vivek Test System");
		extent.setSystemInfo("Environment", "QA");
		extent.setSystemInfo("User", "Vivek Dhavale");

		// Customizing the report appearance
		htmlReporter.config().setDocumentTitle("Automation Test Report");
		htmlReporter.config().setReportName("Automation Suite Execution Report");
		htmlReporter.config().setTheme(Theme.DARK);
		htmlReporter.config().setTimeStampFormat("MMMM dd, yyyy HH:mm:ss");
	}


	public void setupBrowserOnly() {


		File propFile = new File("src/test/java/properties/projectData.properties");
		try (FileInputStream fis = new FileInputStream(propFile)) {
			prop.load(fis);
		} catch (IOException e) {
			e.printStackTrace();
		}

		String browserName = prop.getProperty("browser");

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

		homePage = new HomePage(driver);  // only initialize homepage
	}

	@AfterMethod
	public void tearDown(ITestResult result) {

		if (driver != null) {
			driver.quit();
		}
	}

	@AfterSuite
	public void tearDown() {

		if (driver != null) {
			driver.quit();
		}
		// Write everything to the report file
		extent.flush();

		// Wait for a short time to allow file writing to complete
		try {
			Thread.sleep(3000); // 3 seconds delay
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// Build the expected report path using the same time stamp
		String reportPath = System.getProperty("user.dir") + "/test-output/ExtentReport_" + reportTimestamp + ".html";
		File reportFile = new File(reportPath);

		if (reportFile.exists()) {
			try {
				java.awt.Desktop.getDesktop().browse(reportFile.toURI());
				System.out.println("report is genrated in browser");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.err.println("⚠️ Report file not found to open: " + reportPath);
		}
	}

}
