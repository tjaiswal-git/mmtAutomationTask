package naveenLabMMTAssignment.com.naveenLabMMTAssignment.utility;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonUtility {

	public String getTodayDate()
	{
		String pattern = "E MMM dd yyyy";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		String date = simpleDateFormat.format(new Date());
		System.out.println(date);
		return date;
	}
	
}
