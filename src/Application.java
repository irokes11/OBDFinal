import java.sql.SQLException;
import java.sql.SQLRecoverableException;
import java.util.InputMismatchException;
import odb.view.viewTable;
import odb.model.SQLViolation;
import odb.model.Data;
import odb.model.DataBaseCon;
import odb.model.Query;
import odb.model.TableStructure;
import odb.model.sqlTablesProject;
import odb.model.Data.InsertEngine;
import odb.view.view;

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

	private void addGradeToDB(int idu, int idn, int ido, int idp, String degree) throws Exception {
		sqlTablesProject stp = sqlTablesProject.Instance();

		violation(idu, stp.getStudents());
		violation(idn, stp.getTeachers());
		violation(ido, stp.getDegrees());
		violation(idp, stp.getSubjects());
		InsertEngine ie = new Data.InsertEngine(stp.getIssuingGrades(), false);
System.out.println("Adding grades to the Database");
		ie.values(idp, ido, idn, idu, degree);

	}
	
	public void createData() throws ClassNotFoundException, SQLException {
		System.out.println("Creating an examples. Please wait..");
		sqlTablesProject stp = sqlTablesProject.Instance();
		stp.createData(true, true);
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
				} catch (SQLViolation e) {
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
		sqlTablesProject stp = sqlTablesProject.Instance();
		if (view.LISTSTUDENTS.equals(op)) {
			viewTable.showList(stp.getStudents(), "Students: ");
		} else if (view.LISTTEACHERS.equals(op)) {
			viewTable.showList(stp.getTeachers(), "Teachers: ");
			
		} else if (view.LISTDEGREES.equals(op)) {
			viewTable.showList(stp.getDegrees(), "Grades: ");
		} else if (view.LISTSUBJECTS.equals(op)) {   
			viewTable.showList(stp.getSubjects(), "lasses: ");
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

		int idu = Id(" of Student");
		int idn = Id(" of Teacher");
		int ido = Id(" of Grade");
		int idp = Id("of Class");
		view.resetScanner();
		String degree = Degree();
		if ("S".equalsIgnoreCase(degree) || "C".equalsIgnoreCase(degree)) {
			try {
				System.out.println();				
				addGradeToDB(idu, idn, ido, idp, degree);
				System.out.println("Evaluation mark has been saved in the system");
			} catch (SQLViolation e) {
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

	private void violation(int id, TableStructure table) throws Exception {
		Query sq = new Query(table.getTableName(), String.format(" %s = %d", table.getIdColumnName(), id));
		if (!sq.anyRowExists()) {
			throw new SQLViolation(table.getTableName(), id);
		}
	}

}
