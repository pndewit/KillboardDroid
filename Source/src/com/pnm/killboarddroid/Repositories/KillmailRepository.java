package com.pnm.killboarddroid.Repositories;

import java.util.LinkedList;
import android.database.Cursor;
import com.pnm.killboarddroid.Database.DatabaseHelper;
import com.pnm.killboarddroid.Models.TinyKillmail;

public class KillmailRepository {
	private DatabaseHelper	databasehelper;
	
	public KillmailRepository(String name) {
		databasehelper = new DatabaseHelper(name);
	}
	
	public LinkedList<TinyKillmail> getAllMails(String limit) {
		LinkedList<TinyKillmail> list = new LinkedList<TinyKillmail>();
		
		if(limit == "0") { return list; }

		databasehelper.open();
		Cursor killmailCursor = databasehelper.query("" +
				"SELECT Killmails.ID, Players.Name, Victims.Ship, Killmails.Timestamp " +
				"FROM Victims, Killmails, Players " +
				"WHERE Killmails.VictimID = Victims.ID AND Players.ID = Victims.ID " +
				"ORDER BY Killmails.Timestamp DESC");
		
		String name, date;
		int shipID, id;
		
		while(killmailCursor.moveToNext()) {
			id = killmailCursor.getInt(killmailCursor.getColumnIndex("Killmails.id"));
			name = killmailCursor.getString(killmailCursor.getColumnIndex("Players.Name"));
			shipID = killmailCursor.getInt(killmailCursor.getColumnIndex("Victims.Ship"));
			date = killmailCursor.getString(killmailCursor.getColumnIndex("Killmails.Timestamp"));
			
			TinyKillmail killmail = new TinyKillmail(id, name, shipID, date);
			list.add(killmail);
		}
		
		databasehelper.close();
		return list;
	}
}