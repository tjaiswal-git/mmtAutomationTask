
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

	@BeforeTest
	public void setUp() {
		Properties properties = getProp();
		String mmtURL = properties.getProperty("MMTURL");
		init(mmtURL);
	}

	@Test
	public void testMe() throws InterruptedException {
		flightBooked = new FlightBooked(driver);
		flightBooked.bookedTickets();
		try {
			int totalflightBothSide = flightBooked.checkTopFlightAvailabilityOnBothSide();
			for (int elementIndex = 0; elementIndex < totalflightBothSide; elementIndex++) {
				flightBooked.selectTopFlights(elementIndex);
				int sourceSideFare = flightBooked.getSourceSideFlightFare();
				int destSideFare = flightBooked.getDestSideFlightFare();
				int totalActaulFare = sourceSideFare + destSideFare;
				int totalexpectedFare = flightBooked.getTotalBothSideFlightFare();
				Assert.assertEquals(totalActaulFare, totalexpectedFare);
			}
		} catch (Exception e) {
			logger.info("while assertion getting unknown exception..");
		}
	}

	@AfterTest
	public void tearDown() {
		closeDriver();
	}
}
