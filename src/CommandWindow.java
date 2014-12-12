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


public class CommandWindow {

	private JFrame frame;
	private JTextField txtTestingText;

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
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 508, 339);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		txtTestingText = new JTextField();
		txtTestingText.setFont(new Font("Consolas", Font.PLAIN, 12));
		txtTestingText.setToolTipText("Enter commands here");
		txtTestingText.setBackground(new Color(255, 255, 255));
		txtTestingText.setForeground(new Color(0, 0, 0));
		frame.getContentPane().add(txtTestingText, BorderLayout.SOUTH);
		txtTestingText.setColumns(10);
		
		JTextPane textPane = new JTextPane();
		textPane.setFont(new Font("Consolas", Font.PLAIN, 12));
		textPane.setEditable(false);
		textPane.setToolTipText("Command output");
		textPane.setBackground(new Color(204, 204, 204));
		frame.getContentPane().add(textPane, BorderLayout.CENTER);
	}

}
