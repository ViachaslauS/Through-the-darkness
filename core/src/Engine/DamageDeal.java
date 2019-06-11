package Engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.WorldManifold;
import com.badlogic.gdx.utils.Array;

import Entities.Buff.BuffType;

public class DamageDeal {

	RPGWorld world;
	ObjectData data;
	Sound hitSound;
	Sound clickSound;
	
	public DamageDeal(RPGWorld world) {
		this.world = world;
		hitSound = Gdx.audio.newSound(Gdx.files.internal("hit.wav"));
		clickSound = Gdx.audio.newSound(Gdx.files.internal("gamebutton.wav"));
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
					// Slavino govno
					//_____________________________	
					if(checkOnButton(contacts,i))
						continue;
					//________________________________________________
				
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

	
	private boolean checkOnButton(Array<Contact> contacts, int i) {
		if(contacts.get(i).getFixtureA().getUserData()!=null && contacts.get(i).getFixtureB().getUserData()!=null) {
			// Slavino govno
			//_____________________________	
			if(contacts.get(i).getFixtureA().getUserData().getClass().getName().equals("Engine.DynamicObjectsData"))
			{
				checkButtons(contacts.get(i).getFixtureA(),contacts.get(i).getFixtureB());
				return true;
			}
			if(contacts.get(i).getFixtureB().getUserData().getClass().getName().equals("Engine.DynamicObjectsData"))
			{
				checkButtons(contacts.get(i).getFixtureB(),contacts.get(i).getFixtureA());
				return true;
			}
		}
		return false;
	}

	private void checkButtons(Fixture button_, Fixture player_) {
		DynamicObjectsData button = (DynamicObjectsData)button_.getUserData();
		if(player_.getUserData().getClass().getName().equals("Engine.ObjectData")) {
			ObjectData playerData = (ObjectData) player_.getUserData();
			if(playerData.isAi == true)
				return;
		}
		if(player_.getUserData().getClass().getName().equals("com.badlogic.gdx.physics.box2d.Fixture")) {
			Fixture fixPlayer = (Fixture) player_.getUserData();
			if(fixPlayer.getUserData().getClass().getName().equals("Engine.ObjectData")) {
				ObjectData tempData = (ObjectData) fixPlayer.getUserData();
				if(tempData.isAi) 
					return;
			}
		}
		if(Gdx.input.isKeyJustPressed(Keys.F) && !button.isTrigger) {
			clickSound.play(0.4f);
			TriggerListener.objects.set(button.id,!TriggerListener.objects.get(button.id)); 
		}
		if(button.isTrigger) {
			TriggerListener.objects.set(button.id,true); 
		}
		button.isNear = true;
	}

	private void collisionWizard(Fixture receiver, Fixture dealer) {
		
		Fixture recDataFix = (Fixture) receiver.getUserData();
		ObjectData recData = (ObjectData) recDataFix.getUserData();
		ObjectData dealData = (ObjectData) dealer.getUserData();
		
		if(dealData.isAttacking == 1) {
			if((dealer.getBody().getPosition().x > receiver.getBody().getPosition().x && dealData.sideView < 0) || (dealer.getBody().getPosition().x < receiver.getBody().getPosition().x && dealData.sideView > 0))
			recData.setHitpoint(dealData.getDAMAGE());
			hitSound.play(0.3f);
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
					hitSound.play(0.3f);
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