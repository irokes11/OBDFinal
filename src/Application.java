import java.sql.SQLException;
import java.sql.SQLRecoverableException;
import odb.model.DataBaseCon;
import odb.view.Control;


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
				Control.loadData();
				Control.run1();
				
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

	}


