package Entities;

import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Entities {
	
	protected float HITPOINT;
	public float getHITPOINT() {
		return HITPOINT;
	}

	public float getMANA() {
		return MANA;
	}

	public float getARMOR() {
		return ARMOR;
	}

	public float getDAMAGE() {
		return DAMAGE;
	}
	protected float MANA;
	protected float ARMOR;
	protected float DAMAGE;
	
	
	Preferences preferences;
	//В какую сторону направлена сущность
	protected int sideView;  // 1 - вправо, -1 - влево
	protected boolean isAttacking;
	//Координаты сущности
	protected float coordX;
	protected float coordY;
	protected float coordZ;
	//Размер сущности
	protected float sizeX;
	protected float sizeY;
	
	protected float ANIMATION_SPEED = 0.1f;
	
	//Текущий кадр
	public TextureRegion currentFrame;
	//Все спрайты из 1 листа
	protected Texture allSheets;
	//Общее количество спрайтов в картинке
	protected static int PIC_FRAME_COLS;
	protected static int PIC_FRAME_ROWS;
	TextureRegion[][] imageCollector;
	
	// Количество необходимых спрайтов покоя в картинке
	protected static int STAY_FRAME_COLS;  //Количество колонн
	protected static int STAY_FRAME_COL;   //С какой колонны начинать заполнение
	protected static int STAY_FRAME_ROWS;  //Количество строк
	protected static int STAY_FRAME_ROW;   //С какой строки
	//Анимация покоя
	protected Animation<TextureRegion> stayAnimation;
	
	// Количество спрайтов движения
	protected static int MOVE_FRAME_COLS;
	protected static int MOVE_FRAME_COL;
	protected static int MOVE_FRAME_ROWS;
	protected static int MOVE_FRAME_ROW;
	//Анимация движения
	protected Animation<TextureRegion> moveAnimation;
	
	// Спрайты атаки
	protected static int ATTACK1_FRAME_COLS;
	protected static int ATTACK1_FRAME_COL;
	protected static int ATTACK1_FRAME_ROWS;
	protected static int ATTACK1_FRAME_ROW;
	
	protected float CURRENT_DURATION = 0.0f;
	//Анимация атаки1
	protected Animation<TextureRegion> attack1Animation;
	
	protected TextureRegion[] stayFrames;
	protected TextureRegion[] moveFrames;
	protected TextureRegion[] attack1Frames;
	
	/**
	 *  это набор вариантов анимаций
	 *  -1 - Стоп/
	 *	0 - Состояние покоя, простое движение/		1 - Атака1/	
	 *	2 - Телепорт/								3 -  			
	 */
	protected int currentAction = 0;
	
	public void update(float delta) {
	
	}
	
	public void dispose() {
		
	}
	
	public void setSize(Vector2 newSize) {
		sizeX = newSize.x;
		sizeY = newSize.y;
	}
	public void setCoord(Vector2 newCoord) {
		coordX = newCoord.x;
		coordY = newCoord.y;
	}
	public float getCoordX() {
		return coordX;
	}

	public void setCoordX(int coordX) {
		this.coordX = coordX;
	}

	public float getCoordY() {
		return coordY;
	}

	public void setCoordY(int coordY) {
		this.coordY = coordY;
	}

	public float getCoordZ() {
		return coordZ;
	}

	public void setCoordZ(int coordZ) {
		this.coordZ = coordZ;
	}
	
	public float getSizeX() {
		return sizeX;
	}

	public void setSizeX(float sizeX) {
		this.sizeX = sizeX;
	}

	public float getSizeY() {
		return sizeY;
	}

	public void setSizeY(float sizeY) {
		this.sizeY = sizeY;
	}

	/** Получить урон

	 * @param damage параметр, соответстующий наносимому урону
	 */
	public void giveDamage(float damage) {
		HITPOINT-=(damage*((ARMOR+1)/100));
	}
	/**
	 * @return Урон сущности
	 */
	public float getDamage() {
		return DAMAGE;
	}
	public float getHitPoints() {
		return HITPOINT;
	}
	public void resetPreferences() {
		preferences.clear();
		preferences.putFloat("HITPOINT", 100.0f);
		preferences.putFloat("ARMOR", 0.0f);
		preferences.putFloat("MANA", 100.0f);
		preferences.putFloat("DAMAGE", 1.0f);
	}
	protected void loadPreferences() {
		HITPOINT = preferences.getFloat("HITPOINT", 100.0f);
		DAMAGE = preferences.getFloat("DAMAGE", 1.0f);
		ARMOR = preferences.getFloat("ARMOR", 0.0f);
		MANA = preferences.getFloat("MANA", 100.0f);
	}
}
