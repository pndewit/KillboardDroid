package com.pnm.killboarddroid.Main;

import java.util.ArrayList;

import org.w3c.dom.Node;

import com.pnm.killboarddroid.CustomExpandableListAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ExpandableListView;

public class KillActivity extends Activity {
	ExpandableListView list;
	ArrayList<Node> kill;
	CustomExpandableListAdapter adapter;
	
	public KillActivity(ArrayList<Node> killInfo) {
		this.kill = killInfo;
	}
	
	public KillActivity() {}
	
	public void setBlaat() {
		int a = 1;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.kill_details);
		
		list = (ExpandableListView) findViewById(R.id.expListView);
		
		adapter = new CustomExpandableListAdapter(this, kill);
		list.setAdapter(adapter);
	}
}
