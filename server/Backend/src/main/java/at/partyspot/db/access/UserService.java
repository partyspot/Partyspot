package at.partyspot.db.access;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import at.partyspot.db.model.User;

//import at.partyspot.db.model.Userrole;

public class UserService extends DatabaseService {

	public List<User> getAll() throws Exception {
		List<User> users = new ArrayList<User>();
		DatabaseService databaseService = new DatabaseService();
		ResultSet resultSet = databaseService.getAll("puser");
		if (resultSet.getFetchSize() > 0) {
			while (resultSet.next()) {
				User user = new User();
				UUID uuid = UUID.fromString(resultSet.getString("id"));
				user.setId(uuid);
				user.setName(resultSet.getString("name"));
				users.add(user);
			}
		}
		return users;
	}

	public User getUser(String name) throws Exception {
		User user = new User();
		DatabaseService databaseService = new DatabaseService();
		ResultSet resultSet = databaseService.getByName("puser", name);
		if (resultSet.getFetchSize() > 0) {
			UUID uuid = UUID.fromString(resultSet.getString("id"));
			user.setId(uuid);
			user.setName(resultSet.getString("name"));
		}
		return user;
	}
	
	public User getUser(UUID id) throws Exception {
		User user = new User();
		DatabaseService databaseService = new DatabaseService();
		ResultSet resultSet = databaseService.getById("puser", id);
		if (resultSet.getFetchSize() > 0) {
			UUID uuid = UUID.fromString(resultSet.getString("id"));
			user.setId(uuid);
			user.setName(resultSet.getString("name"));
		}
		return user;
	}

}
