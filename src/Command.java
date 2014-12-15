import java.awt.Color;
import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;


public class Command {
	//This will expand as more commands get implemented
	public static String[] commandList = {"clear", "getchampid", "setdefaultregion", "summid"};
	public String[] cmd;
	
	public Command(String cmd) {
		this.cmd = cmd.split(" ");
	}
	
	public void execute() {
		if (isValidCommand()) {
			if (cmd[0].equals("clear")) {
				CommandWindow.clearTextPane();
			} else if (cmd[0].equals("getchampid")) {
				int id = ChampionList.getID(new Champion(cmd[1]));
				if (id != -1) {
					CommandWindow.addToTextPane("" + id);
				}
			} else if (cmd[0].equals("setdefaultregion")) {
				String reg = cmd[1];
				Region.setDefaultRegion(new Region(reg));
			} else if (cmd[0].equals("summid")) {
				String summName = cmd[1];
				
				//if a region is provided...
				String reg;
				if (cmd.length > 2) { 
					reg = cmd[2].toLowerCase();
				} else {
					reg = Region.getDefaultRegion().toString().toLowerCase();
				}
				
				String url = "https://" + reg + ".api.pvp.net/api/lol/" + reg + "/v1.4/summoner/by-name/" + summName + "?api_key=" + APIKey.getKey();
				
				try {
					JSONObject summID = JSONUtils.getJSON(url);
					
					JSONObject summIDChildObject = (JSONObject)summID.get(summName);
					String id = summIDChildObject.getString("id");
					Log.write("Summoner ID: " + id);
				} catch (IOException | JSONException e) {
					Log.write("Summoner Name not found. Check region and summName.");
					e.printStackTrace();
				}
				
			}
		} else {
			Log.write("Not a valid command.");
		}
	}
	
	public boolean isValidCommand() {
		String cmdName = cmd[0];
		for (String s : commandList) {
			if (cmdName.equals(s)) {
				return true;
			}
		}
		return false;
	}
}
