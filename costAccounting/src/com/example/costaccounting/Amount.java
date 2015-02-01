package com.example.costaccounting;

public class Amount {

	private int _id;
	private int Goal;
	private double Amount;
	private String Date;

	public void setID(int id) {
		this._id = id;
	}
	
	public int getID() {
		return this._id;
	}
	
	public void setGoal(int goal) {
		this.Goal = goal;
	}
	
	public int getGoal() {
		return this.Goal;
	}
	
	public void setAmount(double amount) {
		this.Amount = amount;
	}
	
	public double getAmount() {
		return this.Amount;
	}
	
	public void setDate(String date) {
		this.Date = date;		
	}
	
	public String getDate() {
		return this.Date;
	}
}
