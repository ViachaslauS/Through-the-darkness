package Entities;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.WorldManifold;
import com.badlogic.gdx.utils.Array;

import Engine.DamageDeal;
import Engine.ObjectData;
import Engine.RPGWorld;

import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public class Entities {
	
	
	public Entities(String name) {
		
		entitieData = new ObjectData(name);
	}
	
	protected ObjectData entitieData;
	
	protected float HITPOINT;
	/**
	 * true return
	 * @return 
	 */
	public float getHITPOINT() {
		return entitieData.getHITPOINT();
	}

	public float getMANA() {
		return MANA;
	}

	public float getARMOR() {
		return ARMOR;
	}

	public float getDAMAGE() {
		return DAMAGE;
	}
	protected float MANA;
	protected float ARMOR;
	protected float DAMAGE;
	
	protected Body body;
	
	protected boolean isDeath = false;
	
	protected AssetManager manager;
	
	//protected Preferences preferences;
	
	/**
	 *  side which sees entities
	 *   <br>
	 *   1 - right
	 *   <br>
	 *   2 - left
	 */
	public int sideView;  
	//protected boolean isAttacking;
	//Coord of Entities
	protected float coordX;
	protected float coordY;
	protected float coordZ;
	//Size of Entities
	protected float sizeX;
	protected float sizeY;
	
	protected float ANIMATION_SPEED = 0.1f;
	
	//current Frame
	public TextureRegion currentFrame;
	
	/**
	 *  All sprites from 1 picture
	 */
	protected Texture allSheets;
	//count of sprites in pic
	protected  int PIC_FRAME_COLS;
	protected  int PIC_FRAME_ROWS;
	protected TextureRegion[][] imageCollector;
	
	// count of needed sprites from pic
	protected  int STAY_FRAME_COLS;  //column
	protected  int STAY_FRAME_COL;   //from which column
	protected  int STAY_FRAME_ROWS;  //row
	protected  int STAY_FRAME_ROW;   //from which row
	//Idle Animtaion
	protected Animation<TextureRegion> stayAnimation;
	
	//Count mount sprites
	protected  int MOVE_FRAME_COLS;
	protected  int MOVE_FRAME_COL;
	protected  int MOVE_FRAME_ROWS;
	protected  int MOVE_FRAME_ROW;
	//move Animation
	protected Animation<TextureRegion> moveAnimation;
	
	//Attack sprites
	protected  int ATTACK1_FRAME_COLS;
	protected  int ATTACK1_FRAME_COL;
	protected  int ATTACK1_FRAME_ROWS;
	protected  int ATTACK1_FRAME_ROW;
	
	protected float CURRENT_DURATION = 0.0f;
	//Animation attack1
	protected Animation<TextureRegion> attack1Animation;
	
	// SLAVA CRITICAL SECTION
	//________________________________________________________
	protected int DEATH_FRAME_COLS;
	protected int DEATH_FRAME_COL;
	protected int DEATH_FRAME_ROWS;
	protected int DEATH_FRAME_ROW;
	
	protected Animation<TextureRegion> deathAnimation;
	protected TextureRegion[] deathFrames;
	//________________________________________________________
	protected TextureRegion[] stayFrames;
	protected TextureRegion[] moveFrames;
	protected TextureRegion[] attack1Frames;
	
	/**
	 *  Variants of actions:
	 *  <br>
	 *	0 - idle, or simple move
	 *  <br>
	 *  1 - Attack1	
	 *  <br>
	 *	2 - Teleport	
	 *  <br>
	 *  3 - Jump					 			
	 */
	protected int currentAction = 0;
	
	public void update(float delta) {
	
	}
	
	public void dispose() {
		
	}
	
	public void setSize(Vector2 newSize) {
		sizeX = newSize.x;
		sizeY = newSize.y;
	}
	public void setCoord(Vector2 newCoord) {
		coordX = newCoord.x;
		coordY = newCoord.y;
	}
	public float getCoordX() {
		return coordX;
	}

	public void setCoordX(int coordX) {
		this.coordX = coordX;
	}

	public float getCoordY() {
		return coordY;
	}

	public void setCoordY(int coordY) {
		this.coordY = coordY;
	}

	public float getCoordZ() {
		return coordZ;
	}

	public void setCoordZ(int coordZ) {
		this.coordZ = coordZ;
	}
	
	public float getSizeX() {
		return sizeX;
	}

	public void setSizeX(float sizeX) {
		this.sizeX = sizeX;
	}

	public float getSizeY() {
		return sizeY;
	}

	public void setSizeY(float sizeY) {
		this.sizeY = sizeY;
	}
	
	public Body getBody() {
		return entitieBox;
	}

	/** Get Damage
		(descrease HITPOINT of entities)
	 * @param damage
	 */
	public void giveDamage(float damage) {
		entitieData.setHitpoint(damage);
	}
	/**
	 * @return Damage of Entities
	 */
	public float getDamage() {
		return DAMAGE;
	}
	public float getHitPoints() {
		return HITPOINT;
	}

	/** 
	 *  I don't know what do this method, but his work
	 */
	public void frameFlip() {
		if(sideView == 1)
		{
			if(!currentFrame.isFlipX()) {											    
				currentFrame.flip(false, false);	
			}
			else {
				currentFrame.flip(true, false);
			}
		}
		else {
			if(currentFrame.isFlipX()) {
			
				currentFrame.flip(false, false);
			}
			else {
			
				currentFrame.flip(true, false);
			}
		}
	}
	
	protected Body entitieBox;
	protected Fixture physicsFixture;
	protected Fixture sensorFixture;
	protected RPGWorld rpgWorld;
	/**
	 * Set body for entitie
	 * @param world from the level
	 */
	public void setBody(RPGWorld rpgWorld) {
		this.rpgWorld = rpgWorld;
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		
		entitieBox = rpgWorld.world.createBody(bodyDef);
		bodyInitialize();
	}
	/**
	 *  Initializing entities body. <br>
	 *  Creating polygons and adding them to Body
	 */
	protected void bodyInitialize() {
		
		CircleShape circlePolygon = new CircleShape();
		circlePolygon.setRadius(75);
		circlePolygon.setPosition(new Vector2(75,75));
		
		//Gdx.app.log("Sprite Coord", ""+ entitieBox.getGravityScale());
		sensorFixture = entitieBox.createFixture(circlePolygon,0f);
		sensorFixture.setUserData(entitieData);
		sensorFixture.setSensor(true);
		circlePolygon.dispose();
		
			PolygonShape polygon = new PolygonShape();
			polygon.setAsBox(38, 75,new Vector2(75,75),0);
			physicsFixture = entitieBox.createFixture(polygon, 0.0f);
			polygon.dispose();
			physicsFixture.setDensity(0.0f);
			physicsFixture.setSensor(false);
			physicsFixture.setUserData(sensorFixture);
			entitieBox.setBullet(true);
			entitieBox.setGravityScale(500f);
			entitieBox.setTransform(coordX, coordY, 0);
			//entitieBox.setUserData(entitieData);
	}
	
	Vector2 bodyVelocity;
	protected boolean isPhysicUpdatingActive = true;
	
	protected void updatePhysic() {
		if(!isPhysicUpdatingActive)
			return;
		//Gdx.app.log("velocity", ""+entitieBox.getLinearVelocity().y);
		
		updateCollisions();
		
		if(entitieBox.getLinearVelocity().y == 0 || (entitieBox.getLinearVelocity().y < 0.0f && isEntitieGrounded())) {
			coordY = entitieBox.getPosition().y;
		}
		else {
			coordY += Gdx.graphics.getDeltaTime()*entitieBox.getLinearVelocity().y;
		}
		if(entitieBox.getLinearVelocity().x !=0) {
			coordX += Gdx.graphics.getDeltaTime()*entitieBox.getLinearVelocity().x;
			//return;
		}
		entitieBox.setTransform(coordX, coordY,0);
		//Gdx.app.log("Gravity and speed",""+entitieBox.getGravityScale()+"  "+ entitieBox.getLinearVelocity());
	}

	private void updateCollisions() {
		
	}

	protected boolean isEntitieGrounded() {
		//get all contacts in world
		boolean below = false;
		Array<Contact> contactList = rpgWorld.world.getContactList();
		for(int i=0;i<contactList.size;i++) {
			Contact contact = contactList.get(i);
			// Check all contacts in world between player
			if (contact.isTouching()
					&& (contact.getFixtureA() == physicsFixture  && contact.getFixtureB().getUserData() == null )
					|| (contact.getFixtureB() == physicsFixture  && contact.getFixtureA().getUserData() == null )) {
				WorldManifold manifold = contact.getWorldManifold();
				// Slava CRITICAL SECTION
				
				if(manifold.getPoints() == null)
					return false;
				for(int j=0;j<manifold.getNumberOfContactPoints();j++) {
					below = (manifold.getPoints()[j].y < physicsFixture.getBody().getPosition().y);
				}
				if(below) {
					return true;
				}
			}
		}
		return below;
	}
	protected void move(float coords) {
		//if(isCanMove())
		//entitieBox.applyLinearImpulse(new Vector2(100*sideView,0), entitieBox.getPosition(), true);
			entitieBox.setLinearVelocity(new Vector2(coords*Gdx.graphics.getDeltaTime()*50,entitieBox.getLinearVelocity().y));
			//coordX+=coords*Gdx.graphics.getDeltaTime();
			//entitieBox.setTransform(entitieBox.getPosition().x +coords*Gdx.graphics.getDeltaTime(),entitieBox.getPosition().y, 0);		
		//else
		//	if(sideView == moveOut)
		//		entitieBox.setLinearVelocity(new Vector2(coords*Gdx.graphics.getDeltaTime(),entitieBox.getLinearVelocity().y));
			
				//coordX+=coords*Gdx.graphics.getDeltaTime();
				//entitieBox.setTransform(entitieBox.getPosition().x +coords*Gdx.graphics.getDeltaTime(),entitieBox.getPosition().y, 0);
		//		else
				setCoord(entitieBox.getPosition());
		//setCoord(entitieBox.getPosition());
	}
	
	/**
	 *  1 - move right to out, -1 - left, 0 - you have some problems, bro
	 */
	private int moveOut = 0;
	/**
	 * check the lets on the way
	 * @return if entitie can move return true
	 */
	private boolean isCanMove() {
		Array<Contact> contactList = rpgWorld.world.getContactList();
		boolean below = true;
		boolean rez = true;
		moveOut = 0;
		for(int i=0;i<contactList.size;i++) {
			Contact contact = contactList.get(i);
			// Check all contacts in world between player
			if(contact.isTouching() && (contact.getFixtureA() == physicsFixture || contact.getFixtureB() == physicsFixture)) {
				WorldManifold manifold = contact.getWorldManifold();
				//Slava CRITICAL SECTION
				if(manifold.getNumberOfContactPoints() == 0)
					continue;
				for(int j=0;j<manifold.getNumberOfContactPoints();j++) {
					below = !(manifold.getPoints()[j].y  > coordY  + 25);
					rez &= below;
					if(!below) {
						if(coordX+sizeX/2 + 19> manifold.getPoints()[j].x) {
							
							if(moveOut == -1) {
								//moveOut = 0;	
								continue;
							}
							
							moveOut = 1;
						}
						else {
							if(moveOut == 1) {
								//moveOut = 0;
								continue;
							}
							
							moveOut = -1;
						}
						
					}
				}
			}
		}
		return rez;
	}

	
}