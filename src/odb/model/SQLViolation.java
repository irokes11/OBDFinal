package odb.model;

public class SQLViolation extends Exception {
	
	public SQLViolation(String tableName, int id) {
		System.out.println("Naruszono wiezy sp�jnosci - nie znaleziono klucza nadrzednego o id" +id+" "+"w tabeli "+tableName);
	}

}
