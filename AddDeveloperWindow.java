import javax.swing.*;
import java.io.*;

public class AddDeveloperWindow {
	private DeveloperWindow disp;
    private JTextField name;

    public JTextField details;
    public JButton submit, cancel;

    public AddDeveloperWindow(DeveloperWindow disp) {
        this.disp = disp;
        disp.display();
    }

    private void addNewDeveloper() {
        // Add a new developer.
    }

    private void cancelSubmission() {
        // Cancel the Submission.
    }
}
