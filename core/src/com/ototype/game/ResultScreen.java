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
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;

public class ResultScreen extends ScreenAdapter {
	Ototype game;
	SongInfo songInfo;
	private Stage stage;
	private PlayfieldDetails pfDetails;
	private TextButton button;
	private TextButton middleButton;
	private ShapeRenderer shapeRenderer;
	private Texture coverImage;
	float[] currentColor;
	Score score;
	
	public ResultScreen(Ototype game,Score score, SongInfo songInfo, PlayfieldDetails pfDetails) {
		this.game = game;
		this.songInfo = songInfo;
		this.shapeRenderer = new ShapeRenderer();
		this.score = score;
		currentColor = songInfo.getColors();
		this.pfDetails = pfDetails;
		
		//get current path
		Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
		String pathString = path.toString();
		System.out.println(pathString + songInfo.getCoverPath());
		coverImage = new Texture(Gdx.files.absolute(pathString + "\\data\\" + songInfo.getCoverPath()));
		game.subtitleFont.setColor(currentColor[3],currentColor[4],currentColor[5],1);
		
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
		 textButtonStyle.font = game.menuItemFont;
		 textButtonStyle.fontColor = Color.WHITE;
		 button = new TextButton("back", textButtonStyle);
		 TextButton comboLabel = new TextButton("Max Combo : " + score.getMaxCombo(), textButtonStyle);
		 TextButton scoreLabel = new TextButton("Score : " + score.getScore(), textButtonStyle);
		 button.setSize(1280, 112);
		 button.setPosition(0, 0);
		 
		 comboLabel.setSize(640, 112);
		 comboLabel.setPosition(0, 112);
		 
		 scoreLabel.setSize(640,112);
		 scoreLabel.setPosition(641, 112);
		 
		 button.addListener(new ChangeListener() {
		        @Override
		        public void changed (ChangeEvent event, Actor actor) {
		            game.setScreen(new SongSelectScreen(game,pfDetails));
		            
		        }

				
		    });
		 stage.addActor(button);
		 stage.addActor(comboLabel);
		 stage.addActor(scoreLabel);
		 game.saveList.addSave(score, songInfo.getId(),pfDetails);
		 
		 
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		//left
		shapeRenderer.setColor(0.509f, 0.435f, 0.772f,1);
		shapeRenderer.rect(0,0,1280,112);
		//score display rects
		shapeRenderer.setColor(0.819f, 0.588f, 0.733f,1);
		shapeRenderer.rect(0,112,640,112);
		shapeRenderer.setColor(0.262f, 0.196f, 0.372f,1);
		shapeRenderer.rect(640,112,640,112);
		
		shapeRenderer.setColor(currentColor[0],currentColor[1],currentColor[2],1);
		shapeRenderer.rect(0,224,1280,318);
		
		shapeRenderer.end();
		game.batch.begin();
		
		game.headerFont.setColor(Color.BLACK);

		game.headerFont.draw(game.batch,"results",16, 680);
		game.jpFont.draw(game.batch,songInfo.getName(),382, 512);
		game.jpFont.draw(game.batch,songInfo.getArtist(),382, 412);
		game.jpFont.draw(game.batch,"Mode : " + pfDetails.getModeText(),382, 312);
		game.batch.draw(this.coverImage,0,224);
		
		game.batch.end();
		
		stage.draw();
		
	}
	

	

}
