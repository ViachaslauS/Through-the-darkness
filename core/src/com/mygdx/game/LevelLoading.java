package com.mygdx.game;

import javax.naming.spi.DirStateFactory.Result;
import javax.xml.transform.Templates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import Levels.GlobalWindow;

public class LevelLoading implements Screen {

	final RPG game;
	//private GameScreen gameScreen;
	private GlobalWindow loadingWindow;
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private Texture currentLoad, fullLoad;
	
	public LevelLoading(RPG game_,GlobalWindow newWindow) {
		game = game_;
		loadingWindow = newWindow;
		currentLoad = new Texture(Gdx.files.internal("currentloadbar.png"));
		fullLoad = new Texture(Gdx.files.internal("fullloadbar.png"));
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1280, 720);
		
		
	}
	@Override
	public void show() {
		// TODO Auto-generated method stub
		loadingWindow.managerLoad();
	}

	@Override
	public void render(float delta) {
	
		Gdx.gl.glClearColor(0, 0, 1, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        batch.begin();
        
        batch.draw(fullLoad, Gdx.graphics.getWidth()/2-fullLoad.getWidth()/2, Gdx.graphics.getHeight()/2-fullLoad.getHeight()/2,fullLoad.getWidth(),fullLoad.getHeight());
        batch.draw(currentLoad,Gdx.graphics.getWidth()/2-currentLoad.getWidth()/2,Gdx.graphics.getHeight()/2-currentLoad.getHeight()/2,loadingWindow.getLoadProgress()*currentLoad.getWidth(),currentLoad.getHeight());
        
        batch.end();
		
        if(loadingWindow.isLoaded()) {
        	game.setScreen(loadingWindow);
        }
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
		
	}

}
