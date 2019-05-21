package Levels;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.LevelLoading;
import com.mygdx.game.MainMenuScreen;
import com.mygdx.game.RPG;

import Engine.DamageDeal;
import Engine.Platform;
import Engine.RPGWorld;
import Engine.UserInterface;
import Entities.Hero;
import aiall.AiCustom;

public class BaseLevel implements GlobalWindow{

		final RPG game;
		
		protected AssetManager assetManager;
		
		DamageDeal damageMaster;
		
		SpriteBatch batch;
		BitmapFont font;                        //font
		Texture background;
		
		OrthographicCamera camera;
		FitViewport viewport;
		
		OrthographicCamera cameraHUD;			//Camera UI
		FitViewport viewportHUD;
		
		// Creating a hero
		BodyDef def;
		Hero hero;								//Hero
		AiCustom ai;
		//World world;
		RPGWorld rpgWorld;
		Box2DDebugRenderer debugRenderer;
		
		UserInterface UI;
		
		Array<Platform> platforms;
		
		private int mapCounter = 0; 
		private float centreMapCoord = 0.0f;
		
		private float Time = 0.0f;
		
		
		public BaseLevel(final RPG game_) {
			assetManager = new AssetManager();
			game = game_;
			rpgWorld = new RPGWorld();
			rpgWorld.setEnvironment(createEnvironment(), createEnemy());
			//world = new World(new Vector2(0,-100), true);
			debugRenderer = new Box2DDebugRenderer(true, true, true, true, true, true);
			damageMaster = new DamageDeal(rpgWorld);
			UI = new UserInterface();
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
			for(int i =0; i<bots.size();i++)
			{
				// Slava CRITICAL SECTION
				// _______________________________
				game.batch.draw(bots.get(i).currentFrame, bots.get(i).getCoordX(), bots.get(i).getCoordY(), bots.get(i).getSizeX(), bots.get(i).getSizeY());
				bots.get(i).barAIDrawing(game.batch);
				for(int j=0;j<bots.get(i).bullets.size();j++)  
					bots.get(i).bullets.get(j).render(game.batch);
				// ________________________________
			}
			
			for(int i = 0; i < platforms.size;i++) {
				platforms.get(i).draw(game.batch);
			}
			game.batch.end();
			//cameraHUD.position.set(hero.getCoordX(), /* hero.getCoordY() + */350.0f, 0);
			cameraHUD.update();
			game.batch.setProjectionMatrix(cameraHUD.combined);
			game.batch.begin();
			
			drawInterface();
			// player interface is here
			game.batch.end();
			//debugRenderer.render(world, viewport.getCamera().combined);
			debugRenderer.render(rpgWorld.world, viewport.getCamera().combined);
		}
		
		
		private void drawInterface() {
			
			UI.draw(game.batch,hero.getEntitieData());
			
		}
		// OVERRIDE!
		public  void managerLoad() {
			
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
			// Exit from level to menu
			if(Gdx.input.isKeyPressed(Keys.ESCAPE))
				game.setScreen(new LevelLoading(game, new MainMenuScreen(game)));
			
			damageMaster.update();
			//Gdx.app.log("Hitpoints of ai and hero",""+ ai.getHITPOINT()+"  "+hero.getHITPOINT());
			if(hero.getHITPOINT() <= 0.0f) {
				if(hero.death()) {
					
					//dispose();
				}
			}
			else {
				hero.update(delta);	
				//hero.giveDamage(0.1f);
			}
			check_path();
			for(int i = 0; i< bots.size(); i++) {
				
				for(int j = 0; j < bots.get(i).bullets.size(); j++) {
					if(bots.get(i).bullets.get(j).remove) {
					//	bots.get(i).bullets.get(j).delete();
						bots.get(i).bullets.remove(j);
					}
				
				}
				// Slava CRITICAL SECTION
				//____________________________________________
				//bots.get(i).update(delta);
				//if(bots.get(i).getHITPOINT() <= 0.0f) {
				//	bots.get(i).deleteBot();
				//	bots.remove(i);
				//}
				bots.get(i).update(delta);
				if(bots.get(i).isDead) {
					bots.get(i).deleteBot();
					bots.remove(i);
				}
					
			}
			//_________________________________________________
			rpgWorld.world.step(1/1000f, 100, 100);
		}
		
		private void check_path() {
			
			  //System.out.println("hero :" + hero.getCoordX()+ "\n");
			  //System.out.println("ai :" + ai.getCoordX()+ "\n"); 
			 for(int i = 0; i< bots.size(); i++)
				  if(bots.get(i).getCoordX() <=
				  hero.getCoordX()) {bots.get(i).sideView = 1;} else { bots.get(i).sideView = -1;} 
			 
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
			camera.setToOrtho(false, RPG.WINDOW_WIDTH, RPG.WINDOW_HEIGHT);
			viewport = new FitViewport(RPG.WINDOW_WIDTH, RPG.WINDOW_HEIGHT, camera);
			
			//camera HUD
			cameraHUD = new OrthographicCamera();		
			cameraHUD.setToOrtho(false, RPG.WINDOW_WIDTH, RPG.WINDOW_HEIGHT);
			viewportHUD = new FitViewport(RPG.WINDOW_WIDTH, RPG.WINDOW_HEIGHT, cameraHUD);
			
			//Background
			background = assetManager.get("Battleground1.png",Texture.class);
			//Player
			hero = new Hero(new Vector2(150.0f,150.0f),new Vector2(600.0f,150.0f),assetManager);
			//ai = new AiCustom(new Vector2(150.0f,150.0f) , new Vector2(900.0f,150.0f),2225);
			//hero.setBody(world);
			hero.setBody(rpgWorld);
			hero.setFIlter();
			for(int i = 0; i < bots.size(); i++)
				bots.get(i).setBody(rpgWorld);
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
			RPG.WINDOW_HEIGHT = height;
			RPG.WINDOW_WIDTH = width;
			viewport.update(width, height,false);
			viewportHUD.update(width, height,false);
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
		//Override!!!
		public Array<Platform> createEnvironment() {
			return platforms;
			
		}
		
		public ArrayList<AiCustom> bots;
		//Override!!!
		public ArrayList<AiCustom> createEnemy() {
			return bots;
			}	
		
		
}
