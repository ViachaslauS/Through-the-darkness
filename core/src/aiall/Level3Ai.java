package aiall;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import Engine.RPGWorld;

public class Level3Ai extends AiCustom {
	RPGWorld rpgWorld;
	Texture image_;
	TextureRegion[][] image;
	Animation<TextureRegion> attack11Animation,attack22Animation,idleAnimation,current1Animation;
	TextureRegion current1Frame;
	
	public Level3Ai(Vector2 size, Vector2 coord, int botLevel, RPGWorld rpg) {
		super(size, coord, botLevel);
		rpgWorld = rpg;
		 bots = new ArrayList<AiCustom>();
		image_ = new Texture(Gdx.files.internal("mageSkills.png"));
		image = TextureRegion.split(image_, image_.getWidth()/10, image_.getHeight()/10);
		TextureRegion[] temp = new TextureRegion[10];
		for(int i=0;i<10;i++) {
			temp[i] = image[0][i];
		}
		attack11Animation = new Animation<TextureRegion>(0.10f,temp);
		TextureRegion[] temp2 = new TextureRegion[5];
		for(int i = 0; i< 5; i++) {
			temp2[i] = image[1][i];
		}
		TextureRegion[] temp3 = new TextureRegion[1];
		idleAnimation = new Animation<TextureRegion>(0.1f,temp3);
		attack22Animation = new Animation<TextureRegion>(0.10f,temp2);
		current1Animation = idleAnimation;
		current1Frame = current1Animation.getKeyFrame(0);
		}
	
	@Override
	public void update (float delta) {
		for(int i = 0; i< bots.size(); i++) {
			
			
			bots.get(i).update(delta);
			if(bots.get(i).isDead) {
				bots.get(i).deleteBot();
				bots.remove(i);
			}
				
		}
		super.update(delta);
		
		if(isEnded1 == 2)
			ignoreAttack1 += Gdx.graphics.getDeltaTime();
		if(ignoreAttack1 > 2) {
			ignoreAttack1 = 0;
			isEnded1 = 1;
		}
		if(isEnded2 == 2)
			ignoreAttack2 += Gdx.graphics.getDeltaTime();
		if(ignoreAttack2 > 2) {
			ignoreAttack2 = 0;
			isEnded2 = 1;
		}
		if(isEnded3 == 2)
			ignoreAttack3 += Gdx.graphics.getDeltaTime();
		if(ignoreAttack3 > 5) {
			ignoreAttack3 = 0;
			isEnded3 = 1;
		}
		}

	float ignoreAttack1 = 0;
	float ignoreAttack2 = 0;
	float ignoreAttack3 = 0;

	@Override
	public boolean attack() {
		current1Frame = current1Animation.getKeyFrame(DURATION,false);
		if(current1Animation != attack11Animation) {
			  current1Animation = attack11Animation;
			  current1Frame = current1Animation.getKeyFrame(0);
			  DURATION = 0;
		}
		
		if(current1Animation.getKeyFrames()[current1Animation.getKeyFrames().length-1] == current1Frame) {
			  entitieData.isAttacking = 0;
			  entitieData.isMustAttack = false;
			  current1Animation = idleAnimation;
			  current1Frame  = current1Animation.getKeyFrame(0);
			  currentAnimation = stayAnimation;
			  currentFrame = currentAnimation.getKeyFrame(0);
			  delta = 0;
			  isEnded1 = 2;
			 
			  return true;
		  }
		
		delta+=Gdx.graphics.getDeltaTime();
		//current1Frame = current1Animation.getKeyFrame(delta);
	
		//DURATION +=Gdx.graphics.getDeltaTime();
		frame1Flip();
		 
		 if(current1Frame == current1Animation.getKeyFrames()[7]) {
			 if(entitieData.isAttacking != -3) entitieData.isAttacking = -2; }


		 
			currentFrame = currentAnimation.getKeyFrame(DURATION,true);
			if(currentAnimation != attack1Animation) {
				  currentAnimation = attack1Animation;
				  currentFrame = currentAnimation.getKeyFrame(0);

			}
			
//			if(currentAnimation.getKeyFrames()[currentAnimation.getKeyFrames().length-1] == currentFrame) {
//				  entitieData.isAttacking = 0;
//				  
//				  entitieData.isMustAttack = false;
//				  DURATION = 0;
//				  return true;
//			  }
			
			
			DURATION +=Gdx.graphics.getDeltaTime();
			frameFlip();
			 if(currentFrame == currentAnimation.getKeyFrames()[7]) {
				 if(entitieData.isAttacking != -3) entitieData.isAttacking = -3; }
 
			
			return false;
	}
	@Override 
	public boolean attack3() {
		if(bots.size() <= 3) {
		bots.add(new Level1Ai(new Vector2(150.0f,150.0f) , new Vector2(this.getCoordX()-50,180.0f),1));
		bots.add(new Level1Ai(new Vector2(150.0f,150.0f) , new Vector2(this.getCoordX()+ 50,180.0f),1));
		for(int i = bots.size()-1; i > bots.size()-3; i--)
			bots.get(i).setBody(rpgWorld);
		isEnded3 = 2;
		}
		return false;
	}
	
	@Override
	public boolean attackShoot() {
		current1Frame = current1Animation.getKeyFrame(DURATION,false);
		if(current1Animation != attack22Animation) {
			  current1Animation = attack22Animation;
			  current1Frame = current1Animation.getKeyFrame(0);
			  DURATION = 0;
		}
		
		if(current1Animation.getKeyFrames()[current1Animation.getKeyFrames().length-1] == current1Frame) {
			  entitieData.isAttacking = 0;
			  entitieData.isMustAttack = false;
			  current1Animation = idleAnimation;
			  current1Frame  = current1Animation.getKeyFrame(0);
			  delta = 0;
			  isEnded2 = 2;
			 
			  return true;
		  }
		
		delta+=Gdx.graphics.getDeltaTime();
		//current1Frame = current1Animation.getKeyFrame(delta);
	
		//DURATION +=Gdx.graphics.getDeltaTime();
		frame1Flip();
		 
		 if(current1Frame == current1Animation.getKeyFrames()[3]) {
			 if(entitieData.isAttacking != -5) {
			 shoot();
				 entitieData.isAttacking = -5; 
				 }
		 }

		 
			currentFrame = currentAnimation.getKeyFrame(DURATION,true);
			if(currentAnimation != attack1Animation) {
				  currentAnimation = attack1Animation;
				  currentFrame = currentAnimation.getKeyFrame(0);

			}
			
//			if(currentAnimation.getKeyFrames()[currentAnimation.getKeyFrames().length-1] == currentFrame) {
//				  entitieData.isAttacking = 0;
//				  
//				  entitieData.isMustAttack = false;
//				  DURATION = 0;
//				  return true;
//			  }
			
			
			DURATION +=Gdx.graphics.getDeltaTime();
			frameFlip();
		/*
		 * if(currentFrame == currentAnimation.getKeyFrames()[7]) {
		 * if(entitieData.isAttacking != -3) entitieData.isAttacking = -2; }
		 */
 
			
			return false;
		
	}
	
	
	float delta= 0;
	@Override
	public void draw(SpriteBatch batch) {
			if(current1Frame!=null)
			batch.draw(current1Frame,coordX,coordY,sizeX,sizeY);
			for(int i = 0;i<bots.size();i++) {
				batch.draw(bots.get(i).currentFrame, bots.get(i).getCoordX(), bots.get(i).getCoordY(), bots.get(i).getSizeX(), bots.get(i).getSizeY());
				bots.get(i).barAIDrawing(batch);
				for(int j=0;j<bots.get(i).bullets.size();j++)  
					bots.get(i).bullets.get(j).render(batch,delta);
			}
	}
	public void frame1Flip() {
		if(sideView == 1)
		{
			if(!current1Frame.isFlipX()) {											    
				current1Frame.flip(false, false);	
			}
			else {
				current1Frame.flip(true, false);
			}
		}
		else {
			if(current1Frame.isFlipX()) {
			
				current1Frame.flip(false, false);
			}
			else {
			
				current1Frame.flip(true, false);
			}
		}
	}
}
