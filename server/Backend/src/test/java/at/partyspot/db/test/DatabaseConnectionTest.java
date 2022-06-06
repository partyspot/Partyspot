package at.partyspot.db.test;

import static org.junit.Assert.*;

import java.sql.Connection;

import org.junit.Test;

import at.partyspot.db.access.DatabaseService;

public class DatabaseConnectionTest {

	@Test
	public void testCreateConnection() throws Exception {
		
		DatabaseService databaseService = new DatabaseService();
		Connection connection = databaseService.createConnection();
		boolean isClosed = connection.isClosed();
		assertFalse(isClosed);
		
		//fail("Not yet implemented");
	}

}
