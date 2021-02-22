package com.ototype.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class TypeBuffer {
	//typebuffer "new world" edition
	//because screw what I did months ago.
	//seriously.
	//TypeBuffer is probably the most complicated, insane and dumb part of project ototype.
	//There has ben SEVERAL refactors of PlayScreen (the second most janky one) and this class.
	//Seeing this class alone makes me depressed. Everything else is done in 2-3 days yet this bad mother took me
	//3 weeks. I started drinking heavily again, because only vodka and coke can bring myself to work on this
	//piece of pure wota garbage. I stop working on it for a few days, and it comes back biting myself in the rear
	//even with all this documentation. I want this to be done already. I don't want to mess with this garbage anymore. 
	//send help. take me maiyan. take me to the asahi land. --seirent
	//
	//TODO : In order:
	/*
	 * 	+ Implement timed lyrics.
	 * 	+ Implement word separation.
	 * 	+ Implement coloring
	 * 	+ Implement typing
	 * 	+ Implement checking.
	 * 	+ Implement scoring
	 * 	+ Implement the UI
	 * 	+ Typing QoL updates.
	 * */
	
	/*
	 * This is the relationship between TypeBuffer and PlayScreen from now on.
	 * 
	 * Terminologies:
	 * 		- Sentence is a line of lyrics from the chart.json file
	 * 		- Target Word is the word that needs to be typed at that time.
	 * 		- Target is the index number of the word that needs to be typed.
	 * 
	 * TypeBuffer holds all the upcoming "word" logic like what to draw and whatnot.
	 * includes:
	 * 		- Index of current sentence
	 * 		- Index of current word that needs to be typed
	 * 		- Index of current target
	 * 
	 * TypeBuffer will deal with these actions:
	 * 		- Load chart file with info from PlayScreen
	 * 		- Handle input
	 * 			- Accumulating typed letters
	 * 			- Backspace
	 * 			- Carriage Return
	 * 		- Advance through the Sentences
	 * 		- Compare input and current Target Word;
	 * 		- Scoring
	 * 			- Add Score
	 * 			- Add Combo
	 * 			- Reset Combo
	 * 			- Prepare score for Score screen.
	 * 		- Tell when the chart is done.
	 * 
	 * 
	 * 
	 * PlayScreen only tracks timing and draws what typebuffer holds. TypeBuffer will handle all the markup and splitting logic.
	 * PlayScreen only tells TypeBuffer what character is typed, and when to advance to the next sentence/target word.
	 * 
	 * Basically, PlayScreen only handles timing-related logic like calling Sentence advancing methods on TypeBuffer.
	 * PlayScreen will act like a dumb display class only.
	 * 
	 * 
	 * 
	 * As of 1/18/21, PlayScreen is SUPER broke right now. We'll fix it later.
	 * PlayScreen is no longer broken, it's barebones now.
	 */
	private String carriage;
	private boolean carriageEnabled;
	private Chart chart;
	private int senIndex;
	private int targetIndex;
	private int target,senMaxScore,senScore;
	private Score score;
	private int checkIndex; //it's back!
	private PlayfieldDetails pfd;
	
	public TypeBuffer(SongInfo songInfo, Chart chart, PlayfieldDetails pfd) {
		this.carriage = "";
		this.carriageEnabled = true;
		this.chart = chart;
		this.senIndex = 0;
		this.targetIndex = 0;
		this.score = new Score(this.chart,pfd);
		this.checkIndex = 0;
		this.pfd = pfd;
	}
	
	public void inputCarriage(char key) {
		//why have carriage control methods when we can check it here? like, check it everytime
		//we type something?
		if(chart.targets[senIndex][0] != -1 && carriage.length() < this.currentWord().length() && this.carriageEnabled) {
			if(Character.isLetter(key)) {
				if(checkIndex == 0  && key == Character.toLowerCase(currentWord().charAt(checkIndex))) {
					carriage += key;
					checkIndex++;
					compareInput();
				} else if (key == Character.toLowerCase(currentWord().charAt(checkIndex)) ) {
					carriage += key;
					checkIndex++;
					compareInput();
				}
				
			}
		}
		
		
		
	}
	
	public String getCarriage() {
		return this.carriage;
	}
	
	public void backspace() {
		//what is this supposed to do? remove the last letter in the carriage?
		// I thought we didn't want that?
		//or we can implement it, I dunno. might work.
	}
	
	
	public void clearCarriage() {
		//call this to clear the carriage.
		this.carriage = "";
		checkIndex = 0;
	}
	
	private void disableCarriage() {
		//call this to disable the carriage. does not care about the carriage state
		this.carriageEnabled = false;
	}
	
	private void enableCarriage() {
		//call this to disable the carriage. does not care about the carriage state
		this.carriageEnabled = true;
	}
	
	public int getIndex() {
		//call this to get the index of the current sentence index
		return this.senIndex;
	}
	
	public void advance() {
		//advances to the next Sentence
		enableCarriage();
		if (senIndex < chart.lyrics.length-1) {
			if (senScore != senMaxScore && chart.targets[senIndex][0] != -1) {
				this.score.resetCombo();
			}
			this.senIndex++;
			
			this.targetIndex = 0;
			checkIndex = 0;
			this.target = chart.targets[senIndex][targetIndex];
			this.senScore = 0;
			clearCarriage();
		}
	}
	
	public String currentSentence() {
		//returns current Sentence for rendering
		//TODO : Add formatting for highlighted words.
		//this is going to be a PITA.
		String phrase = chart.lyrics[senIndex];
		senMaxScore = chart.targets[senIndex].length;
		String[] precolorWordArray = phrase.split(" ");
		if(chart.targets[senIndex][0] != -1 && this.carriageEnabled) {
			 
			 for (int i : chart.targets[senIndex]) {
							if( i >= chart.targets[senIndex][targetIndex] ) {
								
								if (i == chart.targets[senIndex][targetIndex]) {
									//we only want to first word to have rescinding colors
									precolorWordArray[i] = new StringBuffer(precolorWordArray[i]).insert(checkIndex,"[PINK]").toString() + "[]";
								}else {
									precolorWordArray[i] = "[PINK]" + precolorWordArray[i] + "[]";
								}
							}
			 }
			 
			 
			 
			 
			 return String.join(" ", precolorWordArray);
			
		} else if (chart.targets[senIndex][0] == -1) {
			return "[GRAY]" + phrase + "[]" ;
			
		} else if (!carriageEnabled) {
			//when on last word
			return String.join(" ", precolorWordArray);
		}
		

		return phrase;
	}
	
	public String[] upcomingSentences() {
		
		ArrayList<String> lst = new ArrayList<String>();
		int limitIndex = senIndex+7;
		for(int readingIndex = senIndex + 1 ; readingIndex < limitIndex ; readingIndex++) {
			if(readingIndex <= chart.lyrics.length-1) {
				lst.add("[GRAY]"+ chart.lyrics[readingIndex] + "[]");
			}
			
		}
		
		String[] returnValue = lst.toArray(new String[lst.size()]);
		

		return returnValue;
	}
	
	

	
	public int currentTime() {
		return chart.time[senIndex];
	}
	
	private String currentWord() {
		//used for getting the word that needs to be typed right now.
		//targetIndex points to the index of the target inside the chart
		String[] wordsinSentence;
		wordsinSentence = chart.lyrics[senIndex].split(" ");
		return wordsinSentence[chart.targets[senIndex][targetIndex]].trim().replaceAll("[^A-Za-z]+", "");
	}
	
	private void advanceWord() {
		//advance to the next word, if sentence has many words.  has self checks, so call as much as you like.
		if(targetIndex + 1 == chart.targets[senIndex].length) {
			this.checkIndex = 0;
			this.senScore++;
			disableCarriage();
		}
		
		
		if(targetIndex < chart.targets[senIndex].length && carriageEnabled) {
			this.checkIndex = 0;
			targetIndex++;
			this.senScore++;
		} 
		
		
	}
	
	private void compareInput() {
			//I can't even
			String currentWord = currentWord().toLowerCase().replaceAll("[^a-zA-Z0-9]", "");
			String localCarriage = carriage.toLowerCase().replaceAll("[^a-zA-Z0-9]", "");
			
			if(currentWord.equals(localCarriage)) {

				score.addScore(1);
				score.addCombo(1);
				clearCarriage();
				advanceWord();
			} else if (currentWord.length() == localCarriage.length()){
				clearCarriage();
			}
		 
		 

	}
	

	
	public int lastWordTime() {
		//yee haw
		
		return chart.time[chart.time.length-1];
	}
	
	public Score getScore() {
		return this.score;
	}
	
	
	
}
