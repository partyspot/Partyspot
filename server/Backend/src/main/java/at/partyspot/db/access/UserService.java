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
import at.partyspot.db.model.User;
import at.partyspot.db.model.Userrole;

@Stateless
public class UserService extends DatabaseService {

	@EJB
	DatabaseService databaseService;
	@EJB
	UserroleService userroleService;
	@EJB
	PartyService partyService;
	
	public List<User> getAll() throws Exception {
		List<User> users = new ArrayList<User>();
		Connection conn = databaseService.createConnection();
		String query = "{CALL getAllUsers()}";
		CallableStatement statement = conn.prepareCall(query);
		ResultSet resultSet = statement.executeQuery();
		while (resultSet.next()) {
			User user = new User();
			UUID uuid = UUID.fromString(resultSet.getString("id"));
			user.setId(uuid);
			user.setName(resultSet.getString("name"));
			UUID userroleId = UUID.fromString(resultSet.getString("userrole_id"));
			Userrole userrole = userroleService.getUserrole(userroleId);
			user.setUserrole(userrole);
			Party party = partyService.getParty(UUID.fromString(resultSet.getString("party_id")));
			user.setParty(party);
			users.add(user);
		}
		conn.close();
		return users;
	}

	public User getUser(String name) throws Exception {
		User user = new User();
		Connection conn = databaseService.createConnection();
		String query = "{CALL getUserByName(?)}";
		CallableStatement statement = conn.prepareCall(query);
		statement.setString(1, name);
		ResultSet resultSet = statement.executeQuery();
		if (resultSet.next()) {
			UUID uuid = UUID.fromString(resultSet.getString("id"));
			user.setId(uuid);
			user.setName(resultSet.getString("name"));
			UUID userroleId = UUID.fromString(resultSet.getString("userrole_id"));
			Userrole userrole = userroleService.getUserrole(userroleId);
			user.setUserrole(userrole);
			Party party = partyService.getParty(UUID.fromString(resultSet.getString("party_id")));
			user.setParty(party);
		}
		conn.close();
		return user;
	}

	public User getUser(UUID id) throws Exception {
		User user = new User();
		Connection conn = databaseService.createConnection();
		String query = "{CALL getUserById(?)}";
		CallableStatement statement = conn.prepareCall(query);
		statement.setString(1, id.toString());
		ResultSet resultSet = statement.executeQuery();
		if (resultSet.next()) {
			UUID uuid = UUID.fromString(resultSet.getString("id"));
			user.setId(uuid);
			user.setName(resultSet.getString("name"));
			UUID userroleId = UUID.fromString(resultSet.getString("userrole_id"));
			Userrole userrole = userroleService.getUserrole(userroleId);
			user.setUserrole(userrole);
			Party party = partyService.getParty(UUID.fromString(resultSet.getString("party_id")));
			user.setParty(party);
		}
		conn.close();
		return user;
	}
	
	public User createUser(String name, UUID roleId, UUID partyId) throws Exception {
		User user = new User();
		UUID id = UUID.randomUUID();
		user.setId(id);
		user.setName(name);
		
		Connection conn = databaseService.createConnection();
		String query = "{CALL createUser(?, ?, ?, ?)}";
		CallableStatement statement = conn.prepareCall(query);
		statement.setString(1, user.getId().toString());
		statement.setString(2, name);
		statement.setString(3, roleId.toString());
		statement.setString(4, partyId.toString());
		statement.executeQuery();
		statement.close();
		conn.close();
		return user;
	}
	
	public void connectUserWithSongs(UUID userId, UUID songId) throws Exception {
		Connection conn = databaseService.createConnection();
		String query = "{CALL connectUserToSongs(?, ?)}";
		CallableStatement statement = conn.prepareCall(query);
		statement.setString(1, userId.toString());
		statement.setString(2, songId.toString());
		statement.executeQuery();
		conn.close();
	}

}
