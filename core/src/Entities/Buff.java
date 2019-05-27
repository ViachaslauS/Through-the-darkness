package Entities;

public class Buff {

	public final  boolean isCycle;
	private int clockTimes = 1;
	
	public enum BuffType {
		HITPOINTS, MANA, DAMAGE, ARMOR, 
		POWER, INTELLIGENCY, AGILITY, REGEN_FREQUENCY
	}
	public BuffType type;
	public float value;
	
	public float durating;
	
	/**
	 * @param type
	 * @param value
	 * @param durating
	 * @param cicle
	 */
	public Buff(BuffType type,float value, float durating, boolean cicle) {
		this.type = type;
		this.value = value;
		this.durating = durating;
		isCycle = cicle;
	}
	/**
	 * @return 0 if buf has ended, or time left
	 */
	public float update(float delta) {
		durating -= delta;
		if(durating <= 0)
			return 0;
		else {
			if(isCycle)
				clockTimes++;
			return durating;
		}
	}
	public int getClocks() {
		return clockTimes;
	}
	public float getFinalValue() {
		return value*clockTimes;
	}
}
