import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.io.*;
import java.sql.SQLException;
import java.util.*;

public class ProjectManagerWindow {
	private JButton removeFixedBug;
	private JButton rejectBug;
	private JButton approveBug;
	private JButton assignBug;
	private JButton addDeveloper;
	private JButton removeDeveloper;
	private JButton updateDeveloper;
	private JButton addProduct;
	private JButton removeProduct;
	private JButton updateProduct;
	private Vector<Developer> developers;
	private JList developerList;
	private String managerName;
    protected JList<String> productList, bugList;
    protected Vector<Bug> bugs;
    protected Vector<Product> products;
    protected JButton submitBug, refresh;
    protected MouseAdapter mouseEventListener;
    protected ActionListener buttonEventListener;
    protected Product activeProduct;

    private class EventListener implements ActionListener {
        ProjectManagerWindow display;
        public EventListener (ProjectManagerWindow d) {
            display = d;
        }
        public void actionPerformed (ActionEvent e) {
            if (e.getSource() == submitBug) {
                display.submitNewBug();
            } else if (e.getSource() == refresh) {
                display.refresh();
            } else if (e.getSource() == removeFixedBug) {
                display.removeBug();
            } else if (e.getSource() == rejectBug) {
                display.rejectBugReport();
            } else if (e.getSource() == approveBug) {
                display.approveBugReport();
            } else if (e.getSource() == assignBug) {
                display.assignBugToDev();
            } else if (e.getSource() == addDeveloper) {
                display.addDev();
            } else if (e.getSource() == removeDeveloper) {
                display.removeDev();
            } else if (e.getSource() == updateDeveloper) {
                display.updateDev();
            } else if (e.getSource() == addProduct) {
                display.addNewProduct();
            } else if (e.getSource() == removeProduct) {
                display.removeAProduct();
            } else if (e.getSource() == updateProduct) {
                display.updateAProduct();
            }
        }
    }
	public ProjectManagerWindow (String username) {
        buttonEventListener = new EventListener(this);
        managerName = username;
        this.display();
	}
	public void display () {
        // Set up display defaults
        Font defaultFont = new Font("Candara", Font.BOLD, 20);
        Color defaultColor = new Color (54, 51, 51);

        // Create Frame
        JFrame main = new JFrame();
        main.setTitle("RAID Bug Tracking System: Developer");
        main.setIconImage(new ImageIcon("Images/Logo.png").getImage());
        main.setSize(800, 1000);
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
        JLabel username = new JLabel("  " + managerName);
        username.setBorder(new EmptyBorder (9, 0, 80, 0));
        username.setForeground(new Color(0, 0, 0));
        username.setFont(new Font("Britannic Bold", Font.BOLD, 26));
        userPanel.add(username);

        JPanel contentDiv = new JPanel();
        contentDiv.setLayout(new GridLayout(0, 1));
        contentDiv.setOpaque(false);

        // Developer stuff
        JPanel devDiv = new JPanel();
        devDiv.setLayout(new BoxLayout(devDiv, BoxLayout.Y_AXIS));
        devDiv.setOpaque(false);
        devDiv.setBorder(new EmptyBorder (0, 25, 0, 25));
        JLabel label = new JLabel("Developers:                                                                              ");
        label.setBorder(new EmptyBorder (5, 0, 5, 0));
        label.setForeground(defaultColor);
        label.setFont(defaultFont);
        devDiv.add(label);
        developerList = new JList();
        developerList.setFont(new Font("Courier", Font.BOLD, 14));
        JScrollPane tempScroll = new JScrollPane(developerList);
        developerList.setBackground(new Color (162, 154, 154));
        devDiv.add(tempScroll);
        contentDiv.add(devDiv);

        // Product Stuff
        JPanel productDiv = new JPanel();
        productDiv.setLayout(new BoxLayout(productDiv, BoxLayout.Y_AXIS));
        productDiv.setOpaque(false);
        productDiv.setBorder(new EmptyBorder (0, 25, 0, 25));
        label = new JLabel("Products:                                                                         ");
        label.setBorder(new EmptyBorder (5, 0, 5, 0));
        label.setForeground(defaultColor);
        label.setFont(defaultFont);
        productDiv.add(label);
        productList = new JList();
        productList.setFont(new Font("Courier", Font.BOLD, 14));
        tempScroll = new JScrollPane(productList);
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
        buttonPanel.setLayout(new FlowLayout (FlowLayout.CENTER, 30, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(new EmptyBorder (20, 0, 10, 0));
        submitBug = new CustomJButton(19, 38, "Submit New Bug");
        submitBug.addActionListener(buttonEventListener);
        buttonPanel.add(submitBug);
        assignBug = new CustomJButton(27, 38, "Assign Bug To A Developer");
        assignBug.addActionListener(buttonEventListener);
        buttonPanel.add(assignBug);
        removeFixedBug = new CustomJButton(21, 38, "Remove A Fixed Bug");
        removeFixedBug.addActionListener(buttonEventListener);
        buttonPanel.add(removeFixedBug);
        manyButtons.add(buttonPanel);

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout (FlowLayout.CENTER, 30, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(new EmptyBorder (10, 0, 20, 0));
        rejectBug = new CustomJButton(23, 38, "Reject A Reported Bug");
        rejectBug.addActionListener(buttonEventListener);
        buttonPanel.add(rejectBug);
        approveBug = new CustomJButton(24, 38, "Accept A Reported Bug");
        approveBug.addActionListener(buttonEventListener);
        buttonPanel.add(approveBug);
        refresh = new CustomJButton(19, 38, "Refresh Results");
        refresh.addActionListener(buttonEventListener);
        buttonPanel.add(refresh);

        manyButtons.add(buttonPanel);

        controlBox.add("South", manyButtons);
        controlBox.add("Center", contentDiv);
        background.add("North", userPanel);
        background.add("Center", controlBox);
        main.add(background);
        main.setVisible(true);
	}

    protected void submitNewBug() {
        // Display the Bugs from the product argument
        System.out.println("working1");
    }

    protected void refresh() {
        // Refresh.
        System.out.println("working2");
    }

    private void removeBug () {

    }

    private void rejectBugReport () {

    }

    private void approveBugReport () {

    }

    private void assignBugToDev () {

    }

    private void addDev () {

    }

    private void removeDev () {

    }

    private void updateDev () {

    }

    private void addNewProduct () {

    }

    private void removeAProduct () {

    }

    private void updateAProduct () {

    }

    public static void main (String[] args) throws SQLException {
        ProjectManagerWindow temp = new ProjectManagerWindow("ManagerName");
    }
}
