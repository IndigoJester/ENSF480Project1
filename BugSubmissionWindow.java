import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.Date;

public class BugSubmissionWindow {
    private JFrame main;
    private JTextField bugDetails, title;
    private JButton cancelSubmission, submitBug;
    private Product theProduct;
    private ActionListener buttonEventListener;

    public BugSubmissionWindow(Product product) {
        theProduct = product;
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
        main.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

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
        JLabel label = new JLabel("Bug Title:");
        label.setBorder(new EmptyBorder (12, 0, 12, 0));
		label.setForeground(defaultColor);
		label.setFont(defaultFont);
		content.add(label);
        title = new CustomJTextField(60);
        content.add(title);

        // Password stuff
        label = new JLabel("Bug Information:");
        label.setBorder(new EmptyBorder (12, 0, 12, 0));
		label.setForeground(defaultColor);
		label.setFont(defaultFont);
		content.add(label);
        bugDetails = new CustomJTextField(40);
        content.add(bugDetails);

        // Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout (FlowLayout.CENTER, 46, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(new EmptyBorder (20, 0, 20, 0));
        cancelSubmission = new CustomJButton(18, 38, "Submit");
        //cancelSubmission.addActionListener(buttonEventListener);
        buttonPanel.add(cancelSubmission);
        submitBug = new CustomJButton(18, 38, "Cancel");
        //submitBug.addActionListener(buttonEventListener);
        buttonPanel.add(submitBug);

        JLabel title = new JLabel("Add A New Bug");
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
        //
    }

    private void submit() {
        //
    }

    public static void main (String[] args) throws SQLException {
        Product tempProd = new Product("Temp", new Date(2017, 12, 1), 10, "String details");
        BugSubmissionWindow temp = new BugSubmissionWindow(tempProd);
    }
}
