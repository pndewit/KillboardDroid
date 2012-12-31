package com.pnm.killboarddroid.Database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

public class DatabaseHelper {
	private static String	databasePath;
	private SQLiteDatabase	database;
	
	public DatabaseHelper(String name) {
		databasePath = Environment.getExternalStorageDirectory() + "/KillboardDroid/" + name;
	}
	
	public void open() {
		database = SQLiteDatabase.openDatabase(databasePath, null, SQLiteDatabase.OPEN_READWRITE);
	}
	
	public void close() {
		database.close();
	}
	
	public Cursor query(String query) {
		return database.rawQuery(query, new String[0]);
	}
	
	public int executeUpdate(String table, ContentValues values, String whereClause, String[] whereArgs) {
		return database.update(table, values, whereClause, whereArgs);
	}
	
	public long executeInsert(String Table, ContentValues values) {
		return database.insert(Table, null, values);
	}
	
	public int executeDelete(String table, String whereClause, String[] whereArgs) {
		return database.delete(table, whereClause, whereArgs);
	}
	
	public Cursor executeSelect(String Table, String[] Columns, String Selection, String[] SelectionArgs, String GroupBy, String Having, String OrderBy, String Limit) {
		return database.query(Table, Columns, Selection, SelectionArgs, GroupBy, Having, OrderBy, Limit);
	}
}