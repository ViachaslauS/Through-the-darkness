package Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Skill {

	public String name;
	
	public float coolDown;
	public float time;
	
	public boolean isAvailable;
	
	public boolean isEarned;
	
	public boolean isActive = false;
	
	public Texture available,unEarned,blocked;
	
	public Skill(String name,float coolDown,boolean isAvailable,boolean isEarned,String fileName) {
		this.name = name;
		this.coolDown = coolDown;
		this.isAvailable = isAvailable;
		this.isEarned = isEarned;
		
		available = new Texture(Gdx.files.internal(fileName+"_earned.png"));
		unEarned = new Texture(Gdx.files.internal(fileName+"_unearned.png"));
		blocked = new Texture(Gdx.files.internal(fileName+"_blocked.png"));
	}
	
	public void update() {
		if(time < 0) {
			time = 0;
			isActive = false;
		}
		if(time > 0 && isActive)
			time -=Gdx.graphics.getDeltaTime();
	}

 }
