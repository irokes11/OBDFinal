package odb.view;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.InputMismatchException;
import odb.model.Data;
import odb.model.Query;
import odb.model.SQLViolation;
import odb.model.TableStructure;
import odb.model.SQLTablesProject;
import odb.model.Data.InsertEngine;

public class Control {
	private static void addGradeToDB(int idu, int idn, int ido, int idp, String degree) throws Exception {
		SQLTablesProject stp = SQLTablesProject.Instance();

		violation(idu, stp.getStudents());
		violation(idn, stp.getTeachers());
		violation(ido, stp.getDegrees());
		violation(idp, stp.getSubjects());
		InsertEngine ie = new Data.InsertEngine(stp.getIssuingGrades(), false);
		System.out.println("Adding grades to the Database");
		ie.values(idp, ido, idn, idu, degree);

	}
	
	public static void loadData() throws ClassNotFoundException, SQLException {
		System.out.println("Creating an examples. Please wait..");
		SQLTablesProject stp = SQLTablesProject.Instance();
		stp.createData(true, true);
	}

	
	public static void run1() throws Exception {
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

	private static void run2(String op) throws Exception {
		SQLTablesProject stp = SQLTablesProject.Instance();
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

	public static void createGrades() throws Exception {
		System.out.println("Enter proper IDs for student evaluation: ");

		int idu = Id(" of Student");
		int idn = Id(" of Teacher");
		int ido = Id(" of Grade");
		int idp = Id("of Class");
		view.resetScanner();
		String degree = Degree();
		if ("S".equalsIgnoreCase(degree) || "C".equalsIgnoreCase(degree)) {
			try {
							
				addGradeToDB(idu, idn, ido, idp, degree);
				System.out.println("Evaluation mark has been saved in the system");
			} catch (SQLViolation e) {
			//	System.out.println(e.getMessage()); //this created unnecessary null when violoation came up
				//throw e;
			}
		} else {
			System.out.println("Incorrect type of mark. Evaluation finished, try again ");
		}

	}

	private static int Id(String what) {
		System.out.print(String.format("Enter ID %s: ", what));
		int id = view.readId();
		return id;
	}

	private static String Degree() {
		System.out.print(String.format("Please choose type of mark [C - partial grade, S - full term grade]: "));
		return view.readChara();
	}

	

	private static void violation(int id, TableStructure table) throws Exception {
		Query sq = new Query(table.getTableName(), String.format(" %s = %d", table.getIdColumnName(), id));
		if (!sq.anyRowExists()) {
			throw new SQLViolation(table.getTableName(), id);
		}
}

	public static void insertSQL() {
	
			try {
				
				String url = "jdbc:oracle:thin:@ora3.elka.pw.edu.pl:1521:ora3inf";
				String user = "iseredyn";
				String password = "iseredyn";
				
				
				String sql1 = "INSERT INTO OCENA (IDO,WARTOSC_OPISOWA,WARTOSC_NUMERYCZNA) VALUES('2','jeden +',1.5)";  //wstawianie danych  inna skladnia niz w v4
				String sql2 = "INSERT INTO OCENA (IDO,WARTOSC_OPISOWA,WARTOSC_NUMERYCZNA) VALUES('4','dwa +',2.5)";
				String sql3 = "INSERT INTO OCENA (IDO,WARTOSC_OPISOWA,WARTOSC_NUMERYCZNA) VALUES('6','trzy +',3.5)";
				String sql4 = "INSERT INTO OCENA (IDO,WARTOSC_OPISOWA,WARTOSC_NUMERYCZNA) VALUES('8','cztery +',4.5)";
				String sql5 = "INSERT INTO OCENA (IDO,WARTOSC_OPISOWA,WARTOSC_NUMERYCZNA) VALUES('10','pec +',5.5)";
				
				Connection connection = DriverManager.getConnection(url,user,password);
				//System.out.println("AutoCommit: "+connection.getAutoCommit()+ " "+connection.hashCode());
				Statement polecenie = connection.createStatement();	
				System.out.println(polecenie.executeUpdate(sql1));
				System.out.println(polecenie.executeUpdate(sql2));
				System.out.println(polecenie.executeUpdate(sql3));
				System.out.println(polecenie.executeUpdate(sql4));
				System.out.println(polecenie.executeUpdate(sql5));
				
		
				
				
			} catch (Exception e) {
				System.out.println("Nieudane dodanie ocen polowicznych");	
				e.printStackTrace();
				return;
			}
			System.out.println("Sukces dodano oceny polowiczne" );
			
			
			}
		}
		
	
