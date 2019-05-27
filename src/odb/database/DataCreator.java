package odb.database;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

public class DataCreator {

	public static InsertEngine insertWithCheckExists(TableStructure def) {
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
			for (ColumnDefinition col : def.getColumns()) {
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

			if (checkIdExists && idExists(def.getTableName(), colNameId, valId)) {

				return;
			}

			StringBuilder insertDML = new StringBuilder();

			insertDML.append("insert into " + def.getTableName() + " (");
			insertDML.append(colNames);
			insertDML.append(") values (");
			insertDML.append(colVals);
			insertDML.append(")");

//			System.out.println(insertDML);
			DataBaseCon db = DataBaseCon.getInstance();
			Statement stat = db.getStatement();			
		    stat.executeUpdate(insertDML.toString());
			return;
		
		}

		private boolean idExists(String tableName, String colNameId, String val) throws ClassNotFoundException, SQLException {
			String where = colNameId + " = " + val;
			SimpleQuery sq = new SimpleQuery(tableName, where);
			return sq.anyRowExists();
		}

	}
}
