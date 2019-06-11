package Environment;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import Engine.RPGWorld;
import Engine.TriggerListener;

public class MovementPlatform extends Platform{

	float  leftBorder, rightBorder;
	float  downBorder, upBorder;

	int sideMove = 1;
	
	Vector2 velocity;
	
	public MovementPlatform(int id,Vector2 pos, Vector2 size, RPGWorld rpgWorld, Vector2 maxPos, Vector2 velocity,boolean startState) {
		super(id ,pos, size, rpgWorld);
		leftBorder = pos.x-size.x/2;
		downBorder = pos.y-size.y/2;
		
		TriggerListener.objects.add(id, startState);
		
		rightBorder = maxPos.x+size.x/2;
		upBorder = maxPos.y+size.y/2;
		
		PhysicFixture.setFriction(1.0f);
		this.velocity = velocity;
	
	}

	@Override 
	public void update(SpriteBatch batch) {
		if(TriggerListener.objects.get(id)) {
			Vector2 currentVelocity = new Vector2(velocity);
			if(position.x + size.x/2 > rightBorder || position.x-size.x/2  < leftBorder)
				currentVelocity.x = 0;
			if(position.y + size.y/2 >upBorder || position.y-size.y/2 < downBorder)
				currentVelocity.y = 0;
			if(currentVelocity.x == 0 && currentVelocity.y == 0) {
				sideMove *= -1;
				currentVelocity = velocity;
			}
			position.x+=sideMove*currentVelocity.x*Gdx.graphics.getDeltaTime();
			position.y+=sideMove*currentVelocity.y*Gdx.graphics.getDeltaTime();
			
			PhysicFixture.getBody().setTransform(position, 0);
			
		}
		draw(batch);
			
	}
}
