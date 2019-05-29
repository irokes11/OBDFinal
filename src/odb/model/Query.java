package odb.model;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class Query {

	private String where;
	private String table;


	public Query(String table, String where) {
		this.where = where;
		this.table = table;
		
	}

	public boolean anyRowExists() throws SQLException, ClassNotFoundException {
		DataBaseCon db = DataBaseCon.getInstance();
		int count = 0;
		Statement statement = db.getStatement();
		String query = "select 1 from " + table + " where " + where;
		ResultSet rs = statement.executeQuery(query);
		return rs.next();
	}

}
