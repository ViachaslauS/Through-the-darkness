package Engine;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import aiall.AiCustom;

public class RPGWorld {

	public World world;
	public Array<Platform> platforms;
	public ArrayList<AiCustom> enemy;
	final public static short CATEGORY_PLAYER = 0x0001;
	final public static short CATEGORY_BALOOM = 0x0002;
	final public static short CATEGORY_RUNNER = 0x0003;
	final public static short CATEGORY_SCENERY = 0x0004;
	final public static short CATEGORY_BULLET = 0x0005;
	
	final public static short MASK_PLAYER = CATEGORY_RUNNER | CATEGORY_SCENERY;
	final public static short MASK_BALOOM = CATEGORY_SCENERY;
	final public static short MASK_RUNNER = CATEGORY_PLAYER | CATEGORY_SCENERY;
	final public static short MASK_SCENERY = -1;
	final public static short MASK_BULLET = CATEGORY_RUNNER | CATEGORY_PLAYER;
	
	
	public RPGWorld() {
		
		world = new World(new Vector2(0, -100),true);
		world.setContactListener(new RPGContactListener(this));
		world.setContactFilter(new RPGContactFilter(this));
	}
	public void setEnvironment(Array<Platform> levelDesign, ArrayList<AiCustom> arrayList) {
		platforms = levelDesign;
		enemy = arrayList;
	}
	public void dispose() {
		world.dispose();
		
		
	}
}
