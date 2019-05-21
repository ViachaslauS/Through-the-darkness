package Engine;

import com.badlogic.gdx.physics.box2d.ContactFilter;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;

public class RPGContactFilter implements ContactFilter {

	RPGWorld world;
	 public RPGContactFilter(RPGWorld world) {
		this.world = world;
	}
	
	/* (non-Javadoc)
	 * @see com.badlogic.gdx.physics.box2d.ContactFilter#shouldCollide(com.badlogic.gdx.physics.box2d.Fixture, com.badlogic.gdx.physics.box2d.Fixture)
	 */
	@Override
	public boolean shouldCollide(Fixture fixtureA, Fixture fixtureB) {
		Filter filterA = fixtureA.getFilterData();
		Filter filterB = fixtureB.getFilterData();
		if((filterA.categoryBits == filterB.categoryBits) || (filterA.categoryBits == RPGWorld.CATEGORY_BULLET && filterB.categoryBits == RPGWorld.CATEGORY_RUNNER) || (filterA.categoryBits == RPGWorld.CATEGORY_RUNNER && filterB.categoryBits == RPGWorld.CATEGORY_BULLET))
			return false;
		if(filterA.categoryBits != RPGWorld.CATEGORY_SCENERY && filterB.categoryBits != RPGWorld.CATEGORY_SCENERY)
		{	
			ObjectData fixAData = recreate(fixtureA);
		ObjectData fixBData = recreate(fixtureB);
		
			if(fixAData.isInvisible || fixBData.isInvisible)
			return false;
			
			if((fixAData.isBull && fixBData.isAi ) || (fixAData.isAi && fixBData.isBull))
				return false;
			
			if((fixAData.isBull && !(fixBData.isAi)) || (!(fixAData.isAi) && fixBData.isBull)) {
					return true;}
		}
	return true;
	
	}
	
	/**
	 * @param fixtureA
	 * @return hz che kak no perevodit fixture v ojectData
	 */
	private ObjectData recreate(Fixture fixtureA) {
		if(fixtureA.getUserData().getClass().getName() == "com.badlogic.gdx.physics.box2d.Fixture" ) {
			Fixture temp = (Fixture) fixtureA.getUserData();
			return (ObjectData) temp.getUserData();
		}
		return (ObjectData) fixtureA.getUserData();
	}
	
}