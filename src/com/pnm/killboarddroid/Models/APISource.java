package com.pnm.killboarddroid.Models;

public class APISource {
	public enum Type {
		CHARACTER, CORPORATION, ALLIANCE
	}
	
	public String API;
	public String VCode;
	public String name;
	public Type type;
	
	public APISource(String API_key, String VCode, String name, Type type) {
		this.API = API_key;
		this.VCode = VCode;
		this.name = name;
		this.type = type;
	}
}