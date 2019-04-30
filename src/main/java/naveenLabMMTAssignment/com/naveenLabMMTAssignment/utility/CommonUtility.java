package naveenLabMMTAssignment.com.naveenLabMMTAssignment.utility;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
/**
 * This class is used for getting current date and after 7 days date for in MMT date format
 * @author TJaiswal
 *
 */
public class CommonUtility {

	String pattern = "E MMM dd yyyy";
	SimpleDateFormat simpleDateFormat;
	
	/**
	 * This method is used for getting current date format
	 * @return
	 */
	public String getTodayDate()
	{
		pattern = "E MMM dd yyyy";
		simpleDateFormat = new SimpleDateFormat(pattern);
		String currentDate = simpleDateFormat.format(new Date());
		return currentDate;
	}
	
	/**
	 * This method is used for getting after 7 days date format
	 * @return
	 */
	public String getAfter7daysDate()
	{
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 7);
		pattern = "E MMM dd yyyy";
		simpleDateFormat = new SimpleDateFormat(pattern);
		String after7DaysDate = simpleDateFormat.format(cal.getTime());
		return after7DaysDate;

	}
}
