package com.devcode.timekeeper;

import java.util.Calendar;
import java.util.Date;

import android.app.Activity;

import com.devcode.timekeeper.data.*;

public class LogActivity extends Activity {
	
	DatabaseHelper DBH = new DatabaseHelper(this);
	
	///<summary>
	///The startLog() method initializes a new log object and triggers the timer to start
	///</summary>
	public void startLog(){
		logItem log = new logItem();
		
		//test data
		log.getTags().add(new tagItem("Uni"));
		log.getTags().add(new tagItem("APT"));
		log.getTags().add(new tagItem("Assignment1"));
		
		intervalItem int1 = new intervalItem();
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		
		int1.setStartTime(cal.getTime());
		cal.add(Calendar.HOUR_OF_DAY, 1);
		int1.setEndTime(cal.getTime());
		
		log.getIntervals().add(int1);
		
		DBH.createLog(log);
		
		
	}
	
	public void pauseLog(){
		
	}
	
	public void endLog(){
		
	}
	
}
