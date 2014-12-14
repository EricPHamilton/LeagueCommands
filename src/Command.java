
public class Command {
	//This will expand as more commands get implemented
	public static String[] commandList = {"clear"};
	public String[] cmd;
	
	public Command(String cmd) {
		this.cmd = cmd.split(" ");
	}
	
	public void execute() {
		if (isValidCommand()) {
			if (cmd[0].equals("clear")) {
				CommandWindow.clearTextPane();
			}
		} else {
			CommandWindow.addToTextPane("Not a valid command.");
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
