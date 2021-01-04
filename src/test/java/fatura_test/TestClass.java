package fatura_test;

import org.testng.Assert;
import org.testng.annotations.Test;

import activity_classes.FaturaActivityPO;
import activity_classes.LoginActivityPO;

/*
 * This is the main class used for running the test cases
 * 
 * @author Mahmoud Bindary
 */

public class TestClass extends BaseTestClass {

	@Test(priority = 1, enabled = true, dataProvider = "invalid-phone-number-dp")
	public void invalidPhoneNumberLogin(String invalidOrEmptyPhoneNumber, String validOrInvalidPassword) {
		loginActivity = new LoginActivityPO(driver, test);
		loginActivity.setPhoneNumber(invalidOrEmptyPhoneNumber);
		loginActivity.setPassword(validOrInvalidPassword);
		loginActivity.clickSignInBtn();
		Assert.assertTrue(loginActivity.isLoginFailedDueToInvalidPhoneNumber());
	}

	@Test(priority = 2, enabled = true, dataProvider = "invalid-password-dp")
	public void invalidPasswordLogin(String validPhoneNumber, String invalidOrEmptyPassword) {
		loginActivity = new LoginActivityPO(driver, test);
		loginActivity.setPhoneNumber(validPhoneNumber);
		loginActivity.setPassword(invalidOrEmptyPassword);
		loginActivity.clickSignInBtn();
		Assert.assertTrue(loginActivity.isLoginFailedDueToInvalidPassword());
	}

	@Test(priority = 3, enabled = true)
	public void validLogin() {
		loginActivity = new LoginActivityPO(driver, test);
		loginActivity.setPhoneNumber(validPhoneNumber);
		loginActivity.setPassword(validPassword);
		loginActivity.clickSignInBtn();
		faturaActivity = new FaturaActivityPO(driver, test, wait);
		Assert.assertTrue(faturaActivity.isUserAbleToLoginWithValidCredentials());
	}

	@Test(priority = 4, enabled = true, dependsOnMethods = { "validLogin" })
	public void submittingOrder() {
		faturaActivity = new FaturaActivityPO(driver, test, wait);
		faturaActivity.clickWholesalersBtn();
		faturaActivity.clickPepsiListBtn();
		faturaActivity.addFirstElementToCart();
		Assert.assertTrue(faturaActivity.isElementAddedToCart());
		Assert.assertTrue(faturaActivity.isCashPriceAddedToTotalCash());
		faturaActivity.clickCartBtn();
		Assert.assertTrue(faturaActivity.isAddedElementPlacedInsideCart());
		faturaActivity.submitOrder();
		Assert.assertTrue(faturaActivity.isOrderConfirmed());
		faturaActivity.acceptConfirmationMessage();
	}

	@Test(priority = 5, enabled = true, dependsOnMethods = { "submittingOrder" })
	public void validLogout() {
		faturaActivity = new FaturaActivityPO(driver, test, wait);
		faturaActivity.clickProfileBtn();
		faturaActivity.logOut();
		loginActivity = new LoginActivityPO(driver, test);
		Assert.assertTrue(loginActivity.isAppBackToLoginActivity());
	}

}
