import java.awt.Color;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class Champion {
	
	String defString;
	String name;
	int id;
	
	/**
	 * Creates a Champion object with given name 'name'.
	 */
	public Champion (String name) {
		this.name = name;
		this.id = getID();
		if (id == -1) {
			Log.write("Unable to find champion.");
		}
		this.defString = getDefString();
	}
	
	/**
	 * returns a String representation of Champion object.
	 */
	public String toString() {
		return defString;
	}
	
	/**
	 * Returns the ID number of the champion object. 
	 * 
	 * Called by constructor to give variable id meaningful value.
	 */
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
	
	/**
	 * Returns the definition String of the given champion.
	 * ie. "the Dark Child" if given "Annie"
	 */
	public String getDefString() {
		String name = this.name;
		for (String s : ChampionList.championList) {
			if (s.contains(name)) {
				return s;
			}
		}
		return null;
	}
	
	/**
	 * Opens the lolWiki page associated with the champion.
	 */
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

	/**
	 * Gets the ability of the champion binded to the provided key.
	 * 
	 * @param key must either be q, w, e, or r.
	 */
	public String getAbility(String key) {
		String abilityString = "";
		if (key.equalsIgnoreCase("q") || key.equalsIgnoreCase("w") || key.equalsIgnoreCase("e") || key.equalsIgnoreCase("r")) {
			int keyNum = -1;
			
			if (key.equalsIgnoreCase("q")) keyNum = 0;
			else if (key.equalsIgnoreCase("w")) keyNum = 1;
			else if (key.equalsIgnoreCase("e")) keyNum = 2;
			else keyNum = 3;
			
			try {
				String apiName = this.getAPIChampName();
				JSONObject champJSON = JSONUtils.getJSON("http://ddragon.leagueoflegends.com/cdn/4.20.1/data/en_US/champion/" + apiName + ".json");
				
				JSONObject data = champJSON.getJSONObject("data");
				JSONObject name = data.getJSONObject(apiName);
				
				JSONArray allSpells = name.getJSONArray("spells");
				JSONObject spell = (JSONObject) allSpells.get(keyNum);
				
				abilityString += spell.getString("id");
				abilityString += "\n" + "CD: " + spell.getString("cooldownBurn");
				abilityString += "\n" + "Range: " + spell.getString("rangeBurn");
				abilityString += "\n" + "Cost: " + spell.getString("costBurn");
			} catch (IOException | JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			abilityString += "Invalid button. Try q/w/e/r.";
		}
		return abilityString;
	}
}