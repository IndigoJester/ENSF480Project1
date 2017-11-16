// LoginWindow.java
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.io.*;
import java.sql.SQLException;


public class LoginWindow {
    // private static Authenticator anAuthenticator;
    private JButton loginButton;
    private JButton defaultButton;
    private JTextField username;
    private JTextField password;
    private ActionListener buttonEventListener;

    public LoginWindow () throws SQLException {

    	// anAuthenticator = new Authenticator();
        this.display();
    }

    private void display () {
        // Set up display defaults
        Font defaultFont = new Font("Candara", Font.BOLD, 20);
        Color defaultColor = new Color (36, 33, 33);

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
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setOpaque(false);
        content.setBorder(new EmptyBorder (0, 50, 0, 0));
        JLabel label = new JLabel("Username:");
		label.setForeground(defaultColor);
		label.setFont(defaultFont);
		content.add(label);

        background.add("South", content);



        main.add(background);
        main.setVisible(true);
    }

    public static void main (String[] args) throws SQLException {
        LoginWindow temp = new LoginWindow();
        // String name = "MNewell";
        // String password = "password";
        // boolean login = anAuthenticator.authenticate(name, password);
    }
}
