package pageObjects.phptravels.admin;

import org.openqa.selenium.WebDriver;

import commons.BasePage;
import commons.PageGeneratorManager;
import pageUIs.phptravels.admin.AdminLoginPageUI;

public class AdminLoginPageObject extends BasePage {
	private WebDriver driver;

	public AdminLoginPageObject(WebDriver driver) {
		this.driver = driver;
	}

	public void inputToEmailTextbox(String loginEmail) {
		waitForElementVisible(driver, AdminLoginPageUI.EMAIL_INPUT);
		sendkeyToElement(driver, AdminLoginPageUI.EMAIL_INPUT, loginEmail);
	}

	public void inputToPasswordTextbox(String password) {
		waitForElementVisible(driver, AdminLoginPageUI.PASSWORD_INPUT);
		sendkeyToElement(driver, AdminLoginPageUI.PASSWORD_INPUT, password);
	}

	public AdminDashboardPageObject clickToLoginButton() {
		waitForElementClickable(driver, AdminLoginPageUI.LOGIN_BUTTON);
		clickToElement(driver, AdminLoginPageUI.LOGIN_BUTTON);
		return PageGeneratorManager.getAdminDashboardPageObject(driver);
	}
	
	public String getEmailTextboxPlaceholder() {
		waitForElementVisible(driver, AdminLoginPageUI.EMAIL_INPUT);
		return getELementAttribute(driver, AdminLoginPageUI.EMAIL_INPUT, "placeholder");
	}
	
	public String getPasswordTextboxPlaceholder() {
		waitForElementVisible(driver, AdminLoginPageUI.PASSWORD_INPUT);
		return getELementAttribute(driver, AdminLoginPageUI.PASSWORD_INPUT, "placeholder");
	}

	public String getEmailCustomTextboxPlaceholder() {
		waitForElementVisible(driver, AdminLoginPageUI.EMAIL_INPUT_CUSTOM_PLACEHOLDER);
		return getElementText(driver, AdminLoginPageUI.EMAIL_INPUT_CUSTOM_PLACEHOLDER);
	}

	public String getPasswordCustomTextboxPlaceholder() {
		waitForElementVisible(driver, AdminLoginPageUI.PASSWORD_INPUT_CUSTOM_PLACEHOLDER);
		return getElementText(driver, AdminLoginPageUI.PASSWORD_INPUT_CUSTOM_PLACEHOLDER);
	}

	public void clickToRememberCheckbox() {
		waitForElementClickable(driver, AdminLoginPageUI.REMEMBER_ME_CHECKBOX);
		clickToElement(driver, AdminLoginPageUI.REMEMBER_ME_CHECKBOX);
	}

	public boolean isRememberCheckboxSelected() {
		waitForElementVisible(driver, AdminLoginPageUI.REMEMBER_ME_CHECKBOX_SELECTED);
		return isElementDisplayed(driver, AdminLoginPageUI.REMEMBER_ME_CHECKBOX_SELECTED);
	}
}
