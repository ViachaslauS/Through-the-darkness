package Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import Engine.ObjectData;
import Engine.RPGWorld;

public class Bullet {
	RPGWorld world;
	Body box;
	Filter f = new Filter();
	public static final int speed = 200;
	private static Texture texture = new Texture(Gdx.files.internal("bullets.png"));
	
	public static TextureRegion[][] allBullets;
	public TextureRegion[] bulletFrames;
	Animation<TextureRegion> bulletAnimation;
	TextureRegion currentFrame;
	private int sideView;
	float x,y, startX, raznica;
	
	public boolean remove = false;
	
	public Bullet (float x, float y, int side) {
		
		allBullets = TextureRegion.split(texture, texture.getWidth()/10, texture.getHeight()/10);
		bulletFrames = new TextureRegion[4];
	
		bulletAnimation = new Animation<TextureRegion>(0.05f,bulletFrames);
		this.x = x;
		startX = this.x;
		this.y = y;
		sideView = side;
		f.categoryBits = RPGWorld.CATEGORY_BULLET;
		f.maskBits = RPGWorld.MASK_BULLET;
		f.groupIndex = -3;
	}
	public void update (float deltaTime) {
		bulletData.updateData();
		box.setTransform(x, y, 0);
		//updatePhysic();
		x += speed* deltaTime*sideView;
		raznica =Math.abs(x - startX);
		
		if(raznica > 150) {
			remove = true;
		}
		if(bulletData.shouldRemove)
			remove = true;
		
	}
	float DURATION = 0.0f;
	public void render (SpriteBatch batch,float delta) {
		currentFrame = bulletAnimation.getKeyFrame(DURATION,true);
		DURATION+=Gdx.graphics.getDeltaTime();
		if(DURATION > bulletAnimation.getAnimationDuration()*2)
			DURATION = 0.0f;
		frameFlip();
		batch.draw(currentFrame, x, y,64,64);
	}
	public void delete () {
		box.destroyFixture(physicsFixture);
		box.destroyFixture(sensorFixture);
		//texture.dispose();
	}
	public void setBody(RPGWorld world) {
		this.world = world;
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		box = world.world.createBody(bodyDef);
		bodyInitialize();
		
	}
	private Fixture sensorFixture;
	private Fixture physicsFixture;
	private ObjectData bulletData = new ObjectData("Bullet");
	
	private void bodyInitialize() {
		CircleShape circlePolygon = new CircleShape();
		circlePolygon.setRadius(32);
		circlePolygon.setPosition(new Vector2(32,32));
		sensorFixture = box.createFixture(circlePolygon,0f);
		sensorFixture.setUserData(bulletData);
		sensorFixture.setSensor(true);
		circlePolygon.dispose();
		
		PolygonShape polygon = new PolygonShape();
		polygon.setAsBox(32, 32,new Vector2(32,32),0);
		physicsFixture = box.createFixture(polygon, 0.0f);
		polygon.dispose();
		physicsFixture.setDensity(0);
		box.setGravityScale(0);
		physicsFixture.setSensor(true);
		physicsFixture.setUserData(sensorFixture);
		box.setBullet(true);
		box.setTransform(this.x, this.y, 0);
		bulletData.isBull = true;
		physicsFixture.setFilterData(f);
		sensorFixture.setFilterData(f);
	}
	
	protected void updatePhysic() {
		if(box.getLinearVelocity().y == 0) {
			y = box.getPosition().y;
		}
		else {
			y += Gdx.graphics.getDeltaTime()*box.getLinearVelocity().y;
		}
		
		//Gdx.app.log("Gravity and speed",""+entitieBox.getGravityScale()+"  "+ entitieBox.getLinearVelocity());
	}
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
}
