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
        Color defaultColor = new Color (54, 51, 51);

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

        //JPanel setup
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setOpaque(false);
        content.setBorder(new EmptyBorder (0, 50, 0, 50));

        // Username stuff
        JLabel label = new JLabel("Username:");
        label.setBorder(new EmptyBorder (12, 0, 12, 0));
		label.setForeground(defaultColor);
		label.setFont(defaultFont);
		content.add(label);
        username = new CustomJTextField(60);
        content.add(username);

        // Password stuff
        label = new JLabel("Password:");
        label.setBorder(new EmptyBorder (12, 0, 12, 0));
		label.setForeground(defaultColor);
		label.setFont(defaultFont);
		content.add(label);
        password = new CustomJPasswordField(60);
        content.add(password);

        // Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout (FlowLayout.CENTER, 46, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(new EmptyBorder (20, 0, 20, 0));
        loginButton = new JButton("Login");
        buttonPanel.add(loginButton);
        defaultButton = new JButton("Default User");
        buttonPanel.add(defaultButton);

        content.add(buttonPanel);
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
