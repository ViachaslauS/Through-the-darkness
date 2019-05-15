package Entities;

public class Buff {

	/**
	 * 1,2,3 - Power, Intell, Agility <br> 4,5,6,7 - HP, Mana, Armor, Damage
	 */
	public int type;
	public float value;
	
	public float durating;
	
	/**
	 * @param type
	 * <br> 1,2,3 - Power, Intell, Agility <br> 4,5,6,7 - HP, Mana, Armor, Damage
	 * @param value
	 * @param durating
	 */
	public Buff(int type,float value, float durating) {
		this.type = type;
		this.value = value;
		this.durating = durating;
	}
	/**
	 * @return 0 if buf has ended, or time left
	 */
	public float update(float delta) {
		durating -= delta;
		if(durating <= 0)
			return 0;
		else
			return durating;
	}
}
