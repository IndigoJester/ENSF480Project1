import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Authenticator {
	
	public static void authenticate(String username , String password) throws SQLException {

		Connection myConn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/credentials?autoReconnect=true&useSSL=false", "root", "Bigpapi");
		PreparedStatement stmt = myConn.prepareStatement("SELECT * FROM credentials WHERE username = ?");
		stmt.setString(1, username);
		ResultSet rs = stmt.executeQuery();
		if(rs.next() && password.equals(rs.getString("password"))) {
				System.out.println("Login is successful");
				System.out.println("Logged in as: " + username);
				myConn.close();
				//return true;
			}else {
			System.out.println("Login was not successful");
			System.out.println("Incorrect username/password.");
			myConn.close();
			//return false;
		}
	}
}
