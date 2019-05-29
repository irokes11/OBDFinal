package odb.model;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

public class Data {

	public static InsertEngine insert(TableStructure def) {
		return new InsertEngine(def, true);
	}

	public static class InsertEngine {

		private TableStructure def;
		private boolean checkIdExists;

		public InsertEngine(TableStructure def, boolean checkIdExists) {
			this.def = def;
			this.checkIdExists = checkIdExists;
		}

		public void values(Object... objects) throws ClassNotFoundException, SQLException {
	
			int colIndex = 0;

			StringBuilder colNames = new StringBuilder();
			StringBuilder colVals = new StringBuilder();
			String valId = "";
			String colNameId = "";
			for (ColPattern col : def.getColumns()) {
				if (colIndex > 0) {
					colNames.append(", ");
					colVals.append(", ");
				}
				colNames.append(col.getColumnName());
				colVals.append(col.getColumnType().valToDbVal(objects[colIndex]));

				if (col.isId()) {
					valId = col.getColumnType().valToDbVal(objects[colIndex]);
					colNameId = col.getColumnName();
				}
				colIndex++;

			}

			if (checkIdExists && id(def.getTableName(), colNameId, valId)) {

				return;
			}

			StringBuilder SQL = new StringBuilder();

			SQL.append("INSERT INTO " + def.getTableName() + " (");
			SQL.append(colNames);
			SQL.append(") VALUES (");
			SQL.append(colVals);
			SQL.append(")");

//		
			DataBaseCon db = DataBaseCon.getInstance();
			Statement stat = db.getStatement();			
		    stat.executeUpdate(SQL.toString());
			return;
		
		}

		private boolean id(String tableName, String colNameId, String val) throws ClassNotFoundException, SQLException {
			String where = colNameId + " = " + val;
			Query sq = new Query(tableName, where);
			return sq.anyRowExists();
		}

	}
}
