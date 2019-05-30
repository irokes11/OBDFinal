package odb.model;

import java.util.function.Function;

import javax.xml.soap.Text;

public class TypeOfColumn {
	
	private ItemType type;
	private int length;
	private int precision;
	
	public TypeOfColumn(ItemType type, int length, int precision) {		
		this.type = type;
		this.length = length;
		this.precision = precision;
	}
	
	public TypeOfColumn(ItemType type) {		
		this.type = type;
		this.length = 0;
		this.precision = 0;
	}
	
	public ItemType getTypeName() {
		return type;
	}
	
	public TypeOfColumn(ItemType type, int length) {		
		this.type = type;
		this.length = length;
		this.precision = 0;
	}
	public int getLength() {
		return length;
	}
	
	public String valToDbVal(Object o) {
		return type.mapValToString(o);
	}
	
	public String getToText() {
		String typeName = type.getDesc();
		switch (type) {
		case CHAR:
		case TEXT: return typeName+"("+length+")";		 
		case FLOAT:
		case INT: return typeName; 
		case DOUBLE: 
		case NUMERIC :return typeName+"("+length+","+precision+")";
		}
		return "";
	}	

	
	public static TypeOfColumn intColumn() { return new TypeOfColumn(ItemType.INT); }
	public static TypeOfColumn floatColumn() { return new TypeOfColumn(ItemType.FLOAT); }
	public static TypeOfColumn doubleColumn() { return new TypeOfColumn(ItemType.DOUBLE); }
	public static TypeOfColumn textColumn(int maxLength) { return new TypeOfColumn(ItemType.TEXT, maxLength); }
	public static TypeOfColumn charsColumn(int fixedLength) { return new TypeOfColumn(ItemType.CHAR, fixedLength); }
	public static TypeOfColumn numericColumn(int length, int precision) { return new TypeOfColumn(ItemType.NUMERIC, length, precision); }
	

}
