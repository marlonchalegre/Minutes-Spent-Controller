package br.callcontroller.activities;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.widget.DatePicker;
import br.callcontroller.R;

public class SettingsUI extends PreferenceActivity {

	private static final int DATE_DIALOG_ID = 67770;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.settings);
		Preference customPref = (Preference) findPreference("customPref");
		customPref
				.setOnPreferenceClickListener(new OnPreferenceClickListener() {
					public boolean onPreferenceClick(Preference preference) {
						showDialog(DATE_DIALOG_ID);
						return true;
					}
				});
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		Calendar c = Calendar.getInstance();
		int cyear = c.get(Calendar.YEAR);
		int cmonth = c.get(Calendar.MONTH);
		int cday = c.get(Calendar.DAY_OF_MONTH);
		switch (id) {
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this, mDateSetListener, cyear, cmonth,
					cday);
		}
		return null;
	}

	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
		// onDateSet method
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			String date_selected = String.format("%4d-%2d-%2d", year,monthOfYear + 1,dayOfMonth);
			
			SharedPreferences customSharedPreference = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
			SharedPreferences.Editor editor = customSharedPreference.edit();
			editor.putString("dateSelected", date_selected);
			editor.commit();			
		}
	};
}
