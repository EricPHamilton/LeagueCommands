import java.awt.Desktop;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Summoner {
	String name; 
	int id;
	Region reg;
	
	public Summoner (String name, Region reg) {
		this.name = name;
		this.reg = reg;
		this.id = this.getSummonerID();
	}
	
	public Summoner (String name) {
		this.name = name;
		this.reg = Region.getDefaultRegion();
		this.id = this.getSummonerID();
	}
	
	public int getSummonerID() {
		String summName = name;
		String urlRegion = reg.toString().toLowerCase();
		
		int id = -1;
		
		String url = "https://" + urlRegion + ".api.pvp.net/api/lol/" + urlRegion + "/v1.4/summoner/by-name/" + summName + "?api_key=" + APIKey.getKey();
		
		try {
			JSONObject summID = JSONUtils.getJSON(url);
			
			JSONObject summIDChildObject = (JSONObject)summID.get(summName);
			String idString = summIDChildObject.getString("id");
			id = Integer.parseInt(idString);
		} catch (IOException e) {
			Log.write("Summ name/region combo not found for ID.");
			e.printStackTrace();
		} catch (JSONException e) {
			Log.write("JSONException. Report on thread please with command that causes this to occur.");
			e.printStackTrace();
		}
		
		return id;
	}
	
	public String getRank() {
		String urlReg = reg.toString().toLowerCase();
		String tier = ""; 
		String div = "";
		
		if (this.id != -1) { //If there was an error finding the summonerID
			try {
				JSONObject leagueCall = JSONUtils.getJSON("https://" + urlReg + ".api.pvp.net/api/lol/" + urlReg + "/v2.5/league/by-summoner/" + this.id + "/entry?api_key=" + APIKey.getKey());
				JSONArray dataArr = leagueCall.getJSONArray("" + getSummonerID());
				
				JSONObject firstObj = dataArr.getJSONObject(0);
				tier = firstObj.getString("tier");
				
				JSONArray entr = firstObj.getJSONArray("entries");
				div = entr.getJSONObject(0).getString("division");
				
				return (name + " is " + tier + " " + div);
			} catch (IOException e) {
				Log.write("Summ name/region combo not found for rank.");
				e.printStackTrace();
			} catch (JSONException e) {
				Log.write("JSONException. Report on thread please with command that causes this to occur.");
				e.printStackTrace();
			}
		} else {
			Log.write("There was an error finding summID. Can't look for rank until ID is found.");
		}
			
		return "";
		
	}
	
	public void openLolking() {
		if (Desktop.isDesktopSupported()) {
			Desktop d = Desktop.getDesktop();
			try {
				d.browse(new URI("http://www.lolking.net/summoner/" + reg.toURLString() + "/" + id));
			} catch (IOException | URISyntaxException e) {
				Log.write("IOException or URLSyntaxException. Check summoner name + region.");
				e.printStackTrace();
			}
		} else {
			Log.write("This command is not supported by your OS.");
		}
	}
	
	public void openOPGG() {
		if (Desktop.isDesktopSupported()) {
			Desktop d = Desktop.getDesktop();
			try {
				d.browse(new URI(reg.toURLString() + ".op.gg/summoner/userName=" + name));
			} catch (URISyntaxException | IOException e) {
				Log.write("IOException or URLSyntaxException. Check summoner name + region.");
				e.printStackTrace();
			}
		} else {
			Log.write("This command is not supported by your OS.");
		}
	}
}
