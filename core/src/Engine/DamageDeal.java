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
						{
						
						continue;}
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
		
		if(dealData.isAttacking == 1 ) {
			recData.setHitpoint(dealData.getSkillDamage());
			dealData.isAttacking = 2;
		}
		aiReact(recData, dealData);
	}
	
	private void aiReact(ObjectData rec, ObjectData deal) {
	if(rec.isAi) {
		rec.isAttacking = -1;
		
	}
	else
	if(deal.isAi){deal.isAttacking = -1;}
		
	}
		
		private void aiAttack(Fixture receiver, Fixture dealer) {
			Fixture recDataFix = (Fixture) receiver.getUserData();
			ObjectData recData = (ObjectData) recDataFix.getUserData();
			ObjectData dealData = (ObjectData) dealer.getUserData();
			
			if(recData.isAi && recData.isAttacking == -1) {
			 if(recData.attackTime >=3) {
				 recData.attackTime =0;
				 recData.isAttacking = 0;
				 dealData.setHitpoint(recData.getDAMAGE());
				 
			 }
			 else {
				 if(recData.isAi && dealData.isAttacking == 1)
					 recData.shouldEvade = true;
			 }
			 
				 
			}
			if(dealData.isAi && dealData.isAttacking == -1) {
				if(dealData.attackTime >=3) {
					dealData.attackTime =0;
					dealData.isAttacking = 0;
					recData.setHitpoint(dealData.getDAMAGE());
				}
				else {
					 if(dealData.isAi && recData.isAttacking == 1)
						 dealData.shouldEvade = true;
				 }
			}

			
			
		}
	
	
	
}



