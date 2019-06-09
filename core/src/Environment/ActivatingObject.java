package Environment;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import Engine.DynamicObjectsData;
import Engine.RPGWorld;
import Engine.TriggerListener;

public class ActivatingObject extends Platform{

	DynamicObjectsData myId;
	int targetId;
	Texture activated, idle;
	
	boolean beginState;
	
	Fixture useRange;
	public ActivatingObject(int id,Vector2 pos, Vector2 size, RPGWorld rpgWorld, int targetId) {
		super(id,pos, size, rpgWorld);
		this.myId = new DynamicObjectsData(id);
		PolygonShape polygon = new PolygonShape();
		polygon.setAsBox(size.x+size.x/4, size.y);
		useRange = platformBox.createFixture(polygon, 0);
		useRange.setSensor(true);
		PhysicFixture.setSensor(true);
		PhysicFixture.setUserData(myId);
		useRange.setUserData(myId);
		platformBox.setUserData(myId);
		this.targetId = targetId;
		TriggerListener.objects.add(id,false);
		beginState = TriggerListener.objects.get(targetId);
		idle = new Texture(Gdx.files.internal("game_button.png"));
		activated = new Texture(Gdx.files.internal("game_button_pressed.png"));
	}
	
	/**
	 * if need only one activating, comment 2 if() block
	 */
	@Override
	public void update(SpriteBatch batch) {
		
		if(TriggerListener.objects.get(id)) {
			TriggerListener.objects.set(targetId, !beginState);	
		}
		if(!TriggerListener.objects.get(id)) {
			TriggerListener.objects.set(targetId, beginState);	
		}
		if(TriggerListener.objects.get(id) == false) {
			if(texture!= idle) texture = idle;
		}
		else
			if(texture !=activated) texture = activated;
		draw(batch);
		if(myId.isNear) {
			// ADD TEXT "PRESS F TO USE!!!!
		}
		
	}
}
