package com.pnm.killboarddroid.Models;

import java.util.LinkedList;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import android.util.Log;

public class Loadout {
	LinkedList<LoadoutEntry> fitted = new LinkedList<LoadoutEntry>();
	LinkedList<LoadoutEntry> cargo = new LinkedList<LoadoutEntry>();
	LinkedList<LoadoutEntry> dronebay = new LinkedList<LoadoutEntry>();
	LinkedList<LoadoutEntry> implant = new LinkedList<LoadoutEntry>();
	
	public Loadout(Element loadout) {
		if(loadout != null) { // In case of an empty ship/capsule!
			NodeList items = loadout.getChildNodes();
			for(int i = 0; i < items.getLength(); ++i) {
				// Some rare XML bug where loadout rows have weird (almost) empty items in between them
				if(((Node) items.item(i)).getLocalName() == null)
					continue;
				
				NamedNodeMap nnm = items.item(i).getAttributes();
				
				LoadoutEntry le = new LoadoutEntry();
				int typeID = Integer.parseInt(nnm.item(0).getNodeValue());
				
				Log.i("MODELS_LOADOUT_DEBUG", "New item: " + typeID);
				
				Module item = new Module(typeID);
				le.module = item;
				
				le.dropped = Integer.parseInt(nnm.item(2).getNodeValue());
				le.destroyed = Integer.parseInt(nnm.item(3).getNodeValue());
				le.price = 0; // TODO: Change to eve-central price
				
				Log.i("MODELS_LOADOUT_DEBUG", "Item specs:\n- Dropped: " + le.dropped + "\n- Destroyed: " + le.destroyed);
				Log.i("MODELS_LOADOUT_DEBUG", "Item slot: " + nnm.item(1).getNodeValue());
				switch(Integer.parseInt(nnm.item(1).getNodeValue())) {
					case(0):
						fitted.add(le);
						break;
					case(5):
						cargo.add(le);
						break;
					case(87):
						dronebay.add(le);
						break;
					case(89):
						implant.add(le);
						break;
					default:
						break;
				}
				Log.i("MODELS_LOADOUT_DEBUG", "Item added!");
			}
		}
	}
	
	public class LoadoutEntry {
		public Module module;
		public int destroyed;
		public int dropped;
		public float price;
	}
}