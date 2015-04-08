import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;


public class Command {
	//This will expand as more commands get implemented
	//Add more commands IN ALPHABETICAL ORDER.
	public static String[] commandList = {"champwiki", "clear", "clearnotes", "getchampid", "getdefaultregion", "getlevel", "getrank", "getability", "getsummid", "help", "lolking", "note", "opgg", "setdefaultregion"};
	
	public static String[] commandHelp = {"Displays the lolwiki page of the given champion", //champwiki
										"Clears console and erases previous commands.", //Clear
										"Clears all notes.", //clearnotes
										"Gets the stats of a champion's ability.", //getability
										"Grabs the ID provided a champion name.", //getchampid
										"Gets the default region specified in the filesystem.",//getdefaultregion
										"Gets the level of the summoner.", //getlevel
										"Gets the rank of the given summoner.", //getrank
										"Displays a summoner's ID number.",//getsummid
										"Lists what each command does.", //help
										"Displays the lolking page of the given summoner.",//lolking
										"Adds note to a note file", //note
										"Displays the op.gg page of the given summoner.", //opgg
										"Sets a default region in the filesystem.",//setdefaultregion
										};
	public static String[] commandUsage = {"champwiki <championName>",
										"clear", 
										"clearnotes",
										"getability <champion> <button (q/w/e/r)> <CDR (0 - 40)>",
										"getchampid <championName>",
										"getdefaultregion",
										"getlevel <summonerName> <region>",
										"getrank <summonerName> <region>. If region defined by setdefault reigion, leave blank.",
										"getsummid <summonerName> <region>. If region defined by setdefault reigion, leave blank.",
										"help <command>.",
										"lolking <summonerName> <region>. If region defined by setdefault reigion, leave blank.",
										"note <message>",
										"opgg <summonerName> <region>. If region defined by setdefault reigion, leave blank.",
										"setdefaultregion <region>. Region can be: BR, EUNE, EUW, KR, LAN, LAS, NA, OCE, RU, TR"
										
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
			if (cmd[0].equals("champwiki")) {
				Champion champ = new Champion(cmd[1]);
				champ.openWikiPage();
				
			} else if (cmd[0].equals("clear")) {
				clearPreviousCommands();
				CommandWindow.clearTextPane();
				
			} else if (cmd[0].equals("clearnotes")) {
				Log.clearFile(new File("notes.txt"));
				Log.write("Notes cleared.");
				
			} else if (cmd[0].equals("getability")) {
				//Gets the JSONObject of given champion...
				Champion champ = new Champion(cmd[1]);
				String[] parts;
				String str = "";
				if (cmd.length > 2) { //If a button was provided... (If ability is requested)
					str = champ.getAbility(cmd[2]);
					parts = str.split("\n");
					if (cmd.length > 3) { //If a CDR Request was provided...
						//Line before manipulation: "CD: 4" or "CD: 120/100/80"
						int cdr = Integer.parseInt(cmd[3]);
						if (cdr >= 0 && cdr <= 40) {
							String cdParts[] = parts[1].split("/");
							cdParts[0] = cdParts[0].replace("CD: ", "");
							
							int[] cdInts = new int[cdParts.length];
							for (int i = 0 ; i < cdParts.length ; i++) {
								int newTime = Integer.parseInt(cdParts[i]);
								newTime = (int) (newTime * (1 - (double)cdr / 100));
								cdInts[i] = newTime;
							}
							
							String newCoolDowns = "CD: ";
							for (int i = 0 ; i < cdInts.length ; i++) {
								newCoolDowns += cdInts[i];
								if (i < (cdInts.length - 1)) {
									newCoolDowns += "/";
								}
							}
							parts[1] = newCoolDowns;
						} else {
							Log.write("Invalid CDR Number. Must be between 0% and 40%.");
							return;
						}
					}
				} else { //Button not provided, get generic champ stats.
					str = JSONUtils.getChampData(champ);
					parts = str.split("\n");
				}
				Log.write("-" + this.toString());
				for (String s : parts) {
					Log.write("  " + s);
				}
				
			} else if (cmd[0].equals("getchampid")) {
				Champion champ = new Champion(cmd[1]);
				int id = champ.id;
				if (id != -1) {
					Log.write("" + id);
				}
				
			} else if (cmd[0].equals("getdefaultregion")) {
				Log.write("Default Region: " + Region.getDefaultRegion().toString());
				
			} else if (cmd[0].equals("getlevel")) {
				Summoner summ;
				if (cmd.length > 2) { //If region is provided...
					summ = new Summoner(cmd[1].toLowerCase(), new Region(cmd[2]));
				} else {
					summ = new Summoner(cmd[1].toLowerCase());
				}
				Log.write("Summoner level: " + summ.getLevel());
				
			} else if (cmd[0].equals("getrank")) {
				Summoner summ = new Summoner(cmd[1].toLowerCase());
				String rank = summ.getRank();
				if (!rank.equals("")) {
					Log.write(rank);
				}
			} else if (cmd[0].equals("getsummid")) {
				Summoner summ = new Summoner(cmd[1].toLowerCase());
				int id = summ.getSummonerID();
				if (id != -1) {
					Log.write("Summoner ID: " + id);
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
				
			} else if (cmd[0].equals("note")) {
				String note = "";
				for (int i = 1 ; i < cmd.length ; i++) {
					note += cmd[i] + " ";
				}
				File f = new File("notes.txt");
				Log.writeToFile(note, f);
				Log.write("Note written.");
				
			} else if (cmd[0].equals("setdefaultregion")) {
				String reg = cmd[1];
				Region.setDefaultRegion(new Region(reg));
			}
		} else {
			Log.write("Not a valid command.");
		}
	}
	
	/**
	 * Returns string representation of Command object.
	 */
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
	
	/**
	 * Returns true if cmd[0] is defined in the array "commandList"
	 */
	public boolean isValidCommand() {
		String cmdName = cmd[0];
		for (String s : commandList) {
			if (cmdName.equals(s)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * returns Command at index @param i in list "previousCommands".
	 */
	public static Command getCommand(int i) {
		return previousCommands.get(i);
	}
	
	/**
	 * Adds @param c to list "previousCommands".
	 */
	public static void addPreviousCommand(Command c) {
		previousCommands.add(c);
	}
	
	/**
	 * Gets the index of an old command. 
	 * 
	 * Used for arrow key up/down navigation of commands.
	 */
	public static int getIndexOfOldCommand(String s) {
		return previousCommands.indexOf(s);
	}
	
	/**
	 * Clears list previousCommands. Used if the command "clear" is written.
	 */
	public static void clearPreviousCommands() {
		previousCommands.clear();
	}
}
