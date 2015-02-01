package com.example.costaccounting;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class MainActivity extends ActionBarActivity implements OnClickListener {

	final String LOG_TAG = "myLogs";
	
	Button bHome;
	Button bExp;
	Button bInc;
	Button bStat;

    DBhelper dbh;
    
   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "--- mainActivity ---");
       dbh = new DBhelper(this);
    }

    @Override
    public void onClick(View V) {
    	SQLiteDatabase mydb = dbh.openDB(dbh);
    	switch(V.getId()){
    	case R.id.bHome : 
    		Intent intentHome = new Intent(this, HomeActivity.class);
    		startActivity(intentHome);
    		break;
    	case R.id.bExp : 
    		Intent intentExp = new Intent(this, Expense.class);
    	    startActivity(intentExp);
    		break;
    	case R.id.bInc : 
    		Intent intentInc = new Intent(this, Income.class);
    	    startActivity(intentInc);
    		break;
    	case R.id.bStat : 
	
    		break;
    	default:
    		break;
    	}
    	mydb.close();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
