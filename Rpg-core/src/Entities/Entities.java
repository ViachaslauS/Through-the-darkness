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
	
	
	protected Preferences preferences;
	//� ����� ������� ���������� ��������
	protected int sideView;  // 1 - ������, -1 - �����
	private boolean isAttacking;
	//���������� ��������
	protected float coordX;
	protected float coordY;
	protected float coordZ;
	//������ ��������
	protected float sizeX;
	protected float sizeY;
	
	protected float ANIMATION_SPEED = 0.1f;
	
	//������� ����
	public TextureRegion currentFrame;
	//��� ������� �� 1 �����
	protected Texture allSheets;
	//����� ���������� �������� � ��������
	protected static int PIC_FRAME_COLS;
	protected static int PIC_FRAME_ROWS;
	protected TextureRegion[][] imageCollector;
	
	// ���������� ����������� �������� ����� � ��������
	protected static int STAY_FRAME_COLS;  //���������� ������
	protected static int STAY_FRAME_COL;   //� ����� ������� �������� ����������
	protected static int STAY_FRAME_ROWS;  //���������� �����
	protected static int STAY_FRAME_ROW;   //� ����� ������
	//�������� �����
	protected Animation<TextureRegion> stayAnimation;
	
	// ���������� �������� ��������
	protected static int MOVE_FRAME_COLS;
	protected static int MOVE_FRAME_COL;
	protected static int MOVE_FRAME_ROWS;
	protected static int MOVE_FRAME_ROW;
	//�������� ��������
	protected Animation<TextureRegion> moveAnimation;
	
	// ������� �����
	protected static int ATTACK1_FRAME_COLS;
	protected static int ATTACK1_FRAME_COL;
	protected static int ATTACK1_FRAME_ROWS;
	protected static int ATTACK1_FRAME_ROW;
	
	protected float CURRENT_DURATION = 0.0f;
	//�������� �����1
	protected Animation<TextureRegion> attack1Animation;
	
	protected TextureRegion[] stayFrames;
	protected TextureRegion[] moveFrames;
	protected TextureRegion[] attack1Frames;
	
	/**
	 *  ��� ����� ��������� ��������
	 *  -1 - ����/
	 *	0 - ��������� �����, ������� ��������/		1 - �����1/	
	 *	2 - ��������/								3 -  			
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

	/** �������� ����

	 * @param damage ��������, �������������� ���������� �����
	 */
	public void giveDamage(float damage) {
		HITPOINT-=(damage*((ARMOR+1)/100));
	}
	/**
	 * @return ���� ��������
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

	public boolean getisAttacking() {
		return isAttacking;
	}

	public void setisAttacking(boolean isAttacking) {
		this.isAttacking = isAttacking;
	}
}
