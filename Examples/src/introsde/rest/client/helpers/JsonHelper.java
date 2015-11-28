package introsde.rest.client.helpers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonHelper {

	JSONObject jsonObj = null;
	JSONArray jsonArr = null;


	public void loadJson(String json) {

		// Load JSON object
		if (json.startsWith("{")) {
			jsonObj = new JSONObject(json);
		}

		// Load JSON array
		if (json.startsWith("[")) {
			jsonArr = new JSONArray(json);
		}
	}


	public String getElement(String expr) throws Exception {

		if (jsonObj == null)
			throw new Exception("Not a JSON object");

		try {
			return jsonObj.getString(expr);
		} catch (JSONException e) {}

		try {
			return Integer.toString(jsonObj.getInt(expr));
		} catch (JSONException e) {}

		try {
			return Double.toString(jsonObj.getDouble(expr));
		} catch (JSONException e) {}

		return "";
	}


	public int countList() throws Exception {
		if (jsonArr == null)
			throw new Exception("Not a JSON object");

		return jsonArr.length();
	}

	public static String prettyJSON(String jsonString, int indent){
	String res;

	if(jsonString.length()==0)
		return "";

	char c = jsonString.trim().charAt(0);
	if(c == '['){
		JSONArray json = new JSONArray(jsonString);
		res = json.toString(indent);
	} else {
		JSONObject json = new JSONObject(jsonString);
		res = json.toString(indent);
	}
	return res;
}
}
