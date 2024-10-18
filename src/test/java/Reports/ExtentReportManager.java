package Reports;


import org.testng.annotations.AfterSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportManager {
    private static ExtentReports extent;

    public static ExtentReports getInstance() {
        if (extent == null) {
            String reportPath = "C:\\selenium-workspace\\practice-workspace\\javapractice\\PFProj\\test-output\\ExtentReportfinal.html"; // Path for a single report
            ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
            spark.config().setDocumentTitle("Extent Report");
            spark.config().setReportName("Test Automation Report");
            
            extent = new ExtentReports();
            extent.attachReporter(spark);
        }
        return extent;
    }
    @AfterSuite
    public static void flush() {
        extent.flush();
    }
}
