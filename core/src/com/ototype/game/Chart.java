package com.ototype.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//don't screw with this again.
public class Chart {
	
	public String[] lyrics;
	public Integer[] time;
	public Integer[][] targets;
	private int mode;
	

	public Chart(String chartName) {

	
			
			}
	public void makeOvertureChart() {
		for (int index = 0 ; index < targets.length; index++) {
			if(targets[index][0] != -1) {
				int firstWord = targets[index][0];
				targets[index] = new Integer[1];
				targets[index][0] = firstWord;
			}
		}
	}
	
	public void makeEncoreChart() {
		System.out.println("Encore triggered");
		for (int index = 0 ; index < lyrics.length; index++) {
			if(targets[index][0] != -1) {
				String[] splits = lyrics[index].split(" ");
				List<Integer> allWords = new ArrayList<Integer>();
				for(int c = 0; c < splits.length ; c++) {
					allWords.add(c);
					
				}
					
					targets[index] = allWords.toArray(new Integer[allWords.size()]);
					System.out.println(targets[index][0]);
			}
		}
	}
			
}
	

