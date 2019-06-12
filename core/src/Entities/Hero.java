package Entities;

import java.io.Console;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.World;

import Engine.ObjectData;
import Engine.RPGWorld;
import Entities.Buff.BuffType;



/**
 * @author Slava
 *<br> 
 *{@link silentiumslava@gmail.com}
 *
 */
public class Hero  extends Entities{
	
	public ArrayList<Skill> msSkills = new ArrayList<Skill>();
	public ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	
	
	//public PlayerStats stats;
	
	private Animation<TextureRegion> teleportAnimation;
	//private Animation<TextureRegion> deathAnimation;
	
	private TextureRegion[] teleportFrames;
	private Texture skills;
	Filter f = new Filter();
	
	private float soundsDuration = 0;
	private Sound teleport;
	private Sound move;
	private Sound attack1Sound;
	private Sound attack2Sound;
	private Sound shootSound;
	
	private Sound shift;
	Animation<TextureRegion> currentAnimation;
	Animation<TextureRegion>[] allAnimations;
	
	private Animation<TextureRegion> buff1Animation;
	private Animation<TextureRegion> buff2Animation;
	private Animation<TextureRegion> buff3Animation;
	TextureRegion[] buff1Frames,buff2Frames,buff3Frames;
	private Animation<TextureRegion> shootAnimation;
	private TextureRegion[] shootFrames;
	
	private Animation<TextureRegion> attack2Animation;
	private TextureRegion[] attack2Frames;
	
	int[] skillsLevel = new int[3];
	public void picParam() {
		//Count of sprites in image
				PIC_FRAME_COLS = 10;
				PIC_FRAME_ROWS = 10;
				//count idle sprites
				STAY_FRAME_COL = 0;
				STAY_FRAME_COLS = 10;	
				STAY_FRAME_ROW = 0;
				STAY_FRAME_ROWS = 1;
				//count move sprites
				MOVE_FRAME_COL = 0;
				MOVE_FRAME_COLS = 10;
				MOVE_FRAME_ROW = 2;
				MOVE_FRAME_ROWS = 1;
				//count attack1 sprites
				ATTACK1_FRAME_COL = 0;
				ATTACK1_FRAME_COLS = 10;
				ATTACK1_FRAME_ROW = 3;
				ATTACK1_FRAME_ROWS = 1;
				//for death
				DEATH_FRAME_COL = 0;
				DEATH_FRAME_COLS = 10;
				DEATH_FRAME_ROW = 4;
				DEATH_FRAME_ROWS = 1;
	}
	
	
	public Hero(Vector2 heroSize,Vector2 heroCoord,AssetManager loader) {
		super("herostats");
		//preferences = Gdx.app.getPreferences("herostats");
		manager = loader;
		createSkills();
		//stats = new PlayerStats("herostats");
		setSize(heroSize);
		setCoord(heroCoord);
		sideView = 1; 
		allSheets = manager.get("Hero.png",Texture.class);
		move = manager.get("move.wav",Sound.class);
		skills = manager.get("dark_skills.png",Texture.class);
		entitieData.isAi = false;
		picParam();
		
		skillsLevel[0] = skillsLevel[1] = skillsLevel[2] = 1;
		// filter
				f.maskBits = RPGWorld.MASK_PLAYER;
				f.categoryBits = RPGWorld.CATEGORY_PLAYER;
				f.groupIndex = -1;
		
		//include to collector all sprites from picture
		imageCollector = TextureRegion.split(allSheets,allSheets.getWidth()/PIC_FRAME_ROWS,allSheets.getHeight()/PIC_FRAME_COLS);
		//initialize TextureRegions
		stayFrames = new TextureRegion[STAY_FRAME_COLS*STAY_FRAME_ROWS];
		moveFrames = new TextureRegion[MOVE_FRAME_COLS*MOVE_FRAME_ROWS];
		attack1Frames = new TextureRegion[ATTACK1_FRAME_COLS*ATTACK1_FRAME_ROWS];
		attack2Frames = new TextureRegion[8];
		
		shootFrames = new TextureRegion[10];
		buff1Frames = new TextureRegion[10];
		buff2Frames = new TextureRegion[10];
		buff3Frames = new TextureRegion[10];
		
		
		
		//include to TextureRegions sprites
		int index = 0;
		for(int i=0;i<STAY_FRAME_ROWS;i++)  
			for(int j=0;j<STAY_FRAME_COLS;j++) {
				stayFrames[index++] = imageCollector[i+STAY_FRAME_ROW][j+STAY_FRAME_COL];
			}
		index = 0;
		for(int i=0;i<MOVE_FRAME_ROWS;i++)  
			for(int j=0;j<MOVE_FRAME_COLS;j++)
				moveFrames[index++] = imageCollector[i+MOVE_FRAME_ROW][j+MOVE_FRAME_COL];
		index = 0;
		deathFrames = new TextureRegion[DEATH_FRAME_COLS*DEATH_FRAME_ROWS];
		for(int i = 0; i< DEATH_FRAME_ROWS; i++) {
			for(int j = 0; j<DEATH_FRAME_COLS; j++) {
				deathFrames[index++] = imageCollector[i+DEATH_FRAME_ROW][j+ATTACK1_FRAME_COL];
			}
		}
		index = 0;
		TextureRegion[][] temp = TextureRegion.split(skills,skills.getWidth()/10,skills.getHeight()/10);
		teleportFrames = new TextureRegion[10];
		for(int i=0;i<10;i++) {
			teleportFrames[i] = temp[0][i];
			attack1Frames[i] = temp[1][i];
			shootFrames[i] = temp[4][i];
			buff1Frames[i] = temp[5][i];
			buff2Frames[i] = temp[6][i];
			buff3Frames[i] = temp[7][i];
		}
		for(int i = 0; i<8; i++) {
			attack2Frames[i] = temp[3][i];
		}
		//Initialize of animations
		buff1Animation = new Animation<TextureRegion>(ANIMATION_SPEED*(1-entitieData.stats.ATKSPEED()),buff1Frames);
		buff2Animation = new Animation<TextureRegion>(ANIMATION_SPEED*(1-entitieData.stats.ATKSPEED()),buff2Frames);
		buff3Animation = new Animation<TextureRegion>(ANIMATION_SPEED*(1-entitieData.stats.ATKSPEED()),buff3Frames);
		
		stayAnimation = new Animation<TextureRegion>(0.10f, stayFrames);
		moveAnimation = new Animation<TextureRegion>(0.10f,moveFrames);
		attack1Animation = new Animation<TextureRegion>(ANIMATION_SPEED*(1-entitieData.stats.ATKSPEED()),attack1Frames);
		attack2Animation = new Animation<TextureRegion>(0.1f,attack2Frames);
		deathAnimation = new Animation<TextureRegion>(0.10f,deathFrames);
		teleportAnimation = new Animation<TextureRegion>(ANIMATION_SPEED*(1-entitieData.stats.ATKSPEED()),teleportFrames);	
		
		shootAnimation = new Animation<TextureRegion>(ANIMATION_SPEED*(1-entitieData.stats.ATKSPEED()),shootFrames);
		currentFrame = stayAnimation.getKeyFrame(0.10f, true);
		// Add fucking teleport
		
		attack1Sound = manager.get("attack1.wav",Sound.class);
		attack2Sound = manager.get("attack2.wav",Sound.class);
		teleport = manager.get("teleport.wav",Sound.class);
		shootSound = manager.get("magic.wav",Sound.class);
		
	}

	@Override
	public void update(float delta) {
		updateStats();
		entitieData.updateData();
		updatePhysic();
		for(int i = 0; i<msSkills.size();i++)
			msSkills.get(i).update();
		
		
		// bullets for hero
		 for(int i = 0; i< bullets.size(); i++)
			 bullets.get(i).update(Gdx.graphics.getDeltaTime());
	  
		
		
		 if(coordX < 0)
			coordX = 0;
		 if(currentAction!=0) {
			 if(checkStop())
				 return;
			 if(switchAction())
				 return;
		 }
		 else
			 checkKeys();
		 if(Gdx.input.isKeyPressed(Keys.D) || Gdx.input.isKeyPressed(Keys.RIGHT)) {   //move right
			 if(soundsDuration >= 0.4f && isEntitieGrounded()) {
				 move.play(0.1f);
				 soundsDuration = 0;
				}
				soundsDuration+=Gdx.graphics.getDeltaTime();
	
			 
				currentFrame = moveAnimation.getKeyFrame(delta, true);
				//entitieData.currentFrame = currentFrame;
				if(currentFrame.isFlipX()) {
					currentFrame.flip(true, false);
				}
				//coordX += 500 * Gdx.graphics.getDeltaTime();
				move(500);
				sideView = 1;
				entitieData.sideView = 1;
				return;	
			}
			if(Gdx.input.isKeyPressed(Keys.A) || Gdx.input.isKeyPressed(Keys.LEFT)) {    //move left
				if(soundsDuration >= 0.4f && isEntitieGrounded()) {
					 move.play(0.1f);
					 soundsDuration = 0;
					}
					soundsDuration+=Gdx.graphics.getDeltaTime();
				
				currentFrame = moveAnimation.getKeyFrame(delta, true);
				//entitieData.currentFrame = currentFrame;
				if(!currentFrame.isFlipX())
					currentFrame.flip(true, false);
				//coordX -= 500 * Gdx.graphics.getDeltaTime();
				move(-500);
				sideView = -1;
				entitieData.sideView = -1;
				
				return;
			}
			currentFrame = stayAnimation.getKeyFrame(delta,true);
			frameFlip();
			
	}
			
	public boolean switchAction() {
		switch(currentAction) {
		case 1:attack1();   	break;
		case 2:teleport();	    break;
		case 3:jump();		   return false;
		case 4:attack2();       break;
		case 5:shoot(); 		break;
		case 6:buff1();			break;
		case 7:buff2();			break;
		case 8:buff3();			break;
		default: return false;
		}
		return true;
	}


	/**
	 *  Check pressed buttons.<br>
	 *  Here need to add new actions with buttons
	 */
	public void checkKeys() { 	//Here including new button
		
		//Attack1
		if(Gdx.input.isKeyJustPressed(Keys.NUM_1)) {
			currentAction = 1;
			currentAnimation = attack1Animation;
			entitieData.skillDamage = 1.8f;
			attack1();
			attack1Sound.play(0.6f);
			return;
		}
		if(Gdx.input.isKeyJustPressed(Keys.NUM_3) && msSkills.get(2).isAvailable) {
			if(!this.entitieData.setMANA(10))
				return;
			msSkills.get(2).setCooldown();
			currentAction = 5;
			currentAnimation = shootAnimation;
			entitieData.skillDamage = 3f;
			shootSound.play(0.3f);
			shoot();
			return;
		}
		//teleport
		if(Gdx.input.isKeyJustPressed(Keys.SHIFT_LEFT) && msSkills.get(0).isAvailable) {
			if(!this.entitieData.setMANA(10))
				return;
			msSkills.get(0).setCooldown();
			//isPhysicUpdatingActive = false;
			//this.physicsFixture.setSensor(true);
			teleport.play(0.3f);
			currentAction = 2;
			currentAnimation = teleportAnimation;
			teleport();
			return;
		}
		//jump
		if(Gdx.input.isKeyJustPressed(Keys.UP)) {
			currentAction = 3;
			currentAnimation = stayAnimation;
			jump();
		}
		//attack2
		if(Gdx.input.isKeyPressed(Keys.NUM_2) && msSkills.get(1).isEarned) {
			currentAction = 4;
			currentAnimation = attack2Animation;
			entitieData.skillDamage = 1.2f;
			attack2();
		}
		if(Gdx.input.isKeyJustPressed(Keys.Q) && msSkills.get(3).isEarned && msSkills.get(3).isAvailable ) {
			msSkills.get(3).setCooldown();
			currentAction = 6;
			currentAnimation = buff1Animation;

			buff1();
		}
		if(Gdx.input.isKeyJustPressed(Keys.W) && msSkills.get(4).isEarned && msSkills.get(4).isAvailable ) {
			msSkills.get(4).setCooldown();
			currentAction = 7;
			currentAnimation = buff2Animation;

			buff2();
		}
		if(Gdx.input.isKeyJustPressed(Keys.E) && msSkills.get(5).isEarned && msSkills.get(5).isAvailable ) {
			msSkills.get(5).setCooldown();
			currentAction = 8;
			currentAnimation = buff3Animation;

			buff3();
		}
	}


	/** Check, was li pressed S button
	 * @return
	 * If button was pressed then true
	 */
	private boolean checkStop() {
		if(Gdx.input.isKeyPressed(Keys.S)) {
			reset();
			return true;
		}
		return false;	
	}
	/**
	 * update animation frame
	 * @return
	 * return true if animation was ended, and use reset()
	 */
	public boolean refresh() { 
		currentFrame = currentAnimation.getKeyFrame(CURRENT_DURATION, false);
		//entitieData.currentFrame = currentFrame;
		if(currentFrame == currentAnimation.getKeyFrames()[currentAnimation.getKeyFrames().length-1]) {
			CURRENT_DURATION = 0.0f;
			reset();
			
			return true;
		}
		CURRENT_DURATION+=Gdx.graphics.getDeltaTime();	
		frameFlip();
		return false;
	}
	
	private void buff1() {
		if(refresh()) {
			entitieData.setNewBuff(BuffType.HITPOINTS, 0.1f, 10, true);
			return;
		}
	}
	private void buff2() {
		if(refresh()) {
			entitieData.setNewBuff(BuffType.MANA, 0.1f, 10, true);
			return;
		}
	}
	private void buff3() {
		if(refresh()) {
			entitieData.setNewBuff(BuffType.REGEN_FREQUENCY, 2.5f, 10, false);
			return;
		}
	}
	
	public void attack1() {
		if(refresh())
			return;
		if(currentFrame == currentAnimation.getKeyFrames()[5] || currentFrame == currentAnimation.getKeyFrames()[6] || currentFrame == currentAnimation.getKeyFrames()[7])
		{	//coordX+=200*Gdx.graphics.getDeltaTime()*sideView;
			if(entitieData.isAttacking != 2 )
				entitieData.isAttacking = 1;
			move(200*sideView);
		}
		
		//frameFlip();
		return;
		
	}
	int attackinInThisFrame = 0;
	private void attack2() {
		if(refresh()) {
			attackinInThisFrame = 0;
			
			return;
		}
		
		if(currentFrame == currentAnimation.getKeyFrames()[2] && attackinInThisFrame!=2) {
			if(entitieData.isAttacking == 2) {
				attackinInThisFrame = 2;
				entitieData.isAttacking = 0;
				attack2Sound.play(0.2f);
			}
			else
				entitieData.isAttacking = 1;
		}
		if(currentFrame == currentAnimation.getKeyFrames()[4] && attackinInThisFrame!=4) {
			if(entitieData.isAttacking == 2) {
				attackinInThisFrame = 4;
				entitieData.isAttacking = 0;
				attack2Sound.play(0.2f);
			}
			else
				entitieData.isAttacking = 1;
		}
		if(currentFrame == currentAnimation.getKeyFrames()[6] && attackinInThisFrame!=6) {
			if(entitieData.isAttacking == 2) {
				attackinInThisFrame = 6;
				entitieData.isAttacking = 0;
				attack2Sound.play(0.2f);
			}
			else
				entitieData.isAttacking = 1;
		}
		if(!Gdx.input.isKeyPressed(Keys.NUM_2) && (currentFrame == currentAnimation.getKeyFrames()[3] || currentFrame == currentAnimation.getKeyFrames()[5] || currentFrame == currentAnimation.getKeyFrames()[7]))
			reset();
	}
	public void teleport() {
		if(refresh()) {
			entitieData.isInvisible = false;
			return;
		}
		 entitieData.isInvisible = true;
		if(currentFrame == currentAnimation.getKeyFrames()[4] || currentFrame == currentAnimation.getKeyFrames()[5] || currentFrame == currentAnimation.getKeyFrames()[6]) {
			//coordX+=1000*Gdx.graphics.getDeltaTime()*sideView/(1-stats.ATKSPEED());
			move(750*sideView/(1-entitieData.stats.ATKSPEED()));
	
		}
		//frameFlip();
		return;
	}
	private boolean isJump = false;
	private void jump() {
		if(isJump) {
			if(isEntitieGrounded()) {
				isJump = false;
				reset();
			}
			return;
		}
		if(isEntitieGrounded()) {
			isJump = true;
			entitieBox.applyLinearImpulse(new Vector2(0,1450), entitieBox.getPosition(), true);
		}
		else
			reset();
	}
	
//  hero shoot
	boolean isShooting = false;
		public void shoot() {
		if(refresh()) {
			isShooting = false;

			return;
		}
		if(currentFrame == currentAnimation.getKeyFrames()[7] && !isShooting) {
			Bullet bullet = new Bullet((this.coordX+sizeX/2)+(sizeX/2*sideView), this.coordY+(this.getSizeY()/2),this.sideView);	
			for(int i=0;i<4;i++) {
				bullet.bulletFrames[i] = bullet.allBullets[0][i];
			}
			bullet.f.groupIndex = -4;
			bullet.setBody(rpgWorld);
			bullets.add(bullet);
			isShooting = true;
			}
		}

	/**
	 * reset current animation and action
	 */
	public void reset() {
		currentAnimation = stayAnimation;
		currentFrame = stayAnimation.getKeyFrames()[0];
		currentAction = 0;
		entitieData.isAttacking = 0;
		entitieData.skillDamage = 0.0f;
		CURRENT_DURATION = 0.0f;
		entitieData.skillDamage = 1.0f;
	}
	
	@Override
	public void dispose() {
		allSheets.dispose();
		stayFrames = null;
		imageCollector = null;
		stayAnimation = null;
	}

	/* (non-Javadoc)
	 * @see Entities.Entities#getDamage()
	 */
	@Override
	public float getDamage() {
		return DAMAGE;
		
	}

	
	public boolean death() {
		if(!isDeath) {
			isDeath = true;
			currentAnimation = deathAnimation;
			currentFrame = currentAnimation.getKeyFrame(0);
			CURRENT_DURATION = 0.0f;
		}
		updatePhysic();
		if(currentFrame == currentAnimation.getKeyFrames()[currentAnimation.getKeyFrames().length-1])
			return true;
		currentFrame = currentAnimation.getKeyFrame(CURRENT_DURATION);
		CURRENT_DURATION+=Gdx.graphics.getDeltaTime();
		return false;
	}


	public ObjectData getEntitieData() {
		return entitieData;
	}
	public void setFIlter() {
		physicsFixture.setFilterData(f);
		sensorFixture.setFilterData(f);
	}
	
	
	
	
	public void updateStats() {
		
		attack1Animation.setFrameDuration(ANIMATION_SPEED*(1-entitieData.stats.ATKSPEED()));
		teleportAnimation.setFrameDuration(ANIMATION_SPEED*(1-entitieData.stats.ATKSPEED()));
	}
	public void createSkills() {
		msSkills.add(new Skill("Teleport", 3, false, false, "teleport" ));
		msSkills.add(new Skill("Tripple", 0, false, false, "tripple" ));
		msSkills.add(new Skill("Magic", 5, false, false, "magic" ));
		msSkills.add(new Skill("HP", 20, false, false, "HP" ));
		msSkills.add(new Skill("Mana", 20, false, false, "MANA" ));
		msSkills.add(new Skill("Buff", 20, false, false, "buff" ));
		msSkills.add(new Skill("Armor", 0, false, false, "armor" ));
		msSkills.add(new Skill("Armor2", 0, false, false, "armor2" ));
		msSkills.add(new Skill("Vampire", 0, false, false, "vampire" ));
		msSkills.add(new Skill("Regen", 0, false, false, "regen" ));
		msSkills.add(new Skill("Duration", 0,false, false, "duration" ));
		msSkills.add(new Skill("Cooldown", 0, false, false, "cooldown" ));
		msSkills.add(new Skill("Max", 0, false, false, "max" ));
		
	}
}
