package Levels;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.RPG;

import Engine.DamageDeal;
import Engine.RPGWorld;
import Engine.UserInterface;
import Entities.Hero;
import Entities.Buff.BuffType;
import Environment.Platform;
import aiall.AiCustom;

public class BaseLevel implements GlobalWindow, Serializable{

		/**
	 * 
	 */
	private static final long serialVersionUID = -2823973721578840873L;

		/**
		 * standart size
		 */
		public static final float SS = 80f;
	
		private static final float MAP_MAX_HEIGHT = 5000f;

		final RPG game;
		
		protected AssetManager assetManager;
		
		DamageDeal damageMaster;
		
		SpriteBatch batch;
		BitmapFont font;                        //font
		Texture background;
		Texture backgroundSkills;
		Texture gameMenuScreen;
		
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
		
		
		public enum State {
			PAUSE,
			RUN,
			RESUME,
			STOPPED,
			SKILLS_MENU
		}

		private State state = State.RUN;
		
		public BaseLevel(final RPG game_) {
			assetManager = new AssetManager();
			game = game_;
			World.setVelocityThreshold(0.01f);
			rpgWorld = new RPGWorld();
			rpgWorld.setEnvironment(createEnvironment(), createEnemy());
			rpgWorld.world.setAutoClearForces(true);
			rpgWorld.world.setContinuousPhysics(false);
			rpgWorld.world.setWarmStarting(false);
			//world = new World(new Vector2(0,-100), true);
			debugRenderer = new Box2DDebugRenderer(true, true, true, true, true, true);
			damageMaster = new DamageDeal(rpgWorld);
			UI = new UserInterface();
		}
		@Override
		public void render(float delta) {
			
			checkCurrentState();
			switch(state) {
			
			case RUN:
				renderRun(delta);
				break;
			case PAUSE:
				renderPause(delta);
				break;
			case SKILLS_MENU:
				renderSkillMenu(delta);
			default:
				break;
			}
			
			
			
			
			}
/**
 * @return false if state not changed
 */
private boolean checkCurrentState() {
		State newState = null;
		//state = State.RUN;
		if(Gdx.input.isKeyJustPressed(Keys.TAB))
			newState = State.SKILLS_MENU;
		if(Gdx.input.isKeyJustPressed(Keys.ESCAPE))
			newState = State.PAUSE;
		if(newState == null)
			return false;
		if(state != newState) {
			state = newState;
		}
		else {
			state = State.RUN;
		}
		return true;
}
private void renderRun(float delta) {
	
	checkOnAdmin();
	
	Time+=delta;
	Gdx.gl.glClearColor(0, 0.1f, 0, 1);
	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	update(Time);
	camera.position.set(hero.getCoordX(), hero.getCoordY()+80, 0);
	if(camera.position.x < 640.0f) camera.position.x = 640.0f;
	if(camera.position.y < 360 )
		camera.position.y = 360;
	if(camera.position.y > MAP_MAX_HEIGHT-360)
		camera.position.y = MAP_MAX_HEIGHT-360;
	Gdx.app.log("camera coord", ""+ (hero.getCoordY()-260));
	camera.update();
	
	game.batch.setProjectionMatrix(camera.combined);
	
	game.batch.begin();
	
	backgroundDraw();
	for(int i = 0; i < platforms.size;i++) {
		platforms.get(i).update(game.batch);
	}
	game.batch.draw(hero.currentFrame, hero.getCoordX(), hero.getCoordY(), hero.getSizeX(), hero.getSizeY());
	for(int i =0; i<bots.size();i++)
	{
		// Slava CRITICAL SECTION
		// _______________________________
		game.batch.draw(bots.get(i).currentFrame, bots.get(i).getCoordX(), bots.get(i).getCoordY(), bots.get(i).getSizeX(), bots.get(i).getSizeY());
		bots.get(i).barAIDrawing(game.batch);
		for(int j=0;j<bots.get(i).bullets.size();j++)  
			bots.get(i).bullets.get(j).render(game.batch,delta);
		// ________________________________
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


private void renderSkillMenu(float delta) {
	Gdx.gl.glClearColor(0, 0.1f, 0, 1);
	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	
//	if(checkCurrentState())
//		return;
	// if(Gdx.input.isKeyJustPressed(Keys.TAB)) { state = State.RUN; return; }
	game.batch.begin();
	game.batch.draw(gameMenuScreen, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	game.batch.end();
}

private void renderPause(float delta) {
		Gdx.gl.glClearColor(0, 0.1f, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//		if(checkCurrentState())
//			return;
//		  if(Gdx.input.isKeyJustPressed(Keys.ESCAPE)) { state = State.RUN;
//		  setGameState(state); return; }
		 game.batch.begin();
		 game.batch.draw(gameMenuScreen, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		 game.batch.end();
		
		 FileHandle file = new FileHandle("level.dat");
		 file.length();	
		 try(ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(file))) {
			 
		 }
		 catch(Exception ex) {
			 
		 }
}	
	
private void drawInterface() {
			
			UI.draw(game.batch,hero.getEntitieData());
			
		}
// OVERRIDE!
		@Override
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
			//float coordYBG = (camera.position.y - 360f)/MAP_MAX_HEIGHT*720;
			
			game.batch.draw(background, centreMapCoord-1280, camera.position.y-360,1280,720);
			game.batch.draw(background, centreMapCoord, camera.position.y-360,1280,720);
			game.batch.draw(background, centreMapCoord+1280, camera.position.y-360,1280,720);
		}
		
		private void update(float delta)
		{
			if(Gdx.input.isTouched()) {
				Vector3 touchPos = new Vector3(Gdx.input.getX(),Gdx.input.getY(),0);
				camera.unproject(touchPos);
				Gdx.app.log("coords of touch "," "+touchPos);
			}

			damageMaster.update();
			//Gdx.app.log("Hitpoints of ai and hero",""+ ai.getHITPOINT()+"  "+hero.getHITPOINT());
			if(hero.getHITPOINT() <= 0.0f || hero.getCoordY() < -100) {
				hero.getEntitieData().resetHitpoints();
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
						bots.get(i).bullets.get(j).delete();
						bots.get(i).bullets.remove(j);
					}
				
				}
				// Slava CRITICAL SECTION
				//____________________________________________
				//bots.get(i).update(delta);
				//if(bots.get(i).getHITPOINT() <= 0.0f) {
				//	bots.get(i).deleteBot();
				//	bots.remove(i);22
				//}
				bots.get(i).update(delta);
				if(bots.get(i).isDead) {
					hero.getEntitieData().stats.ADDEXP(bots.get(i).level*bots.get(i).level);
					bots.get(i).deleteBot();
					bots.remove(i);
				}
					
			}
			//_________________________________________________
			rpgWorld.world.step(1/1000f, 100,100);
			rpgWorld.world.step(1/1000f, 100, 100);
		
	
//			Gdx.app.log("exp",""+hero.getEntitieData().stats.getExp()+"/"+
//			hero.getEntitieData().stats.getMaxExp()+"  level: "+
//			hero.getEntitieData().stats.getLevel()+"  free stats: "+
//			hero.getEntitieData().stats.getStatsPoints()+"  free skill:"+ 
//			hero.getEntitieData().stats.getSkillPoints());
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
			backgroundSkills = assetManager.get("niceBG.jpg",Texture.class);
			gameMenuScreen = assetManager.get("woodenBG.jpg",Texture.class);
			//Player
			hero = new Hero(new Vector2(150.0f,150.0f),new Vector2(SS*2,20*SS),assetManager);
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
			//hero.getEntitieData().setNewBuff(BuffType.MANA, 50, 15, true);
			//hero.getEntitieData().setNewBuff(BuffType.HITPOINTS, 100f, 1000, true);
			hero.getEntitieData().setNewBuff(BuffType.MANA, 100, 1000, true);

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
		this.state = State.PAUSE;
			
		}

		@Override
		public void resume() {
			this.state = State.RUN;
			
			
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
		@Override
		public Array<Platform> createEnvironment() {
			return platforms;
		}
		
		public ArrayList<AiCustom> bots;
		//Override!!!
		public ArrayList<AiCustom> createEnemy() {
			return bots;
			}	
		
		public void setGameState (State s) {
			this.state = s;
		}
		private void checkOnAdmin() {
			if(Gdx.input.isKeyPressed(Keys.I) && Gdx.input.isKeyPressed(Keys.NUM_9)) {
				hero.getEntitieData().setNewBuff(BuffType.HITPOINTS, 100f, 1000, true);
				hero.getEntitieData().setNewBuff(BuffType.MANA, 100, 1000, true);

			}
			if(Gdx.input.isKeyPressed(Keys.R))
				hero.getEntitieData().resetBuffs();
		}
}
