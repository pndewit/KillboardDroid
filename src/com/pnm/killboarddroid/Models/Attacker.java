package com.pnm.killboarddroid.Models;

import org.w3c.dom.Element;
import android.graphics.Bitmap;

public class Attacker{
	public String id; // EVE ID
	public String name;
	public String corp;
	public String alliance;
	
	public String shipID;
	public int damage;
	public Module module;
	public boolean finalBlow;
	
	public Attacker(Element item) {
		this.id = item.getAttributeNode("characterID").getNodeValue();
		this.name = item.getAttributeNode("characterName").getNodeValue();
		this.corp = item.getAttributeNode("corporationName").getNodeValue();
		this.alliance = item.getAttributeNode("allianceName").getNodeValue();
		
		this.shipID = item.getAttributeNode("shipTypeID").getNodeValue();
		this.damage = Integer.parseInt(item.getAttributeNode("damageDone").getNodeValue());
		this.module = new Module(Integer.parseInt(item.getAttributeNode("weaponTypeID").getNodeValue()));
		
		int tmp = Integer.parseInt(item.getAttributeNode("finalBlow").getNodeValue());
		if(tmp == 0)
			this.finalBlow = false;
		else
			this.finalBlow = true;
	}

	public Bitmap getImage(){
		
		return null;
	}
}
