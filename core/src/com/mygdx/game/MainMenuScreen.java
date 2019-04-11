package com.mygdx.game;

import javax.swing.JButton;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
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

public class MainMenuScreen implements Screen{
	
	final RPG game;
	

	OrthographicCamera camera;
	Texture mainImage;
	
	Music soundtrack;
	
	public MainMenuScreen(final RPG rpg) {
		game = rpg;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1280, 720);
		mainImage = new Texture(Gdx.files.internal("mainImage.jpg"));
		soundtrack = Gdx.audio.newMusic(Gdx.files.internal("menuSound.mp3"));
		

	}
	
	public void render(float delta)
	{
		playMusic();
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera.update();
		game.batch.setProjectionMatrix(camera.combined);
		
		
		game.batch.begin();
		game.batch.draw(mainImage, 0, 0, 1280, 720);
		game.font.draw(game.batch, "Hello in RPG", 640, 360);
		game.batch.end();
		
		if(Gdx.input.isKeyPressed(Keys.LEFT)) {       // Начало игры
			game.setScreen(new GameScreen(game));
			dispose();
		}
	}
	public void playMusic() {
		soundtrack.setLooping(true);
		soundtrack.play();
		soundtrack.setVolume(0.8f);
	}
	
	
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
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
	

}
