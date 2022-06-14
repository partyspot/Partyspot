package at.partyspot.db.access;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import at.partyspot.db.model.Party;

@Stateless
public class PartyService {
	
	@EJB
	DatabaseService databaseService;

	public List<Party> getAll() throws Exception {
		List<Party> parties = new ArrayList<Party>();
		Connection conn = databaseService.createConnection();
		String query = "{CALL getAllParties()}";
		CallableStatement statement = conn.prepareCall(query);
		ResultSet resultSet = statement.executeQuery();
		while (resultSet.next()) {
			Party party = new Party();
			UUID uuid = UUID.fromString(resultSet.getString("id"));
			party.setId(uuid);
			party.setName(resultSet.getString("name"));
			party.setCode(resultSet.getString("code"));
			party.setToken(resultSet.getString("token"));
			parties.add(party);
		}
		conn.close();
		return parties;
	}

	public Party getParty(String name) throws Exception {
		Party party = new Party();
		Connection conn = databaseService.createConnection();
		String query = "{CALL getPartyByName(?)}";
		CallableStatement statement = conn.prepareCall(query);
		statement.setString(1, name);
		ResultSet resultSet = statement.executeQuery();
		if (resultSet.next()) {
			UUID uuid = UUID.fromString(resultSet.getString("id"));
			party.setId(uuid);
			party.setName(resultSet.getString("name"));
			party.setCode(resultSet.getString("code"));
			party.setToken(resultSet.getString("token"));
		}
		conn.close();
		return party;
	}

	public Party getParty(UUID id) throws Exception {
		Party party = new Party();
		Connection conn = databaseService.createConnection();
		String query = "{CALL getPartyById(?)}";
		CallableStatement statement = conn.prepareCall(query);
		statement.setString(1, id.toString());
		ResultSet resultSet = statement.executeQuery();
		if (resultSet.next()) {
			UUID uuid = UUID.fromString(resultSet.getString("id"));
			party.setId(uuid);
			party.setName(resultSet.getString("name"));
			party.setCode(resultSet.getString("code"));
			party.setToken(resultSet.getString("token"));
		}
		conn.close();
		return party;
	}

	public Party createParty(String partyName, String Code, String accessToken) throws Exception {
		Party party = new Party();
		UUID uuid = UUID.randomUUID();
		party.setId(uuid);
		party.setName(partyName);
		party.setToken(accessToken);
		
		Connection conn = databaseService.createConnection();
		String query = "{CALL createParty(?, ?, ?, ?)}";
		CallableStatement statement = conn.prepareCall(query);
		statement.setString(1, party.getId().toString());
		statement.setString(2, party.getName());
		statement.setString(3, party.getCode());
		statement.setString(4, party.getToken());
		statement.executeQuery();
		conn.close();		
		return party;
	}
	
	public void updatePartyToken(String newToken, UUID partyId) throws Exception {
		Connection conn = databaseService.createConnection();
		String query = "{CALL updatePartyToken(?, ?)}";
		CallableStatement statement = conn.prepareCall(query);
		statement.setString(1, newToken);
		statement.setString(2, partyId.toString());
		statement.executeQuery();
		conn.close();		
	}

}
