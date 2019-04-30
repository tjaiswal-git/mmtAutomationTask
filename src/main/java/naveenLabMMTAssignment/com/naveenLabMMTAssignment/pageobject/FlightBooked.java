package naveenLabMMTAssignment.com.naveenLabMMTAssignment.pageobject;

import naveenLabMMTAssignment.com.naveenLabMMTAssignment.testbase.*;
import naveenLabMMTAssignment.com.naveenLabMMTAssignment.utility.CommonUtility;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

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

	@FindBy(xpath = "//input[@name='splitowJourney']")
	By totalDepartureFlights;

	@FindBy(xpath = "//input[@name='splitrtJourney']")
	By totalReturnFlights;

	@FindBy(xpath = "//label[@for='filter_stop0']/span[1]/span[@class='check']")
	WebElement nonStopFlight;

	@FindBy(xpath = "//label[@for='filter_stop1']/span[1]/span[@class='check']")
	WebElement midstopFlight;

	@FindBy(xpath = "//span[@class='splitVw-total-fare']")
	WebElement splitTotalFare;

	@FindBy(xpath = "//input[@name='splitowJourney']/parent::div/label/div/span[@class='splitVw-outer append_right9']")
	By toflightRadioBtnInput;

	@FindBy(xpath = "//input[@name='splitrtJourney']/parent::div/label/div/span[@class='splitVw-outer append_right9']")
	By returnflightRadioBtnInput;

	@FindBy(xpath = "//div[@class='splitVw-footer-left ']/div/div/div[4]/p/span")
	WebElement toSideFligtCost;

	@FindBy(xpath = "//div[@class='splitVw-footer-right ']/div/div/div[4]/p/span")
	WebElement returnSideFlightCost;

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
		Thread.sleep(3000);
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
			logger.info("Total departure flight are for default fliter " + totalDepFlights());
			logger.info("Total return flight are  for default fliter " + totalReturnsFlights());
			System.out.println("Total departure flight are for default fliter " + totalDepFlights());
			System.out.println("Total return flight are for default fliter " + totalReturnsFlights());
			selectFliterNonAndStopFlight();

		}

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
	 */
	public int totalNonAndStopDepartureFlights() {
		int totalnonAndstopFlights = totalDepFlights();
		return totalnonAndstopFlights;
	}

	/**
	 * This method is used for total number of return flight are non stop and
	 * stop
	 * 
	 * @return
	 */
	public int totalNonAndStopReturnFlights() {
		int totalnonAndstopReturnFlights = totalReturnsFlights();
		return totalnonAndstopReturnFlights;
	}

	/**
	 * This method is used for total number of departure flight for defualt
	 * fliter selection
	 * 
	 * @return
	 */
	public int totalDepFlights() {
		int allFlightsCount = 0;
		// 1+ is added because we are not adding already selected flight
		try {
			allFlightsCount = driver.findElements(totalDepartureFlights).size() + 1;

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
	 */
	public int totalReturnsFlights() {
		int allReturnFlightsCount = 0;
		// 1+ is added because we are not adding already selected flight
		try {
			allReturnFlightsCount = driver.findElements(totalReturnFlights).size() + 1;

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
		int totalToSideFlight = 0;
		try {
			List<WebElement> topFlights = driver.findElements(toflightRadioBtnInput);
			totalToSideFlight = topFlights.size();
			if (totalToSideFlight > 10) {
				logger.info("we found more then 10 flight ,, so only select first 10");
				totalToSideFlight = 10;
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
		List<WebElement> topFlights = driver.findElements(returnflightRadioBtnInput);
		int totalDestSideFlight = topFlights.size();
		if (totalDestSideFlight > 10) {
			logger.info("we found more then 10 flight ,, so only select first 10");
			totalDestSideFlight = 10;
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
		flightCost = flightCost.replaceAll(",", "");
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
		flightCost = flightCost.replaceAll(",", "");
		int flightFare = Integer.parseInt(flightCost);
		return flightFare;
	}

	/**
	 * This method is used for say total fare for round trip
	 * 
	 * @return
	 * @throws NoSuchElementException
	 */
	public int getTotalBothSideFlightFare() throws NoSuchElementException {
		String flightCost = splitTotalFare.getText().trim();
		flightCost = flightCost.replaceAll(",", "");
		int flightFare = Integer.parseInt(flightCost);
		return flightFare;
	}

	/**
	 * This method is used for select one of flight in top 10 list
	 * 
	 * @param elementIndex
	 * @throws InterruptedException
	 */
	public void selectTopFlights(int elementIndex) throws InterruptedException {
		List<WebElement> toflightElement = driver.findElements(toflightRadioBtnInput);
		List<WebElement> destFlightElement = driver.findElements(returnflightRadioBtnInput);

		{
			try {
				Thread.sleep(3000);
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

}