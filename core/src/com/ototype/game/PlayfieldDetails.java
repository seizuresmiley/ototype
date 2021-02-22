package com.ototype.game;

public class PlayfieldDetails {
	private int mode;
	public PlayfieldDetails() {
		this.mode = 0;
		// 0 - Overture
		// 1 - Regular
		// 2 - Encore
	}
	
	public String getModeText() {
		switch(mode) {
		case 0 :
			return "overture";
		case 1 :
			return "regular";
		case 2 :
			return "encore";
		}
		
		return "Phantom";
		
		
	}
	
	public void cycleModes() {
		if (mode < 2) {
			mode++;
		} else {
			mode = 0;
		}
	}
	
	public int getMode() {
		return mode;
	}
}
