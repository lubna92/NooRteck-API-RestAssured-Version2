package com.noorteck.qa.utils;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;

public class JsonFileUtils {

	/**
	 * This method converts JSON file to String
	 * 
	 * @param fileName
	 * @return
	 */
	public static String jsonToStrConvertion(String fileName) {
		String str = null;

		try {
			byte[] encoded = Files.readAllBytes(Paths.get("src/test/resources/" + fileName + ".json"));
			str = new String(encoded, Charset.defaultCharset());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return str;
	}

	/**
	 * TODO This method retrieves all mapped configuration data from JSON file and
	 * filters by retrieving only specified field object data
	 * 
	 * @param filterByObjName
	 * @param fileName
	 * @param jsonArrayIndex
	 * @return
	 */
	public static Map<String, Object> filterJson(String filterByObjName, String fileName, Integer jsonArrayIndex) {

		Map<String, Object> regionDataMap = new LinkedHashMap<String, Object>();

		// Path the file name to convert json to string and assign to str obj
		String jsonToString = jsonToStrConvertion(fileName);

		try {
			// Convert to JSON object
			JSONObject obj = new JSONObject(jsonToString);

			// check if jsonobject is not null and contains the specified filterByObjName
			if (obj != null && obj.has(filterByObjName)) {

				// convert to jSON object to object
				Object test = obj.get(filterByObjName);
				JSONObject filteredJsonObj = null;

				// check if it is JSONObject or JSONArray
				if (test instanceof JSONArray) {
					// if its JSONArray then create JonsArray object
					JSONArray filterJsonArr = obj.getJSONArray(filterByObjName);

					// now filter json Array based on index and store inside jsonObject
					filteredJsonObj = filterJsonArr.getJSONObject(jsonArrayIndex);

				} else {
					// if it is JSonObject then store the values to filteredJsonObject
					filteredJsonObj = obj.getJSONObject(filterByObjName);
				}

				Iterator<String> keys = filteredJsonObj.keys();
				// loop through json object and store in map
				while (keys.hasNext()) {
					String key = keys.next();

					// if key is null or empty then dont add value to map
					if (filteredJsonObj.optString(key) != null && !filteredJsonObj.optString(key).equals("")) {
						regionDataMap.put(key, filteredJsonObj.optString(key));
					}
				}

			} else {
				// Raise an error if the specified object was not mapped in json file
				Assert.fail("Unable to Locate: " + filterByObjName + " in " + fileName + ".json file");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return regionDataMap;
	}

}
