package com.ototype.game;


import java.io.IOException;

import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class PlayScreen extends ScreenAdapter {
	//if all else fails, restore "demented edition".
	//this copy is the "new world" edition with attempts
	//to write a better gameplay screen because that old one
	//was straight up demented.
	
	Ototype game;
	String currentSong , currentPhrase = "",audioPath;
	Music music;
	String[] lyrics;
	char currentKey;
	Gson gson;
	long currentTimePos,startTimePos;
	private Integer[] timestamp;
	GameConstants constants = new GameConstants();
	TypeBuffer tbuf;
	SongInfo songInfo;
	ShapeRenderer shapeRenderer;
	double oldTime;
	long progressbar;
	int timeleft;
	float[] colors;
	public PlayScreen(Ototype game,SongInfo songInfo,PlayfieldDetails pfd) {
		this.songInfo = songInfo;
		gson = new Gson();
		colors = songInfo.getColors();
		game.lyricFont.setColor(Color.BLACK);
		game.lyricFont.getData().markupEnabled = true;
		this.progressbar = 0;
		
		shapeRenderer = new ShapeRenderer();
		
		
		try {
			Reader reader = Files.newBufferedReader(Paths.get("data//" + songInfo.getChartPath()));
			Chart chart = gson.fromJson(reader, Chart.class);
			
			// we hijack the chart loading process here to make a minichart or superchart.
			// a wonky hack but it beats having to rewrite typebuffer.
			
			switch (pfd.getMode()) {
				case 0 : 
					chart.makeOvertureChart();
					break;
				case 2:
					chart.makeEncoreChart();
					break;
				default:
					break;
			}
			
			
			this.tbuf = new TypeBuffer(songInfo,chart,pfd);
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		this.game = game;
		
		
	}
	
	private void returnToSelect() {
		music.stop();
		music.dispose();
		game.setScreen(new SongSelectScreen(game, new PlayfieldDetails()));
	}
	@Override
	public void show() {
		game.ingameSongName.setColor(colors[3],colors[4],colors[5],1);
		oldTime = 0;
		Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
		String pathString = path.toString();
		music = Gdx.audio.newMusic(Gdx.files.absolute(pathString + "\\data\\" + songInfo.getAudioPath()));
		music.setVolume(parseVol());
		music.play();
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		startTimePos = System.currentTimeMillis();
		Gdx.input.setInputProcessor(new InputAdapter() {
			@Override
			public boolean keyTyped(char keyCode) {
				
				currentKey = Character.toLowerCase(keyCode);
				if (keyCode != Input.Keys.BACKSPACE) {
					tbuf.inputCarriage(currentKey);
				}
				
				
				return true;
			} 
			@Override
			public boolean keyDown(int keyCode) {
				if (keyCode == Input.Keys.ESCAPE) {
					
					game.setScreen(new SongSelectScreen(game, new PlayfieldDetails()));
					music.stop();
					music.dispose();
					
				} if (keyCode == Input.Keys.BACKSPACE) {
					tbuf.clearCarriage();
				}
				return true;
			}
			
		});
		
	}
	
	
	private float parseVol() {
		
		
		
		System.out.println(game.prefs.getVolume() / 10.f);
		return game.prefs.getVolume() / 10f;
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		//ooh boy
		//how do we do this again?
		currentTimePos = System.currentTimeMillis() - startTimePos;
		
		if (currentTimePos > tbuf.currentTime()) {
			oldTime = tbuf.currentTime();
			tbuf.advance();
			//holy shit it worked
			progressbar = 0;
			
		}
		if (currentTimePos > tbuf.lastWordTime()) {
			music.stop();
			music.dispose();
			game.setScreen(new ResultScreen(game, tbuf.getScore(), songInfo,new PlayfieldDetails()));
		}
		progressbar = (long) (((tbuf.currentTime() - currentTimePos) * 1280) / (tbuf.currentTime() - oldTime));
		
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.setColor(colors[0], colors[1], colors[2],1);
		shapeRenderer.rect(0,650,1280,70);
		shapeRenderer.setColor(0.819f, 0.588f, 0.733f,1);
		shapeRenderer.rect(824,650,456,70);
		shapeRenderer.setColor(0.819f, 0.588f, 0.733f,1);
		shapeRenderer.rect(0,540,1280,110);
		shapeRenderer.setColor(0.262f, 0.196f, 0.372f,1);
		shapeRenderer.rect(824,540,456,110);
		shapeRenderer.setColor(colors[0], colors[1], colors[2],1);
		shapeRenderer.rect(0,530,progressbar,10);
		shapeRenderer.end();
		
		game.batch.begin();
		int pos = 390;
		for(String str : tbuf.upcomingSentences()) {
			game.lyricFont.draw(game.batch,str,45, pos);
			pos = pos-60;
		}
		game.lyricFont.draw(game.batch,tbuf.currentSentence(),45, 450);
		game.lyricFont.draw(game.batch,">" + tbuf.getCarriage(),45, 510);
		game.menuItemFont.draw(game.batch,"Score : " + Integer.toString(tbuf.getScore().getScore()),45, 605);
		game.menuItemFont.draw(game.batch,"Combo : " + Integer.toString(tbuf.getScore().getCombo()),860, 605);
		game.ingameSongName.draw(game.batch,songInfo.getName(),45, 693);
		game.batch.end();
		
		
		
		
	}

	

}
