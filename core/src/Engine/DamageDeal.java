package Engine;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.WorldManifold;
import com.badlogic.gdx.utils.Array;

public class DamageDeal {

	RPGWorld world;
	ObjectData data;
	
	public DamageDeal(RPGWorld world) {
		this.world = world;
	}
	
	/**
	 *  Attention!!!
	 *  <br> CRUTCHES!!!!
	 */
	public void update() {
		Array<Contact> contacts = world.world.getContactList();
		for(int i = 0; i< contacts.size;i++) {
			//if(contacts.get(i).isTouching()) {
				if(contacts.get(i).getFixtureA().getUserData()!=null && contacts.get(i).getFixtureB().getUserData()!=null) {
					if(contacts.get(i).getFixtureA().getUserData().getClass().getName() == "com.badlogic.gdx.physics.box2d.Fixture" && contacts.get(i).getFixtureB().getUserData().getClass().getName() == ("com.badlogic.gdx.physics.box2d.Fixture"))
						continue;
					if(contacts.get(i).getFixtureA().getUserData().getClass().getName() == "com.badlogic.gdx.physics.box2d.Fixture")
						collisionWizard(contacts.get(i).getFixtureA(),contacts.get(i).getFixtureB());
					else
						if(contacts.get(i).getFixtureB().getUserData().getClass().getName() == "com.badlogic.gdx.physics.box2d.Fixture")
							collisionWizard(contacts.get(i).getFixtureB(),contacts.get(i).getFixtureA());
				}
			}
		//}
	}

	private void collisionWizard(Fixture receiver, Fixture dealer) {
		
		Fixture recDataFix = (Fixture) receiver.getUserData();
		ObjectData recData = (ObjectData) recDataFix.getUserData();
		ObjectData dealData = (ObjectData) dealer.getUserData();
		
		if(dealData.isAttacking == 1 ) {
			recData.setHitpoint(dealData.getDAMAGE());
			dealData.isAttacking = 2;
		}
	}
	
}
