

public class Champion {
	
	String defString;
	String name;
	int id;
	
	public Champion (String name) {
		this.name = name;
		this.id = ChampionList.getID(this);
		if (id == -1) {
			Log.write("Unable to find champion.");
		}
		this.defString = ChampionList.getDefString(this);
	}
	
	public String toString() {
		return defString;
	}
}