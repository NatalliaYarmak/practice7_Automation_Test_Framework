package webdriver;

import static webdriver.Logger.getLoc;

import java.util.concurrent.TimeUnit;

import javax.naming.NamingException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Strings;

import webdriver.elements.Label;

/**
 * The main class to access the browser, which extends the capabilities of the
 * standard Webdriver
 */
public final class Browser {

	private static final long IMPLICITY_WAIT = 10;
	private static final String DEFAULT_CONDITION_TIMEOUT = "defaultConditionTimeout";
	private static final String DEFAULT_PAGE_LOAD_TIMEOUT = "defaultPageLoadTimeout";

	// имя файла с настройками Selenium
	static final String SELENIUM_PROPERTIES_FILE = "selenium.properties";
	// имя файла с URL-ми страниц
	static final String STAGE_PROPERTIES_FILE = "stage.properties";
	private static final String BROWSER_BY_DEFAULT = "firefox";
	private static final String BROWSER_PROP = "browser";

	// browsers
	private static Browser instance;
	private static RemoteWebDriver driver;
	public static PropertiesResourceManager seleniumProps;
	public static PropertiesResourceManager stageProps;

	// поля класса

	private static String timeoutForPageLoad;
	private static String timeoutForCondition;

	public static Browsers currentBrowser;

	/**
	 * Private constructor (singleton pattern)
	 */
	private Browser() {
		Logger.getInstance().info(String.format(getLoc("loc.browser.ready"), currentBrowser.toString()));
	}

	/**
	 * Checks is Browser slive
	 * 
	 * @return true\false
	 */
	public boolean isBrowserAlive() {
		return instance != null;
	}

	public static String getBaseUrl() {
		return System.getProperty("mainForm", stageProps.getProperty("mainForm"));
	}

	/**
	 * Gets instance of Browser
	 * 
	 * @return browser instance
	 */
	public static Browser getInstance() {
		if (instance == null) {
			initProperties();
			try {
				driver = BrowserFactory.setUp(currentBrowser.toString());
				driver.manage().timeouts().implicitlyWait(IMPLICITY_WAIT, TimeUnit.SECONDS);
				Logger.getInstance().info(getLoc("loc.browser.constructed"));
			} catch (NamingException e) {
				Logger.getInstance().info("NamingException...");
				e.printStackTrace();
			}
			instance = new Browser();
		}
		return instance;
	}

	/**
	 * The implementation of the browser is closed
	 * <p>
	 * see {@link BaseEntity#checkAndKill()} all browser processes will be
	 * killed
	 * <p>
	 * void after test
	 */
	public void exit() {
		try {
			driver.quit();
			Logger.getInstance().info(getLoc("loc.browser.driver.qiut"));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			instance = null;
		}
	}

	/**
	 * gets TimeoutForCondition
	 * 
	 * @return timeoutForCondition
	 */
	public String getTimeoutForCondition() {
		return timeoutForCondition;
	}

	/**
	 * gets TimeoutForPageLoad
	 * 
	 * @return timeoutForPageLoad
	 */
	public String getTimeoutForPageLoad() {
		return timeoutForPageLoad;
	}

	/**
	 * init
	 */
	private static void initProperties() {

		seleniumProps = new PropertiesResourceManager(SELENIUM_PROPERTIES_FILE);
		stageProps = new PropertiesResourceManager(STAGE_PROPERTIES_FILE);
		timeoutForPageLoad = seleniumProps.getProperty(DEFAULT_PAGE_LOAD_TIMEOUT);
		timeoutForCondition = seleniumProps.getProperty(DEFAULT_CONDITION_TIMEOUT);

		if (Strings.isNullOrEmpty(seleniumProps.getProperty(BROWSER_PROP))) {
			// using System.getProperty
			currentBrowser = Browsers.valueOf(System.getProperty(BROWSER_PROP, BROWSER_BY_DEFAULT).toUpperCase());
		} else {
			// using selenium.properties
			String proper = seleniumProps.getProperty(BROWSER_PROP);
			currentBrowser = Browsers.valueOf(proper.toUpperCase());
		}
	}

	/**
	 * wait the download page (on Javascript readyState)
	 */
	public void waitForPageToLoad() {
		// Logger.getInstance().info("waitForPageToLoad started");
		WebDriverWait wait = new WebDriverWait(driver, Long.parseLong(getTimeoutForPageLoad()));

		try {
			wait.until((ExpectedCondition<Boolean>) new ExpectedCondition<Boolean>() {
				public Boolean apply(final WebDriver d) {
					if (!(d instanceof JavascriptExecutor)) {
						return true;
					}
					Object result = ((JavascriptExecutor) d)
							.executeScript("return document['readyState'] ? 'complete' == document.readyState : true");
					if (result != null && result instanceof Boolean && (Boolean) result) {
						return true;
					}
					return false;
				}
			});
		} catch (Exception e) {
			Logger.getInstance().warn(getLoc("loc.browser.page.timeout"));
		}
		// Logger.getInstance().info("waitForPageToLoad ended");
	}

	/**
	 * maximizes the window
	 */
	public void windowMaximise() {
		driver.manage().window().maximize();
	}

	/**
	 * Navgates to the Url
	 * 
	 * @param url
	 *            Url
	 */
	public void navigate(final String url) {
		driver.navigate().to(url);
	}

	/**
	 * get RemoteWebDriver
	 * 
	 * @return driver
	 */
	public RemoteWebDriver getDriver() {
		return driver;
	}

	/**
	 * Clicks on element
	 * 
	 * @param selector
	 *            By Locator
	 */
	@Deprecated
	public void click(final By selector) {
		new Label(selector).click();
	}

	/**
	 * Gets current URL
	 * 
	 * @return current URL
	 */
	public String getLocation() {
		return driver.getCurrentUrl();
	}

	/**
	 * Browsers enumeration
	 */
	public enum Browsers {
		/**
		 * @uml.property name="fIREFOX"
		 * @uml.associationEnd
		 */
		FIREFOX("firefox"),
		/**
		 * @uml.property name="iEXPLORE"
		 * @uml.associationEnd
		 */
		IEXPLORE("iexplore"),
		/**
		 * @uml.property name="cHROME"
		 * @uml.associationEnd
		 */
		CHROME("chrome"),
		/**
		 * @uml.property name="oPERA"
		 * @uml.associationEnd
		 */
		OPERA("opera"),
		/**
		 * @uml.property name="sAFARI"
		 * @uml.associationEnd
		 */
		SAFARI("safari");

		public String value;

		/**
		 * Constructor
		 * 
		 * @param values
		 *            Value
		 */
		Browsers(final String values) {
			value = values;
		}

		/**
		 * Returns string value
		 * 
		 * @return String value
		 */
		public String toString() {
			return value;
		}
	}

}
