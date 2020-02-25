package co.ean.compromisos.util;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Util {

	public static String calendarToString(Calendar calendar, String formato, Locale local) {

		String fecha = null;
		try {
			SimpleDateFormat fmto = new SimpleDateFormat(formato, local);
			fecha = fmto.format(calendar.getTime());
		} catch (Exception e) {
			System.out.println("Error al Pasar de Calendar a tipo String" + e.getMessage());
		}

		return fecha;
	}

	public static java.sql.Date convertUtilToSql(java.util.Date uDate) {
		java.sql.Date sDate = new java.sql.Date(uDate.getTime());
		return sDate;
	}

	public static Calendar toCalendar(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}

}
