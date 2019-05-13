package Entities;

import java.io.Console;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;



/**
 * @author Slava
 * 
 * {@link silentiumslava@gmail.com}
 *
 */
public class Hero  extends Entities{
	
	public PlayerStats stats;
	
	private Animation<TextureRegion> teleportAnimation;
	private TextureRegion[] teleportFrames;
	private Texture skills;
	private Sound shift;
	Animation<TextureRegion> currentAnimation;
	Animation<TextureRegion>[] allAnimations;
	public void picParam() {
		//����� ���������� �������� � �����
				PIC_FRAME_COLS = 10;
				PIC_FRAME_ROWS = 10;
				//���������� �������� �����
				STAY_FRAME_COL = 0;
				STAY_FRAME_COLS = 10;	
				STAY_FRAME_ROW = 0;
				STAY_FRAME_ROWS = 1;
				//���������� �������� ��������
				MOVE_FRAME_COL = 0;
				MOVE_FRAME_COLS = 10;
				MOVE_FRAME_ROW = 2;
				MOVE_FRAME_ROWS = 1;
				//���������� �������� �����1
				ATTACK1_FRAME_COL = 0;
				ATTACK1_FRAME_COLS = 10;
				ATTACK1_FRAME_ROW = 3;
				ATTACK1_FRAME_ROWS = 1;
	}
	
	
	public Hero(Vector2 heroSize,Vector2 heroCoord) {
		preferences = Gdx.app.getPreferences("herostats");
		stats = new PlayerStats();
		setSize(heroSize);
		setCoord(heroCoord);
		sideView = 1; 
		setisAttacking(false);
		allSheets = new Texture(Gdx.files.internal("Hero.png"));
		
		picParam();
		
		//���������� ���������� ����� ��������� �� �����
		imageCollector = TextureRegion.split(allSheets,allSheets.getWidth()/PIC_FRAME_ROWS,allSheets.getHeight()/PIC_FRAME_COLS);
		//������������� ���� TextureRegion'�� 
		stayFrames = new TextureRegion[STAY_FRAME_COLS*STAY_FRAME_ROWS];
		moveFrames = new TextureRegion[MOVE_FRAME_COLS*MOVE_FRAME_ROWS];
		attack1Frames = new TextureRegion[ATTACK1_FRAME_COLS*ATTACK1_FRAME_ROWS];
		//���������� TextureRegion'�� ���������
		int index = 0;
		for(int i=0;i<STAY_FRAME_ROWS;i++)  //���������� �����
			for(int j=0;j<STAY_FRAME_COLS;j++) {
				stayFrames[index++] = imageCollector[i+STAY_FRAME_ROW][j+STAY_FRAME_COL];
			}
		index = 0;
		for(int i=0;i<MOVE_FRAME_ROWS;i++)  //���������� ��������
			for(int j=0;j<MOVE_FRAME_COLS;j++)
				moveFrames[index++] = imageCollector[i+MOVE_FRAME_ROW][j+MOVE_FRAME_COL];
		index = 0;
		//������������� ���������� ��������
		stayAnimation = new Animation<TextureRegion>(0.10f, stayFrames);
		moveAnimation = new Animation<TextureRegion>(0.10f,moveFrames);
		attack1Animation = new Animation<TextureRegion>(ANIMATION_SPEED*(1-stats.ATKSPEED()),attack1Frames);
		currentFrame = stayAnimation.getKeyFrame(0.10f, true);
		
		// ���������� �������� ������������
		shift = Gdx.audio.newSound(Gdx.files.internal("sprintSound.wav"));
		skills = new Texture(Gdx.files.internal("dark_skills.png"));
		TextureRegion[][] temp = TextureRegion.split(skills,skills.getWidth()/10,skills.getHeight()/10);
		teleportFrames = new TextureRegion[10];
		for(int i=0;i<10;i++) {
			teleportFrames[i] = temp[0][i];
			attack1Frames[i] = temp[1][i];
		}
		teleportAnimation = new Animation<TextureRegion>(ANIMATION_SPEED*(1-stats.ATKSPEED()),teleportFrames);
		
		
	}

	
	// -1 - ����
	// 0 - ��������� �����, ������� ��������		1 - �����1	
	// 2 - ��������									3 -  			
	@Override
	public void update(float delta) {
		
		 if(coordX < 0)
			coordX = 0;
		 if(currentAction!=0) {
			 if(checkStop())
				 return;
			 if(switchAction())
				 return;
		 }
		 checkKeys();
		 if(Gdx.input.isKeyPressed(Keys.D) || Gdx.input.isKeyPressed(Keys.RIGHT)) {   //�������� ������
				
				currentFrame = moveAnimation.getKeyFrame(delta, true);
				
				if(currentFrame.isFlipX()) {
					currentFrame.flip(true, false);
				}
				coordX += 500 * Gdx.graphics.getDeltaTime();
				sideView = 1;
				return;	
			}
			if(Gdx.input.isKeyPressed(Keys.A) || Gdx.input.isKeyPressed(Keys.LEFT)) {    //�������� �����
				
				currentFrame = moveAnimation.getKeyFrame(delta, true);
				
				if(!currentFrame.isFlipX())
					currentFrame.flip(true, false);
				coordX -= 500 * Gdx.graphics.getDeltaTime();
				sideView = -1;
				
				return;
			}
			currentFrame = stayAnimation.getKeyFrame(delta,true);
			frameFlip();
			
	}
			
	public boolean switchAction() {
		switch(currentAction) {
		case 1:attack1();   	break;
		case 2:teleport();	    break;
		case 3:   break;
		default: return false;
		}
		return true;
	}
	/**
	 *  �������� �� ������� ������.
	 *  ���� ����������� ��� ����� ������
	 */
	public void checkKeys() { 	//���� ��������� ����� ������
		
		//����1 
		if(Gdx.input.isKeyJustPressed(Keys.F)) {
			currentAction = 1;
			currentAnimation = attack1Animation;
			attack1();
			return;
		}
		//�����
		if(Gdx.input.isKeyJustPressed(Keys.SHIFT_LEFT)) {
			currentAction = 2;
			currentAnimation = teleportAnimation;
			teleport();
			return;
		}
	}
	/** ��������� ������ �� S(������� ���������), ���� �� �� ���������� ��������
	 * @return
	 * ���� ������� ���� ������ �� true
	 */
	private boolean checkStop() {
		if(Gdx.input.isKeyPressed(Keys.S)) {
			reset();
			return true;
		}
		return false;	
	}
	/**
	 * ��������� ���� ��������
	 * @return
	 * ���������� true ���� �������� ���������, ��� ���� ���������� �����
	 */
	public boolean refresh() { 
		currentFrame = currentAnimation.getKeyFrame(CURRENT_DURATION, false);
		if(currentFrame == currentAnimation.getKeyFrames()[currentAnimation.getKeyFrames().length-1]) {
			CURRENT_DURATION = 0.0f;
			reset();
			return true;
		}
		CURRENT_DURATION+=Gdx.graphics.getDeltaTime();	
		return false;
	}
	public void attack1() {
		if(refresh())
			return;
		if(currentFrame == currentAnimation.getKeyFrames()[4] || currentFrame == currentAnimation.getKeyFrames()[5] || currentFrame == currentAnimation.getKeyFrames()[6])
			coordX+=200*Gdx.graphics.getDeltaTime()*sideView;
		frameFlip();
		return;
		
	}
	public void teleport() {
		if(refresh())
			return;
		if(currentFrame == currentAnimation.getKeyFrames()[4] || currentFrame == currentAnimation.getKeyFrames()[5] || currentFrame == currentAnimation.getKeyFrames()[6]) {
			coordX+=1000*Gdx.graphics.getDeltaTime()*sideView/(1-stats.ATKSPEED());
			
		}
		frameFlip();
		return;
	}
	/**
	 * ���������� �������� � ���� � ��������� �����
	 */
	public void reset() {
		currentAnimation = stayAnimation;
		currentFrame = stayAnimation.getKeyFrames()[0];
		currentAction = 0;
	}
	/**
	 *  ���� ���� �� ����� ��� �������� ���� �����, �� ��������� ���� ��� �������� � ������ �������
	 */
	public void frameFlip() {
		if(sideView == 1)
		{
			if(!currentFrame.isFlipX()) {											    
				currentFrame.flip(false, false);	
			}
			else {
				currentFrame.flip(true, false);
			}
		}
		else {
			if(currentFrame.isFlipX()) {
			
				currentFrame.flip(false, false);
			}
			else {
			
				currentFrame.flip(true, false);
			}
		}
	}
	@Override
	public void dispose() {
		allSheets.dispose();
		stayFrames = null;
		imageCollector = null;
		stayAnimation = null;
	}
	@Override
	protected void loadPreferences() {
		HITPOINT = preferences.getFloat("HITPOINT", 100.0f);
		DAMAGE = preferences.getFloat("DAMAGE", 1.0f);
		ARMOR = preferences.getFloat("ARMOR", 0.0f);
		MANA = preferences.getFloat("MANA", 100.0f);
		
	}
	@Override
	public void resetPreferences() {
		preferences.clear();
		HITPOINT = preferences.getFloat("HITPOINT", 100.0f);
		DAMAGE = preferences.getFloat("DAMAGE", 1.0f);
		ARMOR = preferences.getFloat("ARMOR", 0.0f);
		MANA = preferences.getFloat("MANA", 100.0f);
	}
	/* (non-Javadoc)
	 * @see Entities.Entities#getDamage()
	 */
	@Override
	public float getDamage() {
		return DAMAGE;
		
	}
}
