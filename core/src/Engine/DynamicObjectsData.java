package Engine;

public class DynamicObjectsData {

	public int id;
	public boolean isNear;
	public boolean isTrigger = false;
	public boolean wasActivated = false;
	public DynamicObjectsData(int id) {
		this.id = id;
		isNear = false;
	}
}
