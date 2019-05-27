package odb.database;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class TablesCreator {

	List<TableStructure> tables;

	public TablesCreator(TableStructure... tables) {
		this.tables = Arrays.asList(tables);
	}
	
	public TablesCreator(List<TableStructure> tables) {
		this.tables = tables;
	}
	
	public void dropAllIfExists() throws SQLException, ClassNotFoundException {
		for (TableStructure td : tables) {
			if (tableExists(td.getTableName())) {
				dropTable(td);				
			}			
		}
	}

	public void createAllIfNotExists(Consumer<TableStructure> consumer) throws SQLException, ClassNotFoundException {
		for (TableStructure td : tables) {
			if (!tableExists(td.getTableName())) {
				createTable(td);				
			}
			consumer.accept(td);
		}
	}

	private boolean tableExists(String tableName) throws SQLException, ClassNotFoundException {
		return DataBaseCon.simpleQuery("user_tables", "table_name=upper('" + tableName + "')").anyRowExists();
	}

	private void createTable(TableStructure table) throws SQLException, ClassNotFoundException {
		DataBaseCon db = DataBaseCon.getInstance();		
		Statement statement = db.getStatement();

		StringBuilder sb = new StringBuilder();
		sb.append("CREATE TABLE ");
		sb.append(table.getTableName());
		sb.append("(");
		boolean isFirst = true;
		for(ColumnDefinition col : table.getColumns()) {
			sb.append("\n");
			if (!isFirst) {
				sb.append(", ");
			}
			sb.append(col.getColumnName() + " ");
			sb.append(col.getColumnTypeDefinition());
			if (!col.isNullable()) {
				sb.append(" NOT NULL");
			}
			isFirst = false;
		}
		sb.append(")");
//		System.out.println(sb.toString());
		statement.execute(sb.toString());

	}
	
	private void dropTable(TableStructure table) throws SQLException, ClassNotFoundException {		
		DataBaseCon db = DataBaseCon.getInstance();		
		Statement statement = db.getStatement();

		StringBuilder sb = new StringBuilder();
		sb.append("DROP TABLE ");
		sb.append(table.getTableName());				
		statement.execute(sb.toString());
		
	}


}
