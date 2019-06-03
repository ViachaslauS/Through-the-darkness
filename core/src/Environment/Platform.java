package Environment;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

import Engine.RPGWorld;
import Engine.TriggerListener;

import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;

public class Platform {
	

	protected Vector2 position;
	protected	Rectangle bounds;
	protected	Body platformBox;
	protected	Fixture PhysicFixture;
	protected	Vector2 size;
	protected	Filter f = new Filter();
	
	protected	Texture texture;
	
	protected int id;
	
	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}
	
	public Texture getTexture() {
		return texture;
	}
	
	public Platform(int id,Vector2 pos,Vector2 size, RPGWorld rpgWorld) {
	
		this.id = id;
		
		TriggerListener.objects.add(id, true);
		
		position = pos;
		bounds = new Rectangle();
		bounds.width = size.x;
		bounds.height = size.y;
		this.size = size;
		texture = new Texture(Gdx.files.internal("platformtexture.png"));
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.StaticBody;
		platformBox = rpgWorld.world.createBody(bodyDef);
		PolygonShape polygon = new PolygonShape();
		polygon.setAsBox(size.x/2, size.y/2);
		platformBox.setTransform(pos, 0);
		PhysicFixture = platformBox.createFixture(polygon, 0);
		PhysicFixture.setDensity(100000.0f);
		PhysicFixture.setFriction(10f);
		polygon.dispose();
		
		//fitler
				f.categoryBits = RPGWorld.CATEGORY_SCENERY;
				f.maskBits = RPGWorld.MASK_SCENERY;
				f.groupIndex = 2;
				PhysicFixture.setFilterData(f);
		
		
	}

	public void update(SpriteBatch batch) {
		//batch.draw(texture,position.x - size.x/2,position.y - size.y/2,size.x,size.y);
		draw(batch);
	}
	
	public void draw(SpriteBatch batch) {
		batch.draw(texture,position.x - size.x/2,position.y - size.y/2,size.x,size.y);
	}
}
