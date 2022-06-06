package at.partyspot.db.access;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import at.partyspot.db.model.Party;
import at.partyspot.rest.resources.Utilities;

public class PartyService {

	public static List<Party> getAll() throws Exception {
		List<Party> parties = new ArrayList<Party>();
		DatabaseService databaseService = new DatabaseService();
		ResultSet resultSet = databaseService.getAll("party");
		while (resultSet.next()) {
			Party party = new Party();
			UUID uuid = UUID.fromString(resultSet.getString("id"));
			party.setId(uuid);
			party.setName(resultSet.getString("name"));
			party.setCode(resultSet.getString("code"));
			party.setToken(resultSet.getString("token"));
			parties.add(party);
		}
		return parties;
	}

	public static Party getParty(String name) throws Exception {
		Party party = new Party();
		DatabaseService databaseService = new DatabaseService();
		ResultSet resultSet = databaseService.getByName("party", name);
		if (resultSet.next()) {
			UUID uuid = UUID.fromString(resultSet.getString("id"));
			party.setId(uuid);
			party.setName(resultSet.getString("name"));
			party.setCode(resultSet.getString("code"));
			party.setToken(resultSet.getString("token"));
		}
		return party;
	}

	public static Party getParty(UUID id) throws Exception {
		Party party = new Party();
		DatabaseService databaseService = new DatabaseService();
		ResultSet resultSet = databaseService.getById("party", id);
		if (resultSet.next()) {
			UUID uuid = UUID.fromString(resultSet.getString("id"));
			party.setId(uuid);
			party.setName(resultSet.getString("name"));
			party.setCode(resultSet.getString("code"));
			party.setToken(resultSet.getString("token"));
		}
		return party;
	}

	public static Party createParty(String accessToken, String partyName) throws Exception {
		Party party = new Party();
		UUID uuid = UUID.randomUUID();
		party.setId(uuid);
		party.setName(partyName);
		party.setToken(accessToken);
		DatabaseService databaseService = new DatabaseService();
		Connection connection = databaseService.createConnection();
		Statement statement = connection.createStatement();
		String sql = "insert into party (id, name, token) VALUES ("+Utilities.uuidToBin(uuid)+", "+partyName+", "+accessToken+")";
		ResultSet resultSet = statement.executeQuery(sql);
		
		
		return party;
	}

}
