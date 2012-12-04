package com.pnm.killboarddroid.Models;

import java.util.LinkedList;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import android.util.Log;

public class Killmail {
	public int id;	// EVE ID
	public Victim victim;
	public LinkedList<Attacker> killers = new LinkedList<Attacker>();
	public String solarSystem;
	public String date;
	
	public Killmail(Element kill) {
		this.id = Integer.parseInt(kill.getAttribute("killID"));
		this.date = kill.getAttribute("killTime");
		this.solarSystem = kill.getAttribute("solarSystemID"); // TODO: <<-- ID!!
		
		NodeList killInfo = kill.getChildNodes();
		
		Element lo = (Element) killInfo.item(2);
		this.victim = new Victim((Element) killInfo.item(0), lo);
		
		Element killers = (Element) killInfo.item(1);
		NodeList attackers = killers.getChildNodes();
		
		Log.i("MODELS_KILLMAIL_DEBUG", "Attackers size: " + attackers.getLength() + "\nSystem: " + this.solarSystem + "\nVictim: " + this.victim.name);
		
		for(int i = 0; i < attackers.getLength(); ++i) {
			Attacker attacker = new Attacker((Element) attackers.item(i));
			
			Log.i("MODELS_KILLMAIL_DEBUG", "" + attackers.item(i));
			this.killers.add(attacker);
		}
	}
}