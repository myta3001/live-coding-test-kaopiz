package commons;

import org.openqa.selenium.WebDriver;

import pageObjects.phptravels.admin.AdminDashboardPageObject;
import pageObjects.phptravels.admin.AdminLoginPageObject;

public class PageGeneratorManager {
	
	public static AdminLoginPageObject getAdminLoginPageObject(WebDriver driver) {
		return new AdminLoginPageObject(driver);
	}
	
	public static AdminDashboardPageObject getAdminDashboardPageObject(WebDriver driver) {
		return new AdminDashboardPageObject(driver);
	}
}
