import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;

import java.awt.BorderLayout;

import javax.swing.JTextPane;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class CommandWindow {

	private JFrame frame;
	private static int cmdIndex = -1;
	private final static int pixelsHeightPerLine = 15;
	private static int linesInTextPane = 0;
	
	/**
	 * Launch the application.
	 */
	public static void runGUI() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CommandWindow window = new CommandWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public CommandWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	static JTextPane textPane;
	JTextField txtBox;
	private void initialize() {
		
		frame = new JFrame();
		textPane = new JTextPane();

		frame.setBounds(100, 100, 508, 531);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		txtBox = new JTextField();
		txtBox.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					String cmd = txtBox.getText();
					txtBox.setText("");

					Command newCmd = new Command(cmd);
					
					Command.addPreviousCommand(newCmd);
					cmdIndex = Command.previousCommands.size();
					
					newCmd.execute();

				} else if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {
					//goUp is true if user pressed "up" instead of "down"
					boolean goUp = e.getKeyCode() == KeyEvent.VK_UP; 
					
					if (goUp) {
						if (cmdIndex - 1 >= 0) {
							cmdIndex--;
							txtBox.setText(Command.getCommand(cmdIndex).toString());
						}
					} else {
						if (cmdIndex + 1 <= Command.previousCommands.size()-1) {
							cmdIndex++;
							txtBox.setText(Command.getCommand(cmdIndex).toString());
						}
					}
					
				}
			}
		});
		txtBox.setFont(new Font("Consolas", Font.PLAIN, 12));
		txtBox.setToolTipText("Enter commands here");
		txtBox.setBackground(new Color(255, 255, 255));
		txtBox.setForeground(new Color(0, 0, 0));
		frame.getContentPane().add(txtBox, BorderLayout.SOUTH);
		txtBox.setColumns(10);
	
		textPane.setFont(new Font("Consolas", Font.PLAIN, 12));
		textPane.setEditable(false);
		textPane.setToolTipText("Command output");
		textPane.setBackground(new Color(204, 204, 204));
		frame.getContentPane().add(textPane, BorderLayout.CENTER);
		
		frame.addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent e) {
				txtBox.requestFocus();
			}
		});
	}

	/**
	 * Adds the string s to the Text Pane and displays it to the user.
	 */
	public static void addToTextPane(String s) {
		String previousText = textPane.getText();
		String futureText = previousText;
		
		int windowHeight = textPane.getHeight();
		int maxLines = windowHeight / pixelsHeightPerLine - 1;
		if (linesInTextPane > maxLines) {
			for (int i = linesInTextPane  - maxLines ; i > 0 ; i--) { //Deletes the exact amnt of lines we need for next output to show up.
				futureText = futureText.substring(futureText.indexOf('\n') + 1);
				linesInTextPane--;
			}
		}
		
		if (!previousText.equals("")) {
			futureText += "\n";
		}

		if (s.contains("\n")) {
			String[] parts = s.split("\n");
			for (String part : parts) {
				addToTextPane(part);
			}
		} else {
			futureText += s;
			linesInTextPane++;
			textPane.setText(futureText);
		}
	}
	
	/**
	 * Returns the number of lines of text in the Text Pane.
	 */
	private static int getLinesInTextPane() {
		return linesInTextPane;
	}
	
	/**
	 * Clears all text in text pane.
	 */
	public static void clearTextPane() {
		textPane.setText("");
		linesInTextPane = 0;
	}
}
