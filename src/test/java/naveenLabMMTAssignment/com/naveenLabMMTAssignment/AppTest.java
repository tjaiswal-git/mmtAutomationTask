package naveenLabMMTAssignment.com.naveenLabMMTAssignment;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Unit test for simple App.
 */
public class AppTest {
	public static void main(String args[])
	{
//Wed Apr 03 2019
		String pattern = "E MMM dd yyyy";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		String date = simpleDateFormat.format(new Date());
		System.out.println(date);
			}
}
