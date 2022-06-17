package at.partyspot.db.access;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

import javax.ejb.Stateless;

import at.partyspot.rest.resources.FileHandler;

@Stateless
public class DatabaseService {

	private Connection connect = null;
	private Statement statement = null;
	private ResultSet resultSet = null;

	public Connection createConnection() throws Exception {
		Connection mySqlCon = null;
		
		HashMap<String, Object> config = FileHandler.getDBConfigData();
		String dbDriver = config.get("driver").toString();
		String url = config.get("url").toString();
		String dbUser = config.get("user").toString();
		String dbPass = config.get("password").toString();
		
		try {
			Class.forName(dbDriver);
			mySqlCon = DriverManager.getConnection(url, dbUser, dbPass);
		} catch (Exception e) {
			throw e;
		} finally {
			close();
		}
		return mySqlCon;
	}

	public void close() {
		try {
			if (resultSet != null) {
				resultSet.close();
			}

			if (statement != null) {
				statement.close();
			}

			if (connect != null) {
				connect.close();
			}
		} catch (Exception e) {

		}
	}

}
