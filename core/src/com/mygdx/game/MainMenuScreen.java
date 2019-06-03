package com.mygdx.game;

import java.awt.Font;

import javax.swing.JButton;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import Environment.Platform;
import Levels.GlobalWindow;
import Levels.Level1;
import Levels.Level2;

public class MainMenuScreen implements GlobalWindow{
	
	private AssetManager assetManager;
	
	final RPG game;
	float VIRTUAL_WIDTH = 1280f;
	float VIRTUAL_HEIGHT = 720f;
	TextureRegion buttonUpTex;
	TextureRegion buttonDownTex;
	private Texture allSheets;
	TextureRegion buttonOverTex;
	private OrthographicCamera camera;
	private Texture mainImage;
	private TextButton btnPlay, btnExit;
	private TextButton.TextButtonStyle tbs;
	private Table table;
	private Stage stage;
	private Viewport viewport;
	private Music soundtrack;
	private BitmapFont font;
	private TextureRegion[][] imageCollector;
	
	public MainMenuScreen(final RPG rpg) {
		assetManager = new AssetManager();
		game = rpg;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, RPG.WINDOW_WIDTH, RPG.WINDOW_HEIGHT);
		
		//mainImage = new Texture(Gdx.files.internal("mainImage.jpg"));
		//soundtrack = Gdx.audio.newMusic(Gdx.files.internal("menuSound.mp3"));
		

	}

	
	public void render(float delta)
	{
		
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera.update();
		game.batch.setProjectionMatrix(camera.combined);
		
		stage.act(Math.min(Gdx.graphics.getDeltaTime(),1/60f));	
		
		game.batch.begin();
		
		game.batch.draw(mainImage, 0, 0, RPG.WINDOW_WIDTH, RPG.WINDOW_HEIGHT);
		
		game.batch.end();
		stage.draw();
		
		if(btnPlay.isPressed()) {
			game.setScreen(new LevelLoading(game, new Level1(game)));
			dispose();
		}
		if(btnExit.isPressed()) {
			Gdx.app.exit();
		}
	
		
	}
	public void playMusic() {
		soundtrack.setLooping(true);
		soundtrack.setVolume(0.8f);
		soundtrack.play();
	}
	
	public void create() {
		
	}
	
	@Override
	public void show() {
		viewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
		
		mainImage = assetManager.get("mainImage.png", Texture.class);
		allSheets = assetManager.get("buttons.png", Texture.class);
		imageCollector = TextureRegion.split(allSheets, allSheets.getWidth()/2, allSheets.getHeight()/2);
		soundtrack = assetManager.get("mainMusic.mp3", Music.class);
		
		playMusic();
		stage = new Stage(viewport);
		Gdx.input.setInputProcessor(stage);
		
		
		
		
		
		buttonUpTex = imageCollector[0][0];
		buttonOverTex = imageCollector[0][1];
		buttonDownTex = imageCollector[0][0];
		font = assetManager.get("menu_font.fnt", BitmapFont.class);
		
		
		tbs = new TextButton.TextButtonStyle();
		tbs.font = font;
		tbs.up = new TextureRegionDrawable(new TextureRegion(buttonUpTex));
		tbs.over = new TextureRegionDrawable(new TextureRegion(buttonOverTex));
		tbs.down = new TextureRegionDrawable(new TextureRegion(buttonDownTex));
		btnPlay = new TextButton("Play", tbs);
		btnExit = new TextButton("Exit", tbs);
		
		
		table = new Table();
		
		table.row();
		table.add(btnPlay).padTop(10f).colspan(2).width(300).height(200);
		table.row();
		table.add(btnExit).padTop(10f).colspan(2).width(300).height(200);
		
		table.setFillParent(true);
		table.pack();
		
		table.getColor().a = 0f;
		//table.addAction(fadeIn(2f));
		
		
		btnPlay.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x,float y) {
			Gdx.app.log("ssss", "Play");
				};
		});
		
		stage.addActor(table);
		table.addAction(Actions.fadeIn(2f));
		
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
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
		
		mainImage.dispose();
		soundtrack.dispose();
		stage.dispose();
		font.dispose();
	
		
	}


	@Override
	public void managerLoad() {
		assetManager.load("menu_font.fnt", BitmapFont.class);
		assetManager.load("buttons.png", Texture.class);
		assetManager.load("mainImage.png", Texture.class);
		assetManager.load("mainMusic.mp3", Music.class);
		
	}


	@Override
	public boolean isLoaded() {
		return assetManager.update();
		
	}


	@Override
	public float getLoadProgress() {
		// TODO Auto-generated method stub
		return assetManager.getProgress();
	}


	@Override
	public Array<Platform> createEnvironment() {
		// TODO Auto-generated method stub
		return null;
	}
	

}
