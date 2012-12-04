package com.pnm.killboarddroid;

import com.pnm.killboarddroid.Main.R;
import com.pnm.killboarddroid.Models.Victim;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class CustomExpandableListAdapter extends BaseAdapter {
	private Activity activity;
	private static LayoutInflater inflater = null;
	
	public CustomExpandableListAdapter(Activity a, Victim victim) {
		inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getCount() {
		return 0;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		if(convertView == null)
            vi = inflater.inflate(R.layout.list_row, null);
		
		return vi;
	}

}
