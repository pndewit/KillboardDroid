package com.pnm.killboarddroid.Main;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.pnm.killboarddroid.API.APIInterface;
import com.pnm.killboarddroid.API.APIRequest;
import com.pnm.killboarddroid.CustomListAdapter;
import com.pnm.killboarddroid.XMLfunctions;

public class MainActivity extends Activity {
	ListView list;
	CustomListAdapter adapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		APIInterface i = new APIInterface();
		String killmails = i.getKillMails("Jake Patton");
		
		setXmlText(killmails);
		// TEST DATA: "<row killID=\"23077635\">\n\t<victim characterID=\"91781053\" />\n</row>"
	}
	
	// API call result
	public void setXmlText(String xml) {
		Document doc = XMLfunctions.XMLfromString(xml);
		if(doc == null) {
			Log.e("DEBUG", "No kills? :(");
		} else {
			NodeList kills = doc.getElementsByTagName("row");
			NodeList victims = doc.getElementsByTagName("victim");
			
			list = (ListView) findViewById(R.id.list);
			
			// TODO: ugly workaround to get only the good rows
			ArrayList<Node> first25Kills = new ArrayList<Node>();
			for(int i = 0; first25Kills.size() < 25; i++) {
				Node tmp = kills.item(i).getAttributes().getNamedItem("killID");
				if(tmp == null) {
					continue;
				}
				first25Kills.add(kills.item(i));
			}
			
			// Getting adapter by passing xml data ArrayList
	        adapter = new CustomListAdapter(this, first25Kills);
	        list.setAdapter(adapter);
	 
	        // Click event for single list row
	        /*list.setOnItemClickListener(new OnItemClickListener() {
	            @Override
	            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	            	// TODO: Open item :)
	            }
	        });*/
			
			/*Element e = (Element) victims.item(0);
			String cID = e.getAttribute("characterID");
			
			TextView txt = (TextView) findViewById(R.id.xmlView);
			txt.setText(xml + "\n\nCharacter ID: " + cID + "\nGetting image from: " + "http://image.eveonline.com/Character/" + cID + "_512.jpg");
			
			new RequestBitmap().execute("http://image.eveonline.com/Character/" + cID + "_512.jpg");*/
		}
	}
	
	// Async Request Task
	class RequestTask extends AsyncTask<APIRequest, Void, String> {
		@Override
		protected String doInBackground(APIRequest... uri) {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost post = new HttpPost(uri[0].url);
			
			HttpResponse response;
			String responseString = null;
			try {
				post.setEntity(new UrlEncodedFormEntity(uri[0].params));
				
				response = httpclient.execute(post);
				StatusLine statusLine = response.getStatusLine();
				if(statusLine.getStatusCode() == HttpStatus.SC_OK) {
					ByteArrayOutputStream out = new ByteArrayOutputStream();
					response.getEntity().writeTo(out);
					out.close();
					responseString = out.toString();
				} else {
					// Closes the connection.
					response.getEntity().getContent().close();
					throw new IOException(statusLine.getReasonPhrase());
				}
			} catch(ClientProtocolException e) {
				// TODO Handle problems..
			} catch(IOException e) {
				// TODO Handle problems..
			}
			return responseString;
		}
		
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			setXmlText(result);
		}
	}
	
	class RequestBitmap extends AsyncTask<String, Void, Bitmap> {
		@Override
		protected Bitmap doInBackground(String... uri) {
			Bitmap bitmap = null;
			try {
				bitmap = BitmapFactory.decodeStream((InputStream) new URL(uri[0]).getContent());
			} catch(MalformedURLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch(IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return bitmap;
		}
		
		@Override
		protected void onPostExecute(Bitmap result) {
			super.onPostExecute(result);
			ImageView img = (ImageView) findViewById(R.id.charPic);
			img.setImageBitmap(result);
		}
	}
}