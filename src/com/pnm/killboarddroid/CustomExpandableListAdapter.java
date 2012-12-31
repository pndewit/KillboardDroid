package com.pnm.killboarddroid;

import java.util.ArrayList;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.pnm.killboarddroid.Main.R;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

public class CustomExpandableListAdapter extends BaseExpandableListAdapter {
	private Activity activity;
	private static LayoutInflater inflater = null;
	private ArrayList<Node> data;
	
	public CustomExpandableListAdapter(Activity a, ArrayList<Node> kill) {
		activity = a;
		this.data = kill;
		inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return null;
	}

	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
		View vi = convertView;
		if(convertView == null)
            vi = inflater.inflate(R.layout.list_row, null);
		
		@SuppressWarnings("unused")
		Element victim = (Element)data.get(0);
		
		return vi;
	}

	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return 0;
	}

	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return null;
	}

	public int getGroupCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return 0;
	}

	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		View vi = convertView;
		if(convertView == null)
            vi = inflater.inflate(R.layout.list_row, null);
		
		@SuppressWarnings("unused")
		Element victim = (Element)data.get(0);
		
		return vi;
	}

	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return false;
	}
}
