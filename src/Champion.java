import java.util.Comparator;

public class Champion implements Comparable{
	
	String defString;
	String name;
	int id;
	
	public Champion (String name) {
		this.name = name;
		this.id = ChampionList.getID(this);
		if (id == -1) {
			CommandWindow.addToTextPane("Unable to find champion.");
		}
		this.defString = ChampionList.getDefString(this);
	}

	@Override
	public int compareTo(Object o) {
		Champion c = (Champion) o;
		if (c instanceof Champion) {
			if (c.id > this.id) {
				return -1;
			} else if (c.id < this.id) {
				return 1;
			}
			return 0;
		}
		return 0;
	}
	
	public String toString() {
		return defString;
	}
}