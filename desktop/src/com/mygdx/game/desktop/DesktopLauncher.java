package com.mygdx.game.desktop;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.RPG;



public class DesktopLauncher {
	
	
	public static final int windowHeight = 720;
	public static final int windowWidth = 1280;
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Through the darkness";
		config.width = windowWidth;
		config.height = windowHeight;
		config.foregroundFPS = 40;
		config.backgroundFPS = 40;
		config.addIcon("Icon.png", FileType.Internal);
		new LwjglApplication(new RPG(), config);
	}
}
