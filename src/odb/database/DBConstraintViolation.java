package odb.database;

public class DBConstraintViolation extends Exception {
	
	public DBConstraintViolation(String tableName, int id) {
		System.out.println("Naruszono wiezy sp�jnosci - nie znaleziono klucza nadrzednego o id" +id+" "+"w tabeli "+tableName);
	}

}
