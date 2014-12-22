import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class JSONUtils {
	
	public static JSONObject getJSON(String call) throws IOException, JSONException {
		InputStream is = new URL(call).openStream();
		try {
			BufferedReader read = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = readAll(read);
			JSONObject json = new JSONObject(jsonText);
			return json;
		} finally {
			is.close();
		}
	}
	
	public static JSONArray getJSONArray(String url) {
		return null;
	}
	
	public static String getData(JSONObject json, String[] dtataNames) {
		return null;
	}
	
	public static String getData(JSONArray json, String[] dataNames) {
		return null;
	}
	
	public static JSONObject getChampJSON(Champion champ) throws JSONException, IOException {
		String apiName = champ.getAPIChampName();
		return JSONUtils.getJSON("http://ddragon.leagueoflegends.com/cdn/4.20.1/data/en_US/champion/" + apiName + ".json");
	}
	
	private static String readAll(Reader read) throws IOException {
		StringBuilder sb = new StringBuilder();
		int i;
		while ((i = read.read()) != -1) {
			sb.append((char) i);
		}
		return sb.toString();
	}
	
	public static String getChampData(JSONObject champJSON, Champion champ) {
		JSONObject data;
		try {
			data = champJSON.getJSONObject("data");
			JSONObject name = data.getJSONObject(champ.getAPIChampName());
			
			String stats = name.getString("stats");
			stats = stats.replace("{", "");
			stats = stats.replace("}", "");
			stats = stats.replaceAll(",", "\n");
			stats = stats.replaceAll("\"", "");
			stats = stats.replaceAll(":", " - "); //Improves readability.
			return stats;
		} catch (JSONException | IOException e) {
			// TODO Auto-generated catch block
			Log.write("JSON/IO exception. Please submit conditions on how you're getting this error if your champ name is correct.");
			e.printStackTrace();
		}
		return null;

	}
}
