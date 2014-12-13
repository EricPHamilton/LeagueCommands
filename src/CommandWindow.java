import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.FlowLayout;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class CommandWindow {

	private JFrame frame;

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

		frame.setBounds(100, 100, 508, 339);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		txtBox = new JTextField();
		txtBox.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					String cmd = txtBox.getText();
					txtBox.setText("");
					addToTextPane(cmd);
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
		
	}

	private void addToTextPane(String cmd) {
		String previousText = textPane.getText();
		String futureText = previousText;
		if (!previousText.equals("")) {
			futureText += "\n";
		}
		
		//TODO: Change to command output.
		futureText += cmd;
		textPane.setText(futureText);
	}
}
