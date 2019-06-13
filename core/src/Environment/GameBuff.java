package Environment;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import Engine.RPGWorld;


public class GameBuff extends Trigger{
	
	//Texture bufTexture;

	public GameBuff(int id,Vector2 pos, Vector2 size, RPGWorld rpgWorld) {
		super(id, pos, size, rpgWorld,false);
		texture = new Texture(Gdx.files.internal("gemYellow.png"));
	}
	@Override
	public void update(SpriteBatch batch) {
		super.update(batch);
		if(!myData.wasActivated)
			draw(batch);
	}
			
}
