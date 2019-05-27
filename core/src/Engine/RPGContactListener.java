package Engine;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
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
	
		Array<Contact> contacts = world.world.getContactList();
		for(int i = 0; i< contacts.size;i++) {
			//if(contacts.get(i).isTouching()) {
			
			// collision bullet w/ stenki
			if(contacts.get(i).getFixtureA().getUserData() == null && contacts.get(i).getFixtureB().getUserData() != null) {
				ObjectData fixData = recreate(contacts.get(i).getFixtureB());
				if(fixData.isBull)
					fixData.shouldRemove = true;
			}
			if(contacts.get(i).getFixtureB().getUserData() == null && contacts.get(i).getFixtureA().getUserData() != null) {
				ObjectData fixData = recreate(contacts.get(i).getFixtureA());
				if(fixData.isBull)
					fixData.shouldRemove = true;
			}
			
			
			
			
			
			
			
				if(contacts.get(i).getFixtureA().getUserData()!=null && contacts.get(i).getFixtureB().getUserData()!=null) {
					ObjectData fixAData = recreate(contacts.get(i).getFixtureA());
					ObjectData fixBData = recreate(contacts.get(i).getFixtureB());
					if(contacts.get(i).getFixtureA().getUserData().getClass().getName() == "com.badlogic.gdx.physics.box2d.Fixture" && contacts.get(i).getFixtureB().getUserData().getClass().getName() == "com.badlogic.gdx.physics.box2d.Fixture")
						{			stopBot(contacts.get(i).getFixtureA(),contacts.get(i).getFixtureB());
						continue;
						
						}
				
					if(contacts.get(i).getFixtureA().getUserData().getClass().getName() == "com.badlogic.gdx.physics.box2d.Fixture") {
						if(fixBData.isBull) {
							fixBData.shouldRemove = true;
						if(!(fixAData.isAi) && !(fixAData.isBull)) {
						
							fixAData.setHitpoint(5);
						}
					}
											}
					else
						if(contacts.get(i).getFixtureB().getUserData().getClass().getName() == "com.badlogic.gdx.physics.box2d.Fixture")
							if(fixAData.isBull) {
								fixAData.shouldRemove = true;
							if(!(fixBData.isAi) && !(fixBData.isBull) && fixAData.isBull) {
							
								fixBData.setHitpoint(5);
							}
						}
				}
			}
		
	}
	
	private void stopBot(Fixture rec, Fixture deal) {
		ObjectData fixAData = recreate(rec);
		ObjectData fixBData = recreate(deal);
		if(fixAData.isAi)
			fixAData.isAttacking = 0;
		if(fixBData.isAi)
			fixBData.isAttacking = 0;
		
		
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
	// Function for razreshat dvigatsya botu if hero otoshel ot ran
	private void checkAi(ObjectData fixA, ObjectData fixB)
	{
		if(fixA.isAi && fixA.isAttacking == -2)
			fixA.isAttacking = -1;
		if(fixB.isAi && fixB.isAttacking == -2)
			fixB.isAttacking = -1;
	}
private void collisionWizard(Fixture receiver, Fixture dealer) {
		
	ObjectData fixAData = recreate(receiver);
	ObjectData fixBData = recreate(dealer);
	if(fixAData.isAttacking == -1 || fixBData.isAttacking == -1)
		aiReact(fixAData, fixBData);
		checkAi(fixAData,fixBData);
		
	
		
	}
private void aiReact(ObjectData rec, ObjectData deal) {
		
		  if(rec.isAi) { rec.isAttacking = 0; } else deal.isAttacking = 0;
		 
	}
	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		
		/*
		 * WorldManifold manifold = contact.getWorldManifold();
		 * 
		 * for(int i =0; i < manifold.getNumberOfContactPoints(); i++) {
		 * if(contact.getFixtureA().getUserData() != null &&
		 * contact.getFixtureB().getUserData() != null) { ObjectData fixAData =
		 * recreate(contact.getFixtureA()); ObjectData fixBData =
		 * recreate(contact.getFixtureB());
		 * 
		 * if(fixAData.isBull && fixBData.isBull) contact.setEnabled(false);
		 * if((fixAData.isBull && !(fixBData.isAi)) || (fixBData.isBull &&
		 * !(fixAData.isAi))) contact.setEnabled(true); }
		 * 
		 * }
		 */
		
		
		
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
		
	}
	
	public void setEntitie(Entities ent) {
		entitie = ent;
	}

}
