package com.devcode.timekeeper.data;

import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	static final String dbName="dbTimeKeeper";
	
	static final String tblTag="Tags";
	static final String colTagID="TagID";
	static final String colTagDesc="Description";
	
	static final String tblLog="Logs";
	static final String colLogID="LogID";
	static final String colLogDesc="Description";
	
	static final String tblInterval="Intervals";
	static final String colIntervalID="IntervalID";
	static final String colIntervalStartTime="StartTime";
	static final String colIntervalEndTime="EndTime";
	static final String colIntervalLogID="LogID";
	
	static final String tblLogTag="LogTag";
	static final String colLogTagLogID="LogID";
	static final String colLogTagTagID="TagID";
	
	public DatabaseHelper(Context context) {
		  super(context, dbName, null,1); 
		  }
	
	/*
	 * The onCreate method is executed the first time the app is run on the device.
	 * All the tables and triggers will be create here.
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		
		  db.execSQL("CREATE TABLE "+tblTag+
			" ("+colTagID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "+
				colTagDesc+ " TEXT);");
		  
		  db.execSQL("CREATE TABLE "+tblLog+
		    " ("+colLogID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
		        colLogDesc+ " TEXT);");
		  
		  db.execSQL("CREATE TABLE "+tblInterval+
			" ("+colIntervalID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "+
				colIntervalStartTime+ " TEXT, "+
				colIntervalEndTime+ " TEXT, "+
				colIntervalLogID+ " INTEGER NOT NULL, FOREIGN KEY ("+colIntervalLogID+") REFERENCES "+tblLog+" ("+colLogID+"));");
		  
		  db.execSQL("CREATE TABLE "+tblLogTag+
		    " ("+colLogTagLogID+" INTEGER NOT NULL, FOREIGN KEY ("+colLogTagLogID+") REFERENCES "+tblLog+" ("+colLogID+"), "+
		        colLogTagTagID+ " INTEGER NOT NULL, FOREIGN KEY ("+colLogTagTagID+") REFERENCES "+tblTag+" ("+colTagID+"));");
		  
		  db.execSQL("CREATE TRIGGER fkIntervalIDLogID " +
				    " BEFORE INSERT "+
				    " ON "+tblInterval+
				    
				    " FOR EACH ROW BEGIN"+
				    " SELECT CASE WHEN ((SELECT "+colLogID+" FROM "+tblLog+ 
				    " WHERE "+colLogID+"=new."+colIntervalLogID+" ) IS NULL)"+
				    " THEN RAISE (ABORT,'Foreign Key Violation') END;"+
				    " END;");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

	public int createLog(logItem log){
		int recordsChanged = 0;
		long logID;
		
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();
		
		cv.put(colLogDesc, log.getDescription());
		
		logID = db.insert(tblLog,null,cv );
		cv.clear();
		
		recordsChanged++;
		
		for (intervalItem interval : log.getIntervals()) {
			cv.put(colIntervalLogID, logID);
			cv.put(colIntervalStartTime, interval.getStartTime().toString());
			cv.put(colIntervalEndTime, interval.getEndTime().toString());
			db.insert(tblInterval, null, cv);
			
			recordsChanged++;
			
			cv.clear();
		}
		
		for (tagItem tag : log.getTags()){
			long tagID;
			
			if((tagID = getTagID(tag.getDesc()))==-1){
				cv.put(colTagDesc, tag.getDesc());
				tagID = db.insert(tblTag, null, cv);
				cv.clear();
				
				recordsChanged++;
			}
			
			cv.put(colLogTagLogID, logID);
			cv.put(colLogTagTagID, tagID);
			db.insert(tblLogTag, null, cv);
			
			cv.clear();
			
			recordsChanged++;
			
		}
		
		db.close();
		
		return recordsChanged;
	}
	
	public logItem readLog(int id){
		logItem log = new logItem();
		
		
		
		return log;
	}
	
	public int updateLog(logItem orilog, logItem newlog){
		int recordsChanged = 0;
		
		return recordsChanged;
	}
	
	public int deleteLog(logItem log){
		int recordsChanged = 0;
		
		return recordsChanged;
	}
	
	public int getTagID(String tag){
		int tagID = -1;
		
		return tagID;
	}
}
