package br.callcontroller.record;

import java.util.Date;
import java.util.List;

import android.content.Context;
import br.callcontroller.persistence.IRecord;
import br.callcontroller.persistence.db.RecordDB;
import br.callcontroller.utillity.Operators;

public class RecordManager {

	private IRecord recordDAO;
	
	public RecordManager(Context context) {
		recordDAO = new RecordDB(context);
	}
	
	public void createRecord(Record record){
		recordDAO.insert(record);
	}
	
	public void deleteRecord(int id){
		recordDAO.remove(id);
	}
	
	public Record searchRecord(int id){
		return recordDAO.search(id);
	}
	
	public List<Record> allRecordsBetweenDates(Date startDate, Date endDate){
		return recordDAO.allRecordBetween(startDate, endDate);
	}
	
	public void updateRecord(Record record){
		recordDAO.update(record);
	}
	
	public int getTotalTimeByOperator(Operators operator, Date startDate, Date endDate){
		return recordDAO.totalTimeOperator(operator, startDate, endDate);
	}
}
