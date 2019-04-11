package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import Entities.Hero;

public class GameScreen implements Screen {
	
	final RPG game;
	
	SpriteBatch batch;
	BitmapFont font;                        //Шрифт
	Texture background;
	
	OrthographicCamera camera;
	Viewport viewport;
	
	OrthographicCamera cameraHUD;			//Камера UI
	Viewport viewportHUD;
	
	Hero hero;								//Главный герой
	
	private float Time = 0.0f;
	public GameScreen(final RPG game_) {
		game = game_;
		batch = new SpriteBatch();
		
		// Камера
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1280, 720);
		viewport = new FillViewport(1280, 720, camera);
		
		//Камера HUD
		cameraHUD = new OrthographicCamera();		
		cameraHUD.setToOrtho(false, 1280, 720);
		viewportHUD = new FillViewport(1280, 720, cameraHUD);
		
		//БГ
		background = new Texture(Gdx.files.internal("Battleground1.png"));
		//Игрок
		hero = new Hero(new Vector2(150.0f,150.0f),new Vector2(100.0f,150.0f));

	}
	@Override
	public void render(float delta) {
		
		
		Time+=delta;
		Gdx.gl.glClearColor(0, 0.1f, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		update(Time);
		camera.position.set(hero.getCoordX(), hero.getCoordY()+350.0f, 0);
		camera.update();
		
		game.batch.setProjectionMatrix(camera.combined);
		
		game.batch.begin();
		
		game.batch.draw(background, 0, 0,1280,720);
		game.batch.draw(hero.currentFrame, hero.getCoordX(), hero.getCoordY()+150, hero.getSizeX(), hero.getSizeY());

		
		game.batch.end();
//		
		
//		if(Gdx.input.isTouched()) {
//			Vector3 vec = new Vector3(Gdx.input.getX(),Gdx.input.getY(),0f);
//			//camera.unproject(vec);
//			camera.position.set(vec);
//		}
		
		
		
		
		cameraHUD.update();
		game.batch.setProjectionMatrix(cameraHUD.combined);
		game.batch.begin();
		game.font.draw(game.batch, "Suka", 100  , 100);
		// Элементы интерфейса
		game.batch.end();
	}
	
	private void update(float delta)
	{
		hero.update(delta);
		
	}
	
	@Override
	public void dispose () {
		font.dispose();
		batch.dispose();
		background.dispose();
		hero.dispose();
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
}
