import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;


public class Log {
	public static void write(String s) {
		CommandWindow.addToTextPane(s);
	}
	
	public static void writeToFile(String s, File f) {
		try {
			PrintWriter pw = new PrintWriter(new FileWriter(f, true));
			pw.println(s);
			pw.flush();
		} catch (IOException e) {
			Log.write("File not found. Create a file in directory 'notes.txt'");
			e.printStackTrace();
		}
	}
	
	public static void clearFile(File f) {
		try {
			PrintWriter pw = new PrintWriter(new FileWriter(f));
			pw.println(" ");
			pw.flush();
		} catch (IOException e) {
			Log.write("File not found. Create a file in directory 'notes.txt'");
			e.printStackTrace();
		}
	}
}
