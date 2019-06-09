package Environment;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import Engine.DynamicObjectsData;
import Engine.RPGWorld;
import Engine.TriggerListener;

public class Trigger extends Platform{
	
	DynamicObjectsData myData;
	boolean isDynamic;
	
	public Trigger(int id,Vector2 pos, Vector2 size, RPGWorld rpgWorld, boolean isDynamic) {
		super(id, pos, size, rpgWorld);
		PhysicFixture.setSensor(true);
		TriggerListener.objects.add(id, false);
		myData = new DynamicObjectsData(id);
		myData.isTrigger = true;
		PhysicFixture.setUserData(myData);
		this.isDynamic = isDynamic;
	}
	
	@Override
	public void update(SpriteBatch batch) {
		boolean temp = TriggerListener.objects.get(id);
		if(myData.isNear) {
			temp = true;
		}
		else
			if(isDynamic)
				temp = false;
		TriggerListener.objects.set(id, temp);
	}
}
