package Levels;

import java.util.ArrayList;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.RPG;

import Engine.Platform;
import aiall.AiCustom;

public class Level1 extends BaseLevel{
	
	public Level1(RPG game) {
		super(game);
	}
	
	
	//@Override
	public ArrayList<AiCustom> createEnemy() {
		 bots = new ArrayList<AiCustom>();
			bots.add(new AiCustom(new Vector2(150.0f,150.0f) , new Vector2(1100.0f,180.0f),1));
			bots.add(new AiCustom(new Vector2(150.0f,150.0f) , new Vector2(1500.0f,150.0f),1));
			bots.add(new AiCustom(new Vector2(150.0f,150.0f) , new Vector2(2100.0f,150.0f),2));
			bots.add(new AiCustom(new Vector2(150.0f,150.0f) , new Vector2(2400.0f,210.0f),1));
			bots.add(new AiCustom(new Vector2(150.0f,150.0f) , new Vector2(2900.0f,150.0f),2));
			bots.add(new AiCustom(new Vector2(150.0f,150.0f) , new Vector2(3000.0f,150.0f),2));
			bots.add(new AiCustom(new Vector2(150.0f,150.0f) , new Vector2(3700.0f,150.0f),1));
			bots.add(new AiCustom(new Vector2(150.0f,150.0f) , new Vector2(4400.0f,150.0f),2));
			bots.add(new AiCustom(new Vector2(150.0f,150.0f) , new Vector2(5200.0f,150.0f),2));
			return bots;
	}	
	
	//@Override
	public Array<Platform> createEnvironment() {
		platforms = new Array<Platform>();
		platforms.add(new Platform(new Vector2(0,-30), new Vector2(10000000,100),rpgWorld)); // Earth platform
		platforms.add( new Platform(new Vector2(1200,150), new Vector2(150,30), rpgWorld));
		platforms.add( new Platform(new Vector2(1500,100), new Vector2(150,300), rpgWorld));
		platforms.add( new Platform(new Vector2(2200,150), new Vector2(150,30), rpgWorld));
		platforms.add( new Platform(new Vector2(2500,220), new Vector2(150,30), rpgWorld));
		platforms.add( new Platform(new Vector2(2900,280), new Vector2(400,30), rpgWorld));
		platforms.add( new Platform(new Vector2(3900,200), new Vector2(150,30), rpgWorld));
		platforms.add( new Platform(new Vector2(4100,350), new Vector2(150,30), rpgWorld));
		platforms.add( new Platform(new Vector2(4250,450), new Vector2(150,30), rpgWorld));
		platforms.add( new Platform(new Vector2(5400,0), new Vector2(20,1000), rpgWorld));
return platforms;
		}
	//@Override
	public  void managerLoad() {
		assetManager.load("Battleground1.png", Texture.class);
		assetManager.load("Hero.png", Texture.class);
		assetManager.load("sprintSound.wav",Sound.class);
		assetManager.load("dark_skills.png",Texture.class);
		assetManager.load("niceBG.jpg",Texture.class);
		assetManager.load("woodenBG.jpg",Texture.class);
	}

}
