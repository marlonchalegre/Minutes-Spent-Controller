package br.callcontroller.receivers;

import java.util.Date;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import br.callcontroller.R;
import br.callcontroller.record.Record;
import br.callcontroller.record.RecordManager;
import br.callcontroller.utillity.Operators;

public class PhoneStateReceiver extends BroadcastReceiver {

	public static String TAG = "PhoneStateReceiver";
	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle extras = intent.getExtras();
		if(extras.getBoolean(context.getString(R.string.KEY_MAINACTIVITY), false)){
			logCalls(context);
		}else{
			phoneStateChanged(context, extras);
		}
	}

	private void phoneStateChanged(Context context, Bundle extras) {
		
		SharedPreferences values = PreferenceManager.getDefaultSharedPreferences(context);
		boolean previousStateOffHook = values.getBoolean(context.getString(R.string.KEY_COUNT), false);
		
		
		if (extras != null) {
			String state = extras.getString(TelephonyManager.EXTRA_STATE);
			Log.d(TAG, state);
			if (state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
				previousStateOffHook = true;
			} else if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)
					&& previousStateOffHook) {
				logCalls(context);
				previousStateOffHook = false;
			} else {
				previousStateOffHook = false;
			}
		}

		// Write the value back to storage for later use
		SharedPreferences.Editor editor = values.edit();
		editor.putBoolean(context.getString(R.string.KEY_COUNT), previousStateOffHook);
		editor.commit();
	}

	public void logCalls(Context context) {

		SharedPreferences values = PreferenceManager.getDefaultSharedPreferences(context);;
		int lastID = values.getInt(context.getString(R.string.KEY_LASTID), 0);
		RecordManager recordManager = new RecordManager(context);
		Cursor cursor = context.getContentResolver().query(
				android.provider.CallLog.Calls.CONTENT_URI, null, "_id > ?",
				new String[] { String.valueOf(lastID) }, null);

		if (cursor != null && cursor.moveToFirst()) {
			do {
				lastID = cursor
						.getColumnIndex(android.provider.CallLog.Calls._ID);
				int _DATE = cursor
						.getColumnIndex(android.provider.CallLog.Calls.DATE);
				int _DURATION = cursor
						.getColumnIndex(android.provider.CallLog.Calls.DURATION);
				int cachedNumber = cursor
						.getColumnIndex(android.provider.CallLog.Calls.CACHED_NUMBER_LABEL);

				long date = cursor.getLong(_DATE);
				String duration = cursor.getString(_DURATION);
				String cachedLabel = cursor.getString(cachedNumber);
				Log.i(TAG, "NUMBER: " + cachedLabel);
				recordManager.createRecord(new Record(getOperator(cachedLabel),
						new Date(date), Integer.parseInt(duration)));
			} while (cursor.moveToNext());
			cursor.close();
			
			SharedPreferences.Editor editor = values.edit();
			editor.putInt(context.getString(R.string.KEY_LASTID), lastID);
			editor.commit();
		}
	}

	private Operators getOperator(String cachedLabel) {

		if(cachedLabel == null){
			return Operators.UNDEFINED;
		}
		
		if (cachedLabel.equalsIgnoreCase("tim")) {
			return Operators.TIM;
		} else if (cachedLabel.equalsIgnoreCase("claro")) {
			return Operators.CLARO;
		} else if (cachedLabel.equalsIgnoreCase("GVT")) {
			return Operators.GVT;
		} else if (cachedLabel.equalsIgnoreCase("Oi")) {// Móvel
			return Operators.OI;
		} else if (cachedLabel.equalsIgnoreCase("Oi Fixo")) {
			return Operators.OIFIXO;
		} else {
			return Operators.UNDEFINED;
		}
	}
}
