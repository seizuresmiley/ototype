package com.ototype.game;

import java.nio.file.FileSystems;
import java.nio.file.Path;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;


public class DetailedInfoScreen extends ScreenAdapter {
	Ototype game;
	SongInfo songInfo;
	private Stage stage;
	private PlayfieldDetails pfDetails;
	private TextButton button;
	private TextButton middleButton;
	private ShapeRenderer shapeRenderer;
	private Texture coverImage;
	float[] currentColor;
	private String artistKana,songKana;
	private String resultText;
	private Save[] saves;
	
	
	public DetailedInfoScreen(Ototype game, SongInfo songInfo, PlayfieldDetails pfDetails) {
		this.game = game;
		this.songInfo = songInfo;
		this.shapeRenderer = new ShapeRenderer();
		this.pfDetails = pfDetails;
		currentColor = songInfo.getColors();
		
		//get current path
		Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
		String pathString = path.toString();
		System.out.println(pathString + songInfo.getCoverPath());
		coverImage = new Texture(Gdx.files.absolute(pathString + "\\data\\" + songInfo.getCoverPath()));
		game.subtitleFont.setColor(currentColor[3],currentColor[4],currentColor[5],1);
		
		saves = game.saveList.getSaves();
		
		if (saves != null) {
			for (Save sv : saves) {
				if(sv.id.equals(songInfo.getId()) && sv.mode ==pfDetails.getMode()) {
					resultText = "High Score : " + sv.score  + " Max Combo: " + sv.maxCombo;
				}
			}
		}
		
		
		if(resultText == null) {
			resultText = "Not Played";
		}
		
	}
	public void create() {

	}
	@Override
	public void show() {
		
		//get current path
		
		Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
		String pathString = path.toString();
		System.out.println(pathString + songInfo.getCoverPath());
		
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
		            game.setScreen(new SongSelectScreen(game,pfDetails));
		            
		        }

				
		    });
		 
		 
		 middleButton = new TextButton("mode : " + pfDetails.getModeText(), textButtonStyle);
		 
		 middleButton.addListener(new ChangeListener() {
		        @Override
		        public void changed (ChangeEvent event, Actor actor) {
		            pfDetails.cycleModes();
		            middleButton.setText("mode : " + pfDetails.getModeText());
		            resultText = null;
		            if (saves != null) {
		    			for (Save sv : saves) {
		    				if(sv.id.equals(songInfo.getId()) && sv.mode ==pfDetails.getMode()) {
		    					resultText = "High Score : " + sv.score  + " Max Combo: " + sv.maxCombo;
		    				}
		    			}
		    		}
		    		
		    		
		    		if(resultText == null) {
		    			resultText = "Not Played";
		    		}
		        }

				
		    });
		 middleButton.setPosition(201,0);
		 middleButton.setSize(881,112);
		 
		 TextButton rightButton = new TextButton("start", textButtonStyle);
		 rightButton.addListener(new ChangeListener() {
		        @Override
		        public void changed (ChangeEvent event, Actor actor) {
		            game.setScreen(new PlayScreen(game,songInfo,pfDetails));

		        }

				
		    });
		 rightButton.setPosition(1082, 0);
		 rightButton.setSize(201,112);
		 pageControlStyle.fontColor = Color.BLACK;
		

				


		 

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
		shapeRenderer.setColor(0.615f, 0.8f, 0.745f,1);
		shapeRenderer.rect(1082,0,201,112);
		
		shapeRenderer.setColor(currentColor[0],currentColor[1],currentColor[2],1);
		shapeRenderer.rect(0,112,1280,318);
		
		shapeRenderer.end();
		game.batch.begin();
		
		game.headerFont.setColor(Color.BLACK);

		game.headerFont.draw(game.batch,"song selected:",16, 550);
		game.jpFont.setColor(currentColor[3],currentColor[4],currentColor[5],1);
		game.jpFont.draw(game.batch,songInfo.getName(),382, 400);
		game.jpFont.draw(game.batch,songInfo.getArtist(),382, 325);
		game.jpFont.draw(game.batch,"Difficulty : " + songInfo.getDifficulty(),382, 250);
		game.jpFont.draw(game.batch, resultText ,382, 175);
		game.batch.draw(this.coverImage,0,112);
		
		game.batch.end();
		
		stage.draw();
		
	}
	

	

}
