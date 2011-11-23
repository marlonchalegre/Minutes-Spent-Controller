package br.callcontroller.persistence.db;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import br.callcontroller.persistence.IRecord;
import br.callcontroller.record.Record;
import br.callcontroller.utillity.Operators;
import br.callcontroller.utillity.Util;

public class RecordDB implements IRecord {

	private SQLiteDatabase db;
	private static final String DATABASE_NAME = "SobMedida";
	private static final int DATABASE_VERSION = 2;
	private static final String TABLE_NAME = "Records";
	private static final String TAG = "RecordDB";
	private SQLiteAdapter dbHelper;
	private Context context;
	public static final String[] SCRIPT_DB_CREATE = { "create table "
			+ TABLE_NAME
			+ " (_id integer primary key autoincrement, operator text not null, "
			+ " created_date date not null, seconds int not null)" };

	public RecordDB(Context context) {
		dbHelper = new SQLiteAdapter(context, DATABASE_NAME, DATABASE_VERSION,
				SCRIPT_DB_CREATE, null);
		 db = dbHelper.getWritableDatabase();
		 this.context = context;
	}

	private void close() {
		if (dbHelper != null) {
			dbHelper.close();
			//db.close();
		}
	}

	private void open() {
		dbHelper = new SQLiteAdapter(context, DATABASE_NAME, DATABASE_VERSION,
				SCRIPT_DB_CREATE, null);
		db = dbHelper.getWritableDatabase();
	}

	@Override
	public void insert(Record record) {
		try {

			if (db == null || !db.isOpen()) {
				open();
			}

			String created_date = Util.formatToSQLDate(record.getDate());
			ContentValues values = new ContentValues();

			values.put("operator", record.getOperator().toString());
			values.put("created_date", created_date);
			values.put("seconds", record.getSeconds());

			db.insert(TABLE_NAME, null, values);
		} catch (Exception ex) {
			Log.e(TAG, ex.getMessage());
		} finally {
			this.close();
		}
	}

	@Override
	public void update(Record record) {
		try {

			if (db == null || !db.isOpen()) {
				open();
			}

			String created_date = Util.formatToSQLDate(record.getDate());
			ContentValues values = new ContentValues();

			values.put("operator", record.getOperator().toString());
			values.put("created_date", created_date);
			values.put("seconds", record.getSeconds());

			db.update(TABLE_NAME, values, "_id=" + record.getId(), null);
		} catch (Exception ex) {
			Log.e(TAG, ex.getMessage());
		} finally {
			this.close();
		}
	}

	@Override
	public void remove(int id) {
		try {
			if (db == null || !db.isOpen()) {
				open();
			}
			db.delete(TABLE_NAME, "_id=" + id, null);
		} catch (Exception ex) {
			Log.e(TAG, ex.getMessage());
		} finally {
			this.close();
		}

	}

	// TODO: Return an exception when the record was not found
	@Override
	public Record search(int id) {
		try {

			if (db == null || !db.isOpen()) {
				open();
			}
			Record record = new Record();
			Cursor c = db.query(TABLE_NAME, new String[] { "_id", "operator",
					"seconds", "created_date" }, "_id=" + id, null, null, null,
					null);
			if (c.getCount() > 0) {
				c.isFirst();
				record.setId(c.getInt(0));
				Operators operator = Operators.valueOf(c.getString(1));
				record.setOperator(operator);
				record.setSeconds(c.getInt(3));
				Date date = Util.convertStringToDate(c.getString(3));
				record.setDate(date);
				return record;
			}
			c.close();
		} catch (Exception ex) {
			Log.e(TAG, ex.getMessage());
		} finally {
			this.close();
		}
		return null;
	}

	@Override
	public List<Record> allRecordBetween(Date dateStart, Date dateEnd) {

		List<Record> recordList = new ArrayList<Record>();
		Record record = null;
		// created_date between 2011-09-11 and 2011-09-16
		try {

			if (db == null || !db.isOpen()) {
				open();
			}

			String query = "select * from " + TABLE_NAME
					+ " where created_date between '"
					+ Util.formatToSQLDate(dateStart) + "' AND '"
					+ Util.formatToSQLDate(dateEnd) + "'";

			Cursor c = db.rawQuery(query, null);
			if (c != null && c.moveToFirst()) {
				do {
					record = new Record();
					record.setId(c.getInt(0));
					record.setSeconds(c.getInt(3));

					Operators operator = Operators.valueOf(c.getString(1));
					record.setOperator(operator);

					Date date = Util.convertStringToDate(c.getString(2));
					record.setDate(date);

					recordList.add(record);
				} while (c.moveToNext());

				return recordList;
			}
			c.close();
		} catch (Exception ex) {
			Log.e(TAG, ex.getMessage());
		} finally {
			this.close();
		}
		return null;
	}

	@Override
	public int totalTimeOperator(Operators operator, Date startDate,
			Date endDate) {

		int total = 0;
		String query = "select sum(seconds) from " + TABLE_NAME
				+ " where operator = '" + operator.toString()
				+ "' AND  created_date between '"
				+ Util.formatToSQLDate(startDate) + "' AND '"
				+ Util.formatToSQLDate(endDate) + "'";

		try {

			if (db == null || !db.isOpen()) {
				open();
			}
			Cursor c = db.rawQuery(query, null);
			if (c != null && c.moveToFirst()) {
				return c.getInt(0);
			}
			c.close();
		} catch (Exception ex) {
			Log.e(TAG, ex.getMessage());
		} finally {
			this.close();
		}
		return total;
	}

}
