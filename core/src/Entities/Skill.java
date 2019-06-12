package Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Skill {

	public String name;
	public String fileName;
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
		this.fileName = fileName;
		available = new Texture(Gdx.files.internal(fileName+"_earned.png"));
		unEarned = new Texture(Gdx.files.internal(fileName+"_unearned.png"));
	//	blocked = new Texture(Gdx.files.internal(fileName+"_blocked.png"));
	}
	
	public void update() {
		if(isEarned) {
		if(time <= 0) {
			time = 0;
			isAvailable = true;
		}
		if(time > 0 ) {
			isAvailable = false;
			time -=Gdx.graphics.getDeltaTime();
		}
	}
		
}
	public String getfileName() {
		return this.fileName;
	}
	public void setCooldown() {
		time = coolDown;
	}

 }
