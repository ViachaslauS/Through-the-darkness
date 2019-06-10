package aiall;

import com.badlogic.gdx.math.Vector2;

public class Level1Ai extends AiCustom {

	public Level1Ai(Vector2 size, Vector2 coord, int botLevel) {
		super(size, coord, botLevel);

	}
	
	
	@Override
	public void update (float delta) {
	super.update(delta);	
	}
	
	
	
	@Override
	protected boolean attack() {
	 super.attack();
	  
		
		 if(currentFrame == currentAnimation.getKeyFrames()[7]) {
		 if(entitieData.isAttacking != -3) entitieData.isAttacking = -2; }
		 
		
		return false;
	}
	
	

}
