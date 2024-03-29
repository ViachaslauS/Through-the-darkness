package Levels;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.Array;

import Environment.Platform;

public interface GlobalWindow extends Screen{
	
	void managerLoad();

	boolean isLoaded();

	float getLoadProgress();

	Array<Platform> createEnvironment();
}
