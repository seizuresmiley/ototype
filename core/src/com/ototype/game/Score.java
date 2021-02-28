package com.ototype.game;

public class Score {
	private int combo,maxCombo,wordCount,score,mode;
	private Chart chart;
	
	public Score(Chart chart,PlayfieldDetails pfd) {
		this.chart = chart;
		combo = 0;		
		score = 0;
		maxCombo = 0;
		wordCount = 0;
		mode = pfd.getMode();
	}
	
	public void addScore(int base) {
		score += base;
	}
	
	public void addCombo(int base) {
		combo += base;
		if (combo > maxCombo) {
			maxCombo = combo;
			System.out.println("max combo is " + maxCombo);
		} else {
			combo += base;
		}
		
		
	}
	public int getMaxCombo() {
		return this.maxCombo;
	}
	
	public int getScore() {
		return this.score;
	}
	
	public int getCombo() {
		return this.combo;
	}
	
	public void resetCombo() {
		if (combo > maxCombo) {
			this.maxCombo = combo;
			
		}
		this.combo = 0;
		
	}
	
	public int getMode() {
		return mode;
	}
	
	public int getWordCount() {
		return wordCount;
	}
}
