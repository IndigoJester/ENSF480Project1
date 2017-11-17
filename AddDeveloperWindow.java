import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.Date;

public class AddDeveloperWindow {
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
                display.addNewDeveloper();
			} else if (e.getSource() == cancelSubmission) {
                display.cancelSubmission();
			}
		}
	}

    public AddDeveloperWindow() {
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
        main.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

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

    private void addNewDeveloper() {
        // Add a new developer.
    }

    private void cancelSubmission() {
        // Cancel the Submission.
    }

    public static void main (String[] args) throws SQLException {
        AddDeveloperWindow temp = new AddDeveloperWindow();
    }
}
