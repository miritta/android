package com.example.costaccounting;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Expense extends MainActivity implements OnClickListener{
	
	final String LOG_TAG = "myLogs";
	
	Button bAddExp;
	Button bAddInc;
	
	TextView tVres;
	TextView tVresView;
	
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expense);
        
        bHome = (Button) findViewById(R.id.bHome);
        bExp = (Button) findViewById(R.id.bExp);
        bInc = (Button) findViewById(R.id.bInc);
        bStat = (Button) findViewById(R.id.bStat);
        bAddExp = (Button) findViewById(R.id.bAddExp);
        bAddInc = (Button) findViewById(R.id.bAddInc);
        
        tVres = (TextView) findViewById(R.id.tVres);
        tVresView = (TextView) findViewById(R.id.tVresView);
        
        bHome.setOnClickListener(this);
        bExp.setOnClickListener(this);
        bInc.setOnClickListener(this);
        bStat.setOnClickListener(this);
        bAddExp.setOnClickListener(this);
        bAddInc.setOnClickListener(this);

        viewExpense(dbh.dailyExpense());
        
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
		      tVresView.setText(str.toString());
		      //c.close();
		    } else
		      Log.d(LOG_TAG, "Cursor is null");
	}
	
	@Override
	public void onClick(View V) {  
		super.onClick(V);
		//открыли соединение
		SQLiteDatabase mydb4 = dbh.openDB(dbh);
    	switch(V.getId()){
    	   	case R.id.bAddExp :
    	    Intent intentAddExp = new Intent(this, AddExpense.class);
    	    startActivity(intentAddExp);
    		break;
    	case R.id.bAddInc : 
    		Intent intentAddInc = new Intent(this, AddIncome.class);
    	    startActivity(intentAddInc);
    		break;
    	default:
    		break;
    	}
    	mydb4.close();
    }
}