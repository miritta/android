package com.example.costaccounting;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

public class Income extends MainActivity implements OnClickListener{
	
	private final String LOG_TAG = "myLogs";
	private int DIALOG_DATE = 1;
	private int myYear = Calendar.getInstance().get(Calendar.YEAR);
	private int myMonth = Calendar.getInstance().get(Calendar.MONTH);
	private int myDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
	
	Button bAddExp;
	Button bAddInc;
	Button bChsDate;
	Button bChsPeriod;
	Button bChsCategory;
	
	TextView tVres;
	TextView tVresViewIn;
	TextView tVresDate;
	TextView tVresDateView;
	
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.income);
        
        bHome = (Button) findViewById(R.id.bHome);
        bExp = (Button) findViewById(R.id.bExp);
        bInc = (Button) findViewById(R.id.bInc);
        bStat = (Button) findViewById(R.id.bStat);
        bAddExp = (Button) findViewById(R.id.bAddExp);
        bAddInc = (Button) findViewById(R.id.bAddInc);
        bChsDate = (Button) findViewById(R.id.bChsDate);
        bChsPeriod = (Button) findViewById(R.id.bChsPeriod);
        bChsCategory = (Button) findViewById(R.id.bChsCategory);
        
        tVres = (TextView) findViewById(R.id.tVres);
        tVresViewIn = (TextView) findViewById(R.id.tVresViewIn);
        tVresDate = (TextView) findViewById(R.id.tVresDate);
        tVresDateView = (TextView) findViewById(R.id.tVresDateView);
        
        bHome.setOnClickListener(this);
        bExp.setOnClickListener(this);
        bInc.setOnClickListener(this);
        bStat.setOnClickListener(this);
        bAddExp.setOnClickListener(this);
        bAddInc.setOnClickListener(this);
        bChsDate.setOnClickListener(this);
        bChsPeriod.setOnClickListener(this);
        bChsCategory.setOnClickListener(this);

        viewExpense(dbh.monthIncome());
        
	}
	
	private void viewExpense(Cursor c) {
		 if (c != null) {
			 StringBuilder str = new StringBuilder(" ");
		      if (c.moveToFirst()) {
		        do {
		          for (String cn : c.getColumnNames()) {
		            str = str.append(c.getString(c.getColumnIndex(cn))).append(";  ");
		          }
		        } while (c.moveToNext());
		      }
		      tVresViewIn.setText(str.toString());
		      //c.close();
		    } else
		      Log.d(LOG_TAG, "Cursor is null");
	}
	
		@SuppressWarnings("deprecation")
		@Override
	public void onClick(View V) {  
			super.onClick(V);
		//открыли соединение
		SQLiteDatabase mydb5 = dbh.openDB(dbh);
    	switch(V.getId()){
    	 	case R.id.bAddExp :
    	 		Intent intentAddExp = new Intent(this, AddExpense.class);
    	 		startActivity(intentAddExp);
    		break;
    	 	case R.id.bAddInc : 
    	 		Intent intentAddInc = new Intent(this, AddIncome.class);
    	 		startActivity(intentAddInc);
    	 		break;
    	 	case R.id.bChsDate :
    	 		showDialog(DIALOG_DATE);
    	 		break;
    	 	default:
    	 		break;
    	}
    	mydb5.close();
    }
		
		@SuppressWarnings("deprecation")
		protected Dialog onCreateDialog(int id) {
		      if (id == DIALOG_DATE) {
		        DatePickerDialog tpd = new DatePickerDialog(this, myCallBack, myYear, myMonth, myDay);
		        return tpd;
		      }
		      return super.onCreateDialog(id);
		    }
		    
		    OnDateSetListener myCallBack = new OnDateSetListener() {

		    public void onDateSet(DatePicker view, int year, int monthOfYear,
		        int dayOfMonth) {
		      myYear = year;
		      myMonth = monthOfYear;
		      myDay = dayOfMonth;
		    }
		    };
}
