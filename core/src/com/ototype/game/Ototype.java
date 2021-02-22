package com.ototype.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class Ototype extends Game {
	SpriteBatch batch;
	Texture img;
	BitmapFont titleFont, menuFont,subtitleFont,songSelectFont,lyricFont,menuItemFont,jpFont,headerFont,ingameSongName;
	Music music;
	SaveList saveList;
	Preferences prefs;
	@Override
	public void create () {
		GameConstants constants = new GameConstants();
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("fonts/robotomonolight.ttf"));
		FreeTypeFontGenerator genThin = new FreeTypeFontGenerator(Gdx.files.internal("fonts/RobotoMono-Thin.ttf"));
		FreeTypeFontGenerator genNoto = new FreeTypeFontGenerator(Gdx.files.internal("fonts/robotomonolight.ttf"));
		
		FreeTypeFontParameter param = new FreeTypeFontParameter();
		param.magFilter = Texture.TextureFilter.Linear;
		param.minFilter = Texture.TextureFilter.MipMapLinearLinear;
		param.genMipMaps = true;
		param.size = 96;
		
		
		titleFont = genThin.generateFont(param);
		param.size = constants.menuItemFontSize;
		menuFont = gen.generateFont(param);
		param.size = constants.subtitleFontSize;
		subtitleFont = gen.generateFont(param);
		param.size = constants.songSelectFontSize;
		songSelectFont = gen.generateFont(param);
		param.size = constants.lyricFont;
		lyricFont = gen.generateFont(param);
		param.size = constants.menuItemFontSize;
		menuItemFont = gen.generateFont(param);
		param.size = constants.jpFontSize;
		jpFont = genNoto.generateFont(param);
		param.size = constants.headerFontSize;
		headerFont = genThin.generateFont(param);
		param.size = constants.ingameFontSize;
		ingameSongName = gen.generateFont(param);
		
		
		gen.dispose();
		saveList = new SaveList("saves.json");
		prefs = new Preferences("prefs.json");
		setScreen(new TitleScreen(this));
	}

	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
	
	public void resize() {
	}
}
