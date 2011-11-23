package br.callcontroller.record;

import java.util.Date;

import br.callcontroller.utillity.Operators;

public class Record {

	private Operators operator;
	private Date created_date;
	private int seconds;
	private int id;
	
	public Record() {
	
	}
	
	public Record(Operators operators, Date date, int seconds) {
		this.operator = operators;
		this.created_date = date;
		this.seconds = seconds;
	}
	
	public void setOperator(Operators operator) {
		this.operator = operator;
	}

	public Date getDate() {
		return created_date;
	}

	public void setDate(Date date) {
		this.created_date = date;
	}

	public int getSeconds() {
		return seconds;
	}

	public void setSeconds(int seconds) {
		this.seconds = seconds;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Operators getOperator() {
		return operator;
	}
	
}
