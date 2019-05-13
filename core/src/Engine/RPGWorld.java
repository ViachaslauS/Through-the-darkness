package Engine;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public class RPGWorld {

	public World world;
	public Array<Platform> platforms;
	
	public RPGWorld() {
		
		world = new World(new Vector2(0, -100),true);
		world.setContactListener(new RPGContactListener());
	}
	public void setEnvironment(Array<Platform> levelDesign) {
		platforms = levelDesign;
		
	}
	public void dispose() {
		world.dispose();
		
		
	}
}
