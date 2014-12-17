import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;


public class Region {
	
	private final String name;
	private final String[] regions = {"BR", "EUNE", "EUW", "KR", "LAN", "LAS", "NA", "OCE", "RU", "TR"};
	
	public Region(String name) {
		this.name = name;
	}
	
	public boolean isValidRegion() {
		String s = this.name;
		for (String reg : regions) {
			if (s.equals(reg)) {
				return true;
			}
		}
		return false;
	}
	
	public String toString() {
		return name;
	}
	
	public String toURLString() {
		return name.toLowerCase();
	}
	
	@SuppressWarnings("resource")
	public static void setDefaultRegion(Region reg) {
		if(reg.isValidRegion()) {
			try {
				PrintWriter pw = new PrintWriter("region.txt", "UTF-8");
				pw.println(reg.toString());
				pw.flush();
				Log.write("Region set to " + reg.name);
			} catch (FileNotFoundException | UnsupportedEncodingException e) {
				Log.write("File not found. Create a file in directory 'data/region.txt'");
				e.printStackTrace();
			}
		} else {
			Log.write("Your region was not recognized. Try again with a valid region.");
		}
	}
	
	public static Region getDefaultRegion() {
		FileReader file;
		try {
			file = new FileReader("region.txt");
			BufferedReader read = new BufferedReader(file);
			
			return new Region(read.readLine());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;	
	}
}
