package aiall;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.steer.behaviors.FollowPath;
import com.badlogic.gdx.ai.steer.utils.Path;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import Entities.Entities;

public class AiCustom extends Entities {
	public float _orientation;
	Vector3 all;
	Vector2 _coord;
	SteeringAgent steeringAgent;
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
		preferences = Gdx.app.getPreferences("aistats"+ id);
		setSize(size);
		setCoord(coord);
		_coord = coord;
		coordX = coord.x;
		coordY = coord.y;
		sideView = 1;
		_orientation = (float) sideView;
		setisAttacking(false);
		allSheets = new Texture(Gdx.files.internal("Hero.png"));
		steeringAgent = new SteeringAgent(coord, sideView, getisAttacking());
		//followPath = new FollowPath<Vector2, Pathparametrs>(steeringAgent, path);
		
		
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
		 if(getisAttacking()) {
		 all = steeringAgent.update(this._orientation);
		 coordX = all.y;
		 coordY = all.z;
		 _orientation = all.x;
		 
		 }
	  }
		
	
	
	
}
