import java.awt.Color;
import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;


public class Command {
	//This will expand as more commands get implemented
	public static String[] commandList = {"clear", "getchampid", "setdefaultregion", "summid", "getdefaultregion", "getrank"};
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
				Summoner summ = new Summoner(cmd[1].toLowerCase());
				int id = summ.id;
				if (id != -1) {
					Log.write("Summoner ID: " + id);
				}
			} else if (cmd[0].equals("getdefaultregion")) {
				Log.write("Default Region: " + Region.getDefaultRegion().toString());
			} else if (cmd[0].equals("getrank")) {
				Summoner summ = new Summoner(cmd[1].toLowerCase());
				String rank = summ.getRank();
				if (!rank.equals("")) {
					Log.write(rank);
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
