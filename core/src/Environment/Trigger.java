package Environment;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import Engine.DynamicObjectsData;
import Engine.RPGWorld;
import Engine.TriggerListener;

public class Trigger extends Platform{
	
	
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
	
	protected boolean temp;
	@Override
	public void update(SpriteBatch batch) {
		temp = TriggerListener.objects.get(id);
		if(!isDynamic && myData.wasActivated)
			temp = false;
//					if(temp)
//						myData.wasActivated = true;
//					if(!isDynamic) {
//						if(!myData.isNear)
//							temp = false;
//						if(myData.isNear && !myData.wasActivated)
//							temp = true;
//						if(myData.wasActivated && myData.isNear)
//							temp = TriggerListener.objects.get(id);
//					}
//					else {
//						temp = myData.isNear;
//					}
//		if(wasActivated && !isDynamic && !TriggerListener.objects.get(id)) {
//			temp = false;
//		}
//		
//		if(myData.isNear) {
//			if(!isDynamic && !wasActivated) {
//			temp = true;
//			wasActivated = true;
//			}
//			if(isDynamic)
//				temp = true;
//		}
//		else
//			if(isDynamic)
//				temp = false;
		TriggerListener.objects.set(id, temp);
	}
}
