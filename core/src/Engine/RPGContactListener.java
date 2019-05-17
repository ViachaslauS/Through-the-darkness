package Engine;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.WorldManifold;
import com.badlogic.gdx.utils.Array;

import Entities.Entities;
public class RPGContactListener implements ContactListener{

	Entities entitie;
	ObjectData bodyData;
	int sideView = 1;
	RPGWorld world;
	public RPGContactListener(RPGWorld world) {
		entitie = null;
		this.world = world;
	}
	
	@Override
	public void beginContact(Contact contact) {
		if(contact.getFixtureA().getUserData() != null && contact.getFixtureB().getUserData() != null)
		{
			ObjectData fixAData = recreate(contact.getFixtureA());
			ObjectData fixBData = recreate(contact.getFixtureB());
			
			if(fixAData.isBull && fixBData.isBull)
				contact.setEnabled(false);
			if((fixAData.isBull && fixBData.isAi) || (fixBData.isBull && fixAData.isAi))
				contact.setEnabled(true);
		}
		
		
		
		
	}
	
	private ObjectData recreate(Fixture fixtureA) {
		if(fixtureA.getUserData().getClass().getName() == "com.badlogic.gdx.physics.box2d.Fixture" ) {
			Fixture temp = (Fixture) fixtureA.getUserData();
			return (ObjectData) temp.getUserData();
		}
		return (ObjectData) fixtureA.getUserData();
	}
	
	

	@Override
	public void endContact(Contact contact) {
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
		
	}
private void collisionWizard(Fixture receiver, Fixture dealer) {
		
		Fixture recDataFix = (Fixture) receiver.getUserData();
		ObjectData recData = (ObjectData) recDataFix.getUserData();
		ObjectData dealData = (ObjectData) dealer.getUserData();
		aiReact(recData, dealData);
		
	}
private void aiReact(ObjectData rec, ObjectData deal) {
		
		  if(rec.isAi) { rec.isAttacking = 0; } else deal.isAttacking = 0;
		 
	}
	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
	
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
		
	}
	
	public void setEntitie(Entities ent) {
		entitie = ent;
	}

}
