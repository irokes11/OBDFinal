package odb.database;

public class ColumnPattern {
	
	private boolean isId;

	private String columnName;
	private ColumnType columnType;	
	private boolean isNullable = false;
	
	public ColumnPattern(String columnName, ColumnType columnType, boolean isId) {		
		this.isId = isId;
		this.columnName = columnName;
		this.columnType = columnType;
	}

	public boolean isId() {
		return isId;
	}
	
	public static ColumnPattern createId(String name) {
		  return new ColumnPattern(name, ColumnType.intColumn(), true);
	}
	
	public static ColumnPattern createInt(String name) {
		  return new ColumnPattern(name, ColumnType.intColumn(), false);
	}
	
	public static ColumnPattern createText(String name, int maxLenght) {
		  return new ColumnPattern(name, ColumnType.textColumn(maxLenght), false);
	}
	
	public static ColumnPattern createNumeric(String name, int lenght, int precision) {
		  return new ColumnPattern(name, ColumnType.numericColumn(lenght, precision), false);
	}
	
	public static ColumnPattern createChars(String name, int lenght) {
		  return new ColumnPattern(name, ColumnType.charsColumn(lenght), false);
		  
	  
	}
	
	public static ColumnPattern createFloat(String name) {
		  return new ColumnPattern(name, ColumnType.floatColumn(), false);
	}

	public String getColumnName() {
		return columnName;
	}

	public ColumnType getColumnType() {
		return columnType;
	}
	
	public String getColumnTypeDefinition() {
		return columnType.getTextDefinition();
	}
	
	public Integer getLength() {
		return columnType.getLength();
	}

	public boolean isNullable() {
		return isNullable;
	}

	public void setNullable(boolean isNullable) {
		this.isNullable = isNullable;
	}
		
	
}
