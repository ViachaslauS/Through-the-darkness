package com.mygdx.game;

import javax.swing.JButton;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.Array;

import Engine.Platform;
import Levels.GlobalWindow;
import Levels.Level1;

public class MainMenuScreen implements GlobalWindow{
	
	final RPG game;
	

	private OrthographicCamera camera;
	private Texture mainImage;
	private LevelLoading loadScreen;
	
	private AssetManager assetManager;
	Music soundtrack;
	
	public MainMenuScreen(final RPG rpg) {
		game = rpg;
		assetManager = new AssetManager();
		
	}
	
	public void render(float delta)
	{
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera.update();
		game.batch.setProjectionMatrix(camera.combined);
		
		
		game.batch.begin();
		game.batch.draw(mainImage, 0, 0, 1280, 720);
		game.font.draw(game.batch, "Press Enter to start!", 640, 360);
		game.batch.end();
		
		if(Gdx.input.isKeyPressed(Keys.ENTER)) {       // game begin
			game.setScreen(new LevelLoading(game,new Level1(game)));
			dispose();
		}
	}
	private void playMusic() {
		soundtrack.setLooping(true);
		soundtrack.setVolume(0.8f);
		soundtrack.play();
	}
	
	
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1280, 720);
		mainImage = assetManager.get("mainImage.jpg");
		soundtrack = assetManager.get("menuSound.mp3");
		playMusic();
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
		mainImage.dispose();
		soundtrack.dispose();
		
	}

	@Override
	public void managerLoad() {
		// TODO Auto-generated method stub
		assetManager.load("mainImage.jpg",Texture.class);
		assetManager.load("menuSound.mp3",Music.class);
	}

	@Override
	public boolean isLoaded() {
		return assetManager.update();
	}

	@Override
	public float getLoadProgress() {
		
		return assetManager.getProgress();
	}

	@Override
	public Array<Platform> createEnvironment() {
		// TODO Auto-generated method stub
		return null;
	}
	

}
