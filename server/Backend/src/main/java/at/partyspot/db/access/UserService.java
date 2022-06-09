package at.partyspot.db.access;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import at.partyspot.db.model.User;

@Stateless
public class UserService extends DatabaseService {

	@EJB
	DatabaseService databaseService;
	
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
		}
		conn.close();
		return user;
	}

}
