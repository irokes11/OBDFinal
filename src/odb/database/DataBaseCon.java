package odb.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseCon {

	private static final String JDB_DRIVER  = "oracle.jdbc.driver.OracleDriver";
	private static final String DB_URL  = "jdbc:oracle:thin:@ora3.elka.pw.edu.pl:1521:ora3inf";
	private static final String USER = "iseredyn";
	private static final String PASS  = "iseredyn"; 
	private Connection connection = null;
	private static DataBaseCon DataBaseCon;

	private DataBaseCon() throws ClassNotFoundException {
		init();
	}

	private void init() throws ClassNotFoundException {
		Class c = Class.forName(JDB_DRIVER );
	}
	
	public static DataBaseCon getInstance() throws ClassNotFoundException {
		if (DataBaseCon == null) {
			DataBaseCon = new DataBaseCon();
		}
		return DataBaseCon;
	}

	

	public void open() throws SQLException {		
		connection = DriverManager.getConnection(DB_URL , USER, PASS);		
	}

	public void close() {
		try {
			if (connection != null) {
				connection.close();
				System.out.println("Closing connection with Database");
			}
			connection = null;
		} catch (SQLException e) {
		}
		
	}

	public Statement getStatement() throws SQLException {
		return connection.createStatement();
	}

	public static SimpleQuery simpleQuery(String tableName, String where) {
		return new SimpleQuery(tableName, where);
	}

}
