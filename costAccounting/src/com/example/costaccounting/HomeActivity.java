package com.example.costaccounting;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class HomeActivity extends MainActivity implements OnClickListener {

	
	
	Button bAddExp;
	Button bAddInc;

    
    //@Override
    protected void onCreate(Bundle savedInstanceState) {
    	Log.d(LOG_TAG, "--- homeActivity ---");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
              
       
        bAddExp = (Button) findViewById(R.id.bAddExp);
        bAddInc = (Button) findViewById(R.id.bAddInc);
        
        
        bAddExp.setOnClickListener(this);
        bAddInc.setOnClickListener(this);
        
        bHome = (Button) findViewById(R.id.bHome);
        bExp = (Button) findViewById(R.id.bExp);
        bInc = (Button) findViewById(R.id.bInc);
        bStat = (Button) findViewById(R.id.bStat);
       
        bHome.setOnClickListener(this);
        bExp.setOnClickListener(this);
        bInc.setOnClickListener(this);
        bStat.setOnClickListener(this);

    }

    @Override
    public void onClick(View V) {
    	super.onClick(V);
    	SQLiteDatabase mydb = dbh.openDB(dbh);
    	switch(V.getId()){
    	
    	case R.id.bAddExp : 
    		Intent intent = new Intent(this, AddExpense.class);
    		startActivity(intent);
    		break;
    	case R.id.bAddInc : 
    		Intent intent2 = new Intent(this, AddIncome.class);
    		startActivity(intent2);
    		break;
    	default:
    		break;
    	}
    	mydb.close();
    }
    
    
}

