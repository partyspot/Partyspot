package at.partyspot.db.access;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.UUID;

public class DatabaseService {

	private Connection connect = null;
    private Statement statement = null;
    private ResultSet resultSet = null;
    private String dbDriver = "com.mysql.jdbc.Driver";
    private String url = "jdbc:mysql://localhost:3306/partyspot";
    private String dbUser = "root";
    private String dbPass = "!Dr.Hugo8010";
    
    public Connection createConnection() throws Exception {
    	Connection mySqlCon = null;
    	try {
            // This will load the MySQL driver, each DB has its own driver
            Class.forName(dbDriver);
            // Setup the connection with the DB
            mySqlCon = DriverManager
                    .getConnection(url, dbUser, dbPass);
    	}
    	catch (Exception e) {
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
    
    public ResultSet getAll(String table) throws Exception {
		DatabaseService ds = new DatabaseService();
		Connection conn = ds.createConnection();
		Statement statement = conn.createStatement();
		String sql = "select * from " + table.toString();
        // Result set get the result of the SQL query
        ResultSet resultSet = statement
                .executeQuery(sql);
        return resultSet;
	}
	
	public ResultSet getById(String table, UUID id) throws Exception {
		DatabaseService ds = new DatabaseService();
		Connection conn = ds.createConnection();
		Statement statement = conn.createStatement();
		String sql = "select * from " + table.toString() + " where id = UUID_TO_BIN('"+ id.toString() +"')";
        // Result set get the result of the SQL query
        ResultSet resultSet = statement
                .executeQuery(sql);
        return resultSet;
	}
	
	public ResultSet getByName(String table, String name) throws Exception {
		DatabaseService ds = new DatabaseService();
		Connection conn = ds.createConnection();
		Statement statement = conn.createStatement();
		String sql = "select * from " + table.toString() + "where name = "+ name;
        // Result set get the result of the SQL query
        ResultSet resultSet = statement
                .executeQuery(sql);
        return resultSet;
	}
}
