import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;


public class Command {
	//This will expand as more commands get implemented
	//Add more commands IN ALPHABETICAL ORDER.
	public static String[] commandList = {"champwiki", "clear", "clearnotes", "getchampid", "getdefaultregion", "getlevel", "getrank", "getstat", "help", "lolking", "note", "opgg", "setdefaultregion", "summid"};
	
	public static String[] commandHelp = {"Displays the lolwiki page of the given champion", //getchampwiki
										"Clears console and erases previous commands.", //Clear
										"Clears all notes.", //clearnotes
										"Grabs the ID provided a champion name.", //getchampid
										"Gets the default region specified in the filesystem.",//getdefaultregion
										"Gets the level of the summoner.", //getlevel
										"Gets the rank of the given summoner.", //getrank
										"Gets the stats of a champion's ability.", //getstat
										"Lists what each command does.", //help
										"Displays the lolking page of the given summoner.",//lolking
										"Adds note to a note file", //note
										"Displays the op.gg page of the given summoner.", //opgg
										"Sets a default region in the filesystem.",//setdefaultregion
										"Displays a summoner's ID number."//summid
										};
	public static String[] commandUsage = {"champwiki <championName>",
										"clear", 
										"clearnotes",
										"getchampid <championName>",
										"getdefaultregion",
										"getlevel <summonerName> <region>",
										"getrank <summonerName> <region>. If region defined by setdefault reigion, leave blank.",
										"getstat <champion> <button (q/w/e/r)>",
										"help <command>.",
										"lolking <summonerName> <region>. If region defined by setdefault reigion, leave blank.",
										"note <message>",
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
				Champion champ = new Champion(cmd[1]);
				int id = champ.id;
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
			} else if (cmd[0].equals("help")) {
				if (cmd.length > 1) { //If they provided a command to be helped with.
					Command needHelpWith = new Command(cmd[1]);
					if (needHelpWith.isValidCommand()) {
						for (int i = 0 ; i < commandList.length ; i++) {
							if (needHelpWith.cmd[0].equals(commandList[i])) {
								Log.write(commandList[i] + " - " + commandHelp[i]);
								Log.write("Usage: " + commandUsage[i]);
							}
						}
					} else {
						Log.write("You provided an invalid command as an argument.");
					}
				} else {
					for (int i = 0 ; i < commandList.length ; i++) {
						Log.write(commandList[i] + " - " + commandHelp[i]);
					}
				}
			} else if (cmd[0].equals("champwiki")) {
				Champion champ = new Champion(cmd[1]);
				champ.openWikiPage();
			} else if (cmd[0].equals("note")) {
				String note = "";
				for (int i = 1 ; i < cmd.length ; i++) {
					note += cmd[i] + " ";
				}
				File f = new File("notes.txt");
				Log.writeToFile(note, f);
				Log.write("Note written.");
			} else if (cmd[0].equals("clearnotes")) {
				Log.clearFile(new File("notes.txt"));
				Log.write("Notes cleared.");
			} else if (cmd[0].equals("getlevel")) {
				Summoner summ;
				if (cmd.length > 2) { //If region is provided...
					summ = new Summoner(cmd[1].toLowerCase(), new Region(cmd[2]));
				} else {
					summ = new Summoner(cmd[1].toLowerCase());
				}
				Log.write("Summoner level: " + summ.level);
			} else if (cmd[0].equals("getstat")) {
				//Gets the JSONObject of given champion...
				Champion champ = new Champion(cmd[1]);
				if (cmd.length > 2) { //If a button was provided... (If ability is requested)
					Log.write(champ.getAbility(cmd[2]));
				} else { //Button not provided, get generic champ stats.
					Log.write(JSONUtils.getChampData(champ));
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
