// LoginWindow.java
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.io.*;
import java.sql.SQLException;


public class LoginWindow extends Authenticator{
    // private Authenticator anAuthenticator;
    private JButton loginButton;
    private JButton defaultButton;
    private JTextField username;
    private JTextField password;
    private ActionListener buttonEventListener;

    public LoginWindow () {
        // anAuthenticator = new Authenticator();
        this.display();
    }

    private void display () {
        // Create Frame
        JFrame main = new JFrame();
		main.setTitle("RAID Bug Tracking System");
		// main.setIconImage(new ImageIcon("Icons/Travel.png").getImage());
		main.setSize(700, 500);
		main.setLocationRelativeTo(null);
		main.setResizable(false);
		main.setLayout(new BorderLayout());
        main.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Set background image
        BackgroundPanel background = new BackgroundPanel(new BorderLayout(), "Images/Login.png");

        //JPanel content


        //background.add()
        
        

        main.add(background);
        main.setVisible(true);
    }

    public static void main (String[] args) throws SQLException {
        LoginWindow temp = new LoginWindow();
        String name = "DRoman";
        String password = "password";
        authenticate(name, password);
    }
}
