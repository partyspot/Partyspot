package at.partyspot.db.access;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import at.partyspot.db.model.Userrole;

public class UserroleService {

	public List<Userrole> getAll() throws Exception {
		List<Userrole> userroles = new ArrayList<Userrole>();
		DatabaseService databaseService = new DatabaseService();
		ResultSet resultSet = databaseService.getAll("userrole");
		while (resultSet.next()) {
			Userrole userrole = new Userrole();
			UUID uuid = UUID.fromString(resultSet.getString("id"));
			userrole.setId(uuid);
			userrole.setName(resultSet.getString("name"));
			userroles.add(userrole);
		}
		return userroles;
	}

	public Userrole getUserrole(String name) throws Exception {
		Userrole userrole = new Userrole();
		DatabaseService databaseService = new DatabaseService();
		ResultSet resultSet = databaseService.getByName("userrole", name);
		if (resultSet.next()) {
			UUID uuid = UUID.fromString(resultSet.getString("id"));
			userrole.setId(uuid);
			userrole.setName(resultSet.getString("name"));
		}
		return userrole;
	}

	public Userrole getUserrole(UUID id) throws Exception {
		Userrole userrole = new Userrole();
		DatabaseService databaseService = new DatabaseService();
		ResultSet resultSet = databaseService.getById("userrole", id);
		if (resultSet.next()) {
			UUID uuid = UUID.fromString(resultSet.getString("id"));
			userrole.setId(uuid);
			userrole.setName(resultSet.getString("name"));
		}
		return userrole;
	}

}
