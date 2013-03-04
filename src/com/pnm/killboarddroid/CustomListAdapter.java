package com.pnm.killboarddroid;

import java.util.ArrayList;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.pnm.killboarddroid.Main.KillActivity;
import com.pnm.killboarddroid.Main.R;
import com.pnm.killboarddroid.Models.Victim;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomListAdapter extends BaseAdapter {
    private Activity activity;
    private ArrayList<Node> data;
    private static LayoutInflater inflater = null;
    public ImageLoader imageLoader;
    public ArrayList<Node> kill = new ArrayList<Node>();
    
    public CustomListAdapter(Activity a, ArrayList<Node> d) {
        activity = a;
        data = d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader = new ImageLoader(activity.getApplicationContext());
    }
 
    public int getCount() {
        return data.size();
    }
 
    public Object getItem(int position) {
        return position;
    }
    
    public long getItemId(int position) {
        return position;
    }
 
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        
        for(int i = 0; i < data.get(position).getChildNodes().getLength(); i++) {
        	this.kill.add(data.get(position).getChildNodes().item(i));
    	}
    
        if(convertView == null)
            vi = inflater.inflate(R.layout.list_row, null);
        
        TextView title = (TextView) vi.findViewById(R.id.title); // title
        TextView subtitle = (TextView) vi.findViewById(R.id.subtitle); // subtitle
        TextView corporation = (TextView) vi.findViewById(R.id.corporation); // corporation
        ImageView thumb_image = (ImageView) vi.findViewById(R.id.kill_image); // thumb image
        
        Element victim = (Element) data.get(position).getChildNodes().item(1);
        Element loadout = (Element) data.get(position).getChildNodes().item(5);
        
        Victim vic = new Victim(victim, loadout);
 
        // Setting all values in listview
        title.setText(vic.getName());
        subtitle.setText(vic.getId());
        corporation.setText(vic.getCorporation());
        imageLoader.DisplayImage(vic.getImageURL(), thumb_image);
        
        vi.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				KillActivity ka;
				
				if(kill != null) {
					ka = new KillActivity(kill);
				} else {
					Log.e("DEBUG", "No victim info passed!");
					return;
				}
				Intent intent = new Intent(v.getContext(), ka.getClass());
				v.getContext().startActivity(intent);
			}
		});
        
        return vi;
    }
}
