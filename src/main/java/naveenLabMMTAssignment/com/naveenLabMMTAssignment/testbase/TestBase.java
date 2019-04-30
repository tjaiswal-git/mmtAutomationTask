package naveenLabMMTAssignment.com.naveenLabMMTAssignment.testbase;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Timeouts;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;

/**
 * This is parent class of all framework setup operation it contains properties
 * file and browser session.
 * 
 * @author tjaiswal
 *
 */

public class TestBase {
	public static WebDriver driver;

	String browser = "chrome";
	public static final Logger logger = Logger.getLogger(TestBase.class.getName());

	public void init(String URL) {
		selectBrowser(browser);

		PropertyConfigurator.configure("Configuration//tarunlog4j.properties");
		getURL(URL);
	}

	public Properties getProp() {
		File file = new File("Configuration//prop.properties");
		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Properties properties = new Properties();
		try {
			properties.load(fileInputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return properties;
	}

	@SuppressWarnings("deprecation")
	public void selectBrowser(String browser)

	{
		if (browser.equalsIgnoreCase("firefox")) {
			FirefoxProfile profile = new FirefoxProfile();
			profile.setPreference("browser.download.folderList", 2);
			profile.setPreference("browser.download.manager.showWhenStarting", false);
			profile.setPreference("browser.helperApps.neverAsk.openFile",
					"text/csv,application/x-msexcel,application/excel,application/x-excel,application/vnd.ms-excel,image/png,image/jpeg,text/html,text/plain,application/msword,application/xml");
			profile.setPreference("browser.helperApps.neverAsk.saveToDisk",
					"text/csv,application/x-msexcel,application/excel,application/x-excel,application/vnd.ms-excel,image/png,image/jpeg,text/html,text/plain,application/msword,application/xml");
			profile.setPreference("browser.helperApps.alwaysAsk.force", false);
			profile.setPreference("browser.download.manager.alertOnEXEOpen", false);
			profile.setPreference("browser.download.manager.focusWhenStarting", false);
			profile.setPreference("browser.download.manager.useWindow", false);
			profile.setPreference("browser.download.manager.showAlertOnComplete", false);
			profile.setPreference("browser.download.manager.closeWhenDone", false);
			FirefoxOptions firefoxOptions = new FirefoxOptions();
			firefoxOptions.addArguments("--incognito");
			firefoxOptions.setCapability("marionette", true);
			System.setProperty("webdriver.gecko.driver", "Configuration\\geckodriver.exe");
			driver = new FirefoxDriver(firefoxOptions);
			logger.info("Firefox has launching...");

		} else if (browser.equalsIgnoreCase("chrome")) {
			ChromeOptions chromeOptions = new ChromeOptions();
			chromeOptions.addArguments("--incognito");
			chromeOptions.setCapability("ACCEPT_SSL_CERTS", true);

			System.setProperty("webdriver.chrome.driver", "Configuration\\chromedriver.exe");
			driver = new ChromeDriver(chromeOptions);
			logger.info("chrome browser has launching..");

		}

	}

	public void getURL(String URL) {
		driver.get(URL);
		logger.info("url has lauching..");
		driver.manage().window().maximize();
		logger.info("Now Browser is maximize...");
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().deleteAllCookies();
		logger.info("Page loading.....");
	}

	public Timeouts ownImplicitWait(long time) {
		Timeouts implicitWait = driver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
		return implicitWait;
	}

	public void closeDriver() {
		driver.close();
	}
}
