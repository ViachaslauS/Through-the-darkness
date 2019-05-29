package Engine;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.WorldManifold;
import com.badlogic.gdx.utils.Array;

import Entities.Buff.BuffType;

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
					if(contacts.get(i).getFixtureA().getUserData().getClass().getName() == "com.badlogic.gdx.physics.box2d.Fixture" && contacts.get(i).getFixtureB().getUserData().getClass().getName() == "com.badlogic.gdx.physics.box2d.Fixture")
						continue;
						
				
					if(contacts.get(i).getFixtureA().getUserData().getClass().getName() == "com.badlogic.gdx.physics.box2d.Fixture")
						{	if(contacts.get(i).getFixtureB().getFilterData().groupIndex == -5)
							aiAttack(contacts.get(i).getFixtureA(),contacts.get(i).getFixtureB());
						else
						collisionWizard(contacts.get(i).getFixtureA(),contacts.get(i).getFixtureB());
						}
						
					else
						if(contacts.get(i).getFixtureB().getUserData().getClass().getName() == "com.badlogic.gdx.physics.box2d.Fixture")
							{	
							if(contacts.get(i).getFixtureA().getFilterData().groupIndex == -5)
								aiAttack(contacts.get(i).getFixtureA(),contacts.get(i).getFixtureB());
							else
							collisionWizard(contacts.get(i).getFixtureB(),contacts.get(i).getFixtureA());
							}
				}
			}
		//}
	}

	
	private void collisionWizard(Fixture receiver, Fixture dealer) {
		
		Fixture recDataFix = (Fixture) receiver.getUserData();
		ObjectData recData = (ObjectData) recDataFix.getUserData();
		ObjectData dealData = (ObjectData) dealer.getUserData();
		
		if(dealData.isAttacking == 1) {
			if((dealer.getBody().getPosition().x > receiver.getBody().getPosition().x && dealData.sideView < 0) || (dealer.getBody().getPosition().x < receiver.getBody().getPosition().x && dealData.sideView > 0))
			recData.setHitpoint(dealData.getDAMAGE());
			dealData.isAttacking = 2;
		}
		aiReact(recData, dealData);
	}
	
	private void aiReact(ObjectData rec, ObjectData deal) {
	if(rec.isAi && rec.isAttacking == 0) {
		rec.isAttacking = -1;
		
	}
	else
	if(deal.isAi && deal.isAttacking == 0){deal.isAttacking = -1;}
		
	}
		
		private void aiAttack(Fixture receiver, Fixture dealer) {
			Fixture recDataFix = (Fixture) receiver.getUserData();
			ObjectData recData = (ObjectData) recDataFix.getUserData();
			ObjectData dealData = (ObjectData) dealer.getUserData();
			
//			if(recData.isAi) {
//				// recData.isAttacking = -2;
//			 if(recData.isAttacking == -2) {
//				 recData.attackTime = 0;
//				 recData.isAttacking = -3;
//				 dealData.setHitpoint(recData.getDAMAGE());
//				 
//			 }
//			 else {
//				 if(dealData.isAttacking == 1)
//					 recData.shouldEvade = true;
//			 }
//			 
//				 
//			}
//			 
			if(dealData.isAi) {
			//	dealData.isAttacking = -2;
				if(dealData.isAttacking == -2) {
					dealData.attackTime = 0;
					dealData.isAttacking = -3;
					recData.setHitpoint(dealData.getDAMAGE());
				}
				else {
					if(dealData.isAttacking != -3) {
						dealData.isMustAttack = true;
					}
					 if(recData.isAttacking == 1)
						 dealData.shouldEvade = true;
				 }
			}
			
			
		}
		

	
	
}