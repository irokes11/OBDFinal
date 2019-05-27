package odb.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseCon {

	private static final String driverName = "oracle.jdbc.driver.OracleDriver";
	private static final String url = "jdbc:oracle:thin:@ora3.elka.pw.edu.pl:1521:ora3inf";
	private static final String username = "iseredyn";
	private static final String password = "iseredyn"; 
	private Connection connection = null;
	private static DataBaseCon DataBaseCon;

	private DataBaseCon() throws ClassNotFoundException {
		init();
	}

	public static DataBaseCon getInstance() throws ClassNotFoundException {
		if (DataBaseCon == null) {
			DataBaseCon = new DataBaseCon();
		}
		return DataBaseCon;
	}

	private void init() throws ClassNotFoundException {
		Class c = Class.forName(driverName);
	}

	public void open() throws SQLException {		
		connection = DriverManager.getConnection(url, username, password);		
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
