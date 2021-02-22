package com.ototype.game;

public class WritableFileManager {
	private String rootPath,saveName,prefsName;
	private Preferences prefs;
	private SaveList saveList;
	public WritableFileManager(String rootPath) {
		this.rootPath = rootPath;
		this.saveName = "saves.json";
		this.prefsName = "prefs.json";
		

	}
}
