package br.callcontroller.utillity;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.util.Log;

public class Util {
	private static final String TAG = "ClaroSobMedida";
	public static Date convertStringToDate(String dateTime) {
		Date date = null;
		SimpleDateFormat iso8601Format = new SimpleDateFormat(
				"yyyy-MM-dd");
		try {
			date = iso8601Format.parse(dateTime);
		} catch (java.text.ParseException e) {
			Log.e(TAG, "Parsing ISO8601 datetime failed", e);
		}
		return date;
	}

	public static String formatToSQLDate(Date date) {
		// set the format to sql date time
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd");
		String created_date = dateFormat.format(date);
		return created_date;
	}
}
