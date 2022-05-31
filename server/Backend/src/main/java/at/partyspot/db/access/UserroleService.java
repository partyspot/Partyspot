package at.partyspot.db.access;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;


//import at.partyspot.db.model.Userrole;

public class UserroleService  {

	/*public UserroleService() {
		super(Userrole.class);
	}*/
	
	public ResultSet getUserrole() throws Exception {
		DatabaseService ds = new DatabaseService();
		Connection conn = ds.createConnection();
		Statement statement = conn.createStatement();
        // Result set get the result of the SQL query
        ResultSet resultSet = statement
                .executeQuery("select * from partyspot.userrole");
        return resultSet;
	}
	
	
	public void createUserrole() throws Exception {
		DatabaseService ds = new DatabaseService();
		Connection conn = ds.createConnection();
        PreparedStatement preparedStatement = conn
                .prepareStatement("insert into  partyspot.userrole values (UUID_TO_BIN('c36675af-2e2b-42d3-8f62-011d86f57ea2'), ?)");
        preparedStatement.setString(1, "Testrolle");
        preparedStatement.execute();
	}
	
}
