package aiall;

import java.sql.Time;
import java.util.ArrayList;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.steer.behaviors.FollowPath;
import com.badlogic.gdx.ai.steer.utils.Path;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import Engine.RPGWorld;
import Entities.Bullet;
import Entities.Entities;

public class AiCustom extends Entities {
	//public float sideView;
	//boolean isAttaking;
	Vector3 all;
	private int level;
	Vector2 _coord;
	private float time;
	private float bulletTime = 0;
	SteeringAgent steeringAgent;
	
	public ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	private float attack_time=0;
	
	Filter f = new Filter();
	Filter attf = new Filter();
	
	private Animation<TextureRegion> currentAnimation;

	
	//FollowPath followPath;
	Path<Vector2, Pathparametrs> path;
	public void picParam() {
				// all columns and rows  in pic
				PIC_FRAME_COLS = 10;
				PIC_FRAME_ROWS = 10;
				// stay animation cols and rows and from where begin
				STAY_FRAME_COL = 0;
				STAY_FRAME_COLS = 10;	
				STAY_FRAME_ROW = 0;
				STAY_FRAME_ROWS = 1;
				// for move
				MOVE_FRAME_COL = 0;
				MOVE_FRAME_COLS = 10;
				MOVE_FRAME_ROW = 1;
				MOVE_FRAME_ROWS = 1;
				// for attack
				ATTACK1_FRAME_COL = 0;
				ATTACK1_FRAME_COLS = 10;
				ATTACK1_FRAME_ROW = 2;
				ATTACK1_FRAME_ROWS = 1;
				// for death
				DEATH_FRAME_COL = 0;
				DEATH_FRAME_COLS = 10;
				DEATH_FRAME_ROW = 3;
				DEATH_FRAME_ROWS = 1;
	}
	
	
	public AiCustom(Vector2 size,Vector2 coord, int botLevel) {
		super("aistats"+botLevel);
		this.level = botLevel;
		//preferences = Gdx.app.getPreferences("aistats"+ id);
		setSize(size);
		setCoord(coord);
		_coord = coord;
		coordX = coord.x;
		coordY = coord.y;
		sideView = 1;
		entitieData.isAi = true;
		//sideView = (float) sideView;
		//isAttacking = false;
		
		steeringAgent = new SteeringAgent(coord, sideView, 0);
		//followPath = new FollowPath<Vector2, Pathparametrs>(steeringAgent, path);
		f.maskBits = RPGWorld.MASK_RUNNER;
		f.categoryBits = RPGWorld.CATEGORY_RUNNER;
		f.groupIndex = -1;
		
		attf.groupIndex = -5; // filter of attack
		attf.categoryBits = RPGWorld.CATEGORY_RUNNER;
		attf.maskBits = RPGWorld.MASK_RUNNER;
		
		// Slava CRITICAL SECTION EDIT
		//_______________________________________________________________________________
		allSheets = new Texture(Gdx.files.internal("AI"+botLevel+".png"));
		picParam();
		
		imageCollector = TextureRegion.split(allSheets,allSheets.getWidth()/PIC_FRAME_ROWS,allSheets.getHeight()/PIC_FRAME_COLS);
		stayFrames = new TextureRegion[STAY_FRAME_COLS*STAY_FRAME_ROWS];
		moveFrames = new TextureRegion[MOVE_FRAME_COLS*MOVE_FRAME_ROWS];
		attack1Frames = new TextureRegion[ATTACK1_FRAME_COLS*ATTACK1_FRAME_ROWS];
		deathFrames = new TextureRegion[DEATH_FRAME_COLS*DEATH_FRAME_ROWS];
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
		deathFrames = new TextureRegion[10];
		for(int i = 0; i< DEATH_FRAME_ROWS; i++) {
			for(int j = 0; j<DEATH_FRAME_COLS; j++) {
				deathFrames[index++] = imageCollector[i+DEATH_FRAME_ROW][j+ATTACK1_FRAME_COL];
			}
		}
		index = 0;
		stayAnimation = new Animation<TextureRegion>(0.20f,stayFrames);
		moveAnimation = new Animation<TextureRegion>(0.10f,moveFrames);
		deathAnimation = new Animation<TextureRegion>(0.10f,deathFrames);
		attack1Animation = new Animation<TextureRegion>(0.15f-botLevel/100,attack1Frames);
		currentFrame = stayAnimation.getKeyFrame(0.10f, true);
		currentAnimation = stayAnimation;
		
		
		// hp bar
		fullHPBar = new Texture(Gdx.files.internal("fullloadbar.png"));
		currentHPBar = new Texture(Gdx.files.internal("currentloadbar.png"));
		//_________________________________________________________________________________________________
	}
		//SLAVA CRITICAL
		public boolean isDead = false;
		//___
	  public void update(float delta) {
		 
		  //Slava CRITICAL SECTION
		  //_________________________________________
		  if(getHITPOINT() <=0) {
			  entitieData.resetHitpoints();
			  if(currentFrame == deathAnimation.getKeyFrames()[deathAnimation.getKeyFrames().length-1])
				  isDead = true;
			  else currentFrame = deathAnimation.getKeyFrame(CURRENT_DURATION,false);
			  frameFlip();
			  CURRENT_DURATION+=Gdx.graphics.getDeltaTime();
			  return;
		  }
		  //_________________________________________
		  
		  time+= Gdx.graphics.getDeltaTime();
		  entitieData.attackTime += Gdx.graphics.getDeltaTime();
		  bulletTime += Gdx.graphics.getDeltaTime();
		  if(entitieData.isAttacking == 0)
			  currentFrame = stayAnimation.getKeyFrame(delta,true);
		  if(bulletTime >= 2 && entitieData.isAttacking == -1  && !(isJump) && level == 2) {
			  bulletTime = 0;
			  shoot();
		  }
		  
		  
		  if(time>=4 && level == 2) {
			  
			  jump();
		  }
		  for(int i = 0; i< bullets.size(); i++)
				 bullets.get(i).update(Gdx.graphics.getDeltaTime());
		  
		  if(entitieData.isAttacking == -1) {
		 all = steeringAgent.update(this.sideView);
		 move(all.y);
		 
		 sideView = (int) all.x;
		 entitieData.sideView = (int) all.x;
		 currentFrame = moveAnimation.getKeyFrame(delta,true);
		 }
		  if(entitieData.shouldEvade)
		  {
			  entitieData.shouldEvade = false;
			  entitieBox.applyLinearImpulse(new Vector2((400000*sideView),0), new Vector2(coordX,coordY), true);
		  }
		  
		  
		  updatePhysic();
		  entitieData.updateData();
		  frameFlip();
		  
		  
	  }
	  private boolean isJump = false;
	  public void jump() {
		 
		  time = 0;
		  if(isJump) {
				if(isEntitieGrounded()) {
					isJump = false;
				}
				return;
			}
			if(isEntitieGrounded()) {
				isJump = true;
				entitieBox.applyLinearImpulse(new Vector2(0,1100), new Vector2(coordX,coordY), true);
			}
		  
	  }


	public void setAttacking(int b) {
		entitieData.isAttacking = b;
		
	}
	Fixture attackRange;
	 @Override
	protected void bodyInitialize() {
		 CircleShape circlePolygon = new CircleShape();
			circlePolygon.setRadius(300);
			circlePolygon.setPosition(new Vector2(75,75));
			//Gdx.app.log("Sprite Coord", ""+ entitieBox.getGravityScale());
			sensorFixture = entitieBox.createFixture(circlePolygon,0f);
			sensorFixture.setUserData(entitieData);
			sensorFixture.setSensor(true);
			
			circlePolygon.setRadius(75);
			circlePolygon.setPosition(new Vector2(75,75));
			attackRange = entitieBox.createFixture(circlePolygon,0f);
			attackRange.setUserData(entitieData);
			attackRange.setSensor(true);
			
			
			circlePolygon.dispose();
			
				PolygonShape polygon = new PolygonShape();
				polygon.setAsBox(38, 75,new Vector2(75,75),0);
				physicsFixture = entitieBox.createFixture(polygon, 0.0f);
				polygon.dispose();
				physicsFixture.setDensity(0.0f);
				physicsFixture.setSensor(false);
				physicsFixture.setUserData(sensorFixture);
				entitieBox.setBullet(true);
				entitieBox.setGravityScale(1000f);
				entitieBox.setTransform(coordX, coordY, 0);
				
				physicsFixture.setFilterData(f);
			    sensorFixture.setFilterData(f);
			    attackRange.setFilterData(attf);
	}
	 public void deleteBot() {
		 	entitieBox.destroyFixture(attackRange);
			entitieBox.destroyFixture(physicsFixture);
			entitieBox.destroyFixture(sensorFixture);
			for(int i = 0; i < bullets.size();i++)
				bullets.get(i).delete();
			dispose();
			
		}
		
		// Shoot to hero
		public void shoot() {
			Bullet bullet = new Bullet(this.coordX, this.coordY+(this.getSizeY()/2),this.sideView);
			bullet.setBody(rpgWorld);
			bullets.add(bullet);
			
		}

		//Slava CRITICAL SECTION
		//___________________________________________________
		Texture fullHPBar;
		Texture currentHPBar;
		public void barAIDrawing(SpriteBatch batch) {
			
			batch.draw(fullHPBar, coordX+30, coordY + sizeY+20, entitieData.getMAXHITPOINT()%300, 5);
			batch.draw(currentHPBar, coordX+30, coordY + sizeY+20, getHITPOINT()%300, 5);
		}
		//___________________________________________________
	
}
