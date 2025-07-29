package listneners;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.ExtentTest;

import Base.BaseTest;
import Reports.ExtentManager;

public class ListenerClass implements ITestListener {

    @Override
    public void onStart(ITestContext context) {
        System.out.println("on start method invoke " + context.getName());
    }

    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("Test Started " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("Test passed: " + result.getMethod().getMethodName());

        ExtentTest test = ExtentManager.getTest();
        if (test != null) {
            test.log(Status.PASS, "Test passed: " + result.getMethod().getMethodName());
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("Test Failed: " + result.getMethod().getMethodName());

        Object currentClass = result.getInstance();
        WebDriver driver = ((BaseTest) currentClass).driver;

        ExtentTest test = ExtentManager.getTest();

        if (test != null) {
            test.fail("Test Failed: " + result.getThrowable());

            try {
                String screenshotPath = takeScreenshot(result.getMethod().getMethodName(), driver);
                test.fail("Screenshot of failure",
                        MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("ExtentTest object is null in listener for method: " + result.getName());
        }
    }

    private String takeScreenshot(String testName, WebDriver driver) throws IOException {
    	
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String screenshotDir = System.getProperty("user.dir") + "/screenshots/";
        File dir = new File(screenshotDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String screenshotPath = screenshotDir + testName + "_" + timestamp + ".png";
        File destFile = new File(screenshotPath);
        FileUtils.copyFile(srcFile, destFile);
        return screenshotPath;
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("Test Skipped: " + result.getMethod().getMethodName());

        ExtentTest test = ExtentManager.getTest();
        if (test != null) {
            test.skip("Test Skipped: " + result.getThrowable());
        }
    }

    @Override
    public void onFinish(ITestContext context) {
        System.out.println("on finish method invoke " + context.getName());
    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
        onTestFailure(result); // Treat as failure
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    }
}
