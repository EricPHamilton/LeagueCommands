import java.awt.Color;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;



public class Champion {
	
	String defString;
	String name;
	int id;
	
	public Champion (String name) {
		this.name = name;
		this.id = getID();
		if (id == -1) {
			Log.write("Unable to find champion.");
		}
		this.defString = getDefString();
	}
	
	public String toString() {
		return defString;
	}
	
	public int getID() {
		String name = this.name.toLowerCase();
		String line = "";
		for (String s : ChampionList.championList) {
			s = s.toLowerCase();
			if (s.contains(name)) {
				line = s;
			}
		}
		if(line != "") {
			String[] partsOfLine = line.split(" ");
			int id = Integer.parseInt(partsOfLine[0]);
			return id;
		} else {
			return -1;
		}
	}
	
	public String getDefString() {
		String name = this.name;
		for (String s : ChampionList.championList) {
			if (s.contains(name)) {
				return s;
			}
		}
		return null;
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
	
	//Used for making calls to the DDragon API.
	public String getAPIChampName() throws JSONException, IOException {
		int idOfChamp = this.id;
		
		JSONObject allData = JSONUtils.getJSON("http://ddragon.leagueoflegends.com/cdn/4.20.1/data/en_US/champion.json");
		
		JSONObject jsonChildObject = (JSONObject)allData.get("data");
		Iterator it = jsonChildObject.keys();
		String key = null;
		
		String nameOfChampWithSameID = null;
		while (it.hasNext()) {
			key = (String)it.next();
			int id = Integer.parseInt(((JSONObject)jsonChildObject.get(key)).get("key").toString());
			
			if (id == idOfChamp) {
				nameOfChampWithSameID = (((JSONObject)jsonChildObject.get(key)).get("id").toString());
			}
		}
		
		return nameOfChampWithSameID;
	}
}