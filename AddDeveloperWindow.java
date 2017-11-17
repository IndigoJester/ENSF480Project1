import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddDeveloperWindow {
	private static Connection myConn1, myConn2;
    private JFrame main;
    private JTextField name, username, password, details;
    private JButton cancelSubmission, submitDev;
    private ActionListener buttonEventListener;

    private class EventListener implements ActionListener {
		AddDeveloperWindow display;
		public EventListener (AddDeveloperWindow d) {
			display = d;
		}
		public void actionPerformed (ActionEvent e) {
			if (e.getSource() == submitDev) {
                try {
					display.addNewDeveloper();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			} else if (e.getSource() == cancelSubmission) {
                display.cancelSubmission();
			}
		}
	}

    public AddDeveloperWindow() throws SQLException {
    	myConn1 = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/developer?autoReconnect=true&useSSL=false", "root", "Bigpapi");
    	myConn2 = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/credentials?autoReconnect=true&useSSL=false", "root", "Bigpapi");
        buttonEventListener = new EventListener(this);
        this.display();
    }

    public void display () {
        // Set up display defaults
        Font defaultFont = new Font("Candara", Font.BOLD, 20);
        Color defaultColor = new Color (54, 51, 51);

        // Create Frame
        main = new JFrame();
		main.setTitle("RAID Bug Tracking System: New Developer");
		main.setIconImage(new ImageIcon("Images/Logo.png").getImage());
		main.setSize(700, 500);
		main.setLocationRelativeTo(null);
		main.setResizable(false);
		main.setLayout(new BorderLayout());
        main.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);

        // Set background image
        BackgroundPanel background = new BackgroundPanel(new BorderLayout(), "Images/Extras.png");

        // Panel to control the display
        JPanel controlBox = new JPanel();
        controlBox.setLayout(new BorderLayout());
        controlBox.setOpaque(false);

        JPanel divider = new JPanel();
        divider.setLayout(new GridLayout(0, 2));
        divider.setOpaque(false);


        //JPanel setup
        JPanel content1 = new JPanel();
        content1.setLayout(new BoxLayout(content1, BoxLayout.Y_AXIS));
        content1.setOpaque(false);
        content1.setBorder(new EmptyBorder (0, 20, 0, 10));

        // Username stuff
        JLabel label = new JLabel("Name:");
        label.setBorder(new EmptyBorder (12, 0, 12, 0));
		label.setForeground(defaultColor);
		label.setFont(defaultFont);
		content1.add(label);
        name = new CustomJTextField(20);
        content1.add(name);

        // Password stuff
        label = new JLabel("Username:");
        label.setBorder(new EmptyBorder (12, 0, 12, 0));
		label.setForeground(defaultColor);
		label.setFont(defaultFont);
		content1.add(label);
        username = new CustomJTextField(20);
        content1.add(username);

        //JPanel setup
        JPanel content2 = new JPanel();
        content2.setLayout(new BoxLayout(content2, BoxLayout.Y_AXIS));
        content2.setOpaque(false);
        content2.setBorder(new EmptyBorder (0, 20, 0, 10));

        // Password stuff
        label = new JLabel("Password:");
        label.setBorder(new EmptyBorder (12, 0, 12, 0));
		label.setForeground(defaultColor);
		label.setFont(defaultFont);
		content2.add(label);
        password = new CustomJTextField(20);
        content2.add(password);

        // Password stuff
        label = new JLabel("Details:");
        label.setBorder(new EmptyBorder (12, 0, 12, 0));
		label.setForeground(defaultColor);
		label.setFont(defaultFont);
		content2.add(label);
        details = new CustomJTextField(20);
        content2.add(details);

        // Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout (FlowLayout.CENTER, 46, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(new EmptyBorder (20, 0, 20, 0));
        submitDev = new CustomJButton(18, 38, "Submit");
        submitDev.addActionListener(buttonEventListener);
        buttonPanel.add(submitDev);
        cancelSubmission = new CustomJButton(18, 38, "Cancel");
        cancelSubmission.addActionListener(buttonEventListener);
        buttonPanel.add(cancelSubmission);

        JLabel title = new JLabel("Add A New Developer");
        title.setBorder(new EmptyBorder (30, 50, 50, 50));
		title.setForeground(new Color (0, 0, 0));
		title.setFont(new Font ("Eras Bold ITC", Font.PLAIN, 40));

        // Add elements and display
        divider.add(content1);
        divider.add(content2);

        background.add("North", title);
        controlBox.add("Center", divider);
        controlBox.add("South", buttonPanel);
        background.add("South", controlBox);
        main.add(background);
        main.setVisible(true);
    }

    private void addNewDeveloper() throws SQLException {
        // Add a new developer.
    	String nameNew = name.getText();
    	String uName = username.getText();
    	String pass = password.getText();
    	String detail = details.getText();
    	Blob detailBlob = myConn1.createBlob();
    	detailBlob.setBytes(1, detail.getBytes());
    	
    	PreparedStatement stmt = myConn1.prepareStatement("INSERT INTO developers (name, username, password, details)" + 
    	"VALUES(?, ?, ?, ?)");
		stmt.setString(1, nameNew); 
		stmt.setString(2,uName ); //theBug.getName());
		stmt.setString(3, pass);
		stmt.setBlob(4, detailBlob);
		stmt.executeUpdate();
		
		PreparedStatement stmt2 = myConn2.prepareStatement("INSERT INTO credentials (username, password, userType)" + 
		    	"VALUES(?, ?, ?)");
				stmt2.setString(1,uName ); //theBug.getName());
				stmt2.setString(2, pass);
				stmt2.setString(3, "Developer");
				stmt2.executeUpdate();
		
		
		main.dispatchEvent(new WindowEvent(main, WindowEvent.WINDOW_CLOSING));
    }
    private void cancelSubmission() {
        // Cancel the Submission.
    	main.dispatchEvent(new WindowEvent(main, WindowEvent.WINDOW_CLOSING));
    }
    /*
    public static void main (String[] args) throws SQLException {
        AddDeveloperWindow temp = new AddDeveloperWindow();
    }
    */
}
