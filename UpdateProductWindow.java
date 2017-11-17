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

public class UpdateProductWindow {
	private static Connection myConn;
	private JFrame main;
    private Product theProduct;
    private JTextField newDetails;
    private JButton submitUpdate, cancelUpdate;
    private EventListener buttonEventListener;
    
    private class EventListener implements ActionListener {
    	UpdateProductWindow display;
    	public EventListener (UpdateProductWindow d) {
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

    public UpdateProductWindow(Product aProduct) throws SQLException {
    	myConn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/product?autoReconnect=true&useSSL=false", "root", "Bigpapi");
    	buttonEventListener = new EventListener(this);
    	theProduct = aProduct;
        this.display();
        
    }
    
    public void display() {
        // Display.
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
        BackgroundPanel background = new BackgroundPanel(new BorderLayout(), "Images/Extras.png");

        // Panel to control the display
        JPanel controlBox = new JPanel();
        controlBox.setLayout(new BorderLayout());
        controlBox.setOpaque(false);

        //JPanel setup
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setOpaque(false);
        content.setBorder(new EmptyBorder (80, 50, 80, 50));

        // Status Update stuff
        JLabel label = new JLabel("New Detials:");
        label.setBorder(new EmptyBorder (12, 0, 12, 0));
		label.setForeground(defaultColor);
		label.setFont(defaultFont);
		content.add(label);
         newDetails= new CustomJTextField(60);
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

        JLabel title = new JLabel("Update: " + theProduct.getName());
        title.setBorder(new EmptyBorder (30, 50, 50, 50));
		title.setForeground(new Color (0, 0, 0));
		title.setFont(new Font ("Eras Bold ITC", Font.PLAIN, 40));

        // Add elements and display
        background.add("North", title);
        controlBox.add("North", content);
        controlBox.add("South", buttonPanel);
        background.add("South", controlBox);
        main.add(background);
        main.setVisible(true);
    }

    private void cancel() {
    	
    	main.dispatchEvent(new WindowEvent(main, WindowEvent.WINDOW_CLOSING));
    }

    private void submit() throws SQLException {
    	String details = newDetails.getText();
    	Blob detailBlob = myConn.createBlob();
    	detailBlob.setBytes(1, details.getBytes());
    	
		PreparedStatement stmt2 = myConn.prepareStatement("UPDATE products SET details = ? WHERE name = ?");
		stmt2.setBlob(1, detailBlob);
		stmt2.setString(2, theProduct.getName());
		stmt2.executeUpdate();

    	main.dispatchEvent(new WindowEvent(main, WindowEvent.WINDOW_CLOSING));
    }
}
