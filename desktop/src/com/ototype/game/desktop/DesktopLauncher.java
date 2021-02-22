package com.ototype.game.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.ototype.game.Ototype;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.resizable = false;
		config.width = 1280;
		config.height = 720;
		config.title = "ototype beta";
		config.addIcon("icon.png", Files.FileType.Internal);
		new LwjglApplication(new Ototype(), config);
	}
}
