package at.partyspot.rest.resources;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

import com.fasterxml.jackson.databind.ObjectMapper;

public class FileHandler {

	private static String readDBConfig() throws IOException {
		String path = System.getProperty("jboss.server.data.dir");
		String file = path + "\\config\\dbconfig.json";
        String json = new String(Files.readAllBytes(Paths.get(file)));
        return json;
	}
	
	public static HashMap<String, Object> getDBConfigData() throws IOException{
		String json = readDBConfig();
		@SuppressWarnings("unchecked")
		HashMap<String,Object> config =
		        new ObjectMapper().readValue(json, HashMap.class);
		return config;
	}
	
}
