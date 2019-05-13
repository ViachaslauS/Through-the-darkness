package aiall;

import com.badlogic.gdx.ai.steer.utils.Path.PathParam;

public class Pathparametrs implements PathParam {
   float _distance=0;
	@Override
	public float getDistance() {
		// TODO Auto-generated method stub
		return _distance;
	}

	@Override
	public void setDistance(float distance) {
		// TODO Auto-generated method stub
		_distance = distance;
	}

}
