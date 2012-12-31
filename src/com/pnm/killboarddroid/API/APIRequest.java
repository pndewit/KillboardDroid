package com.pnm.killboarddroid.API;

import java.util.ArrayList;
import org.apache.http.NameValuePair;

public class APIRequest {
	public String					url;
	public ArrayList<NameValuePair>	params;
	
	public APIRequest(String url, ArrayList<NameValuePair> params) {
		this.url = url;
		this.params = params;
	}
}