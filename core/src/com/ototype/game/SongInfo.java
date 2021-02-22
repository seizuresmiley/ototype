package com.ototype.game;

public class SongInfo {
	//class for all info of one song.
	private String songName;
	private String songNameKana;
	private String songArtist;
	private String songArtistKana;
	private String difficulty;
	private String trackPath;
	private String coverPath;
	private String chartPath;
	private String[] colors;
	private String id;
	
	public String getName() {
		//literally exists just for the song select menu.
		return this.songName;
	}
	
	public float[] getColors(){
		//why do we have to do this? I don't know.
		// making colors a float[] would have just worked fine. I'm just severely retarded.
		float[] returnValue = new float[6];
		for(int i = 0 ; i < 6; i++) {
			returnValue[i] = Float.parseFloat(colors[i]);
			
		}
		
		return returnValue;
	}
	
	public String getNameKana() {
		return this.songNameKana;
	}
	
	public String getArtist() {
		return this.songArtist;
	}
	
	public String getArtistKana(){
		return this.songArtistKana;
	}
	public String getCoverPath() {
		return this.coverPath;
	}
	
	public String getDifficulty() {
		return this.difficulty;
	}
	
	public String getAudioPath() {
		return this.trackPath;
	}
	
	public String getChartPath() {
		return this.chartPath;
	}
	
	public String getId() {
		return this.id;
	}
}


