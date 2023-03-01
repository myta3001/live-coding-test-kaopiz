package commons;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * @author myta3
 *
 */
public class BasePage {

	private long longTimeout = GlobalConstants.LONG_TIMEOUT;
	private long shortTimeout = GlobalConstants.SHORT_TIMEOUT;

	public static BasePage getBasePageObject() {
		return new BasePage();
	}

	public String getPageTitle(WebDriver driver) {
		return driver.getTitle();
	}

	private By getByXpath(String xpathLocator, String... dynamicLocatorValues) {
		return By.xpath(String.format(xpathLocator, (Object[]) dynamicLocatorValues));
	}

	private WebElement getWebElement(WebDriver driver, String xpathLocator, String... dynamicLocatorValues) {
		WebElement element = null;
		try {
			element = driver.findElement(getByXpath(xpathLocator, dynamicLocatorValues));
		} catch (NoSuchElementException e) {
			e.printStackTrace();
		}
		return element;
	}

	protected List<WebElement> getListWebElements(WebDriver driver, String xpathLocator, String... dynamicLocatorValues) {
		return driver.findElements(getByXpath(xpathLocator, dynamicLocatorValues));
	}

	protected void clickToElement(WebDriver driver, String xpathLocator, String... dynamicLocatorValues) {
		waitForElementClickable(driver, xpathLocator, dynamicLocatorValues);
		getWebElement(driver, xpathLocator, dynamicLocatorValues).click();
	}

	protected void sendkeyToElement(WebDriver driver, String xpathLocator, String textToElement, String... dynamicLocator) {
		WebElement element = getWebElement(driver, xpathLocator, dynamicLocator);
		element.clear();
		element.sendKeys(textToElement);
	}

	protected String getElementText(WebDriver driver, String xpathLocator, String... dynamicLocator) {
		return getWebElement(driver, xpathLocator, dynamicLocator).getText();
	}

	protected List<String> getAllElementText(WebDriver driver, String xpathLocator, String... dynamicLocator) {
		List<String> allElementText = new ArrayList<String>();
		waitForAllElementVisible(driver, xpathLocator, dynamicLocator);
		List<WebElement> allElements = getListWebElements(driver, xpathLocator, dynamicLocator);

		for (WebElement element : allElements) {
			allElementText.add(element.getText());
		}
		return allElementText;
	}

	protected String getELementAttribute(WebDriver driver, String xpathLocator, String attributeName, String... dynamicLocator) {
		return getWebElement(driver, xpathLocator, dynamicLocator).getAttribute(attributeName);
	}

	protected String getElementCssValue(WebDriver driver, String xpathLocator, String propertyName) {
		return getWebElement(driver, xpathLocator).getCssValue(propertyName);
	}

	protected void checkToDefaultCheckboxRadio(WebDriver driver, String xpathLocator, String... dynamicLocator) {
		WebElement element = getWebElement(driver, xpathLocator, dynamicLocator);
		if (!element.isSelected()) {
			element.click();
		}
	}

	protected void uncheckToDefaultCheckbox(WebDriver driver, String xpathLocator) {
		WebElement element = getWebElement(driver, xpathLocator);
		if (element.isSelected()) {
			element.click();
		}
	}

	protected boolean isElementDisplayed(WebDriver driver, String xpathLocator, String... dynamicLocator) {
		return getWebElement(driver, xpathLocator, dynamicLocator).isDisplayed();
	}

	private void overrideGlobalTimeout(WebDriver driver, long timeout) {
		driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
	}

	protected boolean isElementUndisplayed(WebDriver driver, String xpathLocator, String... dynamicLocator) {
		overrideGlobalTimeout(driver, shortTimeout);
		List<WebElement> elements = getListWebElements(driver, xpathLocator, dynamicLocator);
		overrideGlobalTimeout(driver, longTimeout);
		
		if ((elements.size() == 0) || (elements.size() > 0 && !elements.get(0).isDisplayed())) {
			return true;
		} else {
			return false;
		}
	}

	protected boolean isElementEnabled(WebDriver driver, String xpathLocator) {
		return getWebElement(driver, xpathLocator).isEnabled();
	}

	protected boolean isElementSeleted(WebDriver driver, String xpathLocator) {
		return getWebElement(driver, xpathLocator).isSelected();
	}

	protected void clickToElementByJs(WebDriver driver, WebElement item) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("arguments[0].click();", item);
	}

	protected void clickToElementByJs(WebDriver driver, String xpathLocator) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("arguments[0].click();", getWebElement(driver, xpathLocator));
	}

	protected boolean areJQueryAndJSLoadedSuccess(WebDriver driver) {
		WebDriverWait explicitWait = new WebDriverWait(driver, longTimeout);
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;

		ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				try {
					return ((Long) jsExecutor.executeScript("return jQuery.active") == 0);
				} catch (Exception e) {
					return true;
				}
			}
		};

		ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				return jsExecutor.executeScript("return document.readyState").toString().equals("complete");
			}
		};
		return explicitWait.until(jQueryLoad) && explicitWait.until(jsLoad);
	}

	protected String getElementValidationMessage(WebDriver driver, String xpathLocator) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		return (String) jsExecutor.executeScript("return arguments[0].validationMessage;", getWebElement(driver, xpathLocator));
	}

	protected void waitForElementVisible(WebDriver driver, String xpathLocator, String... dynamicLocatorValues) {
		WebDriverWait explicitWait = new WebDriverWait(driver, longTimeout);
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(getByXpath(xpathLocator, dynamicLocatorValues)));
	}

	protected void waitForAllElementVisible(WebDriver driver, String xpathLocator, String... dynamicLocatorValues) {
		WebDriverWait explicitWait = new WebDriverWait(driver, longTimeout);
		explicitWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(getByXpath(xpathLocator, dynamicLocatorValues)));
	}

	protected void waitForAllElementPresence(WebDriver driver, String xpathLocator, String... dynamicLocatorValues) {
		WebDriverWait explicitWait = new WebDriverWait(driver, longTimeout);
		explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(getByXpath(xpathLocator, dynamicLocatorValues)));
	}

	protected void waitForElementPresence(WebDriver driver, String xpathLocator, String... dynamicLocatorValues) {
		WebDriverWait explicitWait = new WebDriverWait(driver, longTimeout);
		explicitWait.until(ExpectedConditions.presenceOfElementLocated(getByXpath(xpathLocator, dynamicLocatorValues)));
	}

	protected void waitForElementInvisible(WebDriver driver, String xpathLocator, String... dynamicLocatorValues) {
		WebDriverWait explicitWait = new WebDriverWait(driver, shortTimeout);
		overrideGlobalTimeout(driver, shortTimeout);
		explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(getByXpath(xpathLocator, dynamicLocatorValues)));
		overrideGlobalTimeout(driver, longTimeout);
	}

	protected void waitForAllElementInvisible(WebDriver driver, String xpathLocator) {
		WebDriverWait explicitWait = new WebDriverWait(driver, shortTimeout);
		overrideGlobalTimeout(driver, shortTimeout);
		explicitWait.until(ExpectedConditions.invisibilityOfAllElements(getListWebElements(driver, xpathLocator)));
		overrideGlobalTimeout(driver, longTimeout);
	}

	protected void waitForElementClickable(WebDriver driver, String xpathLocator, String... dynamicLocatorValues) {
		WebDriverWait explicitWait = new WebDriverWait(driver, longTimeout);
		explicitWait.until(ExpectedConditions.elementToBeClickable(getByXpath(xpathLocator, dynamicLocatorValues)));
	}

	protected void sleepInSecond(long second) {
		try {
			Thread.sleep(second * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}	
}
