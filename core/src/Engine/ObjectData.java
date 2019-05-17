package Engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import Entities.Buff;
import Entities.PlayerStats;

/**
 * @author Slava Stankevich
 *
 */
public class ObjectData {
	//_______________________________________________
	// Local parameters
	Preferences preferences;
	public PlayerStats stats;
	Array<Buff> buffs;
	
	protected float MAXHITPOINT;
	protected float MAXMANA;
	
	
	//_______________________________________________
	
	/**
	 * 	if this parametr is true - entitie attack and do damage
	 */
	public int isAttacking = 0;
	public boolean isAi = false;
	public float attackTime = 0;
	public boolean isInvisible = false;
	public boolean isBull = false;
	protected float HITPOINT;
	protected float MANA;
	protected float ARMOR;
	protected float DAMAGE;
	
	private float addIntel;
	private float addAgility;
	private float addPower;
	
	public ObjectData(String object) {
		
		preferences = Gdx.app.getPreferences(object);
		stats = new PlayerStats(object+"_stat");
		loadPref();
		buffs = new Array<Buff>();
	}
	
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
	
	/**
	 * Descrease hitpoints on damage parameter
	 * @param damage
	 */
	public void setHitpoint(float damage) {
		checkARMOR();
		HITPOINT -= damage/(ARMOR+1);
		
	}
	
	public void setMANA(float mana) {
		MANA -= mana;
	}
	public void setARMOR(float armor) {
		ARMOR = armor;
		checkARMOR();
	}
	
	public void setBuff(int what, float value, float durating) {
		buffs.add(new Buff(what, value, durating));
		addStats(buffs.get(buffs.size-1));
		
	}
	

	private void loadPref() {
		
		MAXHITPOINT = 100.0f * stats.HP();
		MAXMANA = 100.0f * stats.MANA();
		
		HITPOINT = preferences.getFloat("HITPOINT", 100.0f) * stats.HP();
		DAMAGE = preferences.getFloat("DAMAGE", 100.0f) * stats.DAMAGE();
		ARMOR = preferences.getFloat("ARMOR", 0.0f);
		checkARMOR();
		MANA = preferences.getFloat("MANA", 100.0f) * stats.MANA();
	
	}
	
	public void resetPref() {
		preferences.clear();
	}
	float regenClock = 0.0f;
	/**
	 * Method need for regen and check valid values
	 */
	public void updateData() {
		regenClock += Gdx.graphics.getDeltaTime();
		if(regenClock >= 3.0f) {
			regenClock = 0.0f;
			regenHP();
			regenMANA();
			updateBuffs(Gdx.graphics.getDeltaTime());
		}
		checkStats();
	}
	
	/**
	 *  check on max and min values
	 */
	private void checkStats() {
		if(HITPOINT > MAXHITPOINT)
			HITPOINT = MAXHITPOINT;
		if(MANA > MAXMANA)
			MANA = MAXMANA;
		if(MANA < 0)
			MANA = 0;
		checkARMOR();
	}
	
	/**
	 *  if entitie get buff, inscrease parameters
	 */
	private void addStats(Buff buff) {
			switch(buff.type) {
			case 1:; break;
			case 2:; break; 
			case 3:; break; 
			case 4: HITPOINT+=buff.value; break; 
			case 5: MANA+=buff.value ; break; 
			case 6: ARMOR+=buff.value ; break; 
			case 7: DAMAGE+=buff.value ; break;
			default: break;
			}
	}

	/**
	 * Update buffs, which has entitie
	 * @param delta time
	 */
	private void updateBuffs(float delta) {
		for(int i = 0; i < buffs.size; i++) {
			if(buffs.get(i).update(delta) == 0) {
				descrease(buffs.get(i));
				buffs.removeIndex(i);
			}
		
		}
	}

	private void descrease(Buff buff) {
		switch(buff.type) {
		case 1:; break;
		case 2:; break; 
		case 3:; break; 
		case 4: HITPOINT-=buff.value; break; 
		case 5: MANA-=buff.value; break; 
		case 6: ARMOR-=buff.value; break; 
		case 7: DAMAGE-=buff.value; break;
		default: break;
		}
	}

	private void regenMANA() {
		MANA += stats.MANAREG();
	}

	private void regenHP() {
		HITPOINT += stats.HPREG();
	}

	private void checkARMOR() {
		if(ARMOR > 1) {
			ARMOR = 1;
		}
		if(ARMOR < 0) {
			ARMOR = 0;
		}
	}
}
