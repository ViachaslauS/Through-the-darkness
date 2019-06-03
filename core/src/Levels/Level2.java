package Levels;

import java.util.ArrayList;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.RPG;

import Environment.Platform;
import aiall.AiCustom;

public class Level2 extends BaseLevel{
	
	public Level2(RPG game) {
		super(game);
	}
	
	
	//@Override
	public ArrayList<AiCustom> createEnemy() {
		 bots = new ArrayList<AiCustom>();
		bots.add(new AiCustom(new Vector2(150.0f,150.0f) , new Vector2(1100.0f,150.0f),1));
		bots.add(new AiCustom(new Vector2(150.0f,150.0f) , new Vector2(1300.0f,150.0f),1));
		return bots;
	}	
	
	//@Override
	public Array<Platform> createEnvironment() {
		platforms = new Array<Platform>();
		platforms.add(new Platform(0,new Vector2(0,-30), new Vector2(10000,100),rpgWorld)); // Earth platform
		platforms.add(new Platform(1,new Vector2(1500,40), new Vector2(20,400), rpgWorld));
		platforms.add(new Platform(2,new Vector2(2200,250), new Vector2(300,20), rpgWorld));
		return platforms;
		}
	//@Override
	public  void managerLoad() {
		assetManager.load("Battleground1.png", Texture.class);
		assetManager.load("Hero.png", Texture.class);
		assetManager.load("sprintSound.wav",Sound.class);
		assetManager.load("dark_skills.png",Texture.class);
	}

}
