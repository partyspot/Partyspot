package at.partyspot.rest.resources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Utilities {
	
	public static String addDelimited(String value, String toBeAdded, String delimiter) {
		if (delimiter == null)
			delimiter = "";
		if (value == null)
			value = "";
		if (toBeAdded == null)
			toBeAdded = "";

		if (value.isEmpty()) {
			if (toBeAdded.isEmpty()) {
				return "";
			} else {
				return toBeAdded;
			}
		} else {
			if (toBeAdded.isEmpty()) {
				return value;
			} else {
				return value.concat(delimiter).concat(toBeAdded);
			}
		}
	}
	
	public static String binToUuid(String columnname) {
		return "BIN_TO_UUID(" + columnname + ") as " + columnname;
	}
	
	public static String uuidToBin(UUID uuid) {
		return "UUID_TO_BIN('" + uuid + "')";
	}
	
	public static boolean isNetherNullNorEmpty(String toBeChecked) {
		if(toBeChecked == null || toBeChecked.isEmpty() || toBeChecked.trim().isEmpty()) {
			return false;
		}
		return true;
	}
	
	
	public static Map<String, Object> jsonToMap(Object json) throws JSONException {

        if(json instanceof JSONObject)
            return _jsonToMap_((JSONObject)json) ;

        else if (json instanceof String)
        {
            JSONObject jsonObject = new JSONObject((String)json) ;
            return _jsonToMap_(jsonObject) ;
        }
        return null ;
    }


   private static Map<String, Object> _jsonToMap_(JSONObject json) throws JSONException {
        Map<String, Object> retMap = new HashMap<String, Object>();

        if(json != JSONObject.NULL) {
            retMap = toMap(json);
        }
        return retMap;
    }


    private static Map<String, Object> toMap(JSONObject object) throws JSONException {
        Map<String, Object> map = new HashMap<String, Object>();

        Iterator<String> keysItr = object.keys();
        while(keysItr.hasNext()) {
            String key = keysItr.next();
            Object value = object.get(key);

            if(value instanceof JSONArray) {
                value = toList((JSONArray) value);
            }

            else if(value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            map.put(key, value);
        }
        return map;
    }


    public static List<Object> toList(JSONArray array) throws JSONException {
        List<Object> list = new ArrayList<Object>();
        for(int i = 0; i < array.length(); i++) {
            Object value = array.get(i);
            if(value instanceof JSONArray) {
                value = toList((JSONArray) value);
            }

            else if(value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            list.add(value);
        }
        return list;
    }

}
