package odb.model;

public class ColPattern {
	
	private boolean isId;
	private String columnName;
	private TypeOfColumn columnType;	
	private boolean isNullable = false;
	
	public ColPattern(String columnName, TypeOfColumn columnType, boolean isId) {		
		this.isId = isId;
		this.columnName = columnName;
		this.columnType = columnType;
	}

	public boolean isId() {
		return isId;
	}
	
	public static ColPattern createId(String name) {
		  return new ColPattern(name, TypeOfColumn.intColumn(), true);
	}
	
	public static ColPattern createInt(String name) {
		  return new ColPattern(name, TypeOfColumn.intColumn(), false);
	}
	
	public static ColPattern createText(String name, int maxLenght) {
		  return new ColPattern(name, TypeOfColumn.textColumn(maxLenght), false);
	}
	
	public static ColPattern createNumeric(String name, int lenght, int precision) {
		  return new ColPattern(name, TypeOfColumn.numericColumn(lenght, precision), false);
	}
	
	public static ColPattern createChars(String name, int lenght) {
		  return new ColPattern(name, TypeOfColumn.charsColumn(lenght), false);
		  
	  
	}
	
	public static ColPattern createFloat(String name) {
		  return new ColPattern(name, TypeOfColumn.floatColumn(), false);
	}

	public String getColumnName() {
		return columnName;
	}

	public TypeOfColumn getColumnType() {
		return columnType;
	}
	
	public String getColumnTypeDefinition() {
		return columnType.getToText();
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
