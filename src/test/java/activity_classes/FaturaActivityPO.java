package activity_classes;

import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class FaturaActivityPO {

	AndroidDriver<MobileElement> driver;
	ExtentTest test;
	WebDriverWait wait;

	// Locators
	@FindBy(id = "com.faturaegypt.app.stage:id/wholesalersLinearLayout")
	MobileElement wholesalers_layout;

	@AndroidFindBy(uiAutomator = "new UiScrollable(new UiSelector().scrollable(true))"
			+ ".scrollIntoView(new UiSelector().textContains(\"ساقعة\"))")
	MobileElement pepsi_list_btn;

	@FindBy(id = "com.faturaegypt.app.stage:id/addCash")
	MobileElement add_element_btn;

	@FindBy(id = "com.faturaegypt.app.stage:id/cash_price")
	MobileElement element_cash_price;

	@FindBy(id = "com.faturaegypt.app.stage:id/current_total")
	MobileElement current_total;

	@FindBy(id = "com.faturaegypt.app.stage:id/nav_cart")
	MobileElement cart_btn;

	@FindBy(id = "com.faturaegypt.app.stage:id/nav_profile")
	MobileElement profile_btn;

	@AndroidFindBy(uiAutomator = "new UiScrollable(new UiSelector().scrollable(true))"
			+ ".scrollIntoView(new UiSelector().textContains(\"الخروج\"))")
	MobileElement logout_btn;

	@FindBy(id = "android:id/button1")
	MobileElement confirm_logout_btn;

	@FindBy(id = "com.faturaegypt.app.stage:id/product_card")
	MobileElement added_element_inside_cart;

	@FindBy(id = "com.faturaegypt.app.stage:id/btn_order_now")
	MobileElement order_now_btn;

	@FindBy(id = "com.faturaegypt.app.stage:id/confirmButton")
	MobileElement confirm_order_btn;

	@FindBy(id = "android:id/message")
	MobileElement confirmation_message;

	@FindBy(id = "android:id/button1")
	MobileElement done_btn;

	@FindBy(id = "com.faturaegypt.app.stage:id/empty_state_layout")
	MobileElement empty_state_layout;

	// Constructor
	public FaturaActivityPO(AndroidDriver<MobileElement> driver, ExtentTest test, WebDriverWait wait) {
		this.driver = driver;
		this.test = test;
		this.wait = wait;
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
		test.log(LogStatus.INFO, "Fatura Activity Initialized");
	}

	// Methods
	public void clickPepsiListBtn() {
		pepsi_list_btn.click();
		test.log(LogStatus.INFO, "Pepsi list button is clicked");
	}

	public void clickWholesalersBtn() {
		wait.until(ExpectedConditions.visibilityOf(wholesalers_layout));
		wholesalers_layout.click();
		test.log(LogStatus.INFO, "Wholesalers button is clicked");
	}

	public void clickCartBtn() {
		cart_btn.click();
		test.log(LogStatus.INFO, "Cart button is clicked");
	}

	public void clickProfileBtn() {
		profile_btn.click();
		test.log(LogStatus.INFO, "Profile button is clicked");
	}

	public void addFirstElementToCart() {
		add_element_btn.click();
		test.log(LogStatus.INFO, "First element is added to cart");
	}

	public boolean isUserAbleToLoginWithValidCredentials() {
		boolean loginWithValidCredentials = wholesalers_layout.isDisplayed();
		logCheckStatus("Check user is able to login with valid credentials: ", loginWithValidCredentials);
		return loginWithValidCredentials;
	}

	public boolean isElementAddedToCart() {
		boolean elementIsAddedToCart = cart_btn.findElement(By.xpath(".//android.widget.TextView")).isDisplayed();
		logCheckStatus("Check selected element is added to cart: ", elementIsAddedToCart);
		return elementIsAddedToCart;
	}

	public boolean isCashPriceAddedToTotalCash() {
		boolean cashPriceAddedToTotalCash = current_total.getText().contains(element_cash_price.getText());
		logCheckStatus("Check selected element cash price is added to total cash: ", cashPriceAddedToTotalCash);
		return cashPriceAddedToTotalCash;
	}

	public boolean isAddedElementPlacedInsideCart() {
		boolean addedElementPlacedInsideCart = added_element_inside_cart.isDisplayed();
		logCheckStatus("Check selected element is placed inside cart: ", addedElementPlacedInsideCart);
		return addedElementPlacedInsideCart;
	}

	public void logOut() {
		logout_btn.click();
		test.log(LogStatus.INFO, "Logout button is clicked");
		confirm_logout_btn.click();
		test.log(LogStatus.INFO, "User has logged out");
	}

	public void submitOrder() {
		order_now_btn.click();
		confirm_order_btn.click();
		test.log(LogStatus.INFO, "Order is submitted");
	}

	public boolean isOrderConfirmed() {
		wait.until(ExpectedConditions.elementToBeClickable(done_btn));
		boolean orderConfirmed = confirmation_message.getText().contains("تم");
		logCheckStatus("Check order is confirmed: ", orderConfirmed);
		return orderConfirmed;
	}

	public void acceptConfirmationMessage() {
		done_btn.click();
		test.log(LogStatus.INFO, "Confirmation message is accepted");
	}

	private void logCheckStatus(String description, boolean pass) {
		if (pass) {
			test.log(LogStatus.PASS, description + "PASSED");
		} else {
			test.log(LogStatus.FAIL, description + "FAILED");
		}
	}

}
