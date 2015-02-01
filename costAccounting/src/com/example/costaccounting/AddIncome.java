package com.example.costaccounting;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class AddIncome extends MainActivity implements OnClickListener{
	
	private static final String LOG_TAG = "myLogs";
	
	Button bOK;
	Button bCancel;
	Button bList;
	
	EditText inpSource;
	EditText inpAmount;
	
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addincome);
        
        bHome = (Button) findViewById(R.id.bHome);
        bExp = (Button) findViewById(R.id.bExp);
        bInc = (Button) findViewById(R.id.bInc);
        bStat = (Button) findViewById(R.id.bStat);
        bOK = (Button) findViewById(R.id.bOK);
        bCancel = (Button) findViewById(R.id.bCancel);
        bList = (Button) findViewById(R.id.bList);
        
        inpSource = (EditText) findViewById(R.id.inpSource);
        inpAmount = (EditText) findViewById(R.id.inpAmount);
        
        bHome.setOnClickListener(this);
        bExp.setOnClickListener(this);
        bInc.setOnClickListener(this);
        bStat.setOnClickListener(this);
        bOK.setOnClickListener(this);
        bCancel.setOnClickListener(this);
        bList.setOnClickListener(this);

	}
	
	@Override
	public void onClick(View V) {
		super.onClick(V);
		SQLiteDatabase mydb3 = dbh.openDB(dbh);
    	switch(V.getId()){
    	    	case R.id.bOK :
    	    // получаем данные из полей ввода
    		int checker = 0;
    		String goal = null;
    	    if(inpSource.getText()==null) {
    	    	Log.d(LOG_TAG, "nothing is written");
    	    	checker++;
    	    } else {
    	    	goal = inpSource.getText().toString();
    	    }
    	    
    	    double amount = 0;
    	    try{
    	    	amount = Integer.parseInt(inpAmount.getText().toString());
    	    } catch (NumberFormatException e) {
    	    	Log.d(LOG_TAG, "nothing is written");
    	    	checker++;
    	    }
    	    if(checker == 0) {
     		 // создаем объект для данных
    	    dbh.addData(mydb3, goal, amount);
    	    }
    		break;
    	case R.id.bCancel : 
    		
    		break;
    	case R.id.bList : 
    		dbh.seeAllDatabase(dbh.getAllAmounts(), dbh.getAllCategory());
    		break;
    		
    	default:
    		break;
    	}
    }
    
	
}