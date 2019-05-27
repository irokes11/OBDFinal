import java.sql.SQLException;
import java.sql.SQLRecoverableException;
import java.util.InputMismatchException;

import odb.database.DataBaseCon;
import odb.database.DBConstraintViolation;
import odb.database.DataCreator;
import odb.database.DataCreator.InsertEngine;
import odb.view.viewTable;
import odb.view.view;
import odb.database.SimpleQuery;
import odb.database.TableStructure;
import odb.database.sqlTablesProject;

public class Application {

	public static void main(String... args) {
		Application app = new Application();
		app.run();
		System.out.println("Exit from application");
	}

	private void run() {
		DataBaseCon db = null;
		try {
			db = DataBaseCon.getInstance();
			if (db != null) {
				db.open();
				System.out.println("Connection successfull");
				createData();
				run1();
			} else {
				System.out.print("Connection failed");
				return;
			}                   //in case connection is unsuccesfull
		} catch (ClassNotFoundException e) {
			System.out.println("Driver not found");
		} catch (SQLRecoverableException e) {
			System.out.println("No connection with DataBase");
		} catch (SQLException e) {
			System.out.println("Unsuccesull connection with Database");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (db != null)
				db.close();
		}
	}

	private void addGradeToDatabase(int idu, int idn, int ido, int idp, String degree) throws Exception {
		sqlTablesProject tdp = sqlTablesProject.getInstance();

		table_foreign_key_constraint_check_or_throw_exception(idu, tdp.getStudents());
		table_foreign_key_constraint_check_or_throw_exception(idn, tdp.getTeachers());
		table_foreign_key_constraint_check_or_throw_exception(ido, tdp.getDegrees());
		table_foreign_key_constraint_check_or_throw_exception(idp, tdp.getSubjects());
		InsertEngine ie = new DataCreator.InsertEngine(tdp.getIssuingGrades(), false);
System.out.println("Adding grades to the Database");
		ie.values(idp, ido, idn, idu, degree);

	}
	
	public void createData() throws ClassNotFoundException, SQLException {
		System.out.println("Creating an examples. Please wait..");
		sqlTablesProject tdp = sqlTablesProject.getInstance();
		tdp.createData(true, true);
	}

	
	private void run1() throws Exception {
		view.showMainMenu();
		String activation = null;
		while (true) {
			activation = view.selectOperation();
			if (view.EXIT.equalsIgnoreCase(activation)) {
				view.close();
				return;
			}
			if (activation != null) {
				try {
					run2(activation);
				} catch (DBConstraintViolation e) {
				} catch (InputMismatchException e) {
					System.out.println(" Incorrect ID number. Please try again.");
					view.resetScanner();
				} catch (StringIndexOutOfBoundsException e) {
					System.out.println("Incorrection Evaluation. Given incorrect mark. Try again");					
				} catch (Exception e) {
					throw e;
				}
			}
		}
	}

	private void run2(String op) throws Exception {
		sqlTablesProject tdp = sqlTablesProject.getInstance();
		if (view.LISTSTUDENTS.equals(op)) {
			viewTable.showList(tdp.getStudents(), "List of Students: ");
		} else if (view.LISTTEACHERS.equals(op)) {
			viewTable.showList(tdp.getTeachers(), "List of Teachers: ");
		} else if (view.LISTDEGREES.equals(op)) {
			viewTable.showList(tdp.getDegrees(), "List of Grades: ");
		} else if (view.LISTSUBJECTS.equals(op)) {
			viewTable.showList(tdp.getSubjects(), "List of classes: ");
		} else if (view.LISTISSUEDGRADES.equals(op)) {
			viewTable.showListIssuedGrades("List of given grades:");
		} else if (view.ISSUINGGRADES.equals(op)) {
			createGrades();
		} else if (view.SHOWMENU.equals(op)) {
			view.showMainMenu();
		}
		;
	}

	private void createGrades() throws Exception {
		System.out.println("Enter proper IDs for student evaluation: ");

		int idu = Id("ucznia");
		int idn = Id("nauczyciela");
		int ido = Id("oceny");
		int idp = Id("przedmiotu");
		view.resetScanner();
		String degree = Degree();
		if ("S".equalsIgnoreCase(degree) || "C".equalsIgnoreCase(degree)) {
			try {
				System.out.println();				
				addGradeToDatabase(idu, idn, ido, idp, degree);
				System.out.println("Evaluation mark has been saved in the system");
			} catch (DBConstraintViolation e) {
				System.out.println(e.getMessage());
				throw e;
			}
		} else {
			System.out.println("Incorrect type of mark. Evaluation finished, try again ");
		}

	}

	

	private String Degree() {
		System.out.print(String.format("Please choose type of mark [C - partial grade, S - full term grade]: "));
		return view.readChara();
	}

	private int Id(String what) {
		System.out.print(String.format("Enter ID %s: ", what));
		int id = view.readId();
		return id;
	}

	private void table_foreign_key_constraint_check_or_throw_exception(int id, TableStructure table) throws Exception {
		SimpleQuery sq = new SimpleQuery(table.getTableName(), String.format(" %s = %d", table.getIdColumnName(), id));
		if (!sq.anyRowExists()) {
			throw new DBConstraintViolation(table.getTableName(), id);
		}
	}

}
