package at.partyspot.db.access;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import at.partyspot.db.model.Party;


public class PartyService {

	public List<Party> getAll() throws Exception {
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

	public Party getParty(String name) throws Exception {
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

	public Party getParty(UUID id) throws Exception {
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

}
