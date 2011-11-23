package br.callcontroller.persistence.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteAdapter extends SQLiteOpenHelper {
	
	private String[] scriptSQLCreate;
	
	public SQLiteAdapter(Context context, String databaseName, int version,  String[] scriptCreate, String scriptDelete) {
		super(context, databaseName, null, version);
		this.scriptSQLCreate = scriptCreate;		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {		
		for(String line : scriptSQLCreate){
			db.execSQL(line);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
	

	}

}
