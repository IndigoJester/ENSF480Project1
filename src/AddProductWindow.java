import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddProductWindow {
	private static Connection myConn;
    private JFrame main;
    private JTextField name, details;
    private JButton submit, cancel;
    private ActionListener buttonEventListener;

    private class EventListener implements ActionListener {
		AddProductWindow display;
		public EventListener (AddProductWindow d) {
			display = d;
		}
		public void actionPerformed (ActionEvent e) {
			if (e.getSource() == submit) {
                try {
					display.submit();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			} else if (e.getSource() == cancel) {
                display.cancel();
			}
		}
	}

    public AddProductWindow () throws SQLException {
    	myConn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/product?autoReconnect=true&useSSL=false", "root", "Bigpapi");
        buttonEventListener = new EventListener(this);
        this.display();
    }

    public void display () {
        // Set up display defaults
        Font defaultFont = new Font("Candara", Font.BOLD, 20);
        Color defaultColor = new Color (54, 51, 51);

        // Create Frame
        main = new JFrame();
		main.setTitle("RAID Bug Tracking System: New Bug");
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

        //JPanel setup
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setOpaque(false);
        content.setBorder(new EmptyBorder (0, 50, 0, 50));

        // Username stuff
        JLabel label = new JLabel("Product Name:");
        label.setBorder(new EmptyBorder (12, 0, 12, 0));
		label.setForeground(defaultColor);
		label.setFont(defaultFont);
		content.add(label);
        name = new CustomJTextField(60);
        content.add(name);

        // Password stuff
        label = new JLabel("Product Information:");
        label.setBorder(new EmptyBorder (12, 0, 12, 0));
		label.setForeground(defaultColor);
		label.setFont(defaultFont);
		content.add(label);
        details = new CustomJTextField(40);
        content.add(details);

        // Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout (FlowLayout.CENTER, 46, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(new EmptyBorder (20, 0, 20, 0));
        submit = new CustomJButton(18, 38, "Submit");
        submit.addActionListener(buttonEventListener);
        buttonPanel.add(submit);
        cancel = new CustomJButton(18, 38, "Cancel");
        cancel.addActionListener(buttonEventListener);
        buttonPanel.add(cancel);

        JLabel title = new JLabel("Add A New Product");
        title.setBorder(new EmptyBorder (30, 50, 50, 50));
		title.setForeground(new Color (0, 0, 0));
		title.setFont(new Font ("Eras Bold ITC", Font.PLAIN, 40));

        // Add elements and display
        background.add("North", title);
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
    	String productName = name.getText();
    	String productInfo = details.getText();
    	Blob detailBlob = myConn.createBlob();
    	detailBlob.setBytes(1, productInfo.getBytes());
    	
    	long time = System.currentTimeMillis();
    	java.sql.Date date = new java.sql.Date(time);
    	
    	PreparedStatement stmt = myConn.prepareStatement("INSERT INTO products (name, created, numberOfBugs, details)" + 
    	"VALUES(?, ?, ?, ?)");
		stmt.setString(1, productName); 
		stmt.setDate(2,date ); //theBug.getName());
		stmt.setInt(3, 0);
		stmt.setBlob(4, detailBlob);
		stmt.executeUpdate();
		
		main.dispatchEvent(new WindowEvent(main, WindowEvent.WINDOW_CLOSING));
    }
    /*
    public static void main (String[] args) throws SQLException {
        AddProductWindow temp = new AddProductWindow();
    }
    */
}
