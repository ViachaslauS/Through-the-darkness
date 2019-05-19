package Engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.RPG;

public class UserInterface {

	

	TextureRegion fullHP;
	TextureRegion fullMANA, emptyBar;
	TextureRegion[][] allBars;
	
	Texture attack1;
	public UserInterface() {
		
		attack1 = new Texture(Gdx.files.internal("attack1.png"));
		
		Texture temp = new Texture(Gdx.files.internal("bars.png"));
		allBars = TextureRegion.split(temp, temp.getWidth(), temp.getHeight()/4);
		fullHP = allBars[2][0];
		fullMANA = allBars[3][0];
		
		emptyBar = allBars[0][0];
		
	}
	
	public void draw(SpriteBatch batch, ObjectData objectData) {
		batch.draw(emptyBar, 20,720-70,objectData.MAXHITPOINT*3,70);
		batch.draw(fullHP, 40,720-70,objectData.getHITPOINT()*3,70);
		//batch.draw(fullHP, 20, Gdx.graphics.getHeight()-50,objectData.MAXHITPOINT*5,50, objectData.HITPOINT*5, 50, 1, 1, 0, false);
		batch.draw(emptyBar,20,720-110,objectData.MAXMANA*3,70);
		batch.draw(fullMANA, 40,720-110,objectData.MANA*3,70);
		
		batch.draw(attack1, 10, 10,75,75);
	}
	
}
