import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// Authenticator Class - Used to authenticate user credentials
public class Authenticator {
    //Connection to the SQL database
	private static Connection myConn;

    // Default Constructor, sets up connection to Database
	public Authenticator() throws SQLException {

		myConn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/credentials?autoReconnect=true&useSSL=false", "root", "Bigpapi");

	}

    // Generates the Developer Window
	public static void generateDevWindow(String username) throws SQLException{
		DeveloperWindow devWindow = new DeveloperWindow(username);

	}

    //Generates the Managed Window
	public static void generateManagerWindow(String username) throws SQLException {

		 ProjectManagerWindow managerWindow = new ProjectManagerWindow(username);
	}

    // Boolean Function that returns true if credentials are found to be correct, otherwise returns false
	public static boolean authenticate(String username , String password) throws SQLException {

		PreparedStatement stmt = myConn.prepareStatement("SELECT * FROM credentials WHERE username = ?");
		stmt.setString(1, username);
		ResultSet rs = stmt.executeQuery();
		if(rs.next() && password.equals(rs.getString("password"))) {
				String userType = rs.getString("userType");
				if(userType.equals("Developer")) {
					generateDevWindow(username);
					System.out.println("Dev window generated");
				}else if (userType.equals("ProjectManager")) {
					generateManagerWindow(username);
					System.out.println("Manageer window generated");
				}

				System.out.println("Login is successful");
				System.out.println("Logged in as: " + username);
				myConn.close();
				return true;
			}else {
			System.out.println("Login was not successful");
			System.out.println("Incorrect username/password.");
			myConn.close();
			return false;
		}
	}
}
