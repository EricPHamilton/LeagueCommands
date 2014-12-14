
public class Command {
	//This will expand as more commands get implemented
	public static String[] commandList = {"clear", "getchampid", "setdefaultregion"};
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
