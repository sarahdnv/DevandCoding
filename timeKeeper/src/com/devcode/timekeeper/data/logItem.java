package com.devcode.timekeeper.data;

import java.util.ArrayList;

public class logItem {
	


	private String description;
	
	private ArrayList<tagItem> tags;
	
	private ArrayList<intervalItem> intervals;
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ArrayList<tagItem> getTags() {
		return tags;
	}

	public ArrayList<intervalItem> getIntervals() {
		return intervals;
	}
	
}
