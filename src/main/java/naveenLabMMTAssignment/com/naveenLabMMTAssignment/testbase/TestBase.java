package naveenLabMMTAssignment.com.naveenLabMMTAssignment.testbase;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Timeouts;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;




/**
 * This is parent class of all framework setup operation
 * it contains properties file,excel reader,waiting statement,screenShoot etc.
 * @author tjaiswal
 *
 */

public class TestBase 
{
	public static WebDriver driver;
	//String URL="";
	String browser="firefox";

	
	
	public static final Logger logger=Logger.getLogger(TestBase.class.getName());
    public void init(String URL)
	{
		selectBrowser(browser);
		
		PropertyConfigurator.configure("Configuration//tarunlog4j.properties");
		getURL(URL);
	}
	
    
    
    public Properties getProp()
    {
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
	  if(browser.equalsIgnoreCase("firefox"))
	  {
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
	        firefoxOptions.setCapability("marionette", true);
	       // firefoxOptions.setCapability("ACCEPT_SSL_CERTS", true);
	        System.setProperty("webdriver.gecko.driver", "Configuration\\geckodriver.exe"); 
	        driver = new FirefoxDriver(firefoxOptions);
	        //System.setProperty("webdriver.gecko.driver", "Configuration\\geckodriver.exe");
	        logger.info("Firefox has launching...");
		  
	  }
	  else if(browser.equalsIgnoreCase("chrome"))
	  {   
		  ChromeOptions chromeOptions = new ChromeOptions();
		  chromeOptions.setCapability("ACCEPT_SSL_CERTS", true);
		  
		  System.setProperty("webdriver.chrome.driver","Configuration\\chromedriver.exe");
		  driver = new ChromeDriver(chromeOptions);
		  logger.info("chrome browser has launching..");
		  
	  }
		
	}
	
	public void getURL(String URL)
	{
		driver.get(URL);
		logger.info("url has lauching..");
		driver.manage().window().maximize();
		logger.info("Now Browser is maximize...");
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		logger.info("Page loading.....");
	}
//	/ZenAuto/src/test/java/Resources/testdata/TestData.xlsx
	
	
	/**
	 * This method is used for waitforElement has to load in page
	 * @param timeOutInSeconds
	 * @param element
	 */

	public void waitforElement(int timeOutInSeconds,WebElement element)
	
	{
	  WebDriverWait wait=new WebDriverWait(driver, timeOutInSeconds);
	  wait.until(ExpectedConditions.visibilityOf(element));
		
	}
	
	/**
	 * This method is used for wait to process some time
	 * @param sleepTimeOutInSeconds
	 */
	
	public static void  sleepTime(int sleepTimeOutInSeconds)
	{
		try 
		{
			Thread.sleep(sleepTimeOutInSeconds*1000);
		} catch (InterruptedException e) 
		{
			
			e.printStackTrace();
		}
	}
	
	public Timeouts ownImplicitWait(long time)
	{
		Timeouts implicitWait=driver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
		return implicitWait;
	}
	
	
	/**
	 * When a FluentWait instance is implemented it defines the maximum amount of time to wait for a condition, 
	 * as well as the frequency with which to check the condition. Furthermore, the user may configure the wait 
	 * to ignore specific types of exceptions whilst waiting, such as NoSuchElementExceptions when searching for
	 *  an element on the page.
	 *  @param element
	 *  @return WebElement element 
	 */
	
	public WebElement ownFluentWait(final WebElement element)
	{
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)

			       .withTimeout(120, TimeUnit.SECONDS)

			       .pollingEvery(5, TimeUnit.SECONDS)

			       .ignoring(WebDriverException.class);

			 

			   WebElement foo = wait.until(new Function<WebDriver, WebElement>() {

			     public WebElement apply(WebDriver driver) {

			       return element;

			     }

			   });
			return foo;
	}
	

	/**
	 * This method is used for take a screen shot through webdriver with format of current time instance
	 * @param name
	 */
			
	
	public void getScreenShot(String name)
	{
		Calendar calendar=Calendar.getInstance();
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
		
		File srcFile=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		try{
		String reportDirectory=new File(System.getProperty("user.dir")).getAbsolutePath()+"/src/main/java/com/guru/Ecommerce/Automation/Screenshot";
		
		File destFile=new File((String)reportDirectory +"/"+name+ "_"+ simpleDateFormat.format(calendar.getTime())+".png");
		FileUtils.copyFile(srcFile, destFile);
		
			
		}
		catch (Exception e) 
		{
		logger.info("Exception is found when capturing Image "+e.getMessage());	
		}
	}
     public void closeDriver()
     {
    	 driver.close();
     }  
}
	