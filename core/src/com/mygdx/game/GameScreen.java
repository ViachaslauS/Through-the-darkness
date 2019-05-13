package com.mygdx.game;

import java.util.function.ToIntFunction;

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
import Levels.Level1;

public class GameScreen implements Screen {
	
	final RPG game;
	
	SpriteBatch batch;
	BitmapFont font;                        //�����
	Texture background;
	
	OrthographicCamera camera;
	Viewport viewport;
	
	OrthographicCamera cameraHUD;			//������ UI
	Viewport viewportHUD;
	
	Hero hero;								//������� �����
	
	private int mapCounter = 0;
	private float centreMapCoord = 0.0f;
	
	private float Time = 0.0f;
	public GameScreen(final RPG game_) {
		game = game_;
		batch = new SpriteBatch();
		
		// ������
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1280, 720);
		viewport = new FillViewport(1280, 720, camera);
		
		//������ HUD
		cameraHUD = new OrthographicCamera();		
		cameraHUD.setToOrtho(false, 1280, 720);
		viewportHUD = new FillViewport(1280, 720, cameraHUD);
		
		//��
		background = new Texture(Gdx.files.internal("Battleground1.png"));
		//�����
		hero = new Hero(new Vector2(150.0f,150.0f),new Vector2(700.0f,00.0f));

		
		//level = new Level1();
	}
	@Override
	public void render(float delta) {
		
		//level.render(camera);
		Time+=delta;
		Gdx.gl.glClearColor(0, 0.1f, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		update(Time);
		camera.position.set(hero.getCoordX(), hero.getCoordY()+350.0f, 0);
		if(camera.position.x < 640.0f) camera.position.x = 640.0f;
		camera.update();
		
		game.batch.setProjectionMatrix(camera.combined);
		
		game.batch.begin();
		
		backgroundDraw();
		
		
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
		// �������� ����������
		game.batch.end();
	}
	
	
	private void backgroundDraw() {
		
		float coord = hero.getCoordX();
		if(coord/1280 > mapCounter)
		{
			mapCounter++;
			centreMapCoord+=1280;
		}
		if(coord/1280 < mapCounter) {
			mapCounter--;
			centreMapCoord-=1280;
		}
		game.batch.draw(background, centreMapCoord-1280, 0,1280,720);
		game.batch.draw(background, centreMapCoord, 0,1280,720);
		game.batch.draw(background, centreMapCoord+1280, 0,1280,720);
	}
	
	private void update(float delta)
	{
		if(hero.getHITPOINT() <= 0.0f) {
			//��� ������ ��-���������
			
		}
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
