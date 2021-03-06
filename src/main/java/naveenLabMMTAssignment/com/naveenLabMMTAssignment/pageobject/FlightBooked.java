package naveenLabMMTAssignment.com.naveenLabMMTAssignment.pageobject;

import naveenLabMMTAssignment.com.naveenLabMMTAssignment.testbase.TestBase;
import naveenLabMMTAssignment.com.naveenLabMMTAssignment.utility.CommonUtility;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author TJaiswal
 * @mail tarunjaiswal92@gmail.com
 */

public class FlightBooked extends TestBase {
	public WebDriver driver;
	Properties prop = getProp();
	public final Logger logger = Logger.getLogger(FlightBooked.class.getName());

	@FindBy(xpath = "//a[text()='Search']")
	WebElement searchBtn;

	@FindBy(xpath = "//li[text()='Round Trip']")
	WebElement roundTripFlight;

	@FindBy(xpath = "//label[@for='departure']")
	WebElement departure;

	@FindBy(xpath = "//span[text()='RETURN']")
	WebElement returnFlight;

	@FindBy(className = "DayPicker-Day")
	WebElement dayPicker;

	@FindBy(xpath = "//p[@class='error-title']")
	WebElement errorPage;

	@FindBy(xpath = "//label[@for='filter_stop0']/span[1]/span[@class='check']")
	WebElement nonStopFlight;

	@FindBy(xpath = "//label[@for='filter_stop1']/span[1]/span[@class='check']")
	WebElement midstopFlight;

	@FindBy(xpath = "//span[@class='splitVw-total-fare']")
	WebElement splitTotalFare;

	@FindBy(xpath = "//div[@class='splitVw-footer-left ']/div/div/div[4]/p/span")
	WebElement toSideFligtCost;

	@FindBy(xpath = "//div[@class='splitVw-footer-right ']/div/div/div[4]/p/span")
	WebElement returnSideFlightCost;

	@FindBy(xpath = "//p[@class='disc-applied']/span[2]")
	WebElement discountAvail;

	@FindBy(xpath = "//a[@id='webklipper-publisher-widget-container-notification-close-div']")
	WebElement notificationPopUp;

	public FlightBooked(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	/**
	 * This method is used for search flight and fliter based on the stop, non
	 * stop
	 *
	 * @throws InterruptedException
	 */
	public void bookedTickets() throws InterruptedException {
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		Thread.sleep(5000);
		System.out.println("Notifcation popup status is " + checkNotificationPopupIsExistOrNot());
		roundTripFlight.click();
		logger.info("Round trip option is selected...");
		departure.click();
		Thread.sleep(3000);
		departureFilghtDate();
		Thread.sleep(3000);
		returnFlight.click();
		driver.switchTo().defaultContent();
		Thread.sleep(3000);
		returnFlightDate();

		searchFlight();

		if (errorPage()) {
			System.out.println("Page is not loading or site in maintance");
		} else {
			System.out.println("Site is up and running keep patience");
			System.out.println("Total departure flight are for default fliter " + totalDepFlights());
			System.out.println("Total return flight are for default fliter " + totalReturnsFlights());
			selectFliterNonAndStopFlight();

		}

	}

	/**
	 * In case have any offer notification item then we are following same thing
	 *
	 * @return
	 * @throws InterruptedException
	 */
	private boolean checkNotificationPopupIsExistOrNot() throws InterruptedException {
		boolean status = false;
		try {
			if (driver.findElement(By.id("webklipper-publisher-widget-container-notification-frame")).isDisplayed()) {
				System.out.println("frame element is found");
				driver.switchTo().frame(driver.findElement(By.id("webklipper-publisher-widget-container-notification-frame")));
				System.out.println("changed frame");
			}
			Thread.sleep(3000);

			if (notificationPopUp.isDisplayed()) {
				try {
					Thread.sleep(3000);
					notificationPopUp.click();
					status = true;
				} catch (ElementClickInterceptedException e) {
					notificationPopUp.click();
				}
			}
		}
		catch (NoSuchElementException e)
		{
			logger.trace("No such element found..");
		}
		return  status;
	}

	/**
	 * This method is used for get total departure flight object in the page
	 *
	 * @return
	 * @throws InterruptedException
	 */
	private List<WebElement> getTotalDepartureFlightsElement() throws InterruptedException {
		JavascriptExecutor js = ((JavascriptExecutor) driver);

		List<WebElement> totalDepartureFlightsObject = null;
		for (int second = 0;; second++) {
			if (second >= 60) {
				break;
			}
			js.executeScript("window.scrollBy(0,200)", ""); // y value '200' can
			// be altered
			Thread.sleep(1000);
			totalDepartureFlightsObject = driver.findElements(By.xpath("//input[@name='splitowJourney']"));
		}
		totalDepartureFlightsObject = driver.findElements(By.xpath("//input[@name='splitowJourney']"));
		return totalDepartureFlightsObject;
	}

	/**
	 * This method is used for get total return flight object in the page
	 *
	 * @return
	 * @throws InterruptedException
	 */
	private List<WebElement> getTotalReturnFlightsElement() throws InterruptedException {
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		List<WebElement> totalReturnFlightsObject = null;
		for (int second = 0;; second++) {
			if (second >= 60) {
				js.executeScript("window.scrollTo(0, -document.body.scrollHeight)");
				Thread.sleep(5000);
				break;
			}
			Thread.sleep(1000);
			totalReturnFlightsObject = driver.findElements(By.xpath("//input[@name='splitrtJourney']"));
		}

		return totalReturnFlightsObject;
	}

	/**
	 * This method is used for scoll the page on to top
	 */
	private void scrollPage() {
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("window.scrollTo(0,0)");
	}

	/**
	 * specific search flight only
	 */
	public void searchFlight() {
		try {
			searchBtn.click();
		} catch (TimeoutException e) {
			System.out.println("Timeout ... again trying to connect");
			searchBtn.click();
		}
	}

	/**
	 * this method is used for select today for departure
	 */
	public void departureFilghtDate() {
		List<WebElement> allDays = driver.findElements(By.className("DayPicker-Day"));
		for (int dayIndex = 0; dayIndex < allDays.size(); dayIndex++) {
			try {
				System.out.println(allDays.get(dayIndex).getAttribute("aria-label"));
				String datTxt = allDays.get(dayIndex).getAttribute("aria-label");
				System.out.println(datTxt);
				CommonUtility cUtility = new CommonUtility();
				String todayday = cUtility.getTodayDate();

				if (datTxt.trim().equals(todayday)) {
					allDays.get(dayIndex).click();
					Thread.sleep(2000);
					break;
				}

			} catch (Exception e) {
				System.out.println("Exception block");
			}

		}
	}

	/**
	 * This method is used for select return flight date for after 7 days
	 *
	 * @throws InterruptedException
	 */
	public void returnFlightDate() throws InterruptedException {
		List<WebElement> allDays = driver.findElements(By.className("DayPicker-Day"));
		for (int dayIndex = 0; dayIndex < allDays.size(); dayIndex++) {
			try {
				System.out.println(allDays.get(dayIndex).getAttribute("aria-label"));
				String datTxt = allDays.get(dayIndex).getAttribute("aria-label");
				CommonUtility cUtility = new CommonUtility();

				String after7daysDate = cUtility.getAfter7daysDate();
				if (datTxt.trim().equals(after7daysDate)) {
					Thread.sleep(3000);
					allDays.get(dayIndex).click();
					break;
				}
			} catch (Exception e) {
				System.out.println("Exception block");
			}

		}
	}

	/**
	 * This method is used for if server is down or some other reason is working
	 * then showing some message to user
	 *
	 * @return
	 */
	private boolean errorPage() {
		try {
			String errorPageMsg = "";
			WebElement errorPage = driver.findElement(By.xpath("//p[@class='error-title']"));
			if (errorPage.isDisplayed()) {
				System.out.println("Error page is displyed...");
				errorPageMsg = errorPage.getText();
				System.out.println("Error msg " + errorPageMsg);
				return true;
			}
		} catch (NoSuchElementException e) {
			return false;
		}
		return false;
	}

	/**
	 * This method is used for select filter for non stop and stop flight for
	 * departure and return
	 *
	 * @throws InterruptedException
	 */
	private void selectFliterNonAndStopFlight() throws InterruptedException {
		try {
			scrollPage();
			nonStopFlight.click();
			Thread.sleep(2000);
			midstopFlight.click();
			logger.info("total non and stop departure flight count is " + totalNonAndStopDepartureFlights());
			logger.info("total non and stop return flight count is " + totalNonAndStopReturnFlights());
		} catch (NoSuchElementException e) {
			logger.info("While clicking the element getting unusal exception...");
		}
	}

	/**
	 * This method is used for total number of departure flight are non stop and
	 * stop
	 *
	 * @return
	 * @throws InterruptedException
	 */
	public int totalNonAndStopDepartureFlights() throws InterruptedException {
		int totalnonAndstopFlights = totalDepFlights();
		return totalnonAndstopFlights;
	}

	/**
	 * This method is used for total number of return flight are non stop and
	 * stop
	 *
	 * @return
	 * @throws InterruptedException
	 */
	public int totalNonAndStopReturnFlights() throws InterruptedException {
		int totalnonAndstopReturnFlights = totalReturnsFlights();
		return totalnonAndstopReturnFlights;
	}

	/**
	 * This method is used for total number of departure flight for defualt
	 * fliter selection
	 *
	 * @return
	 * @throws InterruptedException
	 */
	public int totalDepFlights() throws InterruptedException {
		int allFlightsCount = 0;
		// 1+ is added because we are not adding already selected flight
		try {
			allFlightsCount = getTotalDepartureFlightsElement().size() + 1;

		} catch (NoSuchElementException e) {
			logger.info("No flight information is found..");
		}
		return allFlightsCount;
	}

	/**
	 * This method is used for total number of return flight for defualt fliter
	 * selection
	 *
	 * @return
	 * @throws InterruptedException
	 */
	public int totalReturnsFlights() throws InterruptedException {
		int allReturnFlightsCount = 0;
		// 1+ is added because we are not adding already selected flight
		try {
			allReturnFlightsCount = getTotalReturnFlightsElement().size() + 1;

		} catch (NoSuchElementException e) {
			logger.info("No flight information is found..");
		}
		return allReturnFlightsCount;
	}

	/**
	 * This method is used for found out of first top 10 flight for departure
	 * side
	 *
	 * @return
	 */
	private int firstTenSourceSideFlight() {
		scrollPage();
		int totalToSideFlight = 0;
		try {
			List<WebElement> topFlights = driver.findElements(By.xpath(
					"//input[@name='splitowJourney']/parent::div/label/div/span[@class='splitVw-outer append_right9']"));
			totalToSideFlight = topFlights.size();
			if (totalToSideFlight > 10) {
				logger.info("we found more then 10 flight ,, so only select first 10");
				totalToSideFlight = 10;

			} else if (totalToSideFlight == 10) {
				logger.info("we found exact 10 flight ,, so only select all 10 flight");
			} else {
				logger.info("we are found flight less than top 10.. " + totalToSideFlight);
			}
		} catch (Exception e) {
			logger.info("No such element or other element related exception ");
		}
		return totalToSideFlight;
	}

	/**
	 * This method is used for found out of first top 10 flight for return side
	 *
	 * @return
	 */
	private int firstTenDestinationSideFlight() {
		scrollPage();
		List<WebElement> topFlights = driver.findElements(By.xpath(
				"//input[@name='splitrtJourney']/parent::div/label/div/span[@class='splitVw-outer append_right9']"));
		int totalDestSideFlight = topFlights.size();
		if (totalDestSideFlight > 10) {
			logger.info("we found more then 10 flight ,, so only select first 10");
			totalDestSideFlight = 10;
		} else if (totalDestSideFlight == 10) {
			logger.info("we found exact 10 flight ,, so only select all 10 flight");
		} else {
			logger.info("we are found flight less than top 10.. " + totalDestSideFlight);
		}
		return totalDestSideFlight;
	}

	/**
	 * This method is used for check flight availability for both side
	 *
	 * @return
	 */
	public int checkTopFlightAvailabilityOnBothSide() {
		int equalFlightOnBothSide = 0;
		int firstTopFlightToSide = firstTenSourceSideFlight();
		int firstTopFlightDestSide = firstTenDestinationSideFlight();
		if (firstTopFlightToSide == firstTopFlightDestSide) {
			equalFlightOnBothSide = firstTopFlightToSide;
		} else if (firstTopFlightDestSide < firstTopFlightDestSide) {
			equalFlightOnBothSide = firstTopFlightDestSide;
		} else {
			equalFlightOnBothSide = firstTopFlightToSide;
		}
		return equalFlightOnBothSide;
	}

	/**
	 * This method is used for say about fare for source to destination station
	 *
	 * @return
	 * @throws NoSuchElementException
	 */
	public int getSourceSideFlightFare() throws NoSuchElementException {
		String flightCost = toSideFligtCost.getText().trim();
		flightCost = flightCost.replaceAll("[[a-zA-Z]\\,]", "").trim();
		int flightFare = Integer.parseInt(flightCost);
		return flightFare;
	}

	/**
	 * This method is used for say about fare for destination to source station
	 *
	 * @return
	 * @throws NoSuchElementException
	 */
	public int getDestSideFlightFare() throws NoSuchElementException {
		String flightCost = returnSideFlightCost.getText().trim();
		flightCost = flightCost.replaceAll("[[a-zA-Z]\\,]", "").trim();
		int flightFare = Integer.parseInt(flightCost);
		return flightFare;
	}

	/**
	 * This method is used for say total fare for round trip
	 *
	 * @return
	 * @throws NoSuchElementException
	 * @throws InterruptedException
	 */
	public int getTotalBothSideFlightFare() throws NoSuchElementException, InterruptedException {
		String flightCost = splitTotalFare.getText().trim();
		flightCost = flightCost.replaceAll("[[a-zA-Z]\\,]", "").trim();
		int discountRs = 0;
		if (checkDiscountIsAvail()) {
			String discountValInStr = discountAvail.getText().trim();
			discountValInStr = discountValInStr.replaceAll("[[a-zA-Z]\\,]", "").trim();
			discountRs = Integer.parseInt(discountValInStr);
			System.out.println("Discount coupen is " + discountRs + "Rs");
		}
		int flightFare = Integer.parseInt(flightCost);
		return flightFare + discountRs;
	}

	/**
	 * This method is used for check for any discount coupen is avail or not
	 *
	 * @return
	 * @throws InterruptedException
	 */
	private boolean checkDiscountIsAvail() throws InterruptedException {
		boolean status = false;
		Thread.sleep(2000);
		try {
			if (discountAvail.isDisplayed()) {
				status = true;
			}
		} catch (Exception e) {
			return status;
		}
		return status;
	}

	/**
	 * This method is used for select one of flight in top 10 list
	 *
	 * @param elementIndex
	 * @throws InterruptedException
	 */
	public void selectTopFlights(int elementIndex) throws InterruptedException {
		List<WebElement> toflightElement = driver.findElements(By.xpath(
				"//input[@name='splitowJourney']/parent::div/label/div/span[@class='splitVw-outer append_right9']"));
		List<WebElement> destFlightElement = driver.findElements(By.xpath(
				"//input[@name='splitrtJourney']/parent::div/label/div/span[@class='splitVw-outer append_right9']"));

		{
			try {
				Thread.sleep(3000);
				scrollToElement();

				toflightElement.get(elementIndex).click();
				logger.info("selected the flight(source) in list of " + elementIndex);

				Thread.sleep(3000);
				destFlightElement.get(elementIndex).click();
				logger.info("selected the flight(destination) in list of " + elementIndex);
			} catch (NoSuchElementException e) {
				logger.info("Not able to click for top flight ");
			}
		}
	}

	/**
	 * This method is used for Scroll the page with specific height of scroll
	 * bar
	 */
	private void scrollToElement() {
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("window.scrollBy(0,150)", "");
	}

}
