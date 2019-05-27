package odb.database;

public class DBConstraintViolation extends Exception {
	
	public DBConstraintViolation(String tableName, int id) {
		super(String.format("Naruszono wiezy spójnosci - nie znaleziono klucza nadrzednego o id=%d w tabeli \"%s\".",id, tableName));
	}

}
