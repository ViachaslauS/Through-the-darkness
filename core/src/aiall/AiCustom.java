package aiall;

import java.sql.Time;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.steer.behaviors.FollowPath;
import com.badlogic.gdx.ai.steer.utils.Path;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import Engine.RPGWorld;
import Entities.Bullet;
import Entities.Entities;

public class AiCustom extends Entities {
	//public float sideView;
	//boolean isAttaking;
	Vector3 all;
	Vector2 _coord;
	private float time;
	private float bulletTime = 0;
	SteeringAgent steeringAgent;
	
	public ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	private float attack_time=0;
	
	Filter f = new Filter();
	
	
	//FollowPath followPath;
	Path<Vector2, Pathparametrs> path;
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
	
	
	public AiCustom(Vector2 size,Vector2 coord, int id) {
		super("aistats"+id);
		
		//preferences = Gdx.app.getPreferences("aistats"+ id);
		setSize(size);
		setCoord(coord);
		_coord = coord;
		coordX = coord.x;
		coordY = coord.y;
		sideView = 1;
		entitieData.isAi = true;
		//sideView = (float) sideView;
		//isAttacking = false;
		allSheets = new Texture(Gdx.files.internal("Hero.png"));
		steeringAgent = new SteeringAgent(coord, sideView, 0);
		//followPath = new FollowPath<Vector2, Pathparametrs>(steeringAgent, path);
		f.maskBits = RPGWorld.MASK_RUNNER;
		f.categoryBits = RPGWorld.CATEGORY_RUNNER;
		f.groupIndex = -1;
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
		currentFrame = stayAnimation.getKeyFrame(0.10f, true);
			
	}
	
	  public void update() {
		 
		  time+= Gdx.graphics.getDeltaTime();
		  entitieData.attackTime += Gdx.graphics.getDeltaTime();
		  bulletTime += Gdx.graphics.getDeltaTime();
		  if(bulletTime >= 5 && entitieData.isAttacking == -1) {
			  bulletTime = 0;
			  shoot();
		  }
		  
		  
		  if(time>=1) {
			  
			  jump();
		  }
		  for(int i = 0; i< bullets.size(); i++)
				 bullets.get(i).update(Gdx.graphics.getDeltaTime());
		  
		  if(entitieData.isAttacking == -1) {
		 all = steeringAgent.update(this.sideView);
		 move(all.y);
		 
		 sideView = (int) all.x;
		 
		 }
		  updatePhysic();
		  entitieData.updateData();
		  
		  
	  }
	  private boolean isJump = false;
	  public void jump() {
		 
		  time = 0;
		  if(isJump) {
				if(isEntitieGrounded()) {
					isJump = false;
				}
				return;
			}
			if(isEntitieGrounded()) {
				isJump = true;
				entitieBox.applyLinearImpulse(new Vector2(0,1100), new Vector2(coordX,coordY), true);
			}
		  
	  }


	public void setAttacking(int b) {
		entitieData.isAttacking = b;
		
	}
	 @Override
	protected void bodyInitialize() {
		 CircleShape circlePolygon = new CircleShape();
			circlePolygon.setRadius(300);
			circlePolygon.setPosition(new Vector2(75,coordY-75));
			//Gdx.app.log("Sprite Coord", ""+ entitieBox.getGravityScale());
			sensorFixture = entitieBox.createFixture(circlePolygon,0f);
			sensorFixture.setUserData(entitieData);
			sensorFixture.setSensor(true);
			circlePolygon.dispose();
			
				PolygonShape polygon = new PolygonShape();
				polygon.setAsBox(38, 75,new Vector2(75,coordY-75),0);
				physicsFixture = entitieBox.createFixture(polygon, 0.0f);
				polygon.dispose();
				physicsFixture.setDensity(10000);
				physicsFixture.setSensor(false);
				physicsFixture.setUserData(sensorFixture);
				entitieBox.setBullet(true);
				entitieBox.setGravityScale(1000f);
				entitieBox.setTransform(coordX, coordY, 0);
				
				physicsFixture.setFilterData(f);
			    sensorFixture.setFilterData(f);
	}
	 public void deleteBot() {
			entitieBox.destroyFixture(physicsFixture);
			entitieBox.destroyFixture(sensorFixture);
			dispose();
			
		}
		
		// Shoot to hero
		public void shoot() {
			Bullet bullet = new Bullet(this.coordX, this.coordY+(this.getSizeY()/2),this.sideView);
			bullet.setBody(rpgWorld);
			bullets.add(bullet);
			
		}
	
	
}
