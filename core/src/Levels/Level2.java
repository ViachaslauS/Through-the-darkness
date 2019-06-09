package Levels;

import java.util.ArrayList;
import java.util.Vector;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.RPG;

import Environment.ActivatingObject;
import Environment.Platform;
import Environment.Trigger;
import aiall.AiCustom;

public class Level2 extends BaseLevel{
	
	public Level2(RPG game) {
		super(game);
	}
	
	
	//@Override
	public ArrayList<AiCustom> createEnemy() {
		 bots = new ArrayList<AiCustom>();
			bots.add(new AiCustom(new Vector2(300.0f,300.0f) , new Vector2(4*SS,15*SS),3));
			
		//bots.add(new AiCustom(new Vector2(150.0f,150.0f) , new Vector2(1100.0f,150.0f),1));
		//bots.add(new AiCustom(new Vector2(150.0f,150.0f) , new Vector2(1300.0f,150.0f),1));
		return bots;
	}	
	
	//@Override
	public Array<Platform> createEnvironment() {
		platforms = new Array<Platform>();
		
		platforms.add(new Platform(0,new Vector2(-5,-10), new Vector2(8,30*SS),rpgWorld)); // Earth platform
		platforms.add(new Platform(platforms.size,new Vector2(0*SS,14*SS),new Vector2(20*SS,5*SS),rpgWorld));//1
		platforms.add(new Platform(platforms.size,new Vector2(10*SS,6*SS),new Vector2(10*SS,8*SS),rpgWorld));//2          
		platforms.add(new Platform(platforms.size,new Vector2(13f*SS,4*SS),new Vector2(7*SS,2*SS),rpgWorld));//3
		platforms.add(new Platform(platforms.size,new Vector2(20*SS,5*SS),new Vector2(24*SS,5*SS),rpgWorld));//4
		platforms.add(new Platform(platforms.size,new Vector2(32*SS,4*SS),new Vector2(12*SS,1*SS),rpgWorld));//5
		platforms.add(new Platform(platforms.size,new Vector2(5*SS,3*SS),new Vector2(2*SS,9*SS),rpgWorld));//6
		platforms.add(new Platform(platforms.size,new Vector2(0*SS,0*SS),new Vector2(5*SS,11*SS),rpgWorld));//7
		platforms.add(new Platform(platforms.size,new Vector2(5*SS,0*SS),new Vector2(6*SS,3*SS),rpgWorld));//8
		platforms.add(new Platform(platforms.size,new Vector2(11*SS,0*SS),new Vector2(17*SS,2*SS),rpgWorld));//9
		platforms.add(new Platform(platforms.size,new Vector2(28*SS,0*SS),new Vector2(2*SS,3*SS),rpgWorld));//10
		platforms.add(new Platform(platforms.size,new Vector2(35*SS,0*SS),new Vector2(45*SS,2*SS),rpgWorld));//11
		platforms.add(new Platform(platforms.size,new Vector2(30*SS,0*SS),new Vector2(5*SS,1*SS),rpgWorld));//12
		platforms.add(new Platform(platforms.size,new Vector2(25*SS,16*SS),new Vector2(1*SS,3*SS),rpgWorld));//13
		platforms.add(new Platform(platforms.size,new Vector2(26*SS,16*SS),new Vector2(7*SS,1*SS),rpgWorld));//14
		platforms.add(new Platform(platforms.size,new Vector2(27*SS,20*SS),new Vector2(29*SS,1*SS),rpgWorld));//15
		platforms.add(new Platform(platforms.size,new Vector2(31*SS,21*SS),new Vector2(11*SS,2*SS),rpgWorld));//16
		platforms.add(new Platform(platforms.size,new Vector2(34*SS,23*SS),new Vector2(1*SS,1*SS),rpgWorld));//17
		platforms.add(new Platform(platforms.size,new Vector2(38*SS,23*SS),new Vector2(1*SS,1*SS),rpgWorld));//18
		platforms.add(new Platform(platforms.size,new Vector2(36*SS,15*SS),new Vector2(8*SS,1*SS),rpgWorld));//19
		platforms.add(new Platform(platforms.size,new Vector2(0*SS,0*SS),new Vector2(0*SS,0*SS),rpgWorld));// 20 MOVING!!!
		platforms.add(new Platform(platforms.size,new Vector2(50*SS,16*SS),new Vector2(3*SS,1*SS),rpgWorld));//21
		platforms.add(new Platform(platforms.size,new Vector2(54*SS,14*SS),new Vector2(1*SS,4*SS),rpgWorld));//22
		platforms.add(new Platform(platforms.size,new Vector2(55*SS,14*SS),new Vector2(2*SS,3*SS),rpgWorld));//23
		platforms.add(new Platform(platforms.size,new Vector2(57*SS,14*SS),new Vector2(7*SS,1*SS),rpgWorld));//24
		platforms.add(new Platform(platforms.size,new Vector2(48*SS,4*SS),new Vector2(6*SS,6*SS),rpgWorld));//25
		platforms.add(new Platform(platforms.size,new Vector2(54*SS,4*SS),new Vector2(1*SS,1*SS),rpgWorld));//26
		platforms.add(new Platform(platforms.size,new Vector2(54*SS,9*SS),new Vector2(6*SS,1*SS),rpgWorld));//27
		platforms.add(new Platform(platforms.size,new Vector2(54*SS,10*SS),new Vector2(9*SS,2*SS),rpgWorld));//28
		platforms.add(new Platform(platforms.size,new Vector2(57*SS,2*SS),new Vector2(33*SS,1*SS),rpgWorld));//29
		platforms.add(new Platform(platforms.size,new Vector2(58*SS,3*SS),new Vector2(3*SS,3*SS),rpgWorld));//30  
		platforms.add(new Platform(platforms.size,new Vector2(52*SS,21*SS),new Vector2(9*SS,1*SS),rpgWorld));//31
		platforms.add(new Platform(platforms.size,new Vector2(61*SS,21*SS),new Vector2(2*SS,3*SS),rpgWorld));//32
		platforms.add(new Platform(platforms.size,new Vector2(60*SS,16*SS),new Vector2(1*SS,2*SS),rpgWorld));//33
		platforms.add(new Platform(platforms.size,new Vector2(61*SS,17*SS),new Vector2(9*SS,1*SS),rpgWorld));//34
		platforms.add(new Platform(platforms.size,new Vector2(65*SS,18*SS),new Vector2(5*SS,2*SS),rpgWorld));//35
		platforms.add(new Platform(platforms.size,new Vector2(67*SS,21*SS),new Vector2(3*SS,2*SS),rpgWorld));//36
		platforms.add(new Platform(platforms.size,new Vector2(70*SS,25*SS),new Vector2(4*SS,1*SS),rpgWorld));//37
		platforms.add(new Platform(platforms.size,new Vector2(62*SS,7*SS),new Vector2(28*SS,1*SS),rpgWorld));// 38
		platforms.add(new Platform(platforms.size,new Vector2(65*SS,5*SS),new Vector2(2*SS,2*SS),rpgWorld));//39
		platforms.add(new Platform(platforms.size,new Vector2(70*SS,5*SS),new Vector2(2*SS,2*SS),rpgWorld));//40
		platforms.add(new Platform(platforms.size,new Vector2(75*SS,5*SS),new Vector2(2*SS,2*SS),rpgWorld));//41
		platforms.add(new Platform(platforms.size,new Vector2(65*SS,8*SS),new Vector2(25*SS,1*SS),rpgWorld));//42
		platforms.add(new Platform(platforms.size,new Vector2(68*SS,9*SS),new Vector2(22*SS,1*SS),rpgWorld));//43
		platforms.add(new Platform(platforms.size,new Vector2(70*SS,10*SS),new Vector2(20*SS,1*SS),rpgWorld));//44
		platforms.add(new Platform(platforms.size,new Vector2(71*SS,11*SS),new Vector2(19*SS,1*SS),rpgWorld));//45
		platforms.add(new Platform(platforms.size,new Vector2(77*SS,12*SS),new Vector2(6*SS,9*SS),rpgWorld));//46
		platforms.add(new Platform(platforms.size,new Vector2(83*SS,12*SS),new Vector2(7*SS,3*SS),rpgWorld));//47
		platforms.add(new Platform(platforms.size,new Vector2(80*SS,5*SS),new Vector2(2*SS,2*SS),rpgWorld));//48
		platforms.add(new Platform(platforms.size,new Vector2(85*SS,5*SS),new Vector2(5*SS,2*SS),rpgWorld));//49
		platforms.add(new Platform(platforms.size,new Vector2(95*SS,15*SS),new Vector2(6*SS,1*SS),rpgWorld));//50
		platforms.add(new Platform(platforms.size,new Vector2(0*SS,0*SS),new Vector2(0*SS,0*SS),rpgWorld));//
		
		
		
		
		
		
		
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
