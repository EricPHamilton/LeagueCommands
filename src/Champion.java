import java.awt.Color;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;



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
	
	public void openWikiPage() {
		if (Desktop.isDesktopSupported()) {
			Desktop d = Desktop.getDesktop();
			try {
				d.browse(new URI("http://leagueoflegends.wikia.com/wiki/" + name));
			} catch (IOException | URISyntaxException e) {
				Log.write("IOException or URLSyntaxException. Check champion name.");
				e.printStackTrace();
			}
		} else {
			Log.write("This command is not supported by your OS.");
		}
	}
}