package br.callcontroller.persistence.sharedprefs;

import java.util.Date;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import br.callcontroller.utillity.Util;

public class SettingsSHPREF {

	private static SettingsSHPREF settings = null;
	private SharedPreferences prefs;

	private SettingsSHPREF(Context context) {
		prefs = PreferenceManager.getDefaultSharedPreferences(context);
	}

	public static SettingsSHPREF getInstance(Context context) {
		if (settings == null) {
			settings = new SettingsSHPREF(context);
		}
		return settings;
	}

	public int getClaroTimeLimit() {
		return Integer.parseInt(prefs.getString("editClaroPref", "1"));
	}

	public int getOthersOpTimeLimit() {
		return Integer.parseInt(prefs.getString("editOutrasOpPref", "1")) + Integer.parseInt(prefs.getString("editFixoPref", "1"));
	}

	public Date getStartDate(){
		//mes dia ano
		String dateTime = (prefs.getString("dateSelected", "1900/01/01")) ;
		return Util.convertStringToDate(dateTime);
	}
}
