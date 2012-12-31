package com.pnm.killboarddroid.Models;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import org.w3c.dom.Element;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Victim {
	public String id; // EVE ID
	public String name;
	public String corp;
	public String alliance;
	
	// Lost items
	public String shipID;
	public Loadout lostLoadout;
	
	public Victim(Element victim, Element loadout) {
		this.id = victim.getAttribute("characterID");
		this.name = victim.getAttribute("characterName");
		this.corp = victim.getAttribute("corporationName");
		this.alliance = victim.getAttribute("allianceName");
		this.shipID = victim.getAttribute("shipTypeID"); // TODO: <<-- ID!!!
		this.lostLoadout = new Loadout(loadout);
	}

	public Bitmap getImage(){
		Bitmap bitmap = null;
		try {
			bitmap = BitmapFactory.decodeStream((InputStream) new URL("http://image.eveonline.com/Character/" + id + "_512.jpg").getContent());
		} catch(MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch(IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return bitmap;
	}
	
	public String getImageURL(){
		return "http://image.eveonline.com/Character/" + id + "_512.jpg";
	}
	
	public String getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getCorporation() {
		return corp;
	}
	public String getAlliance() {
		return alliance;
	}
	public String getShipID() {
		return shipID;
	}
	public Loadout getLostLoadout() {
		return lostLoadout;
	}
}
