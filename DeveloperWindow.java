import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.io.*;
import java.sql.SQLException;
import java.util.*;

public class DeveloperWindow {
	private JButton updateBug;
	private JButton browseAssignment;
	private String developerName;
    protected JList<String> productList, bugList;
    protected Vector<Bug> bugs;
    protected Vector<Product> products;
    protected JButton submitBug, refresh;
    protected MouseAdapter mouseEventListener;
    protected ActionListener buttonEventListener;
    protected Product activeProduct;

    private class EventListener implements ActionListener {
        DeveloperWindow display;
        public EventListener (DeveloperWindow d) {
            display = d;
        }
        public void actionPerformed (ActionEvent e) {
            if (e.getSource() == submitBug) {
                display.submitNewBug();
            } else if (e.getSource() == refresh) {
                display.refresh();
            } else if (e.getSource() == updateBug) {
                display.updateBugStatus();
            } else if (e.getSource() == browseAssignment) {
                display.displayAssignments();
            }
        }
    }

    public DeveloperWindow(String username) {
        buttonEventListener = new EventListener(this);
        developerName = username;
        this.display();
    }

    public void display() {
        // Set up display defaults
        Font defaultFont = new Font("Candara", Font.BOLD, 20);
        Color defaultColor = new Color (54, 51, 51);

        // Create Frame
        JFrame main = new JFrame();
        main.setTitle("RAID Bug Tracking System: Developer");
        main.setIconImage(new ImageIcon("Images/Logo.png").getImage());
        main.setSize(800, 900);
        main.setLocationRelativeTo(null);
        main.setResizable(false);
        main.setLayout(new BorderLayout());
        main.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Set background image
        BackgroundPanel background = new BackgroundPanel(new BorderLayout(), "Images/Default.png");

        // Panel to control the display
        JPanel controlBox = new JPanel();
        controlBox.setLayout(new BorderLayout());
        controlBox.setOpaque(false);

        JPanel userPanel = new JPanel();
        userPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        userPanel.setOpaque(false);
        JLabel username = new JLabel("  " + developerName);
        username.setBorder(new EmptyBorder (9, 0, 80, 0));
        username.setForeground(new Color(0, 0, 0));
        username.setFont(new Font("Britannic Bold", Font.BOLD, 26));
        userPanel.add(username);

        JPanel contentDiv = new JPanel();
        contentDiv.setLayout(new GridLayout(0, 1));
        contentDiv.setOpaque(false);

        // Product Stuff
        JPanel productDiv = new JPanel();
        productDiv.setLayout(new BoxLayout(productDiv, BoxLayout.Y_AXIS));
        productDiv.setOpaque(false);
        productDiv.setBorder(new EmptyBorder (0, 25, 0, 25));
        JLabel label = new JLabel("Products:                                                                         ");
        label.setBorder(new EmptyBorder (5, 0, 5, 0));
        label.setForeground(defaultColor);
        label.setFont(defaultFont);
        productDiv.add(label);
        productList = new JList();
        productList.setFont(new Font("Courier", Font.BOLD, 14));
        JScrollPane tempScroll = new JScrollPane(productList);
        productList.setBackground(new Color (162, 154, 154));
        productDiv.add(tempScroll);
        contentDiv.add(productDiv);

        // Bug stuff
        JPanel bugDiv = new JPanel();
        bugDiv.setLayout(new BoxLayout(bugDiv, BoxLayout.Y_AXIS));
        bugDiv.setOpaque(false);
        bugDiv.setBorder(new EmptyBorder (0, 25, 0, 25));
        label = new JLabel("Bugs:                                                                                    ");
        label.setBorder(new EmptyBorder (5, 0, 5, 0));
        label.setForeground(defaultColor);
        label.setFont(defaultFont);
        bugDiv.add(label);
        bugList = new JList();
        bugList.setFont(new Font("Courier", Font.BOLD, 14));
        tempScroll = new JScrollPane(bugList);
        bugList.setBackground(new Color (162, 154, 154));
        bugDiv.add(tempScroll);
        contentDiv.add(bugDiv);

        // Buttons
        JPanel manyButtons = new JPanel();
        manyButtons.setLayout(new GridLayout(0, 1));
        manyButtons.setOpaque(false);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout (FlowLayout.CENTER, 65, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(new EmptyBorder (20, 0, 10, 0));
        submitBug = new CustomJButton(19, 38, "Submit New Bug");
        submitBug.addActionListener(buttonEventListener);
        buttonPanel.add(submitBug);
        refresh = new CustomJButton(19, 38, "Refresh Results");
        refresh.addActionListener(buttonEventListener);
        buttonPanel.add(refresh);
        updateBug = new CustomJButton(19, 38, "Update This Bug");
        updateBug.addActionListener(buttonEventListener);
        buttonPanel.add(updateBug);
        manyButtons.add(buttonPanel);

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout (FlowLayout.CENTER, 65, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(new EmptyBorder (10, 0, 20, 0));
        browseAssignment = new CustomJButton(26, 38, "Check My Assignments");
        browseAssignment.addActionListener(buttonEventListener);
        buttonPanel.add(browseAssignment);
        manyButtons.add(buttonPanel);

        controlBox.add("South", manyButtons);
        controlBox.add("Center", contentDiv);
        background.add("North", userPanel);
        background.add("Center", controlBox);
        main.add(background);
        main.setVisible(true);
    }

	public void updateBugStatus() {
        System.out.println("working3");
	}

	public void displayAssignments() {
        System.out.println("working4");
	}

    protected void submitNewBug() {
        // Display the Bugs from the product argument
        System.out.println("working1");
    }

    protected void refresh() {
        // Refresh.
        System.out.println("working2");
    }

    protected void getBugs() {
        // Get Bugs from the Database
    }

    protected void getProjects() {
        // Get Products from the Database
    }

    protected void viewBug(Bug theBug) {
        // View the Bug.
    }

    protected void viewProduct(Product theProduct) {
        // View the Product
    }

    protected void showBugsFrom(Product activeProduct) {
        // Display thec bugs from the product argument
    }

    public static void main (String[] args) throws SQLException {
        DeveloperWindow temp = new DeveloperWindow("TestName");
    }

}
