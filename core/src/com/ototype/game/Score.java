package com.ototype.game;

public class Score {
	private int combo,maxCombo,wordCount,score,missedWords,mode;
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
		if (combo > maxCombo) {
			combo += base;
			maxCombo = combo;
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
