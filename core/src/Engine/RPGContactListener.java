package Engine;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.WorldManifold;

import Entities.Entities;

public class RPGContactListener implements ContactListener{

	Entities entitie;
	ObjectData bodyData;
	int sideView = 1;
	public RPGContactListener() {
		entitie = null;
	}
	
	@Override
	public void beginContact(Contact contact) {
		/*
		 * if(contact.getFixtureA().getUserData()!= null &&
		 * contact.getFixtureB().getUserData()!= null ) {
		 * if(contact.getFixtureB().getUserData().equals("box") &&
		 * contact.getFixtureA().getUserData().equals("box")) {
		 * 
		 * } else {
		 * 
		 * if(contact.getFixtureB().getUserData().equals("box")) bodyData = (ObjectData)
		 * contact.getFixtureA().getUserData(); else bodyData = (ObjectData)
		 * contact.getFixtureB().getUserData(); if(bodyData.isAttacking) {
		 * if(contact.getFixtureA().getBody().getPosition().x >
		 * contact.getFixtureB().getBody().getPosition().x) { sideView = -1; }
		 * bodyData.setHitpoint(5); // Õ≈œ–¿¬»À‹ÕŒ –¿¡Œ“¿≈“!!! bodyData.isAttacking =
		 * false;
		 * 
		 * } bodyData = (ObjectData) contact.getFixtureB().getUserData();
		 * if(bodyData.isAttacking) { bodyData.setHitpoint(5); bodyData.isAttacking =
		 * false; } } }
		 */
		
	}

	@Override
	public void endContact(Contact contact) {
		// TODO Auto-generated method stub
		
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
