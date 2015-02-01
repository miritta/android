package com.example.costaccounting;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

class DBhelper extends SQLiteOpenHelper implements BaseColumns{
	
	private static final DateFormat DBQUERY_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
	private static final String DB_NAME = "category_expense";
	private static final String TABLE1_NAME = "category";
	private static final String TABLE2_NAME = "expense";
	private static final String COLUMN1_NAME = "goal";
	private static final String COLUMN2_NAME = "goal_id";
	private static final int DB_VERSION = 1;
	final String LOG_TAG = "myLogs";
	private static final String CREATE_TABLE1 = "create table " + TABLE1_NAME + "( "
	          + "_id1 integer not null primary key autoincrement, " 
	          + COLUMN1_NAME + " text );"; 
	private static final String CREATE_TABLE2 = "create table " + TABLE2_NAME + "( "
	          + "_id2 integer not null primary key autoincrement, " 
	          + COLUMN2_NAME + " integer, "
	          + "amount double, "
	          + "date DATETIME DEFAULT CURRENT_TIMESTAMP, " 
	          + "FOREIGN KEY(" + COLUMN2_NAME + ") REFERENCES " + TABLE1_NAME + "(_id1) );";
	
    public DBhelper(Context context) {
      // конструктор суперкласса
      super(context, DB_NAME, null, DB_VERSION);
      Log.d(LOG_TAG, "--- constructor dbhelper ---");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    	Log.d(LOG_TAG, "--- onCreate database ---");
      db.execSQL(CREATE_TABLE1);
      db.execSQL(CREATE_TABLE2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    
    public void addData(SQLiteDatabase db, String cat, double am) {
    	ContentValues cv = new ContentValues();
	    ContentValues cv2 = new ContentValues();
	    String key1 = null;
	    String key21 = null;
	    String table1 = null;
	    String table2 = null;
	       	key1 = COLUMN1_NAME;
	    	key21 = COLUMN2_NAME;
	    	table1 = TABLE1_NAME;
	    	table2 = TABLE2_NAME;
		cv.put(key1, cat);
		  // вставляем запись и получаем ее ID
	      long rowID = db.insert(table1, null, cv);
	      Log.d(LOG_TAG, "row category inserted, ID = " + rowID);
	      cv2.put(key21, rowID);
		  cv2.put("amount", am);
	      long rowID2 = db.insert(table2, null, cv2);
	      Log.d(LOG_TAG, "row expense inserted, ID = " + rowID2);
    }
    
    public List<Amount> getAllAmounts() {
        List<Amount> expenseList = new ArrayList<Amount>();
        String table = null;
       	table = TABLE2_NAME;
        String selectQuery = "SELECT  * FROM " + table;
 
        SQLiteDatabase db = this.openDB(this);
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        if (cursor.moveToFirst()) {
            do {
            	Amount am = new Amount();
                am.setID(Integer.parseInt(cursor.getString(0)));
                am.setAmount(cursor.getDouble(2));
                am.setGoal(cursor.getInt(1));
                am.setDate(cursor.getString(cursor.getColumnIndex("date")));
                expenseList.add(am);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return expenseList;
    }
    
    public List<Category> getAllCategory() {
        List<Category> categoryList = new ArrayList<Category>();
        String table = null;
       	table = TABLE1_NAME;
        String selectQuery = "SELECT  * FROM " + table;
 
        SQLiteDatabase db = this.openDB(this);
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        if (cursor.moveToFirst()) {
            do {
            	Category cat = new Category();
                cat.setID(Integer.parseInt(cursor.getString(0)));
                cat.setName(cursor.getString(1));
                categoryList.add(cat);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return categoryList;
    }
    
    public void seeAllDatabase(List<Amount> expam, List<Category> cats) {
    	for(Amount am : expam) {
    		Log.i(LOG_TAG, "ROW " + am.getID() + " WITH GOAL_ID " + am.getGoal() + " AMOUNT " + am.getAmount() + " " + am.getDate());
    	}
    	for(Category cat : cats) {
    		Log.i(LOG_TAG, "ROW " + cat.getID() + " Category HAS NAME " + cat.getName());
    	}
    }
    
    public Cursor dailyExpense() {
    	SQLiteDatabase db = this.openDB(this);
    	String table = TABLE1_NAME + " as T1 inner join " +  TABLE2_NAME + " as T2 on T2." + COLUMN2_NAME + " = T1._id1";
    	String[] columns = { "T1." + COLUMN1_NAME + " as " + COLUMN1_NAME, "sum(T2.amount) as amount"};
    	String selection = "T2.date > ? AND T2.date < ? AND T2.amount < 0";
    	Calendar tmp = Calendar.getInstance();
    	tmp.clear(Calendar.AM_PM);
    	tmp.clear(Calendar.HOUR);
    	tmp.clear(Calendar.HOUR_OF_DAY);
    	tmp.clear(Calendar.MINUTE);
    	tmp.clear(Calendar.SECOND);
    	Calendar now1 = tmp;
    	Log.d(LOG_TAG, now1.toString());
    	String now = DBQUERY_DATE_FORMAT.format(now1.getTime());
    	tmp.add(Calendar.DATE, 1);
    	String tomorrow = DBQUERY_DATE_FORMAT.format(tmp.getTime());
    	Log.d(LOG_TAG, now);
    	Log.d(LOG_TAG, tomorrow);
    	String[] selectionArgs = {now, tomorrow};
    	String groupBy = "T1." + COLUMN1_NAME;
    	Cursor c = db.query(table, columns, selection, selectionArgs, groupBy, null, null);
    	return c;
    }
    
    public Cursor monthIncome() {
    	SQLiteDatabase db = this.openDB(this);
    	String table = TABLE1_NAME + " as T1 inner join " +  TABLE2_NAME + " as T2 on T2." + COLUMN2_NAME + " = T1._id1";
    	String[] columns = { "T1." + COLUMN1_NAME + " as " + COLUMN1_NAME, "sum(T2.amount) as amount"};
    	String selection = "T2.date > ? AND T2.date < ? AND T2.amount > 0";
    	Calendar tmp = Calendar.getInstance();
    	tmp.clear(Calendar.AM_PM);
    	tmp.clear(Calendar.HOUR);
    	tmp.clear(Calendar.DAY_OF_MONTH);
    	tmp.clear(Calendar.WEEK_OF_MONTH);
    	tmp.clear(Calendar.DAY_OF_WEEK);
    	tmp.clear(Calendar.DAY_OF_WEEK_IN_MONTH);
    	tmp.clear(Calendar.HOUR_OF_DAY);
    	tmp.clear(Calendar.MINUTE);
    	tmp.clear(Calendar.SECOND);
    	Calendar now1 = tmp;
    	Log.d(LOG_TAG, now1.toString());
    	String monthBegin = DBQUERY_DATE_FORMAT.format(now1.getTime());
    	tmp.add(Calendar.MONTH, 1);
    	String monthEnd = DBQUERY_DATE_FORMAT.format(tmp.getTime());
    	Log.d(LOG_TAG, monthBegin);
    	Log.d(LOG_TAG, monthEnd);
    	String[] selectionArgs = {monthBegin, monthEnd};
    	String groupBy = "T1." + COLUMN1_NAME;
    	Cursor c = db.query(table, columns, selection, selectionArgs, groupBy, null, null);
    	return c;
    }
    
    public SQLiteDatabase openDB(DBhelper dbh) {
    	SQLiteDatabase dbo = dbh.getWritableDatabase();
    	if (!dbo.isReadOnly()) {
    	    // Enable foreign key constraints
    	    dbo.execSQL("PRAGMA foreign_keys=ON;");
    	}
    	return dbo;
    }
    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }
  }