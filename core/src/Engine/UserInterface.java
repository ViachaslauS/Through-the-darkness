package Engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.RPG;

import Entities.Buff;

public class UserInterface {

	

	TextureRegion fullHP;
	TextureRegion fullMANA, emptyBar;
	TextureRegion[][] allBars;
	boolean[][] isDrawed;
	BitmapFont font;
	Texture[][] buffIcons;
	Texture attack1;
	public UserInterface() {
		
		attack1 = new Texture(Gdx.files.internal("attack1.png"));
		
		Texture temp = new Texture(Gdx.files.internal("bars.png"));
		allBars = TextureRegion.split(temp, temp.getWidth(), temp.getHeight()/4);
		fullHP = allBars[2][0];
		fullMANA = allBars[3][0];
		
		emptyBar = allBars[0][0];
		isDrawed = new boolean[10][2];
		buffIcons = new Texture[10][2];
		
		buffIcons[0][0] = new Texture(Gdx.files.internal("power1.png"));
		buffIcons[0][1] = new Texture(Gdx.files.internal("power2.png"));
		buffIcons[1][0] = new Texture(Gdx.files.internal("agility1.png"));
		buffIcons[1][1] = new Texture(Gdx.files.internal("agility2.png"));
		buffIcons[2][0] = new Texture(Gdx.files.internal("intel1.png"));
		buffIcons[2][1] = new Texture(Gdx.files.internal("intel2.png"));
		buffIcons[3][0] = new Texture(Gdx.files.internal("HPregen.png"));
		buffIcons[3][1] = new Texture(Gdx.files.internal("HPregen.png"));
		buffIcons[4][0] = new Texture(Gdx.files.internal("MANAregen.png"));
		buffIcons[4][1] = new Texture(Gdx.files.internal("MANAregen.png"));
		buffIcons[5][0] = new Texture(Gdx.files.internal("damage1.png"));
		buffIcons[5][1] = new Texture(Gdx.files.internal("damage2.png"));
		buffIcons[6][0] = new Texture(Gdx.files.internal("armor1.png"));
		buffIcons[6][1] = new Texture(Gdx.files.internal("armor2.png"));
		buffIcons[7][0] = new Texture(Gdx.files.internal("regspeed1.png"));
		buffIcons[7][1] = new Texture(Gdx.files.internal("regspeed2.png"));
		
		font = new BitmapFont(Gdx.files.internal("UI.fnt"));
	}
	
	public void draw(SpriteBatch batch, ObjectData objectData) {
		coordsX = fullHP.getTexture().getWidth()+40;
		clearDrawed();
		batch.draw(emptyBar, 20,900-70,objectData.MAXHITPOINT*3,70);
		batch.draw(fullHP, 40,900-70,objectData.getHITPOINT()*3,70);
		//batch.draw(fullHP, 20, Gdx.graphics.getHeight()-50,objectData.MAXHITPOINT*5,50, objectData.HITPOINT*5, 50, 1, 1, 0, false);
		batch.draw(emptyBar,20,900-110,objectData.MAXMANA*3,70);
		batch.draw(fullMANA, 40,900-110,objectData.MANA*3,70);
		font.draw(batch,"Level "+objectData.stats.getLevel(),25,790);
		font.draw(batch,""+objectData.stats.getExp()+"/"+objectData.stats.getMaxExp()+" Experience",25,755);
		//batch.draw(attack1, 10, 10,75,75);
		
		//int counter = 0;
		for(int i = 0; i < objectData.getBuffs().size; i++) {
			drawBuff(batch,objectData.getBuffs().get(i));
		}
	}

	private void clearDrawed() {
		for(int i = 0; i < 10;i++) {
			for(int j = 0; j < 2;j++)
				isDrawed[i][j] = false;
		}
	}

	private void drawBuff(SpriteBatch batch, Buff buff) {
		switch(buff.type) {
		case POWER:drawOneBuff(batch,0,buff.isCycle);break;
		case AGILITY: drawOneBuff(batch,1,buff.isCycle);break;
		case INTELLIGENCY:drawOneBuff(batch,2,buff.isCycle);break;
		case HITPOINTS:drawOneBuff(batch,3,buff.isCycle);break;
		case MANA:drawOneBuff(batch,4,buff.isCycle);break;
		case DAMAGE:drawOneBuff(batch,5,buff.isCycle);break; 
		case ARMOR:drawOneBuff(batch,6,buff.isCycle);break;
		case REGEN_FREQUENCY:drawOneBuff(batch,7,buff.isCycle);break;
		}
	}
	private float coordsX = 0.0f;
	private void drawOneBuff(SpriteBatch batch, int i,boolean type) {
		if(type) {
			if(isDrawed[i][1] == true)
				return;
			isDrawed[i][1] = true;
			batch.draw(buffIcons[i][1], coordsX,900-60,50,50);
		}
		else {
			if(isDrawed[i][0] == true)
				return;
			isDrawed[i][0] = true;
			batch.draw(buffIcons[i][0],coordsX,900-60,50,50);
		}
		coordsX+=60;
	}
	
}
