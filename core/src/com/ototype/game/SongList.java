package com.ototype.game;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class SongList {
	//the list of all SongInfos.
	private int count, pages, leftovers, currentPage;
	private String[] shownSongs;
	private float[][] shownSongColors;
	private String[] allSongNames;

	private float[][] allSongColors;
	private SongInfo[] allSongInfos;
	public SongList() {
 		//we shim this with a file parser later.
		
		//me from the present : the shim actualy worked.
		//I feel like I am steve woz rn.

		this.currentPage = 0;
		try {
			Reader reader = Files.newBufferedReader(Paths.get("data/songs.json"));
			//literally a code snippet from somewhere else. I don't know how it works exactly.
			List<SongInfo> songInfoList = new Gson().fromJson(reader, new TypeToken<List<SongInfo>>() {}.getType());
			this.count = songInfoList.size();
			this.pages = ((int) Math.ceil(count / 3.0)) -1;
			this.leftovers = count % 3;
			System.out.println(pages + " " + count);
			allSongInfos = new SongInfo[songInfoList.size()];
			allSongNames = new String[songInfoList.size()];
			allSongColors = new float[songInfoList.size()][6];
			songInfoList.toArray(allSongInfos);
			for (int i = 0; i < allSongInfos.length ; i++) {
				allSongNames[i] = allSongInfos[i].getName();
			}
			
			for (int i = 0; i < allSongInfos.length ; i++) {
				allSongColors[i] = allSongInfos[i].getColors();
			}
			

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
 	
 	public String[] getCurrentPage() {
 		
 		if (currentPage < pages || ( currentPage == pages &&leftovers == 0)) {
 			shownSongs = new String[3];
 			shownSongs[0] = this.allSongNames[currentPage*3];
 			shownSongs[1] = this.allSongNames[(currentPage*3)+1];
 			shownSongs[2] = this.allSongNames[(currentPage*3)+2];
 			
 		} if (currentPage == pages && leftovers > 0){
 			shownSongs = new String[leftovers];
 			for (int i = 0 ; i < leftovers ; i++ ) {
 				shownSongs[i] = allSongNames[currentPage*3+i];

 				
 			}
 		}
 		
 		return shownSongs;
 	}
 	
 	
 	public float[][] getCurrentColors(){
 		if (currentPage < pages || ( currentPage == pages &&leftovers == 0)) {
 			shownSongColors = new float[3][6];
 			shownSongColors[0] = this.allSongColors[currentPage*3];
 			shownSongColors[1] = this.allSongColors[(currentPage*3)+1];
 			shownSongColors[2] = this.allSongColors[(currentPage*3)+2];

 			
 		} if (currentPage == pages && leftovers > 0){
 			shownSongs = new String[leftovers];
 			for (int i = 0 ; i < leftovers ; i++ ) {
 				shownSongColors[i] = allSongColors[currentPage*3+i];

 				
 			}
 		}
 		
 		return shownSongColors;
 	}
 	
 	public void increasePage() {
 		if (currentPage < pages) {
 			currentPage++;
 		}
 	}
 	
 	public void decreasePage() {
 		if (currentPage > 0) {
 			currentPage--;
 		}
 	}
 	
 	public int getCurrentPageCount() {
 		return currentPage;
 	}
 	
 	public SongInfo[] getAllSongInfo() {
 		return allSongInfos;
 	}
}
