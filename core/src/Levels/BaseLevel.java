package Levels;

import java.util.ArrayList;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.LevelLoading;
import com.mygdx.game.MainMenuScreen;
import com.mygdx.game.RPG;

import Engine.DamageDeal;
import Engine.RPGWorld;
import Engine.UserInterface;
import Entities.Hero;
import Entities.Buff.BuffType;
import Environment.Platform;
import aiall.AiCustom;

public class BaseLevel implements GlobalWindow{

		/**
		 * standart size
		 */
		public static final float SS = 75f;
	
		private static final float MAP_MAX_HEIGHT = 5000f;

		final RPG game;
		
		protected AssetManager assetManager;
		
		DamageDeal damageMaster;
		
		Sound deathSound;
		
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
		
		Music[] music;
		
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
	}
		for(int j=0;j<hero.bullets.size();j++)  
			hero.bullets.get(j).render(game.batch,delta);
		// ________________________________
	
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
	Gdx.input.setInputProcessor(stageSk);
	stageSk.act(Math.min(Gdx.graphics.getDeltaTime(),1/60f));	
	checkClick();
	game.batch.begin();
	game.batch.draw(gameMenuScreen, 0, 0, 1600, 900);
	game.batch.end();
	stageSk.draw();
}

private void renderPause(float delta) {
		Gdx.gl.glClearColor(0, 0.1f, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.input.setInputProcessor(stage);
		stage.act(Math.min(Gdx.graphics.getDeltaTime(),1/60f));	
		
		 game.batch.begin();
		 game.batch.draw(gameMenuScreen, 0, 0, 1600, 900);
		 game.batch.end();
		 stage.draw();
		 checkKeys();
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
			if(coord/1600 > mapCounter)
			{
				mapCounter++;
				centreMapCoord+=1600;
			}
			if(coord/1600 < mapCounter) {
				mapCounter--;
				centreMapCoord-=1600;
			}
			//float coordYBG = (camera.position.y - 360f)/MAP_MAX_HEIGHT*720;
			
			game.batch.draw(background, centreMapCoord-1600, camera.position.y-450,1600,900);
			game.batch.draw(background, centreMapCoord, camera.position.y-450,1600,900);
			game.batch.draw(background, centreMapCoord+1600, camera.position.y-450,1600,900);
		}
		boolean deathWas = false;
		private void update(float delta)
		{
			
			playMusic();
			
			if(Gdx.input.isTouched()) {
				Vector3 touchPos = new Vector3(Gdx.input.getX(),Gdx.input.getY(),0);
				camera.unproject(touchPos);
				Gdx.app.log("coords of touch "," "+((int)touchPos.x/SS)+" "+((int)touchPos.y/SS));
			}

			damageMaster.update();
			//Gdx.app.log("Hitpoints of ai and hero",""+ ai.getHITPOINT()+"  "+hero.getHITPOINT());
			if(hero.getHITPOINT() <= 0.0f || hero.getCoordY() < -100) {
				hero.getEntitieData().resetHitpoints();
				if(hero.death() && !deathWas) {
					deathSound.play();
					deathWas = true;
					//dispose();
				}
			}
			else {
				for(int i = 0; i< hero.bullets.size(); i++) {
					
				
						if(hero.bullets.get(i).remove) {
							hero.bullets.get(i).delete();
							hero.bullets.remove(i);
						}
					
					
					
				}
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
		
		int currentMusic = 0;
		private void playMusic() {
			if(!music[currentMusic].isPlaying()) {
				currentMusic++;
			if(currentMusic > 3)
				currentMusic = 0;
			music[currentMusic].setVolume(0.3f);
			music[currentMusic].play();
			}
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

			music = new Music[4];
			for(int i=1;i<5;i++) {
				music[i-1] = assetManager.get(""+i+".mp3",Music.class);
			}
			
			// camera
			camera = new OrthographicCamera();
			camera.setToOrtho(false, 1600, 900);
			viewport = new FitViewport(1600, 900, camera);
			
			//camera HUD
			cameraHUD = new OrthographicCamera();		
			cameraHUD.setToOrtho(false, 1600, 900);
			viewportHUD = new FitViewport(1600, 900, cameraHUD);
			
			createPause();
			createSkill();
			
			deathSound = assetManager.get("dead.mp3",Sound.class);
			
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
		
		Stage stage;
		Table table;
		// buttons for menu
		TextureRegion buttonUpTex, buttonDownTex, buttonOverTex;
		TextButton btnCnt, btnExit, btnMenu;
		TextButton.TextButtonStyle tbs;
		TextureRegion[][] imageCollector;
		Texture allSheets;
		
		
		
		public void createPause() {
			stage = new Stage(viewportHUD);
			allSheets = assetManager.get("buttons.png");
			
			imageCollector = TextureRegion.split(allSheets, allSheets.getWidth()/2, allSheets.getHeight()/2);
			buttonUpTex = imageCollector[0][0];
			buttonDownTex = imageCollector[0][0];
			buttonOverTex = imageCollector[0][1]; 
			font = assetManager.get("menu_font.fnt", BitmapFont.class);
			tbs = new TextButton.TextButtonStyle();
			
			
			tbs.font = font;
			tbs.up = new TextureRegionDrawable(new TextureRegion(buttonUpTex));
			tbs.over = new TextureRegionDrawable(new TextureRegion(buttonOverTex));
			tbs.down = new TextureRegionDrawable(new TextureRegion(buttonDownTex));
			btnMenu = new TextButton("Menu", tbs);
			btnExit = new TextButton("Exit", tbs);
			btnCnt = new TextButton("Continue", tbs);
			
			table = new Table();
			
			table.row();
			table.add(btnCnt).width(350).height(300).padRight(900);
			table.row();
			table.add(btnMenu).width(350).height(300).left();
			table.row();
			table.add(btnExit).width(350).height(300).left();
			table.setFillParent(true);
			table.pack();
			
			table.getColor().a = 0f;
			
			stage.addActor(table);
			table.addAction(Actions.fadeIn(2f));
			
			
		}
	
		public void checkKeys() {
			if(btnCnt.isPressed()) {
				state = State.RUN;
			}
			if(btnExit.isPressed()) {
				Gdx.app.exit();
			}
			if(btnMenu.isPressed()) {
				game.setScreen(new LevelLoading(game, new MainMenuScreen(game)));
			}
		}
		
		// for skills
		Stage stageSk;
		Table tableSk;
		
	/*
	 * TextureRegion buttonUpTex, buttonDownTex, buttonOverTex; TextButton btnCnt,
	 * btnExit, btnMenu; TextButton.TextButtonStyle tbs; TextureRegion[][]
	 * imageCollector; Texture allSheets;
	 */
		ImageButton btnTeleport, btnArmor, btnArmor2, btnBuff, btnCooldown, btnDuration, btnHP, btnMagic, btnMana, btnMax, btnRegen, btnTripple, btnVampire;
	
		
		public void createSkill() {
			load();
			stageSk = new Stage(viewportHUD);
			
			
			//Button armor
			Texture armorUp = assetManager.get("armor_unearned.png", Texture.class);
			Texture armorOver = assetManager.get("armor_blocked.png", Texture.class);
			Texture armorDown = assetManager.get("armor_earned.png", Texture.class);
			ImageButton.ImageButtonStyle armorStyle = new ImageButton.ImageButtonStyle();
			armorStyle.imageUp = new TextureRegionDrawable(new TextureRegion(armorUp));
			armorStyle.imageOver = new TextureRegionDrawable(new TextureRegion(armorOver));
			armorStyle.imageDown = new TextureRegionDrawable(new TextureRegion(armorDown));
			
			btnArmor = new ImageButton(armorStyle);
			
			
			
			// button Armor2
			Texture armor2Up = assetManager.get("armor2_unearned.png", Texture.class);
			Texture armor2Over = assetManager.get("armor2_blocked.png", Texture.class);
			Texture armor2Down = assetManager.get("armor2_earned.png", Texture.class);
			ImageButton.ImageButtonStyle armor2Style = new ImageButton.ImageButtonStyle();
			armor2Style.imageUp = new TextureRegionDrawable(new TextureRegion(armor2Up));
			armor2Style.imageOver = new TextureRegionDrawable(new TextureRegion(armor2Over));
			armor2Style.imageDown = new TextureRegionDrawable(new TextureRegion(armor2Down));
			
			btnArmor2 = new ImageButton(armor2Style);
			
			//Button buff
			Texture buffUp = assetManager.get("buff_unearned.png", Texture.class);
			Texture buffOver = assetManager.get("buff_blocked.png", Texture.class);
			Texture buffDown = assetManager.get("buff_earned.png", Texture.class);
			ImageButton.ImageButtonStyle buffStyle = new ImageButton.ImageButtonStyle();
			buffStyle.imageUp = new TextureRegionDrawable(new TextureRegion(buffUp));
			buffStyle.imageOver = new TextureRegionDrawable(new TextureRegion(buffOver));
			buffStyle.imageDown = new TextureRegionDrawable(new TextureRegion(buffDown));
			
			btnBuff = new ImageButton(buffStyle);
			
			// Button cooldown 
			Texture cooldownUp = assetManager.get("cooldown_unearned.png", Texture.class);
			Texture cooldownOver = assetManager.get("cooldown_blocked.png", Texture.class);
			Texture cooldownDown = assetManager.get("cooldown_earned.png", Texture.class);
			ImageButton.ImageButtonStyle cooldownStyle = new ImageButton.ImageButtonStyle();
			cooldownStyle.imageUp = new TextureRegionDrawable(new TextureRegion(cooldownUp));
			cooldownStyle.imageOver = new TextureRegionDrawable(new TextureRegion(cooldownOver));
			cooldownStyle.imageDown = new TextureRegionDrawable(new TextureRegion(cooldownDown));
			
			btnCooldown = new ImageButton(cooldownStyle);
			
			
			// Button duration
			Texture durationUp = assetManager.get("duration_unearned.png", Texture.class);
			Texture durationOver = assetManager.get("duration_blocked.png", Texture.class);
			Texture durationDown = assetManager.get("duration_earned.png", Texture.class);
			ImageButton.ImageButtonStyle durationStyle = new ImageButton.ImageButtonStyle();
			durationStyle.imageUp = new TextureRegionDrawable(new TextureRegion(durationUp));
			durationStyle.imageOver = new TextureRegionDrawable(new TextureRegion(durationOver));
			durationStyle.imageDown = new TextureRegionDrawable(new TextureRegion(durationDown));
			
			btnDuration = new ImageButton(durationStyle);
			
			// button HP
			Texture hpUp = assetManager.get("HP_unearned.png", Texture.class);
			Texture hpDown = assetManager.get("HP_earned.png", Texture.class);
			ImageButton.ImageButtonStyle hpStyle = new ImageButton.ImageButtonStyle();
			hpStyle.imageUp = new TextureRegionDrawable(new TextureRegion(hpUp));
			hpStyle.imageDown = new TextureRegionDrawable(new TextureRegion(hpDown));
			
			btnHP = new ImageButton(hpStyle);
			
			
			// Button Magic
			Texture magicUp = assetManager.get("magic_unearned.png", Texture.class);
			Texture magicOver = assetManager.get("magic_blocked.png", Texture.class);
			Texture magicDown = assetManager.get("magic_earned.png", Texture.class);
			ImageButton.ImageButtonStyle magicStyle = new ImageButton.ImageButtonStyle();
			magicStyle.imageUp = new TextureRegionDrawable(new TextureRegion(magicUp));
			magicStyle.imageOver = new TextureRegionDrawable(new TextureRegion(magicOver));
			magicStyle.imageDown = new TextureRegionDrawable(new TextureRegion(magicDown));
			
			btnMagic = new ImageButton(magicStyle);
			
			// Button Mana
			
			Texture manaUp = assetManager.get("MANA_unearned.png", Texture.class);
			Texture manaDown = assetManager.get("MANA_earned.png", Texture.class);
			ImageButton.ImageButtonStyle manaStyle = new ImageButton.ImageButtonStyle();
			manaStyle.imageUp = new TextureRegionDrawable(new TextureRegion(manaUp));
			manaStyle.imageDown = new TextureRegionDrawable(new TextureRegion(manaDown));
			
			btnMana = new ImageButton(manaStyle);
			
			// Button max
			Texture maxUp = assetManager.get("max_unearned.png", Texture.class);
			Texture maxOver = assetManager.get("max_blocked.png", Texture.class);
			Texture maxDown = assetManager.get("max_earned.png", Texture.class);
			ImageButton.ImageButtonStyle maxStyle = new ImageButton.ImageButtonStyle();
			maxStyle.imageUp = new TextureRegionDrawable(new TextureRegion(maxUp));
			maxStyle.imageOver = new TextureRegionDrawable(new TextureRegion(maxOver));
			maxStyle.imageDown = new TextureRegionDrawable(new TextureRegion(maxDown));
			
			btnMax = new ImageButton(maxStyle);
			
			// Button Regen
			Texture regenUp = assetManager.get("regen_unearned.png", Texture.class);
			Texture regenOver = assetManager.get("regen_blocked.png", Texture.class);
			Texture regenDown = assetManager.get("regen_earned.png", Texture.class);
			ImageButton.ImageButtonStyle regenStyle = new ImageButton.ImageButtonStyle();
			regenStyle.imageUp = new TextureRegionDrawable(new TextureRegion(regenUp));
			regenStyle.imageOver = new TextureRegionDrawable(new TextureRegion(regenOver));
			regenStyle.imageDown = new TextureRegionDrawable(new TextureRegion(regenDown));
			
			btnRegen = new ImageButton(regenStyle);
			
			
			// Button Teleport
			Texture teleportUp = assetManager.get("teleport_unearned.png", Texture.class);
			Texture teleportOver = assetManager.get("teleport_blocked.png", Texture.class);
			Texture teleportDown = assetManager.get("teleport_earned.png", Texture.class);
			ImageButton.ImageButtonStyle teleportStyle = new ImageButton.ImageButtonStyle();
			teleportStyle.imageUp = new TextureRegionDrawable(new TextureRegion(teleportUp));
			teleportStyle.imageOver = new TextureRegionDrawable(new TextureRegion(teleportOver));
			teleportStyle.imageDown = new TextureRegionDrawable(new TextureRegion(teleportDown));
			
			btnTeleport = new ImageButton(teleportStyle);
			
			
			// Button Tripple
			Texture trippleUp = assetManager.get("tripple_unearned.png", Texture.class);
			Texture trippleOver = assetManager.get("tripple_blocked.png", Texture.class);
			Texture trippleDown = assetManager.get("tripple_earned.png", Texture.class);
			ImageButton.ImageButtonStyle trippleStyle = new ImageButton.ImageButtonStyle();
			trippleStyle.imageUp = new TextureRegionDrawable(new TextureRegion(trippleUp));
			trippleStyle.imageOver = new TextureRegionDrawable(new TextureRegion(trippleOver));
			trippleStyle.imageDown = new TextureRegionDrawable(new TextureRegion(trippleDown));
			
			btnTripple = new ImageButton(trippleStyle);
			
			//Button Vampire
			Texture vampireUp = assetManager.get("vampire_unearned.png", Texture.class);
			Texture vampireOver = assetManager.get("vampire_blocked.png", Texture.class);
			Texture vampireDown = assetManager.get("vampire_earned.png", Texture.class);
			ImageButton.ImageButtonStyle vampireStyle = new ImageButton.ImageButtonStyle();
			vampireStyle.imageUp = new TextureRegionDrawable(new TextureRegion(vampireUp));
			vampireStyle.imageOver = new TextureRegionDrawable(new TextureRegion(vampireOver));
			vampireStyle.imageDown = new TextureRegionDrawable(new TextureRegion(vampireDown));
			
			btnVampire = new ImageButton(vampireStyle);
			
			
			
			
			
			tableSk = new Table();
			
			//table.row();
			tableSk.add(btnTeleport).width(100).height(100);
			tableSk.add(btnHP).width(100).height(100).spaceLeft(50);
			tableSk.add(btnArmor).width(100).height(100).spaceLeft(100);
			tableSk.add(btnRegen).width(100).height(100).spaceLeft(50);
			tableSk.row();
			tableSk.add(btnTripple).width(100).height(100);
			tableSk.add(btnMana).width(100).height(100).spaceLeft(50);
			tableSk.add(btnArmor2).width(100).height(100).spaceLeft(100);
			tableSk.add(btnDuration).width(100).height(100).spaceLeft(50);
			tableSk.row();
			tableSk.add(btnMagic).width(100).height(100);
			tableSk.add(btnBuff).width(100).height(100).spaceLeft(50);
			tableSk.add(btnVampire).width(100).height(100).spaceLeft(100);
			tableSk.add(btnCooldown).width(100).height(100).spaceLeft(50);
			
			
			tableSk.setFillParent(true);
			tableSk.pack();
			
			tableSk.getColor().a = 0f;
			
			stageSk.addActor(tableSk);
			tableSk.addAction(Actions.fadeIn(2f));
			
		}
		public void load() {
		/*
		 * assetManager.load("armor_blocked.png", Texture.class);
		 * assetManager.load("armor_earned.png", Texture.class);
		 * assetManager.load("armor_unearned.png", Texture.class);
		 * assetManager.load("armor2_blocked.png", Texture.class);
		 * assetManager.load("armor2_earned.png", Texture.class);
		 * assetManager.load("armor2_unearned.png", Texture.class);
		 * assetManager.load("buff_blocked.png", Texture.class);
		 * assetManager.load("buff_earneded.png", Texture.class);
		 * assetManager.load("buff_unearned.png", Texture.class);
		 * assetManager.load("cooldown_blocked.png", Texture.class);
		 * assetManager.load("cooldown_earned.png", Texture.class);
		 * assetManager.load("cooldown_unearned.png", Texture.class);
		 * assetManager.load("duration_blocked.png", Texture.class);
		 * assetManager.load("duration_earned.png", Texture.class);
		 * assetManager.load("duration_unearned.png", Texture.class);
		 * assetManager.load("HP_earned.png", Texture.class);
		 * assetManager.load("HP_unearned.png", Texture.class);
		 * assetManager.load("magic_blocked.png", Texture.class);
		 * assetManager.load("magic_earned.png", Texture.class);
		 * assetManager.load("magic_unearned.png", Texture.class);
		 * assetManager.load("MANA_earned.png", Texture.class);
		 * assetManager.load("MANA_unearned.png", Texture.class);
		 * assetManager.load("max_blocked.png", Texture.class);
		 * assetManager.load("max_earned.png", Texture.class);
		 * assetManager.load("max_unearned.png", Texture.class);
		 * assetManager.load("regen_blocked.png", Texture.class);
		 * assetManager.load("regen_earned.png", Texture.class);
		 * assetManager.load("regen_unearned.png", Texture.class);
		 * assetManager.load("telepor_blocked.png", Texture.class);
		 * assetManager.load("telepor_earned.png", Texture.class);
		 * assetManager.load("telepor_unearned.png", Texture.class);
		 * assetManager.load("tripple_blocked.png", Texture.class);
		 * assetManager.load("tripple_earned.png", Texture.class);
		 * assetManager.load("tripple_unearned.png", Texture.class);
		 * assetManager.load("vampire_blocked.png", Texture.class);
		 * assetManager.load("vampire_earned.png", Texture.class);
		 * assetManager.load("vampire_unearned.png", Texture.class);
		 */
			
			
		}
 public void		checkClick() {
	 if(btnArmor.isPressed()) {
		 Texture armOver = assetManager.get("armor_earned.png", Texture.class);
		 Texture armDown = assetManager.get("armor_earned.png", Texture.class);
		 Texture armUp = assetManager.get("armor_earned.png", Texture.class);	
		 ImageButton.ImageButtonStyle armst = new ImageButton.ImageButtonStyle();
			armst.imageOver = new TextureRegionDrawable(new TextureRegion(armOver));
			armst.imageDown = new TextureRegionDrawable(new TextureRegion(armDown));
			armst.imageUp = new TextureRegionDrawable(new TextureRegion(armUp));
			btnArmor.setStyle(armst);
		}
	 if(btnArmor2.isPressed()) {
		 Texture armOver = assetManager.get("armor2_earned.png", Texture.class);
		 Texture armDown = assetManager.get("armor2_earned.png", Texture.class);
		 Texture armUp = assetManager.get("armor2_earned.png", Texture.class);	
		 ImageButton.ImageButtonStyle armst = new ImageButton.ImageButtonStyle();
			armst.imageOver = new TextureRegionDrawable(new TextureRegion(armOver));
			armst.imageDown = new TextureRegionDrawable(new TextureRegion(armDown));
			armst.imageUp = new TextureRegionDrawable(new TextureRegion(armUp));
			btnArmor2.setStyle(armst);
		}
	 if(btnBuff.isPressed()) {
		 Texture armOver = assetManager.get("buff_earned.png", Texture.class);
		 Texture armDown = assetManager.get("buff_earned.png", Texture.class);
		 Texture armUp = assetManager.get("buff_earned.png", Texture.class);		
		 ImageButton.ImageButtonStyle armst = new ImageButton.ImageButtonStyle();
			armst.imageOver = new TextureRegionDrawable(new TextureRegion(armOver));
			armst.imageDown = new TextureRegionDrawable(new TextureRegion(armDown));
			armst.imageUp = new TextureRegionDrawable(new TextureRegion(armUp));
			btnBuff.setStyle(armst);
		}
	 if(btnCooldown.isPressed()) {
		 Texture armOver = assetManager.get("cooldown_earned.png", Texture.class);
		 Texture armDown = assetManager.get("cooldown_earned.png", Texture.class);
		 Texture armUp = assetManager.get("cooldown_earned.png", Texture.class);		
		 ImageButton.ImageButtonStyle armst = new ImageButton.ImageButtonStyle();
			armst.imageOver = new TextureRegionDrawable(new TextureRegion(armOver));
			armst.imageDown = new TextureRegionDrawable(new TextureRegion(armDown));
			armst.imageUp = new TextureRegionDrawable(new TextureRegion(armUp));
			btnCooldown.setStyle(armst);
		}
	 if(btnDuration.isPressed()) {
		 Texture armOver = assetManager.get("duration_earned.png", Texture.class);
		 Texture armDown = assetManager.get("duration_earned.png", Texture.class);
		 Texture armUp = assetManager.get("duration_earned.png", Texture.class);		
		 ImageButton.ImageButtonStyle armst = new ImageButton.ImageButtonStyle();
			armst.imageOver = new TextureRegionDrawable(new TextureRegion(armOver));
			armst.imageDown = new TextureRegionDrawable(new TextureRegion(armDown));
			armst.imageUp = new TextureRegionDrawable(new TextureRegion(armUp));
			btnDuration.setStyle(armst);
		}
	 if(btnHP.isPressed()) {
		 Texture armOver = assetManager.get("HP_earned.png", Texture.class);
		 Texture armDown = assetManager.get("HP_earned.png", Texture.class);
		 Texture armUp = assetManager.get("HP_earned.png", Texture.class);		
		 ImageButton.ImageButtonStyle armst = new ImageButton.ImageButtonStyle();
			armst.imageOver = new TextureRegionDrawable(new TextureRegion(armOver));
			armst.imageDown = new TextureRegionDrawable(new TextureRegion(armDown));
			armst.imageUp = new TextureRegionDrawable(new TextureRegion(armUp));
			btnHP.setStyle(armst);
		}
	 if(btnMagic.isPressed()) {
		 Texture armOver = assetManager.get("magic_earned.png", Texture.class);
		 Texture armDown = assetManager.get("magic_earned.png", Texture.class);
		 Texture armUp = assetManager.get("magic_earned.png", Texture.class);		
		 ImageButton.ImageButtonStyle armst = new ImageButton.ImageButtonStyle();
			armst.imageOver = new TextureRegionDrawable(new TextureRegion(armOver));
			armst.imageDown = new TextureRegionDrawable(new TextureRegion(armDown));
			armst.imageUp = new TextureRegionDrawable(new TextureRegion(armUp));
			btnMagic.setStyle(armst);
		}
	 
	 if(btnMana.isPressed()) {
		 Texture armOver = assetManager.get("MANA_earned.png", Texture.class);
		 Texture armDown = assetManager.get("MANA_earned.png", Texture.class);
		 Texture armUp = assetManager.get("MANA_earned.png", Texture.class);		
		 ImageButton.ImageButtonStyle armst = new ImageButton.ImageButtonStyle();
			armst.imageOver = new TextureRegionDrawable(new TextureRegion(armOver));
			armst.imageDown = new TextureRegionDrawable(new TextureRegion(armDown));
			armst.imageUp = new TextureRegionDrawable(new TextureRegion(armUp));
			btnMana.setStyle(armst);
		}
	 if(btnMax.isPressed()) {
		 Texture armOver = assetManager.get("max_earned.png", Texture.class);
		 Texture armDown = assetManager.get("max_earned.png", Texture.class);
		 Texture armUp = assetManager.get("max_earned.png", Texture.class);		
		 ImageButton.ImageButtonStyle armst = new ImageButton.ImageButtonStyle();
			armst.imageOver = new TextureRegionDrawable(new TextureRegion(armOver));
			armst.imageDown = new TextureRegionDrawable(new TextureRegion(armDown));
			armst.imageUp = new TextureRegionDrawable(new TextureRegion(armUp));
			btnMax.setStyle(armst);
		}
	 if(btnRegen.isPressed()) {
		 Texture armOver = assetManager.get("regen_earned.png", Texture.class);
		 Texture armDown = assetManager.get("regen_earned.png", Texture.class);
		 Texture armUp = assetManager.get("regen_earned.png", Texture.class);		
		 ImageButton.ImageButtonStyle armst = new ImageButton.ImageButtonStyle();
			armst.imageOver = new TextureRegionDrawable(new TextureRegion(armOver));
			armst.imageDown = new TextureRegionDrawable(new TextureRegion(armDown));
			armst.imageUp = new TextureRegionDrawable(new TextureRegion(armUp));
			btnRegen.setStyle(armst);
		}
	 if(btnTeleport.isPressed()) {
		 Texture armOver = assetManager.get("teleport_earned.png", Texture.class);
		 Texture armDown = assetManager.get("teleport_earned.png", Texture.class);
		 Texture armUp = assetManager.get("teleport_earned.png", Texture.class);		
		 ImageButton.ImageButtonStyle armst = new ImageButton.ImageButtonStyle();
			armst.imageOver = new TextureRegionDrawable(new TextureRegion(armOver));
			armst.imageDown = new TextureRegionDrawable(new TextureRegion(armDown));
			armst.imageUp = new TextureRegionDrawable(new TextureRegion(armUp));
			btnTeleport.setStyle(armst);
		}
	 if(btnTripple.isPressed()) {
		 Texture armOver = assetManager.get("tripple_earned.png", Texture.class);
		 Texture armDown = assetManager.get("tripple_earned.png", Texture.class);
		 Texture armUp = assetManager.get("tripple_earned.png", Texture.class);		
		 ImageButton.ImageButtonStyle armst = new ImageButton.ImageButtonStyle();
			armst.imageOver = new TextureRegionDrawable(new TextureRegion(armOver));
			armst.imageDown = new TextureRegionDrawable(new TextureRegion(armDown));
			armst.imageUp = new TextureRegionDrawable(new TextureRegion(armUp));
			btnTripple.setStyle(armst);
		}
	 if(btnVampire.isPressed()) {
		 Texture armOver = assetManager.get("vampire_earned.png", Texture.class);
		 Texture armDown = assetManager.get("vampire_earned.png", Texture.class);
		 Texture armUp = assetManager.get("vampire_earned.png", Texture.class);		
		 ImageButton.ImageButtonStyle armst = new ImageButton.ImageButtonStyle();
			armst.imageOver = new TextureRegionDrawable(new TextureRegion(armOver));
			armst.imageDown = new TextureRegionDrawable(new TextureRegion(armDown));
			armst.imageUp = new TextureRegionDrawable(new TextureRegion(armUp));
			btnVampire.setStyle(armst);
		}
	 
	 
 }
		
		
		
}
