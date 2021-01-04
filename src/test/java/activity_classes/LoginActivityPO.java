package activity_classes;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class LoginActivityPO {

	AndroidDriver<MobileElement> driver;
	ExtentTest test;

	// Locators
	@FindBy(xpath = "(//android.widget.EditText)[1]")
	MobileElement phone_number_text_box;

	@FindBy(xpath = "(//android.widget.EditText)[2]")
	MobileElement password_text_box;

	@FindBy(id = "com.faturaegypt.app.stage:id/text_input_end_icon")
	MobileElement show_password_btn;

	@FindBy(id = "com.faturaegypt.app.stage:id/btn_signin")
	MobileElement signin_btn;

	@FindBy(xpath = "//android.widget.Toast[1]")
	MobileElement toast_message;

	// Constructor
	public LoginActivityPO(AndroidDriver<MobileElement> driver, ExtentTest test) {
		this.driver = driver;
		this.test = test;
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
		test.log(LogStatus.INFO, "Login Activity Initialized");
	}

	public LoginActivityPO(AndroidDriver<MobileElement> driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}

	// Methods
	public void setPhoneNumber(String phoneNumber) {
		phone_number_text_box.clear();
		phone_number_text_box.sendKeys(phoneNumber);
		if (phoneNumber == "") {
			phoneNumber = "Empty";
		}
		test.log(LogStatus.INFO, "Phone Number is set to: " + phoneNumber);
	}

	public void setPassword(String password) {
		password_text_box.clear();
		password_text_box.sendKeys(password);
		if (password == "") {
			password = "Empty";
		}
		test.log(LogStatus.INFO, "Password is set to: " + password);
	}

	public void clickSignInBtn() {
		signin_btn.click();
		test.log(LogStatus.INFO, "Sign in button is clicked");
	}

	public void clickShowPassword() {
		show_password_btn.click();
	}

	public String getToastText() {
		test.log(LogStatus.INFO, "Toast message is: " + toast_message.getText());
		return toast_message.getText();
	}

	public boolean isLoginFailedDueToInvalidPhoneNumber() {
		boolean loginFailedDueToPhoneInvalidNumber = getToastText().contains("هاتف");
		logCheckStatus("Check login failed due to invalid phone number: ", loginFailedDueToPhoneInvalidNumber);
		return loginFailedDueToPhoneInvalidNumber;
	}

	public boolean isLoginFailedDueToInvalidPassword() {
		boolean loginFailedDueToInvalidPassword = getToastText().contains("سري");
		logCheckStatus("Check login failed due to invalid password: ", loginFailedDueToInvalidPassword);
		return loginFailedDueToInvalidPassword;
	}

	public boolean isAppBackToLoginActivity() {
		boolean appBackToLoginActivity = signin_btn.isDisplayed();
		logCheckStatus("Check application is back to login activity: ", appBackToLoginActivity);
		return appBackToLoginActivity;
	}

	private void logCheckStatus(String description, boolean pass) {
		if (pass) {
			test.log(LogStatus.PASS, description + "PASSED");
		} else {
			test.log(LogStatus.FAIL, description + "FAILED");
		}
	}

}
