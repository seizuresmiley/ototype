package com.ototype.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
public class SongSelectScreen extends ScreenAdapter {
	Ototype game;
	private ShapeRenderer shapeRenderer;
	private Stage stage;
	private TextButton button,middleButton;
	private PlayfieldDetails pfDetails;
	private SongList songList;
	private String[] currentSongsInPage;
	private float[][] colorsInPage;
	TextButton firstButton;
	TextButton secondButton;
	TextButton thirdButton;
	
	
	public SongSelectScreen(Ototype game ,PlayfieldDetails pfDetails) {
		this.game = game;
		this.shapeRenderer = new ShapeRenderer();
		this.pfDetails = pfDetails;
		this.songList = new SongList();
		
		
	}
	

	@Override
	public void show() {
		
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		
		 TextButtonStyle textButtonStyle = new TextButtonStyle();
		 TextButtonStyle pageControlStyle = new TextButtonStyle();
		 pageControlStyle.font = game.menuItemFont;
		 textButtonStyle.font = game.menuItemFont;
		 textButtonStyle.fontColor = Color.WHITE;
		 button = new TextButton("back", textButtonStyle);
		 button.setSize(201, 112);
		 button.setPosition(0, 0);
		 
		 
		 button.addListener(new ChangeListener() {
		        @Override
		        public void changed (ChangeEvent event, Actor actor) {
		            game.setScreen(new TitleScreen(game));
		            
		        }

				
		    });
		 
		 
		 middleButton = new TextButton("mode : " + pfDetails.getModeText(), textButtonStyle);
		 
		 middleButton.addListener(new ChangeListener() {
		        @Override
		        public void changed (ChangeEvent event, Actor actor) {
		            pfDetails.cycleModes();
		            middleButton.setText("mode : " + pfDetails.getModeText());

		        }

				
		    });
		 middleButton.setPosition(201,0);
		 middleButton.setSize(881,112);
		 
		 TextButton rightButton = new TextButton("start", textButtonStyle);
		 rightButton.setPosition(1082, 0);
		 rightButton.setSize(201,112);
		 pageControlStyle.fontColor = Color.BLACK;
		 TextButton nextButton = new TextButton("next", pageControlStyle);
		 TextButton prevButton = new TextButton("prev", pageControlStyle);
		 nextButton.setSize(200, 100);
		 prevButton.setSize(200, 100);
		 
		 nextButton.addListener(new ChangeListener() {
		        @Override
		        public void changed (ChangeEvent event, Actor actor) {
		            songList.increasePage();
		            //dispose before rendering new buttons
		            detachActors();
		            refreshSongList();
		            
		        }

				
		    });
		 
		 nextButton.setPosition(1000,470);
		 
		 prevButton.addListener(new ChangeListener() {
		        @Override
		        public void changed (ChangeEvent event, Actor actor) {
		            songList.decreasePage();
		            //same
		            detachActors();
		            refreshSongList();
		            
		        }

				
		    });
		 prevButton.setPosition(80,470);
		 
		 refreshSongList();
		 
		 stage.addActor(prevButton);
		 stage.addActor(nextButton);
		 stage.addActor(button);
		 stage.addActor(middleButton);
		 stage.addActor(rightButton);
		 
		 
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		//left
		shapeRenderer.setColor(0.509f, 0.435f, 0.772f,1);
		shapeRenderer.rect(0,0,201,112);
		//center
		shapeRenderer.setColor(0.819f, 0.588f, 0.733f,1);
		shapeRenderer.rect(201,0,881,112);
		//right
		shapeRenderer.setColor(.75f, .75f, .75f,1);
		shapeRenderer.rect(1082,0,201,112);
		
		//the song list rect begins here.
		//we should check?
		shapeRenderer.setColor(colorsInPage[0][0],colorsInPage[0][1],colorsInPage[0][2],1);
		shapeRenderer.rect(0,352,1280,120);
		
		if(currentSongsInPage.length > 1) {
			shapeRenderer.setColor(colorsInPage[1][0],colorsInPage[1][1],colorsInPage[1][2],1);
			shapeRenderer.rect(0,232,1280,120);
		}
		if(currentSongsInPage.length > 2) {
			shapeRenderer.setColor(colorsInPage[2][0],colorsInPage[2][1],colorsInPage[2][2],1);
			shapeRenderer.rect(0,112,1280,120);
		}
		
		shapeRenderer.end();
		game.batch.begin();
		game.headerFont.setColor(Color.BLACK);
		game.titleFont.setColor(Color.BLACK);
		game.subtitleFont.setColor(Color.BLACK);
		game.headerFont.draw(game.batch,"song select",16, 680);
		
		game.batch.end();
		
		stage.draw();
		
	}
	
	private void detachActors() {
		
		firstButton.remove();
		secondButton.remove();
		thirdButton.remove();
	}
	
	private void refreshSongList() {
		
		// page logic
		this.currentSongsInPage = new String[songList.getCurrentPage().length];
		this.currentSongsInPage = songList.getCurrentPage();
		
		this.colorsInPage = new float[songList.getCurrentPage().length][6];
		this.colorsInPage = songList.getCurrentColors();
		
		
		// deal with buttons (actors here.)
		//possible mem leak? dunno. am dumb. do I need to dispose old buttons? maybe. who knows.
		
		
		TextButtonStyle firstButtonStyle = new TextButtonStyle();
		TextButtonStyle secondButtonStyle = new TextButtonStyle();
		TextButtonStyle thirdButtonStyle = new TextButtonStyle();
		firstButtonStyle.font = game.menuItemFont;
		firstButtonStyle.fontColor = new Color(colorsInPage[0][3],colorsInPage[0][4],colorsInPage[0][5],1);
		firstButton = new TextButton(currentSongsInPage[0], firstButtonStyle);
		secondButton = new TextButton(currentSongsInPage[0], firstButtonStyle);
		thirdButton = new TextButton(currentSongsInPage[0], firstButtonStyle);
		if(currentSongsInPage.length > 1) {
			
			secondButtonStyle.font = game.menuItemFont;
			secondButtonStyle.fontColor = new Color(colorsInPage[1][3],colorsInPage[1][4],colorsInPage[1][5],1);
			secondButton = new TextButton(currentSongsInPage[1], secondButtonStyle);
		}
		
		if(currentSongsInPage.length > 2) {
		
		
		thirdButtonStyle.font = game.menuItemFont;
		thirdButtonStyle.fontColor = new Color(colorsInPage[2][3],colorsInPage[2][4],colorsInPage[2][5],1);
		thirdButton = new TextButton(currentSongsInPage[2], thirdButtonStyle);
		}
		//now we have done that so we can create the buttons and whatnot.
		
		
		
		
		
		firstButton.setPosition(0, 352);
		secondButton.setPosition(0, 232);
		thirdButton.setPosition(0, 112);
		
		firstButton.setSize(1280, 120);
		secondButton.setSize(1280, 120);
		thirdButton.setSize(1280, 120);
		
		
		
		
		
		stage.addActor(firstButton);
		firstButton.addListener(new ChangeListener() {
	        @Override
	        public void changed (ChangeEvent event, Actor actor) {
	            System.out.println(currentSongsInPage[0]);
	            game.setScreen(new DetailedInfoScreen(game,songList.getAllSongInfo()[songList.getCurrentPageCount()*3], pfDetails));
	        }

			
	    });
		
		if(currentSongsInPage.length > 1) {
			stage.addActor(secondButton);
			secondButton.addListener(new ChangeListener() {
		        @Override
		        public void changed (ChangeEvent event, Actor actor) {
		            System.out.println(currentSongsInPage[1]);
		            game.setScreen(new DetailedInfoScreen(game,songList.getAllSongInfo()[(songList.getCurrentPageCount() *3) +1], pfDetails));
		        }

				
		    });
			
		}
		if(currentSongsInPage.length > 2) {
			stage.addActor(thirdButton);
			thirdButton.addListener(new ChangeListener() {
		        @Override
		        public void changed (ChangeEvent event, Actor actor) {
		            System.out.println(currentSongsInPage[2]);
		            game.setScreen(new DetailedInfoScreen(game,songList.getAllSongInfo()[(songList.getCurrentPageCount()*3) +2], pfDetails));

		        }

				
		    });
		}
		
		
		
		
		
		
		
	}

}
