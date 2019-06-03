package Environment;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import Engine.RPGWorld;
import Engine.TriggerListener;

public class ShowablePlatform extends Platform{

	public ShowablePlatform(int id,Vector2 pos, Vector2 size, RPGWorld rpgWorld, boolean startState) {
		super(id,pos, size, rpgWorld);
		TriggerListener.objects.add(id, startState);
		texture = new Texture(Gdx.files.internal("activatingplatform.png"));
	}

	@Override
	public void update(SpriteBatch batch) {
		if(TriggerListener.objects.get(id)) {
			PhysicFixture.setSensor(false);
			draw(batch);
		}
		else
			PhysicFixture.setSensor(true);
	}
}
