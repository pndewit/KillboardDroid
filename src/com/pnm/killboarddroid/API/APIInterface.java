package com.pnm.killboarddroid.API;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.pnm.killboarddroid.Models.Killmail;
import com.pnm.killboarddroid.XMLfunctions;

public class APIInterface {
	public String getKillMails(String pilotName){
		//String URL = "http://eve-kill.net/?a=idfeed";
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>(2);
		params.add(new BasicNameValuePair("pilotname", pilotName));
		params.add(new BasicNameValuePair("allkills", ""));
		
		//APIRequest request = new APIRequest(URL, params);
		
		APIRequest request = new APIRequest("http://localhost:8080/eve-kill.net.xml?", params);
		
		try {
			String result = new RequestXML().execute(request).get();
			result = XMLfunctions.formatXML(result);
			return result;
		} catch(Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public LinkedList<Killmail> createKillmails(Document xml){
		NodeList mails = xml.getElementsByTagName("result");
		if(mails.getLength() > 0) {
			mails = mails.item(0).getChildNodes().item(0).getChildNodes();
			LinkedList<Killmail> resultingMails = new LinkedList<Killmail>();
			for(int i = 0; i < 10/*mails.getLength()*/; ++i) { // TODO: split
				// Construct Killmail object from XML Node
				resultingMails.add(new Killmail((Element) mails.item(i)));
			}
			return resultingMails;
		}
		return null;
	}
	
	// Parse XML to Document
    protected Document XMLfromString(String xml){
    	Document doc = null;
    	
    	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			InputSource is = new InputSource();
			StringReader sr = new StringReader(xml);
			
			Log.i("API_INTERFACE_DEBUG", "StringReader: " + sr.toString());
			
	        is.setCharacterStream(sr);
	        doc = db.parse(is); 
		} catch (ParserConfigurationException e) {
			System.out.println("XML parse error: " + e.getMessage());
			return null;
		} catch (SAXException e) {
			System.out.println("Wrong XML file structure: " + e.getMessage());
	        return null;
		} catch (IOException e) {
			System.out.println("I/O exeption: " + e.getMessage());
			return null;
		}
        return doc;
	}
	
	// Async Request XML
    class RequestXML extends AsyncTask<APIRequest, Void, String>{
		@Override
        protected String doInBackground(APIRequest... uri) {
            HttpClient httpclient = new DefaultHttpClient();
            String completeUrl = uri[0].url;
            
            for(int i = 0; i < uri[0].params.size(); ++i){
            	completeUrl += "&" + uri[0].params.get(i).getName();
            	try {
					completeUrl += "=" + URLEncoder.encode(uri[0].params.get(i).getValue(), "utf-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
            }
            
            HttpGet request = null;
			try {
				request = new HttpGet(new URI(completeUrl));
			} catch (URISyntaxException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            
            HttpResponse response;
            String responseString = null;
            try {
                response = httpclient.execute(request);
                StatusLine statusLine = response.getStatusLine();
                if(statusLine.getStatusCode() == HttpStatus.SC_OK){
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    response.getEntity().writeTo(out);
                    responseString = out.toString();
                    out.close();
                } else{
                    //Closes the connection.
                    response.getEntity().getContent().close();
                    throw new IOException(statusLine.getReasonPhrase());
                }
            } catch (ClientProtocolException e) {
                //TODO Handle problems..
            } catch (IOException e) {
                //TODO Handle problems..
            }
            return responseString;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        	Document doc = XMLfromString(result);
        	//Node a = doc.getFirstChild();
        	//Node b = doc.getFirstChild();
        	//Node c = doc.getFirstChild();
        	//String d = c.getNodeName();
        	//if(doc.getFirstChild().getFirstChild().getFirstChild().getNodeName().equals("Eve-Kill is down")) {
        		//"Eve-Kill is down, please try again later."
        		
        		//TextView txt = (TextView) findViewById(R.id.xmlView);
        		//txt.setText(xml);
        		
        		//Toast t = new Toast();
        	//} else {
        		/*LinkedList<Killmail> kms = */createKillmails(doc);
        	//}
        }
    }
    
    // Async Request Bitmap
    class RequestBitmap extends AsyncTask<String, Void, Bitmap> {
		@Override
        protected Bitmap doInBackground(String... uri) {
			Bitmap bitmap = null;
			try {
				bitmap = BitmapFactory.decodeStream((InputStream) new URL(uri[0]).getContent());
			} catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap image) {
            super.onPostExecute(image);
            
            //TODO Do something with image
        }
    }
}
