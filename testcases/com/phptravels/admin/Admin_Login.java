package com.phptravels.admin;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.BaseTest;
import commons.GlobalConstants;
import commons.PageGeneratorManager;
import pageObjects.phptravels.admin.AdminDashboardPageObject;
import pageObjects.phptravels.admin.AdminLoginPageObject;

public class Admin_Login extends BaseTest {
	private WebDriver driver;
	private AdminLoginPageObject adminLoginPage;
	private AdminDashboardPageObject adminDashboardPage;
	private String loginEmail, password, expectedEmailPlaceholder, expectedPasswordPlaceholder, expectedDashboardPageTitle;

	@Parameters({ "browser" })
	@BeforeClass
	public void beforeClass(String browserName) {
		driver = getBrowserDriver(browserName, GlobalConstants.PHPTRAVELS_ADMIN_URL);
		adminLoginPage = PageGeneratorManager.getAdminLoginPageObject(driver);

		loginEmail = "admin@phptravels.com";
		password = "demoadmin";
		expectedEmailPlaceholder = "Email";
		expectedPasswordPlaceholder = "Password";
		expectedDashboardPageTitle = "Dashboard";
	}

	@Test
	public void TC_01_Success_Login() {
		adminLoginPage.inputToEmailTextbox(loginEmail);
		adminLoginPage.inputToPasswordTextbox(password);
		adminDashboardPage = adminLoginPage.clickToLoginButton();

		verifyTrue(adminDashboardPage.isDashboardPageDisplayed(expectedDashboardPageTitle));
		
		adminLoginPage = adminDashboardPage.clickToLogoutLink();
	}
	
	@Test
	public void TC_02_Verify_Textbox_Placeholder_And_Remember_Checkbox_Select() {
		verifyEquals(adminLoginPage.getEmailTextboxPlaceholder(), expectedEmailPlaceholder);
		verifyEquals(adminLoginPage.getPasswordTextboxPlaceholder(), expectedPasswordPlaceholder);
		
		adminLoginPage.clickToRememberCheckbox();
		
		verifyTrue(adminLoginPage.isRememberCheckboxSelected());
	}

	@Test
	public void TC_03_Verify_Custom_Placeholder() {
		verifyEquals(adminLoginPage.getEmailCustomTextboxPlaceholder(), expectedEmailPlaceholder);
		verifyEquals(adminLoginPage.getPasswordCustomTextboxPlaceholder(), expectedPasswordPlaceholder);
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		closeBrowserAndDriver();
	}

}