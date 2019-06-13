package Levels;

import java.util.ArrayList;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.RPG;

import Environment.ActivatingObject;
import Environment.MovementPlatform;
import Environment.Platform;
import Environment.SecretPlatform;
import Environment.ShowablePlatform;
import aiall.AiCustom;
import aiall.Level1Ai;
import aiall.Level2Ai;
import aiall.Level3Ai;

public class Level1 extends BaseLevel{
	
	public Level1(RPG game) {
		super(game);
	}
	
	
	//@Override
	public ArrayList<AiCustom> createEnemy() {
		 bots = new ArrayList<AiCustom>();
		 	bots.add(new Level3Ai(new Vector2(300.0f,300.0f) , new Vector2(5800.0f,180.0f),3, rpgWorld));
			bots.add(new Level1Ai(new Vector2(150.0f,150.0f) , new Vector2(1100.0f,180.0f),1));
			bots.add(new Level1Ai(new Vector2(150.0f,150.0f) , new Vector2(1500.0f,150.0f),1));
			bots.add(new Level2Ai(new Vector2(150.0f,150.0f) , new Vector2(2100.0f,150.0f),2));
			bots.add(new Level1Ai(new Vector2(150.0f,150.0f) , new Vector2(2400.0f,210.0f),1));
			bots.add(new Level2Ai(new Vector2(150.0f,150.0f) , new Vector2(2900.0f,150.0f),2));
			bots.add(new Level2Ai(new Vector2(150.0f,150.0f) , new Vector2(3000.0f,150.0f),2));
			bots.add(new Level1Ai(new Vector2(150.0f,150.0f) , new Vector2(3700.0f,150.0f),1));
			bots.add(new Level2Ai(new Vector2(150.0f,150.0f) , new Vector2(4400.0f,150.0f),2));
			bots.add(new Level2Ai(new Vector2(150.0f,150.0f) , new Vector2(5200.0f,150.0f),2));
			return bots;
	}	
	
	//@Override
	public Array<Platform> createEnvironment() {
		platforms = new Array<Platform>();
		platforms.add(new Platform(platforms.size,new Vector2(0,-30), new Vector2(10000000,100),rpgWorld)); // Earth platform
		platforms.add( new Platform(platforms.size,new Vector2(1200,150), new Vector2(150,30), rpgWorld));
		platforms.add( new Platform(platforms.size,new Vector2(1500,100), new Vector2(150,300), rpgWorld));
		platforms.add( new Platform(platforms.size,new Vector2(2200,150), new Vector2(150,30), rpgWorld));
		platforms.add( new Platform(platforms.size,new Vector2(2500,220), new Vector2(150,30), rpgWorld));
		platforms.add( new Platform(platforms.size,new Vector2(2900,280), new Vector2(400,30), rpgWorld));
		platforms.add( new Platform(platforms.size,new Vector2(3900,200), new Vector2(150,30), rpgWorld));
		platforms.add( new Platform(platforms.size,new Vector2(4100,350), new Vector2(150,30), rpgWorld));
		platforms.add( new Platform(platforms.size,new Vector2(4250,450), new Vector2(150,30), rpgWorld)); 
		//platforms.add( new Platform(platforms.size,new Vector2(5400,0), new Vector2(20,1000), rpgWorld));
		platforms.add( new Platform(platforms.size,new Vector2(0,450), new Vector2(10,1000), rpgWorld));
		platforms.add(new Platform(platforms.size, new Vector2(SS*2,SS*3), new Vector2(SS,SS), rpgWorld));
		return platforms;
		}
	//@Override
	public  void managerLoad() {
		assetManager.load("Battleground1.png", Texture.class);
		assetManager.load("Hero.png", Texture.class);		
		assetManager.load("1.mp3",Music.class);
		assetManager.load("2.mp3",Music.class);
		assetManager.load("3.mp3",Music.class);
		assetManager.load("4.mp3",Music.class);
		assetManager.load("5.mp3",Music.class);		
		assetManager.load("attack1.wav",Sound.class);
		assetManager.load("teleport.wav",Sound.class);
		assetManager.load("attack2.wav",Sound.class);
		assetManager.load("magic.wav",Sound.class);
		assetManager.load("move.wav",Sound.class);		
		assetManager.load("buttonclick.wav",Sound.class);
		assetManager.load("gamebutton.wav",Sound.class);	
		assetManager.load("dead.mp3",Sound.class);
		assetManager.load("gameover.wav",Sound.class);
		assetManager.load("trigger1.wav",Sound.class);
		assetManager.load("trigger2.wav",Sound.class);
		assetManager.load("trigger3.wav",Sound.class);
		assetManager.load("trigger4.wav",Sound.class);
		assetManager.load("trigger5.wav",Sound.class);
		assetManager.load("trigger6.mp3",Sound.class);
		assetManager.load("skelet.wav",Sound.class);
		assetManager.load("dark_skills.png",Texture.class);
		assetManager.load("niceBG.jpg",Texture.class);
		assetManager.load("woodenBG.jpg",Texture.class);
		assetManager.load("buttons.png",Texture.class);
		assetManager.load("menu_font.fnt",BitmapFont.class);
		assetManager.load("armor_blocked.png", Texture.class);
		assetManager.load("armor_earned.png", Texture.class);
		assetManager.load("armor_unearned.png", Texture.class);
		assetManager.load("armor2_blocked.png", Texture.class);
		assetManager.load("armor2_earned.png", Texture.class);
		assetManager.load("armor2_unearned.png", Texture.class);
		assetManager.load("buff_blocked.png", Texture.class);
		assetManager.load("buff_earned.png", Texture.class);
		assetManager.load("buff_unearned.png", Texture.class);
		assetManager.load("cooldown_blocked.png", Texture.class);
		assetManager.load("cooldown_earned.png", Texture.class);		
		assetManager.load("cooldown_unearned.png", Texture.class);
		assetManager.load("duration_blocked.png", Texture.class);
		assetManager.load("duration_earned.png", Texture.class);
		assetManager.load("duration_unearned.png", Texture.class);
		assetManager.load("HP_earned.png", Texture.class);
		assetManager.load("HP_unearned.png", Texture.class);
		assetManager.load("HP_blocked.png", Texture.class);
		assetManager.load("magic_blocked.png", Texture.class);
		assetManager.load("magic_earned.png", Texture.class);
		assetManager.load("magic_unearned.png", Texture.class);
		assetManager.load("MANA_earned.png", Texture.class);
		assetManager.load("MANA_unearned.png", Texture.class);
		assetManager.load("MANA_blocked.png", Texture.class);
		assetManager.load("max_blocked.png", Texture.class);
		assetManager.load("max_earned.png", Texture.class);
		assetManager.load("max_unearned.png", Texture.class);
		assetManager.load("regen_blocked.png", Texture.class);
		assetManager.load("regen_earned.png", Texture.class);
		assetManager.load("regen_unearned.png", Texture.class);
		assetManager.load("teleport_blocked.png", Texture.class);
		assetManager.load("teleport_earned.png", Texture.class);
		assetManager.load("teleport_unearned.png", Texture.class);
		assetManager.load("tripple_blocked.png", Texture.class);
		assetManager.load("tripple_earned.png", Texture.class);
		assetManager.load("tripple_unearned.png", Texture.class);
		assetManager.load("vampire_blocked.png", Texture.class);
		assetManager.load("vampire_earned.png", Texture.class);
		assetManager.load("vampire_unearned.png", Texture.class);
		assetManager.load("power_unearned.png", Texture.class);
		assetManager.load("power_blocked.png", Texture.class);
		assetManager.load("aghility_unearned.png", Texture.class);
		assetManager.load("aghility_blocked.png", Texture.class);
		assetManager.load("intellegens_unearned.png", Texture.class);
		assetManager.load("intellegens_blocked.png", Texture.class);
		assetManager.load("startImage.png",Texture.class);
		assetManager.load("buffSound.wav",Sound.class);
	}

}
