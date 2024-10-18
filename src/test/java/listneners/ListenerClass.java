package listneners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ListenerClass implements ITestListener{
	
	
	
	public void configureReport() {
		
				
		
	}

	@Override
	public void onStart(ITestContext context) {

		System.out.println("on start method invoke"+context.getName());
	}

	@Override
	public void onTestStart(ITestResult result) {

		System.out.println("Test Started "+result.getMethod());

	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onTestFailedWithTimeout(ITestResult result) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onFinish(ITestContext context) {

		System.out.println("on finish method invoke "+context.getName());

	}
	@Override
	public void onTestFailure(ITestResult result) {

		System.out.println("name of test method failed"+ result.getName());
	}

	@Override
	public void onTestSuccess(ITestResult result) {

		System.out.println("name of test method successfull"+result.getMethod());

	}


	@Override
	public void onTestSkipped(ITestResult result) {

		System.out.println("name of test method skipped"+ result.getMethod());

	}

}
