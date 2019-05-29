package odb.model;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.stream.IntStream;

public class SQLTablesProject {

	public static final String STUDENTS = "Uczen";
	public static final String DEGREES = "Ocena";
	public static final String TEACHER = "Nauczyciel";
	public static final String SUBJECT = "Przedmiot";
	public static final String GRADING = "Ocenianie";

	private TableStructure subjects;
	private TableStructure teachers;
	private TableStructure students;
	private TableStructure degrees;
	private TableStructure issuingGrades;

	private static SQLTablesProject instance = null;

	private SQLTablesProject() {   // Allocating proper items into the columns in sequence
		subjects = new TableStructure(SUBJECT).addIdColumn("idp").addCharCol("nazwa", 20);

		teachers = new TableStructure(TEACHER).addIdColumn("idn").addCharCol("nazwisko", 30)
				.addCharCol("imie", 20);

		students = new TableStructure(STUDENTS).addIdColumn("idu").addCharCol("nazwisko", 30)
				.addCharCol("imie", 20);

		degrees = new TableStructure(DEGREES).addIdColumn("ido").addCharCol("wartosc_opisowa", 20)
				.addFloatColumn("wartosc_numeryczna", 20, 2);

		issuingGrades = new TableStructure(GRADING).addIntColumn("idp").addIntColumn("ido")
				.addIntColumn("idn").addIntColumn("idu").addCharCol("rodzaj_oceny", 1);

	}

	public static SQLTablesProject Instance() {
		if (instance == null) {
			instance = new SQLTablesProject();
		}
		return instance;
	}

	public void createData(boolean dropAllTablesBefore, boolean insertData)
			throws SQLException, ClassNotFoundException {
		Tables table = new Tables(subjects, teachers, students, degrees, issuingGrades);

		if (dropAllTablesBefore) {
			table.dropAllIfExists();
		}

		table.createAllIfNotExists(x -> {
			if (insertData) {
				try {
					insertData(x);
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}
			}
		});

	}

	public void insertData(TableStructure tableDef) throws ClassNotFoundException, SQLException {
		switch (tableDef.getTableName()) {
		case STUDENTS:
			insertStudents(tableDef);
			break;
		case TEACHER:
			insertTeachers(tableDef);
			break;
		case SUBJECT:
			insertSubjects(tableDef);
			break;
		case DEGREES:
			insertDegrees(tableDef);
			break;
		}
	}

	private void insertStudents(TableStructure table) throws ClassNotFoundException, SQLException {
		Data.insert(students).values(1, "Janek", "Wisniewski");
		Data.insert(students).values(2, "Ireneusz", "Seredyn");
		Data.insert(students).values(3, "Szymon", "Kulej");
		Data.insert(students).values(4, "Ibrahim", "Ahmed");
		table.setMinId(1);
		table.setMaxId(4);
	}

	private void insertTeachers(TableStructure table) throws ClassNotFoundException, SQLException {
		Data.insert(teachers).values(1, "Maria", "Zakrzewska");
		Data.insert(teachers).values(2, "Grazyna", "Przytula");
		Data.insert(teachers).values(3, "Piotr", "Seredyn");
		Data.insert(teachers).values(4, "Adam", "Mickiewicz");
		table.setMinId(1);
		table.setMaxId(4);
	}

	private void insertSubjects(TableStructure table) throws ClassNotFoundException, SQLException {
		Data.insert(subjects).values(1, "Biologia");
		Data.insert(subjects).values(2, "Wychowanie Fizyczne");
		Data.insert(subjects).values(3, "Samo Obrona");
		Data.insert(subjects).values(4, "Podstawy Islamu");
		table.setMinId(1);
		table.setMaxId(4);
	}


	private void insertDegrees(TableStructure table) throws ClassNotFoundException, SQLException {
		//String sql1 = "INSERT INTO OCENA (IDO,WARTOSC_OPISOWA,WARTOSC_NUMERYCZNA) VALUES (1,'jedynka',1)";
		Data.insert(degrees).values(1,"jedynka",1);
		Data.insert(degrees).values(2,"jeden +",1.5);
		Data.insert(degrees).values(3,"dwa",2);
		Data.insert(degrees).values(4,"dwa +",2.5);
		Data.insert(degrees).values(5,"trzy",3);
		Data.insert(degrees).values(6,"trzy +",3.5);
		Data.insert(degrees).values(7,"cztery",4);
	    Data.insert(degrees).values(8,"cztery +",4.5);
		Data.insert(degrees).values(9,"piec",5);
		Data.insert(degrees).values(10,"piec +",5.5);
		Data.insert(degrees).values(11,"szesc",6);
	}



	public TableStructure getSubjects() {
		return subjects;
	}

	public TableStructure getTeachers() {
		return teachers;
	}

	public TableStructure getStudents() {
		return students;
	}

	public TableStructure getDegrees() {
		return degrees;
	}

	public TableStructure getIssuingGrades() {
		return issuingGrades;
	}

}
