package fatura_test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import activity_classes.FaturaActivityPO;
import activity_classes.LoginActivityPO;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;

/*
 * This class is used for setup and tear down test cases using @Before, @After and utility methods
 * 
 * @author Mahmoud Bindary
 */

public class BaseTestClass {

	AndroidDriver<MobileElement> driver;
	DesiredCapabilities caps;
	URL url;
	WebDriverWait wait;
	LoginActivityPO loginActivity;
	FaturaActivityPO faturaActivity;
	ExtentReports report;
	ExtentTest test;

	// Change these values to apply another device
	final String DEVICE_NAME = "Real Device";
	final String UDID_REAL_DEVICE = "GYON7D7D69V8FEKR";
//	final String UDID_REAL_DEVICE = "192.168.1.2:5555";
	final String UDID_EMULATOR = "192.168.109.101:5555";
	final String PLATFORM_NAME = "Android";
	final String PLATFORM_VERSION = "10";

	final String AUTOMATION_NAME = "UiAutomator2";
	final String APP_PACKAGE = "com.faturaegypt.app.stage";
	final String APP_ACTIVITY = "com.faturaegypt.app.ui.MainActivity";

	final String validPhoneNumber = "01017319700";
	final String validPassword = "123321";

	final String invalidPhoneNumber = "1017319700";
	final String invalidPassword = "23321";

	final String emptyPhoneNumber = "";
	final String emptyPassword = "";

	@BeforeClass
	public void setUp() {
		try {
			setDesiredCaps();
			url = new URL("http://127.0.0.1:4723/wd/hub");
			driver = new AndroidDriver<MobileElement>(url, caps);
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			wait = new WebDriverWait(driver, 10);
			// Generate report in project's main directory
			report = new ExtentReports(System.getProperty("user.dir") + "//TestReport.html");

			loginActivity = new LoginActivityPO(driver);
			loginActivity.clickShowPassword();

		} catch (Exception e) {
			System.out.println("Cause is: " + e.getCause());
			System.out.println("Message is: " + e.getMessage());
			e.printStackTrace();
		}
	}

	@BeforeMethod
	public void beforeEachTest(Method method) {
		test = report.startTest(method.getName());
		test.log(LogStatus.INFO, "Test Case Started");
	}

	@AfterMethod
	public void afterEachTest(ITestResult iTestResult) {
		if (iTestResult.getStatus() == ITestResult.SUCCESS) {
			test.log(LogStatus.PASS, "Test Case Passed");
		} else if (iTestResult.getStatus() == ITestResult.SKIP) {
			test.log(LogStatus.SKIP, "Test Case Skipped");
		} else if (iTestResult.getStatus() == ITestResult.FAILURE) {
			String path = takeScreenshot(iTestResult.getName());
			String imagePath = test.addScreenCapture(path);
			test.log(LogStatus.FAIL, "Test Case Failed", imagePath);
		}
	}

	@AfterClass
	public void tearDown() {
		driver.quit();
		report.endTest(test);
		report.flush();
	}

	public void setDesiredCaps() {
		caps = new DesiredCapabilities();
		caps.setCapability(MobileCapabilityType.DEVICE_NAME, DEVICE_NAME);
		caps.setCapability(MobileCapabilityType.UDID, UDID_REAL_DEVICE);
		caps.setCapability(MobileCapabilityType.PLATFORM_NAME, PLATFORM_NAME);
		caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, PLATFORM_VERSION);
		caps.setCapability("automationName ", AUTOMATION_NAME);
		caps.setCapability("appPackage", APP_PACKAGE);
		caps.setCapability("appActivity", APP_ACTIVITY);
	}

	public String takeScreenshot(String fileName) {
		fileName = fileName + ".png";
		File sourceFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(sourceFile, new File(System.getProperty("user.dir") + "//Screenshots//" + fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		String destination = System.getProperty("user.dir") + "//Screenshots//" + fileName;
		return destination;
	}

	@DataProvider(name = "invalid-phone-number-dp")
	public Object[][] invalidPhoneNumber() {
		return new Object[][] { { emptyPhoneNumber, emptyPassword }, { emptyPhoneNumber, validPassword },
				{ invalidPhoneNumber, invalidPassword }, { invalidPhoneNumber, validPassword } };
	}

	@DataProvider(name = "invalid-password-dp")
	public Object[][] invalidPassword() {
		return new Object[][] { { validPhoneNumber, emptyPassword }, { validPhoneNumber, invalidPassword } };
	}

}
