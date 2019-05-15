package Levels;

import java.util.function.ToIntFunction;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.RPG;

import Engine.DamageDeal;
import Engine.Platform;
import Engine.RPGWorld;
import Entities.Hero;
import aiall.AiCustom;

public class Level1 implements GlobalWindow {
	
	final RPG game;
	
	private AssetManager assetManager;
	
	DamageDeal damageMaster;
	
	SpriteBatch batch;
	BitmapFont font;                        //font
	Texture background;
	
	OrthographicCamera camera;
	Viewport viewport;
	
	OrthographicCamera cameraHUD;			//Camera UI
	Viewport viewportHUD;
	
	// Creating a hero
	BodyDef def;
	Hero hero;								//Hero
	AiCustom ai;
	//World world;
	RPGWorld rpgWorld;
	Box2DDebugRenderer debugRenderer;
	
	private int mapCounter = 0; 
	private float centreMapCoord = 0.0f;
	
	private float Time = 0.0f;
	
	
	public Level1(final RPG game_) {
		assetManager = new AssetManager();
		game = game_;
		rpgWorld = new RPGWorld();
		rpgWorld.setEnvironment(createEnvironment());
		//world = new World(new Vector2(0,-100), true);
		debugRenderer = new Box2DDebugRenderer(true, true, true, true, true, true);
		damageMaster = new DamageDeal(rpgWorld);
	}
	@Override
	public void render(float delta) {
		
		//level.render(camera);
		
		Time+=delta;
		Gdx.gl.glClearColor(0, 0.1f, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		update(Time);
		camera.position.set(hero.getCoordX(), /* hero.getCoordY() + */350.0f, 0);
		if(camera.position.x < 640.0f) camera.position.x = 640.0f;
		camera.update();
		
		game.batch.setProjectionMatrix(camera.combined);
		
		game.batch.begin();
		
		backgroundDraw();
		
		
		game.batch.draw(hero.currentFrame, hero.getCoordX(), hero.getCoordY(), hero.getSizeX(), hero.getSizeY());
		game.batch.draw(ai.currentFrame, ai.getCoordX(), ai.getCoordY(), ai.getSizeX(), ai.getSizeY());
		
		game.batch.end();

		cameraHUD.update();
		game.batch.setProjectionMatrix(cameraHUD.combined);
		game.batch.begin();
		game.font.draw(game.batch, "Suka", 100  , 100);
		drawInterface();
		// player interface is here
		game.batch.end();
		//debugRenderer.render(world, viewport.getCamera().combined);
		debugRenderer.render(rpgWorld.world, viewport.getCamera().combined);
	}
	
	private void drawInterface() {
		// TODO Auto-generated method stub
		
	}
	public  void managerLoad() {
		
		assetManager.load("Battleground1.png", Texture.class);
		assetManager.load("Hero.png", Texture.class);
		assetManager.load("sprintSound.wav",Sound.class);
		assetManager.load("dark_skills.png",Texture.class);
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
		
		damageMaster.update();
		Gdx.app.log("Hitpoints of ai and hero",""+ ai.getHITPOINT()+"  "+hero.getHITPOINT());
		if(hero.getHITPOINT() <= 0.0f) {
			//here need to make death of player
			
		}
		hero.update(delta);
		ai.update();
		//world.step(1/500f, 36, 16);
		rpgWorld.world.step(1/1000f, 36, 100);
	}
	
	@Override
	public void dispose () {
		font.dispose();
		batch.dispose();
		background.dispose();
		hero.dispose();
		//world.dispose();
		rpgWorld.dispose();
	}

	@Override
	public void show() {

		batch = new SpriteBatch();

		// camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1280, 720);
		viewport = new FillViewport(1280, 720, camera);
		
		//camera HUD
		cameraHUD = new OrthographicCamera();		
		cameraHUD.setToOrtho(false, 1280, 720);
		viewportHUD = new FillViewport(1280, 720, cameraHUD);
		
		//Background
		background = assetManager.get("Battleground1.png",Texture.class);
		//Player
		hero = new Hero(new Vector2(150.0f,150.0f),new Vector2(700.0f,150.0f),assetManager);
		ai = new AiCustom(new Vector2(150.0f,150.0f) , new Vector2(900.0f,150.0f),2225);
		//hero.setBody(world);
		hero.setBody(rpgWorld);
		ai.setBody(rpgWorld);
		def = new BodyDef();
		def.type = BodyType.DynamicBody;
		//Body entitieBox = world.createBody(def);
		Body entitieBox = rpgWorld.world.createBody(def);
		
		createMapObjects();
		//level = new Level1();
		
	}

	/**
	 *  creating objects on map with physics
	 */
	private void createMapObjects() {
		
		
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
	public boolean isLoaded() {
		return assetManager.update();
	}
	@Override
	public float getLoadProgress() {
		return assetManager.getProgress();
	}
	@Override
	public Array<Platform> createEnvironment() {
		Array<Platform> platforms = new Array<Platform>();
		platforms.add(new Platform(new Vector2(0,-30), new Vector2(10000,100),rpgWorld)); // Earth platform
		
		return platforms;
	}
	
	
}
