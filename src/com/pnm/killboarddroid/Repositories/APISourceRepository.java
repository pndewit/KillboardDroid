package com.pnm.killboarddroid.Repositories;

import java.util.LinkedList;
import android.database.Cursor;
import com.pnm.killboarddroid.Database.DatabaseHelper;
import com.pnm.killboarddroid.Models.APISource;

public class APISourceRepository {
	private DatabaseHelper	databasehelper;
	
	public APISourceRepository(String name) {
		databasehelper = new DatabaseHelper(name);
	}
	
	public LinkedList<APISource> getSources(String limit) {
		LinkedList<APISource> list = new LinkedList<APISource>();
		
		if(limit == "0") { return list; }
		
		databasehelper.open();
		Cursor charactersCursor = databasehelper.executeSelect("Characters", null, null, null, null, null, null, limit);
		
		String VCode, name, API_key;
		APISource.Type type;
		
		while(charactersCursor.moveToNext()) {
			API_key = charactersCursor.getString(charactersCursor.getColumnIndex("API_key"));
			VCode = charactersCursor.getString(charactersCursor.getColumnIndex("VCode"));
			name = charactersCursor.getString(charactersCursor.getColumnIndex("Name"));
			type = APISource.Type.values()[charactersCursor.getInt(charactersCursor.getColumnIndex("Type"))];
			
			APISource character = new APISource(API_key, VCode, name, type);
			list.add(character);
		}
		
		databasehelper.close();
		return list;
	}
}