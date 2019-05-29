package odb.model;

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
	//String sql1 = "INSERT INTO OCENA (IDO,WARTOSC_OPISOWA,WARTOSC_NUMERYCZNA) VALUES (1,'jedynka',1)";
	
	
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
	//	System.out.println("AutoCommit: "+connection.getAutoCommit()+ " "+connection.hashCode());
	//	Statement polecenie = connection.createStatement();
		//ResultSet rs = polecenie.executeQuery(sql1);
	//	System.out.println("execute "+polecenie.executeUpdate(sql1));
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

	public static Query Query(String tableName, String where) {
		return new Query(tableName, where);
	}

}
