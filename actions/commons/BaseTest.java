package commons;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {
	private WebDriver driver;

	public WebDriver getDriverInstance() {
		return this.driver;
	}

	protected void closeBrowserAndDriver() {
		String cmd = "";
		try {
			String osName = GlobalConstants.OS_NAME.toLowerCase();

			String driverInstanceName = driver.toString().toLowerCase();
			if (driverInstanceName.contains("chrome")) {
				if (osName.contains("window")) {
					cmd = "taskkill /F /FI \"IMAGENAME eq chromedriver*\"";
				} else {
					cmd = "pkill chromedriver";
				}
			} else if (driverInstanceName.contains("internetexplorer")) {
				if (osName.contains("window")) {
					cmd = "taskkill /F /FI \"IMAGENAME eq IEDriverServer*\"";
				}
			} else if (driverInstanceName.contains("firefox")) {
				if (osName.contains("window")) {
					cmd = "taskkill /F /FI \"IMAGENAME eq geckodriveer*\"";
				} else {
					cmd = "pkill geckodriver";
				}
			} else if (driverInstanceName.contains("edge")) {
				if (osName.contains("window")) {
					cmd = "taskkill /F /FI \"IMAGENAME eq msedgedriver*\"";
				} else {
					cmd = "pkill msedgedriver";
				}
			} else if (driverInstanceName.contains("opera")) {
				if (osName.contains("window")) {
					cmd = "taskkill /F /FI \"IMAGENAME eq operadriver*\"";
				} else {
					cmd = "pkill operadriver";
				}
			} else if (driverInstanceName.contains("safari")) {
				if (osName.contains("mac")) {
					cmd = "pkill safaridriver";
				}
			}

			if (driver != null) {
				driver.manage().deleteAllCookies();
				driver.quit();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				Process process = Runtime.getRuntime().exec(cmd);
				process.waitFor();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	protected WebDriver getBrowserDriver(String browserName, String environmentName) {
		BrowserList browserList = BrowserList.valueOf(browserName.toUpperCase());
		switch (browserList) {
		case FIREFOX:
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
			break;
		case H_FIREFOX:
			// Browser Option: Selenium 3.xx
			FirefoxOptions firefoxOptions = new FirefoxOptions();
			firefoxOptions.addArguments("--headless");
			firefoxOptions.addArguments("window-size=1920x1080");
			driver = new FirefoxDriver(firefoxOptions);
			break;
		case CHROME:
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			break;
		case H_CHROME:
			// Browser Option: Selenium 3.xx
			ChromeOptions chromeOptions = new ChromeOptions();
			chromeOptions.addArguments("--headless");
			chromeOptions.addArguments("window-size=1920x1080");
			driver = new ChromeDriver(chromeOptions);
			break;
		case IE:
			WebDriverManager.iedriver().arch32().setup();
			driver = new InternetExplorerDriver();
			break;
		case EDGE:
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
			break;
		default:
			throw new RuntimeException("Browser is invalid");
		}

		driver.manage().timeouts().implicitlyWait(GlobalConstants.LONG_TIMEOUT, TimeUnit.SECONDS);
		// driver.manage().window().maximize();
		driver.get(environmentName);

		return driver;
	}

	public int generateFakeNumber() {
		Random rand = new Random();
		return rand.nextInt(99999);
	}

	public void sleepInSecond(long second) {
		try {
			Thread.sleep(second * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	protected boolean verifyTrue(boolean condition) {
		boolean casePass = true;
		try {
			Assert.assertTrue(condition);
			System.out.println("---------------------------PASSED---------------------------");
		} catch (Throwable e) {
			casePass = false;
			catchVerifyExceptionManage(e);
		}
		return casePass;
	}

	protected boolean verifyFalse(boolean condition) {
		boolean casePass = true;
		try {
			Assert.assertFalse(condition);
			System.out.println("---------------------------PASSED---------------------------");
		} catch (Throwable e) {
			casePass = false;
			catchVerifyExceptionManage(e);
		}
		return casePass;
	}

	protected boolean verifyEquals(Object actual, Object expected) {
		boolean casePass = true;
		try {
			Assert.assertEquals(actual, expected);
			System.out.println("---------------------------PASSED---------------------------");
		} catch (Throwable e) {
			casePass = false;
			catchVerifyExceptionManage(e);
		}
		return casePass;
	}

	private void catchVerifyExceptionManage(Throwable e) {
		System.out.println("---------------------------FAILED---------------------------");

		ITestResult result = Reporter.getCurrentTestResult();

		System.setProperty("org.uncommons.reportng.escape-output", "false");

		Reporter.log("<p style=\"color:red;\">" + e.toString() + "</p>");

		String screenshotCode = captureErrorScreenshotBase64(driver);
		Reporter.log("<br><a href=\"data:image/png;base64," + screenshotCode + "\" ><img src=\"data:image/png;base64," + screenshotCode + "\" height='100' width='150' /></a><br>");

		VerificationFailures.getFailures().addFailureForTest(result, e);
		result.setThrowable(e);
	}

	public String captureErrorScreenshotBase64(WebDriver driver) {
		return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
	}
}
