package Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;



public class Hero  extends Entities{

	public Hero(Vector2 heroSize,Vector2 heroCoord) {
		
		setSize(heroSize);
		setCoord(heroCoord);
		sideView = true; 
		isAttacking = false;
		allSheets = new Texture(Gdx.files.internal("Hero.png"));
		//Общее количество спрайтов в листе
		PIC_FRAME_COLS = 10;
		PIC_FRAME_ROWS = 10;
		//Количество спрайтов покоя
		STAY_FRAME_COL = 0;
		STAY_FRAME_COLS = 10;	
		STAY_FRAME_ROW = 0;
		STAY_FRAME_ROWS = 1;
		//Количество спрайтов движения
		MOVE_FRAME_COL = 0;
		MOVE_FRAME_COLS = 10;
		MOVE_FRAME_ROW = 2;
		MOVE_FRAME_ROWS = 1;
		//Количество спрайтов атаки1
		ATTACK1_FRAME_COL = 0;
		ATTACK1_FRAME_COLS = 10;
		ATTACK1_FRAME_ROW = 3;
		ATTACK1_FRAME_ROWS = 1;
		//Заполнение собирателя всеми текстурам из листа
		imageCollector = TextureRegion.split(allSheets,allSheets.getWidth()/PIC_FRAME_ROWS,allSheets.getHeight()/PIC_FRAME_COLS);
		//Инициализация всех TextureRegion'ов 
		stayFrames = new TextureRegion[STAY_FRAME_COLS*STAY_FRAME_ROWS];
		moveFrames = new TextureRegion[MOVE_FRAME_COLS*MOVE_FRAME_ROWS];
		attack1Frames = new TextureRegion[ATTACK1_FRAME_COLS*ATTACK1_FRAME_ROWS];
		//Заполнение TextureRegion'ов спрайтами
		int index = 0;
		for(int i=0;i<STAY_FRAME_ROWS;i++)  //Заполнение покоя
			for(int j=0;j<STAY_FRAME_COLS;j++) {
				stayFrames[index++] = imageCollector[i+STAY_FRAME_ROW][j+STAY_FRAME_COL];
			}
		index = 0;
		for(int i=0;i<MOVE_FRAME_ROWS;i++)  //Заполнение движения
			for(int j=0;j<MOVE_FRAME_COLS;j++)
				moveFrames[index++] = imageCollector[i+MOVE_FRAME_ROW][j+MOVE_FRAME_COL];
		index = 0;
		for(int i=0; i<ATTACK1_FRAME_ROWS;i++)
			for(int j=0;j<ATTACK1_FRAME_COLS;j++)
				attack1Frames[index++] = imageCollector[i+ATTACK1_FRAME_ROW][j+ATTACK1_FRAME_COL];
		//Инициализация переменных анимации
		stayAnimation = new Animation<TextureRegion>(0.10f, stayFrames);
		moveAnimation = new Animation<TextureRegion>(0.10f,moveFrames);
		attack1Animation = new Animation<TextureRegion>(ATTACK_SPEED,attack1Frames);
		currentFrame = stayAnimation.getKeyFrame(0.0f, true);
	}
	
	@Override
	public void update(float delta) {
		if((currentFrame == attack1Animation.getKeyFrames()[ATTACK1_FRAME_COLS*ATTACK1_FRAME_ROWS-1]) || Gdx.input.isKeyJustPressed(Keys.S)) {
			ATTACK1_DURATION = 0.0f;
			isAttacking = false;
		}
			if(Gdx.input.isKeyJustPressed(Keys.F)) {
				isAttacking = true;
			}
			if(isAttacking)
			{
				currentFrame = attack1Animation.getKeyFrame(ATTACK1_DURATION, false);
				ATTACK1_DURATION+=Gdx.graphics.getDeltaTime();
				if(sideView) {                                                                 //я хз ваще, но работает
					if(!currentFrame.isFlipX())												   // крч поворот в сторону в кот. шли 
						currentFrame.flip(false, false); 
					else
						currentFrame.flip(true, false);
				}
				else {
					if(currentFrame.isFlipX())
						currentFrame.flip(false, false);
					else
						currentFrame.flip(true, false);
				}	
				return;
			}
			else
			{
				if(Gdx.input.isKeyPressed(Keys.D) || Gdx.input.isKeyPressed(Keys.RIGHT)) {   //движение вправо
					
					currentFrame = moveAnimation.getKeyFrame(delta, true);
					
					if(currentFrame.isFlipX()) {
						currentFrame.flip(true, false);
					}
					coordX += 500 * Gdx.graphics.getDeltaTime();
					sideView = true;
					return;	
				}
				if(Gdx.input.isKeyPressed(Keys.A) || Gdx.input.isKeyPressed(Keys.LEFT)) {    //движение влево
					
					currentFrame = moveAnimation.getKeyFrame(delta, true);
					
					if(!currentFrame.isFlipX())
						currentFrame.flip(true, false);
					coordX -= 500 * Gdx.graphics.getDeltaTime();
					if(coordX <0)				 //При подходе к границе
						 coordX = 0;
					sideView = false;
					
					return;
				}
			}
			currentFrame = stayAnimation.getKeyFrame(delta, true);							//выбор текущего кадра -- в самый низ
			if(sideView) {                                                                 //я хз ваще, но работает
				if(!currentFrame.isFlipX())												   // крч поворот в сторону в кот. шли 
					currentFrame.flip(false, false); 
				else
					currentFrame.flip(true, false);
			}
			else {
				if(currentFrame.isFlipX())
					currentFrame.flip(false, false);
				else
					currentFrame.flip(true, false);
			}
			
		}
	@Override
	public void dispose() {
		allSheets.dispose();
		stayFrames = null;
		imageCollector = null;
		stayAnimation = null;
	}
}
