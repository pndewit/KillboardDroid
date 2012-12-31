package com.pnm.killboarddroid.Models;

public class TinyKillmail {
	public int id; // EVE ID
	public String victim;
	public int shipID;
	public String date;

	public TinyKillmail(int id, String name, int shipID, String date) {
		this.id = id;
		this.victim = name;
		this.shipID = shipID;
		this.date = date;
	}
}