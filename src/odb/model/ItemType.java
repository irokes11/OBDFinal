package odb.model;

import java.util.function.Function;

public enum ItemType {
	CHAR("char", x -> "'"+x+"'" ),
	INT("integer",x -> "'"+String.valueOf(x)+"'" ),
	NUMERIC("numeric", x -> "'"+(String.valueOf(x))+"'" ),
	TEXT("varchar2", x -> "'"+x+"'" ),
	DOUBLE("double", x -> "'"+x+"'" ),
	FLOAT("float", x -> "'"+(String.valueOf(x))+"'" );
	
	private String desc; 
	private Function<Object, String> mapValToStringFunction;
	
	ItemType(String desc, Function<Object, String> mapValToStringFunction) {
		this.desc = desc;
		this.mapValToStringFunction = mapValToStringFunction;
	}
	
	public String mapValToString(Object value) {
		if (value==null || (value instanceof String && ((String)value).isEmpty())) {
			return "null";
		} else {
		   return mapValToStringFunction.apply(value);
		}
	}

	public String getDesc() {
		return desc;
	}
	
	
}