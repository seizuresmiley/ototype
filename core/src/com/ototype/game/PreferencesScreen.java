package com.ototype.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;

public class PreferencesScreen extends ScreenAdapter {
	private Ototype game;
	private ShapeRenderer shapeRenderer;
	private Stage stage;
	private TextButton button;

	public PreferencesScreen(Ototype game) {
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
		 button = new TextButton("back", textButtonStyle);
		 button.setSize(1280, 112);
		 button.setPosition(0, 0);
	
		 TextButtonStyle volButtonStyle = new TextButtonStyle();
		 volButtonStyle.font = game.menuItemFont;
		 volButtonStyle.fontColor = Color.BLACK;
		 
		 TextButton addButton = new TextButton("+",volButtonStyle);
		 addButton.setSize(100,100);
		 addButton.setPosition(590, 570);
		
		 TextButton decreaseButton = new TextButton("-",volButtonStyle);
		 decreaseButton.setSize(100, 100);
		 decreaseButton.setPosition(590, 310);
		 
		 
		 button.addListener(new ChangeListener() {
		        @Override
		        public void changed (ChangeEvent event, Actor actor) {
		        	game.prefs.savePrefs();
		            game.setScreen(new TitleScreen(game));
		        }

				
		    });
		 
		 addButton.addListener(new ChangeListener() {
		        @Override
		        public void changed (ChangeEvent event, Actor actor) {
		            game.prefs.increaseVolume();
		        }

				
		    });
		 
		 decreaseButton.addListener(new ChangeListener() {
		        @Override
		        public void changed (ChangeEvent event, Actor actor) {
		            game.prefs.decreaseVolume();
		        }

				
		    });
		 
		 
		 

		 
		 
		 
		 
		 stage.addActor(button);
		 stage.addActor(decreaseButton);
		 stage.addActor(addButton);
			
		 
		 
		 
		 
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.setColor(0.615f, 0.8f, 0.745f,1);
		shapeRenderer.rect(0,0,1280,112);
		shapeRenderer.end();
		game.batch.begin();
		
		game.titleFont.setColor(Color.BLACK);
		game.subtitleFont.setColor(Color.BLACK);
		game.headerFont.setColor(Color.BLACK);
		game.headerFont.draw(game.batch,Integer.toString(game.prefs.getVolume()),625, 480);
		game.subtitleFont.draw(game.batch,"this is the volume.",530, 550);
		
		game.subtitleFont.draw(game.batch,"How to play:",100, 300);
		game.subtitleFont.draw(game.batch,"Type the words in pink to get score. No need to type punctuations.",125, 250);
		game.subtitleFont.draw(game.batch,"Modes:",100, 200);
		game.subtitleFont.draw(game.batch,"Overture : Only 1 word.    Regular : Some words.    Encore : ALL THE WORDS.",125, 150);
		game.batch.end();
		stage.draw();
		
		
		
	}
}
