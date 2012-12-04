package com.pnm.killboarddroid.Main;

import com.pnm.killboarddroid.CustomExpandableListAdapter;
import com.pnm.killboarddroid.Models.Victim;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ExpandableListView;

public class KillActivity extends Activity {
	ExpandableListView list;
	Victim victim;
	CustomExpandableListAdapter adapter;
	
	public KillActivity(Victim vic) {
		this.victim = vic;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.kill_details);
		
		list = (ExpandableListView) findViewById(R.id.expListView);
		
		adapter = new CustomExpandableListAdapter(this, victim);
		list.setAdapter(adapter);
	}
}
