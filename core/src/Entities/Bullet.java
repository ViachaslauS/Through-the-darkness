package Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
	public static final int speed = 100;
	private static Texture texture = new Texture(Gdx.files.internal("badlogic.jpg"));
	private int sideView;
	float x,y;
	
	public boolean remove = false;
	
	public Bullet (float x, float y, int side) {
		this.x = x;
		this.y = y;
		sideView = side;
		f.categoryBits = RPGWorld.CATEGORY_BULLET;
		f.maskBits = RPGWorld.MASK_BULLET;
		f.groupIndex = 3;
	}
	public void update (float deltaTime) {
		bulletData.updateData();
		updatePhysic();
		x += speed* deltaTime*sideView;
		if( x < 0) {
			remove = true;
		}
		if(bulletData.shouldRemove)
			remove = true;
		
	}
	public void render (SpriteBatch batch) {
		batch.draw(texture, x, y,16,16);
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
		circlePolygon.setRadius(8);
		circlePolygon.setPosition(new Vector2(8,8));
		sensorFixture = box.createFixture(circlePolygon,0f);
		sensorFixture.setUserData(bulletData);
		sensorFixture.setSensor(true);
		circlePolygon.dispose();
		
		PolygonShape polygon = new PolygonShape();
		polygon.setAsBox(8, 8,new Vector2(8,8),0);
		physicsFixture = box.createFixture(polygon, 0.0f);
		polygon.dispose();
		physicsFixture.setDensity(0);
		box.setGravityScale(0);
		physicsFixture.setSensor(false);
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
		box.setTransform(x, y, 0);
		//Gdx.app.log("Gravity and speed",""+entitieBox.getGravityScale()+"  "+ entitieBox.getLinearVelocity());
	}

}
