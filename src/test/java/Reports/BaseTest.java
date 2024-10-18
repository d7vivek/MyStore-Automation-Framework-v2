package Reports;

//package base;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class BaseTest {

    // Global objects for Extent Reports
    public static ExtentSparkReporter htmlReporter;
    public static ExtentReports extent;
    public static ExtentTest test;

    @BeforeSuite
    public void setupExtentReport() {
        // Setting the timestamp for the report name to make it unique
        String timestamp = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
        String reportPath = System.getProperty("user.dir") + "/test-output/ExtentReport_" + timestamp + ".html";

        // Initialize ExtentSparkReporter
        htmlReporter = new ExtentSparkReporter(reportPath);
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

    @AfterMethod
    public void getResult(ITestResult result) throws IOException {
        // Capture the test result and log it in the Extent Report
        if (result.getStatus() == ITestResult.FAILURE) {
            test.log(Status.FAIL, MarkupHelper.createLabel(result.getName() + " - Test Case Failed", ExtentColor.RED));
            test.fail(result.getThrowable());
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            test.log(Status.PASS, MarkupHelper.createLabel(result.getName() + " - Test Case Passed", ExtentColor.GREEN));
        } else if (result.getStatus() == ITestResult.SKIP) {
            test.log(Status.SKIP, MarkupHelper.createLabel(result.getName() + " - Test Case Skipped", ExtentColor.ORANGE));
            test.skip(result.getThrowable());
        }
    }

    @AfterSuite
    public void tearDown() {
        // Write everything to the report file
        extent.flush();
    }
}
