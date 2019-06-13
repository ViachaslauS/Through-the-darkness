package Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class PlayerStats {
	
	private float experience;
	private int level;
	
	private float power;
	private float agility;
	private float intelligency;
	
	private int statsPoints;
	private int skillPoints;
	
	Preferences pref;
	public PlayerStats(String object) {
		pref = Gdx.app.getPreferences(object);
		if(object.equals("aistats3_stat")) {
			startStats(1);
		}
		int isBegin = pref.getInteger("FirstLaunch", 0);
		if(isBegin == 0) {
			startStats(1);
		}
		//pref.clear(); // REMOVE!!!
		experience = pref.getFloat("Exp",0.0f);
		level = pref.getInteger("Level", 1);
		power = pref.getFloat("Power", 1.0f);
		agility = pref.getFloat("Agility", 1.0f);
		intelligency = pref.getFloat("Intel",1.0f);
		statsPoints = pref.getInteger("StatsPoints", 0);
		skillPoints = pref.getInteger("SkillPoints",12);
	}
	void startStats(int level) {
		pref.clear();
		pref.putInteger("FirstLaunch", 1);
		pref.putFloat("Exp", 0.0f);
		pref.putInteger("Level", 1);
		pref.putFloat("Power", 1.0f*level);
		pref.putFloat("Agility", 1.0f*level);
		pref.putFloat("Intel", 1.0f*level);
		pref.putInteger("StatsPoints", 0);
		pref.putInteger("SkillPoints",0);
		pref.flush();
	}
	public void ADDEXP(float exp) {
		experience+=exp;
		checkLevel();
	}
	private void checkLevel() {
		if(experience >= 10) {
			experience = 0.0f;
			level++;
			statsPoints+=3;
			skillPoints+=1;
		}
	}
	public float MAXHP() {
		return (float) (power*1.2);
	}
	public float DAMAGE() {
		return (float) (power*0.5);
	}
	public float SPEED() {
		return (float) (agility*1.01);
	}
	public float ATKSPEED() {		// Don't touch!
		return (float) (agility*0.007<0.75 ? agility*0.007 : 0.75);
	}
	
	public float MAXMANA() {
		return (float)(intelligency*1.0);
	}
	public float MANAREG() {
		return (float)(intelligency*0.2f);
	}
	public float HPREG() {
		return (float)(power*0.2f);
	}
	
	public void setPower(float power) {
		this.power = power;
	}
	public float getPower() {
		return power;
	}
	
	public void setIntel(float intel) {
		this.intelligency = intel;
	}
	public float getIntel() {
		return intelligency;
	}
	
	public void setAgility(float agility) {
		this.agility = agility;
	}
	public float getAgility() {
		return agility;
	}
	public float getExp() {
		return experience;
	}
	public int getLevel() {
		return level;
	}
	public float getMaxExp() {
		return 10f;
	}
	public int getStatsPoints() {
		return statsPoints;
	}
	public int getSkillPoints() {
		return skillPoints;
	}
	public void setSkillPoints(int points) {
		skillPoints = points;
	}
	public void setStatsPoints(int points) {
		statsPoints = points;
	}
	public void addPower(float pow) {
		power+=pow;
	}
	public void addAgility(float ag) {
		agility+=ag;
	}
	public void addIntel(float intel) {
		intelligency+=intel;
	}
}
