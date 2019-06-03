package Environment;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import Engine.RPGWorld;
import Engine.TriggerListener;

public class SecretPlatform extends Platform{

	boolean activeSecret;
	boolean invertor;
	public SecretPlatform(int id, Vector2 pos, Vector2 size, RPGWorld rpgWorld, boolean invertor) {
		super(id, pos, size, rpgWorld);
		PhysicFixture.setSensor(true);
		activeSecret = true;
		this.invertor = invertor;
	}
	
	@Override
	public void update(SpriteBatch batch) {
		if(!invertor) {
		PhysicFixture.setSensor(TriggerListener.objects.get(id));
		draw(batch);
		}
		if(invertor) {
			if(TriggerListener.objects.get(id)) {
				draw(batch);
			}
			PhysicFixture.setSensor(false);
		}
	}
}
