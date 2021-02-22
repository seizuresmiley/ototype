package com.ototype.game;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.reflect.TypeToken;

public class SaveList {
	private Save[] saves;
	private String savePath;
	private Gson gson;
	
	public SaveList(String savePath) {
		this.savePath = savePath;
		this.gson = new Gson();
		loadSave();
	}
	
	public void loadSave() {
		//TODO : Read save from file and mount to saves;
		try {
			File saveFile = new File(Paths.get("data/" + this.savePath).normalize().toString());
			if(!saveFile.exists()) {
				
	            try {
					saveFile.createNewFile();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			Reader reader = Files.newBufferedReader(Paths.get("data/" + this.savePath));
			//literally a code snippet from somewhere else. I don't know how it works exactly.
			List<Save> saveList = new Gson().fromJson(reader, new TypeToken<List<Save>>() {}.getType());
			saves = new Save[saveList.size()];
			saveList.toArray(saves);
			reader.close();
			

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NullPointerException e) {
			
		}
		
	}
	
	public Save[] getSaves() {
		return saves;
	}
	
	public void writeSave() {
		//TODO : Write saves to file...
		try {
			Writer wrt = new FileWriter(Paths.get("data/" + this.savePath).normalize().toString());
			gson.toJson(saves, wrt);
			wrt.flush();
			wrt.close();
		} catch (JsonIOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void addSave(Score score, String id,PlayfieldDetails pfd) {

		boolean needsAppend = true;
		
		File workingFile = new File(Paths.get("data/" + this.savePath).normalize().toString());
		if (workingFile.length() == 0) {
			Save workingSave = new Save();
			workingSave.mode = score.getMode();
			workingSave.id = id;
			workingSave.score = score.getScore();
			workingSave.maxCombo = score.getMaxCombo();
			this.saves = new Save[1];
			this.saves[0] = workingSave;
			
			needsAppend = false;
		}
		
		ArrayList<Save> workingList = new ArrayList<Save>(Arrays.asList(saves));
		for(int index = 0 ; index < workingList.size(); index++) {
			if (workingList.get(index).id.equals(id) && workingList.get(index).mode == pfd.getMode()) {
				workingList.get(index).mode = score.getMode();
				workingList.get(index).score = score.getScore();
				workingList.get(index).maxCombo = score.getMaxCombo();
				needsAppend = false;
			}
		}
		
		if(needsAppend) {
			System.out.println("append");
			Save workingSave = new Save();
			workingSave.mode = score.getMode();
			workingSave.id = id;
			workingSave.score = score.getScore();
			workingSave.maxCombo = score.getMaxCombo();
			workingList.add(workingSave);
		}
		
		saves = workingList.toArray(new Save[workingList.size()]);
		System.out.println(saves.length);
		writeSave();
		
		
	}
}
