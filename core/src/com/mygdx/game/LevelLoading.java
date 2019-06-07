package com.mygdx.game;

import java.util.Map;

import javax.naming.spi.DirStateFactory.Result;
import javax.xml.transform.Templates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import Levels.BaseLevel;
import Levels.GlobalWindow;

/**
 * Abstract factory multithreads level loading
 * @author Slava Stankevich 
 */
public class LevelLoading implements Screen {

	final RPG game;
	//private GameScreen gameScreen;
	private GlobalWindow loadingWindow;
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private TextureRegion currentLoad;
	private TextureRegion fullLoad;
	private TextureRegion[][] loadbars;
	private Texture background;
	public LevelLoading(RPG game_,GlobalWindow newWindow) {
		game = game_;
		loadingWindow = newWindow;
		Texture tempBar = new Texture(Gdx.files.internal("loadingbarNEW.png"));
		loadbars = TextureRegion.split(tempBar, tempBar.getWidth(), tempBar.getHeight()/5);
		currentLoad =loadbars[0][0];
		fullLoad = loadbars[1][0];
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, RPG.WINDOW_WIDTH, RPG.WINDOW_HEIGHT);
		background = new Texture(Gdx.files.internal("loadingBG.jpg"));
		
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
        batch.draw(background, 0, 0);
        batch.draw(fullLoad, Gdx.graphics.getWidth()/2-fullLoad.getRegionWidth()/2 , 100,fullLoad.getRegionWidth(),fullLoad.getRegionHeight());
        batch.draw(currentLoad,Gdx.graphics.getWidth()/2-currentLoad.getRegionWidth()/2+55,100,loadingWindow.getLoadProgress()*currentLoad.getRegionWidth(),currentLoad.getRegionHeight());
        
        batch.end();
		
        if(loadingWindow.isLoaded()) {
        	
        	game.setScreen(loadingWindow);
        }
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		RPG.WINDOW_HEIGHT = height;
		RPG.WINDOW_WIDTH = width;
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
