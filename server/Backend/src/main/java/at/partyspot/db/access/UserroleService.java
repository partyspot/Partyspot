package at.partyspot.db.access;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import at.partyspot.db.model.Userrole;

@Stateless
public class UserroleService {
	
	@EJB
	DatabaseService databaseService;

	public List<Userrole> getAll() throws Exception {
		List<Userrole> userroles = new ArrayList<Userrole>();
		Connection conn = databaseService.createConnection();
		String query = "{CALL getAllUserroles()}";
		CallableStatement statement = conn.prepareCall(query);
		ResultSet resultSet = statement.executeQuery();

		while (resultSet.next()) {
			Userrole userrole = new Userrole();
			UUID uuid = UUID.fromString(resultSet.getString("id"));
			userrole.setId(uuid);
			userrole.setName(resultSet.getString("name"));
			userroles.add(userrole);
		}
		conn.close();
		return userroles;
	}

	public Userrole getUserrole(String name) throws Exception {
		Userrole userrole = new Userrole();
		Connection conn = databaseService.createConnection();
		String query = "{CALL getUserroleByName(?)}";
		CallableStatement statement = conn.prepareCall(query);
		statement.setString(1, name);
		ResultSet resultSet = statement.executeQuery();
		if (resultSet.next()) {
			UUID uuid = UUID.fromString(resultSet.getString("id"));
			userrole.setId(uuid);
			userrole.setName(resultSet.getString("name"));
		}
		conn.close();
		return userrole;
	}

	public Userrole getUserrole(UUID id) throws Exception {
		Userrole userrole = new Userrole();
		Connection conn = databaseService.createConnection();
		String query = "{CALL getUserroleById(?)}";
		CallableStatement statement = conn.prepareCall(query);
		statement.setString(1, id.toString());
		ResultSet resultSet = statement.executeQuery();
		if (resultSet.next()) {
			UUID uuid = UUID.fromString(resultSet.getString("id"));
			userrole.setId(uuid);
			userrole.setName(resultSet.getString("name"));
		}
		conn.close();
		return userrole;
	}

}
