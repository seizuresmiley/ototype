package com.ototype.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class TitleScreen extends ScreenAdapter {
	Ototype game;
	Stage stage;
	TextButton button;
	Table table;
	private ShapeRenderer shapeRenderer;
	private TextButton middleButton;
	
	
	public TitleScreen(Ototype game) {
		this.game = game;
		this.shapeRenderer = new ShapeRenderer();
		
	}
	@Override
	public void show() {
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		
		 TextButtonStyle textButtonStyle = new TextButtonStyle();
		
		 textButtonStyle.font = game.menuItemFont;
		 textButtonStyle.fontColor = Color.WHITE;
		 button = new TextButton("start", textButtonStyle);
		 button.setSize(392, 112);
		 button.setPosition(0, 0);
		 
		 
		 button.addListener(new ChangeListener() {
		        @Override
		        public void changed (ChangeEvent event, Actor actor) {
		        	PlayfieldDetails pfDetails = new PlayfieldDetails();
		            game.setScreen(new SongSelectScreen(game,pfDetails));
		        }

				
		    });
		 
		
		 
		 TextButton exitButton = new TextButton("exit", textButtonStyle);
		 exitButton.setPosition(886, 0);
		 exitButton.setSize(394,112);
		 exitButton.addListener(new ChangeListener() {
		        @Override
		        public void changed (ChangeEvent event, Actor actor) {
		            Gdx.app.exit();
		        }

				
		    });
		 
		 middleButton = new TextButton("preferences / help", textButtonStyle);
		 
		 middleButton.addListener(new ChangeListener() {
		        @Override
		        public void changed (ChangeEvent event, Actor actor) {
		        	game.setScreen(new PreferencesScreen(game));
		        }

				
		    });
		 middleButton.setPosition(392,0);
		 middleButton.setSize(494,112);
		 
		 
		 
		 stage.addActor(button);
		 stage.addActor(exitButton);
		 stage.addActor(middleButton);
		 
		 
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.setColor(0.615f, 0.8f, 0.745f,1);
		shapeRenderer.rect(0,0,392,112);
		shapeRenderer.setColor(0.819f, 0.588f, 0.733f,1);
		shapeRenderer.rect(392,0,494,112);
		shapeRenderer.setColor(1, 0.517f, 0.576f,1);
		shapeRenderer.rect(886,0,394,112);
		shapeRenderer.end();
		game.batch.begin();
		
		game.titleFont.setColor(Color.BLACK);
		game.subtitleFont.setColor(Color.BLACK);
		game.titleFont.draw(game.batch,"Ototype",16, 250);
		game.subtitleFont.draw(game.batch,"The Lyrical Typing Game",500, 200);
		
		game.batch.end();
		stage.draw();
		
		
		
		
	}

}
