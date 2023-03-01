package pageObjects.phptravels.admin;

import org.openqa.selenium.WebDriver;

import commons.BasePage;
import commons.PageGeneratorManager;
import pageUIs.phptravels.admin.AdminDashboardPageUI;

public class AdminDashboardPageObject extends BasePage {
	private WebDriver driver;

	public AdminDashboardPageObject(WebDriver driver) {
		this.driver = driver;
	}
	
	public void waitForDashboardPageLoaded() {
		waitForElementInvisible(driver, AdminDashboardPageUI.ADMIN_DASHBOARD_LOADING_OVERLAY);
	}
	
	public void clickToOpenPersonDropdownMenu() {
		waitForDashboardPageLoaded();
		waitForElementClickable(driver, AdminDashboardPageUI.ADMIN_PERSON_MENU_BUTTON);
		clickToElement(driver, AdminDashboardPageUI.ADMIN_PERSON_MENU_BUTTON);
	}

	public AdminLoginPageObject clickToLogoutLink() {
		clickToOpenPersonDropdownMenu();
		waitForElementClickable(driver, AdminDashboardPageUI.ADMIN_LOGOUT_LINK);
		clickToElement(driver, AdminDashboardPageUI.ADMIN_LOGOUT_LINK);
		return PageGeneratorManager.getAdminLoginPageObject(driver);
	}

	public boolean isDashboardPageDisplayed(String pageTitle) {
		waitForElementVisible(driver, AdminDashboardPageUI.ADMIN_DASHBOARD_HEADER_TITLE, pageTitle);
		return isElementDisplayed(driver, AdminDashboardPageUI.ADMIN_DASHBOARD_HEADER_TITLE, pageTitle);
	}
}
