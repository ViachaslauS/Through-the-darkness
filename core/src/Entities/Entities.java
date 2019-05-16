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
	protected static int PIC_FRAME_COLS;
	protected static int PIC_FRAME_ROWS;
	protected TextureRegion[][] imageCollector;
	
	// count of needed sprites from pic
	protected static int STAY_FRAME_COLS;  //column
	protected static int STAY_FRAME_COL;   //from which column
	protected static int STAY_FRAME_ROWS;  //row
	protected static int STAY_FRAME_ROW;   //from which row
	//Idle Animtaion
	protected Animation<TextureRegion> stayAnimation;
	
	//Count mount sprites
	protected static int MOVE_FRAME_COLS;
	protected static int MOVE_FRAME_COL;
	protected static int MOVE_FRAME_ROWS;
	protected static int MOVE_FRAME_ROW;
	//move Animation
	protected Animation<TextureRegion> moveAnimation;
	
	//Attack sprites
	protected static int ATTACK1_FRAME_COLS;
	protected static int ATTACK1_FRAME_COL;
	protected static int ATTACK1_FRAME_ROWS;
	protected static int ATTACK1_FRAME_ROW;
	
	protected float CURRENT_DURATION = 0.0f;
	//Animation attack1
	protected Animation<TextureRegion> attack1Animation;
	
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

	/** Get Damage
		(descrease HITPOINT of entities)
	 * @param damage
	 */
	public void giveDamage(float damage) {
		HITPOINT-=(damage*((ARMOR+1)/100));
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
		circlePolygon.setPosition(new Vector2(75,coordY-75));
		//Gdx.app.log("Sprite Coord", ""+ entitieBox.getGravityScale());
		sensorFixture = entitieBox.createFixture(circlePolygon,0f);
		sensorFixture.setUserData(entitieData);
		sensorFixture.setSensor(true);
		circlePolygon.dispose();
		
			PolygonShape polygon = new PolygonShape();
			polygon.setAsBox(38, 75,new Vector2(75,coordY-75),0);
			physicsFixture = entitieBox.createFixture(polygon, 0.0f);
			polygon.dispose();
			physicsFixture.setDensity(10000);
			physicsFixture.setSensor(false);
			physicsFixture.setUserData(sensorFixture);
			entitieBox.setBullet(true);
			entitieBox.setGravityScale(1000f);
			entitieBox.setTransform(coordX, coordY, 0);
			//entitieBox.setUserData(entitieData);
	}
	
	Vector2 bodyVelocity;

	protected void updatePhysic() {
		if(entitieBox.getLinearVelocity().y == 0) {
			coordY = entitieBox.getPosition().y;
		}
		else {
			coordY += Gdx.graphics.getDeltaTime()*entitieBox.getLinearVelocity().y;
		}
		entitieBox.setTransform(coordX, coordY, 0);
		//Gdx.app.log("Gravity and speed",""+entitieBox.getGravityScale()+"  "+ entitieBox.getLinearVelocity());
	}

	protected boolean isEntitieGrounded() {
		//get all contacts in world
		Array<Contact> contactList = rpgWorld.world.getContactList();
		for(int i=0;i<contactList.size;i++) {
			Contact contact = contactList.get(i);
			// Check all contacts in world between player
			if(contact.isTouching() && (contact.getFixtureA() == physicsFixture && contact.getFixtureB().getUserData() == null) || (contact.getFixtureB() == physicsFixture && contact.getFixtureA().getUserData() == null)) {
				WorldManifold manifold = contact.getWorldManifold();
				boolean below = true;
				for(int j=0;j<manifold.getNumberOfContactPoints();j++) {
					below &= (manifold.getPoints()[j].y < physicsFixture.getBody().getPosition().y);
				}
				if(below) {
					return true;
				}
			}
		}
		return false;
	}
	protected void move(float coords) {
		if((isCanMove() && isEntitieGrounded()) || (isCanMove() && !isEntitieGrounded()))
			coordX+=coords*Gdx.graphics.getDeltaTime();
		else
			if(sideView == moveOut)
				coordX+=coords*Gdx.graphics.getDeltaTime();
			else
				setCoord(physicsFixture.getBody().getPosition()); 
	}
	
	private int moveOut = 0;
	/**
	 * check the lets on the way
	 * @return if entitie can move return true
	 */
	private boolean isCanMove() {
		Array<Contact> contactList = rpgWorld.world.getContactList();
		boolean below = true;
		for(int i=0;i<contactList.size;i++) {
			Contact contact = contactList.get(i);
			// Check all contacts in world between player
			if(contact.isTouching() && (contact.getFixtureA() == physicsFixture || contact.getFixtureB() == physicsFixture)) {
				WorldManifold manifold = contact.getWorldManifold();
				for(int j=0;j<manifold.getNumberOfContactPoints();j++) {
					below &= !(manifold.getPoints()[j].y - physicsFixture.getShape().getRadius()  > physicsFixture.getBody().getPosition().y- physicsFixture.getShape().getRadius());
					//below = ((manifold.getPoints()[j].y - physicsFixture.getShape().getRadius()+10) < (physicsFixture.getBody().getPosition().y-physicsFixture.getShape().getRadius()));
					if(!below) {
						if(coordX+sizeX/2 > manifold.getPoints()[j].x) {
							
							moveOut = 1;
						}
						else {
							moveOut = -1;
						}
						return below;
					}
				}
			}
		}
		return below;
	}
}