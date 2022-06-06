package at.partyspot.rest.resources;

import java.util.UUID;

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

}
