import javax.swing.*;
import java.io.*;

public class DefaultWindow {
    protected JList productList, bugList;
    protected Bug[] bugs;
    protected JButton submitBug, refresh;
    protected MouseAdapter mouseEventListener;
    protected EventListener buttonEventListener;
    protected Product activeProduct;

    public DefaultWindow() {
        this.display();
    }

    public void display() {
        // Add Display Code Here.
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

    protected void submitNewBug(Product activeProduct) {
        // Display the Bugs from the product argument
    }

    protected void refresh() {
        // Refresh.
    }
}
