package odb.view;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class view {

	private static final Scanner scn = new Scanner(System.in);

	
	public final static String SHOWMENU = "M";
	public final static String LISTSTUDENTS = "A";
	public final static String LISTTEACHERS = "B";
	public final static String LISTDEGREES = "C";
	public final static String LISTSUBJECTS = "D";
	public final static String LISTISSUEDGRADES = "E";
	public final static String ISSUINGGRADES = "F";
	public final static String EXIT = "G";
	
	private final static List<String> AVAILABLES = Arrays.asList(SHOWMENU, LISTSUBJECTS,
			LISTISSUEDGRADES,LISTSTUDENTS,LISTTEACHERS, LISTDEGREES, ISSUINGGRADES, EXIT);

	public static void close() {
		scn.close();
	}

	public static void showMainMenu() {
		System.out.println();
		System.out.println("<Choose from below list, input proper letter:>");
		System.out.println("_____________________________________________");
		System.out.println("M - This MENU");
		System.out.println("A - List of Students");
		System.out.println("B - List of Teachers");
		System.out.println("C - List of available evaluation grades");
		System.out.println("D - List of class subjects");
		System.out.println("E - Lista of given grades (Evaluation marks)");
		System.out.println("F - Evaluate");
		System.out.println("G - Exit");
		System.out.println("_____________________________________________");
	}

	public static String selectOperation() {
		String op;
		System.out.println();
		System.out.println("Insert your command: ");
		do {			
			String operation = scn.nextLine();
			scn.reset();
			op = operation.toUpperCase();
			if (!AVAILABLES.contains(op)) {
				System.out.println("Command not recognised \""+op+"\"!");
				System.out.println();
				System.out.println("Insert your command: ");
			} else {
				System.out.println("Executing command: " + op);
			}
		} while (!AVAILABLES.contains(op));
		return op;
	}
	
	public static void resetScanner() {
		if (scn.hasNextLine()) { scn.nextLine(); }
	}

	public static int readId() {
		int id = 0;		
		id = scn.nextInt();		
		scn.reset();
		return id;
	}

	public static String readChara() {
		String c;		
		c = scn.nextLine();
		scn.reset();
		return c.substring(0, 1).toUpperCase();
	}

}
