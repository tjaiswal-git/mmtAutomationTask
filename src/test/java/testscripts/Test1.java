package testscripts;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Test1 {

	
	public static void main(String args[])
	{
		
		String dd ="Rs 11,952";
		String nn =dd.replaceAll("[[a-zA-Z]\\,]", "").trim();
		System.out.println(nn);
		int val = Integer.parseInt(nn);
		System.out.println(val);
		/*
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 7);
		System.out.println("Date = "+ cal.getTime());
		String pattern = "E MMM dd yyyy";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		String date = simpleDateFormat.format(cal.getTime());
		System.out.println(date);
		*/
	}
}
