package aiall;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class SteeringAgent implements Steerable<Vector2> {
	private Vector2 _position;
	private float _orientation;
	private boolean _tagged;
	public Vector3 all ;
	boolean _independentorientation = false;
	Vector2 linearVelocity = new Vector2(450,0);
	SteeringBehavior<Vector2> steeringBehavior;
	//SteeringAcceleration<Vector2> steeringOutput;
	public SteeringAgent(Vector2 position, int orientetion, boolean tagged) {
		_position = position;
		_orientation = orientetion;
		_tagged = false;
		//steeringOutput.setZero();
		all = new Vector3(0,0,0);
		
	}
	
	
	
	@Override
	public Vector2 getPosition() {
		// TODO Auto-generated method stub
		return _position;
	}

	@Override
	public float getOrientation() {
		// TODO Auto-generated method stub
		return _orientation;
	}

	public void setOrientation(int orientation) {
		// TODO Auto-generated method stub
		_orientation = orientation;
	}

	@Override
	public float vectorToAngle(Vector2 vector) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Vector2 angleToVector(Vector2 outVector, float angle) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Location<Vector2> newLocation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public float getZeroLinearSpeedThreshold() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setZeroLinearSpeedThreshold(float value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public float getMaxLinearSpeed() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setMaxLinearSpeed(float maxLinearSpeed) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public float getMaxLinearAcceleration() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setMaxLinearAcceleration(float maxLinearAcceleration) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public float getMaxAngularSpeed() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setMaxAngularSpeed(float maxAngularSpeed) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public float getMaxAngularAcceleration() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setMaxAngularAcceleration(float maxAngularAcceleration) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Vector2 getLinearVelocity() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public float getAngularVelocity() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getBoundingRadius() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isTagged() {
		// TODO Auto-generated method stub
		return _tagged;
	}

	@Override
	public void setTagged(boolean tagged) {
		// TODO Auto-generated method stub
		_tagged = tagged;
	}



	@Override
	public void setOrientation(float orientation) {
		// TODO Auto-generated method stub
		
	}
	public Vector3 update( float orientation) {
		if (orientation != _orientation)
		{
			_independentorientation = true;
		}
		//if(steeringBehavior != null) {
			//steeringBehavior.calculateSteering(steeringOutput);
		 all =	applySteering( );
		//}
		return all;
	}
	private Vector3 applySteering () {
		if(_independentorientation)
		{
			linearVelocity.x *= -1;
		}
		this._position.mulAdd(linearVelocity, Gdx.graphics.getDeltaTime());
		if(_independentorientation) {
			_independentorientation = false;
			_orientation *= (-1);
		}
	all.x =_orientation;
	all.y = _position.x;
	all.z = _position.y;
		return all;
	}
}
