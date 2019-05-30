package odb.model;

import java.util.ArrayList;
import java.util.List;

public class TableStructure {
	private int minId = 0;
	private int maxId = 0;
	private String tableName;
	private List<ColPattern> columns = new ArrayList<>();
	
	
	public TableStructure(String tableName) {
		this.tableName = tableName;			
	}
	
	public TableStructure addIdColumn(String columnName) {		
		columns.add(ColPattern.createId(columnName) );		
		return this;
	}
	
	public TableStructure addIntColumn(String columnName) {
		columns.add(ColPattern.createInt(columnName) );
		return this;
	}	
	
	public TableStructure addTextColumn(String columnName, int length) {
		columns.add(ColPattern.createText(columnName, length) );
		return this;

	}
	
	public TableStructure addCharCol(String columnName, int length) {
		columns.add(ColPattern.createChars(columnName, length) );
		return this;
	}
	
	public TableStructure addFloatColumn(String columnName, int length, int precision) {
		columns.add(ColPattern.createFloat(columnName) );
	return this;

	}
	public TableStructure addDoubleColumn(String columnName, int length, int precision) {
		columns.add(ColPattern.createDouble(columnName) );
	return this;

	}
	
	public String getTableName() {
		return tableName;
	}
			
	public List<ColPattern> getColumns() {
		return columns;
	}
	
	public String getIdColumnName() {
		return columns.stream().filter(ColPattern::isId).map(ColPattern::getColumnName).findFirst().orElse("");
	}

	public int getMinId() {
		return minId;
	}

	public void setMinId(int minId) {
		this.minId = minId;
	}

	public int getMaxId() {
		return maxId;
	}

	public void setMaxId(int maxId) {
		this.maxId = maxId;
	}
	
	public boolean hasId(int id) {
		return (id>=minId && id<=maxId);
	}

}
