
package testscripts;

import java.util.Properties;

import naveenLabMMTAssignment.com.naveenLabMMTAssignment.pageobject.FlightBooked;
import naveenLabMMTAssignment.com.naveenLabMMTAssignment.testbase.*;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TC001_MMTFlightBookTest extends TestBase {

	FlightBooked flightBooked;

	/**
	 * This method is used for initialization for browser session for perticular
	 * MMT site
	 */
	@BeforeTest
	public void setUp() {
		Properties properties = getProp();
		String mmtURL = properties.getProperty("MMTURL");
		init(mmtURL);
	}

	/**
	 * Test for MMT flight search functionlity
	 * 
	 * @throws InterruptedException
	 */
	@Test(enabled = true)
	public void MMTflightSearchTest() throws InterruptedException {
		flightBooked = new FlightBooked(driver);
		flightBooked.bookedTickets();
		try {
			int totalflightBothSide = flightBooked.checkTopFlightAvailabilityOnBothSide();
			System.out.println("total flight avail " + totalflightBothSide);
			for (int elementIndex = 0; elementIndex < totalflightBothSide; elementIndex++) {
				flightBooked.selectTopFlights(elementIndex);
				int sourceSideFare = flightBooked.getSourceSideFlightFare();
				int destSideFare = flightBooked.getDestSideFlightFare();
				System.out.println(
						"SRC side flight cost " + sourceSideFare + " and flight number is "+elementIndex+" out of TOP 10 ");
				System.out.println(
						"DEST side flight cost " + destSideFare + " and flight number is "+elementIndex+" out of TOP 10 ");
				int totalActaulFare = sourceSideFare + destSideFare;
				int totalexpectedFare = flightBooked.getTotalBothSideFlightFare(elementIndex);
				Assert.assertEquals(totalActaulFare, totalexpectedFare);
			}
		} catch (Exception e) {
			logger.info("while assertion getting unknown exception.." + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Kill for browser session
	 */
	@AfterTest
	public void tearDown() {
		closeDriver();
	}
}
