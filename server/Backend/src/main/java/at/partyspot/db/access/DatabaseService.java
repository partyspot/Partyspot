package at.partyspot.db.access;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import at.partyspot.rest.resources.FileHandler;
import at.partyspot.rest.resources.Utilities;

public class DatabaseService {

	private Connection connect = null;
	private Statement statement = null;
	private ResultSet resultSet = null;
	//private String dbDriver = "com.mysql.jdbc.Driver";
	//private String url = "jdbc:mysql://localhost:3306/partyspot";
	//private String dbUser = "root";
	//private String dbPass = "!Dr.Hugo8010";

	public Connection createConnection() throws Exception {
		Connection mySqlCon = null;
		
		HashMap<String, Object> config = FileHandler.getDBConfigData();
		String dbDriver = config.get("driver").toString();
		String url = config.get("url").toString();
		String dbUser = config.get("user").toString();
		String dbPass = config.get("password").toString();
		
		try {
			// This will load the MySQL driver, each DB has its own driver
			Class.forName(dbDriver);
			// Setup the connection with the DB
			mySqlCon = DriverManager.getConnection(url, dbUser, dbPass);
		} catch (Exception e) {
			throw e;
		} finally {
			close();
		}
		return mySqlCon;
	}

	private void close() {
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

	public HashMap<String, List<String>> getColumnsInfo(String table) throws Exception {
		HashMap<String, List<String>> columns = new HashMap<String, List<String>>();
		DatabaseService databaseService = new DatabaseService();
		Connection connection = databaseService.createConnection();
		DatabaseMetaData metadata = connection.getMetaData();
		ResultSet resultSet = metadata.getColumns(null, null, table, null);

		while (resultSet.next()) {
			List<String> columnDefinition = new ArrayList<String>();
			String name = resultSet.getString("COLUMN_NAME");
			String type = resultSet.getString("TYPE_NAME");
			String size = String.valueOf(resultSet.getInt("COLUMN_SIZE"));
			columnDefinition.add(type);
			columnDefinition.add(size);
			columns.put(name, columnDefinition);
		}
		return columns;
	}
	
	private String buildColumnString(String table) throws Exception {
		String columnString = "";
		HashMap<String, List<String>> columns = getColumnsInfo(table);
		for(String column : columns.keySet()) {
			String toBeAdded = column;
			if(isBinary16(columns.get(column).get(0), columns.get(column).get(1))) {
				toBeAdded = Utilities.binToUuid(column);
			} 
			columnString = Utilities.addDelimited(columnString, toBeAdded, ", ");
		}
		
		return columnString;
	}
	
	private boolean isBinary16(String type, String size) {
		if(type.equals("BINARY") && size.equals("16")) {
			return true;
		}
		return false;
	}
	
	public String buildSelect(String table, String name, UUID id) throws Exception {
		String sql = "";
		String columnString = buildColumnString(table);
		sql = "select " + columnString + " from " + table.toString();
		//String finalSql = "";
		if(Utilities.isNetherNullNorEmpty(name) && id != null) {
			sql = sql.concat(" where name = '" + name + "' and id = " + Utilities.uuidToBin(id));
		} else {
			if(Utilities.isNetherNullNorEmpty(name)) {
				sql = sql.concat(" where name = '" + name + "'");
			}
			if(id != null) {
				sql = sql.concat(" where id = " + Utilities.uuidToBin(id));
			}
		}
		return sql;
	}

	public ResultSet getAll(String table) throws Exception {
		DatabaseService ds = new DatabaseService();
		Connection conn = ds.createConnection();
		Statement statement = conn.createStatement();
		//String columnString = buildColumnString(table);
		//String sql = "select " + columnString + " from " + table.toString();
		// Result set get the result of the SQL query
		ResultSet resultSet = statement.executeQuery(buildSelect(table, null, null));
		return resultSet;
	}

	public ResultSet getById(String table, UUID id) throws Exception {
		DatabaseService ds = new DatabaseService();
		Connection conn = ds.createConnection();
		Statement statement = conn.createStatement();
		//String columnString = buildColumnString(table);
		//String sql = "select " + columnString + " from " + table.toString() + " where id = " + Utilities.uuidToBin(id);
		// Result set get the result of the SQL query
		ResultSet resultSet = statement.executeQuery(buildSelect(table, null, id));
		return resultSet;
	}

	public ResultSet getByName(String table, String name) throws Exception {
		DatabaseService ds = new DatabaseService();
		Connection conn = ds.createConnection();
		Statement statement = conn.createStatement();
		//String columnString = buildColumnString(table);
		//String sql = "select " + columnString + " from " + table.toString() + " where name = '" + name + "'";
		// Result set get the result of the SQL query
		ResultSet resultSet = statement.executeQuery(buildSelect(table, name, null));
		return resultSet;
	}
}
