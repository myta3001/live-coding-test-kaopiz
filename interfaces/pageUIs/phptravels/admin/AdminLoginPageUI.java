package pageUIs.phptravels.admin;

public class AdminLoginPageUI {
	public static final String EMAIL_INPUT = "//form[contains(@class,'form-signin')]//input[@name='email']";
	public static final String EMAIL_INPUT_CUSTOM_PLACEHOLDER = "//form[contains(@class,'form-signin')]//input[@name='email']//following-sibling::span";
	public static final String PASSWORD_INPUT = "//form[contains(@class,'form-signin')]//input[@name='password']";
	public static final String PASSWORD_INPUT_CUSTOM_PLACEHOLDER = "//form[contains(@class,'form-signin')]//input[@name='password']//following-sibling::span";
	public static final String LOGIN_BUTTON = "//form[contains(@class,'form-signin')]//button";
	public static final String REMEMBER_ME_CHECKBOX = "//input[@name='remember']//parent::div";
	public static final String REMEMBER_ME_CHECKBOX_SELECTED = "//input[@name='remember']//parent::div[contains(@class,'checked')]";
}
