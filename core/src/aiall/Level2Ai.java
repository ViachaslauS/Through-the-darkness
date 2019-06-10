package aiall;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class Level2Ai extends AiCustom {

	public Level2Ai(Vector2 size, Vector2 coord, int botLevel) {
		super(size, coord, botLevel);
	
	}
	
	@Override
	public void update (float delta) {
		  if(getHITPOINT() <=0) {
			  entitieData.resetHitpoints();
			  if(currentFrame == deathAnimation.getKeyFrames()[deathAnimation.getKeyFrames().length-1])
				  isDead = true;
			  else currentFrame = deathAnimation.getKeyFrame(CURRENT_DURATION,false);
			  frameFlip();
			  CURRENT_DURATION+=Gdx.graphics.getDeltaTime();
			  return;
		  }
		  
		  updatePhysic();
		  entitieData.updateData();
		 
		  //_________________________________________
		  for(int i = 0; i< bullets.size(); i++)
				 bullets.get(i).update(Gdx.graphics.getDeltaTime());
		  
		/*  if(entitieData.isMustAttack) {
			  if(!attack())
				  return;
		  }*/
		  
		  
		  time+= Gdx.graphics.getDeltaTime();
		  entitieData.attackTime += Gdx.graphics.getDeltaTime();
		  bulletTime += Gdx.graphics.getDeltaTime();
		  if(entitieData.isAttacking == 0)
			  currentFrame = stayAnimation.getKeyFrame(delta,true);

		  
		 
		  if(time>=4) {
			  
			  jump();
		  }
		  if(entitieData.isMustAttack) {
			  if(!attack())
				  return;
		  }
		  
		  if(entitieData.isAttacking == -1 ) {
			 entitieData.isMustAttack = true;
		  }
		 

		  if(entitieData.shouldEvade)
		  {
			  entitieData.shouldEvade = false;
			  entitieBox.applyLinearImpulse(new Vector2(1000,1000), new Vector2(coordX,coordY), true);
			
		  }
		  
		  frameFlip(); 
		
		  
	} 
	
	
	@Override
	protected boolean attack() {
	 super.attack();
	 if(currentFrame == currentAnimation.getKeyFrames()[5] && entitieData.isAttacking!= -5)
	 { shoot(); 
	 entitieData.isAttacking = -5; }
	 
	 
		return false;
	}
	

}
