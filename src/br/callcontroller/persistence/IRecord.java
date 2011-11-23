package br.callcontroller.persistence;

import java.util.Date;
import java.util.List;

import br.callcontroller.record.Record;
import br.callcontroller.utillity.Operators;

public interface IRecord {
	
	public void insert(Record record);
	public void update(Record record);
	public void remove(int id);
	public Record search(int id);
	public List<Record> allRecordBetween(Date dateStart, Date dateEnd);
	public int totalTimeOperator(Operators operator, Date startDate, Date endDate);
}
