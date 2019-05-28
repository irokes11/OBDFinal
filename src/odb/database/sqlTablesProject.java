package odb.database;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.stream.IntStream;

public class sqlTablesProject {

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

	private static sqlTablesProject instance = null;

	private sqlTablesProject() {
		subjects = new TableStructure(SUBJECT).addIdColumn("idp").addCharCol("nazwa", 20);

		teachers = new TableStructure(TEACHER).addIdColumn("idn").addCharCol("nazwisko", 30)
				.addCharCol("imie", 20);

		students = new TableStructure(STUDENTS).addIdColumn("idu").addCharCol("nazwisko", 30)
				.addCharCol("imie", 20);

		degrees = new TableStructure(DEGREES).addIdColumn("ido").addCharCol("wartosc_opisowa", 20)
				.addFloatColumn("wartosc_numeryczna", 10, 2);

		issuingGrades = new TableStructure(GRADING).addIntColumn("idp").addIntColumn("ido")
				.addIntColumn("idn").addIntColumn("idu").addCharCol("rodzaj_oceny", 1);

	}

	public static sqlTablesProject getInstance() {
		if (instance == null) {
			instance = new sqlTablesProject();
		}
		return instance;
	}

	public void createData(boolean dropAllTablesBefore, boolean insertData)
			throws SQLException, ClassNotFoundException {
		TablesCreator tCreator = new TablesCreator(subjects, teachers, students, degrees, issuingGrades);

		if (dropAllTablesBefore) {
			tCreator.dropAllIfExists();
		}

		tCreator.createAllIfNotExists(x -> {
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
		DataCreator.insertWithCheckExists(students).values(1, "Janek", "Wisniewski");
		DataCreator.insertWithCheckExists(students).values(2, "Ireneusz", "Seredyn");
		DataCreator.insertWithCheckExists(students).values(3, "Szymon", "Kulej");
		DataCreator.insertWithCheckExists(students).values(4, "Ibrahim", "Ahmed");
		table.setMinId(1);
		table.setMaxId(4);
	}

	private void insertTeachers(TableStructure table) throws ClassNotFoundException, SQLException {
		DataCreator.insertWithCheckExists(teachers).values(1, "Maria", "Zakrzewska");
		DataCreator.insertWithCheckExists(teachers).values(2, "Grazyna", "Przytula");
		DataCreator.insertWithCheckExists(teachers).values(3, "Piotr", "Seredyn");
		DataCreator.insertWithCheckExists(teachers).values(4, "Adam", "Mickiewicz");
		table.setMinId(1);
		table.setMaxId(4);
	}

	private void insertSubjects(TableStructure table) throws ClassNotFoundException, SQLException {
		DataCreator.insertWithCheckExists(subjects).values(1, "Biologia");
		DataCreator.insertWithCheckExists(subjects).values(2, "Wychowanie Fizyczne");
		DataCreator.insertWithCheckExists(subjects).values(3, "Samo Obrona");
		DataCreator.insertWithCheckExists(subjects).values(4, "Podstawy Islamu");
		table.setMinId(1);
		table.setMaxId(4);
	}

	private void insertDegrees(TableStructure table) throws ClassNotFoundException, SQLException {
		int idDegree = 1;
		for (Double degree : Arrays.asList(Double.valueOf(1.5), Double.valueOf(1.75), Double.valueOf(2),
				Double.valueOf(3), Double.valueOf(4), Double.valueOf(5))) {
			DataCreator.insertWithCheckExists(degrees).values(idDegree, getDegree(degree), degree);
			idDegree++;
		}
		table.setMinId(1);
		table.setMaxId(5);

	}

	private String getDegree(Double degree) {
		if (degree == 1.5) {
			return "jedynka +";
		}
		if (degree == 1.75) {
			return "dwa -";
		}
		if (degree == 2) {
			return "dwa";
		}
		if (degree == 3) {
			return "trzy";
		}
		if (degree == 4) {
			return "cztery";
		}
		if (degree == 5) {
			return "piec";
		}
		return "";
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
