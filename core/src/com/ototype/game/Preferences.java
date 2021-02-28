package com.ototype.game;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import com.google.gson.Gson;

public class Preferences {
	private int volume;
	private String path;
	private Map<String, Double> prefMap;
	private Gson gson;
	public Preferences(String path) {
		this.gson = new Gson();
		this.path = path;
		this.loadPref();

	}
	
	public void resetPreferences() {
		this.volume = 5;
	}
	
	public int getVolume() {
		return this.volume;
	}
	
	public void increaseVolume() {
		if(this.volume < 10) {
			this.volume += 1;
		}
	}
	
	public void decreaseVolume() {
		if(this.volume > 0) {
			this.volume -= 1;
		}
	}
	
	public void writePref() {
		//TODO : write pref to file...
	}
	
	public void loadPref() {
		
		
		
		
		
		try {
			
			Reader rdr = Files.newBufferedReader(Paths.get("data/" + this.path));
			Map<String, Double> prefs = gson.fromJson(rdr, Map.class);
			//unboxing because Double != double
			double smallD = prefs.get("vol");
			//cast
			this.volume = (int) smallD;
			this.prefMap = prefs;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			this.volume = 5;
			this.savePrefs();
		}
		
	}
	
	public void savePrefs() {
		Writer wrt;
		try {
			wrt = new FileWriter(Paths.get("data/" + this.path).normalize().toString());
			wrt.write("{\"vol\" : %s}".formatted(this.volume));
			wrt.flush();
			wrt.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
