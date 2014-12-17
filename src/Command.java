import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;


public class Command {
	//This will expand as more commands get implemented
	//Add more commands IN ALPHABETICAL ORDER.
	public static String[] commandList = {"clear", "getchampid", "getdefaultregion", "getrank", "lolking", "opgg", "setdefaultregion", "summid"};
	
	public static String[] commandHelp = {"Clears console and erases previous commands.", //Clear
										"Grabs the ID provided a champion name.", //getchampid
										"Gets the default region specified in the filesystem.",//getdefaultregion
										"Gets the rank of the given summoner.", //getrank
										"Displays the lolking page of the given summoner.",//lolking
										"Displays the op.gg page of the given summoner.", //opgg
										"Sets a default region in the filesystem.",//setdefaultregion
										"Displays a summoner's ID number."//summid
										};
	public static String[] commandUsage = {"clear", 
										"getchampid <championName>",
										"getdefaultregion",
										"getrank <summonerName> <region>. If region defined by setdefault reigion, leave blank.",
										"lolking <summonerName> <region>. If region defined by setdefault reigion, leave blank.",
										"opgg <summonerName> <region>. If region defined by setdefault reigion, leave blank.",
										"setdefaultregion <region>. Region can be: BR, EUNE, EUW, KR, LAN, LAS, NA, OCE, RU, TR",
										"summid <summonerName> <region>. If region defined by setdefault reigion, leave blank.",
	};
	
	public String[] cmd;
	
	//previousCommands.get(0) will return first command...
	//previousCommands.get(previousCommands.size()-1) will return previous
	public static ArrayList<Command> previousCommands = new ArrayList<Command>();
	
	public Command(String cmd) {
		this.cmd = cmd.split(" ");
	}
	
	public void execute() {
		if (isValidCommand()) {
			if (cmd[0].equals("clear")) {
				clearPreviousCommands();
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
			} else if (cmd[0].equals("opgg") || cmd[0].equals("lolking")) {
				Summoner summ;
				if (cmd.length > 2) { //If region is provided...
					summ = new Summoner(cmd[1].toLowerCase(), new Region(cmd[2]));
				} else {
					summ = new Summoner(cmd[1].toLowerCase());
				}
				if (cmd[0].equals("opgg")) {
					summ.openOPGG();
				} else {
					summ.openLolking();
				}
			}
		} else {
			Log.write("Not a valid command.");
		}
	}
	
	public String toString() {
		String s = "";
		for (int i = 0 ; i < cmd.length ; i++) {
			s += cmd[i];
			if (i != cmd.length-1) {
				s += " ";
			}
		}
		return s;
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
	
	public static Command getCommand(int i) {
		return previousCommands.get(i);
	}
	
	public static void addPreviousCommand(Command c) {
		previousCommands.add(c);
	}
	
	public static int getIndexOfOldCommand(String s) {
		return previousCommands.indexOf(s);
	}
	
	public static void clearPreviousCommands() {
		previousCommands.clear();
	}
}
