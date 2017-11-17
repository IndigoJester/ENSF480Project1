import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateDeveloperWindow {
	private static Connection myConn;
	private JFrame main;
    private Developer theDeveloper;
    private JTextField newName, newDetails;
    private JButton submitUpdate, cancelUpdate;
    private EventListener buttonEventListener;
    
    private class EventListener implements ActionListener {
    	UpdateDeveloperWindow display;
    	public EventListener (UpdateDeveloperWindow d) {
    		display = d;
    	}
    	public void actionPerformed (ActionEvent e) {
    		if (e.getSource() == submitUpdate) {
    			try {
    				display.submit();
    			} catch (SQLException e1) {
    				e1.printStackTrace();
    			}
    		} else if (e.getSource() == cancelUpdate) {
    			display.cancel();
    		}
    	}
    }

    public UpdateDeveloperWindow(Developer aDeveloper) throws SQLException {
    	myConn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/developer?autoReconnect=true&useSSL=false", "root", "Bigpapi");
    	buttonEventListener = new EventListener(this);
    	theDeveloper = aDeveloper;
        this.display();
        
    }
    
    public void display() {
    	// Set up display defaults
        Font defaultFont = new Font("Candara", Font.BOLD, 20);
        Color defaultColor = new Color (54, 51, 51);

        // Create Frame
        main = new JFrame();
		main.setTitle("RAID Bug Tracking System");
		main.setIconImage(new ImageIcon("Images/Logo.png").getImage());
		main.setSize(700, 500);
		main.setLocationRelativeTo(null);
		main.setResizable(false);
		main.setLayout(new BorderLayout());
        main.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);

        // Set background image
        BackgroundPanel background = new BackgroundPanel(new BorderLayout(), "Images/Login.png");

        // Panel to control the display
        JPanel controlBox = new JPanel();
        controlBox.setLayout(new BorderLayout());
        controlBox.setOpaque(false);

        //JPanel setup
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setOpaque(false);
        content.setBorder(new EmptyBorder (0, 50, 0, 50));

        // name stuff
        JLabel label = new JLabel("Name:");
        label.setBorder(new EmptyBorder (12, 0, 12, 0));
		label.setForeground(defaultColor);
		label.setFont(defaultFont);
		content.add(label);
        newName = new CustomJTextField(60);
        content.add(newName);

        // details stuff
        label = new JLabel("Details:");
        label.setBorder(new EmptyBorder (12, 0, 12, 0));
		label.setForeground(defaultColor);
		label.setFont(defaultFont);
		content.add(label);
        newDetails = new CustomJTextField(40);
        content.add(newDetails);

        // Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout (FlowLayout.CENTER, 46, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(new EmptyBorder (20, 0, 20, 0));
        submitUpdate = new CustomJButton(19, 38, "Submit");
        submitUpdate.addActionListener(buttonEventListener);
        buttonPanel.add(submitUpdate);
        cancelUpdate = new CustomJButton(19, 38, "Cancel");
        cancelUpdate.addActionListener(buttonEventListener);
        buttonPanel.add(cancelUpdate);

        // Add elements and display
        controlBox.add("Center", content);
        controlBox.add("South", buttonPanel);
        background.add("South", controlBox);
        main.add(background);
        main.setVisible(true);
    }

    private void cancel() {
    	
    	main.dispatchEvent(new WindowEvent(main, WindowEvent.WINDOW_CLOSING));
    }

    private void submit() throws SQLException {
    	String name = newName.getText();
    	String details = newDetails.getText();
    	Blob detailBlob = myConn.createBlob();
    	detailBlob.setBytes(1, details.getBytes());
    	
    	PreparedStatement stmt1 = myConn.prepareStatement("UPDATE developers SET name = ? WHERE username = ?");
		stmt1.setString(1, name);
		stmt1.setString(2, theDeveloper.getUsername());
		stmt1.executeUpdate();
		
		PreparedStatement stmt2 = myConn.prepareStatement("UPDATE developers SET details = ? WHERE name = ?");
		stmt2.setBlob(1, detailBlob);
		stmt2.setString(2, theDeveloper.getName());
		stmt2.executeUpdate();

    	main.dispatchEvent(new WindowEvent(main, WindowEvent.WINDOW_CLOSING));
    }
}
